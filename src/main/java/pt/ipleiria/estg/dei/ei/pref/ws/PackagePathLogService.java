package pt.ipleiria.estg.dei.ei.pref.ws;

import pt.ipleiria.estg.dei.ei.pref.dtos.PackagePathLogDTO;
import pt.ipleiria.estg.dei.ei.pref.ejbs.PackagePathLogBean;
import pt.ipleiria.estg.dei.ei.pref.entities.PackagePathLog;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/package-path-logs")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class PackagePathLogService {
    @EJB
    private PackagePathLogBean packagePathLogBean;

    @POST
    @Path("/")
    public Response create(PackagePathLogDTO packagePathLogDTO) {
        PackagePathLog packagePathLog = packagePathLogBean.create(
                packagePathLogDTO.getId(),
                packagePathLogDTO.getLocation(),
                packagePathLogDTO.getTransportationType(),
                packagePathLogDTO.getDateOfArrival(),
                packagePathLogDTO.getDateOfDeparture(),
                packagePathLogDTO.getOrderTrackingNumber()
        );

        return Response.status(Response.Status.CREATED)
                .entity(PackagePathLogDTO.from(packagePathLog))
                .build();
    }

}

