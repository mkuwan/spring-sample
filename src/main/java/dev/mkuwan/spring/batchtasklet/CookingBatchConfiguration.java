package dev.mkuwan.spring.batchtasklet;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class CookingBatchConfiguration {
    @Bean
    public CookingTask cookingTask(){
        return new CookingTask();
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("step", jobRepository)
                .tasklet(cookingTask(), transactionManager)
                .build();
    }

    @Bean
    @Qualifier("cookingJob")
    public Job jobTasklet(JobRepository jobRepository,
                          CookingJobCompletionNotificationListener listener,
                          Step step){
        return new JobBuilder("cookingJob", jobRepository)
                .listener(listener)
                .start(step)
                .build();
    }


}
