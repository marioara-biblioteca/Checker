package checker.controllers.advice_controllers;

import checker.exceptions.RoleAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RoleAlreadyExistsAdvice {
    @ResponseBody
    @ExceptionHandler(RoleAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    String roleAlreadyExistsHandler(RoleAlreadyExistsException ex){
        return ex.getMessage();
    }
}
