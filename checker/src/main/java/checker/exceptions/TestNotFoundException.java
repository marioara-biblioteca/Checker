package checker.exceptions;

public class TestNotFoundException extends RuntimeException{
    public TestNotFoundException(Long id) {
        super("Could not find test with id "+ id+".");
    }

    public TestNotFoundException(String link) {
        super("Could not find test with link "+link+".");
    }
}
