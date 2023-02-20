package checker.exceptions;

public class GroupNotFoundException extends RuntimeException{
    public GroupNotFoundException(String message) {
        super("Could not find group " + message + ".");
    }
    public GroupNotFoundException(Long id) {
        super("Could not find group with id " + id + ".");
    }
}
