package checker.services;

import checker.entities.Grouping;

import checker.entities.Task;
import checker.entities.Test;
import checker.exceptions.GroupNotFoundException;
import checker.exceptions.TaskAlreadyExistsException;
import checker.exceptions.TaskNotFoundException;
import checker.repositories.GroupingRepository;
import checker.repositories.TaskRepository;
import checker.repositories.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final GroupingRepository groupingRepository;
    private final TestRepository testRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, GroupingRepository groupingRepository, TestRepository testRepository)
    {
        this.taskRepository = taskRepository;
        this.groupingRepository=groupingRepository;
        this.testRepository = testRepository;
    }
    public Task getTask(Long id) throws TaskNotFoundException{
        return this.taskRepository.findById(id).stream().findFirst().orElseThrow(() -> new TaskNotFoundException(id));
    }
    public List<Task> getTasks(){
        return taskRepository.findAll();
    }
    public List<Task> getTasksBySubject(Long sId){
        List<Task> tasks=taskRepository.findAll();
        List<Task>tasksGroupedBySubject=tasks.stream().filter(
                t-> (t.getSubject()!=null &&t.getSubject().getSubjectId()==sId)

        ).toList();
        return tasksGroupedBySubject;
    }
    public List<Task>getTasksForGroup(Long gId){
        List<Task> tasks=taskRepository.findAll();
        Grouping group=groupingRepository.findById(gId).stream().findFirst().orElseThrow(() -> new GroupNotFoundException(gId));
        List<Task>tasksGroupedByGroup=tasks.stream().filter(
                t-> t.getGrupe().contains(group)
        ).toList();
        return tasksGroupedByGroup;
    }

    public void addTask(Task task) throws TaskAlreadyExistsException{
//        List<Task> tasks = taskRepository.findAll();
//        for(Task t:tasks){
//           if (tasks.contains(t))
//               throw new TaskAlreadyExistsException(task.getTaskId());
//        }
        taskRepository.save(task);
    }
    public Test getTestForTask(Long taskId) {
        Task task=getTask(taskId);
        return task.getTest();
    }

    public void assignGroupToTask(Long taskId, Long groupId) {
        Task task=getTask(taskId);
        Grouping grouping = groupingRepository.findById(groupId).stream().findFirst().orElseThrow(() -> new GroupNotFoundException(groupId));
        task.addGroup(grouping);
        grouping.addTask(task);
        taskRepository.save(task);
        System.out.println("Successfully assigned group to task!");
    }
    public void deleteTask(long id) throws TaskNotFoundException{
        try{
            taskRepository.deleteById(id);
        }catch (EmptyResultDataAccessException ex){
            throw new TaskNotFoundException(id);
        }
    }
    public void setDeadLineForTask(Long id, LocalDateTime deadLine){
        Task task=getTask(id);
        task.setDeadLine(deadLine);
        taskRepository.save(task);

    }
}
