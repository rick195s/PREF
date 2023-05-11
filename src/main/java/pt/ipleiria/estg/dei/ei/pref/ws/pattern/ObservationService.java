package pt.ipleiria.estg.dei.ei.pref.ws.pattern;


import pt.ipleiria.estg.dei.ei.pref.dtos.pattern.CategoryObservationDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.pattern.MeasurementObservationDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.pattern.ObservationDTO;
import pt.ipleiria.estg.dei.ei.pref.ejbs.pattern.ObservationBean;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.CategoryObservation;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.MeasurementObservation;
import pt.ipleiria.estg.dei.ei.pref.entities.pattern.Observation;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
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
    public List<ObservationDTO> getAllObservations() {
        List<ObservationDTO> observationDTOS = new LinkedList<>();

        List<Observation> observations = observationBean.getAllObservations();
        for (Observation observation : observations) {
            if (observation.getPhenomenonType().isMeasurement()) {
                observationDTOS.add(MeasurementObservationDTO.from((MeasurementObservation) observation));
            } else {
                observationDTOS.add(CategoryObservationDTO.from((CategoryObservation) observation));
            }
        }

        return observationDTOS;
    }

    @GET
    @Path("/package/{observablePackageId}")
    public Response getAllObservationsFromPackage(@PathParam("observablePackageId") long observablePackageId) {
        List<Observation> observations = observationBean.getAllPackageObservations(observablePackageId);

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

}
