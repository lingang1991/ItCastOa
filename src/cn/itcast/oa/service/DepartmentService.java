package cn.itcast.oa.service;

import java.util.List;

import cn.itcast.oa.base.DaoSupport;
import cn.itcast.oa.domain.Department;

public interface DepartmentService extends DaoSupport<Department> {
	
	List<Department> findAll();

	void delete(Long id);

	void save(Department department);

	Department getById(Long id);

	void update(Department department);

	/*查询顶级部门列表*/
	List<Department> findTopList();

	/*查询子部门列表*/
	List<Department> findChildren(Long parentId);


}
