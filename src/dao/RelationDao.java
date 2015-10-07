package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import entity.Relation;
import util.JdbcUtil;

/**
 * 最后修改时间：2015-05-30 18:19
 * @author 13302010019-jichao
 * 介绍：本类用于用户关注关系的数据库逻辑操作
 * 
 * 函数介绍：
 *    public boolean relation_create(Relation new_relation)：在数据库中创建一个关注关系
 *    public boolean relation_delete(Relation delete_relation)：在数据库中删除一个关系
 *    public List<Relation> relation_list_by_userfrom(int from)：查看被我关注的人
 *    public List<Relation> relation_list_by_userto(int to)：查看我关注的人
 *    public List<Relation> relation_get_latest_my_fans(int num,int user_id)：查看这个人新增的粉丝
 *    public List<Relation> relation_get_latest_my_focus(int num,int user_id)：查看这个人新关注了谁
 *    public Relation relation_get_two_people_relation(int from,int to):查找某两个特定人的关系
 */
public class RelationDao {
	private JdbcUtil util;
	private static RelationDao relationDao;

	//dao的构造方法
	public RelationDao(){
		util = JdbcUtil.getInstance();
	}
	/*本方法用于获取到relationDao*/
	public static RelationDao getInstance(){
		if(relationDao == null){
			relationDao = new RelationDao();
		}
		return relationDao;
	}
	/*获取user_id最近新增的几个粉丝*/
	public List<Relation> relation_get_latest_my_fans(int num,int user_id){
		Connection conn = util.getConnection();
		String sql = "select * from relation where user_to = ? order by id desc limit ?";
		PreparedStatement ps = null;
		List<Relation> list = new ArrayList<Relation>();
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1,user_id);
			ps.setInt(2,num);
			rs = ps.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				int from = rs.getInt("user_from");
				Relation relation = new Relation(id,from,user_id);
				list.add(relation);
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
		
	/*获取user_id最近关注了谁*/
	public List<Relation> relation_get_latest_my_focus(int num,int user_id){
		Connection conn = util.getConnection();
		String sql = "select * from relation where user_from = ? order by id desc limit ?";
		PreparedStatement ps = null;
		List<Relation> list = new ArrayList<Relation>();
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1,user_id);
			ps.setInt(2,num);
			rs = ps.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				int to = rs.getInt("user_to");
				Relation relation = new Relation(id,user_id,to);
				list.add(relation);
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
	
	
	/*获取某两个人特定人的关系，如果找不到返回null*/
	public Relation relation_get_two_people_relation(int from,int to){
		Connection conn = util.getConnection();
		PreparedStatement ps = null;
		String sql = "select * from relation where user_from= ? and user_to = ?";
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement(sql);
			ps.setInt(1,from);
			ps.setInt(2,to);
			rs = ps.executeQuery();
			if(rs.next()){
				int id = rs.getInt("id");
				Relation relation = new Relation(id,from,to);
				return relation;
			}else{
				return new Relation(-1,-1,-1);
			}
		}catch(SQLException e){
			e.printStackTrace();
			return new Relation(-1,-1,-1);
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
	/*本方法用于关系的创建*/
	public boolean relation_create(Relation new_relation){
		Connection conn = util.getConnection();
		String sql = "insert into relation(user_from,user_to) values(?,?)";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, new_relation.getRelation_user_from());
			ps.setInt(2, new_relation.getRelation_user_to());
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
	/*本方法用于数据库中的关系删除*/
	public boolean relation_delete(Relation delete_relation){
		Connection conn = util.getConnection();
		String sql = "delete from relation where user_from = ? and user_to = ?";
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
		    ps.setInt(1,delete_relation.getRelation_user_from());
		    ps.setInt(2,delete_relation.getRelation_user_to());
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
	/*本方法用于根据user_from（该用户关注了多少人）来查询关注关系*/
	public List<Relation> relation_list_by_userfrom(int from){
		Connection conn = util.getConnection();
		String sql = "select * from relation where user_from = ?";
		PreparedStatement ps = null;
		List<Relation> list = new ArrayList<Relation>();
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1,from);
			rs = ps.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				int to = rs.getInt("user_to");
				Relation relation = new Relation(id,from,to);
				list.add(relation);
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
	/*该方法用于根据user_to（关注我的人）来查询所有的关系*/
	public List<Relation> relation_list_by_userto(int to){
		Connection conn = util.getConnection();
		String sql = "select * from relation where user_to = ?";
		PreparedStatement ps = null;
		List<Relation> list = new ArrayList<Relation>();
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1,to);
			rs = ps.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				int from = rs.getInt("user_from");
				Relation relation = new Relation(id,from,to);
				list.add(relation);
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
