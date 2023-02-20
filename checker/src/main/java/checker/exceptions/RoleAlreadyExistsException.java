package checker.exceptions;

public class RoleAlreadyExistsException extends RuntimeException{

    public RoleAlreadyExistsException(String role) {
        super("Role "+role+" already exists.");
    }
}
