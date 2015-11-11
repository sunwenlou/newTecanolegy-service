package com.sun.wen.lou.newtec.hibernate.validate;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

public interface BeanValidator {

	/**
	 * 校验对象
	 */
	public List<Map<String, String>> validatorObject(Validator validator, Object object);

	/**
	 * 校验对象,输出为JSON格式
	 */
	public String validatorObjectJson(Validator validator, Object object);

	/**
	 * 校验对象内某些属性
	 */
	public Map<String, String> validateObjectProperty(Validator validator, Object object,String property);

	/**
	 * 校验某值是否通过对象内某属性
	 */
	public Map<String, String> validatePropertyValue(Validator validator, Object object,String property,Object value);

	/**
	 * 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
	 */
	public Map<String, String>  validateWithException(Validator validator, Object object, Class<?>... groups) throws ConstraintViolationException;

	/**
	 * 辅助方法,
	 * 转换ConstraintViolationException中的Set<ConstraintViolations>中为List<message>.
	 */
	public List<String> extractMessage(ConstraintViolationException e);

	/**
	 * 辅助方法, 转换Set<ConstraintViolation>为List<message>
	 */
	public List<String> extractMessage(
			Set<? extends ConstraintViolation> constraintViolations);

	/**
	 * 辅助方法,
	 * 转换ConstraintViolationException中的Set<ConstraintViolations>为Map<property,
	 * message>.
	 */
	public Map<String, String> extractPropertyAndMessage(
			ConstraintViolationException e);

	/**
	 * 辅助方法, 转换Set<ConstraintViolation>为Map<property, message>.
	 */
	public Map<String, String> extractPropertyAndMessage(
			Set<? extends ConstraintViolation> constraintViolations);

	/**
	 * 辅助方法, 转换ConstraintViolationException中的Set<ConstraintViolations>为List<
	 * propertyPath message>.
	 */
	public List<String> extractPropertyAndMessageAsList(
			ConstraintViolationException e);

	/**
	 * 辅助方法, 转换Set<ConstraintViolations>为List<propertyPath message>.
	 */
	public List<String> extractPropertyAndMessageAsList(
			Set<? extends ConstraintViolation> constraintViolations);

	/**
	 * 辅助方法, 转换ConstraintViolationException中的Set<ConstraintViolations>为List<
	 * propertyPath +separator+ message>.
	 */
	public List<String> extractPropertyAndMessageAsList(
			ConstraintViolationException e, String separator);

	/**
	 * 辅助方法, 转换Set<ConstraintViolation>为List<propertyPath +separator+ message>.
	 */
	public List<String> extractPropertyAndMessageAsList(
			Set<? extends ConstraintViolation> constraintViolations,
			String separator);
}
