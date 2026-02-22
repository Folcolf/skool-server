package com.folcolf.skool.server.interceptor;

import com.folcolf.skool.server.security.CanAccessClass;
import com.folcolf.skool.server.security.OwnerOrAdmin;
import com.folcolf.skool.server.security.SecurityService;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.ForbiddenException;

import java.lang.reflect.Method;

@ApplicationScoped
public final class AccessInterceptor {

    private AccessInterceptor() {
    }

    @CanAccessClass
    @Interceptor
    static class CanAccessClassInterceptor extends LongInterceptor {
        private final SecurityService securityService;

        @Inject
        public CanAccessClassInterceptor(SecurityService securityService) {
            this.securityService = securityService;
        }

        @AroundInvoke
        public Object check(InvocationContext ctx) throws ForbiddenException {
            Method method = ctx.getMethod();
            CanAccessClass annotation = method.getAnnotation(CanAccessClass.class);
            if (annotation != null) {
                String paramName = annotation.param();
                Long classId = getPathParam(ctx, paramName);
                if (classId == null || !securityService.canAccessClass(classId)) {
                    throw new ForbiddenException("Access denied: cannot access class");
                }
            }
            try {
                return ctx.proceed();
            } catch (Exception e) {
                throw new InternalError(e);
            }
        }

    }

    @OwnerOrAdmin
    @Interceptor
    @Priority(2)
    static class PrincipalOrAdminInterceptor extends LongInterceptor {
        private final SecurityService securityService;

        @Inject
        public PrincipalOrAdminInterceptor(SecurityService securityService) {
            this.securityService = securityService;
        }

        @AroundInvoke
        public Object check(InvocationContext ctx) throws ForbiddenException {
            Method method = ctx.getMethod();
            OwnerOrAdmin annotation = method.getAnnotation(OwnerOrAdmin.class);
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

}
