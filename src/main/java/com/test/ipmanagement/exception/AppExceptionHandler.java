package com.test.ipmanagement.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.jws.WebResult;
import java.util.Date;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException(Exception exception, WebResult webResult) {
        ErrorMessage errorMessage = new ErrorMessage();

        if (!(exception.getLocalizedMessage() == null)) {
            errorMessage = new ErrorMessage(exception.getLocalizedMessage(), new Date());
        } else {
            errorMessage = new ErrorMessage(exception.toString(), new Date());
        }
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> handleNullPointer(NullPointerException exception, WebResult webResult) {
        ErrorMessage errorMessage = new ErrorMessage();

        if (!(exception.getLocalizedMessage() == null)) {
            errorMessage = new ErrorMessage(exception.getLocalizedMessage(), new Date());
        } else {
            errorMessage = new ErrorMessage(exception.toString(), new Date());
        }

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }


}
