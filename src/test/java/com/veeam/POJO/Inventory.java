package com.veeam.POJO;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Inventory {

    private String sold;
    private String string;
    private int unavailable;
    private String pending;
    private String available;
    @JsonProperty("Not Available")
    private String notAvailable;
}
