package com.folcolf.skool.server.interceptor;

import com.folcolf.skool.server.security.CanAccessClass;
import com.folcolf.skool.server.security.OwnerOrAdmin;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.PathParam;

@ApplicationScoped
public class TestResource {

    @OwnerOrAdmin
    public void securedMethod(@PathParam("id") Long id) {
        // No-op, security is handled by interceptor
    }

    @CanAccessClass()
    public void securedClassMethod(@PathParam("classId") Long classId) {
        // No-op, security is handled by interceptor
    }
}
