package dev.mkuwan.spring;

import dev.mkuwan.spring.pattern.behavioral.mediator.noteapp.*;
import dev.mkuwan.spring.pattern.behavioral.mediator.noteapp.List;
import dev.mkuwan.spring.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class SpringSampleApplication extends JFrame {
    public static ApplicationContext context = null;

    public static void main(String[] args) {
        context = SpringApplication.run(SpringSampleApplication.class, args);


    /**
     * JVMが確実に終了する場合
     * 特にバッチではバッチ完了後に確実に終了する方がいい
     * System.exit(SpringApplication.exit(SpringApplication.run(SpringSampleApplication.class, args)));
     */

    }

}
