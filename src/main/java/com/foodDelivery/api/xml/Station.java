package com.foodDelivery.api.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Station {

    private String name;

    @JacksonXmlProperty(localName = "wmocode")
    private String wmoCode;

    @JacksonXmlProperty(localName = "airtemperature")
    private Double airTemperature;

    @JacksonXmlProperty(localName = "windspeed")
    private Double windSpeed;

    @JacksonXmlProperty(localName = "phenomenon")
    private String phenomenon;
}
