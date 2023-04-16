package pt.ipleiria.estg.dei.ei.pref.exceptions.mappers;


import pt.ipleiria.estg.dei.ei.pref.dtos.error.ErrorDTO;
import pt.ipleiria.estg.dei.ei.pref.exceptions.MyEntityNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class MyEntityNotFoundExceptionMapper implements ExceptionMapper<MyEntityNotFoundException> {
    private static final Logger logger =
        Logger.getLogger(MyEntityNotFoundException.class.getCanonicalName());

    @Override
    public Response toResponse(MyEntityNotFoundException e) {
        String errorMsg = e.getMessage();
        logger.warning("ERROR: " + errorMsg);
        return Response.status(Response.Status.NOT_FOUND)
            .entity(new ErrorDTO(errorMsg))
            .build();
    }
}
