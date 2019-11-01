package com.taotao.admin.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysMenu implements Serializable {
    
	private static final long serialVersionUID = 1L;

	private Long menuId;

    private Long parentId;
    
    private String parentName;

    private String name;

    private String url;

    private String perms;

    private Integer type;

    private String icon;

    private Integer orderNum;
    
    private Boolean open;
    
    private List<SysMenu> list;

}