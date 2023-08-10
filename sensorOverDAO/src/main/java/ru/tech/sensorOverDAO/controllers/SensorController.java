package ru.tech.sensorOverDAO.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.tech.sensorOverDAO.dao.SensorDAOImpl;
import ru.tech.sensorOverDAO.dto.SensorDTO;
import ru.tech.sensorOverDAO.exception.MeasurementErrorResponse;
import ru.tech.sensorOverDAO.exception.MeasurementNotAddedException;
import ru.tech.sensorOverDAO.exception.SensorNotFoundException;
import ru.tech.sensorOverDAO.models.Sensor;
import ru.tech.sensorOverDAO.utils.SensorValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorDAOImpl sensorDAO;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorController(SensorDAOImpl sensorDAO, ModelMapper modelMapper, SensorValidator sensorValidator) {
        this.sensorDAO = sensorDAO;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> addSensor(@RequestBody @Valid SensorDTO sensorDTO,
                                                BindingResult bindingResult) {

        Sensor sensorAdd = convertToAdd(sensorDTO);
        sensorValidator.validate(sensorAdd, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new SensorNotFoundException(errorMsg.toString());
        }

        sensorDAO.save(sensorAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<SensorDTO> getAllSensors() {
        return sensorDAO.getAll().stream().map(this::convertToSensorDTO)
                .collect(Collectors.toList());
    }

    private Sensor convertToAdd(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    public SensorDTO convertToSensorDTO(Sensor sensor) {
        return modelMapper.map(sensor, SensorDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotAddedException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
