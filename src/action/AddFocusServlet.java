package action;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import biz.RelationBiz;
import entity.User;

public class AddFocusServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}


	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("[Begin]AdddFocusServlet");
		request.setCharacterEncoding("utf-8");
		int to_id = Integer.parseInt(request.getParameter("to_id"));
		int from_id = ((User)request.getSession().getAttribute("user")).getUser_id();
		System.out.println("[Tip]发起方：" + from_id);
		System.out.println("[Tip]被关注方" + to_id);
		System.out.println("[Tip]动作：添加关注");
		boolean flag = RelationBiz.getInstance().relation_create(from_id, to_id);
		if(flag == false){
			System.out.println("[Error]添加关注失败");
			JSONObject meta = new JSONObject();
			meta.put("status", "error");
			String result = meta.toString();
			PrintWriter out = response.getWriter();
	        out.print(result);  
	        out.flush();  
	        out.close();
		}else{
			System.out.println("[Tip]添加关注成功");
			JSONObject meta = new JSONObject();
			meta.put("status", "ok");
			String result = meta.toString();
			PrintWriter out = response.getWriter();
	        out.print(result);  
	        out.flush();  
	        out.close();
		}
		System.out.println("[End]AddFocusServlet");
	}
}
