package entity;
/**
 * 最后修改时间：2015-05-30 17:29
 * @author 13302010019-jichao
 * 介绍：本类用于每一个用户个体。类中元素包括：用户id，用户昵称nickname,用户电子邮件email，用户密码password，用户头像地址pic_src，
 *      以及用户描述（签名）description，用户关注对象focus,用户粉丝数fans。由于在一开始注册时并无设置头像和签名，所以构造函数中并未传入这两个值。
 *      用户id不可改变，所以其中没有提供set方法。
 *      
 */
public class User {
	private int user_id;
	private String user_nickname;
	private String user_email;
	private String user_password;
	private String user_pic_src;
	private String user_description;
	private int user_fans;
	private int user_focus;

	public User(String nickname,String email,String password){
		this.user_id = -1;
		this.user_nickname = nickname;
		this.user_email = email;
		this.user_password = password;
		this.user_description = "这个用户还没有写描述。全是我写的";
		this.user_pic_src = "no pic";	
		this.user_fans = 0;
		this.user_focus = 0;
	}
	public User(int id,String nickname,String email,String password){
		this.user_id = id;
		this.user_nickname = nickname;
		this.user_email = email;
		this.user_password = password;
		this.user_description = "这个用户还没有写描述。全是我写的";
		this.user_pic_src = "no pic";	
		this.user_fans = 0;
		this.user_focus = 0;
	}
	public User(int id,String nickname,String email,String password,String user_pic_src,String user_description){
		this.user_id = id;
		this.user_nickname = nickname;
		this.user_email = email;
		this.user_password = password;
		this.user_pic_src = user_pic_src;	
		this.user_description = user_description;
	}
	public User(int id,String nickname,String email,String password,String user_pic_src,String user_description,int focus,int fans){
		this.user_id = id;
		this.user_nickname = nickname;
		this.user_email = email;
		this.user_password = password;
		this.user_pic_src = user_pic_src;	
		this.user_description = user_description;
		this.user_focus = focus;
		this.user_fans = fans;
	}

	/*----------------------查询接口-------------------*/
	public int getUser_id() {
		return user_id;
	}	
	public String getUser_nickname() {
		return user_nickname;
	}
	public String getUser_email() {
		return user_email;
	}
	public String getUser_password() {
		return user_password;
	}
	public String getUser_pic_src() {
		return user_pic_src;
	}
	public String getUser_description() {
		return user_description;
	}
	
	public int getUser_fans() {
		return user_fans;
	}
	public int getUser_focus() {
		return user_focus;
	}
	/*----------------------修改接口-------------------*/
	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public void setUser_pic_src(String user_pic_src) {
		this.user_pic_src = user_pic_src;
	}
	public void setUser_description(String user_description) {
		this.user_description = user_description;
	}
	public void setUser_fans(int user_fans) {
		this.user_fans = user_fans;
	}
	public void setUser_focus(int user_focus) {
		this.user_focus = user_focus;
	}
}
