package checker.controllers.request_models_controllers;

import checker.entities.User;
import checker.request_models.LoginRequest;
import checker.services.UserService;
import checker.utils.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginRequestController {
    private final UserService userService;

    @Autowired
    public LoginRequestController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/login")
    public ResponseEntity<Object>loginUser(@RequestBody LoginRequest loginRequest){
        User user=userService.getUserByEmail(loginRequest.getEmail());
        if(Utils.get_SHA_256_SecurePassword(
                loginRequest.getPassword())==(user.getPassword()))
            return new ResponseEntity<>(user.getUserId(), HttpStatus.OK);
        else return new ResponseEntity<>("Bad password!",HttpStatus.NOT_FOUND);

    }
}
