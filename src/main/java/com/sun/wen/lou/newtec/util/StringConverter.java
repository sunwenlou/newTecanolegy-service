package com.sun.wen.lou.newtec.util;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;

public class StringConverter	implements Converter {
    /**
     * String 转换器.
     * @param type Class
     * @param value Object
     * return Double Object.
     */
    public Object convert(Class type,Object value){
    	
        if(value == null){
            return "";
        }else {
//        	return StringUtils.trimToEmpty(value.toString());
        	return value.toString();
        }
        
    }
}