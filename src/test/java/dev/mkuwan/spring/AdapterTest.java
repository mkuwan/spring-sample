package dev.mkuwan.spring;

import dev.mkuwan.spring.pattern.creational.adapter.Adaptor;
import dev.mkuwan.spring.storage.StorageProperties;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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

    @BeforeEach
    void setup(){
        sampleDataList = new ArrayList<>();
    }

    @ParameterizedTest
    @CsvSource({"日本, 100",
            "インド, 220",
            "イギリス, 240",
            "フィンランド, 220/230",
            "アメリカ, 110/120"})
    void voltageTestWithParameterized(String location, String responseVoltage){
        // arrange
        sampleDataList.add(new SampleData(location, responseVoltage));

        // assertion
        sampleDataList.forEach(x -> {
            Adaptor adaptor = new Adaptor(x.location);
            assertEquals(x.responseVoltage, adaptor.getVoltage());
        });
    }

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
