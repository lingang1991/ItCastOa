package cn.itcast.oa.util;

import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.itcast.oa.domain.Privilege;
import cn.itcast.oa.service.PrivilegeService;

public class InitListener implements ServletContextListener {

	
	
     //监听器，最大作用域的容器对象
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//获取容器与相关Servlet对象,最大作用域的容器对象使用WebApplicationContextUtils类获取
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		PrivilegeService privilegeService = (PrivilegeService) ac.getBean("privilegeServiceImpl");

		//准备数据topPrivilegeList
		List<Privilege> topPrivilegeList = privilegeService.findTopList();
		sce.getServletContext().setAttribute("topPrivilegeList", topPrivilegeList);
        System.out.println("-------->数据已经准备好<--------");
        
        //准备数据：allprivilegeUrls
        Collection<String> allPrivilegeUrls = privilegeService.getAllPrivilegeUrls();
        sce.getServletContext().setAttribute("allPrivilegeUrls", allPrivilegeUrls);
        System.out.println("-------->数据已经准备好<--------");
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}
	
}
