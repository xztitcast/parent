package com.taotao.admin.service;

import java.util.ArrayList;
import java.util.List;

import com.taotao.admin.entity.SysRole;
import com.taotao.common.entity.P;

public interface SysRoleService {

	default void saveOrUpdate(SysRole role) {};

	default void deleteBatch(Long... roleIds) {};
	
	/**
	 * 查询用户创建的角色ID列表
	 */
	default List<Long> queryRoleIdList(Long createUserId) {return new ArrayList<>();};
	
	default P<SysRole> queryList(int pageNum, int pageSize, String roleName, Long createUserId) {return null;}
	
	default List<SysRole> queryList(Long createUserId) {return new ArrayList<>();}

	default SysRole queryById(Long roleId) {return null;}
}
