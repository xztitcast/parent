package com.taotao.common.exce;

public class TaotaoJobException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TaotaoJobException(String arg0) {
		super(arg0);
	}

	public TaotaoJobException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
