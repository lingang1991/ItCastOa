package cn.itcast.oa.view.action;

import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.Reply;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.util.QueryHelper;

import com.opensymphony.xwork2.ActionContext;
@Controller
@Scope("prototype")
public class TopicAction extends BaseAction<Topic> {

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 private Long forumId;
	

	/*显示单个主题（主贴+回帖 列表）*/ 
	  public String show() throws Exception{
		    // 准备数据：topic
			Topic topic = topicService.getById(model.getId());
			ActionContext.getContext().put("topic", topic);
			
			// 准备数据：replyList
			//List<Reply> replyList = replyService.findByTopic(topic);
			//ActionContext.getContext().put("replyList", replyList);
			
			//准备分页信息1
//			PageBean pageBean = replyService.getPageBeanByTopic(pageNum,pageSize,topic);
//			System.out.println("pageBean获取到了！");
//			ActionContext.getContext().getValueStack().push(pageBean);
//			return "show"; 
			
//			//准备分页信息v2
//			String hql = "FROM Reply r WHERE r.topic=? ORDER BY r.postTime ASC";
//			List<Object> parameters = new ArrayList<Object>();
//			parameters.add(topic);
//			PageBean pageBean = replyService.getPageBean(pageNum,pageSize,hql,parameters);
//			System.out.println("pageBean获取到了！");
//			ActionContext.getContext().getValueStack().push(pageBean);
			
			//准备分页信息v2 最终版
			 new QueryHelper(Reply.class,"r")//
			.addCondition("r.topic=?", topic)//
			.addOrderProperty("r.postTime", true)//
			.preparePageBean(replyService, pageNum, pageSize);
			 return "show";  
			
			
	 }
	  
	  /*发表新主题页面*/ 
	  public String addUI() throws Exception{
		  
	 //准备数据
	 Forum forum = forumService.getById(forumId);
	 ActionContext.getContext().put("forum", forum);
	 
			return "addUI";	 
	 }
	  
	  /*添加完成需要重定向*/ 
	  public String add() throws Exception{
		  //封装
		 //model.setTitle(title);
		 //model.setContent(content);
		 model.setForum(forumService.getById(forumId));
		 
		 //当前直接获取的信息
	
				 
		 model.setAuthor(getCurrentUser());
		 model.setIpAddr(ServletActionContext.getRequest().getRemoteAddr());//当前请求的IP地址
		 model.setPostTime(new Date());
		 
		 //应放到业务中方法中的
		/* model.setLastReply(lastReply);
		 model.setLastUpdateTime(lastUpdateTime);
		 model.setReplyCount(replyCount);
		 model.setType(type);
		 */
		 
		 //保存
		 topicService.save(model);
		 
			return "toShow";//重定向	 
	 }

	public Long getForumId() {
		return forumId;
	}
	
	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}
	
	
	
}
