package main.java.ru.tech.sensorOverDAO.dao;

import main.java.ru.tech.sensorOverDAO.models.Measurement;

import java.util.List;

/**
 * Класс получения основных данных с таблицы Measurement
 */
public interface MeasurementDAO {

    Measurement save(Measurement measurement);
    List<Measurement> getAll();
    Long getRainyDaysCount();
}
