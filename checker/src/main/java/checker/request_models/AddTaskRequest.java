package checker.request_models;

import jakarta.validation.constraints.FutureOrPresent;
import jdk.jfr.Timestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class AddTaskRequest {
    private String subjectName;
    private String testLink;
    @FutureOrPresent(message ="failed")
    private LocalDateTime deadLine;
    private String requirement;
    Set<String> assignedGroups=new HashSet<>();

    public AddTaskRequest() {
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTestLink() {
        return testLink;
    }

    public void setTestLink(String testLink) {
        this.testLink = testLink;
    }

    public LocalDateTime getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDateTime deadLine) {
        this.deadLine = deadLine;
    }

    public Set<String> getAssignedGroups() {
        return assignedGroups;
    }

    public void addGroup(String group) {this.assignedGroups.add(group);}

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }
}
