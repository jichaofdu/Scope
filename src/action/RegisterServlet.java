package action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import biz.UserBiz;
import entity.User;
/**
 * 最后修改时间： 2015-05-17 19：42
 * @author 13302010019-jichao 
 * 介绍：本servlet用于在数据库创建一个新用户
 *
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("[Begin]RegisterServlet");
		request.setCharacterEncoding("utf-8");
		String username = request.getParameter("nick");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String password_again = request.getParameter("password_again");
		System.out.println("[Tip]用户昵称：" + username);
		System.out.println("[Tip]电子邮箱：" + email);
		System.out.println("[Tip]密码       ：" + password);
		System.out.println("[Tip]重复密码：" + password_again);
		//验证昵称格式
		int nick_len = username.length();
		boolean username_form = false;
		if(nick_len >= 2 && nick_len <= 16){
			username_form = true;
		}
		//验证电子邮箱格式
		boolean email_form = true;
		//验证密码格式	
		int password_len = password.length();
		Pattern all_num = Pattern.compile("^[0-9]*$");
		Pattern num_letter = Pattern.compile("^[A-Za-z0-9]+$");
		boolean password_form = false;
		if((password_len >= 6 && password_len <= 16) && !all_num.matcher(password).matches() && 
				num_letter.matcher(password).matches() && password.equals(password_again)){
			password_form = true;
		}
		//进入注册步骤
		UserBiz userbiz = UserBiz.getInstance();
		boolean register_status = userbiz.userRegister(username, email, password_again);
		JSONArray jsonMembers = new JSONArray();
		if(username_form == true && email_form == true && password_form == true && 
				register_status == true){
			User user = userbiz.user_get_by_login_name(email);
			if(user == null){
			}
			request.getSession().setAttribute("user",user);
			JSONObject meta = new JSONObject();
			meta.put("status", "ok");
			jsonMembers.put(meta);  
			String result = jsonMembers.toString();
			PrintWriter out = response.getWriter();
	        out.print(result);  
	        out.flush();  
	        out.close();
	        System.out.println("[Tip]用户创建成功");
		}else{
			JSONObject meta = new JSONObject();
			meta.put("status", "error");
			jsonMembers.put(meta);  
			String result = jsonMembers.toString();
			PrintWriter out = response.getWriter();
	        out.print(result);  
	        out.flush();  
	        out.close();
	        System.out.println("[Tip]用户创建失败");
		}
		System.out.println("[End]RegisterServlet");
	}
}
