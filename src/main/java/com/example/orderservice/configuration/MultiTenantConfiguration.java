package com.example.orderservice.configuration;

import com.example.orderservice.tenants.MultiTenantDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@Slf4j
public class MultiTenantConfiguration {

    @Value("${defaultTenant}")
    String defaultTenant;


    @Bean
    @ConfigurationProperties
    public DataSource dataSource() {
        log.info("yes i am here");

        File[] files = Paths.get("allTenants").toFile().listFiles();
        Map<Object, Object> resolvedDataSourceBuilder = new HashMap<>();
      assert files != null;
        for (File file : files) {
            Properties properties = new Properties();
            log.info("hi");

            log.info(defaultTenant);
            DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();

            try {
                properties.load(new FileInputStream(file));

                String tenantName = properties.getProperty("name");
              DataSource dataSource=  dataSourceBuilder.driverClassName(properties.getProperty("datasource.driverClassName"))
                .username(properties.getProperty("datasource.username"))
              .password(properties.getProperty("datasource.password"))
                .url(properties.getProperty("datasource.url")).build();



                resolvedDataSourceBuilder.put(tenantName, dataSource);
            } catch (IOException e) {
                System.out.println("error");
                throw new RuntimeException(e);
            }
        }

        AbstractRoutingDataSource dataSource=new MultiTenantDataSource();

        dataSource.setDefaultTargetDataSource(resolvedDataSourceBuilder.get(defaultTenant));
        dataSource.setTargetDataSources(resolvedDataSourceBuilder);

        return dataSource;


    }
}
