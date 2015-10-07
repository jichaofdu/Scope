package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import entity.Message;
import util.JdbcUtil;

/**
 * 最后修改日期：2015-05-31 11:46
 * @author 13302010019-jichao
 * 介绍：本类用于从数据库层面对message信息进行操作。
 *      public boolean message_create(Message message)：在数据库表中创建新的私信条目。信息来源于传入的message对象
 *      public Message message_get_by_message_id(int message_id)：根据私信id获取到某个问题
 *      public List<Message> message_list_by_sender_id(int message_sender_id)：根据发送人id获取私信集合
 *      public List<Message> message_list_by_receiver_id(int message_receiver_id):根据接收人id获取私信集合
 *      public void message_change_status(int message_id)：将某条私信的状态变为已读
 */
public class MessageDao {
	private JdbcUtil util;
	private static MessageDao messageDao;
	public MessageDao(){
		util = JdbcUtil.getInstance();
	}
	/*本方法用于获取到messageDao*/
	public static MessageDao getInstance(){
		if(messageDao == null){
			messageDao = new MessageDao();
		}
		return messageDao;
	}
	/*将某条私信的状态变成已读*/
	public void message_change_status(int message_id){
		Connection conn = util.getConnection();
		String sql = "update message set is_read = ? where id = ?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setBoolean(1, true);
			ps.setInt(2,message_id);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(ps != null)   ps.close();
				if(conn != null) conn.close();	
			} catch (SQLException e) {
					e.printStackTrace();
			}
		}
	}
	/*在数据中增加一个message条目*/
	public boolean message_create(Message message){
		Connection conn = util.getConnection();
		String sql = "insert into message(sender_id,receiver_id,content,send_time,is_read) values(?,?,?,?,?)";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, message.getSender());
			ps.setInt(2, message.getReceiver());
			ps.setString(3, message.getContent());
			ps.setTimestamp(4, message.getSend_time());
			ps.setBoolean(5, message.isIs_read());
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
	/*根据sender_id查询message集合*/
	public List<Message> message_list_by_sender_id(int message_sender_id){
		Connection conn = util.getConnection();
		String sql = "select * from message where sender_id = ?";
		PreparedStatement ps = null;
		List<Message> list = new ArrayList<Message>();
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1,message_sender_id);
			rs = ps.executeQuery();
			while(rs.next()){
				int message_id= rs.getInt("id");
				int message_receiver_id = rs.getInt("receiver_id");
				String message_content = rs.getString("content");
				Timestamp message_send_time = rs.getTimestamp("send_time");
				Boolean message_is_read = rs.getBoolean("is_read");
				Message message = new Message(message_id,message_sender_id,message_receiver_id,message_content,message_send_time,message_is_read);
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
	/*跟message_receiver_id查询message集合*/
	public List<Message> message_list_by_receiver_id(int message_receiver_id){
		Connection conn = util.getConnection();
		String sql = "select * from message where receiver_id = ?";
		PreparedStatement ps = null;
		List<Message> list = new ArrayList<Message>();
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1,message_receiver_id);
			rs = ps.executeQuery();
			while(rs.next()){
				int message_id= rs.getInt("id");
				int message_sender_id = rs.getInt("sender_id");
				String message_content = rs.getString("content");
				Timestamp message_send_time = rs.getTimestamp("send_time");
				Boolean message_is_read = rs.getBoolean("is_read");
				Message message = new Message(message_id,message_sender_id,message_receiver_id,message_content,message_send_time,message_is_read);
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
	/*根据message_id查询一个message*/
	public Message message_get_by_message_id(int message_id){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		String sql = "select * from message where id = ?";
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(sql);
			ps.setInt(1,message_id);
			rs = ps.executeQuery();
			if(rs.next()){
				int message_sender_id= rs.getInt("sender_id");
				int message_receiver_id = rs.getInt("receiver_id");
				String message_content = rs.getString("content");
				Timestamp message_send_time = rs.getTimestamp("send_time");
				Boolean message_is_read = rs.getBoolean("is_read");
				Message message = new Message(message_id,message_sender_id,message_receiver_id,message_content,message_send_time,message_is_read);
				return message;
			}else{
				Timestamp ts = Timestamp.valueOf("0000-00-00 00:00:00"); 
				return new Message(-1,-1,-1,"null",ts,false);
			}
		}catch(SQLException e){
			e.printStackTrace();
			Timestamp ts = Timestamp.valueOf("0000-00-00 00:00:00"); 
			return new Message(-1,-1,-1,"null",ts,false);
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
