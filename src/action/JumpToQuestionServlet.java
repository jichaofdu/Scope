package action;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import biz.QuestionAnswerBiz;
import biz.QuestionBiz;
import entity.Question;
import entity.QuestionAnswer;

public class JumpToQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}


	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("进入jump to question servlet");
		request.setCharacterEncoding("utf-8");
		String question_idStr = request.getParameter("question_id");
		int question_id = Integer.parseInt(question_idStr);
		QuestionBiz questionBiz = QuestionBiz.getInstance();
		Question question = questionBiz.question_get_by_question_id(question_id);
		//获取了问题的对象，应该还需要获取相应的回答
		QuestionAnswerBiz answerBiz = QuestionAnswerBiz.getInstance();
		List<QuestionAnswer> answer_list = answerBiz.questionanswer_list_by_question_id(question_id);
		if(answer_list == null){
			answer_list = new ArrayList<QuestionAnswer>();
		}
		request.setAttribute("this_answer_list", answer_list);
		request.setAttribute("this_question", question);
		System.out.println("即将进入转发");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/user_question.jsp");
		dispatcher.forward(request, response); 
	}

}
