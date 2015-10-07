package action;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import biz.MessageBiz;
import biz.UserBiz;
import entity.User;
/**
 * 介绍：该servlet用于发送消息。消息发送失败将返回错误到前台
 * @author 13302010019-jichao
 *
 */
public class SendMessageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("[Begin]SendMessageServlet");
		request.setCharacterEncoding("utf-8");
		String receiver_nick = request.getParameter("receiver_nick");
		String receiver_content = request.getParameter("receiver_content");
		User sender = (User)request.getSession().getAttribute("user");
		User receiver = UserBiz.getInstance().user_get_by_user_nickname(receiver_nick);
		MessageBiz messageBiz = MessageBiz.getInstance();
		boolean create_flag = false;
		boolean has_receiver = false;
		if(receiver != null){
			has_receiver = true;
			create_flag = messageBiz.create_message(sender.getUser_id(), receiver.getUser_id(), receiver_content);
		}
		System.out.println("[Tip]发送方：" + sender.getUser_nickname());
		System.out.println("[Tip]接收方：" + receiver_nick);
		System.out.println("[Tip]内容：" + receiver_content);
		if(has_receiver == false){
			System.out.println("[Error]接收方不存在");
			JSONObject meta = new JSONObject();
			meta.put("status", "no_receiver");
			String result = meta.toString();
			PrintWriter out = response.getWriter();
	        out.print(result);  
	        out.flush();  
	        out.close();
		}else if(create_flag == false){
			System.out.println("[Error]接收方存在，但发送失败");
			JSONObject meta = new JSONObject();
			meta.put("status", "error");
			String result = meta.toString();
			PrintWriter out = response.getWriter();
	        out.print(result);  
	        out.flush();  
	        out.close();
		}else{
			System.out.println("[Tip]发送成功");
			JSONObject meta = new JSONObject();
			meta.put("status", "ok");
			String result = meta.toString();
			PrintWriter out = response.getWriter();
	        out.print(result);  
	        out.flush();  
	        out.close();
		}
		System.out.println("[End]SendMessageServlet");
	}
}
