package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import entity.Question;
import util.JdbcUtil;
/**
 * 最后修改日期：2015-05-30 19:28
 * @author 13302010019-jichao
 * 介绍：本类用于从数据库层面对question信息进行操作。
 *      public boolean question_create(Question question)：在数据库表中创建新的问题条目。信息来源于传入的question对象
 *      public boolean question_info_change(Question question)：在数据库中更改问题的信息，信息来源于传入的question对象
 * 		public List<Question> question_list_by_asker_id(int question_asker_id):获取某个用户创建的所有问题
 *      public Question question_get_by_question_id(int question_id)：根据问题id获取到某个问题
 *      public List<Question> question_list_by_key_word(String key)：通过关键字查询问题
 *      public boolean question_numanswer_change(Question question)：修改问题的回答数
 *      public List<Question> get_hot_questions(int num):获取几个热门的问题
 *      public Question question_get_latest()：获取最新创建的问题
 */
public class QuestionDao {
	private JdbcUtil util;
	private static QuestionDao questionDao;
	public QuestionDao(){
		util = JdbcUtil.getInstance();
	}
	/*本方法用于获取到questionDao*/
	public static QuestionDao getInstance(){
		if(questionDao == null){
			questionDao = new QuestionDao();
		}
		return questionDao;
	}
	/*获取最近活跃的num条问题*/
	public List<Question> question_get_active(int num){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		String sql = "select * from question order by latest_change_time desc limit ?";
		ResultSet rs = null;
		List<Question> list = new ArrayList<Question>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, num);
			rs = ps.executeQuery();
			while(rs.next()){
				int question_id = rs.getInt("id");
				int question_asker_id = rs.getInt("asker_id");
				String question_title = rs.getString("title");
				String question_content = rs.getString("content");
				int question_numofanswer = rs.getInt("numofanswer");
				Timestamp question_latest_change_time= rs.getTimestamp("latest_change_time");
				Question question = new Question(question_id,question_asker_id,question_title,question_content,question_numofanswer,question_latest_change_time);
				list.add(question);;
			}
			return list;
		} catch (SQLException e) {
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
	/*获取最新创建的问题，即id最大的问题*/
	public Question question_get_latest(){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		String sql = "select * from question order by id desc limit 1";
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				int question_id = rs.getInt("id");
				int question_asker_id = rs.getInt("asker_id");
				String question_title = rs.getString("title");
				String question_content = rs.getString("content");
				int question_numofanswer = rs.getInt("numofanswer");
				Timestamp question_latest_change_time= rs.getTimestamp("latest_change_time");
				Question question = new Question(question_id,question_asker_id,question_title,question_content,question_numofanswer,question_latest_change_time);
				return question;
			}else{
				Timestamp ts = Timestamp.valueOf("0000-00-00 00:00:00"); 
				return new Question(-1,-1,"null","null",-1,ts);
			}
		}catch(SQLException e){
			e.printStackTrace();
			Timestamp ts = Timestamp.valueOf("0000-00-00 00:00:00"); 
			return new Question(-1,-1,"null","null",-1,ts);
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
	/*获取几个热门的问题*/
	public List<Question> get_hot_questions(int num){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		String sql = "select * from question order by numofanswer desc limit ?";
		ResultSet rs = null;
		List<Question> list = new ArrayList<Question>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, num);
			rs = ps.executeQuery();
			while(rs.next()){
				int question_id = rs.getInt("id");
				int question_asker_id = rs.getInt("asker_id");
				String question_title = rs.getString("title");
				String question_content = rs.getString("content");
				int question_numofanswer = rs.getInt("numofanswer");
				Timestamp question_latest_change_time= rs.getTimestamp("latest_change_time");
				Question question = new Question(question_id,question_asker_id,question_title,question_content,question_numofanswer,question_latest_change_time);
				list.add(question);;
			}
			return list;
		} catch (SQLException e) {
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
	
	/*提示：该方法用于新问题的创建*/
	public boolean question_create(Question question){
		Connection conn = util.getConnection();
		String sql = "insert into question(asker_id,title,content,latest_change_time,numofanswer) values(?,?,?,?,?)";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, question.getQuestion_asker_id());
			ps.setString(2, question.getQuestion_title());
			ps.setString(3, question.getQuestion_content());
			ps.setTimestamp(4, question.getQuestion_latest_change_time());
			ps.setInt(5, 0);
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
	/*本方法用于查找问题标题包含某关键字符串的问题（question_title）*/
	public List<Question> question_list_by_key_word(String key){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		String sql = "select * from question where title like ?";
		ResultSet rs = null;
		List<Question> list = new ArrayList<Question>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1,"%" + key + "%");
			rs = ps.executeQuery();
			while(rs.next()){
				int question_id = rs.getInt("id");
				int question_asker_id = rs.getInt("asker_id");
				String question_title = rs.getString("title");
				String question_content = rs.getString("content");
				int question_numofanswer = rs.getInt("numofanswer");
				Timestamp question_latest_change_time= rs.getTimestamp("latest_change_time");
				Question question = new Question(question_id,question_asker_id,question_title,question_content,question_numofanswer,question_latest_change_time);
				list.add(question);
			}
			return list;
		} catch (SQLException e) {
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
	/*该问题用于对问题的修改*/
	public boolean question_info_change(Question question){
		Connection conn = util.getConnection();
		String sql = "update question set content = ?,latest_change_time = ? where id = ?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, question.getQuestion_content());
			ps.setTimestamp(2, question.getQuestion_latest_change_time());
			ps.setInt(3, question.getQuestion_id());
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
	/*该方法用于修改问题的回答数*/
	public boolean question_numanswer_change(Question question){
		Connection conn = util.getConnection();
		String sql = "update question set numofanswer = ? where id = ?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, question.getQuestion_numofanswer());
			ps.setInt(2, question.getQuestion_id());
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
	/*获取问题NO.1 该方法用于问题对象的获取，通过用户id*/
	public List<Question> question_list_by_asker_id(int question_asker_id){
		Connection conn = util.getConnection();
		String sql = "select * from question where asker_id = ?";
		PreparedStatement ps = null;
		List<Question> list = new ArrayList<Question>();
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1,question_asker_id);
			rs = ps.executeQuery();
			while(rs.next()){
				int question_id = rs.getInt("id");
				String question_title = rs.getString("title");
				String question_content = rs.getString("content");
				int question_numofanswer = rs.getInt("numofanswer");
				Timestamp question_latest_change_time= rs.getTimestamp("latest_change_time");
				Question question = new Question(question_id,question_asker_id,question_title,question_content,question_numofanswer,question_latest_change_time);
				list.add(question);
			}
			return list;
		} catch (SQLException e) {
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
	/*获取问题NO.2 该方法用于问题对象的获取，通过问题id*/
	public Question question_get_by_question_id(int question_id){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		String sql = "select * from question where id = ?";
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(sql);
			ps.setInt(1,question_id);
			rs = ps.executeQuery();
			if(rs.next()){
				int question_asker_id = rs.getInt("asker_id");
				String question_title = rs.getString("title");
				String question_content = rs.getString("content");
				int question_numofanswer = rs.getInt("numofanswer");
				Timestamp question_latest_change_time= rs.getTimestamp("latest_change_time");
				Question question = new Question(question_id,question_asker_id,question_title,question_content,question_numofanswer,question_latest_change_time);
				return question;
			}else{
				Timestamp ts = Timestamp.valueOf("0000-00-00 00:00:00"); 
				return new Question(-1,-1,"null","null",-1,ts);
			}
		}catch(SQLException e){
			e.printStackTrace();
			Timestamp ts = Timestamp.valueOf("0000-00-00 00:00:00"); 
			return new Question(-1,-1,"null","null",-1,ts);
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
}
