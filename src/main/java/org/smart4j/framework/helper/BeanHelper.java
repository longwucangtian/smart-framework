package org.smart4j.framework.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.smart4j.framework.util.ReflectionUtil;

public final class BeanHelper {
	/**
	 * bean映射，用于存放Bean类与Bean实例的映射类
	 */
	private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<>();
	
	static {
		Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
		for(Class<?> beanClass : beanClassSet){
			Object obj = ReflectionUtil.newInstance(beanClass);
			BEAN_MAP.put(beanClass, obj);
		}
	}
	/**
	 * 获取bean映射	
	 */
	public static Map<Class<?>, Object> getBeanMap(){
		return BEAN_MAP;
	}
	
	/**
	 * 获取bean实例
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<?> cls){
		if(!BEAN_MAP.containsKey(cls)){
			throw new RuntimeException("can not get bean by class:" + cls);
		}
		return (T) BEAN_MAP.get(cls);
	}
	
	/**
	 * 设置Bean实例
	 */
	public static void setBean(Class<?> cls, Object obj){
		BEAN_MAP.put(cls, obj);
	}
}
