package pt.ipleiria.estg.dei.ei.pref.ws;

import pt.ipleiria.estg.dei.ei.pref.dtos.PackageLogDTO;
import pt.ipleiria.estg.dei.ei.pref.ejbs.PackageLogBean;
import pt.ipleiria.estg.dei.ei.pref.entities.PackageLog;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/package-logs")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PackageLogService {
    @EJB
    private PackageLogBean packageLogBean;

    @GET
    @Path("/package/{simplePackageId}")
    public Response getAllPackageLogs(@PathParam("simplePackageId") long simplePackageId) {
        return Response.ok(PackageLogDTO.from(packageLogBean.getAllPackageLogs(simplePackageId))).build();
    }

    @POST
    @Path("/")
    public Response createPackageLog(PackageLogDTO packageLogDTO) {
        PackageLog packageLog = packageLogBean.create(
                //packageLogDTO.getSimplePackageId(),
                packageLogDTO.getLocation(),
                packageLogDTO.getTemperature(),
                packageLogDTO.getHumidity()
        );

        if (packageLog == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED)
                .entity(PackageLogDTO.from(packageLog))
                .build();
    }


}
