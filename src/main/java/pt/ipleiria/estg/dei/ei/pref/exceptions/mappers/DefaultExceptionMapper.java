package pt.ipleiria.estg.dei.ei.pref.exceptions.mappers;



import pt.ipleiria.estg.dei.ei.pref.dtos.error.ErrorDTO;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception e) {
        var error = new ErrorDTO("Ups... looks like something went wrong. Please, contact our team for support");
        return Response.serverError().entity(error).build();
    }
}
