package dev.mkuwan.spring.batchtasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CookingJobCompletionNotificationListener implements JobExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(CookingJobCompletionNotificationListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("調理を開始します");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED){
            logger.info("調理が完了しました");
        }
    }
}
