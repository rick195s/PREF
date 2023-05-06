package pt.ipleiria.estg.dei.ei.pref.exceptions.mappers;

import org.hibernate.LazyInitializationException;
import pt.ipleiria.estg.dei.ei.pref.dtos.error.ErrorDTO;


import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class LazyInitializationExceptionMapper implements ExceptionMapper<LazyInitializationException> {
    @Override
    public Response toResponse(LazyInitializationException e) {
        var error = new ErrorDTO("We were unable to load the information... . Please, contact our team for support.");
        return Response.serverError().entity(error).build();
    }
}
