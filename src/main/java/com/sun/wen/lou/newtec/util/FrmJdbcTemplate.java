package com.sun.wen.lou.newtec.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * 
 * 类 名: FrmJdbcTemplate<br/>
 * 描 述: 描述类完成的主要功能<br/>
 * 版 本：<br/>
 * 
 * 历 史: (版本) 作者 时间 注释 <br/>
 */
// @Repository("jdbcTemplate")
public class FrmJdbcTemplate extends JdbcTemplate {
	// @Autowired
	private DataSource dataSource;
	protected boolean NEED_ENCODE = false;
	protected String DB_ENCODE = "ISO-8859-1";
	protected String APP_ENCODE = "GBK";

	public boolean isNEED_ENCODE() {
		return this.NEED_ENCODE;
	}

	public void setNEED_ENCODE(boolean nEEDENCODE) {
		this.NEED_ENCODE = nEEDENCODE;
	}

	public String getDB_ENCODE() {
		return this.DB_ENCODE;
	}

	public void setDB_ENCODE(String dBENCODE) {
		this.DB_ENCODE = dBENCODE;
	}

	public String getAPP_ENCODE() {
		return this.APP_ENCODE;
	}

	public void setAPP_ENCODE(String aPPENCODE) {
		this.APP_ENCODE = aPPENCODE;
	}

	// @PostConstruct
	public void injectSessionFactory() {
		super.setDataSource(this.dataSource);
	}
	
	public Object queryForBean(String sql, Class elementType) throws DataAccessException {
		try {
			if (isNEED_ENCODE()) {
				sql = new String(sql.getBytes(getAPP_ENCODE()), getDB_ENCODE());
				return super.queryForObject(sql, new FrmRowMapper(elementType, this.NEED_ENCODE, this.DB_ENCODE, this.APP_ENCODE));
			}
			return super.queryForObject(sql, new FrmRowMapper(elementType));
		} catch (Exception ex) {
		}
		return null;
	}

	public Object queryForBean(String sql,  Class elementType, Object... args) throws DataAccessException {
		try {
			if (isNEED_ENCODE()) {
				sql = new String(sql.getBytes(getAPP_ENCODE()), getDB_ENCODE());
				return super.queryForObject(sql, args, new FrmRowMapper(elementType, this.NEED_ENCODE, this.DB_ENCODE, this.APP_ENCODE));
			}
			return super.queryForObject(sql, args, new FrmRowMapper(elementType));
		} catch (Exception ex) {
		}
		return null;
	}

	@Override
	public List queryForList(String sql, Class elementType) throws DataAccessException {
		try {
			if (isNEED_ENCODE()) {
				return super.query(sql, new FrmRowMapper(elementType, this.NEED_ENCODE, this.DB_ENCODE, this.APP_ENCODE));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.query(sql, new FrmRowMapper(elementType));
	}

	@Override
	public List queryForList(String sql, Class elementType, Object... args) throws DataAccessException {
		try {
			if (isNEED_ENCODE()) {
				sql = new String(sql.getBytes(getAPP_ENCODE()), getDB_ENCODE());
				return super.query(sql, args, new FrmRowMapper(elementType, this.NEED_ENCODE, this.DB_ENCODE, this.APP_ENCODE));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.query(sql, args, new FrmRowMapper(elementType));
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql) throws DataAccessException {
		if (isNEED_ENCODE()) {
			try {
				sql = new String(sql.getBytes(getAPP_ENCODE()), getDB_ENCODE());
				List<Map<String, Object>> list = super.query(sql, new FrmColumnMapRowMapper(this.NEED_ENCODE, this.DB_ENCODE, this.APP_ENCODE));
				return list;
			} catch (UnsupportedEncodingException e) {
			}
		}
		return super.query(sql, new FrmColumnMapRowMapper());
	}	
	
	public List queryForListByColumnStyle(String sql, String columnStyle,Class elementType, Object... args) throws DataAccessException {
		FrmRowMapper frmRowMapper=null;
		try {
			if (isNEED_ENCODE()) {
				sql = new String(sql.getBytes(getAPP_ENCODE()), getDB_ENCODE());
				frmRowMapper= new FrmRowMapper(elementType, this.NEED_ENCODE, this.DB_ENCODE, this.APP_ENCODE);
				frmRowMapper.setColumnStyle("original");
				return super.query(sql, args, frmRowMapper);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		frmRowMapper= new FrmRowMapper(elementType);
		frmRowMapper.setColumnStyle("original");
		return super.query(sql, args, frmRowMapper);
	}
	/**
	 * 
	 * @param sql
	 * @param columnStyle:underline,camelCase,original
	 * @return
	 * @throws DataAccessException
	 */	
	public List<Map<String, Object>> queryForListByColumnStyle(String sql,String columnStyle) throws DataAccessException {
		FrmColumnMapRowMapper frmColumnMapRowMapper=new FrmColumnMapRowMapper();
		frmColumnMapRowMapper.setColumnStyle(columnStyle);
		if (isNEED_ENCODE()) {
			try {
				sql = new String(sql.getBytes(getAPP_ENCODE()), getDB_ENCODE());
				frmColumnMapRowMapper.setAPP_ENCODE(this.APP_ENCODE);
				frmColumnMapRowMapper.setDB_ENCODE(this.DB_ENCODE);
				frmColumnMapRowMapper.setNEED_ENCODE(this.NEED_ENCODE);
				List<Map<String, Object>> list = super.query(sql, frmColumnMapRowMapper);
				return list;
			} catch (UnsupportedEncodingException e) {
			}
		}
		return super.query(sql, frmColumnMapRowMapper);
	}	
	/**
	 * 
	 * @param sql
	 * @param columnStyle:underline,camelCase,original
	 * @param args
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryForListByColumnStyle(String sql,String columnStyle, Object... args) throws DataAccessException {
		FrmColumnMapRowMapper frmColumnMapRowMapper=new FrmColumnMapRowMapper();
		frmColumnMapRowMapper.setColumnStyle(columnStyle);
		if (isNEED_ENCODE()) {
			try {
				sql = new String(sql.getBytes(getAPP_ENCODE()), getDB_ENCODE());
				frmColumnMapRowMapper.setAPP_ENCODE(this.APP_ENCODE);
				frmColumnMapRowMapper.setDB_ENCODE(this.DB_ENCODE);
				frmColumnMapRowMapper.setNEED_ENCODE(this.NEED_ENCODE);
				List<Map<String, Object>> list = super.query(sql,args, frmColumnMapRowMapper);
				return list;
			} catch (UnsupportedEncodingException e) {
			}
		}
		return super.query(sql, args,frmColumnMapRowMapper);
	}	
	@Override
	public List<Map<String, Object>> queryForList(String sql, Object... args) throws DataAccessException {
		if (isNEED_ENCODE()) {
			try {
				sql = new String(sql.getBytes(getAPP_ENCODE()), getDB_ENCODE());
				List<Map<String, Object>> list = super.query(sql, args, new FrmColumnMapRowMapper(this.NEED_ENCODE, this.DB_ENCODE, this.APP_ENCODE));
				return list;
			} catch (UnsupportedEncodingException e) {
			}
		}
		return super.query(sql, args, new FrmColumnMapRowMapper());
	}
	
	public String getSeqNextVal(String seqName) {
		Map mapSEQ = this.queryForMap("SELECT " + seqName + ".NEXTVAL as SEQVAL FROM DUAL");
		if (null != mapSEQ) {
			return ((BigDecimal) mapSEQ.get("SEQVAL")).toString();
		} else {
			return null;
		}
	}

	@Override
	public int update(String sql) throws DataAccessException {
		if (isNEED_ENCODE()) {
			try {
				sql = new String(sql.getBytes(getAPP_ENCODE()), getDB_ENCODE());
			} catch (UnsupportedEncodingException e) {
			}
		}
		return super.update(sql);
	}
	
	@Override
	public int update(String sql, Object... args) throws DataAccessException {
		if (isNEED_ENCODE()) {
			try {
				sql = new String(sql.getBytes(getAPP_ENCODE()), getDB_ENCODE());
			} catch (UnsupportedEncodingException e) {
			}
		}
		return super.update(sql,args);		
	}
	
	@Override
	public Map<String, Object> queryForMap(String sql) throws DataAccessException {
		if (isNEED_ENCODE()) {
			try {
				sql = new String(sql.getBytes(getAPP_ENCODE()), getDB_ENCODE());
				return super.queryForObject(sql, new FrmColumnMapRowMapper(this.NEED_ENCODE, this.DB_ENCODE, this.APP_ENCODE));
			} catch (UnsupportedEncodingException e) {
			}
		}
		return super.queryForObject(sql, new FrmColumnMapRowMapper());
	}

	@Override
	public Map<String, Object> queryForMap(String sql, Object... args) throws DataAccessException {
		if (isNEED_ENCODE()) {
			try {
				sql = new String(sql.getBytes(getAPP_ENCODE()), getDB_ENCODE());
				return super.queryForObject(sql,args, new FrmColumnMapRowMapper(this.NEED_ENCODE, this.DB_ENCODE, this.APP_ENCODE));
			} catch (UnsupportedEncodingException e) {
			}
		}
		return super.queryForObject(sql,args, new FrmColumnMapRowMapper());		
	}
	
	public <T> List<T> queryForSingleColumnList(String sql, Class<T> elementType) throws DataAccessException {
		if (isNEED_ENCODE()) {
			try {
				sql = new String(sql.getBytes(getAPP_ENCODE()), getDB_ENCODE());
				List<T> list = super.query(sql, new FrmSingleColumnRowMapper(elementType, this.NEED_ENCODE, this.DB_ENCODE, this.APP_ENCODE));
				return list;
			} catch (UnsupportedEncodingException e) {
			}
		}
		return super.query(sql, new SingleColumnRowMapper<T>(elementType));
	}
	
	public <T> List<T> queryForSingleColumnList(String sql, Class<T> elementType,Object... args) throws DataAccessException {
		if (isNEED_ENCODE()) {
			try {
				sql = new String(sql.getBytes(getAPP_ENCODE()), getDB_ENCODE());
				List<T> list = super.query(sql,args, new FrmSingleColumnRowMapper(elementType, this.NEED_ENCODE, this.DB_ENCODE, this.APP_ENCODE));
				return list;
			} catch (UnsupportedEncodingException e) {
			}
		}
		return super.query(sql,args, new SingleColumnRowMapper<T>(elementType));
	}

	@Override
	public <T> T execute(String callString, CallableStatementCallback<T> action) throws DataAccessException {
		try {
			// 调用存储过程，需要转码
			if (isNEED_ENCODE()) {
				CallableStatementCallbackImpl callback = (CallableStatementCallbackImpl) action;
				Object bean = callback.getParameterObject();
				Map map = BeanUtils.describe(callback.getParameterObject());
				Set keySet = map.keySet();
				for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
					Object key = iterator.next();
					if (key instanceof String) {
						Object value = map.get(key);
						if (value != null && value instanceof String) {
							value = new String(((String) value).getBytes(getAPP_ENCODE()), getDB_ENCODE());
							BeanUtils.setProperty(bean, (String) key, value);
						}
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return super.execute(callString, action);
	}
	
	@Override
	public void execute(String sql) throws DataAccessException {
		if (isNEED_ENCODE()) {
			try {
				sql = new String(sql.getBytes(getAPP_ENCODE()), getDB_ENCODE());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		super.execute(sql); 
	}
	
	@Override
	public <T> List<T> query(String sql, RowMapper<T> rowMapper) throws DataAccessException {
		if (isNEED_ENCODE()) {
			try {
				sql = new String(sql.getBytes(getAPP_ENCODE()), getDB_ENCODE());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return super.query(sql, rowMapper);
	}

	@Override
	public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {
		if (isNEED_ENCODE()) {
			try {
				sql = new String(sql.getBytes(getAPP_ENCODE()), getDB_ENCODE());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return super.query(sql, args, new RowMapperResultSetExtractor<T>(rowMapper));
	}
	
	public static void main(String args[]) {
		Class sss=Long.class;
		System.out.println((sss==Long.class));
	}
	
}