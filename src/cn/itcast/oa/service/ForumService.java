package cn.itcast.oa.service;

import java.util.List;

import cn.itcast.oa.base.DaoSupport;
import cn.itcast.oa.domain.Forum;

public interface ForumService extends DaoSupport<Forum> {
	
	List<Forum> findAll();

	void delete(Long id);

	void save(Forum forum);

	Forum getById(Long id);

	void update(Forum forum);

	void moveUp(Long id);

	void moveDown(Long id);

	


}
