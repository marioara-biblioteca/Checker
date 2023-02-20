package checker.controllers.entities_controllers;


import checker.DTOs.GroupingDTO;
import checker.DTOs.UserDTO;
import checker.entities.Grouping;
import checker.entities.Subject;
import checker.entities.User;
import checker.services.UserService;
import checker.utils.Utils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static checker.utils.Utils.HALF_DAY;
import static checker.utils.Utils.algorithm;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.*;

@RestController
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;
    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setEmail(user.getEmail());

        //userDTO.setGroupName(user.getGrouping().getSeries()+user.getGrouping().getGroupIdentifier().name());
        //userDTO.setRole(user.getRole().getRoleType().name());
        return userDTO;
    }

    private GroupingDTO convertToDTO(Grouping group) {
        GroupingDTO groupingDTO = modelMapper.map(group, GroupingDTO.class);

        groupingDTO.setGroupIdentifier(group.getGroupIdentifier());
        groupingDTO.setSeries(group.getSeries());

        return groupingDTO;
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO create exception class @Cosmin
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader!= null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUserByEmail(username);

                ArrayList<String> roles = new ArrayList<>();
                roles.add(user.getRole().getRoleType().toString());

                String access_token = Utils.get_JWT(user.getEmail(), HALF_DAY, request.getRequestURL().toString(), roles);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            }catch (Exception exception){
                response.setHeader("error", exception.getMessage());
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }else{
            throw new RuntimeException("Refresh token is missing");
        }

    }

    @GetMapping("/users")
    public List<UserDTO> getUsers(){
        List<User> users= userService.getUsers();
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public  ResponseEntity<Object> getUserById(@PathVariable Long id){
        UserDTO userDTO=convertToDTO(userService.getUserById(id));
        return new ResponseEntity<>(userDTO,HttpStatus.OK);
    }

    @GetMapping("/users/info")
    public ResponseEntity<Object> getUserInfo(@RequestParam(value = "username") String username ){
        User user = userService.getUserByEmail(username);
        UserInfo userInfo = new UserInfo(user);
        return new ResponseEntity<>(userInfo,HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> registerUser(@RequestBody User u) {
        userService.addUser(u);
        return new ResponseEntity<>("User added successfully!", HttpStatus.CREATED);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
        return new ResponseEntity<>("Successfully deleted user",HttpStatus.OK);
    }
    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUserPassword(@RequestBody String newPass,@PathVariable Long id){
        userService.updateUserPassword(id,newPass);
        return new ResponseEntity<>("Succesfully updated password",HttpStatus.OK);
    }
    //adauga o materie pentru intreaga grupa
    @PutMapping("/users/subject/{groupName}")
    public ResponseEntity<Object>setSubjectForGroup(@RequestBody String subjectName,@PathVariable String groupName){
        userService.addSubjectForGroup(subjectName,groupName);
        return new ResponseEntity<>("Succesfully added subject for group",HttpStatus.OK);
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<Object> setUserRole(@RequestBody String role, @PathVariable Long id) {
        userService.setRole(id,role);
        return new ResponseEntity<>("Succesfully set user role",HttpStatus.ACCEPTED);
    }
    @PutMapping("/users/{id}/group")
    public ResponseEntity<Object> setUserGroup(@RequestBody String groupName, @PathVariable Long id) {
        userService.setGroup(id,groupName);
        return new ResponseEntity<>("Succesfully set user group",HttpStatus.ACCEPTED);
    }
    @GetMapping("/users/{userId}/group")
    public ResponseEntity<Object> getUserGroup(@PathVariable Long userId) {
        Grouping grouping = userService.getUsersGrouping(userId);
        if(grouping!= null){
            GroupingDTO groupingDTO = convertToDTO(grouping);
            return new ResponseEntity<>(groupingDTO ,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null ,HttpStatus.OK);
        }
    }
    @GetMapping("/users/{userId}/tasks")
    public ResponseEntity<Object>getUserTasks(@PathVariable Long userId){
        return new ResponseEntity<>( userService.getUsersTasks(userId),HttpStatus.OK);
    }
    @GetMapping("/users/{userId}/tasks/{taskId}")
    public ResponseEntity<Object>getUserTask(@PathVariable Long userId,@PathVariable Long taskId){

        return new ResponseEntity<>( userService.getUsersTask(userId,taskId),HttpStatus.OK);
    }

    @GetMapping("/users/{Id}/subjects")
    public ResponseEntity<Object> getUserSubjects(@PathVariable Long Id) {
            User user = userService.getUserById(Id);
            Set<Subject> subjects = user.getSubjects();
            return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    public static class UserInfo{
        private Long userId;
        private String firstName;
        private String lastName;

        public Long getUserId() {
            return userId;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
        }

        private String email;

        public UserInfo(User user) {
            if(user.getUserId()!=null){
                this.userId=user.getUserId();
            }
            if(user.getFirstName()!=null){
                this.firstName=user.getFirstName();
            }
            if(user.getLastName()!=null){
                this.lastName=user.getLastName();
            }
            if(user.getEmail()!=null){
                this.email=user.getEmail();
            }

        }
    }

}


