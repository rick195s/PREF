package pt.ipleiria.estg.dei.ei.pref.ws;

import pt.ipleiria.estg.dei.ei.pref.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.pref.ejbs.OrderBean;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/orders")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class OrderService {
    @EJB
    private OrderBean orderBean;

    @GET
    @Path("/{trackingNumber}")
    public Response get(@PathParam("trackingNumber") long trackingNumber) {
        return Response.ok(OrderDTO.from(orderBean.findOrFail(trackingNumber))).build();
    }

}
