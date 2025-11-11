package com.folcolf.skool.server.interceptor;

import com.folcolf.skool.server.security.PrincipalOrAdmin;
import com.folcolf.skool.server.security.SecurityService;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.ForbiddenException;

import java.lang.reflect.Method;

@PrincipalOrAdmin
@Interceptor
@Priority(2)
public class PrincipalOrAdminInterceptor extends LongInterceptor {
    private final SecurityService securityService;

    @Inject
    public PrincipalOrAdminInterceptor(SecurityService securityService) {
        this.securityService = securityService;
    }

    @AroundInvoke
    public Object check(InvocationContext ctx) throws ForbiddenException {
        Method method = ctx.getMethod();
        PrincipalOrAdmin annotation = method.getAnnotation(PrincipalOrAdmin.class);
        if (annotation != null) {
            String paramName = annotation.param();
            Long id = getPathParam(ctx, paramName);
            if (!securityService.isAdmin() && (id == null || !securityService.isPrincipal(id))) {
                throw new ForbiddenException("Access denied: not admin or principal");
            }
        }
        try {
            return ctx.proceed();
        } catch (Exception e) {
            throw new InternalError(e);
        }
    }

}
