package pt.ipleiria.estg.dei.ei.pref.ws;

import pt.ipleiria.estg.dei.ei.pref.ejbs.StatisticsBean;
import pt.ipleiria.estg.dei.ei.pref.ejbs.UserBean;
import pt.ipleiria.estg.dei.ei.pref.entities.users.User;
import pt.ipleiria.estg.dei.ei.pref.security.Authenticated;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/statistics")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class StatisticsService {
    @EJB
    private StatisticsBean statisticsBean;

    @EJB
    private UserBean userBean;

    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/temperature-by-carrier/{carrier}")
    public Response getTemperatureByCarrier(@PathParam("carrier") String carrier) {
        return Response.ok(statisticsBean.getTemperatureByCarrier(carrier)).build();
    }

    @GET
    @Authenticated
    @Path("/")
    public Response getStatistics() {
        User user =  userBean.findUserByEmail(securityContext.getUserPrincipal().getName());
        return Response.ok(statisticsBean.getStatisticsDashboardUsers(user.getRole())).build();
    }

    @GET
    @Authenticated
    @Path("/orders-comparation")
    public Response getOrdersComparation() {
        return Response.ok(statisticsBean.getOrdersComparation()).build();
    }

    @GET
    @Authenticated
    @Path("/orders-returned")
    public Response getOrdersReturned() {
        return Response.ok(statisticsBean.getOrdersComparation()).build();
    }


}
