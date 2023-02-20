package kakfka_demo.db;

public class Result {
    private String sourceLink;
    private String resultLink;
    private Long taskId;
    public Result() {
    }

    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
    }

    public String getResultLink() {
        return resultLink;
    }

    public void setResultLink(String resultLink) {
        this.resultLink = resultLink;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "Result{" +
                "sourceLink='" + sourceLink + '\'' +
                ", resultLink='" + resultLink + '\'' +
                '}';
    }
}