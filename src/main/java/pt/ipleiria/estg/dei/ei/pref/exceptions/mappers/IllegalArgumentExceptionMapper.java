package pt.ipleiria.estg.dei.ei.pref.exceptions.mappers;



import pt.ipleiria.estg.dei.ei.pref.dtos.error.ErrorDTO;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    @Override
    public Response toResponse(IllegalArgumentException e) {
        return getResponse(e);
    }

    protected static Response getResponse(IllegalArgumentException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorDTO(e.getMessage())).build();
    }

    public IllegalArgumentExceptionMapper() {
    }
}
