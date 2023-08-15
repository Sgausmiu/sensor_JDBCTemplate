package main.java.ru.tech.sensorOverDAO.controllers;

import jakarta.validation.Valid;
import main.java.ru.tech.sensorOverDAO.dao.MeasurementDAOImpl;
import main.java.ru.tech.sensorOverDAO.dto.MeasurementDTO;
import main.java.ru.tech.sensorOverDAO.exception.MeasurementNotAddedException;
import main.java.ru.tech.sensorOverDAO.models.Measurement;
import main.java.ru.tech.sensorOverDAO.models.Sensor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementDAOImpl measurementDAOImpl;

    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementDAOImpl measurementDAOImpl, ModelMapper modelMapper) {
        this.measurementDAOImpl = measurementDAOImpl;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult){
        // Validation code
        if (bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors){
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new MeasurementNotAddedException(errorMsg.toString());

        }

        measurementDAOImpl.save(convertToAddMeasurement(measurementDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //по совету коллег - предложили передавать не список, а один объект json
    @GetMapping
    public List<MeasurementDTO> getAllMeasurement(){

        return measurementDAOImpl.getAll().stream().map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/rainyDaysCount")
    public Long getRainyDaysCount(){

        return  measurementDAOImpl.getRainyDaysCount();
    }

    //Методы конвертации Measurement <-> MeasurementDTO
    public Measurement convertToAddMeasurement(MeasurementDTO measurementDTO){

        Measurement measurement = new Measurement();
        measurement.setValue(measurementDTO.getValue());
        measurement.setRaining(measurementDTO.isRaining());
        measurement.setLocalResolutionTime(measurementDTO.setLocalResolutionTime(LocalDateTime.now()));//изменил логику получения мгновенного timestamp, ранее время отличалось от времени в БД

        Sensor sensor = new Sensor();
        sensor.setName(measurementDTO.getSensor().getName());
        measurement.setSensor(sensor);

        return measurement;
        //return modelMapper.map(measurementDTO, Measurement.class); с ModelMapper не возвращает значения, причина- несоответствие timestamp между локальным и в БД.
        }

    public MeasurementDTO convertToMeasurementDTO(Measurement measurement){

        MeasurementDTO measurementDTO = new MeasurementDTO();
        measurementDTO.setValue(measurement.getValue());
        measurementDTO.setRaining(measurement.isRaining());
        measurementDTO.setSensor(measurement.getSensor());
        measurementDTO.setLocalResolutionTime(measurement.getLocalResolutionTime());

        return measurementDTO;
       // return modelMapper.map(measurement, MeasurementDTO.class); с ModelMapper не возвращает значения, причину не искал.
    }
}
