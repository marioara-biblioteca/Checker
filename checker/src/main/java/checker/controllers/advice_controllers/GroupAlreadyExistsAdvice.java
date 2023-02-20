package checker.controllers.advice_controllers;

import checker.exceptions.GroupAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GroupAlreadyExistsAdvice {
    @ResponseBody
    @ExceptionHandler(GroupAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    String groupAlreadyExistsHandler(GroupAlreadyExistsException ex){
        return ex.getMessage();
    }
}
