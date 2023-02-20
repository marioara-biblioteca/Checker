package checker.controllers.entities_controllers;

import checker.DTOs.TaskDTO;

import checker.entities.Task;
import checker.entities.Test;
import checker.services.TaskService;
import org.modelmapper.ModelMapper;
import org.simpleframework.xml.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TaskController {
    private final TaskService taskService;
    private final ModelMapper modelMapper;

    @Autowired
    public TaskController(TaskService taskService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }
    private TaskDTO convertToDTO(Task task) {
        if (task.getTest() == null || task.getSubject() == null)
            return null;
        TaskDTO taskDTO = modelMapper.map(task, TaskDTO.class);

        taskDTO.setTaskId(task.getTaskId());
        taskDTO.setDeadLine(task.getDeadLine());
        taskDTO.setSubjectName(task.getSubject().getName());
        taskDTO.setRequirement(taskDTO.getRequirement());

        if (task.getTest().getLink() != null) {
            taskDTO.setTestLink(task.getTest().getLink());
        }

        return taskDTO;
    }

    //    @GetMapping("/tasks/subject/{subjectId}")
//    public ResponseEntity<Object> getTasksBySubject(@PathVariable Long subjectId){
//
//        List<Task> tasks = taskService.getTasksBySubject(subjectId);
//        if(tasks!=null)
//        return new ResponseEntity<>(tasks,HttpStatus.OK);
//        else return new ResponseEntity<>("Tasks not found fpor this subject",HttpStatus.NOT_FOUND);
//    }
    @GetMapping("/tasks/subject/{subjectId}")
    public ResponseEntity<Object> getTasksBySubject(@PathVariable Long subjectId) {
        return new ResponseEntity<>(taskService
                .getTasksBySubject(subjectId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    //    @GetMapping("/tasks")
//    public ResponseEntity<Object> getAllTasks(){
//        List<Task> tasks= taskService.getTasks();
//        return new ResponseEntity<>(tasks,HttpStatus.OK);
//    }
    @GetMapping("/tasks")
    public ResponseEntity<Object> getAllTasks(){
        return new ResponseEntity<>(
                taskService
                        .getTasks()
                        .stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList())
                ,HttpStatus.OK);
    }
    //    @GetMapping("/tasks/groups/{groupId}")
//    public  ResponseEntity<Object> getTasksForGroup(@PathVariable Long groupId){
//        return new ResponseEntity<>(taskService.getTasksForGroup(groupId),HttpStatus.OK);
//    }
    @GetMapping("/tasks/groups/{groupId}")
    public  ResponseEntity<Object> getTasksForGroup(@PathVariable Long groupId){
        return new ResponseEntity<>(taskService
                .getTasksForGroup(groupId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }
    @GetMapping("/tasks/{id}")
    public ResponseEntity<Object> getTask(@PathVariable Long id){
        Task t=taskService.getTask(id);
        return new ResponseEntity<>(t,HttpStatus.OK);
    }
    @GetMapping("/tasks/{id}/test")
    public ResponseEntity<Object> getTestForTask(@PathVariable Long id){
        Test t = this.taskService.getTestForTask(id);
        return new ResponseEntity<>(t,HttpStatus.OK);
        // return new ResponseEntity<>("Test not found",HttpStatus.NOT_FOUND);
    }
    //controller pt adaugarea unui Task este in AddTaskRequestController
    @PutMapping("/tasks/{taskId}/group")
    public ResponseEntity<Object> assignGroupToTask(@RequestBody Long groupingId,@PathVariable Long taskId ){
        taskService.assignGroupToTask(taskId,groupingId);
        return new ResponseEntity<>("Successfully assigned group to task" ,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Object>deleteTask(@PathVariable Long id){
        this.taskService.deleteTask(id);
        return new ResponseEntity<>("Succesdfully deleted task",HttpStatus.OK);
    }
    @PutMapping("/tasks/{id}")
    public  ResponseEntity<Object> setDeadLineForTask(@PathVariable Long id, @RequestBody String deadLineString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime deadLine = LocalDateTime.parse(deadLineString, formatter);
        taskService.setDeadLineForTask(id,deadLine);
        return new ResponseEntity<>("Successfully updated task's deadline",HttpStatus.OK);
    }

}
