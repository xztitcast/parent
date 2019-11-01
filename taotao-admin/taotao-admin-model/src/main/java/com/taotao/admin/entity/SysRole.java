package com.taotao.admin.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysRole implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long roleId;

    private String roleName;

    private String remark;

    private Long createUserId;

    private Date createTime;
    
    private List<Long> menuIdList;

}