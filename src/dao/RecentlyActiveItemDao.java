package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import entity.RecentlyActiveItem;
import util.JdbcUtil;

/**
 * 最后修改日期：2015-05-17 11:25
 * @author 13302010019-jichao
 * 介绍：本类用于从数据库层面对最近活跃信息进行操作。
 * 
 * 		public boolean recentlyactiveitem_create(RecentlyActiveItem recentlyactiveitem)：在数据库中新创建一个条目
 *      public List<RecentlyActiveItem> get_active_item_list(int want_num,int[] focus_list)：取出符合条件的num条动态
 */
public class RecentlyActiveItemDao {
	private JdbcUtil util;
	private static RecentlyActiveItemDao recentlyActiveItemDao;
	public RecentlyActiveItemDao(){
		util = JdbcUtil.getInstance();
	}
	
	/*本方法用于获取到recentlyActiveDao*/
	public static RecentlyActiveItemDao getInstance(){
		if(recentlyActiveItemDao == null){
			recentlyActiveItemDao = new RecentlyActiveItemDao();
		}
		return recentlyActiveItemDao;
	}
	/*在数据库中增加一个message条目*/
	public boolean recentlyactiveitem_create(RecentlyActiveItem recentlyactiveitem){
		Connection conn = util.getConnection();
		String sql = "insert into recentlyactiveitem(user_id,question_id,questionanswer_id,time) values(?,?,?,?)";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, recentlyactiveitem.getItem_user_id());
			ps.setInt(2, recentlyactiveitem.getItem_question_id());
			ps.setInt(3, recentlyactiveitem.getItem_questionanswer_id());
			ps.setTimestamp(4, recentlyactiveitem.getItem_active_time());
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			try {
				if(ps != null)   ps.close();
				if(conn != null) conn.close();	
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}
	}
	/*在数据库中查询有关信息的item*/
	public List<RecentlyActiveItem> get_active_item_list(int want_num,int[] focus_list){
		List<RecentlyActiveItem> list = new ArrayList<RecentlyActiveItem>();
		//接下来开始拼接mysql语句
		String condition = "";
		int focus_size = focus_list.length;
		if(focus_size == 1){
			int id = focus_list[0];
			condition += "user_id=" + "'" + id + "'";
		}else{
			for(int i = 0;i < focus_size - 1;i++){
				int id = focus_list[i];
				condition += "user_id=" + "'" + id + "' or ";
			}
			condition += "user_id=" + "'" + focus_list[focus_size - 1] + "'";
		}

		String first = "select * from recentlyactiveitem";
		String second = "where (" +  condition + ")";
		String third = "order by id desc limit " + want_num;
		String sql = first + " " + second + " " + third + ";";
		System.out.println("mysql语句：" + sql);
		//拼接结束开始执行mysql指令
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				int user_id = rs.getInt("user_id");
				int question_id = rs.getInt("question_id");
				int questionanswer_id = rs.getInt("questionanswer_id");
				Timestamp time = rs.getTimestamp("time");
				RecentlyActiveItem active_item = new RecentlyActiveItem(id,user_id,question_id,questionanswer_id,time);
				list.add(active_item);
			}
			return list;
		}catch(SQLException e){
			e.printStackTrace();
			return null;
		}finally{
			try {
				if(ps != null)   ps.close();
				if(conn != null) conn.close();	
				if(rs != null) rs.close();
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}
	}
	//此部分内容待定，直至完成ajax
}
