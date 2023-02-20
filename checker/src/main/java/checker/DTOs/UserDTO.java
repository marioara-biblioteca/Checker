package checker.DTOs;

import checker.entities.User;

public class UserDTO {
    private String email;
    private String groupName;
    private String role;

    public  UserDTO(){

    }
    public  UserDTO(User user){
        this.email=user.getEmail();
        //TODO if-uri daca sunt setate campurile respective
        //this.role=user.getRole().getRoleType().name();
       // this.groupName=user.getGrouping().getSeries()+user.getGrouping().getGroupIdentifier().name();
    }
    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
