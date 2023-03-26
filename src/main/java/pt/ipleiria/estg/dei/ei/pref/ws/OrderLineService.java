package pt.ipleiria.estg.dei.ei.pref.ws;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pt.ipleiria.estg.dei.ei.pref.dtos.OrderLineDTO;
import pt.ipleiria.estg.dei.ei.pref.ejbs.OrderLineBean;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;

@Path("/ordersLine")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class OrderLineService {
    @EJB
    private OrderLineBean orderLineBean;

    @PATCH
    @Path("/{id}")
    public Response dispatchOrder(@Context HttpServletRequest request, @PathParam("id") long id) throws IOException {
        InputStream inputStream = request.getInputStream();

        // Read the message body from the input stream
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(inputStream);

        // Extract the values from the JSON object
        long simplePackageId = rootNode.get("simplePackageId").asLong();
        return Response.ok(OrderLineDTO.from(orderLineBean.dispatchOrder(id, simplePackageId))).build();
    }
}
