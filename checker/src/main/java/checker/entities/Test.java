package checker.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "test")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testId;
    @NotBlank
    private String link;
    @OneToMany(mappedBy = "test")
    private Set<Task> tasks=new HashSet<>();

    public Test() {
    }
    public void addTaskToTest(Task t){
        if(!this.tasks.contains(t))
            tasks.add(t);
    }
    public Test(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public Long getTestId() {
        return testId;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
