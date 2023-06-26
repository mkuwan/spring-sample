package dev.mkuwan.spring.pattern.creational.adapter;

/**
 *
 */
public class Adaptee {

    public String getVoltage(String location){
        switch (location){
            case "日本":
                return "100";
            case "インド", "韓国", "フランス":
                return "220";
            case "シンガポール":
                return "230";
            case "イギリス":
                return "240";
            case "フィンランド":
                return "220/230";
            case "アメリカ":
                return "110/120";
        }
        return "unknown";
    }

    public String getTerminal(String location){
        return switch (location) {
            case "日本", "アメリカ" -> "A";
            case "インド" -> "B3/C";
            case "韓国", "フランス" -> "C/SE";
            case "シンガポール", "イギリス" -> "BF";
            case "フィンランド" -> "C";
            default -> "unknown";
        };
    }
}
