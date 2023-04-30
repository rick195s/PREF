package pt.ipleiria.estg.dei.ei.pref.ws.pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pt.ipleiria.estg.dei.ei.pref.dtos.PackageLogDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.pattern.CategoryObservationDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.pattern.MeasurementDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.pattern.ObservationDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.pattern.ObserverDTO;
import pt.ipleiria.estg.dei.ei.pref.ejbs.pattern.ObservationBean;
import pt.ipleiria.estg.dei.ei.pref.ejbs.pattern.ObserverBean;
import pt.ipleiria.estg.dei.ei.pref.entities.PackageLog;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.CategoryObservation;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Measurement;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Observation;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Observer;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PhenomenonType;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/observers")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ObserverService {
    @EJB
    private ObserverBean observerBean;

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") long id) {
        return Response.ok(ObserverDTO.from(observerBean.findOrFail(id))).build();
    }

    @GET
    @Path("/")
    public List<ObserverDTO> getAllObservers() {
        return ObserverDTO.from(observerBean.getAllObservers());
    }

    @POST
    @Path("/")
    public Response createObserver(ObserverDTO observerDTO) {
        Observer observer = observerBean.create(
                observerDTO.getType()
        );

        if (observer == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED)
                .entity(ObserverDTO.from(observer))
                .build();
    }

}
