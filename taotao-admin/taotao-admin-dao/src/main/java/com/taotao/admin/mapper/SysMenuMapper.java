package com.taotao.admin.mapper;

import com.taotao.admin.entity.SysMenu;
import java.util.List;

public interface SysMenuMapper {

    int deleteById(Long menuId);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    List<SysMenu> selectAll();

    SysMenu selectById(Long menuId);

    int updateBySelective(SysMenu record);

    int updateById(SysMenu record);

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     * @return
     */
	List<SysMenu> queryListParentId(Long parentId);
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<SysMenu> queryNotButtonList();
}