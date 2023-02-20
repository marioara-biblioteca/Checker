package checker.controllers.advice_controllers;

import checker.exceptions.SubjectAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SubjectAlreadyExistsAdvice {
    @ResponseBody
    @ExceptionHandler(SubjectAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    String subjectAlreadyExistsHandler(SubjectAlreadyExistsException ex){
        return ex.getMessage();
    }
}
