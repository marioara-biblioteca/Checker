package checker.controllers.entities_controllers;
import checker.entities.Subject;
import checker.enums.Semester;
import checker.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
public class SubjectController {
    private final SubjectService subjectService;
    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/subjects")
    public ResponseEntity<Object> getAllSubjects()
    {
        List<Subject> subjects= subjectService.getSubjects();
        return new ResponseEntity<>(subjects,HttpStatus.OK);
    }
    @GetMapping("/subjects/{id}")
    public ResponseEntity<Object> getSubject(@PathVariable Long id) {
        Subject sub= subjectService.getSubject(id);
        return new ResponseEntity<>(sub,HttpStatus.OK);
    }
    @PostMapping("/subjects")
    public ResponseEntity<Object> createSubject(@RequestParam(value = "name") String name,
                                                @RequestParam(value = "status") String status) {
        subjectService.addSubject(new Subject(name,Semester.valueOf(status)));
        return new ResponseEntity<>("Subject added successfully!", HttpStatus.CREATED);
    }
    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<Object> deleteSubjectById(@PathVariable("id") Long id){
        subjectService.deleteSubject(id);
        return new ResponseEntity<>("Successfully deleted subject",HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/subjects")
    public ResponseEntity<Object> deleteSubjectByName(@RequestParam(value = "name") String name) {
        subjectService.deleteSubjectByName(name);
        return new ResponseEntity<>("Successfully deleted subject",HttpStatus.ACCEPTED);
    }
    @PutMapping("/subjects/{id}")
    public ResponseEntity<Object> updateSubject(@RequestBody String newName, @PathVariable Long id) {
        subjectService.updateSubject(id,newName);
        return new ResponseEntity<>("Succesfully updated subject",HttpStatus.ACCEPTED);
    }
}
