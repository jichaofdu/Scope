package action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import entity.User;
import biz.UserBiz;

public class UserInfoChangeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("[Begin]UserInfoChangeServlet");
		request.setCharacterEncoding("utf-8");
		String description = request.getParameter("description");
		String password = request.getParameter("password");
		User user = (User)request.getSession().getAttribute("user");
		if(description.equals("") == true){
			description = user.getUser_description();
		}
		if(password.equals("") == true){
			password = user.getUser_password();
		}
		String pic_src = user.getUser_pic_src();
		System.out.println("[Tip]新签名：" + description);
		System.out.println("[Tip]新密码：" + password);
		//检查密码格式和描述的长度限制
		int password_len = password.length();
		Pattern all_num = Pattern.compile("^[0-9]*$");
		Pattern num_letter = Pattern.compile("^[A-Za-z0-9]+$");
		boolean password_form = false;
		if((password_len >= 6 && password_len <= 16) && !all_num.matcher(password).matches() && 
				num_letter.matcher(password).matches()){
			password_form = true;
			System.out.println("[Tip]新密码格式符合要求");
		}else{
			System.out.println("[Error]新密码格式不符合要求");
		}
		boolean change_flag = false; 
		if(password_form == true){
			UserBiz userbiz = UserBiz.getInstance();
			change_flag = userbiz.userInfoChange(user.getUser_id(), password, pic_src, description);	
		}
	
		if(password_form == false){
			System.out.println("[Error]新密码格式不符合要求");;
			JSONObject meta = new JSONObject();
			meta.put("status", "password_form_error");
			String result = meta.toString();
			PrintWriter out = response.getWriter();
	        out.print(result);  
	        out.flush();  
	        out.close();
		}else if(change_flag == false){
			System.out.println("[Error]密码格式符合要求，但保存修改失败");
			JSONObject meta = new JSONObject();
			meta.put("status", "save_error");
			String result = meta.toString();
			PrintWriter out = response.getWriter();
	        out.print(result);  
	        out.flush();  
	        out.close();
		}else{
			user = UserBiz.getInstance().user_get_by_user_id(user.getUser_id());
			request.getSession().setAttribute("user", user);
			System.out.println("[Tip]修改成功");
			JSONObject meta = new JSONObject();
			meta.put("status", "ok");
			String result = meta.toString();
			PrintWriter out = response.getWriter();
	        out.print(result);  
	        out.flush();  
	        out.close();
		}
		System.out.println("[End]UserInfoChangeServlet");
	}

}
