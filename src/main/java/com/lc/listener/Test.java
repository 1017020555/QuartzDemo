package com.lc.listener;

import com.lc.MailJob;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class Test {
    public static void main(String[] args) throws Exception{

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        Trigger trigger= newTrigger()
                .withIdentity("trigger1","group1")
                .startNow()
                .withSchedule(simpleSchedule().withIntervalInSeconds(1).withRepeatCount(5))
                .build();

        JobDetail jobDetail=newJob(MailJob.class)
                .withIdentity("job1","group1")
                .usingJobData("email","臭弟弟")
                .build();

        MailJobListener mailJobListener=new MailJobListener();
        KeyMatcher<JobKey> keyMatcher = KeyMatcher.keyEquals(jobDetail.getKey());
        scheduler.getListenerManager().addJobListener(mailJobListener,keyMatcher);

        scheduler.scheduleJob(jobDetail,trigger);
        scheduler.start();

        Thread.sleep(20000);
        scheduler.shutdown();
    }
}
