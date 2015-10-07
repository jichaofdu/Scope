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
import entity.QuestionAnswer;
import entity.User;
import biz.QuestionAnswerBiz;
import biz.UserBiz;

public class GetMoreAnswerServlet extends HttpServlet {

	static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("[Begin]GetMoreAnswerServlet");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int before_num = Integer.parseInt(request.getParameter("num"));
		System.out.println("[Tip]已经存在的答案的数量：" + before_num);
		int after_num = before_num + 5;
		System.out.println("[Tip]预计获得的答案数量：" + after_num);
		int question_id = Integer.parseInt(request.getParameter("question_id"));
		QuestionAnswerBiz answerBiz = QuestionAnswerBiz.getInstance();
		List<QuestionAnswer> list = answerBiz.get_latest_answers(after_num, question_id);
		int size = list.size();
		System.out.println("[Tip]实际取得的回答数量" + size);
		PrintWriter out = response.getWriter();
		JSONArray jar = new JSONArray();
		for(int i = 0;i < size;i++){
			QuestionAnswer here_answer = list.get(i);
			//获取当前问题最近的一条回答
			int responder_id = here_answer.getQuestionanswer_responder_id();
			User responder = UserBiz.getInstance().user_get_by_user_id(responder_id);
			//将问题信息和回答信息填充到json中
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id",here_answer.getQuestionanswer_id());
			jsonObject.put("content",here_answer.getQuestionanswer_content());
			jsonObject.put("numofcomment",here_answer.getQuestionanswer_numofcomment());
			jsonObject.put("time",here_answer.getQuestionanswer_time());
			jsonObject.put("nick",responder.getUser_nickname());
			jsonObject.put("pic_src", responder.getUser_pic_src());
			jsonObject.put("responder", responder.getUser_id());
			jsonObject.put("description", responder.getUser_description());
			jar.put(jsonObject);
		}
		System.out.println("[Tip]json编写结束");
		out.print(jar.toString());
	    out.flush();
	    out.close();
	    System.out.println("[End]GetMoreAnswerServlet");
	}
}
