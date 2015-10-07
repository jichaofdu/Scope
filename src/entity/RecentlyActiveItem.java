package entity;
import java.sql.Timestamp;
/**
 * 最后修改时间：2015-05-15 16:27
 * @author 13302010019-jichao
 * 介绍：RecentlyActiveItem是活动状态表的条目，维持item的id，操作者的id，问题的id，回答的id，
 * 		以及最近活跃时间。
 *      活跃条目没有修改的必要，所以一律不提供set方法
 */
public class RecentlyActiveItem {
	private int item_id;
	private int item_user_id;
	private int item_question_id;
	private int item_questionanswer_id;
	private Timestamp item_active_time;
	
	public RecentlyActiveItem(int ID,int userID,int questionID,int questionanswerID
			,Timestamp active_time){
		this.item_id = ID;
		this.item_user_id = userID;
		this.item_question_id = questionID;
		this.item_questionanswer_id = questionanswerID;
		this.item_active_time = active_time;
	}

	public int getItem_id() {
		return item_id;
	}
	public int getItem_user_id() {
		return item_user_id;
	}
	public int getItem_question_id() {
		return item_question_id;
	}
	public int getItem_questionanswer_id() {
		return item_questionanswer_id;
	}
	public Timestamp getItem_active_time() {
		return item_active_time;
	}
}

