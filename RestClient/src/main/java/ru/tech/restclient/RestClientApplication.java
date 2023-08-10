package ru.tech.restclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.tech.restclient.rest.GetMeasurement;
import ru.tech.sensorOverDAO.dto.MeasurementDTO;
import ru.tech.sensorOverDAO.exception.MeasurementNotAddedException;

import java.util.*;

/**
 * Добавление и последующее получение различных данных прибора
 */
@SpringBootApplication
public class RestClientApplication {


    public static void main(String[] args) throws JsonProcessingException {

        final String deviceName = "Sensor_62";
        saveSensor(deviceName);

        Random random = new Random();
        MeasurementDTO measurementDTO = new MeasurementDTO();
        Double temperature =  measurementDTO.setValue(getRandomTemperature());
        Boolean rain = measurementDTO.setRaining(getRandomBoolean());


        for (int i = 0; i<2; i++){
            postMeasurement(random.nextDouble()*temperature, random.nextBoolean(), deviceName);
            System.out.println("Идет инкрементация = " + i);
        }
        getMeasurementInConsole();

      SpringApplication.run(RestClientApplication.class, args);
    }

    private static void saveSensor(String deviceName) {
        String url  = "http://localhost:8333/sensors/registration";
        Map<String, Object> data = new HashMap<>();
        data.put("name", deviceName);

        postDataFromServer(url, data);
    }

    public static void postMeasurement(Double temperature, Boolean rain, String deviceName){
        String url  = "http://localhost:8333/measurements/add";
        Map<String, Object> data = new HashMap<>();
        data.put("value", temperature);
        data.put("raining", rain);
        data.put("sensor", Map.of("name", deviceName));

        postDataFromServer(url, data);
    }


    private static void postDataFromServer(String url, Map<String, Object> data) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> request = new HttpEntity<>(data, headers);
        try{
        restTemplate.postForObject(url, request, String.class);
            System.out.println("Данные отправлены.");}
        catch (MeasurementNotAddedException e){
            System.out.println("Сбой при отправке данных.");
            e.getMessage();
        }
    }
    private static double getRandomTemperature() {
        Random random = new Random();
        return random.nextDouble() * 50.0;
    }
    private static boolean getRandomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }

    private static String getMeasurementInConsole() throws JsonProcessingException {
        GetMeasurement getMeasurement = new GetMeasurement();
        return getMeasurement.getDataFromServer();
    }
}
