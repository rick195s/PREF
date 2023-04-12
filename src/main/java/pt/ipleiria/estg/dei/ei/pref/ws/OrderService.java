package pt.ipleiria.estg.dei.ei.pref.ws;

import pt.ipleiria.estg.dei.ei.pref.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.PaginatedDTO;
import pt.ipleiria.estg.dei.ei.pref.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.pref.entities.Order;
import pt.ipleiria.estg.dei.ei.pref.requests.PageRequest;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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

    @GET
    @Path("/")
    public Response getAllOrders(@BeanParam @Valid PageRequest pageRequest) {
        List<Order> orders;

        Long count = orderBean.count();

        if (pageRequest.getOffset() > count) {
            return Response.ok(new PaginatedDTO<>(count)).build();
        }

        orders = orderBean.getAllOrders(pageRequest.getOffset(), pageRequest.getLimit());
        if (orders == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }

        if (orders.isEmpty()) {
            count = 0L;
        }

        var paginatedDTO = new PaginatedDTO<>(OrderDTO.from(orders), count, pageRequest.getOffset());

        return Response.ok(paginatedDTO).build();
    }



}
