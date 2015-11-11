package com.sun.wen.lou.newtec.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sun.wen.lou.newtec.entity.Organization;
import com.sun.wen.lou.newtec.util.PageController;

public interface OrganizationMapper {

	public int createOrganization(Organization organization);

	public int updateOrganization(Organization organization);

	public int deleteOrganization(Long organizationId);

	Organization findOne(Long organizationId);

	List<Organization> findAll();

	List<Organization> findAllWithExclude(Organization excludeOraganization);

	void move(Organization source, Organization target);

	void updateOrganizationMany(@Param("tarparentids") String tarparentids,
			@Param("sourceparentids") String sourceparentids);

	public List<Organization> listOrganzation(
			@Param("page") PageController page,
			@Param("organization") Organization organization);
}
