package com.sun.wen.lou.newtec.util;

import java.io.Writer;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtils {
	private static Log log = LogFactory.getLog(JsonUtils.class);
	private static ObjectMapper mapper = null;

	static {
		mapper = new ObjectMapper();
		// 设置输出全部属性到JSON字符串
		mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
		// json字符串中存在但Java对象没有的属性将被忽略
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		// 设置json的日期解析格式
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		// 不序列化属性值为null或为空的属性
		mapper.setSerializationInclusion(Include.NON_EMPTY);
	}

	/**
	 * 
	 * <ol>
	 * <li>将给定的json字段串反序列化为指定的类型，并返回</li>
	 * </ol>
	 * 
	 * @version 1.0
	 * @since 1.0
	 * @param json
	 *            json字符串
	 * @param classes
	 *            标准的JavaBean类型或者集合类型
	 * @param elementClasses
	 *            如果classes为一个集合类型，通过此参数可以指定集合元素的类型（标准的JavaBean类型），
	 *            默认类型为LinkedHashMap
	 * 
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T parseJson(String json, Class<T> classes, Class<?>... elementClasses) throws Exception {
		if (json == null || classes == null)
			return null;
		try {
			if (elementClasses == null || elementClasses.length == 0)
				return (T) mapper.readValue(json, classes);
			return (T) mapper.readValue(json, mapper.getTypeFactory()
					.constructParametricType(classes, elementClasses));
		} catch (Exception e) {
			log.error("反序列化对象异常：", e);
			throw new Exception("反序列化对象异常：" + e.getMessage(), e);
		}
	}

	/**
	 * 
	 * <ol>
	 * <li>将给定Object序列化为json字段串，并返回</li>
	 * </ol>
	 * 
	 * @version 1.0
	 * @since 1.0
	 * @param object
	 *            标准的JavaBean
	 * @throws Exception
	 */
	public static String getJsonForString(Object object) throws Exception {
		if (object == null)
			return null;
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			log.error("序列化对象异常：", e);
			throw new Exception("序列化对象异常：" + e.getMessage(), e);
		}
	}

	/**
	 * 
	 * <ol>
	 * <li>将给定Object序列化为json字段串，写入到指定输出流</li>
	 * </ol>
	 * 
	 * @version 1.0
	 * @since 1.0
	 * @param writer
	 *            字符输出流
	 * @param object
	 *            标准的JavaBean
	 * @throws Exception
	 */
	public static void responseJson(Writer writer, Object object)
			throws Exception {
		if (object == null || writer == null)
			return;
		try {
			mapper.writeValue(writer, object);
			writer.flush();
		} catch (Exception e) {
			log.error("输出json异常：", e);
			throw new RuntimeException("输出json异常：" + e.getMessage(), e);
		} finally {
			writer.close();
		}
	}
}
