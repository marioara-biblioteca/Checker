package checker.controllers.advice_controllers;

import checker.exceptions.TestNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TestNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(TestNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String testNotFoundHandler(TestNotFoundException ex){
        return ex.getMessage();
    }
}
