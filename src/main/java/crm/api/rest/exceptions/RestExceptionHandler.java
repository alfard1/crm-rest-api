package crm.api.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    // exception handler for RestExceptionNotFound

    @ExceptionHandler
    public ResponseEntity<RestExceptionEntity> handleException(RestExceptionNotFound exc) {

        // create RestExceptionEntity Entity
        RestExceptionEntity error = new RestExceptionEntity(
                HttpStatus.NOT_FOUND.value(),
                exc.getMessage(),
                System.currentTimeMillis());

        // return ResponseEntity
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // another exception handler to catch any exception (catch all)
    @ExceptionHandler
    public ResponseEntity<RestExceptionEntity> handleException(Exception exc) {

        // create RestExceptionEntity Entity
        RestExceptionEntity error = new RestExceptionEntity(
                HttpStatus.BAD_REQUEST.value(),
                exc.getMessage(),
                System.currentTimeMillis());

        // return ResponseEntity
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
