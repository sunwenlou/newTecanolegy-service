package com.sun.wen.lou.newtec.service;

import java.util.List;

import com.sun.wen.lou.newtec.entity.Organization;
import com.sun.wen.lou.newtec.util.PageController;

public interface OrganizationService {

	public int createOrganization(Organization organization);

	public int updateOrganization(Organization organization);

	public int deleteOrganization(Long organizationId);

	Organization findOne(Long organizationId);

	List<Organization> findAll();

	List<Organization> findAllWithExclude(Organization excludeOraganization);

	void move(Organization source, Organization target);
	public PageController listOrganzation(PageController page,Organization organization);
}
