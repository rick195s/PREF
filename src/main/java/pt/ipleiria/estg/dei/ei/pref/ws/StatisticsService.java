package pt.ipleiria.estg.dei.ei.pref.ws;

import pt.ipleiria.estg.dei.ei.pref.ejbs.StatisticsBean;
import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/statistics")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class StatisticsService {
    @EJB
    private StatisticsBean statisticsBean;

    @GET
    @Path("/temperature-by-carrier/{carrier}")
    public Response getTemperatureByCarrier(@PathParam("carrier") String carrier) {
        return Response.ok(statisticsBean.getTemperatureByCarrier(carrier)).build();
    }

    @GET
    @Path("/{role}")
    public Response getStatistics(@PathParam("role") String role) {
        return Response.ok(statisticsBean.getStatisticsDashboardUsers(role)).build();
    }


}
