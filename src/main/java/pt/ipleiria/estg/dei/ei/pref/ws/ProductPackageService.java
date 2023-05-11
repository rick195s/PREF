package pt.ipleiria.estg.dei.ei.pref.ws;

import pt.ipleiria.estg.dei.ei.pref.dtos.packages.ProductPackageDTO;
import pt.ipleiria.estg.dei.ei.pref.ejbs.packages.SimplePackageTypeBean;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/product-packages")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProductPackageService {
    @EJB
    private SimplePackageTypeBean simplePackageTypeBean;

    @GET
    @Path("/")
    public List<ProductPackageDTO> getAllProductPackageTypes() {
        return ProductPackageDTO.from(simplePackageTypeBean.getAllProductPackageTypes());
    }

}

