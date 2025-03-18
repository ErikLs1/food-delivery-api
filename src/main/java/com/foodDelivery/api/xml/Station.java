package com.foodDelivery.api.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

/**
 * Represents a station in the XML response.
 *
 * <p>
 *     Example:
 *     <pre>
 *         <station>
 *             <name>Tallinn-Harku</name>
 *             <wmocode>26038</wmocode>
 *             <phenomenon>Clear</phenomenon>
 *             <airtemperature>2.9</airtemperature>
 *             <windspeed>3.4</windspeed>
 *         </station>
 *     </pre>
 * </p>
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Station {

    /**
     * The name of the station.
     */
    private String name;

    /**
     * The WMO code that represents the station.
     */
    @JacksonXmlProperty(localName = "wmocode")
    private String wmoCode;

    /**
     * The air temperature recorded at the station.
     */
    @JacksonXmlProperty(localName = "airtemperature")
    private Double airTemperature;

    /**
     * The wind speed recorder at the station.
     */
    @JacksonXmlProperty(localName = "windspeed")
    private Double windSpeed;

    /**
     * Description of the weather phenomenon (e.g, Light Rain, Thunder).
     */
    @JacksonXmlProperty(localName = "phenomenon")
    private String phenomenon;
}
