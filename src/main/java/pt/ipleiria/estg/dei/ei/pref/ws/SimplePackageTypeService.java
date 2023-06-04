package pt.ipleiria.estg.dei.ei.pref.ws;

import pt.ipleiria.estg.dei.ei.pref.dtos.packages.SimplePackageTypeDTO;
import pt.ipleiria.estg.dei.ei.pref.ejbs.packages.SimplePackageTypeBean;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/packages")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class SimplePackageTypeService {
    @EJB
    private SimplePackageTypeBean simplePackageTypeBean;

    @GET
    @Path("/")
    public List<SimplePackageTypeDTO> getAllSimplePackageTypes() {
        return SimplePackageTypeDTO.from(simplePackageTypeBean.getAllSimplePackageTypes());
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") String id) {
        return Response.ok(SimplePackageTypeDTO.from(simplePackageTypeBean.findOrFail(id))).build();
    }

    /*@POST
    @Path("/")
    public Response create(SimplePackageDTO simplePackageDTO) {
        SimplePackage simplePackage = simplePackageBean.create(
                simplePackageDTO.getId(),
                simplePackageDTO.getName(),
                simplePackageDTO.getDimension(),
                simplePackageDTO.getMaterialType(),
                simplePackageDTO.getPackageCategory()
        );

        if (simplePackage == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED)
                .entity(SimplePackageDTO.from(simplePackage))
                .build();
    }*/

}

