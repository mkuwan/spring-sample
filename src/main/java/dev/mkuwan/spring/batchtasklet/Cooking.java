package dev.mkuwan.spring.batchtasklet;

import lombok.Data;

import java.util.UUID;

@Data
public class Cooking {
    private String cookingId;
    private String cookingName;
    private String result;

    public Cooking(String cookingId, String cookingName, String result) {
        this.cookingId = UUID.randomUUID().toString();
        this.cookingName = cookingName;
        this.result = result;
    }
}
