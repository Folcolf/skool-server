package com.folcolf.skool.server.exception;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class NotFoundExceptionMapperTest {
    @Test
    void testToResponse() {
        NotFoundExceptionMapper mapper = new NotFoundExceptionMapper();
        Response response = mapper.toResponse(new NotFoundException("Not found!"));
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        Object entity = response.getEntity();
        assertNotNull(entity, "L'entité de la réponse ne doit pas être nulle");
        assertTrue(entity.toString().contains("Not found!"));
    }
}
