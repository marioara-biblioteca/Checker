package checker.DTOs;

public class ResultDTO {
    private String sourceLink;
    private String resultLink;
    private Long taskId;

    public ResultDTO(String sourceLink,Long taskId) {
        this.sourceLink = sourceLink;
        this.taskId=taskId;
    }

    public ResultDTO(String sourceLink, String resultLink, Long taskId) {
        this.sourceLink = sourceLink;
        this.resultLink = resultLink;
        this.taskId = taskId;
    }

    public ResultDTO() {
    }

    public String getSourceLink() {
        return sourceLink;
    }


    public String getResultLink() {
        return resultLink;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    public void setResultLink(String resultLink) {
        this.resultLink = resultLink;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }
}
