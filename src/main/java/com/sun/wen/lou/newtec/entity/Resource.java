package com.sun.wen.lou.newtec.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

public class Resource implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id; //编号
    private String name; //资源名称
    /**
	 * 类型(1：菜单 2：按钮 3：操作)
	 */
    private String type ; //资源类型
    private String url; //资源路径
    private String permission; //权限字符串
    private Long parentId; //父编号
    private String parentIds; //父编号列表
    private Boolean available = Boolean.FALSE;
    
    
	/**
	 * 资源标识
	 */
	@NotBlank
	private String resourceFlag;
	
	/**
	 * ID树
	 */
	private String idTree;
	
	/**
	 * 资源描述
	 */
	private String resourceDesc;
	
	/**
	 * 菜单排序
	 */
	private Integer resourceSort;
	

	public String getResourceFlag() {
		return resourceFlag;
	}

	public void setResourceFlag(String resourceFlag) {
		this.resourceFlag = resourceFlag;
	}

	public String getIdTree() {
		return idTree;
	}

	public void setIdTree(String idTree) {
		this.idTree = idTree;
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

	/**
	 * 控制表格树关闭状态
	 */
	private String state;


    public static enum ResourceType {
    	menu("菜单"),dosomething("操作"), button("按钮");

        private final String info;
        private ResourceType(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
        }
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public boolean isRootNode() {
        return parentId == 0;
    }

    public String makeSelfAsParentIds() {
        return getParentIds() + getId() + "/";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resource resource = (Resource) o;

        if (id != null ? !id.equals(resource.id) : resource.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", permission='" + permission + '\'' +
                ", parentId=" + parentId +
                ", parentIds='" + parentIds + '\'' +
                ", available=" + available +
                '}';
    }
}
