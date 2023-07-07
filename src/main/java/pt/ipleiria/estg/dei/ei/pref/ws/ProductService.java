package pt.ipleiria.estg.dei.ei.pref.ws;

import pt.ipleiria.estg.dei.ei.pref.dtos.PaginatedDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.ProductDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.packages.ProductPackageDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.packages.ProductPackageTypeDTO;
import pt.ipleiria.estg.dei.ei.pref.ejbs.ProductBean;
import pt.ipleiria.estg.dei.ei.pref.entities.Product;
import pt.ipleiria.estg.dei.ei.pref.requests.PageRequest;

import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/products")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProductService {
    @EJB
    private ProductBean productBean;

    @GET
    @Path("/")
    public Response getAllProducts(@BeanParam @Valid PageRequest pageRequest) {
        List<Product> products;

        Long count = productBean.count();

        if (pageRequest.getOffset() > count) {
            return Response.ok(new PaginatedDTO<>(count)).build();
        }

        products = productBean.getAllProducts(pageRequest.getOffset(), pageRequest.getLimit());
        if (products == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }

        if (products.isEmpty()) {
            count = 0L;
        }

        var paginatedDTO = new PaginatedDTO<>(ProductDTO.from(products, true), count, pageRequest.getOffset());

        return Response.ok(paginatedDTO).build();
    }

   /* @POST
    @Path("/")
    public Response createProduct(ProductDTO productDTO){
        HashSet<String> productPackagesIds = new HashSet<>();
        for (ProductPackageTypeDTO productPackageTypeDTO : productDTO.getProductPackageTypes()) {
            productPackagesIds.add(productPackageTypeDTO.getId());
        }


        Product product = productBean.create(
                productDTO.getName(),
                productDTO.getLength(),
                productDTO.getWidth(),
                productDTO.getHeight(),
                productPackagesIds,
                productDTO.getType()
        );


        return Response
                .ok(ProductDTO.from(productBean.findOrFail(product.getId()), true))
                .status(Response.Status.CREATED).build();
    }
    */
}
