package com.taotao.job.service.schedule;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.taotao.job.entity.ScheduleJob;

/**
 * 定时任务执行业务逻辑核心类
 * job执行成功或者失败最好将日志打印出来
 * @author xiangzuotao
 * @2019年11月7日
 */
public class ScheduleJobBean extends QuartzJobBean {
	
	private ExecutorService service = Executors.newSingleThreadExecutor(); 

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		ScheduleJob scheduleJob = (ScheduleJob)context.getMergedJobDataMap().get(ScheduleJob.JOB_PARAM_KEY);
		try {
			ScheduleJobRunnable task = new ScheduleJobRunnable(scheduleJob.getBeanName(),
	        		scheduleJob.getMethodName(), scheduleJob.getParams());
			service.execute(task);
		} catch (Exception e) {
			// job执行的日志
		} 
	}

}
