package checker.exceptions;

public class SubjectAlreadyExistsException extends RuntimeException{
    public SubjectAlreadyExistsException(String name) {
        super("Subject with name "+ name+" already exists.");
    }
}
