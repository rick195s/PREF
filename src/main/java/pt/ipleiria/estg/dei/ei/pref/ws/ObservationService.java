package pt.ipleiria.estg.dei.ei.pref.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import pt.ipleiria.estg.dei.ei.pref.dtos.OrderDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.PaginatedDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.SimplePackageDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.pattern.CategoryObservationDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.pattern.MeasurementDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.pattern.ObservationDTO;
import pt.ipleiria.estg.dei.ei.pref.ejbs.OrderBean;
import pt.ipleiria.estg.dei.ei.pref.ejbs.pattern.ObservationBean;
import pt.ipleiria.estg.dei.ei.pref.entities.Order;
import pt.ipleiria.estg.dei.ei.pref.entities.packages.SimplePackage;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.CategoryObservation;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Measurement;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Observation;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PhenomenonType;
import pt.ipleiria.estg.dei.ei.pref.requests.PageRequest;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
        String authorString = rootNode.get("author").asText();
        long simplePackageId = rootNode.get("simplePackageId").asLong();
        String value = rootNode.get("value").asText();

        Observation observation = observationBean.create(phenomenonType, authorString, simplePackageId, value);

        if (observation instanceof CategoryObservation) {
            return Response.ok(CategoryObservationDTO.from((CategoryObservation) observation)).build();
        }

        return Response.ok(MeasurementDTO.from((Measurement) observation)).build();
    }

}
