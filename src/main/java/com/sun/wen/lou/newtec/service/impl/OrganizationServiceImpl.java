package com.sun.wen.lou.newtec.service.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.wen.lou.newtec.entity.Organization;
import com.sun.wen.lou.newtec.mapper.OrganizationMapper;
import com.sun.wen.lou.newtec.service.OrganizationService;
import com.sun.wen.lou.newtec.util.PageController;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service("OrganizationService")
public class OrganizationServiceImpl implements OrganizationService {
	@Autowired
	private OrganizationMapper organizationMapper;

	@Override
	public int createOrganization(Organization organization) {
		return organizationMapper.createOrganization(organization);
	}

	@Override
	public int updateOrganization(Organization organization) {
		return organizationMapper.updateOrganization(organization);
	}

	@Override
	public int deleteOrganization(Long organizationId) {
		return organizationMapper.deleteOrganization(organizationId);
	}

	@Override
	public Organization findOne(Long organizationId) {
		return organizationMapper.findOne(organizationId);
	}

	@Override
	public List<Organization> findAll() {
		return organizationMapper.findAll();
	}

	@Override
	public List<Organization> findAllWithExclude(
			Organization excludeOraganization) {
		return organizationMapper.findAllWithExclude(excludeOraganization);
	}

	@Override
	public void move(Organization source, Organization target) {

		/*
		 * String moveSourceSql =
		 * "update sys_organization set parent_id=?,parent_ids=? where id=?";
		 * jdbcTemplate.update(moveSourceSql, target.getId(),
		 * target.getParentIds(), source.getId()); String
		 * moveSourceDescendantsSql =
		 * "update sys_organization set parent_ids=concat(?, substring(parent_ids, length(?))) where parent_ids like ?"
		 * ; jdbcTemplate.update(moveSourceDescendantsSql,
		 * target.makeSelfAsParentIds(), source.makeSelfAsParentIds(),
		 * source.makeSelfAsParentIds() + "%");
		 */
		Organization tmp = new Organization();
		try {
			BeanUtils.copyProperties(tmp, source);
			tmp.setParentId(target.getId());
			tmp.setParentIds(target.getParentIds()+target.getId()+"/");
			organizationMapper.updateOrganization(tmp);
			// organizationMapper.updateOrganizationMany(target.makeSelfAsParentIds(),
			// source.makeSelfAsParentIds());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 分页查询
	 * 
	 * @param page
	 * @param organization
	 * @return
	 */
	@Override
	public PageController listOrganzation(PageController page,
			Organization organization) {
		List<Organization> list = organizationMapper.listOrganzation(page,
				organization);
		if (list != null) {
			page.setContent(list);
		}
		return page;
	}
}
