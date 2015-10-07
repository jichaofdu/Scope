package action;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import entity.User;
import biz.QuestionBiz;

public class AskQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("[Begin]AskQuestionServlet");
		request.setCharacterEncoding("utf-8");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		int asker_id = ((User)request.getSession().getAttribute("user")).getUser_id();
		QuestionBiz questionBiz = QuestionBiz.getInstance();
		boolean status = questionBiz.ask_question(asker_id, title, content);
		if(status == false){
			System.out.println("[Error]问题创建失败");
			JSONObject meta = new JSONObject();
			meta.put("status", "error");
			String result = meta.toString();
			PrintWriter out = response.getWriter();
	        out.print(result);  
	        out.flush();  
	        out.close();
		}else{
			System.out.println("[Tip]问题创建成功");
			JSONObject meta = new JSONObject();
			meta.put("status", "ok");
			String result = meta.toString();
			PrintWriter out = response.getWriter();
	        out.print(result);  
	        out.flush();  
	        out.close();
		}
		System.out.println("[End]AskQuestionServlet");
	}

}
