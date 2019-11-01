package com.taotao.admin.service;

import java.util.List;

import com.taotao.admin.entity.SysUser;
import com.taotao.common.entity.P;

public interface SysUserService {
	
	/**
	 * 查询用户列表
	 * @param pageNum
	 * @param pageSize
	 * @param userId
	 * @return
	 */
	default P<SysUser> queryList(int pageNum, int pageSize, Long userId) {return null;};

	/**
	 * 查询所有用户权限
	 * @param userId
	 * @return
	 */
	default List<String> queryAllPerms(Long userId) {return null;};
	
	/**
	 * 查询用户的所有菜单ID
	 */
	default List<Long> queryAllMenuId(Long userId) {return null;};

	/**
	 * 根据用户名，查询系统用户
	 */
	default SysUser queryByUserName(String username) {return null;};
	
	default SysUser queryByUserId(Long userId) {return null;};

	/**
	 * 保存用户
	 */
	default void saveOrUpdate(SysUser user) {};
	
	/**
	 * 删除用户
	 */
	default void deleteBatch(Long... userIds) {};

	/**
	 * 修改密码
	 * @param userId       用户ID
	 * @param password     原密码
	 * @param newPassword  新密码
	 */
	default boolean updatePassword(Long userId, String password, String newPassword) {return false;};
}
