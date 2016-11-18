package cn.itcast.oa.view.action;

import java.util.HashSet;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Privilege;
import cn.itcast.oa.domain.Role;

import com.opensymphony.xwork2.ActionContext;
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    //先在RoleService中用Service将“roleService“注入容器Bean，再使用Resource得到该bean并使用
	
//    private Long id;
//    private String name;
//    private String description;
	private Long[] privilegeIds;
	private Long topicId;
	
	
	/*列表
	 * */
	public String list() throws Exception{
     List<Role> roleList = roleService.findAll();
	 ActionContext.getContext().put("roleList", roleList);
		return "list";	
	}
	
	/*删除
	 * */
	public String delete() throws Exception{
		roleService.delete(model.getId());
		return "tolist";	
	}
	
	
	/*添加页面
	 * */
	public String addUI() throws Exception{
		return "saveUI";	
	}
	
	/*添加
	 * */
	public String add() throws Exception{
	
//		//封装到对象中
//		Role role = new Role();
//	    role.setName(model.getName());
//		role.setDescription(model.getDescription());
//		
//		//保存到数据库中
//		roleService.save(role);
		
		//model更方便
		roleService.save(model);
		return "tolist";	
	}
	
	/*修改页面
	 * */
	public String editUI() throws Exception{
		//准备回显的数据
		Role role = roleService.getById(model.getId());
		
		/*回显方法一：
		this.name=role.getName();
		this.description=role.getDescription();
		return "editUI";	
	    */
		/*回显方法二：*/
		ActionContext.getContext().getValueStack().push(role);
		return "saveUI";
	}
	
	
	/*修改
	 * */
	public String edit() throws Exception{
		//1.从数据库获取源对象
		Role role = roleService.getById(model.getId());
		//2.设置要修改的属性
		role.setName(model.getName());
		role.setDescription(model.getDescription());
		//3.提交到数据库
		roleService.update(role);
		return "tolist";	
	}
	
	
	/*设置权限页面
	 * */
	public String setPrivilegeUI() throws Exception{
		//准备回显的数据
		Role role = roleService.getById(model.getId());
		
		/*回显方法一：
		this.name=role.getName();
		this.description=role.getDescription();
		return "editUI";	
	    */
		/*回显方法二：*/
		ActionContext.getContext().getValueStack().push(role);
		
		//如果岗位role的权限不为空，则把权限的id号找出来，放在数组privilegeIds中
		if (role.getPrivileges() != null) {
			int index=0;
			privilegeIds = new Long[role.getPrivileges().size()];
			for(Privilege priv : role.getPrivileges()){
				privilegeIds[index++] = priv.getId();
			}
		}	
		
		//准备权限列表数据privilegeList
		List<Privilege> privilegeList = privilegeService.findAll();
		ActionContext.getContext().put("privilegeList", privilegeList);
		
		
		
		return "setPrivilegeUI";
	}
	
	
	/*设置权限
	 * */
	public String setPrivilege() throws Exception{
		//1.从数据库获取源对象
		Role role = roleService.getById(model.getId());
		//2.设置要修改的属性
		List<Privilege> privilegeList =privilegeService.getByIds(privilegeIds);
		role.setPrivileges(new HashSet<Privilege>(privilegeList));
		
		//3.提交到数据库
		roleService.update(role);
		return "tolist";	
	}

	public Long[] getPrivilegeIds() {
		return privilegeIds;
	}

	public void setPrivilegeIds(Long[] privilegeIds) {
		this.privilegeIds = privilegeIds;
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	

}
