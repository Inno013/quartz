package uji.sistem.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uji.sistem.quartz.service.FileMsAccess;
import uji.sistem.quartz.service.FileUploadJob;

@SpringBootApplication
public class QuartzServiceApplication {
    public static void main(String[] args) {
        try {
            JobDetail job = JobBuilder.newJob(FileUploadJob.class).build();
            JobDetail job1 = JobBuilder.newJob(FileMsAccess.class).build();
            Trigger t = TriggerBuilder.newTrigger().withIdentity("trigger01")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(0)
                            .withRepeatCount(0)).build();
            Trigger t1 = TriggerBuilder.newTrigger().withIdentity("trigger02")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(0)
                            .withRepeatCount(0)).build();
            Scheduler s = StdSchedulerFactory.getDefaultScheduler();
            s.scheduleJob(job, t);
            s.scheduleJob(job1, t1);
            s.start();
        } catch (SchedulerException ex) {
            ex.printStackTrace();
        }
    }
}
