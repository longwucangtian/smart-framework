package org.smart4j.framework.helper;

import java.lang.reflect.Field;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.util.CollectionUtil;
import org.smart4j.framework.util.ReflectionUtil;

public final class IocHelper {
	static{
		//获取所有的Bean类与Bean实例之间的映射关系
		Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
		if(CollectionUtil.isNotEmpty(beanMap)){
			for(Map.Entry<Class<?>, Object> beanEntity : beanMap.entrySet()){
				//从map中获取bean类和bean实例
				Class<?> beanClass = beanEntity.getKey();
				Object beanInstance = beanEntity.getValue();
				//获取Bean类的所有成员变量
				Field[]beanFields = beanClass.getDeclaredFields();
				if(ArrayUtils.isNotEmpty(beanFields)){
					//遍历Field
					for(Field beanField : beanFields){
						//判断当前的Field是否带有Inject注解
						if(beanField.isAnnotationPresent(Inject.class)){
							Class<?> beanFieldClass = beanField.getType();
							Object beanFieldInstance = beanMap.get(beanFieldClass);
							if(beanFieldInstance != null){
								//通过反射初始化BeanField的值
								ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
							}
							
							
						}
					}
				}
				
				
				
			}
		}
		
		
		
		
	}
}
