package com.taotao.admin.service;

import java.util.ArrayList;
import java.util.List;

public interface SysUserRoleService {

    default void saveOrUpdate(Long userId, List<Long> roleIdList) {};
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	default List<Long> queryRoleIdList(Long userId) {return new ArrayList<>();};

	/**
	 * 根据角色ID数组，批量删除
	 */
	default int deleteBatch(Long[] roleIds) {return -1;};
}
