package pt.ipleiria.estg.dei.ei.pref;

import okhttp3.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pt.ipleiria.estg.dei.ei.pref.ApiIntegrationTests.baseUrl;
import static pt.ipleiria.estg.dei.ei.pref.ApiIntegrationTests.printJsonResponse;

public class ProductApiTests {

    private final OkHttpClient client = new OkHttpClient();

    @Test
    public void testListProducts() throws IOException {
        String queryParams = "?offset=0&limit=10";

        Request request = new Request.Builder()
                .url(baseUrl + "products" + queryParams)
                .addHeader("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("Request URL: " + request.url());
            System.out.println("Response Body:");
            if (response.body() != null) {
                printJsonResponse(response.body().string());
            }
            assertEquals(200, response.code());
        }
    }

    @Test
    public void testCreateProduct() throws IOException {
        String jsonBody = "{\"name\":\"Example\",\"category\":\"FOOD\",\"price\":12.3,\"weight\":0.5,\"validityRange\":4,\"length\":2,\"width\":2,\"height\":1,\"productPackageTypes\":[{\"id\":1},{\"id\":4}]}";

        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(baseUrl + "products" )
                .post(body)
                .addHeader("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("Request URL: " + request.url());
            System.out.println("Response Body:");
            if (response.body() != null) {
                printJsonResponse(response.body().string());
            }
            assertEquals(201, response.code());
        }
    }

}
