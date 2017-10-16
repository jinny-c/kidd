package com.kidd.db.commom.bean;

public class KiddPage {

	/** 分页查询开始记录位置 */
	private int begin;

	/** 分页查看下结束位置 */
	private int end;

	/** 每页显示记录数 */
	private int pageSize;

	/** 查询结果总记录数 */
	private int count;

	/** 当前页码 */
	private int current;

	/** 总共页数 */
	private int total;

	public KiddPage() {
	}

	/**
	 * 构造函数
	 * 
	 * @param begin
	 * @param pageSize
	 */
	public KiddPage(int currentPage) {
		this.current = currentPage == 0 ? 1 : currentPage;
		this.pageSize = 10;
		this.begin = (this.current - 1) * this.pageSize + 1;
		this.end = this.current * this.pageSize;
	}

	/**
	 * 构造函数(含排序)
	 * 
	 * @param currentPage
	 * @param orderByClause
	 */
	public KiddPage(int currentPage, String orderByClause) {
		this.current = currentPage == 0 ? 1 : currentPage;
		this.pageSize = 10;
		this.begin = (this.current - 1) * this.pageSize + 1;
		this.end = this.current * this.pageSize;
	}

	/**
	 * 构造函数
	 * 
	 * @param begin
	 * @param pageSize
	 */
	public KiddPage(int currentPage, int pageSize) {
		this.current = currentPage == 0 ? 1 : currentPage;
		this.pageSize = pageSize == 0 ? 10 : pageSize;
		this.begin = (this.current - 1) * this.pageSize + 1;
		this.end = this.current * this.pageSize;
	}

	public KiddPage(int begin, int pageSize, int count) {
		this(begin, pageSize);
		this.count = count;
	}

	public int getBegin() {
		return begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public void setBegin(int begin) {
		this.begin = begin;
		if (this.pageSize != 0) {
			this.current = (int) Math.floor((this.begin * 1.0d) / this.pageSize) + 1;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		if (this.begin != 0) {
			this.current = (int) Math.floor((this.begin * 1.0d) / this.pageSize) + 1;
		}
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
		this.total = (int) Math.floor((this.count * 1.0d) / this.pageSize);
		if (this.count % this.pageSize != 0) {
			this.total++;
		}
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public int getTotal() {
		if (total == 0) {
			return 1;
		}
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Page [begin=" + begin + ", end=" + end + ", pageSize=" + pageSize + ", count=" + count + ", current=" + current + ", total=" + total + "]";
	}
}
