package pt.ipleiria.estg.dei.ei.pref.ws;
import pt.ipleiria.estg.dei.ei.pref.dtos.OrderPackageDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.SimplePackageDTO;
import pt.ipleiria.estg.dei.ei.pref.ejbs.OrderPackageBean;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/orderPackages")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class OrderPackageService {

    @EJB
    private OrderPackageBean orderPackageBean;

    @GET
    @Path("/")
    public List<OrderPackageDTO> getAllSimplePackages() {
        return OrderPackageDTO.fromOrderPackageList(orderPackageBean.getAllOrderPackages());
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") long id) {
        return Response.ok(OrderPackageDTO.from(orderPackageBean.findOrFail(id))).build();
    }
}
