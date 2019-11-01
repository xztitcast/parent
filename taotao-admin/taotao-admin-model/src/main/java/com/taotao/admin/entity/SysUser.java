package com.taotao.admin.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysUser implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long userId;

    private String username;

    private String password;

    private String salt;

    private String email;

    private String mobile;

    private Boolean status;

    private Long createUserId;

    private Date createTime;
    
    private List<Long> roleIdList;

}