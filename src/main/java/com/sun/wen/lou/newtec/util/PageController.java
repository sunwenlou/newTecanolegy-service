package com.sun.wen.lou.newtec.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

//import com.mongodb.BasicDBObject;
//import com.mongodb.DBObject;
//import com.zhaopin.framework.util.mongo.MongoTemplate;
//import com.zhaopin.framework.util.mongo.MongoUtil;

/**
 * 
 * 类 名: PageController<br/>
 * 描 述: 描述类完成的主要功能<br/>
 *
 */
public class PageController {

	int totalRowsAmount;// 符合查询条件的总条数
	int pageSize = 10;// 分页每页返回多少条
	int currentPage = 1;// 当前第几页
	private String sort;// 排序字段
	private String order;// asc/desc

	/**
	 * 当前页数据
	 */
	private List<?> content;

	int toPage;// 查询第几页
	int nextPage;// 下一页页数
	int previousPage;// 上一页页数
	int lastPage;// 最后一页页数
	int totalPages;// 根据totalRowsAmount与pageSize,计算出分页数量
	boolean rowsAmountSet;
	boolean hasNext;
	boolean hasPrevious;
	String description;
	int pageStartRow;
	int pageEndRow;
	String formAction;
	String hexQueryCondition;
	String pageCtrlDesc;
	String pageScript;
	String elements;
	String querySql;
	String sortname;// 排序字段名（ligerUI使用）
	String sortorder;// 排序顺序（ligerUI使用）

	private boolean isUnderlineNameSortName = true;// 是否启用驼峰命名转换

	public PageController(int totalRows) {
		setTotalRowsAmount(totalRows);
	}

	public PageController(HttpServletRequest req) {
		BeanTools.popu(this, req);
		setElements(req);
		setFormAction(req.getRequestURI() + getUrl(req));
		String page = req.getParameter("page");
		String pagesize = req.getParameter("pagesize");
		String rows = req.getParameter("rows");// EASYUI使用
		if (StringUtils.isNotBlank(rows)) {
			pagesize = rows;
		}
		setToPage(Integer.parseInt(page == null ? "1" : page));
		// setPage(getToPage());//EASYUI使用
		setPageSize(Integer.parseInt(pagesize == null ? "20" : pagesize));
		// setRows(getPageSize());//EASYUI使用

	}

	public PageController() {
		setToPage(1);
		// setPage(getToPage());//EASYUI使用
		setPageSize(20);
		// setRows(getPageSize());//EASYUI使用
	}

	public PageController(int page, int rows) {
		if (page > 0) {
			setToPage(Integer.valueOf(page));
		}
		if (rows > 0) {
			setPageSize(Integer.valueOf(rows));
		}
		// setPage(getToPage());// EASYUI使用
		// setRows(getPageSize());// EASYUI使用
	}

	public void setTotalRowsAmount(int i) {
		if (!(this.rowsAmountSet)) {
			this.totalRowsAmount = i;

			int k = this.totalRowsAmount % this.pageSize;
			if (k == 0) {
				this.totalPages = (this.totalRowsAmount / this.pageSize);
			} else {
				this.totalPages = (this.totalRowsAmount / this.pageSize + 1);
			}

			setCurrentPage(1);
			this.rowsAmountSet = true;
		}
	}

	public void setCurrentPage(int i) {
		this.currentPage = i;
		this.nextPage = (this.currentPage + 1);
		this.previousPage = (this.currentPage - 1);

		if (this.currentPage * this.pageSize <= this.totalRowsAmount) {
			this.pageEndRow = (this.currentPage * this.pageSize);
			this.pageStartRow = (this.pageEndRow - this.pageSize + 1);
		} else {
			this.pageEndRow = this.totalRowsAmount;
			if (this.totalPages < 1) {
				this.pageStartRow = (this.pageSize * this.totalPages  + 1);
			} else {
				this.pageStartRow = (this.pageSize * (this.totalPages - 1) + 1);
			}
		}

		if (this.nextPage > this.totalPages)
			this.hasNext = false;
		else {
			this.hasNext = true;
		}
		if (this.previousPage == 0)
			this.hasPrevious = false;
		else {
			this.hasPrevious = true;
		}
		makingDesc();
	}

	private void makingDesc() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<script language='javascript' type='text/JavaScript'>\n");
		buffer.append("function submitController(page){\n");
		buffer.append("document.pageController.toPage.value=page;\n");
		buffer.append("document.pageController.submit();\n");
		buffer.append("}\n");

		buffer.append("</script>\n");
		buffer.append("<form name=\"pageController\" action=\"");
		buffer.append(this.formAction);
		buffer.append("\" method=\"POST\">\n");
		buffer.append("<input type=\"hidden\" name=\"hexQueryCondition\" value=\"" + getHexQueryCondition() + "\"/>\n");
		buffer.append("<input type=\"hidden\" name=\"toPage\" value=\"\"/>\n");
		buffer.append("<input type=\"hidden\" name=\"formAction\" value=\"" + this.formAction + "\"/>\n");
		buffer.append(getElements());
		// buffer.append("每页<input type=\"text\" name=\"pageSize\" value=\""+getPageSize()+"\" style=\"width:25px\"/>条 \n");
		buffer.append("</form>\n");
		this.pageScript = buffer.toString();
		buffer.delete(0, buffer.length());
		buffer.append("共");
		buffer.append(this.totalRowsAmount);
		buffer.append("条&nbsp;共");
		buffer.append(this.totalPages);
		buffer.append("页&nbsp;当前第");
		buffer.append(this.currentPage);
		buffer.append("页 &nbsp;");
		buffer.append("<label onclick='submitController(");
		buffer.append(1);
		buffer.append(")' style='cursor:hand;'>首页</label>&nbsp;");
		if (this.hasPrevious) {
			buffer.append("<label onclick='submitController(");
			buffer.append(this.previousPage);
			buffer.append(")' style='cursor:hand'>上一页</label>&nbsp;");
		} else {
			buffer.append("<label ");
			buffer.append(">上一页</label>&nbsp;");
		}
		if (this.hasNext) {
			buffer.append("<label onclick='submitController(");
			buffer.append(this.nextPage);
			buffer.append(")' style='cursor:hand'>下一页</label>&nbsp;");
		} else {
			buffer.append("<label ");
			buffer.append(">下一页</label>&nbsp;");
		}
		buffer.append("<label onclick='submitController(");
		buffer.append(this.totalPages);
		buffer.append(")' style='cursor:hand'>末页</label>&nbsp;");
		this.pageCtrlDesc = buffer.toString();
		buffer.delete(0, buffer.length());
	}

	public int getCurrentPage() {
		return this.currentPage;
	}

	public boolean isHasNext() {
		return this.hasNext;
	}

	public boolean isHasPrevious() {
		return this.hasPrevious;
	}

	public int getNextPage() {
		return this.nextPage;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public int getPreviousPage() {
		return this.previousPage;
	}

	public int getTotalPages() {
		return this.totalPages;
	}

	public int getTotalRowsAmount() {
		return this.totalRowsAmount;
	}

	public void setHexQueryCondition(String hexQueryCondition) {
		this.hexQueryCondition = hexQueryCondition;
	}

	public void setToPage(int toPage) {
		this.toPage = toPage;
	}

	public void setFormAction(String formAction) {
		this.formAction = formAction;
	}

	public int getPageEndRow() {
		return this.pageEndRow;
	}

	public int getPageStartRow() {
		return this.pageStartRow;
	}

	public String getDescription() {
		String description = "CurrentPage:" + getCurrentPage() + ";Total:" + getTotalRowsAmount() + ";items:" + getTotalPages() + " pages";
		return description;
	}

	public String getHexQueryCondition() {
		return this.hexQueryCondition;
	}

	public int getToPage() {
		return this.toPage;
	}

	public int getLastPage() {
		return this.lastPage;
	}

	public String getClientScript() {
		return this.pageScript;
	}

	public String getClientPageCtrlDesc() {
		return this.pageCtrlDesc;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List getWarpedList(String firstQuerySQL, Class elementType, FrmJdbcTemplate jdbcTemplate) {
		return getWarpedList(firstQuerySQL, new FrmRowMapper(elementType), jdbcTemplate);
	}

	public List getWarpedList(String firstQuerySQL, FrmRowMapper mapper, FrmJdbcTemplate jdbcTemplate) {
		String querySQL = "";

		/** 转换数据库编码 */
		boolean NEED_ENCODE = jdbcTemplate.isNEED_ENCODE();
		String DB_ENCODE = jdbcTemplate.getDB_ENCODE();
		String APP_ENCODE = jdbcTemplate.getAPP_ENCODE();
		mapper.setNEED_ENCODE(NEED_ENCODE);
		mapper.setDB_ENCODE(DB_ENCODE);
		mapper.setAPP_ENCODE(APP_ENCODE);

		if ((this.hexQueryCondition == null) || (this.hexQueryCondition.equals(""))) {
			setHexQueryCondition(WrapHelper.byte2hex(firstQuerySQL.getBytes()));
			querySQL = firstQuerySQL;
		} else {
			querySQL = new String(WrapHelper.hex2byte(getHexQueryCondition()));
		}

		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT count(*) AS rown FROM ( ");
		buffer.append(querySQL);
		buffer.append(") pagetable");
		int rowamount = jdbcTemplate.queryForInt(buffer.toString());
		buffer.delete(0, buffer.length());
		this.rowsAmountSet = false;
		setTotalRowsAmount(rowamount);
		if (this.toPage == 0) {
			this.toPage = 1;
		}
		setCurrentPage(this.toPage);
		buffer.append("SELECT * FROM (SELECT pagetable.*,ROWNUM AS rowcounter FROM (");
		buffer.append(querySQL);
		buffer.append(") pagetable) subt WHERE subt.rowcounter>");
		buffer.append(this.pageStartRow - 1);
		buffer.append(" AND subt.rowcounter<=");
		buffer.append(this.pageEndRow);
		buffer.append(" and rownum <=" + this.pageSize);// 解决海量数据
		String sql = buffer.toString();
		List result = jdbcTemplate.query(buffer.toString(), mapper);
		buffer.delete(0, buffer.length());
		return result;
	}

	public String getFormAction() {
		return this.formAction;
	}

	public String description() {
		String description = "Total:" + getTotalRowsAmount() + " items " + getTotalPages() + " pages,Current page:" + this.currentPage
				+ " Previous " + this.hasPrevious + " Next:" + this.hasNext + " start row:" + this.pageStartRow + " end row:"
				+ this.pageEndRow;
		return description;
	}

	public static void main(String[] args) {
	}

	private String getUrl(HttpServletRequest request) {
		String url = "";
		Enumeration param = request.getParameterNames();
		while (param.hasMoreElements()) {
			String pname = param.nextElement().toString();
			if (pname.equalsIgnoreCase("method"))
				url = url + "?" + pname + "=" + request.getParameter(pname);
		}
		return url;
	}

	public String getElements() {
		return this.elements;
	}

	public void setElements(HttpServletRequest request) {
		String s = "";
		Enumeration param = request.getParameterNames();
		while (param.hasMoreElements()) {
			String pname = param.nextElement().toString();
			if ((!(pname.equalsIgnoreCase("page"))) && (!(pname.equalsIgnoreCase("method"))) && (!(pname.equalsIgnoreCase("toPage")))
					&& (!(pname.equalsIgnoreCase("pageSize"))) && (!(pname.equalsIgnoreCase("formAction")))
					&& (!(pname.equalsIgnoreCase("hexQueryCondition"))))
				s = s + "<input type=\"hidden\" name=\"" + pname + "\" value=\"" + request.getParameter(pname) + "\">\n";
		}
		this.elements = s;
	}

	private void setPagespara(int i) {
		if (!this.rowsAmountSet) {
			totalRowsAmount = i;
			// totalPages = totalRowsAmount / pageSize + 1;
			int k = totalRowsAmount % pageSize;
			if (k == 0) {
				totalPages = totalRowsAmount / pageSize;
			} else {
				totalPages = totalRowsAmount / pageSize + 1;
			}
			setCurrentPage(1);
			this.rowsAmountSet = true;
		}
	}

	/**
	 * ODS初始化工具用 描 述：<br/>
	 * 作 者：jin.zhang<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param firstQuerySQL
	 * @param jdbcTemplate
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public List<Map<String, Object>> getWarpedList(String firstQuerySQL, FrmJdbcTemplate jdbcTemplate) throws UnsupportedEncodingException {
		String querySQL = "";

		/** 转换数据库编码 */
		boolean NEED_ENCODE = jdbcTemplate.isNEED_ENCODE();
		String DB_ENCODE = jdbcTemplate.getDB_ENCODE();
		String APP_ENCODE = jdbcTemplate.getAPP_ENCODE();

		if ((this.hexQueryCondition == null) || (this.hexQueryCondition.equals(""))) {
			setHexQueryCondition(WrapHelper.byte2hex(firstQuerySQL.getBytes()));
			querySQL = firstQuerySQL;
		} else {
			querySQL = new String(WrapHelper.hex2byte(getHexQueryCondition()));
		}

		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT count(*) AS rown FROM ( ");
		buffer.append(querySQL);
		buffer.append(") pagetable");
		int rowamount = jdbcTemplate.queryForInt(buffer.toString());
		buffer.delete(0, buffer.length());
		this.rowsAmountSet = false;
		setTotalRowsAmount(rowamount);
		if (this.toPage == 0) {
			this.toPage = 1;
		}
		setCurrentPage(this.toPage);
		buffer.append("SELECT * FROM (SELECT pagetable.*,ROWNUM AS rowcounter FROM (");
		buffer.append(querySQL);
		buffer.append(") pagetable) subt WHERE subt.rowcounter>");
		buffer.append(this.pageStartRow - 1);
		buffer.append(" AND subt.rowcounter<=");
		buffer.append(this.pageEndRow);
		buffer.append(" and rownum <=" + this.pageSize);// 解决海量数据
		String sql = buffer.toString();
		List<Map<String, Object>> result = jdbcTemplate.queryForList(buffer.toString());
		buffer.delete(0, buffer.length());
		return result;
	}

	/**
	 * 描 述：<br/>
	 * 作 者：jin.zhang<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param firstQuerySQL
	 * @param rowamount
	 * @param jdbcTemplate
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public List<Map<String, Object>> getWarpedListNoCount(String firstQuerySQL, int totalRows, FrmJdbcTemplate jdbcTemplate)
			throws UnsupportedEncodingException {
		String querySQL = "";

		/** 转换数据库编码 */
		boolean NEED_ENCODE = jdbcTemplate.isNEED_ENCODE();
		String DB_ENCODE = jdbcTemplate.getDB_ENCODE();
		String APP_ENCODE = jdbcTemplate.getAPP_ENCODE();

		if ((this.hexQueryCondition == null) || (this.hexQueryCondition.equals(""))) {
			setHexQueryCondition(WrapHelper.byte2hex(firstQuerySQL.getBytes()));
			querySQL = firstQuerySQL;
		} else {
			querySQL = new String(WrapHelper.hex2byte(getHexQueryCondition()));
		}

		StringBuffer buffer = new StringBuffer();
		// buffer.append("SELECT count(*) AS rown FROM ( ");
		// buffer.append(querySQL);
		// buffer.append(") pagetable");
		// int rowamount = jdbcTemplate.queryForInt(buffer.toString());
		// buffer.delete(0, buffer.length());
		this.rowsAmountSet = false;
		setTotalRowsAmount(totalRows);
		if (this.toPage == 0) {
			this.toPage = 1;
		}
		setCurrentPage(this.toPage);
		buffer.append("SELECT * FROM (SELECT pagetable.*,ROWNUM AS rowcounter FROM (");
		buffer.append(querySQL);
		buffer.append(") pagetable) subt WHERE subt.rowcounter>");
		buffer.append(this.pageStartRow - 1);
		buffer.append(" AND subt.rowcounter<=");
		buffer.append(this.pageEndRow);
		buffer.append(" and rownum <=" + this.pageSize);// 解决海量数据
		String sql = buffer.toString();
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
		buffer.delete(0, buffer.length());

		return result;
	}

	/**
	 * 
	 * 描 述：sqlserver 2012<br/>
	 * 作 者：zhangjin<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param firstQuerySQL
	 * @param orderBy
	 * @param mapper
	 * @param jdbcTemplate
	 * @return
	 */
	public List getWarpedListByMssql(String querySQL, String orderBy, Class elementType, FrmJdbcTemplate jdbcTemplate) {
		return getWarpedListByMssql(querySQL, orderBy, new FrmRowMapper(elementType), jdbcTemplate);
	}

	public List getWarpedListByMssql(String querySQL, String orderBy, FrmRowMapper mapper, FrmJdbcTemplate jdbcTemplate) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT count(*) AS rown FROM ( ");
		buffer.append(querySQL);
		buffer.append(") pagetable");
		int rowamount = jdbcTemplate.queryForInt(buffer.toString());
		buffer.delete(0, buffer.length());
		this.rowsAmountSet = false;
		setTotalRowsAmount(rowamount);
		if (this.toPage == 0) {
			this.toPage = 1;
		}
		setCurrentPage(this.toPage);
		// 仅支持sqlserver2012分页，效率高
		buffer.append(querySQL + " order by " + orderBy + " offset " + (this.pageStartRow - 1) + " rows fetch next " + this.pageSize
				+ " rows only");
		List result = jdbcTemplate.query(buffer.toString(), mapper);
		buffer.delete(0, buffer.length());
		return result;
	}

	/**
	 * sqlserver 2012 描 述：<br/>
	 * 作 者：jin.zhang<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param querySQL
	 * @param orderBy
	 * @param jdbcTemplate
	 * @return
	 */
	public List<Map<String, Object>> getWarpedListByMssql(String querySQL, String orderBy, FrmJdbcTemplate jdbcTemplate) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT count(*) AS rown FROM ( ");
		buffer.append(querySQL);
		buffer.append(") pagetable");
		int rowamount = jdbcTemplate.queryForInt(buffer.toString());
		buffer.delete(0, buffer.length());
		this.rowsAmountSet = false;
		setTotalRowsAmount(rowamount);
		if (this.toPage == 0) {
			this.toPage = 1;
		}
		setCurrentPage(this.toPage);
		buffer.append(querySQL + " order by " + orderBy + " offset " + (this.pageStartRow - 1) + " rows fetch next " + this.pageSize
				+ " rows only");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(buffer.toString());
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> newKeyMap = new HashMap<String, Object>();
			Map<String, Object> valueMap = list.get(i);
			Set s = valueMap.keySet();
			Iterator<String> it = s.iterator();
			while (it.hasNext()) {
				String key = it.next();
				Object obj = valueMap.get(key);
				newKeyMap.put(formatColumnName(key), obj);
			}
			result.add(newKeyMap);
		}

		buffer.delete(0, buffer.length());
		return result;
	}

	/**
	 * sqlserver 2012 描 述：<br/>
	 * 作 者：jin.zhang<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param querySQL
	 * @param orderBy
	 * @param jdbcTemplate
	 * @return
	 */
	public List<Map<String, Object>> getWarpedListByMssql(String querySQL, String orderBy, FrmJdbcTemplate jdbcTemplate, String columnStyle) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT count(*) AS rown FROM ( ");
		buffer.append(querySQL);
		buffer.append(") pagetable");
		int rowamount = jdbcTemplate.queryForInt(buffer.toString());
		buffer.delete(0, buffer.length());
		this.rowsAmountSet = false;
		setTotalRowsAmount(rowamount);
		if (this.toPage == 0) {
			this.toPage = 1;
		}
		setCurrentPage(this.toPage);
		buffer.append(querySQL + " order by " + orderBy + " offset " + (this.pageStartRow - 1) + " rows fetch next " + this.pageSize
				+ " rows only");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(buffer.toString(), columnStyle);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> newKeyMap = new HashMap<String, Object>();
			Map<String, Object> valueMap = list.get(i);
			Set s = valueMap.keySet();
			Iterator<String> it = s.iterator();
			while (it.hasNext()) {
				String key = it.next();
				Object obj = valueMap.get(key);
				String column = key;
				newKeyMap.put(column, obj);
			}
			result.add(newKeyMap);
		}

		buffer.delete(0, buffer.length());
		return result;
	}

	/**
	 * sqlserver 2012 描 述：<br/>
	 * 作 者：jin.zhang<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param querySQL
	 * @param rowamount
	 * @param orderBy
	 * @param jdbcTemplate
	 * @return
	 */
	public List<Map<String, Object>> getWarpedListByMssqlNoCount(String querySQL, int totalRows, String orderBy,
			FrmJdbcTemplate jdbcTemplate) {
		StringBuffer buffer = new StringBuffer();
		// buffer.append("SELECT count(*) AS rown FROM ( ");
		// buffer.append(querySQL);
		// buffer.append(") pagetable");
		// int rowamount = jdbcTemplate.queryForInt(buffer.toString());
		this.rowsAmountSet = false;
		setTotalRowsAmount(totalRows);
		if (this.toPage == 0) {
			this.toPage = 1;
		}
		setCurrentPage(this.toPage);
		buffer.append(querySQL + " order by " + orderBy + " offset " + (this.pageStartRow - 1) + " rows fetch next " + this.pageSize
				+ " rows only");
		List<Map<String, Object>> list = jdbcTemplate.queryForList(buffer.toString());
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> newKeyMap = new HashMap<String, Object>();
			Map<String, Object> valueMap = list.get(i);
			Set s = valueMap.keySet();
			Iterator<String> it = s.iterator();
			while (it.hasNext()) {
				String key = it.next();
				Object obj = valueMap.get(key);
				newKeyMap.put(formatColumnName(key), obj);
			}
			result.add(newKeyMap);
		}

		buffer.delete(0, buffer.length());
		return result;
	}

	/**
	 * mongodb 使用 描 述：<br/>
	 * 作 者：zhangjin<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param tableName
	 * @param condition
	 * @param orderBy
	 * @param mongoUtil
	 * @return
	 */
	// public List<DBObject> getWarpedList(String tableName,BasicDBObject
	// condition,BasicDBObject orderBy,MongoUtil mongoUtil) {
	// int rowamount = (int)mongoUtil.getCount(tableName, condition);
	// this.rowsAmountSet = false;
	// setTotalRowsAmount(rowamount);
	// if (this.toPage == 0) {
	// this.toPage = 1;
	// }
	// setCurrentPage(this.toPage);
	// List<DBObject> result = mongoUtil.find(tableName, condition,orderBy,
	// this.toPage, pageSize);
	// return result;
	// }

	/**
	 * mongodb 使用 描 述：<br/>
	 * 作 者：zhangjin<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param tableName
	 * @param condition
	 * @param orderBy
	 * @param mongoUtil
	 * @return
	 */
	// public List<DBObject> getWarpedList(String tableName,BasicDBObject
	// condition,BasicDBObject columns,BasicDBObject orderBy,MongoUtil
	// mongoUtil) {
	// int rowamount = (int)mongoUtil.getCount(tableName, condition);
	// this.rowsAmountSet = false;
	// setTotalRowsAmount(rowamount);
	// if (this.toPage == 0) {
	// this.toPage = 1;
	// }
	// setCurrentPage(this.toPage);
	//
	// List<DBObject> result = mongoUtil.findLess(tableName,
	// condition,columns,orderBy, this.toPage, pageSize);
	// return result;
	// }

	/**
	 * mongodb 使用 描 述：<br/>
	 * 作 者：zhangjin<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param tableName
	 * @param condition
	 * @param orderBy
	 * @param mongoUtil
	 * @return
	 */
	// public List<DBObject> getWarpedList(String tableName,BasicDBObject
	// condition,BasicDBObject orderBy,MongoTemplate mongoTemplate) {
	// int rowamount = (int)mongoTemplate.getCount(tableName, condition);
	// this.rowsAmountSet = false;
	// setTotalRowsAmount(rowamount);
	// if (this.toPage == 0) {
	// this.toPage = 1;
	// }
	// setCurrentPage(this.toPage);
	//
	// List<DBObject> result = mongoTemplate.find(tableName, condition,orderBy,
	// this.toPage, pageSize);
	// return result;
	// }

	/**
	 * mongodb 使用 描 述：<br/>
	 * 作 者：zhangjin<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param tableName
	 * @param condition
	 * @param orderBy
	 * @param mongoUtil
	 * @return
	 */
	// public List<DBObject> getWarpedList(String tableName,BasicDBObject
	// condition,BasicDBObject columns,BasicDBObject orderBy,MongoTemplate
	// mongoTemplate) {
	// int rowamount = (int)mongoTemplate.getCount(tableName, condition);
	// this.rowsAmountSet = false;
	// setTotalRowsAmount(rowamount);
	// if (this.toPage == 0) {
	// this.toPage = 1;
	// }
	// setCurrentPage(this.toPage);
	// List<DBObject> result = mongoTemplate.findLess(tableName,
	// condition,columns,orderBy, this.toPage, pageSize);
	// return result;
	// }

	/**
	 * 
	 * 描 述：Mysql分页<br/>
	 * 作 者：zhangjin<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param firstQuerySQL
	 * @param mapper
	 * @param jdbcTemplate
	 * @return
	 */
	public List getWarpedListByMysql(String querySQL, Class elementType, FrmJdbcTemplate jdbcTemplate) {
		return getWarpedListByMysql(querySQL, new FrmRowMapper(elementType), jdbcTemplate);
	}

	/**
	 * 
	 * 描 述：Mysql分页<br/>
	 * 作 者：jin.zhang<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param querySQL
	 * @param mapper
	 * @param jdbcTemplate
	 * @return
	 */
	public List getWarpedListByMysql(String querySQL, FrmRowMapper mapper, FrmJdbcTemplate jdbcTemplate) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT count(*) AS rown FROM ( ");
		buffer.append(querySQL);
		buffer.append(") pagetable");
		int rowamount = jdbcTemplate.queryForInt(buffer.toString());
		buffer.delete(0, buffer.length());
		this.rowsAmountSet = false;
		setTotalRowsAmount(rowamount);
		if (this.toPage == 0) {
			this.toPage = 1;
		}
		setCurrentPage(this.toPage);
		buffer.append(querySQL + " limit " + (this.pageStartRow - 1) + "," + this.pageSize);
		List result = jdbcTemplate.query(buffer.toString(), mapper);
		buffer.delete(0, buffer.length());
		return result;
	}

	/**
	 * 
	 * 描 述：Mysql 分页<br/>
	 * 作 者：jin.zhang<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * 
	 * @param querySQL
	 * @param jdbcTemplate
	 * @return
	 */
	public List<Map<String, Object>> getWarpedListByMysql(String querySQL, String orderBy, FrmJdbcTemplate jdbcTemplate) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT count(*) AS rown FROM ( ");
		buffer.append(querySQL);
		buffer.append(") pagetable");
		int rowamount = jdbcTemplate.queryForInt(buffer.toString());
		buffer.delete(0, buffer.length());
		this.rowsAmountSet = false;
		setTotalRowsAmount(rowamount);
		if (this.toPage == 0) {
			this.toPage = 1;
		}
		setCurrentPage(this.toPage);
		buffer.append(querySQL + " limit " + (this.pageStartRow - 1) + "," + this.pageSize);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(buffer.toString());
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> newKeyMap = new HashMap<String, Object>();
			Map<String, Object> valueMap = list.get(i);
			Set s = valueMap.keySet();
			Iterator<String> it = s.iterator();
			while (it.hasNext()) {
				String key = it.next();
				Object obj = valueMap.get(key);
				newKeyMap.put(formatColumnName(key), obj);
			}
			result.add(newKeyMap);
		}

		buffer.delete(0, buffer.length());
		return result;
	}

	public String getSortname() {
		return (isUnderlineNameSortName ? CamelCaseUtils.toUnderlineName(this.sortname) : this.sortname);
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	public String getSortorder() {
		return sortorder;
	}

	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}

	/**
	 * 格式化字段名 ,返回Bean类的名称<br>
	 * 将表名中的下划线去除,第一个字母大写<br>
	 * 例：user_name -> userName
	 * 
	 * @param column
	 * @return
	 */
	public static String formatColumnName(String columnName) {
		String returnColumnName = "";
		if (columnName != null && columnName.length() > 0) {
			String tables[] = columnName.split("_");
			for (int i = 0; i < tables.length; i++) {
				if (i == 0) {
					returnColumnName += tables[i].toLowerCase();
				} else {
					returnColumnName += tables[i].substring(0, 1).toUpperCase() + tables[i].substring(1).toLowerCase();
				}
			}
		}
		return returnColumnName;
	}

	public String getSort() {
		// 转换驼峰命名，防止前台bean列名和数据库字段命名不一致
		return (isUnderlineNameSortName ? CamelCaseUtils.toUnderlineName(this.sort) : this.sort);
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setRowsAmountSet(boolean rowsAmountSet) {
		this.rowsAmountSet = rowsAmountSet;
	}

	public void setUnderlineNameSortName(boolean isUnderlineNameSortName) {
		this.isUnderlineNameSortName = isUnderlineNameSortName;
	}

	public String getQuerySql() {
		return querySql;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

	public List<?> getContent() {
		return content;
	}

	public void setContent(List<?> content) {
		this.content = content;
	}

}