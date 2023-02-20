package checker.exceptions;

public class ResultNotFoundException extends RuntimeException{

    public ResultNotFoundException(String link) {
        super("Could not find result with link "+link+".");
    }
}
