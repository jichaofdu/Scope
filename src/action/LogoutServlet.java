package action;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 最后修改时间： 2015-05-17 19：42
 * @author 13302010019-jichao 
 * 介绍：本servlet用于注销当前用户的登录
 *
 */
public class LogoutServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("[Begin]LogoutServlet");
		System.out.println("[Tip]使缓存session全部过期动作开始");
		request.setCharacterEncoding("utf-8");
		request.getSession().setAttribute("user", null);
		request.getSession().setAttribute("this_answer_list", null);
		request.getSession().setAttribute("this_answer_list", null);
		request.getSession().setAttribute("search_question_list", null);
		request.getSession().setAttribute("search_user_list", null);
		System.out.println("[Tip]使缓存session全部过期动作结束");
		System.out.println("[Begin]LogoutServlet");
		//强制session过期
	}
}
