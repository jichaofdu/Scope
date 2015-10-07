package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import entity.QuestionAnswer;
import util.JdbcUtil;
/**
 * 最后修改日期：2015-05-31 11:50
 * @author 13302010019-jichao
 * 介绍：本类用于从数据库层面对QuestionAnswer信息进行操作。
 * 		public boolean questionanswer_create(QuestionAnswer questionanswer)：在数据库中创建一个questionanswer条目
 * 		public boolean questionanswer_info_change(QuestionAnswer questionanswer):修改回答的内容
 * 		public List<QuestionAnswer> questionanswer_list_by_responder_id(int questionanswer_responder_id)：根据questionanswer的id查询到这个对象
 *      public List<QuestionAnswer> questionanswer_list_by_responder_id(int questionanswer_responder_id):获得某人个旗下的所有回答
 *      public List<QuestionAnswer> questionanswer_list_by_question_id:获得某个问题下的所有回答
 *      public boolean questionanswer_numofcomment_change(QuestionAnswer questionanswer)：改变某问题回答数
 *      public QuestionAnswer questionanswer_get_latest()：获取最新的一条回答
 *      public List<QuestionAnswer> answer_get_latest(int num,int question_id)：获取最新的几条回答
 */
public class QuestionAnswerDao {
	private JdbcUtil util;
	private static QuestionAnswerDao questionAnswerDao;
	public QuestionAnswerDao(){
		util = JdbcUtil.getInstance();
	}
	/*本方法用于获取到questionAnswerDao*/
	public static QuestionAnswerDao getInstance(){
		if(questionAnswerDao == null){
			questionAnswerDao = new QuestionAnswerDao();
		}
		return questionAnswerDao;
	}
	/*获取最近某问题最新的几条回答*/
	public List<QuestionAnswer> answer_get_latest(int num,int question_id){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		String sql = "select * from questionanswer where question_id = ? order by time desc limit ?";
		ResultSet rs = null;
		List<QuestionAnswer> list = new ArrayList<QuestionAnswer>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(2, num);
			ps.setInt(1,question_id);
			rs = ps.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				int responder = rs.getInt("responder_id");
				String content = rs.getString("content");
				int numofcomment = rs.getInt("numofcomment");
				Timestamp time = rs.getTimestamp("time");
				QuestionAnswer answer = new QuestionAnswer(id,question_id,responder,content,numofcomment,time);
				list.add(answer);
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
	/*获取最新创建的一个回答*/
	public QuestionAnswer questionanswer_get_latest(){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		String sql = "select * from questionanswer order by id desc limit 1";
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				int questionanswer_id = rs.getInt("id");
				int questionanswer_question_id = rs.getInt("question_id");
				int questionanswer_responder_id = rs.getInt("responder_id");
				String questionanswer_content = rs.getString("content");
				int questionanswer_numofcomment = rs.getInt("numofcomment");
				Timestamp questionanswer_time = rs.getTimestamp("time");
				QuestionAnswer questionanswer = new QuestionAnswer(questionanswer_id,questionanswer_question_id,questionanswer_responder_id,questionanswer_content,questionanswer_numofcomment,questionanswer_time);
				return questionanswer;
			}else{
				Timestamp ts = Timestamp.valueOf("0000-00-00 00:00:00"); 
				return new QuestionAnswer(-1,-1,-1,"null",-1,ts);
			}
		}catch(SQLException e){
			e.printStackTrace();
			Timestamp ts = Timestamp.valueOf("0000-00-00 00:00:00"); 
			return new QuestionAnswer(-1,-1,-1,"null",-1,ts);
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
	/*在数据中增加一个QuesitionAnswer条目*/
	public boolean questionanswer_create(QuestionAnswer questionanswer){
		Connection conn = util.getConnection();
		String sql = "insert into questionanswer(question_id,responder_id,content,numofcomment,time) values(?,?,?,?,?)";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, questionanswer.getQuestionanswer_question_id());
			ps.setInt(2, questionanswer.getQuestionanswer_responder_id());
			ps.setString(3, questionanswer.getQuestionanswer_content());
			ps.setInt(4, questionanswer.getQuestionanswer_numofcomment());
			ps.setTimestamp(5, questionanswer.getQuestionanswer_time());
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
	/*在数据库中修改一个question_answer条目*/
	public boolean questionanswer_info_change(QuestionAnswer questionanswer){
		Connection conn = util.getConnection();
		String sql = "update questionanswer set content = ?,time = ? where id = ?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, questionanswer.getQuestionanswer_content());
			ps.setTimestamp(2, questionanswer.getQuestionanswer_time());
			ps.setInt(3, questionanswer.getQuestionanswer_id());
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
	/*在数据库中修改某个回答的评论数*/
	public boolean questionanswer_numofcomment_change(QuestionAnswer questionanswer){
		Connection conn = util.getConnection();
		String sql = "update questionanswer set numofcomment = ? where id = ?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, questionanswer.getQuestionanswer_numofcomment());
			ps.setInt(2, questionanswer.getQuestionanswer_id());
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
	/*根据QuestionAnswer_Id查找相应的回答*/
	public QuestionAnswer questionanswer_get_by_questionanswer_id(int questionanswer_id){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		String sql = "select * from questionanswer where id = ?";
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(sql);
			ps.setInt(1,questionanswer_id);
			rs = ps.executeQuery();
			if(rs.next()){
				int questionanswer_question_id = rs.getInt("question_id");
				int questionanswer_responder_id = rs.getInt("responder_id");
				String questionanswer_content = rs.getString("content");
				int questionanswer_numofcomment = rs.getInt("numofcomment");
				Timestamp questionanswer_time = rs.getTimestamp("time");
				QuestionAnswer questionanswer = new QuestionAnswer(questionanswer_id,questionanswer_question_id,questionanswer_responder_id,questionanswer_content,questionanswer_numofcomment,questionanswer_time);
				return questionanswer;
			}else{
				Timestamp ts = Timestamp.valueOf("0000-00-00 00:00:00"); 
				return new QuestionAnswer(-1,-1,-1,"null",-1,ts);
			}
		}catch(SQLException e){
			e.printStackTrace();
			Timestamp ts = Timestamp.valueOf("0000-00-00 00:00:00"); 
			return new QuestionAnswer(-1,-1,-1,"null",-1,ts);
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
	/*根据question_id查找对应的回答集合*/
	public List<QuestionAnswer> questionanswer_list_by_question_id(int questionanswer_question_id){
		Connection conn = util.getConnection();
		String sql = "select * from questionanswer where question_id = ?";
		PreparedStatement ps = null;
		List<QuestionAnswer> list = new ArrayList<QuestionAnswer>();
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1,questionanswer_question_id);
			rs = ps.executeQuery();
			while(rs.next()){
				int questionanswer_id = rs.getInt("id");
				int questionanswer_responder_id = rs.getInt("responder_id");
				String questionanswer_content = rs.getString("content");
				int questionanswer_numofcomment = rs.getInt("numofcomment");
				Timestamp questionanswer_time = rs.getTimestamp("time");
				QuestionAnswer questionanswer = new QuestionAnswer(questionanswer_id,questionanswer_question_id,questionanswer_responder_id,questionanswer_content,questionanswer_numofcomment,questionanswer_time);
				list.add(questionanswer);
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
	/*根据回答人id查找对应回答的集合*/
	public List<QuestionAnswer> questionanswer_list_by_responder_id(int questionanswer_responder_id){
		Connection conn = util.getConnection();
		String sql = "select * from questionanswer where responder_id = ?";
		PreparedStatement ps = null;
		List<QuestionAnswer> list = new ArrayList<QuestionAnswer>();
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1,questionanswer_responder_id);
			rs = ps.executeQuery();
			while(rs.next()){
				int questionanswer_id = rs.getInt("id");
				int questionanswer_question_id = rs.getInt("question_id");
				String questionanswer_content = rs.getString("content");
				int questionanswer_numofcomment = rs.getInt("numofcomment");
				Timestamp questionanswer_time = rs.getTimestamp("time");
				QuestionAnswer questionanswer = new QuestionAnswer(questionanswer_id,questionanswer_question_id,questionanswer_responder_id,questionanswer_content,questionanswer_numofcomment,questionanswer_time);
				list.add(questionanswer);
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
}
