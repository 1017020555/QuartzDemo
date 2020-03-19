package com.lc;

import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@DisallowConcurrentExecution
public class MailJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail detail=jobExecutionContext.getJobDetail();
        String email = detail.getJobDataMap().getString("email");

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String now = sdf.format(new Date());

        System.out.printf("给邮件地址 %s 发出了一封定时邮件, 当前时间是: %s%n" ,email, now);
    }
}
