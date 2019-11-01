package com.taotao.admin.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysUserRole implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

    private Long userId;

    private Long roleId;

}