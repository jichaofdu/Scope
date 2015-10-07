package action;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import biz.MessageBiz;

public class ChangeMessageStatusServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("[Begin]ChangeMessageStatusServlet");
		request.setCharacterEncoding("utf-8");
		int message_id = Integer.parseInt(request.getParameter("message_id"));
		System.out.println("[Tip]状态将被改变的私信是：" + message_id);
		MessageBiz messageBiz = MessageBiz.getInstance();
		messageBiz.change_message_status(message_id);
		System.out.println("[End]ChangeMessageStatusServlet");
	}
}
