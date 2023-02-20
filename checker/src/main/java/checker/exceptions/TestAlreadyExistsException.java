package checker.exceptions;

public class TestAlreadyExistsException extends RuntimeException{
    public TestAlreadyExistsException(String link) {
        super("Test with link "+link + " already exists.");
    }
}
