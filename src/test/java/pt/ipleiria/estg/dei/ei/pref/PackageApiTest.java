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

public class PackageApiTest {

    private final OkHttpClient client = new OkHttpClient();

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void testGetPackages() throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + "packages")
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
    public void testGetPackageDetails() throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + "packages/120x070x210")
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
