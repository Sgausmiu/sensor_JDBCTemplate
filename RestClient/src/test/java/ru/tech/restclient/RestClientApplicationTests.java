package test.java.ru.tech.restclient;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestClientApplicationTests {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate = new TestRestTemplate(TestRestTemplate.HttpClientOption.ENABLE_COOKIES);
    @Test
    public void testSaveSensor() {
        String url = "http://localhost:" + port + "/sensors/registration";
        String deviceName = "Sensor_63";
        String requestBody = "{\"name\":\"" + deviceName + "\"}";
        String expectedResponse = "Sensor saved successfully.";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(requestBody, headers);

        String response = restTemplate.postForObject(url, request, String.class);

        assertEquals(expectedResponse, response);
    }
    @Test
    public void testPostMeasurement() {
        String url = "http://localhost:" + port + "/measurements/add";
        String deviceName = "Sensor_63";
        Double temperature = 25.0;
        Boolean rain = true;
        String requestBody = "{\"value\":" + temperature + ",\"raining\":" + rain + ",\"sensor\":{\"name\":\"" + deviceName + "\",\"localResolutionTime\":null}}";
        String expectedResponse = "Measurement added successfully.";

        String response = restTemplate.postForObject(url, requestBody, String.class);

        assertEquals(expectedResponse, response);
    }
    @Test
    public void testGetMeasurement() {
        String url = "http://localhost:" + port + "/measurements";
        String expectedResponse = "[{\"value\":25.0,\"raining\":true,\"sensor\":{\"name\":\"Sensor_63\"},\"localResolutionTime\":null}]";

        String response = restTemplate.getForObject(url, String.class);

        assertEquals(expectedResponse, response);
    }

}
