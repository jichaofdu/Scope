package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import entity.AnswerComment;
import util.JdbcUtil;
/**
 * 最后修改时间：2015-05-31 11:45
 * @author 13302010019-冀超
 * 		public boolean answercomment_create(AnswerComment answercomment)：在数据库中船建一个answercomment条目
 *      public AnswerComment answercomment_get_by_answercomment_id(int answercomment_id)：根据评论的id查找到对应的评论
 *      ublic List<AnswerComment> answercomment_list_by_questionanswer_id(int answercomment_questionanswer_id)：根据某个回答的id列出所有附属的评论
 *
 */
public class AnswerCommentDao {
	private JdbcUtil util;
	private static AnswerCommentDao answerCommentDao;
	public AnswerCommentDao(){
		util = JdbcUtil.getInstance();
	}
	/*本方法用于获取到answercommentDao*/
	public static AnswerCommentDao getInstance(){
		if(answerCommentDao == null){
			answerCommentDao = new AnswerCommentDao();
		}
		return answerCommentDao;
	}
	/*在数据中增加一个answercomment条目*/
	public boolean answercomment_create(AnswerComment answercomment){
		Connection conn = util.getConnection();
		String sql = "insert into answercomment(questionanswer_id,responder_id,content,time) values(?,?,?,?)";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, answercomment.getAnswercomment_questionanswer_id());
			ps.setInt(2, answercomment.getAnswercomment_responder_id());
			ps.setString(3, answercomment.getAnswercomment_content());
			ps.setTimestamp(4, answercomment.getAnswercomment_time());
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
	/*通过answer_comment的id获取到这个answer_comment*/
	public AnswerComment answercomment_get_by_answercomment_id(int answercomment_id){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		String sql = "select * from answercomment where id = ?";
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(sql);
			ps.setInt(1,answercomment_id);
			rs = ps.executeQuery();
			if(rs.next()){
				int answercomment_responder= rs.getInt("responder_id");
				int answercomment_questionanswer_id = rs.getInt("questionanswer_id");
				String answercomment_content = rs.getString("content");
				Timestamp answercomment_time = rs.getTimestamp("time");
				AnswerComment message = new AnswerComment(answercomment_id,answercomment_responder,answercomment_questionanswer_id,answercomment_content,answercomment_time);
				return message;
			}else{
				Timestamp ts = Timestamp.valueOf("0000-00-00 00:00:00"); 
				return new AnswerComment(-1,-1,-1,"null",ts);
			}
		}catch(SQLException e){
			e.printStackTrace();
			Timestamp ts = Timestamp.valueOf("0000-00-00 00:00:00"); 
			return new AnswerComment(-1,-1,-1,"null",ts);
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
	/*通过回答的id获取到有关的所有的评论*/
	public List<AnswerComment> answercomment_list_by_questionanswer_id(int answercomment_questionanswer_id){
		Connection conn = util.getConnection();
		String sql = "select * from answercomment where questionanswer_id = ?";
		PreparedStatement ps = null;
		List<AnswerComment> list = new ArrayList<AnswerComment>();
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1,answercomment_questionanswer_id);
			rs = ps.executeQuery();
			while(rs.next()){
				int answercomment_responder = rs.getInt("responder_id");
				int answercomment_id = rs.getInt("id");
				String answercomment_content = rs.getString("content");
				Timestamp answercomment_time = rs.getTimestamp("time");
				AnswerComment message = new AnswerComment(answercomment_id,answercomment_questionanswer_id,answercomment_responder,answercomment_content,answercomment_time);
				list.add(message);
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
