package checker.controllers.advice_controllers;

import checker.exceptions.TestAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TestAlreadyExistsAdvice {
    @ResponseBody
    @ExceptionHandler(TestAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    String testAlreadyExistsHandler(TestAlreadyExistsException ex){
        return ex.getMessage();
    }
}
