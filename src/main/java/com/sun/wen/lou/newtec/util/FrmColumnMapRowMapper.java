package com.sun.wen.lou.newtec.util;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.LinkedCaseInsensitiveMap;


/** 
 * 类 名: FrmColumnMapRowMapper<br/>
 * 描 述: 描述类完成的主要功能<br/>
 * 版 本：1.0<br/>
 *
 * 历 史: (版本) 作者 时间 注释 <br/>
 */
public class FrmColumnMapRowMapper  implements RowMapper<Map<String, Object>> {
	protected String columnStyle="camelCase";//underline,camelCase,original
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
	public void setColumnStyle(String columnStyle) {
		this.columnStyle = columnStyle;
	}	
	public FrmColumnMapRowMapper() {
	}

	public FrmColumnMapRowMapper(boolean NEED_ENCODE,String DB_ENCODE,String APP_ENCODE) {
		this.NEED_ENCODE=NEED_ENCODE;
		this.DB_ENCODE=DB_ENCODE;
		this.APP_ENCODE=APP_ENCODE;
	}

	public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		Map<String, Object> mapOfColValues = createColumnMap(columnCount);
		try {
		for (int i = 1; i <= columnCount; i++) {
			String key = getColumnKey(JdbcUtils.lookupColumnName(rsmd, i));
			Object obj = getColumnValue(rs, i);
			if(isNEED_ENCODE() && obj!=null && obj instanceof String){
				String val=(String)obj;
				if(StringUtils.isNotBlank(val)){
					try {
						val = new String(val.getBytes(getDB_ENCODE()),getAPP_ENCODE());
					} catch (UnsupportedEncodingException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
				}
				mapOfColValues.put(key, val);
			}else{
				mapOfColValues.put(key, obj);
			}
		}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return mapOfColValues;
	}

	/**
	 * Create a Map instance to be used as column map.
	 * <p>By default, a linked case-insensitive Map will be created.
	 * @param columnCount the column count, to be used as initial
	 * capacity for the Map
	 * @return the new Map instance
	 * @see org.springframework.util.LinkedCaseInsensitiveMap
	 */
	@SuppressWarnings("unchecked")
	protected Map<String, Object> createColumnMap(int columnCount) {
		return new LinkedCaseInsensitiveMap<Object>(columnCount);
	}

	/**
	 * Determine the key to use for the given column in the column Map.
	 * @param columnName the column name as returned by the ResultSet
	 * @return the column key to use
	 * @see java.sql.ResultSetMetaData#getColumnName
	 */
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

	/**
	 * Retrieve a JDBC object value for the specified column.
	 * <p>The default implementation uses the <code>getObject</code> method.
	 * Additionally, this implementation includes a "hack" to get around Oracle
	 * returning a non standard object for their TIMESTAMP datatype.
	 * @param rs is the ResultSet holding the data
	 * @param index is the column index
	 * @return the Object returned
	 * @see org.springframework.jdbc.support.JdbcUtils#getResultSetValue
	 */
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
