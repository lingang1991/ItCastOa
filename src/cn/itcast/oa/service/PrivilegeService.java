package cn.itcast.oa.service;

import java.util.Collection;
import java.util.List;

import cn.itcast.oa.base.DaoSupport;
import cn.itcast.oa.domain.Privilege;

public interface PrivilegeService extends DaoSupport<Privilege> {

	/*查询所有顶级的权限*/
	List<Privilege> findTopList();

	Collection<String> getAllPrivilegeUrls();

	

}
