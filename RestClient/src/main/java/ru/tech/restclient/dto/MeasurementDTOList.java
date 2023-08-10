package ru.tech.restclient.dto;


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
