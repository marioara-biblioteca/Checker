package checker.entities;

import checker.enums.Semester;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    /*@SequenceGenerator(name="subject_sequence",
    sequenceName = "subject_sequence",
    allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "subject_sequence")
*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subjectId;
    @NotBlank
    private String name;

    @Column(columnDefinition = "ENUM('FIRST', 'SECOND')")
    @Enumerated(EnumType.STRING)
    private Semester status;

    @ManyToMany(mappedBy = "subjects")
    Set<User> users=new HashSet<>();

    @OneToMany(mappedBy = "subject")
    private Set<Task> tasks=new HashSet<>();

    public Subject() {
    }

    public Subject(String name, Semester status) {
        this.name = name;
        this.status = status;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public String getName() {
        return name;
    }

    public Semester getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }
    public  void addTask(Task t){
        if(!tasks.contains(t))
            tasks.add(t);
    }
}
