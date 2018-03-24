package org.smart4j.framework.util;

import org.apache.commons.lang3.StringUtils;

import com.mysql.fabric.xmlrpc.base.Value;

public final class CastUtil {
	
	public static String castString(Object obj){
		return castString(obj,"");
	}
	
	public static String castString(Object obj, String defaultString){
		return obj == null ? defaultString : String.valueOf(obj);
	}
	
	public static double castDouble(Object obj, double defaultDouble){
		double value = defaultDouble;
		if(obj != null){
			String strValue = castString(obj);
			if(StringUtils.isNotEmpty(strValue)){
				try{
					value = Double.parseDouble(strValue);
				}catch(NumberFormatException e){
					value = defaultDouble;
				}
			}
		}
		return value;
	}
	
	public static double castDouble(Object obj){
		return castDouble(obj,0);
	}
	
	public static long castLong(Object obj,long defaultValue){
		long value = defaultValue;
		if(obj != null){
			String strValue = castString(obj);
			if(StringUtils.isNotEmpty(strValue)){
				try{
					value = Long.parseLong(strValue);
				}catch(NumberFormatException e){
					value = defaultValue;
				}
			}
		}
		return value;
	}
	
	public static long castLong(Object obj){
		return castLong(obj,0);
	}
	
	public static int castInt(Object obj,int defaultValue){
		int value = defaultValue;
		if(obj != null){
			String strValue = castString(obj);
			if(StringUtils.isNotEmpty(strValue)){
				try{
					value = Integer.parseInt(strValue);
				}catch(NumberFormatException e){
					value = defaultValue;
				}
			}
		}
		return value;
	}
	
	public static int castInt(Object obj){
		return castInt(obj,0);
	}
	
	public static boolean castBoolean(Object obj,boolean defaultValue){
		boolean value = defaultValue;
		if(obj != null){
			value = Boolean.parseBoolean(castString(obj));
		}
		return value;
	}
	
	public static boolean castBoolean(Object obj){
		return castBoolean(obj,false);
	}
}
