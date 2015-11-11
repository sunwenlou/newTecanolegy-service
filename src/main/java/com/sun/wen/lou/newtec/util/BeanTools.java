package com.sun.wen.lou.newtec.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
/**
 * 
 * 类 名: BeanTools<br/>
 * 描 述: 描述类完成的主要功能<br/>
 * 版 本：<br/>
 *
 * 历 史: (版本) 作者 时间 注释 <br/>
 */
public class BeanTools {
	public static Object popu(Class objectClass, ServletRequest request) {
		HashMap map = new HashMap();
		Object obj = null;
		Enumeration names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = request.getParameter(name);
			map.put(name, value);
		}
		try {
			obj = objectClass.newInstance();
			BeanUtils.populate(obj, map);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return obj;
	}

	public static Object popu(Object object, ServletRequest request) {
		HashMap map = new HashMap();
		Enumeration names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = request.getParameter(name);
			map.put(name, value);
		}
		try {
			BeanUtils.populate(object, map);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return object;
	}

	public static Object beanValueCopy(Object sourceObject, Object aimObject)
			throws Exception {
		try {
			BeanUtils.copyProperties(aimObject, sourceObject);
		} catch (InvocationTargetException ex) {
			throw new Exception(ex.getMessage());
		} catch (IllegalAccessException ex) {
			throw new Exception(ex.getMessage());
		}
		return aimObject;
	}

	public static Object beanValueCopy(String _className, Object sourceObj)
			throws Exception {
		Object targetObj = null;
		try {
			if (sourceObj != null) {
				targetObj = Class.forName(_className).newInstance();
				PropertyUtils.copyProperties(targetObj, sourceObj);
			} else {
				targetObj = null;
			}
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
		return targetObj;
	}

	public static String bean2Input(Object bean, String isValue)
			throws Exception {
		StringBuffer buf = new StringBuffer();
		try {
			Map p = BeanUtils.describe(bean);
			Set s = p.keySet();
			Iterator i = s.iterator();
			while (i.hasNext()) {
				Object key = i.next();
				if (!(key.equals("class"))) {
					Object value = p.get(key);
					if ((value == null)
							|| (value.toString().toLowerCase().equals("null"))) {
						value = "";
					}
					if (isValue.equals("1")) {
						buf.append("<input type=\"hidden\" name=\"" + key
								+ "\" value=\"" + value + "\"/>\n");
					} else
						buf.append("<input type=\"hidden\" name=\"" + key
								+ "\" value=\"\"/>\n");
				}
			}
		} catch (Exception ex) {
			throw new Exception(
					"Error occurred while trans bean to Input node:"
							+ ex.getMessage());
		}

		return buf.toString();
	}

	public static String clearPageInputValue(Object _obj, String _formName,
			String _formFieldAppendName, boolean isParent) {
		StringBuffer buf = new StringBuffer();
		try {
			Map p = BeanUtils.describe(_obj);
			Set s = p.keySet();
			Iterator k = s.iterator();
			while (k.hasNext()) {
				buf.append("try{\n");
				Object key = k.next();
				if (!(key.equals("class"))) {
					if (isParent) {
						buf.append("parent.document." + _formName + "."
								+ key.toString().toLowerCase()
								+ _formFieldAppendName + ".value='';\n");
					} else {
						buf.append(_formName + "."
								+ key.toString().toLowerCase()
								+ _formFieldAppendName + ".value='';\n");
					}

				}

				buf.append("}catch(ex){}\n");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return buf.toString();
	}

	public static String setPageInputValue(Object _obj, String _formName,
			String _formFieldAppendName, boolean isParent, boolean isEmpty) {
		StringBuffer buf = new StringBuffer();
		try {
			Map p = BeanUtils.describe(_obj);
			Set s = p.keySet();
			Iterator k = s.iterator();
			while (k.hasNext()) {
				buf.append("try{\n");
				Object key = k.next();
				if (!(key.equals("class"))) {
					Object value = p.get(key);
					String m_curValue = (String) value;
					m_curValue = ReplaceString(m_curValue, "'", "\\'");
					m_curValue = ReplaceString(m_curValue, "\"", "\\\"");
					m_curValue = ReplaceString(m_curValue, "\n", "\\n");
					if ((m_curValue == null)
							|| (m_curValue.toLowerCase().equals("null"))) {
						m_curValue = "";
					}
					if (isParent) {
						if (isEmpty) {
							buf.append("parent.document." + _formName + "."
									+ key.toString().toLowerCase()
									+ _formFieldAppendName + ".value='';\n");
						} else {
							buf.append("parent.document." + _formName + "."
									+ key.toString().toLowerCase()
									+ _formFieldAppendName + ".value='"
									+ m_curValue + "';\n");
						}

					} else if (isEmpty) {
						buf.append(_formName + "."
								+ key.toString().toLowerCase()
								+ _formFieldAppendName + ".value='';\n");
					} else {
						buf.append(_formName + "."
								+ key.toString().toLowerCase()
								+ _formFieldAppendName + ".value='"
								+ m_curValue + "';\n");
					}

				}

				buf.append("}catch(ex){}\n");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return buf.toString();
	}

	public static String setPageSpanValue(Object _obj, String _formName,
			String _formFieldAppendName, boolean isParent, boolean isEmpty) {
		StringBuffer buf = new StringBuffer();
		try {
			Map p = BeanUtils.describe(_obj);
			Set s = p.keySet();
			Iterator k = s.iterator();
			while (k.hasNext()) {
				buf.append("try{\n");
				Object key = k.next();
				if (!(key.equals("class"))) {
					Object value = p.get(key);
					String m_curValue = (String) value;
					m_curValue = ReplaceString(m_curValue, "'", "\\'");
					m_curValue = ReplaceString(m_curValue, "\"", "\\\"");
					m_curValue = ReplaceString(m_curValue, "\n", "\\n");
					if ((m_curValue == null)
							|| (m_curValue.toLowerCase().equals("null"))) {
						m_curValue = "";
					}
					if (isParent) {
						if (isEmpty) {
							buf
									.append("parent.document." + _formName
											+ "."
											+ key.toString().toLowerCase()
											+ _formFieldAppendName
											+ ".innerHTML='';\n");
						} else {
							buf.append("parent.document." + _formName + "."
									+ key.toString().toLowerCase()
									+ _formFieldAppendName + ".innerHTML='"
									+ m_curValue + "';\n");
						}

					} else if (isEmpty) {
						buf.append(_formName + "."
								+ key.toString().toLowerCase()
								+ _formFieldAppendName + ".innerHTML='';\n");
					} else {
						buf.append(_formName + "."
								+ key.toString().toLowerCase()
								+ _formFieldAppendName + ".innerHTML='"
								+ m_curValue + "';\n");
					}

				}

				buf.append("}catch(ex){}\n");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return buf.toString();
	}

	public static String ReplaceString(String original, String find,
			String replace) {
		if (original == null) {
			original = "";
		}
		String returnStr = "";
		if (original.indexOf(find) < 0)
			returnStr = original;
		try {
			while (original.indexOf(find) >= 0) {
				int indexbegin = original.indexOf(find);
				String leftstring = original.substring(0, indexbegin);
				original = original.substring(indexbegin + find.length());
				if (original.indexOf(find) <= 0)
					returnStr = returnStr + leftstring + replace + original;
				else {
					returnStr = returnStr + leftstring + replace;
				}
			}
			return returnStr;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return original;
	}
	
	public static Map bean2Map(Object bean) throws Exception {
		Map<String, String> retMap =new HashMap<String, String>();
		try {
			Map p = BeanUtils.describe(bean);
			Set s = p.keySet();
			Iterator i = s.iterator();
			while (i.hasNext()) {
				Object key = i.next();
				if (!(key.equals("class"))) {
					Object value = p.get(key);
					if(value instanceof String){
						if ((value == null)|| (value.toString().toLowerCase().equals("null"))) {
							value = "";
						}
						retMap.put((String)key, (String)value);
					}
				}
			}
		} catch (Exception ex) {
			throw new Exception(
					"Error occurred while trans bean to Input node:"
							+ ex.getMessage());
		}

		return retMap;
	}
	
	/**
	 * 例:将contactId置换为contact_id
	 * @param columnName
	 * @return
	 */
	public static String formatColumnName(String columnName) {
		char[] array = columnName.toCharArray();
		String transColumn = "";
		for (char x:array){
			if (!Character.isLowerCase(x))
			{
				transColumn += "_"+x;
			} else {
				transColumn += x;
			}
		}
		return transColumn;
	}	
}