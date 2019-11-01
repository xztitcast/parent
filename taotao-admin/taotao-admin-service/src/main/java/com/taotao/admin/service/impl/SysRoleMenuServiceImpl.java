package com.taotao.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.admin.entity.SysRoleMenu;
import com.taotao.admin.mapper.SysRoleMenuMapper;
import com.taotao.admin.service.SysRoleMenuService;

@Component("sysRoleMenuService")
@Service(interfaceClass = SysRoleMenuService.class)
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
	
	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;

	@Override
	@Transactional
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
		deleteBatch(roleId);
		
		if(CollectionUtils.isEmpty(menuIdList)) return;
		
		menuIdList.forEach(menuId -> {
			SysRoleMenu sysRoleMenu = new SysRoleMenu();
			sysRoleMenu.setMenuId(menuId);
			sysRoleMenu.setRoleId(roleId);
			sysRoleMenuMapper.insert(sysRoleMenu);
		});
	}

	@Override
	public List<Long> queryMenuIdList(Long roleId) {
		return sysRoleMenuMapper.queryMenuIdList(roleId);
	}

	@Override
	@Transactional
	public int deleteBatch(Long... roleIds) {
		return sysRoleMenuMapper.deleteBatch(roleIds);
	}

}
