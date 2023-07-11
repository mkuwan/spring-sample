package dev.mkuwan.spring.hateoas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

public class HateoasGreeting extends RepresentationModel<HateoasGreeting> {
    private final String content;

    @JsonCreator
    public HateoasGreeting(@JsonProperty("content") String content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }
}
