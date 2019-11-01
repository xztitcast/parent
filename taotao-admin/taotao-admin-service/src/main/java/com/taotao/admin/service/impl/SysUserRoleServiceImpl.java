package com.taotao.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.admin.entity.SysUserRole;
import com.taotao.admin.mapper.SysUserRoleMapper;
import com.taotao.admin.service.SysUserRoleService;

@Component("sysUserRoleService")
@Service(interfaceClass = SysUserRoleService.class)
public class SysUserRoleServiceImpl implements SysUserRoleService {
	
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;

	@Override
	@Transactional
	public void saveOrUpdate(Long userId, List<Long> roleIdList) {
		sysUserRoleMapper.deleteByUserId(userId);
		
		if(CollectionUtils.isEmpty(roleIdList)) return;
		
		roleIdList.forEach(id -> {
			SysUserRole sysUserRole = new SysUserRole();
			sysUserRole.setRoleId(id);
			sysUserRole.setUserId(userId);
			sysUserRoleMapper.insert(sysUserRole);
		});
	}

	@Override
	public List<Long> queryRoleIdList(Long userId) {
		return sysUserRoleMapper.queryRoleIdList(userId);
	}

	@Override
	public int deleteBatch(Long[] roleIds) {
		return sysUserRoleMapper.deleteBatch(roleIds);
	}

}
