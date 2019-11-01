package com.taotao.admin.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.admin.service.ShiroService;

@Configuration
public class Config {
	
	@Reference(interfaceClass = ShiroService.class)
	private ShiroService shiroService;

	@Bean
	public ShiroService shiroService() {
		return shiroService;
	}
}
