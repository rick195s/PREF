package pt.ipleiria.estg.dei.ei.pref.ws;
import pt.ipleiria.estg.dei.ei.pref.dtos.OrderPackageTypeDTO;
import pt.ipleiria.estg.dei.ei.pref.ejbs.OrderPackageTypeBean;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/orderPackageTypes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class OrderPackageTypeService {

    @EJB
    private OrderPackageTypeBean orderPackageTypeBean;

    @GET
    @Path("/")
    public List<OrderPackageTypeDTO> getAllSimplePackages() {
        return OrderPackageTypeDTO.fromOrderPackageTypeList(orderPackageTypeBean.getAllOrderPackageTypes());
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") long id) {
        return Response.ok(OrderPackageTypeDTO.from(orderPackageTypeBean.findOrFail(id))).build();
    }
}
