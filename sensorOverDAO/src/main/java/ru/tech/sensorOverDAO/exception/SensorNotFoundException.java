package ru.tech.sensorOverDAO.exception;

public class SensorNotFoundException extends RuntimeException{
    public SensorNotFoundException(String msg){
        super(msg);
    }
}
