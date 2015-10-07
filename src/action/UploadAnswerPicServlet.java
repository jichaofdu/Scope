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
import biz.QuestionAnswerBiz;
import entity.QuestionAnswer;

public class UploadAnswerPicServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("[Begin]UploadAnswerPicServlet");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//创建解析器实例
		ServletFileUpload sfu = new ServletFileUpload(factory);
		int question = 0;
		int responder = 0;
		String content_temp = "";
		try {
			sfu.setFileSizeMax(1024 * 50 * 1024);//用来设置上传文件的大小
			//FileItem对象对应表单中的一个表单域
			List<FileItem> fileItems = sfu.parseRequest(request);
			for(int i = 0;i < fileItems.size();i++){
				FileItem item = fileItems.get(i);
				String name = item.getFieldName();//该名字取的是控件的名称
				System.out.println("[Tip]控件的名称是：" + name);
				if(name.equals("question_id")){
					question = Integer.parseInt(item.getString());
					System.out.println("[Tip]获取到的问题的id是：" + item.getString());
				}
				if(name.equals("responder_id")){
					responder = Integer.parseInt(item.getString());
					System.out.println("[Tip]获取到的回答者的id是：" + item.getString());
				}
				if(name.equals("answer_content")){
					content_temp = item.getString();
					System.out.println("[Tip]获取到的回答的内容是：" + item.getString());
				}
				if(name != null && name.equals("answer_pic")){
					try{
						String fileName = item.getName();//取的是上传文件的名称
						System.out.println("[Tip]上传的文件的文件名是：" + fileName);
					    fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
					    if(null != fileName && !("".equals(fileName))){
					        //String fileType = item.getContentType();
					    	QuestionAnswer answer = QuestionAnswerBiz.getInstance().get_latest_answer();
					    	int answer_id = answer.getQuestionanswer_id() + 1;//新的answer的id
						    ServletContext sctx = getServletContext();//依据逻辑路径返回实例的物理路径。
							String path = sctx.getRealPath("img/answer_img");
							String img_path = path + "/" + answer_id +".jpg";
							System.out.println("[Tip]新图片的路径是：" + img_path);
							File file = new File(img_path);
							item.write(file);
							//对答案的内容进行修改（添加图片）
							content_temp += "<img class='inner_answer_pic' src='img/answer_img/" + answer_id + ".jpg'>";
							System.out.println("[Tip]新添加的答案的内容是" + content_temp);
					    }		
					}catch(Exception e){
						System.out.println("[Error]保存文件出错");
					}
			    }		
			}
			//此处创建答案
			QuestionAnswerBiz answerBiz = QuestionAnswerBiz.getInstance();
			answerBiz.create_questionanswer(question, responder, content_temp);
		} catch (Exception e) {
			System.out.println("[Error]准备组件出错");
			e.printStackTrace();
		}
		System.out.println("[Tip]文件上传结束");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/user_question.jsp?question_id=" + question);
		dispatcher.forward(request, response); 
		System.out.println("[End]UploadAnswerPicServlet");
	}
}
