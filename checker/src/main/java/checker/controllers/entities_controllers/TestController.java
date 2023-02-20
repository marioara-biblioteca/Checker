package checker.controllers.entities_controllers;

import checker.DTOs.TestDTO;
import checker.entities.Test;
import checker.services.TestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TestController {
    private final TestService testService;
    private final ModelMapper modelMapper;

    @Autowired
    public TestController(TestService testService, ModelMapper modelMapper) {
        this.testService = testService;
        this.modelMapper = modelMapper;
    }

    private TestDTO convertToDTO(Test test) {
        TestDTO testDTO = modelMapper.map(test, TestDTO.class);
        testDTO.setLink(test.getLink());
        return testDTO;
    }
    //    @GetMapping("/tests")
//    public ResponseEntity<Object>  getAllTests(){
//        List<Test> tests=
//                testService.getTests();
//        if(tests==null) return new ResponseEntity<>("Tests not found",HttpStatus.NOT_FOUND);
//        else return new ResponseEntity<>(tests,HttpStatus.OK);
//    }
    @GetMapping("/tests")
    public ResponseEntity<Object>  getAllTests(){
        return new ResponseEntity<>(testService
                .getTests()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList()),HttpStatus.OK);

    }
    //    @GetMapping("/tests/{id}")
//    public  ResponseEntity<Object> getTest(@PathVariable  Long id){
//
//        Test test= testService.getTestById(id);
//        if(test !=null) return new ResponseEntity<>(test,HttpStatus.OK);
//        else return new ResponseEntity<>("Test not found",HttpStatus.NOT_FOUND);
//    }
    @GetMapping("/tests/{id}")
    public  ResponseEntity<Object> getTest(@PathVariable  Long id) {
        return new ResponseEntity<>(convertToDTO(testService.getTestById(id)),HttpStatus.OK);
    }
    @PostMapping("/tests")
    public ResponseEntity<Object> createTest(@RequestBody Test test){
        testService.addTest(test);
        return new ResponseEntity<>("Test added successfully!", HttpStatus.CREATED);
    }
    @DeleteMapping("/tests/{id}")
    public ResponseEntity<Object> deleteTestById(@PathVariable("id")Long id) {
        testService.deleteTest(id);
        return new ResponseEntity<>("Successfully deleted test",HttpStatus.OK);
    }
    @PutMapping("/tests/{id}")
    public ResponseEntity<Object> updateSubject(@RequestBody String newLink, @PathVariable Long id) {
        testService.updateTestLink(id,newLink);
        return new ResponseEntity<>("Successfully changed link of test",HttpStatus.ACCEPTED);
    }
}
