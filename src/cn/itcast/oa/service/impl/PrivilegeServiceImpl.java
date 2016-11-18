package cn.itcast.oa.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.domain.Privilege;
import cn.itcast.oa.service.PrivilegeService;
@Service
@Transactional
@SuppressWarnings("unchecked")
public class PrivilegeServiceImpl extends DaoSupportImpl<Privilege> implements PrivilegeService {

	/*查询顶级的权限*/
	//即权限的父级权限为空
	@Override
	public List<Privilege> findTopList() {
		// TODO Auto-generated method stub
		return getSession().createQuery(//
				"FROM Privilege p WHERE p.parent IS NULL"//
				).list();
	}

	/*查询所有权限的url*/
	@Override
	public Collection<String> getAllPrivilegeUrls() {
	
		return getSession().createQuery(//去DISTINCT除重复的结果
				"SELECT DISTINCT p.url FROM Privilege p WHERE p.url IS NOT NULL"//
				).list();
	}

}
