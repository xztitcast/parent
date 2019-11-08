package com.taotao.job.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "schedule_job")
public class ScheduleJob implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "job_id", nullable = false, columnDefinition = "bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id'")
	private Long jobId;

	@NotBlank(message = "bean名称不能为空!")
	@Column(name  = "bean_name", nullable = true, columnDefinition = "varchar(200) DEFAULT NULL COMMENT 'spring bean名称'")
    private String beanName;

	@NotBlank(message="方法名称不能为空")
	@Column(name = "method_name", nullable = true, columnDefinition = "varchar(100) DEFAULT NULL COMMENT '方法名'")
    private String methodName;

	@Column(name = "params", nullable = true, columnDefinition = "varchar(2000) DEFAULT NULL COMMENT '参数'")
    private String params;

	@NotBlank(message="cron表达式不能为空")
	@Column(name = "cron", nullable = true, columnDefinition = "varchar(100) DEFAULT NULL COMMENT 'cron表达式'")
    private String cron;

	@Column(name = "status", nullable = true, columnDefinition = "tinyint(4) DEFAULT '0' COMMENT '任务状态  0：正常  1：暂停'")
    private Integer status;

	@Column(name = "remark", nullable = true, columnDefinition = "varchar(255) DEFAULT NULL COMMENT '备注'")
    private String remark;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created", nullable = true, columnDefinition = "datetime DEFAULT NULL COMMENT '创建时间'")
    private Date created;
}
