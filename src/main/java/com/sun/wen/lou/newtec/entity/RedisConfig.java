package com.sun.wen.lou.newtec.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RedisConfig
{
  public static String rbac_interface_resource;
  public static String rbac_interface_resourceAll;
  public static String rbac_interface_permissions;

  public String getRbac_interface_resource()
  {
    return rbac_interface_resource;
  }

  @Value("#{rbacInterfaceProperties['rbac.interface.resource.url']}")
  public void setRbac_interface_resource(String rbac_interface_resource)
  {
    RedisConfig.rbac_interface_resource = rbac_interface_resource;
  }

  public String getRbac_interface_resourceAll() {
    return rbac_interface_resourceAll;
  }

  @Value("#{rbacInterfaceProperties['rbac.interface.resourceAll.url']}")
  public void setRbac_interface_resourceAll(String rbac_interface_resourceAll)
  {
    RedisConfig.rbac_interface_resourceAll = rbac_interface_resourceAll;
  }

  public String getRbac_interface_permissions() {
    return rbac_interface_permissions;
  }

  @Value("#{rbacInterfaceProperties['rbac.interface.permissions.url']}")
  public void setRbac_interface_permissions(String rbac_interface_permissions)
  {
    RedisConfig.rbac_interface_permissions = rbac_interface_permissions;
  }
}