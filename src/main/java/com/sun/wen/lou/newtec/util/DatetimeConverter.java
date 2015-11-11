package com.sun.wen.lou.newtec.util;

import org.apache.commons.beanutils.Converter;
/**
 * 
 * 类 名: DatetimeConverter<br/>
 * 描 述: 描述类完成的主要功能<br/>
 * 版 本：<br/>
 *
 * 历 史: (版本) 作者 时间 注释 <br/>
 */
public class DatetimeConverter implements Converter {
    /**
     * Date转换器.
     * @param type Class
     * @param value Object
     * return Double Object.
     */
    public Object convert(Class type,Object value){
        if(value == null){
            return null;
        }else {
        	String format;
			return new String(value.toString());
        }
    }
}
