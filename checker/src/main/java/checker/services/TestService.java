package checker.services;

import checker.entities.Task;
import checker.entities.Test;
import checker.exceptions.TestAlreadyExistsException;
import checker.exceptions.TestNotFoundException;
import checker.repositories.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TestService {
    private final TestRepository testRepository;
    @Autowired
    public TestService(TestRepository testRepository) {

        this.testRepository = testRepository;
    }
    public List<Test> getTests(){
        return testRepository.findAll();
    }
    public Test getTestById(Long id) throws TestNotFoundException{
        return testRepository.findById(id).stream().findFirst().orElseThrow(() -> new TestNotFoundException(id));
    }
    public void  addTest(Test test) throws TestAlreadyExistsException {
        //verificam daca mai exista vreun test cu acelasi link
        List<Test> tests=testRepository.findAll();
        for(Test t : tests){
            if(t.getLink().equals(test.getLink()))
                throw new TestAlreadyExistsException(test.getLink());
        }
        testRepository.save(test);
    }
    public void deleteTest(Long id) throws TestNotFoundException{
        try {
            testRepository.deleteById(id);
            System.out.println("Successfully deleted test");
        }catch (EmptyResultDataAccessException ex){
            throw new TestNotFoundException(id);
        }
    }

    public void assignTestToTask(Task task,Test test){
        task.setTest(test);
        test.addTaskToTest(task);
        testRepository.save(test);
    }

    public void updateTestLink(Long id,String newLink){
        Test test=getTestById(id);
        test.setLink(newLink);
        testRepository.save(test);
        System.out.println("Successfully updated test link");
    }
    public Test getTestByLink(String link){
        List<Test>tests=getTests();
        for(Test t: tests)
            if(t.getLink().equals(link))
                return t;
        throw new TestNotFoundException(link);
    }

}
