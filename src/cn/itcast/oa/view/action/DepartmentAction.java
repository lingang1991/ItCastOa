package cn.itcast.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Department;
import cn.itcast.oa.util.DepartmentUtils;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class DepartmentAction extends BaseAction<Department>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long parentId;
	/*
	 * 列表
	 */
	public String list() throws Exception {
		List<Department> departmentList = null;
		/*parentId为空表示没有传递id号，查询的是顶级部门，如果点击了则传递id号，显示的是子部门*/
		if (parentId ==null) {
			departmentList = departmentService.findTopList();	
		}else {
			departmentList = departmentService.findChildren(parentId);	
		    Department parent = departmentService.getById(parentId);
		    ActionContext.getContext().put("parent", parent);
		}
		ActionContext.getContext().put("departmentList", departmentList);
		return "list";
	}

	/*
	 * 删除
	 */
	public String delete() throws Exception {
		departmentService.delete(model.getId());
		return "tolist";
	}

	/*
	 * 添加页面
	 */
	public String addUI() throws Exception {
		// 准备数据departmentLists
		List<Department> topList = departmentService.findTopList();//新建时，选取当前页面的所有父顶点
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		return "saveUI";
	}

	/*
	 * 添加
	 */
	public String add() throws Exception {

		// 选择了上级部门，就可以获取到id（即parentId），根据id号查询到上级的信息，和下级信息一起保存在栈中（即model）
		//,通过获取parent.name获取上级部门的名称，以及name获取本级部门的名称
	
		// //封装到对象中
		// Role role = new Role();
		// role.setName(model.getName());
		// role.setDescription(model.getDescription());

		// parentId查询出上级部门的名称，并一起放入栈中
		Department parent = departmentService.getById(parentId);
		model.setParent(parent);

		// //保存到数据库中
		// roleService.save(role);

		// model更方便
		departmentService.save(model);
		return "tolist";
	}

	/*
	 * 修改页面
	 */
	public String editUI() throws Exception {
		// 准备数据departmentLists
		List<Department> topList = departmentService.findTopList();//新建时，选取当前页面的所有父顶点
		List<Department> departmentList = DepartmentUtils.getAllDepartments(topList);
				ActionContext.getContext().put("departmentList", departmentList);
		
		// 准备回显的数据
		Department department = departmentService.getById(model.getId());

		/*
		 * 回显方法一： this.name=role.getName();
		 * this.description=role.getDescription(); return "editUI";
		 */
		/* 回显方法二： */
		ActionContext.getContext().getValueStack().push(department);
		if (department.getParent()!=null) {
			parentId = department.getParent().getId();
		}
		return "saveUI";
	}

	/*
	 * 修改
	 */
	public String edit() throws Exception {
		// 1.从数据库获取源对象
		Department department = departmentService.getById(model.getId());
		// 2.设置要修改的属性
		department.setName(model.getName());
		department.setDescription(model.getDescription());
		department.setParent(departmentService.getById(parentId));//设置新的上级
		// 3.提交到数据库
		departmentService.update(department);
		return "tolist";
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	
	
}
