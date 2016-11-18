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

	// ��δдjspҳ��ʱ������ʹ��url����Ч��������http://localhost:8080/ItcastOA/forum_show.action?id=100&viewType=1&orderBy=3&asc=true&pageNum=7
	/*
	 * 0��ʾȫ������</option> 1��ʾȫ��������</option>
	 */
	private int viewType = 0;

	/*
	 * 0��ʾĬ������(�����ö�����ǰ�棬����������ʱ�併������) 1��ʾֻ��������ʱ������</option>
	 * 2��ʾֻ�����ⷢ��ʱ������</option> 3��ʾֻ���ظ���������</option>
	 */
	private int orderBy = 0;

	/*
	 * true��ʾ���� false��ʾ����
	 */
	private boolean asc = false;

	Forum forum = new Forum();

	@Test
	public void testQueryHelper() {
        //��������
		QueryHelper queryHelper = new QueryHelper(Topic.class, "t");
		queryHelper.addCondition("t.forum=?", forum);
		
		//��������
		queryHelper.addCondition((viewType == 1), "t.type=?", Topic.TYPE_BEST);// 1																				// ��ʾֻ��������
		queryHelper.addOrderProperty((orderBy == 1), "t.lastUpdateTime", asc);// 1																				// ��ʾֻ��������ʱ������
		queryHelper.addOrderProperty((orderBy == 2), "t.postTime", asc);// 2																		// ��ʾֻ�����ⷢ��ʱ������
		queryHelper.addOrderProperty((orderBy == 3), "t.replyCount", asc);// 3										// ��ʾֻ���ظ���������
		queryHelper.addOrderProperty("(CASE t.type WHEN 2 THEN 2 ELSE 0 END)",false);// 0 ��ʾĬ������(�����ö�����ǰ�棬����������ʱ�併������)
		queryHelper.addOrderProperty("t.lastUpdateTime", false);
		
		//

	}
}
