package com.foodDelivery.api.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

/**
 * Represents the root element of the XML response receive from weather data API.
 * At the root element it contains a timestamp attribute and a list of stations.
 *
 * <p>
 *     Example:
 *     <pre>
 *         <observations timestamp="1742317497">
 *              <station> </station>
 *              <station> </station>
 *              <station> </station>
 *         </observations>
 *     </pre>
 * </p>
 *
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Observations {

    /**
     * The timestamp attribute in Epoch time format
     */
    @JacksonXmlProperty(isAttribute = true)
    private String timestamp;

    /**
     * The list of station elements found inside observations.
     */
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "station")
    private List<Station> stations;
}
