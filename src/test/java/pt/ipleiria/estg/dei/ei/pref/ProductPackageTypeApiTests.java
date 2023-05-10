package pt.ipleiria.estg.dei.ei.pref;

import okhttp3.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pt.ipleiria.estg.dei.ei.pref.ApiIntegrationTests.baseUrl;
import static pt.ipleiria.estg.dei.ei.pref.ApiIntegrationTests.printJsonResponse;

public class ProductPackageTypeApiTests {

    private final OkHttpClient client = new OkHttpClient();

    @Test
    public void testListProductPackages() throws IOException {

        Request request = new Request.Builder()
                .url(baseUrl + "product-packages")
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
