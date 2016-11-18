package cn.itcast.oa.view.action;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.oa.base.BaseAction;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.util.QueryHelper;

import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ForumAction extends BaseAction<Forum> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//还未写jsp页面时，可以使用url测试效果：例如http://localhost:8080/ItcastOA/forum_show.action?id=100&viewType=1&orderBy=3&asc=true&pageNum=7
	/*
	 * 0表示全部主题</option> 1表示全部精华贴</option>
	 */
	private int viewType = 0;

	/*
	 * 0表示默认排序(所有置顶帖在前面，并按最后更新时间降序排列) 1表示只按最后更新时间排序</option>
	 * 2表示只按主题发表时间排序</option> 3表示只按回复数量排序</option>
	 */
	private int orderBy = 0;

	/*
	 * true表示升序 false表示降序
	 */
	private boolean asc = false;

	/* 板块列表 */
	public String list() throws Exception {
		List<Forum> forumList = forumService.findAll();
		ActionContext.getContext().put("forumList", forumList);
		return "list";
	}

	/* 显示单个板块（主题列表） */
	public String show() throws Exception {
		// 准备数据forum
		Forum forum = forumService.getById(model.getId());
		ActionContext.getContext().put("forum", forum);

		// 准备主题数据
		// List<Topic> topicList = topicService.findByForum(forum);
		// ActionContext.getContext().put("topicList", topicList);

		// 准备分页信息v1
		// PageBean pageBean =
		// topicService.getPageBeanByForum(pageNum,pageSize,forum);
		// System.out.println("fourm pageBean获取到了！");
		// ActionContext.getContext().getValueStack().push(pageBean);
		// return "show";

		// 准备分页信息v2
		// String hql =
		// "FROM Topic t WHERE t.forum=? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC, t.lastUpdateTime DESC";
		// List<Object> parameters = new ArrayList<Object>();
		// parameters.add(forum);
		// PageBean pageBean = topicService.getPageBean(pageNum, pageSize, hql,
		// parameters);
		// System.out.println("fourm pageBean获取到了！");
		// ActionContext.getContext().getValueStack().push(pageBean);
		// return "show";

		// 准备分页信息 v3,根据筛选条件进行hql语句拼接
//		String hql = "FROM Topic t WHERE t.forum=? ";
//		List<Object> parameters = new ArrayList<Object>();
//		parameters.add(forum);
//
//		if (viewType == 1) { // 1 表示只看精华帖
//			hql += " AND t.type=? ";
//			parameters.add(Topic.TYPE_BEST);
//		}
//		if (orderBy == 1) { // 1 表示只按最后更新时间排序
//			hql += " ORDER BY t.lastUpdateTime " + (asc ? "ASC" : "DESC");
//		} else if (orderBy == 2) { // 2 表示只按主题发表时间排序
//			hql += " ORDER BY t.postTime " + (asc ? "ASC" : "DESC");
//		} else if (orderBy == 3) { // 3 表示只按回复数量排序
//			hql += " ORDER BY t.replyCount " + (asc ? "ASC" : "DESC");
//		} else { // 0 表示默认排序(所有置顶帖在前面，并按最后更新时间降序排列)
//			hql += " ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC, t.lastUpdateTime DESC";
//		}
//
		
		
		// 准备分页信息 v4,根据筛选条件进行hql语句拼接
		//过滤条件
        new QueryHelper(Topic.class, "t")
        .addCondition("t.forum=?", forum)
        
        //排序条件
        .addCondition((viewType == 1), "t.type=?", Topic.TYPE_BEST)// 1																				// 表示只看精华帖
        .addOrderProperty((orderBy == 1), "t.lastUpdateTime", asc)// 1																				// 表示只按最后更新时间排序
        .addOrderProperty((orderBy == 2), "t.postTime", asc)// 2																		// 表示只按主题发表时间排序
        .addOrderProperty((orderBy == 3), "t.replyCount", asc)// 3										// 表示只按回复数量排序
        .addOrderProperty("(CASE t.type WHEN 2 THEN 2 ELSE 0 END)",false)// 0 表示默认排序(所有置顶帖在前面，并按最后更新时间降序排列)
        .addOrderProperty("t.lastUpdateTime", false)//
        .preparePageBean(forumService, pageNum, pageSize);       
	 	return "show"; 

	}

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}

}
