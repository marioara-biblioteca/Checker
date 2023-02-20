package checker.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "result")
public class Result {

    @CreationTimestamp
    private LocalDateTime uploadedTime;


    private String sourceLink;
    private String resultLink;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long resultId;
    @ManyToOne
    @JoinColumn(name="taskId",nullable = true)
    private Task task;

    @ManyToOne
    @JoinColumn(name="userId",nullable = true)
    private User user;

    public Result() {
    }
    public Result(String sourceLink){
            this.sourceLink=sourceLink;
    }
    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    public void setResultLink(String resultLink) {
        this.resultLink = resultLink;
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public String getResultLink() {
        return resultLink;
    }

    public void setUser(User u){this.user = u;}
    public void setTask(Task t){this.task = t;}
    public Task getTask(){return this.task;}
}
