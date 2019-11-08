package com.taotao.job.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.taotao.job.entity.ScheduleJob;

public interface ScheduleJobDao extends JpaRepository<ScheduleJob, Long>, JpaSpecificationExecutor<ScheduleJob> {

}
