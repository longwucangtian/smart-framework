package org.smart4j.framework.helper;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.proxy.AspectProxy;
import org.smart4j.framework.proxy.Proxy;
import org.smart4j.framework.proxy.ProxyManager;

/**
 * 方法拦截助手类
 * @author luyc
 *
 */
public final class AopHelper {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);
	
	static{
		try{
			Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
			Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
			for(Map.Entry<Class<?>, List<Proxy>> targetEntity : targetMap.entrySet()){
				Class<?> targetClass = targetEntity.getKey();
				List<Proxy> proxyList = targetEntity.getValue();
				Object proxy = ProxyManager.createProxy(targetClass, proxyList);
				BeanHelper.setBean(targetClass, proxy);
			}
		}catch(Exception e){
			LOGGER.error("aop failure",e);
		}
		
		
	}
	/**
	 * 根据Aspect注解获取相关类
	 * @param aspect
	 * @return
	 * @throws Exception
	 */
	private static Set<Class<?>> createTargetClassSet(Aspect aspect)throws Exception{
		Set<Class<?>> targetClassSet = new HashSet<>();
		Class<? extends Annotation> annotation = aspect.value();
		if(annotation != null && !annotation.equals(Aspect.class)){
			targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
		}
		return targetClassSet;
	}
	
	/**
	 * 获取代理类和目标类集合之间的映射Map
	 * @return
	 * @throws Exception
	 */
	private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception{
		Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<>();
		Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);   //找到所有继承了AspectProxy的类
		for(Class<?> proxyClass : proxyClassSet){
			if(proxyClass.isAnnotationPresent(Aspect.class)){
				Aspect aspect = proxyClass.getAnnotation(Aspect.class);
				Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
				proxyMap.put(proxyClass, targetClassSet);
			}
		}
		return proxyMap;
	}
	
	/**
	 * 根据代理类和目标对象的映射获取目标类和代理对象链的映射关系
	 * @param proxyMap
	 * @return
	 * @throws Exception
	 */
	private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception{
		Map<Class<?>, List<Proxy>> targetMap = new HashMap<>();
		for(Map.Entry<Class<?>, Set<Class<?>>> proxyEntity : proxyMap.entrySet()){
			Class<?> proxyClass = proxyEntity.getKey();
			Set<Class<?>> targetClassSet = proxyEntity.getValue();
			for(Class<?> targetClass : targetClassSet){
				Proxy proxy = (Proxy) proxyClass.newInstance();
				if(targetMap.containsKey(targetClass)){
					targetMap.get(targetClass).add(proxy);
				}else{
					List<Proxy> proxyList = new ArrayList<>();
					proxyList.add(proxy);
					targetMap.put(targetClass, proxyList);
				}
			}
		}
		return targetMap;
	}
}
