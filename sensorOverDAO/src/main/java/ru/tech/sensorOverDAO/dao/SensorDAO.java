package main.java.ru.tech.sensorOverDAO.dao;

import main.java.ru.tech.sensorOverDAO.models.Sensor;

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
