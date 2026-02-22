package com.folcolf.skool.server.interceptor;

import com.folcolf.skool.server.security.SecurityService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ResourceInfo;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

class RolesAllowedInterceptorTest {
    SecurityService securityService;
    ResourceInfo resourceInfo;
    RolesAllowedInterceptor interceptor;
    ContainerRequestContext ctx;

    @BeforeEach
    void setUp() {
        securityService = mock(SecurityService.class);
        resourceInfo = mock(ResourceInfo.class);
        interceptor = new RolesAllowedInterceptor(securityService, resourceInfo);
        ctx = mock(ContainerRequestContext.class);
    }

    @Test
    void allowsAdmin() throws Exception {
        when(securityService.isAdmin()).thenReturn(true);
        Method m = TestResource.class.getMethod("adminMethod");
        when(resourceInfo.getResourceMethod()).thenReturn(m);
        assertDoesNotThrow(() -> interceptor.filter(ctx));
    }

    @Test
    void allowsViewer() throws Exception {
        when(securityService.isViewer()).thenReturn(true);
        Method m = TestResource.class.getMethod("viewerMethod");
        when(resourceInfo.getResourceMethod()).thenReturn(m);
        assertDoesNotThrow(() -> interceptor.filter(ctx));
    }

    @Test
    void deniesIfNoRole() throws Exception {
        when(securityService.isAdmin()).thenReturn(false);
        when(securityService.isViewer()).thenReturn(false);
        Method m = TestResource.class.getMethod("adminMethod");
        when(resourceInfo.getResourceMethod()).thenReturn(m);
        assertThrows(ForbiddenException.class, () -> interceptor.filter(ctx));
    }

    @Test
    void noAnnotationDoesNothing() throws Exception {
        Method m = TestResource.class.getMethod("noRoleMethod");
        when(resourceInfo.getResourceMethod()).thenReturn(m);
        assertDoesNotThrow(() -> interceptor.filter(ctx));
    }

    static class TestResource {
        @RolesAllowed("admin")
        public void adminMethod() { /* Méthode vide pour test d'annotation */ }

        @RolesAllowed("viewer")
        public void viewerMethod() { /* Méthode vide pour test d'annotation */ }

        public void noRoleMethod() { /* Méthode vide pour test d'absence d'annotation */ }
    }
}
