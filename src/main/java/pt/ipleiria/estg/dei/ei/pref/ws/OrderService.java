package pt.ipleiria.estg.dei.ei.pref.ws;

import pt.ipleiria.estg.dei.ei.pref.dtos.packages.OrderPackageTypeDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.requests.OrderDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.detailed.DetailedOrderDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.PaginatedDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.requests.ProductQuantityDTO;
import pt.ipleiria.estg.dei.ei.pref.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.pref.ejbs.packages.OrderPackageBean;
import pt.ipleiria.estg.dei.ei.pref.entities.Order;
import pt.ipleiria.estg.dei.ei.pref.requests.PageRequest;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/orders")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class OrderService {
    @EJB
    private OrderBean orderBean;

    @EJB
    private OrderPackageBean orderPackageBean;

    @GET
    @Path("/{trackingNumber}")
    public Response get(@PathParam("trackingNumber") String id) {
        return Response.ok(DetailedOrderDTO.from(orderBean.findOrFail(id))).build();
    }

    @GET
    @Path("/")
    public Response getAllOrders(@BeanParam @Valid PageRequest pageRequest) {
        List<Order> orders;

        Long count = orderBean.count(pageRequest.getCarrier());

        if (pageRequest.getOffset() > count) {
            return Response.ok(new PaginatedDTO<>(count)).build();
        }

        orders = orderBean.getAllOrders(pageRequest.getOffset(), pageRequest.getLimit(), pageRequest.getCarrier());
        if (orders == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }

        if (orders.isEmpty()) {
            count = 0L;
        }

        var paginatedDTO = new PaginatedDTO<>(DetailedOrderDTO.from(orders), count, pageRequest.getOffset());

        return Response.ok(paginatedDTO).build();
    }

    @POST
    @Path("/")
    public Response createOrder(OrderDTO orderDTO){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Map<Long, Integer> map = new HashMap<>();
        for (ProductQuantityDTO productQuantityDTO : orderDTO.getProductsQuantities()) {
            map.put(productQuantityDTO.getProductId(), productQuantityDTO.getQuantity());
        }

        Order order = orderBean.create(dateFormat.format(new Date()), map, orderDTO.getSource(), orderDTO.getDestination(), orderDTO.getCarrier(), orderDTO.getShippingMethod());

        return Response
                .ok(DetailedOrderDTO.from(orderBean.findOrFail(order.getId())))
                .status(Response.Status.CREATED).build();
    }

    @PATCH
    @Path("/{trackingNumber}")
    public Response associatePackageWithOrder(@PathParam("trackingNumber") String orderId, OrderPackageTypeDTO orderPackageTypeDTO) {
        orderPackageBean.create(orderPackageTypeDTO.getId(), orderId);

        DetailedOrderDTO detailedOrderDTO = DetailedOrderDTO.from(orderBean.findOrFail(orderId));

        return Response.ok(detailedOrderDTO).build();
    }

    @PATCH
    @Path("/{trackingNumber}/pack")
    public Response packOrder(@PathParam("trackingNumber") String id) {
        return Response.ok(DetailedOrderDTO.from(orderBean.packOrder(id))).build();
    }

    @GET
    @Path("/carriers")
    public Response getCarriers() {
        return Response.ok(orderBean.getCarriers()).build();
    }
}
