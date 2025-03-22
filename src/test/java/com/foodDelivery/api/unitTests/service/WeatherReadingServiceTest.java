package com.foodDelivery.api.unitTests.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.foodDelivery.api.xml.Observations;
import com.foodDelivery.api.xml.Station;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * Unit tests for parsing the XML weather data.
 */
@ExtendWith(MockitoExtension.class)
public class WeatherReadingServiceTest {

    /**
     * Tests XML weather data parsing for one station.
     *
     * @throws Exception if XML parsing fails.
     */
    @Test
    void testWeatherDataParsingForOneStation() throws Exception {
        String xml = """
                <observations timestamp="1742472839">
                    <station>
                        <name>Tallinn-Harku</name>
                        <wmocode>26038</wmocode>
                        <longitude>24.602891666624284</longitude>
                        <latitude>59.398122222355134</latitude>
                        <phenomenon>Cloudy with clear spells</phenomenon>
                        <visibility>35.0</visibility>
                        <precipitations>0</precipitations>
                        <airpressure>1018.1</airpressure>
                        <relativehumidity>79</relativehumidity>
                        <airtemperature>3.6</airtemperature>
                        <winddirection>210</winddirection>
                        <windspeed>4.8</windspeed>
                        <windspeedmax>8.2</windspeedmax>
                        <waterlevel/>
                        <waterlevel_eh2000/>
                        <watertemperature/>
                        <uvindex>1.7</uvindex>
                        <sunshineduration>57</sunshineduration>
                        <globalradiation>277</globalradiation>
                    </station>
                </observations>
                """;
        XmlMapper xmlMapper = new XmlMapper();
        Observations observations = xmlMapper.readValue(xml, Observations.class);

        assertNotNull(observations);
        assertEquals("1742472839", observations.getTimestamp());

        assertNotNull(observations.getStations());
        assertEquals(1, observations.getStations().size());

        Station station1 = observations.getStations().getFirst();
        assertEquals("Tallinn-Harku", station1.getName());
        assertEquals("26038", station1.getWmoCode());
        assertEquals(3.6, station1.getAirTemperature());
        assertEquals(4.8, station1.getWindSpeed());
        assertEquals("Cloudy with clear spells", station1.getPhenomenon());
    }

    /**
     * Tests XML weather data parsing for multiple stations.
     *
     * @throws Exception if XML parsing fails.
     */
    @Test
    void testWeatherDataParsingForMultipleStations() throws Exception {
        String xml = """
                <observations timestamp="1742472839">
                    <station>
                        <name>Tallinn-Harku</name>
                        <wmocode>26038</wmocode>
                        <longitude>24.602891666624284</longitude>
                        <latitude>59.398122222355134</latitude>
                        <phenomenon>Cloudy with clear spells</phenomenon>
                        <visibility>35.0</visibility>
                        <precipitations>0</precipitations>
                        <airpressure>1018.1</airpressure>
                        <relativehumidity>79</relativehumidity>
                        <airtemperature>3.6</airtemperature>
                        <winddirection>210</winddirection>
                        <windspeed>4.8</windspeed>
                        <windspeedmax>8.2</windspeedmax>
                        <waterlevel/>
                        <waterlevel_eh2000/>
                        <watertemperature/>
                        <uvindex>1.7</uvindex>
                        <sunshineduration>57</sunshineduration>
                        <globalradiation>277</globalradiation>
                    </station>
                    <station>
                        <name>Tartu-Tõravere</name>
                        <wmocode>26242</wmocode>
                        <longitude>26.46130555576748</longitude>
                        <latitude>58.264072222179834</latitude>
                        <phenomenon>Variable clouds</phenomenon>
                        <visibility>35.0</visibility>
                        <precipitations>0</precipitations>
                        <airpressure>1020.4</airpressure>
                        <relativehumidity>73</relativehumidity>
                        <airtemperature>4.5</airtemperature>
                        <winddirection>242</winddirection>
                        <windspeed>4.3</windspeed>
                        <windspeedmax>7</windspeedmax>
                        <waterlevel/>
                        <waterlevel_eh2000/>
                        <watertemperature/>
                        <uvindex/>
                        <sunshineduration>46</sunshineduration>
                        <globalradiation>394</globalradiation>
                    </station>
                </observations>
                """;

        XmlMapper xmlMapper = new XmlMapper();
        Observations observations = xmlMapper.readValue(xml, Observations.class);

        assertNotNull(observations);
        assertEquals("1742472839", observations.getTimestamp());

        assertNotNull(observations.getStations());
        assertEquals(2, observations.getStations().size());

        Station station1 = observations.getStations().getFirst();
        assertEquals("Tallinn-Harku", station1.getName());
        assertEquals("26038", station1.getWmoCode());
        assertEquals(3.6, station1.getAirTemperature());
        assertEquals(4.8, station1.getWindSpeed());
        assertEquals("Cloudy with clear spells", station1.getPhenomenon());

        Station station2 = observations.getStations().get(1);
        assertEquals("Tartu-Tõravere", station2.getName());
        assertEquals("26242", station2.getWmoCode());
        assertEquals(4.5, station2.getAirTemperature());
        assertEquals(4.3, station2.getWindSpeed());
        assertEquals("Variable clouds", station2.getPhenomenon());
    }
}
