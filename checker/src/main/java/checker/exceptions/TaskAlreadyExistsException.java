package checker.exceptions;

public class TaskAlreadyExistsException extends RuntimeException{
    public TaskAlreadyExistsException(Long taskId) {
        super("Task with id "+taskId+" already exists.");
    }
}
