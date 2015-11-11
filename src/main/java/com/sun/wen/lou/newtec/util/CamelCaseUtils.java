package com.sun.wen.lou.newtec.util;


/**
 * 类 名: CamelCaseUtils<br/>
 * 描 述: 驼峰命名转换<br/>
 * 创 建： 2014年12月19日<br/>
 * 版 本：1.0<br/>
 * 
 */
public class CamelCaseUtils {

	private static final char SEPARATOR = '_';
	/**
	 * 
	 * 描 述：驼峰格式转下划线格式<br/>
	 * 作 者：jin.zhang<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * @param s
	 * @return
	 */
	public static String toUnderlineName(String s) {
		if (s == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			boolean nextUpperCase = true;

			if (i < (s.length() - 1)) {
				nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
			}

			if ((i >= 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					if (i > 0)
						sb.append(SEPARATOR);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}

			sb.append(Character.toLowerCase(c));
		}

		return sb.toString();
	}
	/**
	 * 
	 * 描 述：首字母小写的驼峰格式<br/>
	 * 作 者：jin.zhang<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * @param s
	 * @return
	 */
	public static String toCamelCase(String s) {
		if (s == null) {
			return null;
		}

		s = s.toLowerCase();

		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}
	/**
	 * 
	 * 描 述：首字母大写的驼峰格式<br/>
	 * 作 者：jin.zhang<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * @param s
	 * @return
	 */
	public static String toCapitalizeCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = toCamelCase(s);
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	public static void main(String[] args) {
		System.out.println(CamelCaseUtils.toUnderlineName("ISOCertifiedStaff,oosIs"));
		System.out.println(CamelCaseUtils.toUnderlineName("CertifiedStaff"));
		System.out.println(CamelCaseUtils.toUnderlineName("UserID"));
		System.out.println(CamelCaseUtils.toCamelCase("iso_certified_staff"));
		System.out.println(CamelCaseUtils.toCamelCase("certified_staff,sdsd_ss"));
		System.out.println(CamelCaseUtils.toCamelCase("user_id"));
	}
}