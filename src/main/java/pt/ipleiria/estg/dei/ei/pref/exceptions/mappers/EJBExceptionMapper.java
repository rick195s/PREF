package pt.ipleiria.estg.dei.ei.pref.exceptions.mappers;


import pt.ipleiria.estg.dei.ei.pref.dtos.error.ErrorDTO;

import javax.ejb.EJBException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EJBExceptionMapper implements ExceptionMapper<EJBException> {
    @Override
    public Response toResponse(EJBException e) {
        var cause = e.getCausedByException();

        if (cause instanceof EntityExistsException) {
            return EntityExistsExceptionMapper.getResponse((EntityExistsException) cause);
        }

        if (cause instanceof EntityNotFoundException) {
            return EntityNotFoundExceptionMapper.getResponse((EntityNotFoundException) cause);
        }


        if (cause instanceof IllegalArgumentException) {
            return IllegalArgumentExceptionMapper.getResponse((IllegalArgumentException) cause);
        }

        return Response.serverError().entity(new ErrorDTO(e.getMessage())).build();
    }
}
