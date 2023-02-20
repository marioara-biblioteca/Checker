package checker.controllers.request_models_controllers;

import checker.entities.Grouping;
import checker.entities.Subject;
import checker.entities.Task;
import checker.entities.Test;
import checker.request_models.AddTaskRequest;
import checker.services.GroupingService;
import checker.services.SubjectService;
import checker.services.TaskService;
import checker.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class AddTaskRequestController {
    private final GroupingService groupingService;
    private final TestService testService;
    private final SubjectService subjectService;
    private final TaskService taskService;
    @Autowired
    public AddTaskRequestController(GroupingService groupingService, TestService testService, SubjectService subjectService, TaskService taskService) {
        this.groupingService = groupingService;
        this.testService = testService;
        this.subjectService = subjectService;
        this.taskService = taskService;
    }


}
