package action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import biz.UserBiz;
import entity.User;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	//do post中实现具体的登录的操作
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("[Begin]LoginServlet");
		request.setCharacterEncoding("utf-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println("[Tip]登录名:" + username);
		System.out.println("[Tip]密码:" + password);
		//验证表单：密码和用户名是否符合规范
		//验证密码格式	
		int len = password.length();
		Pattern all_num = Pattern.compile("^[0-9]*$");
		Pattern num_letter = Pattern.compile("^[A-Za-z0-9]+$");
		boolean password_form = false;
		if((len >= 6 && len <= 16) && !all_num.matcher(password).matches() && 
				num_letter.matcher(password).matches()){
			password_form = true;
		}
		//验证email格式
		boolean email_form = true;
		//验证成功，进入创建步骤
		UserBiz userbiz = UserBiz.getInstance();
		boolean login = userbiz.userLogin(username, password);
		if(login == false || password_form == false || email_form == false){
			System.out.println("[Error]状态：登录失败");
			JSONObject meta = new JSONObject();
			meta.put("status", "error");
			String result = meta.toString();
			PrintWriter out = response.getWriter();
	        out.print(result);  
	        out.flush();  
	        out.close();
	        System.out.println("[End]SendMessageServlet");
		}else{
			System.out.println("[Tip]状态：登录成功");
			User user = userbiz.user_get_by_login_name(username);
			//System.out.println("重新获取到的用户是" + user.getUser_nickname());
			request.getSession().setAttribute("test","test string");
			request.getSession().setAttribute("user",user);
			JSONObject meta = new JSONObject();
			meta.put("status", "ok");
			String result = meta.toString();
			PrintWriter out = response.getWriter();
	        out.print(result);  
	        out.flush();  
	        out.close();
	        System.out.println("[End]SendMessageServlet");
		}
	}
}
