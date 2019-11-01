package com.taotao.admin.service;

import java.util.Set;

import com.taotao.admin.entity.SysUser;

public interface ShiroService {

	/**
	 * 生成token
	 * @return
	 */
	default String createToken(SysUser adminUser) {return null;};
	
	/**
	 * 获取用户信息
	 * @param token
	 * @return
	 */
	default SysUser getByToken(String token) {return null;};
	
	/**
	 * 删除
	 * @param token
	 * @return
	 */
	default boolean remove(String token) {return false;};
	
	default Set<String> getUserPermissions(long userId){return null;};
}
