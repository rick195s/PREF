package pt.ipleiria.estg.dei.ei.pref;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiIntegrationTests {

    public static final String baseUrl = "http://localhost:8080/pref/api/";


    public static void printJsonResponse(String string) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Object json = objectMapper.readTree(string);

        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json));
    }
}
