package com.atguigu.wms.outbound.task;

import com.atguigu.wms.base.client.WareConfigFeignClient;
import com.atguigu.wms.model.base.WareConfig;
import com.atguigu.wms.outbound.service.OutPickingTaskService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 *
 */
@Component
@Slf4j
@EnableScheduling
public class ScheduledTask implements SchedulingConfigurer {

    @Autowired
    private OutPickingTaskService outPickingTaskService;

    @Resource
    private WareConfigFeignClient wareConfigFeignClient;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(doTask(), getTrigger());
    }

    private Runnable doTask() {
        return new Runnable() {
            @Override
            public void run() {
                // 业务逻辑
                log.info("执行了ScheduledTask,时间为:" + new DateTime(new Date()).toString("yyyy-MM-dd HH:mm:ss"));
                outPickingTaskService.run();
            }
        };
    }

    private Trigger getTrigger() {
        return new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                // 触发器
                CronTrigger trigger = new CronTrigger(getCron());
                return trigger.nextExecutionTime(triggerContext);
            }
        };
    }

    /**
     * cron从数据库中获取，这样在配置值修改后，定时任务调度器便被更新了，不过需要注意的是，此种方式修改了配置值后，
     * 需要在下一次调度结束后，才会更新调度器，并不会在修改配置值时实时更新。
     * @return
     */
    public String getCron() {
        WareConfig waveConfig = wareConfigFeignClient.getWaveConfig();
        //当前为多少秒执行一次，方便测试；正式环境为多少分钟执行一次
        return "0/" + waveConfig.getIntervalTime() + " * * * * ?";
    }

}
