package cn.itcast.oa.service;

import java.util.List;

import cn.itcast.oa.base.DaoSupport;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Reply;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.util.QueryHelper;

public interface ReplyService extends DaoSupport<Reply>{
	@Deprecated
	/*查询指定主题中所有列表，按发表时间升序排序*/
	List<Reply> findByTopic(Topic topic);

	@Deprecated
	/*查询分页信息，当前页面，每页显示记录条数，主题*/
	PageBean getPageBeanByTopic(int pageNum, int pageSize, Topic topic);
	
	

}
