package main.java.ru.tech.restclient.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SendMeasurementException extends RuntimeException{
    private HttpStatus httpStatus;

    public SendMeasurementException(HttpStatus httpStatus) {
        super("Неверные параметры отправки измерения" + httpStatus);
        this.httpStatus = httpStatus;

    }
    public HttpStatus getStatusCode() {
        return httpStatus;
    }

}
