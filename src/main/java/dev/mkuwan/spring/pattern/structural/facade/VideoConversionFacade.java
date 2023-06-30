package dev.mkuwan.spring.pattern.structural.facade;

import java.io.File;

public class VideoConversionFacade {
    public File convertVideo(String fileName, String format){
        System.out.println("Convert start");

        try {
            VideoFile file = new VideoFile(fileName);
            ICodec sourceCodec = CodecFactory.extract(file);
            ICodec destinationCodec = null;

            if(format.equals("mp4")){
                destinationCodec = new Mp4Codec();
            }

            if(format.equals("ogg")){
                destinationCodec = new OggCodec();
            }

            if(destinationCodec == null){
                throw new Exception("未対応");
            }

            VideoFile buffer = BitrateReader.read(file, sourceCodec);
            VideoFile intermediateResult = BitrateReader.convert(buffer, destinationCodec);
            File result = (new AudioMixer()).fix(intermediateResult);

            System.out.println("Conversion Completed");
            return result;

        } catch (Exception ex) {
            System.out.println("Error Occurred : " + ex.getMessage());
            return null;
        }
    }
}
