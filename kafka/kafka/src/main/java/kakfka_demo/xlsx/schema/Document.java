package kakfka_demo.xlsx.schema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
//trebuie respectata  ordinea asta cand formatam json-ul
@JsonPropertyOrder({
        "userEmail",
        "sourceLink",
        "resultLink"
})
public class Document {
//    @JsonProperty("name")
//    private String name;
    @JsonProperty("userEmail")
    private String userEmail;
    @JsonProperty("sourceLink")
    private String sourceLink;
    @JsonProperty("resultLink")
    private String resultLink;

    //TREBUIE NEAPARAT SA EXISTE SI CONSTRUCTORUL DEFAULT
    public Document()
    {

    }
    public Document( String userEmail,String sourceLink,String resultLink) {
        this.userEmail=userEmail;
        this.sourceLink = sourceLink;
        this.resultLink=resultLink;
    }

//    @JsonProperty("name")
//    public String getName() {
//        return name;
//    }
    @JsonProperty("userEmail")
    public String getOwner() {
        return userEmail;
    }
    @JsonProperty("sourceLink")
    public String getSourceLink() {
        return sourceLink;
    }
    @JsonProperty("resultLink")
    public String getResultLink() {
        return resultLink;
    }

}
