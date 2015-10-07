package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.User;
import util.JdbcUtil;
/**
 * 最后修改日期：2015-05-30 17:30
 * @author 13302010019-jichao
 * 介绍：本类用于从数据库层面对user信息进行操作。
 *      user_create：在t_user表中创建一个user条目，信息为传入user对象中存储的值
 *      user_info_change：修改t_user中的某个条目，要修改成的信息为传入user对象中的值
 *      public User user_get_by_user_email(String user_email)：根据用户名取出用户的信息
 *      public List<User> user_list_by_key_word(String key)：根据某关键词查找出用户
 *      public User user_get_by_user_id(int user_id)：根据用户id查找出用户
 *      public boolean user_create(User user)：创建用户
 *      public boolean user_info_change(User user)：修改用户的信息
 *      public User user_get_by_user_nickname(String user_nickname):根据昵称获取用户信息
 */
public class UserDao {
	private JdbcUtil util;
	private static UserDao userDao;
	String default_pic_src = "img/user_img/img1.jpg";
	String default_description = "这个人还没有创建描述";
	public UserDao(){
		util = JdbcUtil.getInstance();
	}
	/*获取到userDao*/
	public static UserDao getInstance(){
		if(userDao == null){
			userDao = new UserDao();
		}
		return userDao;
	}
	/*提示：本方法用于修改用户的粉丝数*/
	public boolean user_fans_change(int id,int num){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		String sql = "update user set fans = ? where id = ?";
		User user = user_get_by_user_id(id);
		int fans_num = user.getUser_fans() + num;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, fans_num);
			ps.setInt(2, id);
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
	/*提示：本方法用于修改关注数*/
	public boolean user_focus_change(int id,int num){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		String sql = "update user set focus = ? where id = ?";
		User user = user_get_by_user_id(id);
		int focus_num = user.getUser_focus() + num;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, focus_num);
			ps.setInt(2, id);
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
	/*提示：本方法用于提取出该用户的所有信息，传入参数为email*/
	public User user_get_by_user_email(String user_email){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		String sql = "select * from user where email = ?";
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(sql);
			ps.setString(1,user_email);
			rs = ps.executeQuery();
			if(rs.next()){
				int user_id = rs.getInt("id");
				String user_nickname = rs.getString("nickname");
				String user_password = rs.getString("password");
				String user_pic_src = rs.getString("pic_src");
				String user_description = rs.getString("description");
				int user_fans = rs.getInt("fans");
				int user_focus = rs.getInt("focus");
				User user = new User(user_id,user_nickname,user_email,user_password,user_pic_src,user_description,user_focus,user_fans);
				return user;
			}else{
				return null;
			}
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
	/*本方法用于根据昵称查找某个用户*/
	public User user_get_by_user_nickname(String user_nickname){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		String sql = "select * from user where nickname = ?";
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(sql);
			ps.setString(1,user_nickname);
			rs = ps.executeQuery();
			if(rs.next()){
				int user_id = rs.getInt("id");
				String user_email = rs.getString("email");
				String user_password = rs.getString("password");
				String user_pic_src = rs.getString("pic_src");
				String user_description = rs.getString("description");
				int user_fans = rs.getInt("fans");
				int user_focus = rs.getInt("focus");
				User user = new User(user_id,user_nickname,user_email,user_password,user_pic_src,user_description,user_focus,user_fans);
				return user;
			}else{
				return null;
			}
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
	/*本方法用于查找昵称包含某关键字符串的用户（nickname）*/
	public List<User> user_list_by_key_word(String key){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		String sql = "select * from user where nickname like ?";
		ResultSet rs = null;
		List<User> list = new ArrayList<User>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1,"%" + key + "%");
			rs = ps.executeQuery();
			while(rs.next()){
				int user_id = rs.getInt("id");
				String user_nickname = rs.getString("nickname");
				String user_password = rs.getString("password");
				String user_email = rs.getString("email");
				String user_pic_src = rs.getString("pic_src");
				String user_description = rs.getString("description");
				int user_fans = rs.getInt("fans");
				int user_focus = rs.getInt("focus");
				User user = new User(user_id,user_nickname,user_email,user_password,user_pic_src,user_description,user_focus,user_fans);
				list.add(user);
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
	/*本方法通过用户id获取到某个用户的信息*/
	public User user_get_by_user_id(int user_id){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		String sql = "select * from user where id = ?";
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(sql);
			ps.setInt(1,user_id);
			rs = ps.executeQuery();
			if(rs.next()){
				String user_nickname = rs.getString("nickname");
				String user_password = rs.getString("password");
				String user_email = rs.getString("email");
				String user_pic_src = rs.getString("pic_src");
				String user_description = rs.getString("description");
				int user_fans = rs.getInt("fans");
				int user_focus = rs.getInt("focus");
				User user = new User(user_id,user_nickname,user_email,user_password,user_pic_src,user_description,user_focus,user_fans);
				return user;
			}else{
				return null;
			}
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
	/*提示：本方法用于在数据库中创建一个新用户*/
	public boolean user_create(User user){
		Connection conn = util.getConnection();
		
		String sql = "insert into user(nickname,email,password,pic_src,fans,focus,description) values(?,?,?,?,?,?,?)";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUser_nickname());
			ps.setString(2, user.getUser_email());
			ps.setString(3, user.getUser_password());
			ps.setString(4, default_pic_src);
			ps.setInt(5,0);
			ps.setInt(6,0);
			ps.setString(7, default_description);
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
	/*本方法用于对用户信息的修改*/
	public boolean user_info_change(User user){
		Connection conn = util.getConnection();
		String sql = "update user set password = ?,pic_src = ?,description = ? where id = ?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getUser_password());
			ps.setString(2, user.getUser_pic_src());
			ps.setString(3, user.getUser_description());
			ps.setInt(4, user.getUser_id());
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
	/*介绍：按关注数获取比较多的用户*/
	/*本方法用于查找昵称包含某关键字符串的用户（nickname）*/
	public List<User> get_hot_user(int num){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		String sql = "select * from user order by fans desc limit ?";
		ResultSet rs = null;
		List<User> list = new ArrayList<User>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, num);
			rs = ps.executeQuery();
			while(rs.next()){
				int user_id = rs.getInt("id");
				String user_nickname = rs.getString("nickname");
				String user_password = rs.getString("password");
				String user_email = rs.getString("email");
				String user_pic_src = rs.getString("pic_src");
				String user_description = rs.getString("description");
				int user_fans = rs.getInt("fans");
				int user_focus = rs.getInt("focus");
				User user = new User(user_id,user_nickname,user_email,user_password,user_pic_src,user_description,user_focus,user_fans);
				list.add(user);
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
  