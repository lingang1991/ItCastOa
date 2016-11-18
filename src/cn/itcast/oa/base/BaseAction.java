package cn.itcast.oa.base;

import java.lang.reflect.ParameterizedType;

import javax.annotation.Resource;

import cn.itcast.oa.domain.User;
import cn.itcast.oa.service.DepartmentService;
import cn.itcast.oa.service.ForumService;
import cn.itcast.oa.service.PrivilegeService;
import cn.itcast.oa.service.ReplyService;
import cn.itcast.oa.service.RoleService;
import cn.itcast.oa.service.TopicService;
import cn.itcast.oa.service.UserService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
@SuppressWarnings("unchecked")
public abstract class BaseAction<T> extends ActionSupport implements
ModelDriven<T>{
	/**
	 * ---------------1.ModelDriven的支持-----------------
	 */
	private static final long serialVersionUID = 1L;
	
	protected T model;
	
	public BaseAction() {
		try{
		// 通过反射得到model的真实类型
       ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass(); // 获取当前new的对象的 泛型的父类 类型
		Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];;
		//通过反射创建model的实例
		model = clazz.newInstance(); 
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public T getModel() {
		return model;
	}
	
	/**
	 *-----------------2. service实例的声明------------------
	 */
	@Resource
	protected DepartmentService departmentService;
	
	@Resource
	protected RoleService roleService;
	
	@Resource
	protected UserService userService;

	@Resource
	protected PrivilegeService privilegeService;
	
	@Resource
	protected ForumService forumService;
	
	@Resource
	protected TopicService topicService;

	@Resource
	protected  ReplyService replyService;
	
	
	 //获取当前登录用户
	  protected User getCurrentUser(){
		  
		  return (User) ActionContext.getContext().getSession().get("user");
	  }
	//======================分页用的参数和get()、set()方法=====================
	    protected int pageNum=1;//当前页
	    protected int pageSize=10;//每页显示多少条记录

		public int getPageNum() {
			return pageNum;
		}

		public void setPageNum(int pageNum) {
			this.pageNum = pageNum;
		}

		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}
		
		
	  
}
