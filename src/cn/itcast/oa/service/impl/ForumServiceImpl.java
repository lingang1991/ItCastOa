package cn.itcast.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.base.DaoSupportImpl;
import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.service.ForumService;

@Service
@Transactional
public class ForumServiceImpl extends DaoSupportImpl<Forum> implements ForumService {

	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Forum> findAll() {
		
		return getSession().createQuery(//
				"FROM Forum f ORDER BY f.position")//
				.list();
	}

	
	
	@Override
	public void save(Forum forum) {
		
		super.save(forum);//保存完之后，根据id号更新自己的位置poisition
		//设置poisition的值
		forum.setPosition(forum.getId().intValue());
	}



	/*上移和下移的思路：根据id查询到当前要上移的数据，修改该数据的position字段的值，然后根据position字段
	 * 重新排序后，输出到前台，这里需要重写findAll(),因为现在是按照position排序，不是按照id号*/
	@Override
	public void moveUp(Long id) {
		// 找出相关的Forum
		Forum forum = getById(id);//当前要移动的forum
		
		//当前要移动的forum的上一个forum，取出position比当前forum值小的数据，降序排序，取第一个数据
		Forum other = (Forum) getSession().createQuery(//
				"FROM Forum f WHERE f.position < ? ORDER BY f.position DESC")//
				.setParameter(0, forum.getPosition())
				.setFirstResult(0)//
		        .setMaxResults(1)//
		        .uniqueResult();
		System.out.println("上移other查询完毕！");
		if (other==null) {
			return;
		}
		//交换position的值
		int temp = forum.getPosition();
		forum.setPosition(other.getPosition());
		other.setPosition(temp);
		
		//更新到数据库中
		getSession().update(forum);
		getSession().update(other);
		
	}

	@Override
	public void moveDown(Long id) {
		// 找出相关的Forum
				Forum forum = getById(id);//当前要移动的forum
				
				//当前要移动的forum的上一个forum，取出position比当前forum值小的数据，升序排序，取第一个数据
				Forum other = (Forum) getSession().createQuery(//
						"FROM Forum f WHERE f.position > ? ORDER BY f.position")//
						.setParameter(0, forum.getPosition())
						.setFirstResult(0)//
				        .setMaxResults(1)//
				        .uniqueResult();
				System.out.println("下移other查询完毕！");
				if (other==null) {
					return;
				}
				//交换position的值
				int temp = forum.getPosition();
				forum.setPosition(other.getPosition());
				other.setPosition(temp);
				
				//更新到数据库中
				getSession().update(forum);
				getSession().update(other);
				
		
		
	}
   
  

}
