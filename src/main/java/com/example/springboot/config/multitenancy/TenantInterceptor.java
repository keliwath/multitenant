package com.example.springboot.config.multitenancy;

import com.example.springboot.config.JWTHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import java.text.ParseException;

@Component
@Slf4j
public class TenantInterceptor implements WebRequestInterceptor {

    private static final String AUTH_HEADER = "Authorization";
    private final String defaultTenant;

    @Autowired
    public TenantInterceptor(
            @Value("${multitenancy.tenant.default-tenant:#{null}}") String defaultTenant) {
        this.defaultTenant = defaultTenant;
    }

    @Override
    public void preHandle(WebRequest request) throws ParseException {
        String tenantId;
        String authHeader = request.getHeader(AUTH_HEADER);
        if (authHeader != null) {
            tenantId = JWTHelper.extractRealmFromHeader(authHeader);
        } else if (this.defaultTenant != null) {
            tenantId = this.defaultTenant;
        } else {
            throw new RuntimeException("No tenant");
        }
        TenantContext.setTenantId(tenantId);
    }

    @Override
    public void postHandle(@NonNull WebRequest request, ModelMap model) {
        TenantContext.clear();
    }

    @Override
    public void afterCompletion(@NonNull WebRequest request, Exception ex) {}
}
