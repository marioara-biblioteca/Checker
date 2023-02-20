package checker.services;

import checker.entities.Result;
import checker.entities.Task;
import checker.entities.User;
import checker.exceptions.ResultNotFoundException;
import checker.repositories.ResultRepository;
import checker.repositories.TaskRepository;
import checker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResultService {

    private final ResultRepository resultRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    @Autowired
    public ResultService(ResultRepository resultRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.resultRepository = resultRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<Result> getAllResults() {
        return this.resultRepository.findAll();
    }

    public Result getResultBySourceLink(String link) throws ResultNotFoundException{
        List<Result> results=resultRepository.findAll();
        for(Result r:results)
            if( r.getSourceLink()!=null && r.getSourceLink().equals(link))
                return  r;
        throw new ResultNotFoundException(link);

    }
    public void updateResult(String sourceLink,String resultLink){
        Result result=getResultBySourceLink(sourceLink);
        result.setResultLink(resultLink);
        resultRepository.save(result);
    }

    public void addResultToUser(Result result, User user,Task task){

        result.setUser(user);
        //ar trebui verificat daca taskul apartine userului
        result.setTask(task);
        resultRepository.save(result);

        user.addResult(result);
        userRepository.save(user);

        task.addResult(result);
        taskRepository.save(task);

    }

    public void deleteResult(Result result) {
        this.resultRepository.delete(result);

    }

}
