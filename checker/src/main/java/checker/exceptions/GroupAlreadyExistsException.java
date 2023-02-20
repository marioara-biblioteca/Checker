package checker.exceptions;

public class GroupAlreadyExistsException extends RuntimeException{

    public GroupAlreadyExistsException(String groupName) {
        super("Group "+groupName+" already exists.");
    }
}
