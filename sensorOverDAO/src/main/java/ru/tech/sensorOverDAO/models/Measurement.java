package main.java.ru.tech.sensorOverDAO.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "Measurement")
public class Measurement {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Size(min = -100, max = 100, message = "Диапазон значенией от -100 до +100 градусов")
    @Column(name = "value")
    private Double value;

    @NotNull
    @Column(name = "raining")
    private Boolean raining;

    @NotNull
    @Column(name = "local_resolution_time")
    private LocalDateTime localResolutionTime;

    @NotNull
    @JoinColumn(name = "sensor", referencedColumnName = "name")
    @ManyToOne
    private Sensor sensor;

    public Measurement(Integer id, Double value, Boolean raining, LocalDateTime localResolutionTime, Sensor sensor) {
        this.id = id;
        this.value = Double.parseDouble(String.valueOf(value));
        this.raining = Boolean.parseBoolean(String.valueOf(raining));
        this.localResolutionTime = localResolutionTime;
        this.sensor = sensor;
    }
    public Measurement() {

    }

    // Jackson смотрит на название геттера, отсекает is и оставляет название поля
    public Boolean isRaining() {
        return raining;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = Double.parseDouble(String.valueOf(value));
    }

    public void setRaining(Boolean raining) {
        this.raining = Boolean.parseBoolean(String.valueOf(raining));
    }

    public LocalDateTime getLocalResolutionTime() {
        return localResolutionTime;
    }

    public void setLocalResolutionTime(LocalDateTime localResolutionTime) {
        this.localResolutionTime = localResolutionTime;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
