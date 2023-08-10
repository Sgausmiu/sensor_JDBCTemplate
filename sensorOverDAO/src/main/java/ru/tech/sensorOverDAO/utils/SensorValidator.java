package ru.tech.sensorOverDAO.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.tech.sensorOverDAO.dao.SensorDAOImpl;
import ru.tech.sensorOverDAO.models.Sensor;

@Component
public class SensorValidator implements Validator {

    private SensorDAOImpl sensorDAO;

    @Autowired
    public SensorValidator(SensorDAOImpl sensorDAO) {
        this.sensorDAO = sensorDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {

        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;
        if(sensorDAO.searchByName(sensor.getName()).isPresent())
            errors.rejectValue("name", "Sensor also is registered.");

    }
}
