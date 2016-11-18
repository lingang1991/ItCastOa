package cn.itcast.oa.service;

import java.util.List;

import cn.itcast.oa.base.DaoSupport;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Topic;

public interface TopicService extends DaoSupport<Topic> {
	@Deprecated//表示过期了
	/*查询指定板块中的所有主题，排序：所有置顶的贴在最上面，并按最后更新时间排序，让新状态的在上面*/
	List<Topic> findByForum(Forum forum);

	@Deprecated
	//查询分页信息
		/*查询指定板块中的所有主题，排序：所有置顶的贴在最上面，并按最后更新时间排序，让新状态的在上面*/
	PageBean getPageBeanByForum(int pageNum, int pageSize, Forum forum);



	


}
