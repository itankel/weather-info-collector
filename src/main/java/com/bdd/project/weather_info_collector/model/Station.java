package com.bdd.project.weather_info_collector.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Station {
    private int stationId;
    private String name;
    private String shortName;
    private String stationTag;
    private Location location;
    private int timebase;
    private boolean active;
    private String owner;
    private int regionId;
    private List<Monitor> monitors;
    @JsonProperty(value="StationTarget") // needed since the json name of the attribute is uppercase
    private String stationTarget;

}
