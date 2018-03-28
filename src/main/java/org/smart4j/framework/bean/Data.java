package org.smart4j.framework.bean;

public class Data {
	/**
	 * 模型数据
	 */
	private Object model;
	
	private Data(Object model){
		this.model = model;
	}
	
	public Object getModel(){
		return model;
	}
}
