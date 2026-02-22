package com.folcolf.skool.server.interceptor;

import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.PathParam;

import java.lang.annotation.Annotation;

abstract class LongInterceptor {

    public abstract Object check(InvocationContext ctx) throws ForbiddenException;

    Long getPathParam(InvocationContext ctx, String paramName) {
        Annotation[][] paramAnnotations = ctx.getMethod().getParameterAnnotations();
        Object[] params = ctx.getParameters();
        for (int i = 0; i < paramAnnotations.length; i++) {
            Long id = extractIdFromParam(paramAnnotations[i], params[i], paramName);
            if (id != null) {
                return id;
            }
        }
        return null;
    }

    private Long extractIdFromParam(Annotation[] annotations, Object param, String paramName) {
        for (Annotation a : annotations) {
            if (a instanceof PathParam pathParam && pathParam.value().equals(paramName)) {
                if (param instanceof Long l) {
                    return l;
                }
                if (param instanceof String s) {
                    try {
                        return Long.valueOf(s);
                    } catch (Exception ignore) {
                        // Ignore parse errors, return null below if not parsable
                    }
                }
            }
        }
        return null;
    }
}
