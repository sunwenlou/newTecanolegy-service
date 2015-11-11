package com.sun.wen.lou.newtec.util;

import java.util.ArrayList;
import java.util.List;


/**
 * EasyUI DataGrid模型
 * 
 * @author
 * 
 */
public class DataGrid implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1627502880199590081L;
	private Long total = 0L;
	private List rows = new ArrayList();

	public Long getTotal() {
		return total;
	}

	public static DataGrid formateList(List<?> list,int total) {
		DataGrid grid = new DataGrid();
		if (list != null && list.size() > 0) {
			grid.setRows(list);
		}
		grid.setTotal(total);
		return grid;
	}

	public static DataGrid formateList(PageController page) {
		DataGrid grid = new DataGrid();
		if (page.getContent() != null && page.getContent().size() > 0) {
			grid.setRows(page.getContent());
		}
		grid.setTotal(page.getTotalRowsAmount());
		return grid;
	}
	
	public void setTotal(Long total) {
		this.total = total;
	}

	public void setTotal(int total) {
		this.total = Long.valueOf(total);
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

}
