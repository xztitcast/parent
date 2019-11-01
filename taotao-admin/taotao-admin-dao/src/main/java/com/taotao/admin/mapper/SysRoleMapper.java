package com.taotao.admin.mapper;

import com.taotao.admin.entity.SysRole;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysRoleMapper {

    int deleteById(Long roleId);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    List<SysRole> selectAll();

    SysRole selectById(Long roleId);

    int updateBySelective(SysRole record);

    int updateById(SysRole record);
    
    List<SysRole> queryList(@Param("roleName") String roleName, @Param("createUserId") Long createUserId);
    
    int deleleByIds(Long[] roleIds);
    
    List<Long> queryRoleIdList(Long createUserId);
}