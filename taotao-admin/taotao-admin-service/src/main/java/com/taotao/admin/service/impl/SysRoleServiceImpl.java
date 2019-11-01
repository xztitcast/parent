package com.taotao.admin.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.taotao.admin.entity.SysRole;
import com.taotao.admin.mapper.SysRoleMapper;
import com.taotao.admin.service.SysRoleMenuService;
import com.taotao.admin.service.SysRoleService;
import com.taotao.admin.service.SysUserRoleService;
import com.taotao.admin.service.SysUserService;
import com.taotao.common.entity.P;
import com.taotao.common.exce.TaotaoAdminException;
import com.taotao.common.utils.Constant;

@Component("sysRoleService")
@Service(interfaceClass = SysRoleService.class)
public class SysRoleServiceImpl implements SysRoleService {
	
	@Autowired
	private SysRoleMapper sysRoleMapper;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	
	@Autowired
	private SysUserRoleService sysUserRoleService;
	
	@Override
	@Transactional
	public void saveOrUpdate(SysRole role) {
		if(role.getRoleId() == null) {
			sysRoleMapper.insert(role);
		}else {
			sysRoleMapper.updateById(role);
		}
		
		checkPrems(role);
		
		sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
		
	}

	@Override
	@Transactional
	public void deleteBatch(Long... roleIds) {
		sysRoleMapper.deleleByIds(roleIds);
		sysRoleMenuService.deleteBatch(roleIds);
		sysUserRoleService.deleteBatch(roleIds);
	}
	
	@Override
	public List<SysRole> queryList(Long createUserId) {
		return sysRoleMapper.queryList(null, createUserId);
	}

	@Override
	public List<Long> queryRoleIdList(Long createUserId) {
		return queryList(createUserId).stream().map(r -> r.getRoleId()).collect(Collectors.toList());
	}

	@Override
	public P<SysRole> queryList(int pageNum, int pageSize, String roleName, Long createUserId) {
		Page<SysRole> page = PageHelper.startPage(pageNum, pageSize);
		sysRoleMapper.queryList(roleName, createUserId);
		return new P<>(page.getTotal(), page.getResult());
	}
	
	@Override
	public SysRole queryById(Long roleId) {
		return sysRoleMapper.selectById(roleId);
	}

	private void checkPrems(SysRole role) {
		if(role.getCreateUserId() == Constant.SUPER_ADMIN) {
			return;
		}
		List<Long> menuIdList = sysUserService.queryAllMenuId(role.getCreateUserId());
		if(!menuIdList.containsAll(role.getMenuIdList())) {
			throw new TaotaoAdminException("新增角色的权限，已超出你的权限范围");
		}
	}
}
