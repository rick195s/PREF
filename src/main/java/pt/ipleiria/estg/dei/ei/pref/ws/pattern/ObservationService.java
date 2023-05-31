package pt.ipleiria.estg.dei.ei.pref.ws.pattern;


import okhttp3.RequestBody;
import pt.ipleiria.estg.dei.ei.pref.dtos.PaginatedDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.pattern.CategoryObservationDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.pattern.MeasurementObservationDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.pattern.ObservationDTO;
import pt.ipleiria.estg.dei.ei.pref.ejbs.pattern.ObservationBean;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.CategoryObservation;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.MeasurementObservation;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Observation;
import pt.ipleiria.estg.dei.ei.pref.enumerators.PhenomenonType;
import pt.ipleiria.estg.dei.ei.pref.requests.PageRequest;

import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
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
    public Response getAllObservations(@BeanParam @Valid PageRequest pageRequest) {
        List<Observation> observations;

        Long count = observationBean.count();

        if (pageRequest.getOffset() > count) {
            return Response.ok(new PaginatedDTO<>(count)).build();
        }

        observations = observationBean.getAllObservations(pageRequest.getOffset(), pageRequest.getLimit());

        if (observations == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (observations.isEmpty()) {
            count = 0L;
        }

        List<ObservationDTO> convertedObservations = new ArrayList<>();

        for (Observation observation : observations) {
            if (observation.getPhenomenonType().isMeasurement()) {
                convertedObservations.add(MeasurementObservationDTO.from((MeasurementObservation) observation));
            } else {
                convertedObservations.add(CategoryObservationDTO.from((CategoryObservation) observation));
            }
        }

        var paginatedDTO = new PaginatedDTO<>(convertedObservations, count, pageRequest.getOffset());

        return Response.ok(paginatedDTO).build();
    }


    @GET
    @Path("/package/")
    public Response getAllObservationsFromPackage(@QueryParam("id") List<Long> observablePackagesIds) {
        List<Observation> observations = observationBean.getAllPackagesObservations(observablePackagesIds);

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

    // post because the query string is too long
    @POST
    @Path("/packages-has-observations/")
    public Response getPackagesHasObservations(String requestBody) {
        // the id of packages with observations will be returned
        // used to check if a package has observations

        JsonReader reader = Json.createReader(new StringReader(requestBody));
        JsonObject jsonObject = reader.readObject();

        // get the ids of the packages
        // {
        //   "ids": [1,2]
        //}

        List<Long> observablePackagesIds = new ArrayList<>();
        for (int i = 0; i < jsonObject.getJsonArray("ids").size(); i++) {
            observablePackagesIds.add(jsonObject.getJsonArray("ids").getJsonNumber(i).longValue());
        }

        if (observablePackagesIds.isEmpty()) {
            return Response.ok(new HashMap<>()).build();
        }

        return Response.ok(observationBean.packagesHasObservations(observablePackagesIds)).build();
    }

    @POST
    @Path("/")
    public Response createObservation(ObservationDTO observationDTO) {

        Observation observation = observationBean.create(
                observationDTO.getPhenomenonType(),
                observationDTO.getObserverId(),
                observationDTO.getDate(),
                observationDTO.getDetails(),
                observationDTO.getObservablePackageId(),
                observationDTO.getValue()
        );

        if (observation instanceof CategoryObservation) {
            return Response.ok(CategoryObservationDTO.from((CategoryObservation) observation)).build();
        }

        return Response.status(Response.Status.CREATED).entity(MeasurementObservationDTO.from((MeasurementObservation) observation)).build();
    }

    @GET
    @Path("/phenomenonTypes")
    public Response getPhenomenonTypes() {
        List<String> phenomenonTypes = new LinkedList<>();
        for (PhenomenonType phenomenonType : PhenomenonType.values()) {
            phenomenonTypes.add(phenomenonType.name());
        }
        return Response.ok(phenomenonTypes).build();
    }

}
