package checker.DTOs;

import checker.enums.GroupIdentifier;

public class GroupingDTO {
    private String series;//C114
    private String groupIdentifier; //E

    public GroupingDTO(String series, String groupIdentifier) {
        this.series = series;
        this.groupIdentifier = groupIdentifier;
    }

    public GroupingDTO() {
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public void setGroupIdentifier(GroupIdentifier groupIdentifier) {
        this.groupIdentifier = groupIdentifier.name();
    }

    public String getSeries() {
        return series;
    }

    public String getGroupIdentifier() {
        return groupIdentifier;
    }
    public String getGroupName(){
        return series+groupIdentifier;
    }
}
