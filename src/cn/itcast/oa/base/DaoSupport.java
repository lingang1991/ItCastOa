package cn.itcast.oa.base;

import java.util.List;

import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.util.QueryHelper;

public interface DaoSupport<T> {

	/**
	 * 保存实体
	 * 
	 * @param entity
	 */
	void save(T entity);

	/**
	 * 删除实体
	 * 
	 * @param id
	 */
	void delete(Long id);

	/**
	 * 更新实体
	 * 
	 * @param entity
	 */
	void update(T entity);

	/**
	 * 按id查询
	 * 
	 * @param id
	 * @return
	 */
	T getById(Long id);

	/**
	 * 按id查询
	 * 
	 * @param ids
	 * @return
	 */
	List<T> getByIds(Long[] ids);

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	List<T> findAll();
	
	/**根据当前页
	 * 查询该页数据列表
	 * sql表示Hql语句
	 * parameters参数列表其顺序和Hql中的问号一一对应
	 * @return
	 */
	@Deprecated
	PageBean getPageBean(int pageNum,int pageSize,String sql,List<Object> parameters);

	/*查询分页信息，queryHelper变量中包含了hql语句和回复总数量(最终版)*/
	PageBean getPageBean(int pageNum, int pageSize, QueryHelper queryHelper);
	
	
}
