package ru.tech.restclient.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;
import ru.tech.restclient.dto.MeasurementDTOList;

import java.util.ArrayList;
import java.util.List;

public class GetMeasurement {

    public  String getDataFromServer() throws JsonProcessingException {
        List<MeasurementDTOList> list = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        MeasurementDTOList[] getMeasurementFromServer = restTemplate.getForObject("http://localhost:8333/measurements", MeasurementDTOList[].class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        System.out.println((objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(getMeasurementFromServer)));
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(getMeasurementFromServer);
    }
}
