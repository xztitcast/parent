package com.taotao.admin.mapper;

import com.taotao.admin.entity.SysUser;
import java.util.List;

public interface SysUserMapper {
	
    int deleteById(Long userId);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    List<SysUser> selectAll();

    SysUser selectById(Long userId);

    int updateBySelective(SysUser record);

    int updateById(SysUser record);

    /**
               * 根据用户Id查询
     * @param userId
     * @return
     */
	List<SysUser> queryListByUserId(Long userId);

	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);
	
	/**
	 * 根据用户名，查询系统用户
	 */
	SysUser queryByUserName(String username);

	void deleteByIds(Long[] userIds);
}