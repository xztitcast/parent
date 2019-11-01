package com.taotao.admin.service;

import java.util.List;

import com.taotao.admin.entity.SysMenu;

public interface SysMenuService {
	
	default void saveOrUpdate(SysMenu menu) {}

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 * @param menuIdList  用户菜单ID
	 */
	default List<SysMenu> queryListParentId(Long parentId, List<Long> menuIdList){return null;};

	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	default List<SysMenu> queryListParentId(Long parentId){return null;};
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	default List<SysMenu> queryNotButtonList(){return null;};
	
	/**
	 * 获取用户菜单列表
	 */
	default List<SysMenu> getUserMenuList(Long userId){return null;};

	/**
	 * 删除
	 */
	default void delete(Long menuId) {};
	
	default SysMenu queryById(Long menuId) {return null;};
}
