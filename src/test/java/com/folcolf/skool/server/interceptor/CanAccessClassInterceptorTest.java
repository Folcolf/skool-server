package com.folcolf.skool.server.interceptor;

import com.folcolf.skool.server.security.CanAccessClass;
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
import java.util.Map;

@QuarkusTest
class CanAccessClassInterceptorTest {
    @Inject
    TestResource resource;
    @InjectMock
    SecurityService securityService;

    @Test
    void shouldAllowAccess() {
        when(securityService.canAccessClass(1L)).thenReturn(true);
        assertDoesNotThrow(() -> resource.securedClassMethod(1L));
    }

    @Test
    void shouldDenyAccess() {
        when(securityService.canAccessClass(1L)).thenReturn(false);
        assertThrows(ForbiddenException.class, () -> resource.securedClassMethod(1L));
    }

    @Test
    void shouldDenyIfParamNotFound() {
        when(securityService.canAccessClass(null)).thenReturn(false);
        assertThrows(ForbiddenException.class, () -> resource.securedClassMethod(null));
    }

    @Test
    void shouldDenyIfParamNotParsable() throws Exception {
        Method m = DummyClass.class.getMethod("securedClassMethod", String.class);
        CanAccessClassInterceptor interceptor = new CanAccessClassInterceptor(securityService);

        InvocationContext ctx = new InvocationContext() {
            @Override
            public Object getTarget() {return new DummyClass();}

            @Override
            public Method getMethod() {return m;}

            @Override
            public Object[] getParameters() {return new Object[]{"notANumber"};}

            @Override
            public Map<String, Object> getContextData() {return null;}

            @Override
            public Object proceed() {return null;}

            @Override
            public Object getTimer() {return null;}

            @Override
            public Constructor<?> getConstructor() {return null;}

            @Override
            public void setParameters(Object[] params) { /* no-op for test */ }
        };
        assertThrows(ForbiddenException.class, () -> interceptor.check(ctx));
    }

    @Test
    void shouldAllowIfNoAnnotation() throws Exception {
        Method m = Object.class.getMethod("toString");
        InvocationContext ctx = new InvocationContext() {
            @Override
            public Object getTarget() {return resource;}

            @Override
            public Method getMethod() {return m;}

            @Override
            public Object[] getParameters() {return new Object[0];}

            @Override
            public Map<String, Object> getContextData() {return null;}

            @Override
            public Object proceed() {return null;}

            @Override
            public Object getTimer() {return null;}

            @Override
            public Constructor<?> getConstructor() {return null;}

            @Override
            public void setParameters(Object[] params) { /* no-op for test */ }
        };
        assertDoesNotThrow(() -> new CanAccessClassInterceptor(securityService).check(ctx));
    }

    static class DummyClass {
        @CanAccessClass()
        public void securedClassMethod(@PathParam("classId") String classId) { /* test only */ }
    }
}
