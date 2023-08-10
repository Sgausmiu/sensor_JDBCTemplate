package ru.tech.sensorOverDAO.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.tech.sensorOverDAO.dao.SensorDAOImpl;
import ru.tech.sensorOverDAO.models.Measurement;

@Component
public class MeasurementValidator implements Validator {

    private final SensorDAOImpl sensorDAO;

    @Autowired
    public MeasurementValidator(SensorDAOImpl sensorDAO) {
        this.sensorDAO = sensorDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;

        if(sensorDAO.searchByName(measurement.getSensor().getName()).isEmpty())
            errors.rejectValue("sensor", "No register sensor.");
    }
}


