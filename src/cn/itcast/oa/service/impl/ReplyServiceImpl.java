package cn.itcast.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.domain.Reply;
import cn.itcast.oa.domain.Topic;
import cn.itcast.oa.service.ReplyService;
@Service
@Transactional
public class ReplyServiceImpl extends DaoSupportImpl<Reply> implements ReplyService{

	@SuppressWarnings("unchecked")
	@Override
	public List<Reply> findByTopic(Topic topic) {
		return getSession().createQuery(//
				// 排序：所有置顶帖在最上面，并按最后更新时间排序，让新状态的在上面。
				"FROM Reply r WHERE r.topic=? ORDER BY r.postTime ASC")//
				.setParameter(0, topic)//
				.list();
	}

	@Override
	public void save(Reply reply) {
		//保存
		getSession().save(reply);
		
		//维护相关的信息
		Topic topic = reply.getTopic();
		Forum forum = topic.getForum();
		
		forum.setArticleCount(forum.getArticleCount()+1);//文章的数量：主题数+回帖数
		topic.setReplyCount(topic.getReplyCount()+1);//主题的回复数量
		topic.setLastReply(reply);//最后发表的回复
		topic.setLastUpdateTime(reply.getPostTime());//主题最后更新的时间（发表的时间或最后回复的时间）
		
		getSession().update(topic);
		getSession().update(forum);
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageBean getPageBeanByTopic(int pageNum, int pageSize, Topic topic) {
	   //查询数据列表
		List<Reply> list = getSession().createQuery(//
				"FROM Reply r WHERE r.topic=? ORDER BY r.postTime ASC")//
				.setParameter(0, topic)//
				.setFirstResult((pageNum-1)*pageSize)//
				.setMaxResults(pageSize)//setMaxResults(int num)每页最多显示的条数为num 这里传递pagesize
				.list();
		
		//查询总记录数
	    Long count=(Long) getSession().createQuery(//
	    		"SELECT COUNT(*) FROM Reply r WHERE r.topic=?")//
	    		.setParameter(0, topic)//
				.uniqueResult();	
	    
		return new PageBean(pageNum,pageSize,count.intValue(), list);
	}
	
	

}
