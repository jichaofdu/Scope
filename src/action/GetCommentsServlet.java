package action;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import biz.AnswerCommentBiz;
import biz.UserBiz;
import entity.AnswerComment;
import entity.User;

public class GetCommentsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("[Begin]GetCommentsServlet");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int answer_id = Integer.parseInt(request.getParameter("answer_id"));
		AnswerCommentBiz commentBiz = AnswerCommentBiz.getInstance();
		List<AnswerComment> list = commentBiz.answercomment_list_by_questionanswer_id(answer_id);
		int size = list.size();
		System.out.println("[Tip]取得的评论数量" + size);
		//开始填充返回内容
		PrintWriter out = response.getWriter();
		JSONArray jar = new JSONArray();
		for(int i = 0;i < size;i++){
			AnswerComment here_comment = list.get(i);
			//获取当前问题最近的一条回答
			int responder_id = here_comment.getAnswercomment_responder_id();
			User responder = UserBiz.getInstance().user_get_by_user_id(responder_id);
			if(responder == null){
				System.out.println("评论者不存在");
			}
			//将问题信息和回答信息填充到json中
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("answer_id",answer_id);
			jsonObject.put("id",here_comment.getAnswercomment_id());
			jsonObject.put("content",here_comment.getAnswercomment_content());
			jsonObject.put("time",here_comment.getAnswercomment_time());
			jsonObject.put("nick",responder.getUser_nickname());
			jsonObject.put("pic_src", responder.getUser_pic_src());
			jsonObject.put("responder", responder.getUser_id());
			jar.put(jsonObject);
		}
		System.out.println("[Tip]json编写结束");
		out.print(jar.toString());
	    out.flush();
	    out.close();
	    System.out.println("[End]GetCommentsServlet");
	}

}
