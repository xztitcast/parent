package com.taotao.admin.form;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm implements Serializable{

	private static final long serialVersionUID = 1L;

	private String username;
	
	private String password;
	
	private String captcha;
	
	private String uuid;
}
