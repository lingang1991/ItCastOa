package cn.itcast.oa.util;

import cn.itcast.oa.domain.User;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CheckPrivilegeInterceptor extends  AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//拦截器,拦截请求
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
//	    System.out.println("----->之前");
//		
//	    String result =invocation.invoke();
//		System.out.println("----->之后");
//		
//		return result;
	//获取用户信息、权限信息
		User user=(User) ActionContext.getContext().getSession().get("user");
		String namespace = invocation.getProxy().getNamespace();
		String actionName = invocation.getProxy().getActionName();
		String privUrl= namespace + actionName;//对应的权限url
		
	//如果未登录
		if (user == null) {
			if(privUrl.startsWith("/user_login")){ //user_login或者user_loginUI
			//如果是去登录就放行
				return invocation.invoke();
			
			}else{
			//如果不是去登录，就转到登录页面(在Struts2中配置全局的result配值)
			return "loginUI";
			}
		}
		//如果已登录，就判断权限
	     else{ 
		    //如果有权限，就放行
	    	 if (user.hasPrivilegeByUrl(privUrl)) {
				return invocation.invoke();
			}
		    //如果没有权限，就转到提示页面
	    	 else {
				return "noPrivilegeError";
			}
	    	 
		}
	
	}

}
