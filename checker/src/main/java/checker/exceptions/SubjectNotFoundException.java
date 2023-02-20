package checker.exceptions;

public class SubjectNotFoundException extends RuntimeException{
    public SubjectNotFoundException(Long id) {
        super("Could not find subject with id "+id+".");
    }

    public SubjectNotFoundException(String subName) {
        super("Could not find subject with name "+subName+".");
    }
}
