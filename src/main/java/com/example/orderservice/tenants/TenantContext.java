package com.example.orderservice.tenants;


import org.springframework.stereotype.Component;

@Component
public class TenantContext {

    private  final ThreadLocal<String> currentTenant=new ThreadLocal<>();

    public   String getCurrentThread()
    {
        return currentTenant.get();
    };

    public   void setCurrentThread(String Tenant)
    {
        currentTenant.set(Tenant);
    }

}
