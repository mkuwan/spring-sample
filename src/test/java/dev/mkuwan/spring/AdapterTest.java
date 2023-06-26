package dev.mkuwan.spring;

import dev.mkuwan.spring.pattern.creational.adapter.Adaptor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
public class AdapterTest {
    private class SampleData{
        String location;
        String responseVoltage;
        SampleData(String location, String responseVoltage){
            this.location = location;
            this.responseVoltage = responseVoltage;
        }
    }

    private List<SampleData> sampleDataList = new ArrayList<>();

    @Test
    void voltageTest(){
        System.out.println("Adapter Pattern Test");
        // arrange
        sampleDataList.add(new SampleData("日本","100"));
        sampleDataList.add(new SampleData("インド","220"));
        sampleDataList.add(new SampleData("イギリス","240"));
        sampleDataList.add(new SampleData("フィンランド","220/230"));
        sampleDataList.add(new SampleData("アメリカ","110/120"));

        // assertion
        sampleDataList.forEach(x -> {
            Adaptor adaptor = new Adaptor(x.location);
            assertEquals(x.responseVoltage, adaptor.getVoltage());
        });
    }
}
