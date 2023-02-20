package checker.services;

import checker.entities.Subject;
import checker.entities.Task;
import checker.exceptions.SubjectAlreadyExistsException;
import checker.exceptions.SubjectNotFoundException;
import checker.repositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }
    public Subject getSubject(Long id) throws SubjectNotFoundException{
        return subjectRepository.findById(id).stream().findFirst().orElseThrow(() -> new SubjectNotFoundException(id));
    }
    public List<Subject> getSubjects(){
        return subjectRepository.findAll();
    }

    public void  addSubject(Subject s) throws SubjectAlreadyExistsException {
      List<Subject> subjects=subjectRepository.findAll();
      for (Subject subject : subjects)
          if(subject.getName().equals(s.getName()) && subject.getStatus().equals(s.getStatus()))
              throw new SubjectAlreadyExistsException(s.getName());
      subjectRepository.save(s);

    }

    public void deleteSubject(Long id) throws SubjectNotFoundException{
        try {
            subjectRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new SubjectNotFoundException(id);
        }
    }

    public void deleteSubjectByName(String subName){
        Subject subject = getSubjectByName(subName);
        subjectRepository.delete(subject);
        System.out.println("Succesfully deleted subject!");
    }
    public void updateSubject(Long id,String newName){
        Subject optSub=  getSubject(id);
        optSub.setName(newName);
        subjectRepository.save(optSub);
        System.out.println("Successfully updated subject name");
    }
    public Subject getSubjectByName(String name) throws SubjectNotFoundException{
        List<Subject>subjects=getSubjects();
        for(Subject s: subjects){
            if(s.getName().equals(name)){
                return s;
            }
        }
        throw new SubjectNotFoundException(name);
    }

    public void addTaskToSubject(String subName,Task task){
        Subject subject=getSubjectByName(subName);
        subject.addTask(task);
        subjectRepository.save(subject);
    }
}
