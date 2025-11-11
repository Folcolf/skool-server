package com.folcolf.skool.server.interceptor;

import com.folcolf.skool.server.security.SecurityService;
import jakarta.annotation.Priority;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.ext.Provider;

import java.lang.reflect.Method;

@Provider
@Priority(1)
public class RolesAllowedInterceptor implements ContainerRequestFilter {
    private final SecurityService securityService;
    private final ResourceInfo resourceInfo;

    @Inject
    public RolesAllowedInterceptor(SecurityService securityService, ResourceInfo resourceInfo) {
        this.securityService = securityService;
        this.resourceInfo = resourceInfo;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) {
        Method method = resourceInfo.getResourceMethod();
        if (method == null)
            return;
        RolesAllowed rolesAllowed = method.getAnnotation(RolesAllowed.class);
        if (rolesAllowed != null) {
            for (String role : rolesAllowed.value()) {
                if ("admin".equalsIgnoreCase(role) && securityService.isAdmin())
                    return;
                if ("viewer".equalsIgnoreCase(role) && securityService.isViewer())
                    return;
            }
            throw new ForbiddenException("Access denied");
        }
    }
}

