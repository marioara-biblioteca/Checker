package checker.entities;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    private String requirement;
    @OneToMany(orphanRemoval = true, cascade = CascadeType.REMOVE,mappedBy = "task")
    private Set<Result> results=new HashSet<>();
    @ManyToOne
    @JoinColumn(name="testId",nullable = true)
    private Test test;

    @ManyToOne
    @JoinColumn(name="subjectId",nullable = true)
    private Subject subject;

    @ManyToMany(mappedBy = "tasks")
    Set<Grouping> grupe=new HashSet<>();


    @Timestamp
    private LocalDateTime deadLine;

    public Task() {
    }
    public Task(String requirement) {
        this.requirement = requirement;
    }

    public Long getTaskId(){return taskId;}

    public Test getTest(){
        return test;
    }
    public Subject getSubject(){
        return subject;
    }
    public  void addGroup(Grouping g){
        if(!this.grupe.contains(g)) {
            this.grupe.add(g);
        }
    }
    public void setTest(Test t){
        this.test=t;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Set<Grouping> getGrupe() {
        return grupe;
    }

    public  void addResult(Result result){
        if(!this.results.contains(result))
            this.results.add(result);
    }
    public LocalDateTime getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDateTime deadLine) {
        this.deadLine = deadLine;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }
}
