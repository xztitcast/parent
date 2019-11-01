package com.taotao.admin.mapper;

import com.taotao.admin.entity.SysRoleMenu;
import java.util.List;

public interface SysRoleMenuMapper {

    int deleteById(Long id);

    int insert(SysRoleMenu record);

    int insertSelective(SysRoleMenu record);

    List<SysRoleMenu> selectAll();

    SysRoleMenu selectById(Long id);

    int updateBySelective(SysRoleMenu record);

    int updateById(SysRoleMenu record);

	List<Long> queryMenuIdList(Long roleId);

	int deleteBatch(Long[] roleIds);
}