package pt.ipleiria.estg.dei.ei.pref;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pt.ipleiria.estg.dei.ei.pref.ApiIntegrationTests.baseUrl;
import static pt.ipleiria.estg.dei.ei.pref.ApiIntegrationTests.printJsonResponse;

public class ObservationApiTest {

    private final OkHttpClient client = new OkHttpClient();

    @Test
    public void testGetObservationsFromSimplePackageType() throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + "observations/package/?id=1")
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
    public void testCreateObservation() throws IOException {
        // Create JSON request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("phenomenonType", "TEMPERATURE");
        requestBody.put("observerId", 1);
        requestBody.put("date", "2023-01-01T00:00:00.000Z");
        requestBody.put("details", "{\"key1\": \"value1\", \"key2\": \"value2\"}");
        requestBody.put("observablePackageId", 20);
        requestBody.put("value", 20);
        String requestBodyJson = new ObjectMapper().writeValueAsString(requestBody);

        // Create HTTP request
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestBodyJson);
        Request request = new Request.Builder()
                .url(baseUrl + "observations")
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build();

        // Send HTTP request and process response
        try (Response response = client.newCall(request).execute()) {
            System.out.println("Request URL: " + request.url());
            System.out.println("Response Body:");
            if (response.body() != null) {
                printJsonResponse(response.body().string());
            }
            assertEquals(201, response.code());
        }
    }

    @Test
    public void testGetObservations() throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + "observations")
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
    public void testGetPackagesHasObservations() throws IOException {

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("ids", new int[]{1, 2});

        String requestBodyJson = new ObjectMapper().writeValueAsString(requestBody);

        // Create HTTP request
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestBodyJson);
        Request request = new Request.Builder()
                .url(baseUrl + "observations/packages-has-observations/")
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
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


}
