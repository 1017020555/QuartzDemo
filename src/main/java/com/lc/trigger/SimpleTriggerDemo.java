package com.lc.trigger;

import com.lc.MailJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class SimpleTriggerDemo {
    public static void main(String[] args) throws Exception{

        Scheduler scheduler= StdSchedulerFactory.getDefaultScheduler();

//        Date startTime = DateBuilder.nextGivenSecondDate(null, 8);
        Date startTime = DateBuilder.futureDate(10, DateBuilder.IntervalUnit.SECOND);

        JobDetail job=JobBuilder.newJob(MailJob.class)
                .withIdentity("job1","group1")
                .usingJobData("email","憨批！")
                .build();
//
//        SimpleTrigger trigger=(SimpleTrigger) TriggerBuilder.newTrigger()
//                .withIdentity("job1","group1")
////                .withSchedule(simpleSchedule().withIntervalInSeconds(1).withRepeatCount(5))
//                .withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(1))
//                .startAt(startTime)
//                .build();

        CronTrigger trigger = newTrigger().withIdentity("trigger1", "group1").withSchedule(cronSchedule("0/2 * * * * ?"))
                .build();

        Date ft = scheduler.scheduleJob(job, trigger);
        System.out.println("当前时间是：" + new Date().toLocaleString());
//        System.out.printf("%s 这个任务会在 %s 准时开始运行，累计运行%d次，间隔时间是%d毫秒%n", job.getKey(), ft.toLocaleString(), trigger.getRepeatCount()+1, trigger.getRepeatInterval());
        System.out.println("使用的Cron表达式是："+trigger.getCronExpression());

        scheduler.start();

        Thread.sleep(200000);
        scheduler.shutdown(true);
    }
}
