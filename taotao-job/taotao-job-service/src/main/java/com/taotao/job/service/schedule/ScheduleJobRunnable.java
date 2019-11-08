package com.taotao.job.service.schedule;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.ReflectionUtils;

import com.taotao.common.exce.TaotaoJobException;
import com.taotao.common.utils.SpringContextUtil;

import lombok.NoArgsConstructor;

/**
 * 任务线程
 * 在生产线上最好将此线程的日志加上
 * 此线程使用java反射来执行对应的定时任务
 * 反射需要的类需要从ApplicationContext容器获取
 * 方法名需要new线程的时候就给定或者时对应的参数
 * @author xiangzuotao
 * @2019年11月7日
 */
@NoArgsConstructor
public class ScheduleJobRunnable implements Runnable {
	
	private Object target;
	
	private Method method;
	
	private String params;
	
	public ScheduleJobRunnable(String beanName, String methodName, String params) throws NoSuchMethodException, SecurityException {
		this.target = SpringContextUtil.getBean(beanName);
		this.params = params;
		if(StringUtils.isBlank(params)) {
			this.method = this.target.getClass().getDeclaredMethod(methodName);
		}else {
			this.method = this.target.getClass().getDeclaredMethod(methodName, String.class);
		}
	}

	@Override
	public void run() {
		try {
			ReflectionUtils.makeAccessible(method);
			if(StringUtils.isBlank(params)) {
				this.method.invoke(target);
			}else {
				this.method.invoke(target, params);
			}
		} catch (Exception e) {
			throw new TaotaoJobException("执行定时任务失败");
		}

	}

}
