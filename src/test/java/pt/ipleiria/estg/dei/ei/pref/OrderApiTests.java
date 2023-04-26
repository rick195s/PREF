package pt.ipleiria.estg.dei.ei.pref;

import okhttp3.*;
import org.junit.jupiter.api.Test;

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
    public void testDispatchOrderLine() throws IOException {
        String jsonBody = "{\"orderPackageId\": 21}";

        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(baseUrl + "orders/300")
                .patch(body)
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
        String jsonBody = "{\n" +
                "  \"productIds\":[\n" +
                "      1,\n" +
                "      2,\n" +
                "      3\n" +
                "    ],\n" +
                "    \"source\": \"Lisbon\",\n" +
                "    \"destination\": \"Porto\",\n" +
                "    \"carrier\": \"DHL\",\n" +
                "    \"shippingMethods\": [\"ground\"]\n" +
                "    \n" +
                "}";

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
