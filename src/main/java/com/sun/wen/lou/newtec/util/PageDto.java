package com.sun.wen.lou.newtec.util;

import com.sun.wen.lou.newtec.util.PageController;

public class PageDto {
	/** 当前页数 */
	private int page;
	/** 每页条数 */
	private int rows;

	public PageController createPage() {
		return new PageController(page, rows);
	}

	public int getPage() {
		return this.page - 1 < 0 ? 0 : (this.page - 1);
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

}
