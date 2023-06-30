package dev.mkuwan.spring.pattern.structural.facade;

public class BitrateReader {
    public static VideoFile read(VideoFile file, ICodec codec){
        System.out.println("read file");
        return file;
    }

    public static VideoFile convert(VideoFile buffer, ICodec codec){
        System.out.println("convert file");
        return buffer;
    }
}
