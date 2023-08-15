package main.java.ru.tech.sensorOverDAO.exception;

import lombok.Data;

@Data
public class MeasurementErrorResponse {
    private String msg;
    private long timestamp;

    public MeasurementErrorResponse(String msg, long timestamp) {
        this.msg = msg;
        this.timestamp = timestamp;
    }
}
