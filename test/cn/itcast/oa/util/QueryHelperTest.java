package cn.itcast.oa.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cn.itcast.oa.domain.Forum;
import cn.itcast.oa.domain.Topic;

public class QueryHelperTest {

	/**
	 * 
	 */

	// 还未写jsp页面时，可以使用url测试效果：例如http://localhost:8080/ItcastOA/forum_show.action?id=100&viewType=1&orderBy=3&asc=true&pageNum=7
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

	Forum forum = new Forum();

	@Test
	public void testQueryHelper() {
        //过滤条件
		QueryHelper queryHelper = new QueryHelper(Topic.class, "t");
		queryHelper.addCondition("t.forum=?", forum);
		
		//排序条件
		queryHelper.addCondition((viewType == 1), "t.type=?", Topic.TYPE_BEST);// 1																				// 表示只看精华帖
		queryHelper.addOrderProperty((orderBy == 1), "t.lastUpdateTime", asc);// 1																				// 表示只按最后更新时间排序
		queryHelper.addOrderProperty((orderBy == 2), "t.postTime", asc);// 2																		// 表示只按主题发表时间排序
		queryHelper.addOrderProperty((orderBy == 3), "t.replyCount", asc);// 3										// 表示只按回复数量排序
		queryHelper.addOrderProperty("(CASE t.type WHEN 2 THEN 2 ELSE 0 END)",false);// 0 表示默认排序(所有置顶帖在前面，并按最后更新时间降序排列)
		queryHelper.addOrderProperty("t.lastUpdateTime", false);
		
		//

	}
}
