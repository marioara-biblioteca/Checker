package checker.services;

import checker.entities.Grouping;
import checker.entities.Task;
import checker.exceptions.GroupAlreadyExistsException;
import checker.exceptions.GroupNotFoundException;
import checker.repositories.GroupingRepository;
import checker.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupingService {

    private final GroupingRepository groupingRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public GroupingService(GroupingRepository groupingRepository, TaskRepository taskRepository) {
        this.groupingRepository = groupingRepository;
        this.taskRepository = taskRepository;
    }

    public List<Grouping> getAllGroups() {
        return this.groupingRepository.findAll();
    }

    public Grouping getGroupById(Long id) throws GroupNotFoundException{
        return this.groupingRepository.findById(id).stream().findFirst().orElseThrow(() -> new GroupNotFoundException(id));
    }
    public void addGroup(Grouping grouping) throws GroupAlreadyExistsException {
        List<Grouping> groupings=groupingRepository.findAll();
        for(Grouping g : groupings) {
            if( (grouping.getGroupName()).equals(g.getGroupName()) )
                throw new GroupAlreadyExistsException(grouping.getGroupName());
        }
        groupingRepository.save(grouping);

    }

    public void deleteGroup(Grouping group) {
        this.groupingRepository.delete(group);

    }
    public void deleteGroupById(Long id) throws GroupNotFoundException{
        try{
            this.groupingRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new GroupNotFoundException(id);
        }
    }
    public void deleteGroupByName(String groupName) throws GroupNotFoundException{
        List<Grouping> groups=groupingRepository.findAll();
        for(Grouping g: groups)
            if((g.getGroupName()).equals(groupName)){
                groupingRepository.delete(g);
                System.out.println("Successfully deleted groups!");
                return;
            }
        throw new GroupNotFoundException(groupName);
    }
    public Grouping getGroupByName(String name){
        List<Grouping>groups=getAllGroups();
        for(Grouping g: groups){
            if((g.getGroupName()).equals(name))
                return g;
        }
        return null;
    }
    public void assignTaskToGroup(String groupName,Task task){
        Grouping grouping=getGroupByName(groupName);
        if(grouping!=null) {
            grouping.addTask(task);
            groupingRepository.save(grouping);
            taskRepository.save(task);
        }
    }

}
