package ru.tech.sensorOverDAO.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "Sensor")
public class Sensor implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Sensor name is required")
    @Size(min = 3, max = 30, message = "Sensor name must be between 3 and 30 characters")
    private String name;

    public Sensor(String name) {
    }
    public Sensor() {

    }
}
