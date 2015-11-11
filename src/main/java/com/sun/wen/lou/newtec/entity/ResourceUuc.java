package com.sun.wen.lou.newtec.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

/** 
 * <br>类 名: ResourceUuc 
 * <br>描 述: UUC资源实体类
 */
public class ResourceUuc implements Serializable {

	private static final long serialVersionUID = 1795609631901321740L;
	
	/**
	 * 资源ID
	 */
	private Integer resourceId;
	/**
	 * 资源名称
	 */
	@NotBlank
	private String resourceName;
	/**
	 * 资源标识
	 */
	@NotBlank
	private String resourceFlag;
	/**
	 * 资源路径
	 */
	private String resourcePath;
	/**
	 * 父ID
	 */
	protected Integer parentId;
	/**
	 * ID树
	 */
	private String idTree;
	/**
	 * 类型(1：菜单 2：按钮 3：操作)
	 */
	@NotBlank
	private String resourceType;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 修改时间
	 */
	private Date updateDate;
	/**
	 * 操作人
	 */
	private String agentName;
	/**
	 * 资源描述
	 */
	private String resourceDesc;
	/**
	 * 菜单排序
	 */
	private Integer resourceSort;
	/**
	 * 控制表格树关闭状态
	 */
	private String state;

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceFlag() {
		return resourceFlag;
	}

	public void setResourceFlag(String resourceFlag) {
		this.resourceFlag = resourceFlag;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getIdTree() {
		return idTree;
	}

	public void setIdTree(String idTree) {
		this.idTree = idTree;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getResourceDesc() {
		return resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}

	public Integer getResourceSort() {
		return resourceSort;
	}

	public void setResourceSort(Integer resourceSort) {
		this.resourceSort = resourceSort;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}