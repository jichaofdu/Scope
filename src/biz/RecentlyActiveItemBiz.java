package biz;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import dao.RecentlyActiveItemDao;
import entity.RecentlyActiveItem;
/**
 * 最后修改时间：2015-05-17 11:26
 * 介绍：本类用于active_item的逻辑操作
 * @author 13302010019-冀超
 * 主要操作：
 * 		  1、创建动态条目
 * 		  2、花式查询各种条目
 */
public class RecentlyActiveItemBiz {
	private RecentlyActiveItemDao itemDao;
	private static RecentlyActiveItemBiz recentlyActiveItemBiz;
	public RecentlyActiveItemBiz(){
		itemDao = RecentlyActiveItemDao.getInstance();
	}
	
	/*本方法用于获取到messageBiz*/
	public static RecentlyActiveItemBiz getInstance(){
		if(recentlyActiveItemBiz == null){
			recentlyActiveItemBiz = new RecentlyActiveItemBiz();
		}
		return recentlyActiveItemBiz;
	}
	public List<RecentlyActiveItem> get_recently_active_items(int num,int[] focus_list){
		List<RecentlyActiveItem> result_list = new ArrayList<RecentlyActiveItem>();
		result_list = itemDao.get_active_item_list(num, focus_list);
		return result_list;
	}
	
	/*创建问题的动态*/
	public boolean activeitem_create_ask(int user,int question){
		Timestamp timestamp = new Timestamp(System.currentTimeMillis()); 
		RecentlyActiveItem active_item = new RecentlyActiveItem(-1,user,question,-1,timestamp);
		boolean result = itemDao.recentlyactiveitem_create(active_item);
		return result;
	}
	/*创建回答问题的动态*/
	public boolean activeitem_create_answer(int user,int question,int answer){
		Timestamp timestamp = new Timestamp(System.currentTimeMillis()); 
		RecentlyActiveItem active_item = new RecentlyActiveItem(-1,user,question,answer,timestamp);
		boolean result = itemDao.recentlyactiveitem_create(active_item);
		return result;
	}
}
