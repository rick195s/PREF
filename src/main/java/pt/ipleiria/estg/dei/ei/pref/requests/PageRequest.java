package pt.ipleiria.estg.dei.ei.pref.requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


public class PageRequest {
    @QueryParam("offset")
    @DefaultValue("0")
    @PositiveOrZero
    private int offset;

    @QueryParam("limit")
    @DefaultValue("10")
    @PositiveOrZero
    @Max(50)
    private int limit;

    @QueryParam("sort")
    private String sort;

    @QueryParam("search")
    private String search;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSortField() {
        if (sort != null && !sort.isEmpty()) {
            try {
                // Parse the sort parameter as JSON
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode sortJson = objectMapper.readTree(sort);

                // Extract the sort field
                return sortJson.get("key").asText();
            } catch (IOException e) {
                // Handle JSON parsing exception
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getSortDirection() {
        if (sort != null && !sort.isEmpty()) {
            try {
                // Parse the sort parameter as JSON
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode sortJson = objectMapper.readTree(sort);

                // Extract the sort direction
                return sortJson.get("order").asText();
            } catch (IOException e) {
                // Handle JSON parsing exception
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getSearchDate() {
        System.out.println(search);
    	if (search != null && !search.isEmpty() && !search.equals("{}")) {
            try {
                // Parse the sort parameter as JSON
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode searchJson = objectMapper.readTree(search);

                // Extract the sort direction
                return searchJson.get("date").asText();
            } catch (IOException e) {
                // Handle JSON parsing exception
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getSearchObservablePackage() {
    	if (search != null && !search.isEmpty() && !search.equals("{}")) {
            try {
                // Parse the sort parameter as JSON
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode searchJson = objectMapper.readTree(search);

                // Extract the sort direction
                return searchJson.get("observablePackage").asText();
            } catch (IOException e) {
                // Handle JSON parsing exception
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getSearchObserver() {
    	if (search != null && !search.isEmpty() && !search.equals("{}")) {
            try {
                // Parse the sort parameter as JSON
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode searchJson = objectMapper.readTree(search);

                // Extract the sort direction
                return searchJson.get("observer").asText();
            } catch (IOException e) {
                // Handle JSON parsing exception
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getSearchPhenomenon() {
    	if (search != null && !search.isEmpty() && !search.equals("{}")) {
            try {
                // Parse the sort parameter as JSON
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode searchJson = objectMapper.readTree(search);

                // Extract the sort direction
                return searchJson.get("phenomenonType").asText();
            } catch (IOException e) {
                // Handle JSON parsing exception
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getSearchValue() {
    	if (search != null && !search.isEmpty() && !search.equals("{}")) {
            try {
                // Parse the sort parameter as JSON
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode searchJson = objectMapper.readTree(search);

                // Extract the sort direction
                return searchJson.get("value").asText();
            } catch (IOException e) {
                // Handle JSON parsing exception
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getSearch() {
    	return search;
    }

    public void setSearch(String search) {
    	this.search = search;
    }

    @Override
    public String toString() {
        return "PageRequest { offset: " + offset + ", limit:" + limit + ", sortBy:" + sort + ", search:" + search + " }";
    }
}
