package ru.tech.restclient.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SendMeasurmentException extends RuntimeException{
    private HttpStatus httpStatus;

    public SendMeasurmentException(HttpStatus httpStatus) {
        super("Неверные параметры отправки измерения" + httpStatus);
        this.httpStatus = httpStatus;

    }
    public HttpStatus getStatusCode() {
        return httpStatus;
    }

}
