package com.folcolf.skool.server.interceptor;

import com.folcolf.skool.server.security.OwnerOrAdmin;
import com.folcolf.skool.server.security.SecurityService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.PathParam;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@QuarkusTest
class OwnerOrAdminInterceptorTest {
    @Inject
    TestResource resource;
    @InjectMock
    SecurityService securityService;

    @Test
    void shouldAllowAdmin() {
        when(securityService.isAdmin()).thenReturn(true);
        assertDoesNotThrow(() -> resource.securedMethod(1L));
    }

    @Test
    void shouldAllowPrincipal() {
        when(securityService.isAdmin()).thenReturn(false);
        when(securityService.isPrincipal(1L)).thenReturn(true);
        assertDoesNotThrow(() -> resource.securedMethod(1L));
    }

    @Test
    void shouldDenyNonAdminNonPrincipal() {
        when(securityService.isAdmin()).thenReturn(false);
        when(securityService.isPrincipal(1L)).thenReturn(false);
        assertThrows(ForbiddenException.class, () -> resource.securedMethod(1L));
    }

    @Test
    void shouldDenyIfParamNotFound() {
        when(securityService.isAdmin()).thenReturn(false);
        when(securityService.isPrincipal((String) null)).thenReturn(false);
        assertThrows(ForbiddenException.class, () -> resource.securedMethod(null));
    }

    @Test
    void shouldDenyIfParamNotParsable() {
        when(securityService.isAdmin()).thenReturn(false);
        Method m;
        try {
            m = DummyClass.class.getMethod("securedMethod", String.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        InvocationContext ctx = new InvocationContext() {
            @Override
            public Object getTarget() {return new DummyClass();}

            @Override
            public Method getMethod() {return m;}

            @Override
            public Object[] getParameters() {return new Object[]{"notANumber"};}

            @Override
            public java.util.Map<String, Object> getContextData() {return new java.util.HashMap<>();}

            @Override
            public Object proceed() {return null;}

            @Override
            public Object getTimer() {return null;}

            @Override
            public Constructor<?> getConstructor() {return null;}

            @Override
            public void setParameters(Object[] params) { /* no-op for test */ }


        };
        AccessInterceptor.PrincipalOrAdminInterceptor interceptor = new AccessInterceptor.PrincipalOrAdminInterceptor(securityService);
        assertThrows(ForbiddenException.class, () -> interceptor.check(ctx));
    }

    @Test
    void shouldAllowIfNoAnnotation() throws Exception {
        Method m = Object.class.getMethod("toString");
        assertDoesNotThrow(() -> new AccessInterceptor.PrincipalOrAdminInterceptor(securityService).check(new InvocationContext() {
            @Override
            public Object getTarget() {return resource;}

            @Override
            public Method getMethod() {return m;}

            @Override
            public Object[] getParameters() {return new Object[0];}

            @Override
            public java.util.Map<String, Object> getContextData() {return null;}

            @Override
            public Object proceed() {return null;}

            @Override
            public Object getTimer() {return null;}

            @Override
            public Constructor<?> getConstructor() {return null;}

            @Override
            public void setParameters(Object[] params) { /* no-op for test */ }


        }));
    }

    static class DummyClass {
        @OwnerOrAdmin
        public void securedMethod(@PathParam("id") String id) { /* test only */ }
    }
}
