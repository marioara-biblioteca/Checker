package checker.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    @NotBlank
    private String password;

    @OneToMany(mappedBy = "user")
    private Set<Result> results=new HashSet<>();
    @ManyToOne
    @JoinColumn(name="roleId",nullable = true)
    private Role role;
    @ManyToOne
    @JoinColumn(name="groupingId",nullable = true)
    private Grouping grouping;

    @ManyToMany
    @JoinTable(
            name = "users_subjects",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "subjectId"))
    Set<Subject> subjects=new HashSet<>();

    public User() {}

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public User(String firstName, String lastName, String email, String password) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

    }

    public void addSubject(Subject subject){
        //if(!this.subjects.contains(subject)){
            subjects.add(subject);
            System.out.println("Subject added!");
     //   }
    }


    public Long getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public Grouping getGrouping() {
        return grouping;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    public void setGrouping(Grouping grouping){this.grouping=grouping;}
    public void addResult(Result r){
        if(!results.contains(r))
            this.results.add(r);
    }

    public Set<Result> getResults() {
        return results;
    }

}
