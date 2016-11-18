package cn.itcast.oa.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.itcast.oa.domain.Department;

public class DepartmentUtils {

	
	/*
	 * 遍历部门树，把所有的部门遍历出来放到同一个集合中返回，并且其中所有部门的名称都修改了，以表示层次性。
	 * */
	public static List<Department> getAllDepartments(List<Department> topList) {
	List<Department> list = new ArrayList<Department>();
	walkDepartmentTreeList(topList,"┣",list);
		return list;
	}

	private static void walkDepartmentTreeList(Collection<Department> topList,String prefix,
			List<Department> list) {
		for(Department top:topList){
		//顶点
		Department copy = new Department();
		  //使用副本，因为源对象在session中
		copy.setId(top.getId());
		copy.setName(prefix+top.getName());
		list.add(copy);
		//子树
		walkDepartmentTreeList(top.getChildren(),"　"+prefix ,list);//使用全角的空格（中文的空格）
		
		}
		
	}

}
