package checker.entities;

import checker.enums.GroupIdentifier;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "groupclass")
public class Grouping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupingId;

    private String series;//C114
    @Column(columnDefinition = "ENUM('A', 'B','C','D','E','CYBER')")
    @Enumerated(EnumType.STRING)
    private GroupIdentifier groupIdentifier;

    @OneToMany(mappedBy = "grouping")
    private Set<User> users=new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "grouping_tasks",
            joinColumns = @JoinColumn(name = "groupingId"),
            inverseJoinColumns = @JoinColumn(name = "taskId"))
    Set<Task> tasks=new HashSet<>();

    public Grouping() {
    }

    public Grouping(String series, GroupIdentifier identifier) {
        this.series = series;
        this.groupIdentifier = identifier;
    }

    public Long getGroupingId() {
        return groupingId;
    }

    public String getSeries() {
        return series;
    }

    public GroupIdentifier getGroupIdentifier() {
        return groupIdentifier;
    }
    public void addTask(Task t){
        this.tasks.add(t);
    }

    public void setSeries(String series) {
        this.series = series;
    }
    public void addUserToGroup(User user){this.users.add(user);}
    public void setGroupIdentifier(GroupIdentifier groupIdentifier) {
        this.groupIdentifier = groupIdentifier;
    }
    public String getGroupName(){
        return series+groupIdentifier.name();
    }

    public Set<User> getUsers() {
        return users;
    }

    public Set<Task> getTasks() {
        return tasks;
    }
}
