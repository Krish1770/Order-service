package com.example.orderservice.tenants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;



public class MultiTenantDataSource extends AbstractRoutingDataSource {

@Autowired
TenantContext tenantContext;
    @Override
     public String determineCurrentLookupKey(){
        return tenantContext.getCurrentThread();
    }
}
