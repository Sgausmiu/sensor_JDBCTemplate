package main.java.ru.tech.sensorOverDAO.utils;

import main.java.ru.tech.sensorOverDAO.dao.SensorDAOImpl;
import main.java.ru.tech.sensorOverDAO.models.Measurement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

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


