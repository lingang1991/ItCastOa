package cn.itcast.oa.base;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.util.QueryHelper;
@Transactional
@SuppressWarnings("unchecked")
public abstract class DaoSupportImpl<T> implements DaoSupport<T> {

	@Resource
	private SessionFactory sessionFactory;
	private Class<T> clazz;

	public DaoSupportImpl() {
		// 使用反射技术得到T的真实类型
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass(); // 获取当前new的对象的 泛型的父类 类型
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0]; // 获取第一个类型参数的真实类型
		System.out.println("clazz ---> " + clazz);
	}

	/**
	 * 获取当前可用的Session
	 * 
	 * @return
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(T entity) {
		getSession().save(entity);
	}

	public void update(T entity) {
		getSession().update(entity);
	}

	public void delete(Long id) {
		Object obj = getById(id);
		if (obj != null) {
			getSession().delete(obj);
		}
	}

	public T getById(Long id) {
		if (id==null) {
			return null;
		}else{
		return (T) getSession().get(clazz, id);
	
		}
	}	


	public List<T> getByIds(Long[] ids) {
		if (ids==null || ids.length == 0) {
			return Collections.EMPTY_LIST;
		}else{
		return getSession().createQuery(//
				"FROM "+ clazz.getSimpleName() +" WHERE id IN (:ids)")//
				.setParameterList("ids", ids)//
				.list();
		}
	}

	public List<T> findAll() {
		return getSession().createQuery(//
				"FROM " + clazz.getSimpleName())//
				.list();
	}

	@Deprecated
	/*公共的查询信息分页的方法*/
	@Override
	public PageBean getPageBean(int pageNum, int pageSize, String hql,
			List<Object> parameters) {
		
		System.out.println("=========调用了DaoSupportImpl.getPageBean()=======");
		//查询数据列表
		
		Query query = getSession().createQuery(hql);//根据传递来的hql查询，其中？号由parameters一一对应
		if (parameters != null) {
			for (int i = 0; i < parameters.size(); i++) {
				query.setParameter(i, parameters.get(i));
			}
		}
		query.setFirstResult((pageNum-1)*pageSize);//
		query.setMaxResults(pageSize);//setMaxResults(int num)每页最多显示的条数为num 这里传递pagesize
        List list = query.list();//执行查询
		
		//查询总记录数
       Query countQuery = getSession().createQuery("SELECT COUNT(*) "+hql);
       if (parameters != null) {
			for (int i = 0; i < parameters.size(); i++) {
				countQuery.setParameter(i, parameters.get(i));
			}
		}
			    
                Long count=(Long) countQuery.uniqueResult();
	            System.out.println("getPageBeanByForum完成！");
	    
		return new PageBean(pageNum,pageSize,count.intValue(), list);	
	
	}
	
	/*公共的查询信息分页的方法（最终版）*/
	@Override
	public PageBean getPageBean(int pageNum, int pageSize, QueryHelper queryHelper) {
		
		System.out.println("=========调用了DaoSupportImpl.getPageBean(int pageNum, int pageSize, QueryHelper queryHelper)=======");
		//查询数据列表
		
		Query query = getSession().createQuery(queryHelper.getListQueryHql());//根据传递来的hql查询，其中？号由parameters一一对应
		List<Object> parameters = queryHelper.getParameters();
		if (parameters != null) {
			for (int i = 0; i < parameters.size(); i++) {
				query.setParameter(i, parameters.get(i));
			}
		}
		query.setFirstResult((pageNum-1)*pageSize);//
		query.setMaxResults(pageSize);//setMaxResults(int num)每页最多显示的条数为num 这里传递pagesize
        List list = query.list();//执行查询
		
		//查询总记录数
       Query countQuery = getSession().createQuery(queryHelper.getCountQueryHql());
       if (parameters != null) {
			for (int i = 0; i < parameters.size(); i++) {
				countQuery.setParameter(i, parameters.get(i));
			}
		}
			    
                Long count=(Long) countQuery.uniqueResult();
	            System.out.println("getPageBeanByForum完成！");
	    
		return new PageBean(pageNum,pageSize,count.intValue(), list);	
	
	}


	
	
}
