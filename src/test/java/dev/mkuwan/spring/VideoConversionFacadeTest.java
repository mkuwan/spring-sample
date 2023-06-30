package dev.mkuwan.spring;

import dev.mkuwan.spring.pattern.structural.facade.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VideoConversionFacadeTest {

//    @MockBean
//    private ICodec codec;


    /** Unit Test **/
    // CodecFactory
    @Test
    public void CodecFactoryExtract_ShouldReturnMp4Codec() throws Exception {
        // arrange
        VideoFile videoFile = new VideoFile("sample.mp4");

        // act
        var codec = CodecFactory.extract(videoFile);

        // assert
        assertEquals(Mp4Codec.class.getName(), codec.getClass().getName());
    }

    @Test
    public void CodecFactoryExtract_ShouldReturnOggCodec() throws Exception {
        // arrange
        VideoFile videoFile = new VideoFile("sample.ogg");

        // act
        var codec = CodecFactory.extract(videoFile);

        // assert
        assertEquals(OggCodec.class.getName(), codec.getClass().getName());
    }

    @Test
    public void CodecFactoryExtract_ShouldReturnException() {
        // arrange
        VideoFile videoFile = new VideoFile("sample.jpg");

        // act & assert
        var exception = assertThrows(Exception.class, () -> {
            CodecFactory.extract(videoFile);
        });

        assertEquals(exception.getMessage(), "Codec type is missing");
    }

    @Test void convertVideoTest(){
        // arrange
        var videoConversionFacade = new VideoConversionFacade();

        // act
        var result = videoConversionFacade.convertVideo("sample.mp4", "ogg");

        // assertion
        assertEquals(result.getName(), "tmp");
    }
}