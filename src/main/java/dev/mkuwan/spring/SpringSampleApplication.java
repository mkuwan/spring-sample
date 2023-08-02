package dev.mkuwan.spring;

import dev.mkuwan.spring.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.swing.*;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class SpringSampleApplication {
    // Mediator PatterのNoteRunnerのコメントを外した場合は以下を使用する
//public class SpringSampleApplication extends JFrame {

    public static ApplicationContext context = null;

    public static void main(String[] args) {
        context = SpringApplication.run(SpringSampleApplication.class, args);


    /**
     * JVMが確実に終了させる場合
     * 特にバッチではバッチ完了後に確実に終了する方がいい
     * System.exit(SpringApplication.exit(SpringApplication.run(SpringSampleApplication.class, args)));
     */

    }

    /**
     * DIについて簡単な実装例
     */
//    private final DemoInterface demoInterface;
//    private final DiServiceClass serviceClass;
//    public SpringSampleApplication(DiServiceClass serviceClass, DemoInterface demoInterface) {
//        // DIを使わずにnewして使用しているパターン 密結合
//        NoDIClass noDIClass = new NoDIClass();
//        noDIClass.method();
//
//        // クラスをDIしたパターン
//        // SpringBoot内部で ServiceClass serviceClass = new ServiceClass()をしてSingletonで使用できるようにしてくれている
//        this.serviceClass = serviceClass;
//        serviceClass.method();
//
//        // インターフェイスをDIしたパターン
//        // インターフェイスを使用することで実装との分離も行われテストがしやすくなる
//        // SpringBootの内部で DemoInterFace demoInterface = new DemoImpl()をしてSingletonで使用できるようにしてくれている
//        this.demoInterface = demoInterface;
//        demoInterface.method();
//    }
}



/** DIについて **/

/**
 * よく見るコード　DIを使っていない場合
 * Classをnewして使う
 * これだとClassへの依存度が高い
 */
//class NoDIClass {
//    // ....色々なメソッドとか
//    void method(){
//        System.out.println("DIをせずにnewして作ったパターンです");
//    }
//}

/**
 * Spring BootのDIを使った場合
 * / @ServiceアノーテーションをつけることでSpringBootが自動的にDIしてくれる
 */
//@Service
//class DiServiceClass{
//    void method(){
//        System.out.println("DIパターン クラス自体をDIしたもの");
//    }
//}

/**
 * さらにインターフェイスを使った場合
 * 実装が分離される
 */
//interface DemoInterface{
//    void method();
//}
//
//@Component
//class DemoImpl implements DemoInterface{
//    @Override
//    public void method() {
//        System.out.println("インターフェイスによる依存性注入しましたよ");
//    }
//}

// 実装は基本的に1つ　以下のコメントを外すとエラーになる
//@Component
//class DemoImpl2 implements DemoInterface{
//    @Override
//    public void method() {
//        System.out.println("2_インターフェイスによる依存性注入しましたよ");
//    }
//}