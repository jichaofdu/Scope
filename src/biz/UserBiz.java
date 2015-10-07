package biz;
import java.util.ArrayList;
import java.util.List;
import dao.UserDao;
import entity.User;
/**
 * 最后修改时间：2015-05-30 18:13
 * 介绍：本类用于user的逻辑操作
 * @author 13302010019-冀超
 *        
 * 函数介绍：
 *     public boolean userFansAdd(int id)：增加粉丝数
 *     public boolean userFansReduce(int id)：减少粉丝数
 *     public boolean userFocusAdd(int id)：增加关注数
 *     public boolean userFocusReduce(int id)：减少关注数
 *     public boolean userLogin(String username,String password)：处理登录操作
 *     public boolean userRegister(String nick,String email,String password)：处理注册操作
 *     public boolean userInfoChange(int id,String nick,String email,String password,
 *		 String pic_src,String description) 处理用户信息修改操作
 *	   public User user_get_by_login_name(String login_name) 处理根据用户名获取用户对象操作
 *	   public User user_get_by_user_id(int user_id)：处理根据用户id获取用户信息的操作
 *     public List<User> user_list_by_key(String key)：通过关键字获取用户集合
 *     public List<User> getHotUser(int num)：传入num，获取到这个数量的关注最多的用户
 *     
 */
public class UserBiz {
	private UserDao userDao;
	private static UserBiz userBiz;
	public UserBiz(){
		userDao = UserDao.getInstance();
	}
	
	/*本方法用于获取到messageBiz*/
	public static UserBiz getInstance(){
		if(userBiz == null){
			userBiz = new UserBiz();
		}
		return userBiz;
	}
	
	
	
	/*获取到当前被关注较多的用户，num为要获取的数量*/
	public List<User> getHotUser(int num){
		return userDao.get_hot_user(num);
	}
	/*增加粉丝数*/
	public boolean userFansAdd(int id){
		return userDao.user_fans_change(id,1);
	}
	/*减少粉丝数*/
	public boolean userFansReduce(int id){
		return userDao.user_fans_change(id,-1);
	}
	/*增加关注数*/
	public boolean userFocusAdd(int id){
		return userDao.user_focus_change(id,1);
	}
	/*减少关注数*/
	public boolean userFocusReduce(int id){
		return userDao.user_focus_change(id,-1);
	}
	/*登录验证的具体实现*/
	public boolean userLogin(String username,String password){
		User user = userDao.user_get_by_user_email(username);
		if(user == null){
			//本步检查该用户是否存在
			return false;
		}else{
			//本步检查用户名与密码是否匹配
			if(user.getUser_password().equals(password)){
				return true;
			}else{
				return false;
			}
		}
	}
	/*注册操作的具体实现*/
	public boolean userRegister(String nick,String email,String password){
		User find_user = userDao.user_get_by_user_email(email);
		//检查重名
		User same_name_user = userDao.user_get_by_user_nickname(nick);
		//注册第一步：检查用户名是否重复
		if(find_user != null || same_name_user != null){
			return false;
		}else{
			//注册第二部：调用创建用户的方法
			User user = new User(0,nick,email,password);
			boolean result = userDao.user_create(user);
			return result;
		}
	}
	/*用户信息修改的实际实现*/
	public boolean userInfoChange(int id,String password,String pic_src,String description){
		User user = new User(id,"","",password,pic_src,description);
		return userDao.user_info_change(user);
	}
	/*通过登录名拿到某个用户的信息*/
	public User user_get_by_login_name(String login_name){
		User user = userDao.user_get_by_user_email(login_name);
		if(user != null){
			return user;
		}else{
			return null;
		}
	}
	/*通过用户id拿到用户的信息*/
	public User user_get_by_user_id(int user_id){
		User user = userDao.user_get_by_user_id(user_id);
		if(user != null){
			return user;
		}else{
			return null;
		}
	}
	/*通过用户昵称拿到用户的信息*/
	public User user_get_by_user_nickname(String nick){
		User user = userDao.user_get_by_user_nickname(nick);
		if(user != null){
			return user;
		}else{
			return null;
		}
		
	}
	/*根据某个关键字搜索用户*/
	public List<User> user_list_by_key(String key){
		List<User> user_list = userDao.user_list_by_key_word(key);
		if(user_list == null){
			user_list = new ArrayList<User>();
		}
		return user_list;
	}
}
