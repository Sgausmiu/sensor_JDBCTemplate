package ru.tech.sensorOverDAO.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SensorDTO {

    @Column(name = "name")
    @NotBlank(message = "Sensor name is required")
    @Size(min = 3, max = 30, message = "Sensor name must be between 3 and 30 characters")
    private String name;
}
