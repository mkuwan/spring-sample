package dev.mkuwan.spring.pattern.structural.facade;

import java.io.File;

public class AudioMixer {
    public File fix(VideoFile file){
        System.out.println("AudioMix");
        return new File("tmp");
    }
}
