package pt.ipleiria.estg.dei.ei.pref.ws;

import pt.ipleiria.estg.dei.ei.pref.dtos.ProductPackageDTO;
import pt.ipleiria.estg.dei.ei.pref.ejbs.packages.SimplePackageBean;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/product-packages")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProductPackageService {
    @EJB
    private SimplePackageBean simplePackageBean;

    @GET
    @Path("/")
    public List<ProductPackageDTO> getAllProductPackageTypes() {
        return ProductPackageDTO.from(simplePackageBean.getAllProductPackageTypes());
    }

}

