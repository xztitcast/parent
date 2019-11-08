package com.taotao.job.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.taotao.common.utils.SpringContextUtil;

@EnableDubbo
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.taotao.job.dao"})
@EntityScan(basePackages = {"com.taotao.job.entity"})
public class TaotaoJobServiceApplication {

	public static void main(String[] args) {
		ApplicationContext run = SpringApplication.run(TaotaoJobServiceApplication.class, args);
		SpringContextUtil.setApplicationContext(run);
	}

}
