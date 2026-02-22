package com.folcolf.skool.server.exception;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(NotFoundException exception) {
        // Ajout d'un body JSON simple pour que l'entité ne soit jamais nulle
        String json = "{\"error\":\"Not found\",\"message\":\"" + exception.getMessage() + "\"}";
        return Response.status(Response.Status.NOT_FOUND)
                .entity(json)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
