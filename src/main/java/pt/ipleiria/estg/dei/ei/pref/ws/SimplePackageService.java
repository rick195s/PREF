package pt.ipleiria.estg.dei.ei.pref.ws;

import pt.ipleiria.estg.dei.ei.pref.dtos.SimplePackageDTO;
import pt.ipleiria.estg.dei.ei.pref.ejbs.SimplePackageBean;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/packages")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class SimplePackageService {
    @EJB
    private SimplePackageBean simplePackageBean;

    @GET
    @Path("/")
    public List<SimplePackageDTO> getAllSimplePackages() {
        return SimplePackageDTO.from(simplePackageBean.getAllSimplePackages());
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") long id) {
        return Response.ok(SimplePackageDTO.from(simplePackageBean.findOrFail(id))).build();
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

