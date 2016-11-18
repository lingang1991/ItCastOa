package cn.itcast.oa.view.action;

import java.util.HashSet;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Department;
import cn.itcast.oa.domain.Role;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.util.DepartmentUtils;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long departmentId;
	private Long[] roleIds;

	/** 列表 */
	public String list() throws Exception {
		List<User> userList = userService.findAll();
		ActionContext.getContext().put("userList", userList);
		return "list";
	}

	/** 删除 */
	public String delete() throws Exception {
		userService.delete(model.getId());
		return "tolist";
	}

	/** 添加页面 */
	public String addUI() throws Exception {
		// 准备数据departmentLists
		List<Department> topList = departmentService.findTopList();// 新建时，选取当前页面的所有父顶点
		List<Department> departmentList = DepartmentUtils
				.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);

		// 准备数据roleList
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);
		return "saveUI";
	}

	/** 添加 */
	public String add() throws Exception {
		// 封装到对象中（model设置未封装的属性）
		// 封装部门
		model.setDepartment(departmentService.getById(departmentId));
		// 封装岗位
		List<Role> roleList = roleService.getByIds(roleIds);
		model.setRoles(new HashSet<Role>(roleList));
		String md5Digest = DigestUtils.md5Hex("1234") ;
		model.setPassword(md5Digest);
		//保存到数据库
		userService.save(model);
		return "tolist";
	}

	/** 修改页面 */
	public String editUI() throws Exception {
		// 准备数据departmentLists
		List<Department> topList = departmentService.findTopList();// 新建时，选取当前页面的所有父顶点
		List<Department> departmentList = DepartmentUtils
				.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);

		// 准备数据roleList
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);

		// 回显数据
		User user = userService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(user);
		  //补全回显数据
		if (user.getDepartment()!=null) {
			departmentId = user.getDepartment().getId();
		}
		if (user.getRoles()!=null) {
			roleIds = new Long[user.getRoles().size()];
			int index=0;
			for(Role role:user.getRoles()){
				roleIds[index++] = role.getId();
			}
			
		}
		
		return "saveUI";
	}

	/** 修改 */
	public String edit() throws Exception {
		//1.从数据库获取源对象
		User user = userService.getById(model.getId());
		
		//2.设置要修改的属性
		user.setLoginName(model.getLoginName());
		user.setName(model.getName());
		user.setGender(model.getGender());
		user.setPhoneNumber(model.getPhoneNumber());
		user.setEmail(model.getEmail());
		user.setDescription(model.getDescription());
		//设置部门
		user.setDepartment(departmentService.getById(departmentId));
		// 设置岗位
		List<Role> roleList = roleService.getByIds(roleIds);
		user.setRoles(new HashSet<Role>(roleList));
		
		//3.提交到数据库
		userService.update(user);
		return "tolist";
	}

	/** 初始化密码 */
	public String initPassword() throws Exception {
		//1.从数据库获取源对象
				User user = userService.getById(model.getId());
				
				//2.设置要修改的属性
				String md5Digest = DigestUtils.md5Hex("1234") ;
				user.setPassword(md5Digest);
				
				//3.提交到数据库
				userService.update(user);
		return "tolist";
	}
	
	/*登录页面*/
	public String loginUI() throws Exception{
		return "loginUI";
		
	}
	
	/*登录*/
	public String login() throws Exception{
		
		User user = userService.findByLoginNameAndPassword(model.getLoginName(),model.getPassword());
		if (user == null) {
			addFieldError("login", "用户名或密码不正确！");
			return "loginUI";
			
		}else{
			ActionContext.getContext().getSession().put("user", user);
		}
		return "toIndex";
		
	}
	
	/*注销*/
	public String logout() throws Exception{
		ActionContext.getContext().getSession().remove("user");
		return "logout";
		
	}

	// --
	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

}
