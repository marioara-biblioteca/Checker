package checker.controllers.advice_controllers;

import checker.exceptions.ResultNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ResultNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(ResultNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String resultNotFoundHandler(ResultNotFoundException ex){
        return ex.getMessage();
    }
}
