package com.taotao.job.service.schedule;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.taotao.common.exce.TaotaoJobException;
import com.taotao.common.utils.Constant;
import com.taotao.job.entity.ScheduleJob;

public abstract class AbstractSchedule {

	private final static String JOB_NAME = "TASK_";
	
	/**
	 * 获取触发器key
	 * @param jobId
	 * @return
	 */
	public static TriggerKey getTriggerKey(Long jobId) {
		return TriggerKey.triggerKey(JOB_NAME + jobId);
	}
	
	/**
	 * 获取jobKey
	 * @param jobId
	 * @return
	 */
	public static JobKey getJobKey(Long jobId) {
		return  JobKey.jobKey(JOB_NAME + jobId);
	}
	
	/**
	 * 获取cron表达式触发器
	 * @param scheduler
	 * @param jobId
	 * @return
	 */
	public static CronTrigger getCronTrigger(Scheduler scheduler, Long jobId) {
		try {
			return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
		} catch (SchedulerException e) {
			throw new TaotaoJobException("获取定时任务CronTrigger出现异常", e);
		}
	}
	
	/**
	 * 创建定时任务
	 * @param scheduler
	 * @param scheduleJob
	 */
	public static void create(Scheduler scheduler, ScheduleJob scheduleJob) {
		try {
			//构建job信息
			JobDetail jobDetail = JobBuilder.newJob(ScheduleJobBean.class).withIdentity(getJobKey(scheduleJob.getJobId())).build();
			
			//表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCron())
            		.withMisfireHandlingInstructionDoNothing();
            
            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduleJob.getJobId())).withSchedule(scheduleBuilder).build();
            
            //放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(ScheduleJob.JOB_PARAM_KEY, scheduleJob);
            
            scheduler.scheduleJob(jobDetail, trigger);
            //暂停任务
            if(scheduleJob.getStatus() == Constant.ScheduleStatus.PAUSE.getValue()){
            	pauseJob(scheduler, scheduleJob.getJobId());
            }
		} catch (SchedulerException e) {
			throw new TaotaoJobException("创建定时任务失败", e);
		}
	}
	
	/**
	 * 更新定时任务
	 * @param scheduler
	 * @param scheduleJob
	 */
	public static void update(Scheduler scheduler, ScheduleJob scheduleJob) {
		try {
			TriggerKey triggerKey = getTriggerKey(scheduleJob.getJobId());

	         //表达式调度构建器
	         CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCron())
	         		.withMisfireHandlingInstructionDoNothing();

	         CronTrigger trigger = getCronTrigger(scheduler, scheduleJob.getJobId());
	         
	         //按新的cronExpression表达式重新构建trigger
	         trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
	         
	         //参数
	         trigger.getJobDataMap().put(ScheduleJob.JOB_PARAM_KEY, scheduleJob);
	         
	         scheduler.rescheduleJob(triggerKey, trigger);
	         
	         //暂停任务
	         if(scheduleJob.getStatus() == Constant.ScheduleStatus.PAUSE.getValue()){
	         	pauseJob(scheduler, scheduleJob.getJobId());
	         }
		} catch (SchedulerException e) {
			throw new TaotaoJobException("更新定时任务失败", e);
		}
	}
	
	/**
	 * 立即执行任务
	 * @param scheduler
	 * @param scheduleJob
	 */
	public static void run(Scheduler scheduler, ScheduleJob scheduleJob) {
		try {
			JobDataMap dataMap = new JobDataMap();
        	dataMap.put(ScheduleJob.JOB_PARAM_KEY, scheduleJob);
        	
            scheduler.triggerJob(getJobKey(scheduleJob.getJobId()), dataMap);
		} catch (SchedulerException e) {
			throw new TaotaoJobException("立即执行定时任务失败", e);
		}
	}
	
	/**
	 * 暂停定时任务
	 * @param scheduler
	 * @param jobId
	 */
	public static void pauseJob(Scheduler scheduler, Long jobId) {
		try {
			scheduler.pauseJob(getJobKey(jobId));
		} catch (SchedulerException e) {
			throw new TaotaoJobException("暂停定时任务失败", e);
		}
	}
	
	/**
	 * 恢复定时任务
	 * @param scheduler
	 * @param jobId
	 */
	public static void resumeJob(Scheduler scheduler, Long jobId) {
		try {
			scheduler.resumeJob(getJobKey(jobId));
		} catch (SchedulerException e) {
			throw new TaotaoJobException("暂停定时任务失败", e);
		}
	}
	
	/**
	 * 删除定时任务
	 * @param scheduler
	 * @param jobId
	 */
	public static void deleteJob(Scheduler scheduler, Long jobId) {
		try {
			scheduler.deleteJob(getJobKey(jobId));
		} catch (SchedulerException e) {
			throw new TaotaoJobException("删除定时任务失败", e);
		}
	}
}
