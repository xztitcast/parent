package com.taotao.admin.web.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.admin.entity.SysUser;
import com.taotao.admin.service.SysUserRoleService;
import com.taotao.admin.service.SysUserService;
import com.taotao.common.entity.P;
import com.taotao.common.entity.R;

@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {

	@Reference(interfaceClass = SysUserService.class)
	private SysUserService sysUserService;
	
	@Reference(interfaceClass = SysUserRoleService.class)
	private SysUserRoleService sysUserRoleService;
	
	@GetMapping("/list")
	@RequiresPermissions("sys:user:list")
	public R list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, 
			@RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
		P<SysUser> list = sysUserService.queryList(pageNum, pageSize, getUserId());
		return R.ok(list);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@GetMapping("/info")
	public R info(){
		return R.ok().put("user", getUser());
	}
	
	@GetMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public R info(@PathVariable("userId") Long userId) {
		SysUser user = sysUserService.queryByUserId(userId);
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		return R.ok(user);
	}
	
	@PostMapping("/save")
	@RequiresPermissions("sys:user:save")
	public R save(SysUser user) {
		user.setCreateTime(new Date());
		user.setCreateUserId(getUserId());
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
		user.setSalt(salt);
		sysUserService.saveOrUpdate(user);
		return R.ok();
	}
	
	/**
	 * 删除用户
	 */
	@PostMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public R delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return R.error("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return R.error("当前用户不能删除");
		}
		
		sysUserService.deleteBatch(userIds);
		
		return R.ok();
	}
	
}
