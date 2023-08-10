package ru.tech.sensorOverDAO.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.tech.sensorOverDAO.models.Sensor;

import java.time.LocalDateTime;

public class MeasurementDTO {
    @NotNull
    @Size(min = -100, max = 100, message = "Диапазон значенией от -100 до +100 градусов")
    private Double value;

    @NotNull
    private Boolean isRaining;

    @NotNull
    private Sensor sensor;
    @NotNull
    private LocalDateTime localResolutionTime;

    public Double getValue() {
        return value; }
    public Double setValue(Double value) {
        this.value = value;
        return value;
    }

    public Boolean isRaining() {
        return isRaining;
    }

    public Boolean setRaining(Boolean raining) {
        this.isRaining = raining;
        return raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public LocalDateTime getLocalResolutionTime() {
        return localResolutionTime;
    }

    public LocalDateTime setLocalResolutionTime(LocalDateTime localResolutionTime) {
        this.localResolutionTime = localResolutionTime;
        return localResolutionTime;
    }
}

