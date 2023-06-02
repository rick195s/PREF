package pt.ipleiria.estg.dei.ei.pref;

import okhttp3.*;
import org.junit.jupiter.api.Test;
import pt.ipleiria.estg.dei.ei.pref.dtos.packages.OrderPackageTypeDTO;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pt.ipleiria.estg.dei.ei.pref.ApiIntegrationTests.baseUrl;
import static pt.ipleiria.estg.dei.ei.pref.ApiIntegrationTests.printJsonResponse;

public class OrderApiTests {

    private final OkHttpClient client = new OkHttpClient();

    @Test
    public void testGetOrderDetails() throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + "orders/1")
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
    public void testAssociatePackageWithOrder() throws IOException {
        String jsonBody = "{\"id\": 19}";

        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(baseUrl + "orders/1")
                .patch(body)
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
    public void packOrder() throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + "orders/1/pack")
                .patch(RequestBody.create("", MediaType.parse("application/json")))
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
    public void testListOrders() throws IOException {
        String queryParams = "?offset=0&limit=10";

        Request request = new Request.Builder()
                .url(baseUrl + "orders" + queryParams)
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
    public void testCreateOrder() throws IOException {
        String jsonBody = "{\"productsQuantities\":[{\"productId\":2,\"quantity\":2},{\"productId\":1,\"quantity\":4},{\"productId\":5,\"quantity\":2}],\"source\":\"Lisbon\",\"destination\":\"Porto\",\"carrier\":\"DHL\",\"shippingMethod\":\"ground\"}\n";

        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(baseUrl + "orders" )
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
