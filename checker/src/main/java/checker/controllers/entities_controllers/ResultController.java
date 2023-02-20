package checker.controllers.entities_controllers;

import checker.DTOs.ResultDTO;
import checker.entities.Result;
import checker.entities.Task;
import checker.entities.User;
import checker.services.ResultService;
import checker.services.TaskService;
import checker.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ResultController {
    private final ResultService resultService;
    private final UserService userService;
    private final TaskService taskService;
    private final ModelMapper modelMapper;
    @Autowired
    public ResultController(ResultService resultService, UserService userService, TaskService taskService, ModelMapper modelMapper) {
        this.resultService = resultService;
        this.userService=userService;
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }
    private ResultDTO convertToDTO(Result result){
        ResultDTO resultDTO=modelMapper.map(result,ResultDTO.class);
        resultDTO.setSourceLink(result.getSourceLink());
        resultDTO.setTaskId(result.getTask().getTaskId());
        return resultDTO;
    }
    @GetMapping("/results")
    public List<ResultDTO> getAllResults(){
        return resultService
                .getAllResults()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    @PostMapping("/results/{userId}")
    public Result createResultForTaskForUser(@RequestBody ResultDTO resultDTO,@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        Task task=userService.getUsersTask(user.getUserId(),resultDTO.getTaskId());

        Result result=new Result(resultDTO.getSourceLink());

        resultService.addResultToUser(result, user,task);
        return result;
    }
    @GetMapping("/results/{userId}")
    public ResponseEntity<Object>getResultForUser(@PathVariable Long userId){
        User user=userService.getUserById(userId);
        return new ResponseEntity<>(user.getResults(),HttpStatus.OK);
    }
    //metoda asta o sa fie apelata cu WebClient din publisherul kafka
    @PutMapping("/results")
    public ResultDTO updateResultWithResultLink(@RequestBody ResultDTO resultDTO){
        resultService.updateResult(resultDTO.getSourceLink(),resultDTO.getResultLink());
        return resultDTO;
    }
}
