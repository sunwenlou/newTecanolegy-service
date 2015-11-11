package com.sun.wen.lou.newtec.hibernate.validate;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class BeanValidatorImpl implements BeanValidator {

	/**
	 * 校验对象
	 */
	public List<Map<String, String>> validatorObject(Validator validator, Object object) {
		Set<ConstraintViolation<Object>> set = validator.validate(object);
		if (set != null && set.size() > 0) {
			List<Map<String, String>> errorMessages = new ArrayList<Map<String, String>>();
			for (ConstraintViolation<Object> violation : set) {
				Map<String, String> errorMap = new HashMap<String, String>();
				System.out.println(violation.getPropertyPath());
				System.out.println(violation.getMessage());
				errorMap.put("key", violation.getPropertyPath().toString());
				errorMap.put("value",
						violation.getInvalidValue() != null ? violation
								.getInvalidValue().toString() : "");
				errorMap.put("message", violation.getMessage());
				errorMessages.add(errorMap);
			}
			return errorMessages;
		}
		return null;
	}

	/**
	 * 校验对象,输出为JSON格式
	 */
	public String validatorObjectJson(Validator validator, Object object) {
		List<Map<String, String>> errorMessages = this.validatorObject(
				validator, object);
		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		String json = "";
		try {
			JsonGenerator gen = new JsonFactory().createJsonGenerator(writer);
			mapper.writeValue(gen, errorMessages);
			json = writer.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 校验对象内某些属性
	 */
	public Map<String, String> validateObjectProperty(Validator validator,
			Object object, String property) {
		// InfoBean infoBean = new InfoBean();
		/* 将类型装载效验 */
		return extractPropertyAndMessage(validator.validateProperty(object,
				property));
	}

	/**
	 * 校验某值是否通过对象内某属性
	 */
	public Map<String, String> validatePropertyValue(Validator validator,
			Object object, String property, Object value) {
		return extractPropertyAndMessage(validator.validateValue(Object.class,
				property, value));

	}

	/**
	 * 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
	 */
	public Map<String, String> validateWithException(Validator validator,
			Object object, Class<?>... groups)
			throws ConstraintViolationException {
		return extractPropertyAndMessage(validator.validate(object, groups));
	}

	/**
	 * 辅助方法,
	 * 转换ConstraintViolationException中的Set<ConstraintViolations>中为List<message>.
	 */
	public List<String> extractMessage(ConstraintViolationException e) {
		return extractMessage(e.getConstraintViolations());
	}

	/**
	 * 辅助方法, 转换Set<ConstraintViolation>为List<message>
	 */
	public List<String> extractMessage(
			Set<? extends ConstraintViolation> constraintViolations) {
		List<String> errorMessages = new ArrayList<String>();
		for (ConstraintViolation violation : constraintViolations) {
			errorMessages.add(violation.getMessage());
		}
		return errorMessages;
	}

	/**
	 * 辅助方法,
	 * 转换ConstraintViolationException中的Set<ConstraintViolations>为Map<property,
	 * message>.
	 */
	public Map<String, String> extractPropertyAndMessage(
			ConstraintViolationException e) {
		return extractPropertyAndMessage(e.getConstraintViolations());
	}

	/**
	 * 辅助方法, 转换Set<ConstraintViolation>为Map<property, message>.
	 */
	public Map<String, String> extractPropertyAndMessage(
			Set<? extends ConstraintViolation> constraintViolations) {
		if (constraintViolations != null && constraintViolations.size() > 0) {
			Map<String, String> errorMessages = errorMessages = new HashMap<String, String>();
			for (ConstraintViolation violation : constraintViolations) {
				errorMessages.put(violation.getPropertyPath().toString(),
						violation.getMessage());
			}
			return errorMessages;
		}
		return null;
	}

	/**
	 * 辅助方法, 转换ConstraintViolationException中的Set<ConstraintViolations>为List<
	 * propertyPath message>.
	 */
	public List<String> extractPropertyAndMessageAsList(
			ConstraintViolationException e) {
		return extractPropertyAndMessageAsList(e.getConstraintViolations(), " ");
	}

	/**
	 * 辅助方法, 转换Set<ConstraintViolations>为List<propertyPath message>.
	 */
	public List<String> extractPropertyAndMessageAsList(
			Set<? extends ConstraintViolation> constraintViolations) {
		return extractPropertyAndMessageAsList(constraintViolations, " ");
	}

	/**
	 * 辅助方法, 转换ConstraintViolationException中的Set<ConstraintViolations>为List<
	 * propertyPath +separator+ message>.
	 */
	public List<String> extractPropertyAndMessageAsList(
			ConstraintViolationException e, String separator) {
		return extractPropertyAndMessageAsList(e.getConstraintViolations(),
				separator);
	}

	/**
	 * 辅助方法, 转换Set<ConstraintViolation>为List<propertyPath +separator+ message>.
	 */
	public List<String> extractPropertyAndMessageAsList(
			Set<? extends ConstraintViolation> constraintViolations,
			String separator) {
		List<String> errorMessages = new ArrayList<String>();
		for (ConstraintViolation violation : constraintViolations) {
			errorMessages.add(violation.getPropertyPath() + separator
					+ violation.getMessage());
		}
		return errorMessages;
	}
}
