package checker.DTOs;

import checker.enums.Semester;

public class SubjectDTO {
    private String name;
    private String semester;

    public SubjectDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
