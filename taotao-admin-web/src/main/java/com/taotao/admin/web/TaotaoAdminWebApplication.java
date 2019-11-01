package com.taotao.admin.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

@EnableDubbo
@SpringBootApplication
public class TaotaoAdminWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaotaoAdminWebApplication.class, args);
	}

}
