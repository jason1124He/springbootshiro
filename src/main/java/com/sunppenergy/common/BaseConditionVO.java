package com.sunppenergy.common;

import java.util.regex.Pattern;

import com.sunppenergy.utils.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;




/**
 * @Title: BaseConditionVO.java
 * @Package com.ecology.admin.core.model.page
 * @Description: (用于分页条件查询)
 * @author Administrator 55928372@qq.com
 * @date 2015年11月3日 上午10:33:26
 * @version V1.0
 */
public class BaseConditionVO {

	private int total;
	private int totalPage;
	private int page = 1;
	private int count = 10;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String endDate;
	private String keywords;
	private String order;
	private String asc;//排序方式
    private String partition;//指定分区表
	
	/**
	 * 记录状态
	 * */
	private Integer status;
	/**
	 * 是否加载全部数据
	 * */
	private Boolean getAllRecord=false;
	/**
	 * 是否获取记录总数等分页参数
	 * false时将不进行count(*)，total和totalPage为null
	 * */
	private Boolean countAllRecord=true;

	public Boolean getCountAllRecord() {
		return countAllRecord;
	}

	public void setCountAllRecord(Boolean countAllRecord) {
		this.countAllRecord = countAllRecord;
	}

    public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getCount() {
		return count<=0?10:count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		String regEx="^[A-Za-z0-9_]+$";
		boolean result= Pattern.compile(regEx).matcher(order).matches();
		if(result){
			this.order = order;
		}else{
			this.order=null;
		}
	}

	public String getPartition() {
		return partition;
	}

	public void setPartition(String partition) {
		this.partition = partition;
	}

	public String getAsc() {
		return asc;
	}

	public void setAsc(String asc) {
		if(StringUtils.equals(asc,"desc")){
			this.asc = "desc";
		}
		if(StringUtils.equals(asc,"asc")){
			this.asc = "asc";
		}
	}


	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	public Boolean getGetAllRecord() {
		return getAllRecord;
	}

	public void setGetAllRecord(Boolean getAllRecord) {
		this.getAllRecord = getAllRecord;
	}

	public int getStartIndex(){
		return (page-1)*count;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	

}
