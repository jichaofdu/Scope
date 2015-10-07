<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
   <%@page import="dao.*" %>
   <%@page import="entity.*" %>
   <%@page import="biz.*" %>
   <%@page import="java.util.List" %>
   <%@page import="java.util.ArrayList" %>
   <%	
   		request.setCharacterEncoding("utf-8");
		User user = (User)request.getSession().getAttribute("user");
		if(user == null){
   			response.sendRedirect("index.jsp");
   			return;
   		}
   		int question_id = Integer.parseInt((String)request.getParameter("question_id"));
   		QuestionBiz questionBiz = QuestionBiz.getInstance();
		Question question = questionBiz.question_get_by_question_id(question_id);
		QuestionAnswerBiz answerBiz = QuestionAnswerBiz.getInstance();
		List<QuestionAnswer> answer_list = answerBiz.questionanswer_list_by_question_id(question_id);
		if(answer_list == null){
			answer_list = new ArrayList<QuestionAnswer>();
		}
   		User asker = UserBiz.getInstance().user_get_by_user_id(question.getQuestion_asker_id());
   		UserBiz userBiz = UserBiz.getInstance();
		List<User> hotUserList = userBiz.getHotUser(3);
		List<Question> hotQuestionList = questionBiz.get_hot_question(5); 
		
		MessageBiz messageBiz = MessageBiz.getInstance();
   		List<Message> message_list_my_receive = messageBiz.message_list_by_receiver_id(user.getUser_id());
   		int receive_size = message_list_my_receive.size();
   		//检查获取到的message_list有无未读
   		boolean have_unread = false;
   		for(int i = 0;i < receive_size;i++){
   			Message message = message_list_my_receive.get(i);
   			if(message.isIs_read() == false){
   				have_unread = true;
   			}
   		}
   %>
   
<!DOCTYPE html>
<html>
	<head>
    	<meta charset="UTF-8">
    	<title>问题界面</title>
    	<link rel="stylesheet" href="css/user_question.css">
        <link rel="stylesheet" href="css/navigate.css">
        <link rel="stylesheet" href="css/ask_pane.css">
        <link rel="stylesheet" href="css/change_info_pane.css">
        <script src="lib/jquery-2.1.3.min.js"></script>
        <script type="text/javascript" src="js/user_question.js" defer charset="utf-8"></script>
        <script type="text/javascript" src="js/ask_question.js" defer charset="utf-8"></script>
        <script type="text/javascript" src="js/navigate.js" defer charset="utf-8"></script>
        <script type="text/javascript" src="js/change_info_pane.js" defer charset="utf-8"></script>
	</head>
	<body>
    	<div id="top">
            <div id="nav">
                <b class="nav_element" id="nav_logo">眺望</b>
                <input class="nav_element" id="search_text" type="text">
                <button class="nav_element" id="search_button">查找 </button>
                <button class="nav_element" id="ask_button">提问题</button>
                <button class="nav_element title active" id="first_page">首页</button>
                <button class="nav_element title" id="discovery">发现</button>
                <button class="nav_element title" id="topic">话题</button>
                <%
                	if(have_unread == true) {
                 %>
                	 <div id="user" class="have">
                 <%
                 	}else{
                  %>
                  	<div id="user" class="no">
                  <%} %>
                	<a id="user_pic_link" href=""><img  id="user_pic" src="<%=user.getUser_pic_src() %>" href=""></a>
               		<b id="user_nick"><%=user.getUser_nickname() %></b>
                    	<ul class="user_submenubar">
                        	<li class="user_submenu" id="my_homepage">
                            	<a><img src="img/icon/user.png"/>
                                <span>我的主页</span></a>
                        </li>
                        <%
                			if(have_unread == true) {
                 		%>
                	 	<li class="user_submenu have" id="my_message">
                            <a class="user_submenu_link"><img src="img/icon/write.png"/>
                                <span>私信</span></a>
                        </li>
                		<%
                 			}else{
                  		%>
                  		<li class="user_submenu no" id="my_message">
                            <a class="user_submenu_link"><img src="img/icon/write.png"/>
                               <span>私信</span></a>
                        </li>
                 		<%} %>
                        <li class="user_submenu" id="my_settings">
                            <a><img src="img/icon/set.png"/>
                                <span>设置</span></a>
                        </li>
                        <li class="user_submenu" id="my_exit">
                            <a><img src="img/icon/exit.png"/>
                                <span>退出</span></a>
                        </li>
                    </ul>
            	</div>
            </div>
        </div>
        <div id="middle">
            <div id="middle_left">
            
                <div id="question_block">
                    <h3 id="question_title"><%=question.getQuestion_title() %></h3>
                    <p id="question_content"><%=question.getQuestion_content() %></p>
                    <div id="question_footer">
                        <a id="asker_link" href="other_user.jsp?other_user_id=<%=asker.getUser_id() %>"><span id="ask_username"><%=asker.getUser_nickname() %></span></a>
                        <span id="ask_text">提问于</span>
                        <span id="ask_time"><%=question.getQuestion_latest_change_time() %></span>
                    </div>
                </div>
                <div id="num">0</div>
                <div id="answers"></div>
				<input type="button" id="get_more_answer_button" value="获取更多"/>
                <div id="question_bottom">
                	<div id="submit_answer_user">
                        <img src="<%=user.getUser_pic_src() %>" id="submit_answer_head"/>
                        <span id="submit_answer_username"><%=user.getUser_nickname() %></span>
                        <span id="submit_answer_sign"><%=user.getUser_description() %></span>
                    </div>
                    <form id="submit_answer_form" action="uploadAnswerPicServlet" method="post" enctype="multipart/form-data">
							<textarea name="answer_content" id="submit_answer_textarea" cols="90" rows="10" wrap="hard" ></textarea>
							<input name="answer_pic" type="file" />
                       	 	<input type="hidden" name="responder_id" value="<%=user.getUser_id() %>"/>
                       	 	<input type="hidden" name="question_id" value="<%=question.getQuestion_id() %>"/>
                       	 	<input type="submit" class="submit" id="submit_answer_button" value="发布回答"/>							
				   </form>    

                </div>
            </div>
            <div id="middle_right">
                <div id="middle_right_top">
                	<b id="hot_question_title">热门问题</b>
	             	<%
						for(Question hot_question: hotQuestionList){%>
							<p class="hot_question">
							<a class="hot_question_link" href="user_question.jsp?question_id=<%=hot_question.getQuestion_id() %>">- <%=hot_question.getQuestion_title() %></a>
							</p>
					<% 	}
					%> 
                </div>
                <div id="middle_right_middle">
                	<b id="hot_user_title">热门用户</b>
	                <%
						for(User temp_user: hotUserList){%>
		                    <div class="hot_user_item">
		                       <a class="hot_user_link" href=""><img class="hot_user_head" src="<%=temp_user.getUser_pic_src()%>"/></a>
		                       <div class="hot_user_item_right">
		                       		<b class="hot_username"><a class="hot_username_link" href="other_user.jsp?other_user_id=<%=temp_user.getUser_id() %>"><%=temp_user.getUser_nickname()%></a></b><br>
		                       		<p class="hot_user_sign"><%=temp_user.getUser_description() %></p>
		                       </div>
		                   </div>
					<% 	}
					%>                 	
                </div>
                <div id="middle_right_bottom">
                	<p id="copyright">@CopyRight© 2015  jichaofdu</p>
                </div>
            </div>   
        </div>
        <!--用户信息修改框-->
        <div id="change_user_info_pane">
        	<p id="alert_tip">如果某项信息不需要修改则无需填入信息</p>
        	<div class="change_div" id="first_change_div">
            	<span class="change_tip">用户简介：</span> 
            	<textarea id="change_description_text"><%=user.getUser_description() %></textarea>
            </div>
            <div class="change_div">
           		<span class="change_tip">密码：</span>
            	<input id="change_password_text" type="password" value="<%=user.getUser_password() %>"/>
            </div>
            <div class="change_div">
            	<span class="change_tip">确认密码：</span>
                <input id="change_passwordagain_text" type="password" value="<%=user.getUser_password() %>"/>
            </div>
            <div class="change_div" id="change_error_alert_div"><p id="change_error_alert">密码输入不符合要求</p></div>
            <div class="change_div">
            	<input type="button" id="cancel_change_info" value="取消"/>
            	<input type="button" id="post_change_info" value="提交修改"/>
            </div>            
            <form action="uploadUserPicServlet" method="post" enctype="multipart/form-data">
				<div class="change_div">
					<span class="change_tip">头像：</span>
					<input name="new_pic" type="file" />
					<input class="btn" type="submit" value="更新头像">
				</div>
			</form>

        </div> 
        <!-- ---------- -->
    	<!---问题框专属区---->    
         <div id="ask_question_pane">
        	<div id="ask_pane_top">
        		<span id="question_title_tip">问题标题：</span>
                <input id="title_text" type="text" placeholder="在此处输入问题标题">
        	</div>
        	<div id="ask_pane_middle">
            	<span id="question_content_tip">问题内容：</span>
        		<textarea id="content_text"></textarea>
        	</div>
        	<div id="ask_pane_bottom">
        		<input type="button" id="post_button" value="提交问题">
                <input type="button" id="cancel_pane" value="取消提问">
        	</div>
        </div>
        <!---------------->    
    </body>