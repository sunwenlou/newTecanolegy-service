package com.sun.wen.lou.newtec.util;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.TypeMismatchDataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.NumberUtils;

/**
 * {@link RowMapper} implementation that converts a single column into a single
 * result value per row. Expects to operate on a <code>java.sql.ResultSet</code>
 * that just contains a single column.
 * 
 * <p>
 * The type of the result value for each row can be specified. The value for the
 * single column will be extracted from the <code>ResultSet</code> and converted
 * into the specified target type.
 * 
 * @author Juergen Hoeller
 * @since 1.2
 * @see JdbcTemplate#queryForList(String, Class)
 * @see JdbcTemplate#queryForObject(String, Class)
 */
public class FrmSingleColumnRowMapper<T> implements RowMapper<T> {

	private Class<T> requiredType;
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

	/**
	 * Create a new SingleColumnRowMapper.
	 * 
	 * @see #setRequiredType
	 */
	public FrmSingleColumnRowMapper() {
	}

	/**
	 * Create a new SingleColumnRowMapper.
	 * 
	 * @param requiredType
	 *            the type that each result object is expected to match
	 */
	public FrmSingleColumnRowMapper(Class<T> requiredType) {
		this.requiredType = requiredType;
	}

	public FrmSingleColumnRowMapper(Class<T> requiredType, boolean NEED_ENCODE, String DB_ENCODE, String APP_ENCODE) {
		this.requiredType = requiredType;
		this.NEED_ENCODE = NEED_ENCODE;
		this.DB_ENCODE = DB_ENCODE;
		this.APP_ENCODE = APP_ENCODE;
	}

	/**
	 * Set the type that each result object is expected to match.
	 * <p>
	 * If not specified, the column value will be exposed as returned by the
	 * JDBC driver.
	 */
	public void setRequiredType(Class<T> requiredType) {
		this.requiredType = requiredType;
	}

	/**
	 * Extract a value for the single column in the current row.
	 * <p>
	 * Validates that there is only one column selected, then delegates to
	 * <code>getColumnValue()</code> and also
	 * <code>convertValueToRequiredType</code>, if necessary.
	 * 
	 * @see java.sql.ResultSetMetaData#getColumnCount()
	 * @see #getColumnValue(java.sql.ResultSet, int, Class)
	 * @see #convertValueToRequiredType(Object, Class)
	 */
	@SuppressWarnings("unchecked")
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		// Validate column count.
		ResultSetMetaData rsmd = rs.getMetaData();
		int nrOfColumns = rsmd.getColumnCount();
		if (nrOfColumns != 1) {
			throw new IncorrectResultSetColumnCountException(1, nrOfColumns);
		}

		// Extract column value from JDBC ResultSet.
		Object result = getColumnValue(rs, 1, this.requiredType);
		if (result != null && this.requiredType != null && !this.requiredType.isInstance(result)) {
			// Extracted value does not match already: try to convert it.
			try {
				if (isNEED_ENCODE() && result instanceof String) {
					String val = result.toString();
					if (StringUtils.isNotBlank(val)) {
						result = new String(val.getBytes(getDB_ENCODE()), getAPP_ENCODE());
					}
				}
				return (T) convertValueToRequiredType(result, this.requiredType);
			} catch (IllegalArgumentException ex) {
				throw new TypeMismatchDataAccessException("Type mismatch affecting row number " + rowNum + " and column type '" + rsmd.getColumnTypeName(1) + "': "
						+ ex.getMessage());
			} catch (UnsupportedEncodingException ex) {
				ex.printStackTrace();
			}

		}
		return (T) result;
	}

	/**
	 * Retrieve a JDBC object value for the specified column.
	 * <p>
	 * The default implementation calls
	 * {@link JdbcUtils#getResultSetValue(java.sql.ResultSet, int, Class)}. If
	 * no required type has been specified, this method delegates to
	 * <code>getColumnValue(rs, index)</code>, which basically calls
	 * <code>ResultSet.getObject(index)</code> but applies some additional
	 * default conversion to appropriate value types.
	 * 
	 * @param rs
	 *            is the ResultSet holding the data
	 * @param index
	 *            is the column index
	 * @param requiredType
	 *            the type that each result object is expected to match (or
	 *            <code>null</code> if none specified)
	 * @return the Object value
	 * @throws SQLException
	 *             in case of extraction failure
	 * @see org.springframework.jdbc.support.JdbcUtils#getResultSetValue(java.sql.ResultSet,
	 *      int, Class)
	 * @see #getColumnValue(java.sql.ResultSet, int)
	 */
	protected Object getColumnValue(ResultSet rs, int index, Class requiredType) throws SQLException {
		if (requiredType != null) {
			return JdbcUtils.getResultSetValue(rs, index, requiredType);
		} else {
			// No required type specified -> perform default extraction.
			return getColumnValue(rs, index);
		}
	}

	/**
	 * Retrieve a JDBC object value for the specified column, using the most
	 * appropriate value type. Called if no required type has been specified.
	 * <p>
	 * The default implementation delegates to
	 * <code>JdbcUtils.getResultSetValue()</code>, which uses the
	 * <code>ResultSet.getObject(index)</code> method. Additionally, it includes
	 * a "hack" to get around Oracle returning a non-standard object for their
	 * TIMESTAMP datatype. See the <code>JdbcUtils#getResultSetValue()</code>
	 * javadoc for details.
	 * 
	 * @param rs
	 *            is the ResultSet holding the data
	 * @param index
	 *            is the column index
	 * @return the Object value
	 * @throws SQLException
	 *             in case of extraction failure
	 * @see org.springframework.jdbc.support.JdbcUtils#getResultSetValue(java.sql.ResultSet,
	 *      int)
	 */
	protected Object getColumnValue(ResultSet rs, int index) throws SQLException {
		return JdbcUtils.getResultSetValue(rs, index);
	}

	/**
	 * Convert the given column value to the specified required type. Only
	 * called if the extracted column value does not match already.
	 * <p>
	 * If the required type is String, the value will simply get stringified via
	 * <code>toString()</code>. In case of a Number, the value will be converted
	 * into a Number, either through number conversion or through String parsing
	 * (depending on the value type).
	 * 
	 * @param value
	 *            the column value as extracted from
	 *            <code>getColumnValue()</code> (never <code>null</code>)
	 * @param requiredType
	 *            the type that each result object is expected to match (never
	 *            <code>null</code>)
	 * @return the converted value
	 * @see #getColumnValue(java.sql.ResultSet, int, Class)
	 */
	@SuppressWarnings("unchecked")
	protected Object convertValueToRequiredType(Object value, Class requiredType) {
		if (String.class.equals(requiredType)) {
			return value.toString();
		} else if (Number.class.isAssignableFrom(requiredType)) {
			if (value instanceof Number) {
				// Convert original Number to target Number class.
				return NumberUtils.convertNumberToTargetClass(((Number) value), requiredType);
			} else {
				// Convert stringified value to target Number class.
				return NumberUtils.parseNumber(value.toString(), requiredType);
			}
		} else {
			throw new IllegalArgumentException("Value [" + value + "] is of type [" + value.getClass().getName() + "] and cannot be converted to required type ["
					+ requiredType.getName() + "]");
		}
	}

}
