package pt.ipleiria.estg.dei.ei.pref.ws;


import pt.ipleiria.estg.dei.ei.pref.dtos.users.AuthDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.users.TokenDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.users.UserDTO;
import pt.ipleiria.estg.dei.ei.pref.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.pref.entities.users.User;
import pt.ipleiria.estg.dei.ei.pref.security.Authenticated;
import pt.ipleiria.estg.dei.ei.pref.security.TokenIssuer;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("auth")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AuthService {
    @Inject
    private TokenIssuer issuer;

    @EJB
    private UserBean userBean;

    @Context
    private SecurityContext securityContext;

    @POST
    @Path("/login")
    public Response authenticate(@Valid AuthDTO authDTO) {
        if (userBean.canLogin(authDTO.getEmail(), authDTO.getPassword())) {
            String token = issuer.issue(authDTO.getEmail());
            return Response.ok(new TokenDTO(token)).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }


    @POST
    @Path("/logout")
    public Response logout() {
        return Response.status(Response.Status.OK).build();
    }

    @GET
    @Authenticated
    @Path("/user")
    public Response getAuthenticatedUser() {
        String userEmail = securityContext.getUserPrincipal().getName();
        User user = userBean.findUserByEmail(userEmail);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(UserDTO.from(user)).build();
    }
}
