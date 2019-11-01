package com.taotao.admin.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.admin.entity.SysMenu;
import com.taotao.admin.mapper.SysMenuMapper;
import com.taotao.admin.service.SysMenuService;
import com.taotao.admin.service.SysUserService;
import com.taotao.common.utils.Constant;

@Component
@Service(interfaceClass = SysMenuService.class)
public class SysMenuServiceImpl implements SysMenuService {
	
	@Autowired
	private SysMenuMapper sysMenuMapper;
	
	@Autowired
	private SysUserService sysUserService;

	@Override
	public void saveOrUpdate(SysMenu menu) {
		if(menu.getMenuId() == null) {
			sysMenuMapper.insert(menu);
		}else {
			sysMenuMapper.updateById(menu);
		}
	}

	@Override
	public List<SysMenu> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<SysMenu> menuList = queryListParentId(parentId);
		if(menuIdList == null) {
			return menuList;
		}
		return menuList.stream().filter(m -> menuIdList.contains(m.getMenuId())).collect(Collectors.toList());
		
	}

	@Override
	public List<SysMenu> queryListParentId(Long parentId) {
		return sysMenuMapper.queryListParentId(parentId);
	}

	@Override
	public List<SysMenu> queryNotButtonList() {
		return sysMenuMapper.queryNotButtonList();
	}

	@Override
	public List<SysMenu> getUserMenuList(Long userId) {
		//系统管理员，拥有最高权限
		if(userId == Constant.SUPER_ADMIN){
			return getAllMenuList(null);
		}
		
		//用户菜单列表
		List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
		return getAllMenuList(menuIdList);
	}

	@Override
	public void delete(Long menuId) {
		// TODO Auto-generated method stub
		SysMenuService.super.delete(menuId);
	}
	
	@Override
	public SysMenu queryById(Long menuId) {
		return sysMenuMapper.selectById(menuId);
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<SysMenu> getAllMenuList(List<Long> menuIdList){
		//查询根菜单列表
		List<SysMenu> menuList = queryListParentId(0L, menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);
		
		return menuList;
	}

	/**
	 * 递归
	 */
	private List<SysMenu> getMenuTreeList(List<SysMenu> menuList, List<Long> menuIdList){
		return menuList.stream().map(m -> {
			if(m.getType() == Constant.MenuType.CATALOG.getValue()) {
				m.setList(getMenuTreeList(queryListParentId(m.getMenuId(), menuIdList), menuIdList));
			}
			return m;
		}).collect(Collectors.toList());
		
	}

}
