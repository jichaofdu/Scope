package action;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import biz.UserBiz;
import entity.User;

public class UploadUserPicServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("[Begin]UploadUserPicServlet");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		User user = (User)request.getSession().getAttribute("user");
		int user_id = user.getUser_id();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//创建解析器实例
		ServletFileUpload sfu = new ServletFileUpload(factory);
		try {
			sfu.setFileSizeMax(1024 * 50 * 1024);//用来设置上传文件的大小
			//FileItem对象对应表单中的一个表单域
			List<FileItem> fileItems = sfu.parseRequest(request);
			FileItem item = fileItems.get(0);
			String name = item.getFieldName();//该名字取的是控件的名称
			if(name != null && name.equals("new_pic")){
				try{
					String fileName = item.getName();//取的是上传文件的名称
					System.out.println("[Tip]上传的文件的文件名是：" + fileName);
				    fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
				    if(null != fileName && !("".equals(fileName))){
				        //String fileType = item.getContentType();
					    ServletContext sctx = getServletContext();//依据逻辑路径返回实例的物理路径。
						String path = sctx.getRealPath("img/user_img");
						String img_path = path + "/" + user_id +".jpg";
						System.out.println("[Tip]新头像的路径是：" + img_path);
						File file = new File(img_path);
						item.write(file);
						user.setUser_pic_src("img/user_img/" + user_id + ".jpg");
						System.out.println("[Tip]用户的头像地址被修改为：" + "img/user_img/" + user_id + ".jpg");
						request.setAttribute("user",user);
				    }		
				}catch(Exception e){
					System.out.println("[Error]保存文件出错");
				}
		    }		
		} catch (Exception e) {
			System.out.println("[Error]准备组件出错");
			e.printStackTrace();
		}
		System.out.println("[Tip]文件上传结束");
		UserBiz.getInstance().userInfoChange(user_id, user.getUser_password(),user.getUser_pic_src(), user.getUser_description());
		RequestDispatcher dispatcher = request.getRequestDispatcher("/user_info.jsp");
		dispatcher.forward(request, response); 
		System.out.println("[End]UploadUserPicServlet");
	}
}
