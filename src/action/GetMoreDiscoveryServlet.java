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
import entity.Question;
import entity.QuestionAnswer;
import biz.QuestionAnswerBiz;
import biz.QuestionBiz;

public class GetMoreDiscoveryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("[Begin]GetMoreDiscoveryServlet");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		int before_num = Integer.parseInt(request.getParameter("num"));
		System.out.println("[Tip]页面中已经存在数量：" + before_num);
		QuestionBiz questionBiz = QuestionBiz.getInstance();
		int after_num = before_num + 5;
		System.out.println("[Tip]预计获得的数量：" + after_num);
		List<Question> list = questionBiz.get_active_questions(after_num);
		int size = list.size();
		System.out.println("[Tip]实际获得的数量" + size);
		//开始填充返回内容
		PrintWriter out = response.getWriter();
		JSONArray jar = new JSONArray();
		for(int i = 0;i < size;i++){
			Question here_question = list.get(i);
			//获取当前问题最近的一条回答
			int question_id = here_question.getQuestion_id();
			QuestionAnswerBiz answerBiz = QuestionAnswerBiz.getInstance();
			List<QuestionAnswer> answer_list = answerBiz.questionanswer_list_by_question_id(question_id);
			QuestionAnswer answer = null;
			if(answer_list != null && answer_list.size() != 0 ){
				answer = answer_list.get(answer_list.size() - 1);
			}
			//将问题信息和回答信息填充到json中
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id",here_question.getQuestion_id());
			jsonObject.put("title",here_question.getQuestion_title());
			jsonObject.put("content",here_question.getQuestion_content());
			jsonObject.put("time", here_question.getQuestion_latest_change_time());
			if(answer == null){
				jsonObject.put("answer", "no_answer");
			}else{
				jsonObject.put("answer", answer.getQuestionanswer_content());
			}
			jar.put(jsonObject);
		}
		System.out.println("[Tip]json编写结束");
		out.print(jar.toString());
	    out.flush();
	    out.close();
	    System.out.println("[End]GetMoreDiscoveryServlet");
	}
}
