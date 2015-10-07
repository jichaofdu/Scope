package action;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import biz.AnswerCommentBiz;
import entity.User;

public class AddCommentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("[Begin]AddCommentServlet");
		request.setCharacterEncoding("utf-8");
		int answer_id = Integer.parseInt(request.getParameter("answer_id"));
		String comment_content = request.getParameter("comment_content");
		User now_user = (User)request.getSession().getAttribute("user");
		System.out.println("[Tip]对应问题id：" + answer_id);
		System.out.println("[Tip]评论内容：" + comment_content);
		System.out.println("[Tip]评论者：" + now_user.getUser_nickname());
		AnswerCommentBiz commentBiz = AnswerCommentBiz.getInstance();
		boolean comment_flag = commentBiz.create_answercomment(answer_id,now_user.getUser_id(), comment_content);
		if(comment_flag == false){
			System.out.println("[Error]评论创建失败");
			JSONObject meta = new JSONObject();
			meta.put("status", "error");
			String result = meta.toString();
			PrintWriter out = response.getWriter();
	        out.print(result);  
	        out.flush();  
	        out.close();
		}else{
			System.out.println("[Tip]评论创建成功");
			JSONObject meta = new JSONObject();
			meta.put("status", "ok");
			String result = meta.toString();
			PrintWriter out = response.getWriter();
	        out.print(result);  
	        out.flush();  
	        out.close();
		}
		System.out.println("[End]AddCommentServlet");
	}

}
