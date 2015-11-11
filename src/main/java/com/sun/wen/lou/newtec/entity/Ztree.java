package com.sun.wen.lou.newtec.entity;

import java.io.Serializable;

public class Ztree
  implements Serializable
{
  private static final long serialVersionUID = -6209178393839642165L;
  private String id;
  private String parentId;
  private String name;
  private String isParent = "false";
  private boolean checked = false;
  private boolean open = false;
  private String file;

  public String getId()
  {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getParentId()
  {
    return this.parentId;
  }

  public void setParentId(String parentId)
  {
    this.parentId = parentId;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIsParent() {
    return this.isParent;
  }

  public void setIsParent(String isParent) {
    this.isParent = isParent;
  }

  public String getFile() {
    return this.file;
  }

  public void setFile(String file) {
    this.file = file;
  }

  public boolean isChecked() {
    return this.checked;
  }
  public void setChecked(boolean checked) {
    this.checked = checked;
  }

  public boolean isOpen() {
    return this.open;
  }
  public void setOpen(boolean open) {
    this.open = open;
  }
}