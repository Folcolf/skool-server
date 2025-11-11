package com.folcolf.skool.server.interceptor;

import com.folcolf.skool.server.security.CanAccessClass;
import com.folcolf.skool.server.security.SecurityService;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.ForbiddenException;

import java.lang.reflect.Method;

@CanAccessClass
@Interceptor
@Priority(2)
public class CanAccessClassInterceptor extends LongInterceptor {
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
