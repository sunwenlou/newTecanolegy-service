package com.sun.wen.lou.newtec.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

/**
 * 
 * 类 名: FrmRowMapper<br/>
 * 描 述: 描述类完成的主要功能<br/>
 * 版 本：<br/>
 *
 * 历 史: (版本) 作者 时间 注释 <br/>
 */
public class FrmRowMapper implements RowMapper {
	protected String columnStyle="camelCase";//underline,camelCase,original
	private Class requiredType;
	protected boolean NEED_ENCODE = false;
	protected String DB_ENCODE = "GBK";
	protected String APP_ENCODE = "GBK";

	public boolean isNEED_ENCODE() {
		return NEED_ENCODE;
	}
	public void setNEED_ENCODE(boolean nEEDENCODE) {
		NEED_ENCODE = nEEDENCODE;
	}
	public String getDB_ENCODE() {
		return DB_ENCODE;
	}
	public void setDB_ENCODE(String dBENCODE) {
		DB_ENCODE = dBENCODE;
	}
	public String getAPP_ENCODE() {
		return APP_ENCODE;
	}
	public void setAPP_ENCODE(String aPPENCODE) {
		APP_ENCODE = aPPENCODE;
	}
	public String getColumnStyle() {
		return columnStyle;
	}
	/**
	 * 
	 * @param columnStyle: underline,camelCase,original
	 */
	public void setColumnStyle(String columnStyle) {
		this.columnStyle = columnStyle;
	}	
	public FrmRowMapper() {
	}

	public FrmRowMapper(Class requiredType) {
		this.requiredType = requiredType;
	}
	
	public FrmRowMapper(Class requiredType,boolean NEED_ENCODE,String DB_ENCODE,String APP_ENCODE) {
		this.requiredType = requiredType;
		this.NEED_ENCODE=NEED_ENCODE;
		this.DB_ENCODE=DB_ENCODE;
		this.APP_ENCODE=APP_ENCODE;
	}

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Object objIns = null;
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();			
			//判断是否是简单的数据类型
			if(this.requiredType==String.class||this.requiredType==Long.class||this.requiredType==Integer.class||this.requiredType==Float.class||this.requiredType==Double.class||this.requiredType==Date.class) {
				if (columnCount != 1) {
					throw new IncorrectResultSetColumnCountException(1, columnCount);
				}
				Object obj = getColumnValue(rs, 1);
				if(isNEED_ENCODE() && obj!=null && obj instanceof String){
					String val=(String)obj;
					if(StringUtils.isNotBlank(val)){
						val = new String(val.getBytes(getDB_ENCODE()),getAPP_ENCODE());
					}
					return val;
				}else{
					return obj;
				}
			}else {
				objIns = this.requiredType.newInstance();
				Map mapOfColValues = createColumnMap(columnCount);
				for (int i = 1; i <= columnCount; ++i) {
					String key = getColumnKey(rsmd.getColumnName(i));
					Object obj = getColumnValue(rs, i);
					if(isNEED_ENCODE() && obj!=null && obj instanceof String){
						String val=(String)obj;
						if(StringUtils.isNotBlank(val)){
							val = new String(val.getBytes(getDB_ENCODE()),getAPP_ENCODE());
						}
						mapOfColValues.put(key, val);
					}else{
						mapOfColValues.put(key, obj);
					}
				}
				ConvertUtilsBean convertUtils = new ConvertUtilsBean();
				DoubleConverter doubleConverter = new DoubleConverter();
				LongConverter longConverter = new LongConverter();
				DatetimeConverter dateConverter = new DatetimeConverter();
				StringConverter stringConverter = new StringConverter();
				convertUtils.register(doubleConverter, Double.class);
				convertUtils.register(longConverter, Long.class);
	//			convertUtils.register(dateConverter, Date.class);
				convertUtils.register(stringConverter, String.class);
				BeanUtilsBean beanUtils = new BeanUtilsBean(convertUtils, new PropertyUtilsBean());
				beanUtils.populate(objIns, mapOfColValues);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return objIns;
	}

	protected Map createColumnMap(int columnCount) {
//		return CollectionFactory.createLinkedCaseInsensitiveMapIfPossible(columnCount);
		return new LinkedHashMap(columnCount);
	}
	
	

	protected String getColumnKey(String columnName) {
		if (StringUtils.equals(columnStyle, "original")) {
			return columnName;
		}
		if (StringUtils.equals(columnStyle, "camelCase")) {
			return CamelCaseUtils.toCamelCase(columnName);
		}
		if (StringUtils.equals(columnStyle, "underline")) {
			return CamelCaseUtils.toUnderlineName(columnName);
		}
		return formatColumnName(columnName);		
	}

	protected Object getColumnValue(ResultSet rs, int index) throws SQLException {
		return JdbcUtils.getResultSetValue(rs, index);
	}
	
	/**
	 * 格式化字段名 ,返回Bean类的名称<br>
	 * 将表名中的下划线去除,第一个字母大写<br>
	 * 例：user_name -> userName
	 * @param column
	 * @return
	 */
	public static String formatColumnName(String columnName) {
		String returnColumnName = "";
		if (columnName != null && columnName.length() > 0) {
			String tables[] = columnName.split("_");
			for (int i = 0; i < tables.length; i++) {
				if(i==0){
					returnColumnName += tables[i].toLowerCase();
				}else{
					returnColumnName += tables[i].substring(0, 1).toUpperCase() + tables[i].substring(1).toLowerCase();
				}
			}
		}
		return returnColumnName;
	}

}