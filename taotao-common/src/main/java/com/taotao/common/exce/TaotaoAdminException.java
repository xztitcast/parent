package com.taotao.common.exce;

public class TaotaoAdminException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TaotaoAdminException(String arg0) {
		super(arg0);
	}

	public TaotaoAdminException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
