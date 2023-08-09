package dev.mkuwan.spring.batchtasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class CookingTask implements Tasklet {
    private final static Logger logger = LoggerFactory.getLogger(CookingTask.class);

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(2 * 1000);
                if(i == 0) Cooking01();
                if(i == 1) Cooking02();
                if(i == 2) Cooking03();
                if(i == 3) Cooking04();
                if(i == 4) Cooking05();
            }
            Thread.sleep(1 * 1000);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return RepeatStatus.FINISHED;
    }

    private void Cooking01(){
        logger.info("フライパンを温めます");
    }
    private void Cooking02(){
        logger.info("たまごを割ってフライパンに入れます");
    }
    private void Cooking03(){
        logger.info("白身が少し白濁してきたところで水を小さじ一杯入れます");
    }
    private void Cooking04(){
        logger.info("フライパンに蓋をします");
    }
    private void Cooking05(){
        logger.info("お好みの焼き加減でお皿に出してください");
    }
}
