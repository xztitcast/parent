package com.taotao.admin.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

@EnableDubbo
@SpringBootApplication
@MapperScan(basePackages = {"com.taotao.admin.mapper"})
public class TaotaoAdminServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaotaoAdminServiceApplication.class, args);
	}

}
