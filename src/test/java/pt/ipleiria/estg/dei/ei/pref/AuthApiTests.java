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

public class AuthApiTests {

    private final OkHttpClient client = new OkHttpClient();

    @Test
    public void testLogin() throws IOException {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", "client@gmail.com");
        requestBody.put("password", "123");
        String requestBodyJson = new ObjectMapper().writeValueAsString(requestBody);

        // Create HTTP request
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), requestBodyJson);

        Request request = new Request.Builder()
                .url(baseUrl + "auth/login")
                .post(body)
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



}
