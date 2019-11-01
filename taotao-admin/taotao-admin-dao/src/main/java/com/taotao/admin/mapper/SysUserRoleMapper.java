package com.taotao.admin.mapper;

import com.taotao.admin.entity.SysUserRole;
import java.util.List;

public interface SysUserRoleMapper {

    int deleteById(Long id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    List<SysUserRole> selectAll();

    SysUserRole selectById(Long id);
    
    int updateBySelective(SysUserRole record);

    int updateById(SysUserRole record);
    
    int deleteByUserId(Long userId);

	List<Long> queryRoleIdList(Long userId);

	int deleteBatch(Long[] roleIds);
}