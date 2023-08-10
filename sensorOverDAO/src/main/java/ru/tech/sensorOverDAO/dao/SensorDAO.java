package ru.tech.sensorOverDAO.dao;

import ru.tech.sensorOverDAO.models.Sensor;

import java.util.List;
import java.util.Optional;

/**
 * Класс получения основных данных с таблицы Sensor
 */
public interface SensorDAO {
    Sensor save (Sensor sensor);
    List<Sensor> getAll();
    Optional<Sensor> searchByName(String name);

}
