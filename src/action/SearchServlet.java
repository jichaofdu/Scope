package action;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.QuestionBiz;
import biz.UserBiz;
import entity.Question;
import entity.User;

public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("[Begin]SearchServlet");
		request.setCharacterEncoding("utf-8");
		String search_content = request.getParameter("search_content");
		QuestionBiz questionBiz = QuestionBiz.getInstance();
		UserBiz userBiz = UserBiz.getInstance();
		List<Question> question_list = questionBiz.question_list_by_key(search_content);
		List<User> user_list = userBiz.user_list_by_key(search_content);
		System.out.println("[Tip]搜索关键字：" + search_content);
		System.out.println("[Tip]检索到问题个数：" + question_list.size());
		System.out.println("[Tip]检索到用户个数：" + user_list.size());
		request.getSession().setAttribute("search_question_list", question_list);
		request.getSession().setAttribute("search_user_list", user_list);
		System.out.println("[Tip]已经将搜索结果放入session");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/search.jsp");
		dispatcher.forward(request, response); 
		System.out.println("[End]SearchServlet");
	}
}
