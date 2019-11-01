package com.taotao.admin.web.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.admin.entity.SysRole;
import com.taotao.admin.service.SysRoleMenuService;
import com.taotao.admin.service.SysRoleService;
import com.taotao.common.entity.P;
import com.taotao.common.entity.R;
import com.taotao.common.utils.Constant;

@RestController("/sys/role")
public class SysRoleController extends BaseController {
	
	@Reference(interfaceClass = SysRoleService.class)
	private SysRoleService sysRoleService;
	
	@Reference(interfaceClass = SysRoleMenuService.class)
	private SysRoleMenuService sysRoleMenuServcie;

	@GetMapping("/list")
	@RequiresPermissions("sys:role:list")
	public R list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, 
			@RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
			@RequestParam(required = false) String roleName) {
		P<SysRole> p = sysRoleService.queryList(pageNum, pageSize, roleName, getUserId() == Constant.SUPER_ADMIN ? null :  getUserId());
		return R.ok(p);
	}
	
	@GetMapping("/select")
	@RequiresPermissions("sys:role:select")
	public R select() {
		List<SysRole> list = sysRoleService.queryList(getUserId() == Constant.SUPER_ADMIN ? null :  getUserId());
		return R.ok(list);
	}
	
	@GetMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public R info(@PathVariable("roleId") Long roleId) {
		SysRole sysRole = sysRoleService.queryById(roleId);
		List<Long> menuIdList = sysRoleMenuServcie.queryMenuIdList(roleId);
		sysRole.setMenuIdList(menuIdList);
		return R.ok(sysRole);
	}
	
	@PostMapping("/saveOrUpdate")
	@RequiresPermissions({"sys:role:update", "sys:role:save"})
	public R saveOrUpdate(@RequestBody SysRole role){
		role.setCreateUserId(getUserId());
		sysRoleService.saveOrUpdate(role);
		return R.ok();
	}
	
	@PostMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public R delete(@RequestBody Long[] roleIds){
		sysRoleService.deleteBatch(roleIds);
		return R.ok();
	}
}
