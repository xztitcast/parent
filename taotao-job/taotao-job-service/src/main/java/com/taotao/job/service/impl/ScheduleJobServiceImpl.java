package com.taotao.job.service.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.common.entity.P;
import com.taotao.common.utils.Constant;
import com.taotao.job.dao.ScheduleJobDao;
import com.taotao.job.entity.ScheduleJob;
import com.taotao.job.service.ScheduleJobService;
import com.taotao.job.service.schedule.AbstractSchedule;

/**
 * 业务实现
 * @author xiangzuotao
 * @2019年11月7日
 */
@Component("scheduleJobService")
@Service(interfaceClass = ScheduleJobService.class)
public class ScheduleJobServiceImpl implements ScheduleJobService {
	
	@Autowired
	private ScheduleJobDao scheduleJobDao;
	
	@Autowired
	private Scheduler scheduler;
	
	@PostConstruct
	private void init() {
		List<ScheduleJob> jobList = scheduleJobDao.findAll();
		jobList.forEach(job -> {
			CronTrigger cronTrigger = AbstractSchedule.getCronTrigger(scheduler, job.getJobId());
			if(cronTrigger == null) {
				AbstractSchedule.create(scheduler, job);
			}else {
				AbstractSchedule.update(scheduler, job);
			}
		});
	}

	@Override
	public P<ScheduleJob> queryJobList(int pageNum, int pageSize, String beanName) {
		Pageable pageable = PageRequest.of(pageNum-1, pageSize, Sort.Direction.DESC, "created");
		Page<ScheduleJob> page = scheduleJobDao.findAll((r, q, cb) -> {
			return cb.equal(r.get("beanName").as(String.class), beanName);
		}, pageable);
		return new P<>(page.getTotalElements(), page.getContent());
	}

	@Override
	public ScheduleJob queryScheduleJob(Long jobId) {
		return scheduleJobDao.findById(jobId).orElse(null);
	}

	@Override
	@Transactional
	public void saveOrUpdate(ScheduleJob scheduleJob) {
		scheduleJobDao.saveAndFlush(scheduleJob);
		if(scheduleJob.getJobId() == null || scheduleJob.getJobId() <= 0) {
			AbstractSchedule.create(scheduler, scheduleJob);
		}else {
			AbstractSchedule.update(scheduler, scheduleJob);
		}
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] jobIds) {
		for(Long jobId : jobIds) {
			scheduleJobDao.deleteById(jobId);
			AbstractSchedule.deleteJob(scheduler, jobId);
		}
	}

	@Override
	public void updateBatch(Long[] jobIds, int status) {
		for(Long jobId : jobIds) {
			Optional<ScheduleJob> optional = scheduleJobDao.findById(jobId);
			optional.ifPresent(job -> job.setStatus(status));
		}
	}

	@Override
	public void run(Long[] jobIds) {
		for(Long jobId : jobIds) {
			Optional<ScheduleJob> optional = scheduleJobDao.findById(jobId);
			optional.ifPresent(job -> AbstractSchedule.run(scheduler, job));
		}
	}

	@Override  
	public void pause(Long[] jobIds) {
		for(Long jobId : jobIds) {
			AbstractSchedule.pauseJob(scheduler, jobId);
		}
		updateBatch(jobIds, Constant.ScheduleStatus.PAUSE.getValue());
	}

	@Override
	public void resume(Long[] jobIds) {
		for(Long jobId : jobIds) {
			AbstractSchedule.resumeJob(scheduler, jobId);
		}
		updateBatch(jobIds, Constant.ScheduleStatus.NORMAL.getValue());
	}

}
