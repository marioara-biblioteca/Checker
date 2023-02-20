package checker.controllers.advice_controllers;

import checker.exceptions.TaskAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TaskAlreadyExistsAdvice {
    @ResponseBody
    @ExceptionHandler(TaskAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    String taskAlreadyExistsHandler(TaskAlreadyExistsException ex){
        return ex.getMessage();
    }
}
