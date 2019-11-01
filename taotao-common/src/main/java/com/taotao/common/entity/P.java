package com.taotao.common.entity;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页
 * @author xiangzuotao
 * @2019年10月31日
 * @param <T>
 */
@Getter
@Setter
@AllArgsConstructor
public class P<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long total;
	
	private List<T> rows;

}
