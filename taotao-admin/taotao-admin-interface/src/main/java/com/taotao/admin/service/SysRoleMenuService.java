package com.taotao.admin.service;

import java.util.List;

public interface SysRoleMenuService {

	default void saveOrUpdate(Long roleId, List<Long> menuIdList) {};
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	default List<Long> queryMenuIdList(Long roleId) {return null;};

	/**
	 * 根据角色ID数组，批量删除
	 */
	default int deleteBatch(Long... roleIds) {return -1;};
}
