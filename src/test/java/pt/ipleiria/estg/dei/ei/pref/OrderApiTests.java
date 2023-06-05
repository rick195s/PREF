package pt.ipleiria.estg.dei.ei.pref;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import pt.ipleiria.estg.dei.ei.pref.dtos.packages.OrderPackageTypeDTO;
import pt.ipleiria.estg.dei.ei.pref.dtos.requests.ProductQuantityDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pt.ipleiria.estg.dei.ei.pref.ApiIntegrationTests.baseUrl;
import static pt.ipleiria.estg.dei.ei.pref.ApiIntegrationTests.printJsonResponse;

public class OrderApiTests {

    private final OkHttpClient client = new OkHttpClient();

    @Test
    public void testGetOrderDetails() throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + "orders/U0000009d000000b5000000d100000079000000a000000099000000a20000008500000093000000ac000000ab")
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
        String jsonBody = "{\"id\": \"085x020x150\"}";

        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(baseUrl + "orders/U0000009d000000b5000000d100000079000000a000000099000000a20000008500000093000000ac000000ab")
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
    public void packOrder() throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + "orders/U0000009d000000b5000000d100000079000000a000000099000000a20000008500000093000000ac000000ab/pack")
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
    public void testCreateOrder() throws IOException {
        Map<String, Object> requestBody = new HashMap<>();

        // Set values in the requestBody
        requestBody.put("carrier", "PROZIS NOW - LISBOA");
        requestBody.put("shippingMethod", "Terrestre");
        requestBody.put("channel", "PRZ");
        requestBody.put("store", "PT");
        requestBody.put("distributionCenter", "CD01");
        requestBody.put("cpDestiny", "2440-010");
        requestBody.put("volumeNumber", 1);
        // productsQuantities
        List<ProductQuantityDTO> productsQuantities = new ArrayList<>();
        productsQuantities.add(new ProductQuantityDTO("NUT00/1482660002", 2));
        productsQuantities.add(new ProductQuantityDTO("NUT00/1465070000", 4));

        // Put the list of ProductQuantityDTO objects into the map
        requestBody.put("productsQuantities", productsQuantities);

        // Convert the map to JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = "";
        try {
            jsonPayload = objectMapper.writeValueAsString(requestBody);
            System.out.println(jsonPayload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // Create HTTP request
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonPayload);

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
