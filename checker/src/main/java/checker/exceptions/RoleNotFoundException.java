package checker.exceptions;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(String message) {
        super("Could not find role "+message+".");
    }
    public RoleNotFoundException(Long id) {
        super("Could not find role with id "+id+".");
    }
}
