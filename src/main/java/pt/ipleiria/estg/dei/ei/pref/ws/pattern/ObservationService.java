package pt.ipleiria.estg.dei.ei.pref.ws.pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pt.ipleiria.estg.dei.ei.pref.dtos.pattern.CategoryObservationDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.pattern.MeasurementObservationDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.pattern.ObservationDTO;
import pt.ipleiria.estg.dei.ei.pref.ejbs.pattern.ObservationBean;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.CategoryObservation;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.MeasurementObservation;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Observation;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PhenomenonType;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/observations")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ObservationService {
    @EJB
    private ObservationBean observationBean;

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") long id) {
        return Response.ok(ObservationDTO.from(observationBean.findOrFail(id))).build();
    }

    @GET
    @Path("/")
    public List<ObservationDTO> getAllObservations() {
        return ObservationDTO.from(observationBean.getAllObservations());
    }

    @GET
    @Path("/package/{simplePackageId}")
    public Response getAllObservationsFromPackage(@PathParam("simplePackageId") long simplePackageId) {
        List<Observation> observations = observationBean.getAllPackageObservations(simplePackageId);

        List<ObservationDTO> results = new ArrayList<>();
        for (Observation obs : observations) {
            if (obs instanceof CategoryObservation) {
                results.add(CategoryObservationDTO.from((CategoryObservation) obs));
            } else if (obs instanceof MeasurementObservation) {
                results.add(MeasurementObservationDTO.from((MeasurementObservation) obs));
            }
        }

        return Response.ok(results).build();
    }

    @POST
    @Path("/")
    public Response createObservation(String jsonObject) {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = null;

        try {
            rootNode = objectMapper.readTree(jsonObject);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        PhenomenonType phenomenonType = PhenomenonType.valueOf(rootNode.get("phenomenonType").asText());
        long observerId = rootNode.get("observerId").asLong();
        String date = rootNode.get("date").asText();
        String details = rootNode.get("details").asText();
        long simplePackageId = rootNode.get("simplePackageId").asLong();
        String value = rootNode.get("value").asText();

        Observation observation = observationBean.create(phenomenonType, observerId, date, details,simplePackageId, value);

        if (observation instanceof CategoryObservation) {
            return Response.ok(CategoryObservationDTO.from((CategoryObservation) observation)).build();
        }

        return Response.status(Response.Status.CREATED).entity(MeasurementObservationDTO.from((MeasurementObservation) observation)).build();
    }

}