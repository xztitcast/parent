package com.taotao.job.service;

import com.taotao.common.entity.P;
import com.taotao.job.entity.ScheduleJob;

/**
 * 定时任务接口
 * @author xiangzuotao
 * @2019年11月7日
 */
public interface ScheduleJobService {

	/**
	 * 获取所有job列表
	 * @param pageNum 页码
	 * @param pageSize 页量
	 * @param beanName bean名称
	 * @return
	 */
	default P<ScheduleJob> queryJobList(int pageNum, int pageSize, String beanName) {return null;}
	
	/**
	 * 根据jobId获取定时任务
	 * @param jobId
	 * @return
	 */
	default ScheduleJob queryScheduleJob(Long jobId) {return null;}
	
	/**
	 * 保存更新
	 * @param scheduleJob
	 */
	default void saveOrUpdate(ScheduleJob scheduleJob) {}
	
	/**
	 * 批量删除
	 * @param jobIds
	 */
	default void deleteBatch(Long[] jobIds) {}
	
	/***
	 * 批量更新
	 * @param jobIds
	 * @param status
	 */
	default void updateBatch(Long[] jobIds, int status) {}
	
	/**
	 * 立即执行
	 * @param jobIds
	 */
	default void run(Long[] jobIds) {}
	
	/**
	 * 暂停执行
	 * @param jobIds
	 */
	default void pause(Long[] jobIds) {}
	
	/**
	 * 恢复执行
	 * @param jobIds
	 */
	default void resume(Long[] jobIds) {}
}
