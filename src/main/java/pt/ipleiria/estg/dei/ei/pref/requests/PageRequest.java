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

    @QueryParam("carrier")
    private String carrier;

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

    //for get all orders

    public String getCarrier() {
    	return carrier;
    }
    public void setCarrier(String carrier) {
    	this.carrier = carrier;
    }
    @Override
    public String toString() {
        return "PageRequest { offset: " + offset + ", limit:" + limit + ", carrier:" + carrier + " }";
    }
}
