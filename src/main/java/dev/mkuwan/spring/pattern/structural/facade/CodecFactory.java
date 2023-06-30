package dev.mkuwan.spring.pattern.structural.facade;

import org.springframework.stereotype.Component;

@Component
public class CodecFactory {
    public static ICodec extract(VideoFile file) throws Exception {
        String type = file.getCodecType();
        if(type.equals("mp4")){
            System.out.println("extract mp4");
            return new Mp4Codec();
        }

        if(type.equals("ogg")){
            System.out.println("extract ogg");
            return new OggCodec();
        }

        throw new Exception("Codec type is missing");
    }
}
