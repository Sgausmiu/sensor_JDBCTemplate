package main.java.ru.tech.restclient.dto;


import main.java.ru.tech.sensorOverDAO.dto.SensorDTO;

import java.time.LocalDateTime;

/**
 * Получение данных с сервера, со включением в список
 */
public class MeasurementDTOList {

    private Double value;
    private Boolean raining;
    private SensorDTO sensor;

    private LocalDateTime localResolutionTime;

    public Double getValue() {
        return value;
    }
    public Boolean getRaining() {
        return raining;
    }
    public SensorDTO getSensor() {
        return sensor;
    }

    public LocalDateTime getLocalResolutionTime() {
        return localResolutionTime;
    }
}
