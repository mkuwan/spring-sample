package dev.mkuwan.spring.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class StorageRunner implements ApplicationRunner {
    private final Logger logger = LoggerFactory.getLogger(StorageProperties.class);
    private final ApplicationContext context;

    public StorageRunner(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        logger.info("StorageServiceで初期化を行います");

        var storageService = context.getBean(IStorageService.class);
        storageService.deleteAll();
        storageService.init();
    }
}
