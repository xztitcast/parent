package com.taotao.admin.web.controller;

import org.apache.shiro.SecurityUtils;

import com.taotao.admin.entity.SysUser;

public abstract class BaseController {

	protected SysUser getUser() {
		return (SysUser) SecurityUtils.getSubject().getPrincipal();
	}
	
	protected Long getUserId() {
		return getUser().getUserId();
	}
}
