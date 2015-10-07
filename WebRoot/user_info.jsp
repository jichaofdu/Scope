<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
   <%@page import="biz.*" %>
   <%@page import="entity.*" %>
   <%@page import="java.util.List" %>
   <%@page import="java.util.ArrayList" %>
   <%	
        User user = (User)request.getSession().getAttribute("user");
   		if(user == null){
   	        response.sendRedirect("index.jsp");
   			return;
   		}
   		/*获取自己的提问*/
   	    QuestionBiz questionBiz = QuestionBiz.getInstance();
		List<Question> askList = questionBiz.question_list_by_asker(user.getUser_id());
		/*获取自己的回答，以及每个回答对应的问题标题*/
		QuestionAnswerBiz questionAnswerBiz = QuestionAnswerBiz.getInstance();
		List<QuestionAnswer> answerList = questionAnswerBiz.questionanswer_list_by_user_id(user.getUser_id());
   		List<Question> titleList = new ArrayList<Question>();
   		int size = answerList.size();
   		for(int i = 0;i < size;i++){
   			QuestionAnswer answer = answerList.get(i);
   			Question question = questionBiz.question_get_by_question_id(answer.getQuestionanswer_question_id());
   			titleList.add(question);
   		}
   		UserBiz userBiz = UserBiz.getInstance();
		List<User> hotUserList = userBiz.getHotUser(3);
		List<Question> hotQuestionList = questionBiz.get_hot_question(5);
		//获取自己最近的粉丝和自己最近的关注
		RelationBiz relationBiz = RelationBiz.getInstance();
		List<Relation> new_fans_list = relationBiz.get_new_fans(7, user.getUser_id());
		List<Relation> new_focus_list = relationBiz.get_new_focus(7, user.getUser_id());
		
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
        <title>用户主界面</title>
        <link rel="stylesheet" href="css/user_info.css">
        <link rel="stylesheet" href="css/navigate.css">
        <link rel="stylesheet" href="css/ask_pane.css">
        <link rel="stylesheet" href="css/change_info_pane.css">
        <script src="lib/jquery-2.1.3.min.js"></script>
        <script type="text/javascript" src="js/user_info.js" defer charset="utf-8"></script>
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
                <button class="nav_element title" id="first_page">首页</button>
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
                	<a id="user_main_pic_link" href=""><img id="user_pic" src="<%=user.getUser_pic_src() %>"></a>
               		<b id="user_nick"><%=user.getUser_nickname() %></b>
                    <ul class="user_submenubar">
                        <li class="user_submenu" id="my_homepage">
                            <a class="user_submenu_link"><img src="img/icon/user.png"/>
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
                            <a class="user_submenu_link"><img src="img/icon/set.png"/>
                                <span>设置</span></a>
                        </li>
                        <li class="user_submenu" id="my_exit">
                            <a class="user_submenu_link"><img src="img/icon/exit.png"/>
                                <span>退出</span></a>
                        </li>
                    </ul> 
            	</div>
            </div>
        </div>
        <div id="middle">
            <div id="middle_left">
                <button class="middle_left_button active" id="my_question">我的提问</button>
                <button class="middle_left_button" id="my_answer">我的回答</button>
                <div class="middle_left_pane active" id="middle_left_question">
	                <%
						for(Question question: askList){%>
							<p class="user_question">
								<a class="my_question_link" href="user_question.jsp?question_id=<%=question.getQuestion_id() %>">
								- <%=question.getQuestion_title() %>
								</a>
							</p>
					<% 	}
					%>
                </div>
                <div class="middle_left_pane" id="middle_left_answer">
                	<% int i = 0;
					   for(QuestionAnswer answer: answerList){
					   		int here_question_id = answer.getQuestionanswer_question_id();
					   		Question here_question = questionBiz.question_get_by_question_id(here_question_id);
					   %>
                	       <div class="answer_item">
                    		   <p class="item_title"><a class="question_link" href="user_question.jsp?question_id=<%=here_question.getQuestion_id() %>"><%=titleList.get(i).getQuestion_title() %></a></p>
                        	   <p class="item_answer"><%=answer.getQuestionanswer_content() %></p>
                        	   <span class="timer"><%=answer.getQuestionanswer_time() %></span>
                    	   </div>
				    <% 	
						   i++;
				       }
					%>
                </div>
            </div>
            <div id="middle_right">
            	<!-- 这里放置右侧的信息界面-->
                <div id="middle_right_top">
					<div id="user_pic_div">
                    	<img id="user_pic_big" src="<%=user.getUser_pic_src() %>">
                    </div>
                    <p id="middle_user_name"><%=user.getUser_nickname() %></p>
                    <div id="middle_user_sign_div">   	
                    	<p id="middle_user_sign"><%=user.getUser_description() %></p>
                    </div>
                    <div id="user_info">
                    	<span id="user_focus_num_tip">关注了</span>
                        <span id="user_focus_by_num_tip">关注者</span><br>
                        <div id="num">
                        	<span id="user_focus_num_outer"><b id="user_focus_num"><%=user.getUser_focus() %></b>人</span>
                        	<span id="user_focus_by_num_outer"><b id="user_focus_by_num"><%=user.getUser_fans() %></b>人</span>
                        </div>
                    </div>
                </div>
                <div id="middle_right_middle">
                	<b id="recent_focus">最近关注了</b><br>
                	<%
                		for(Relation here_relation:new_focus_list){
                			int to_id = here_relation.getRelation_user_to();
                			User here_user = userBiz.user_get_by_user_id(to_id);
                	 %>
                	 <a class="recent_focus_pic" href="other_user.jsp?other_user_id=<%=here_user.getUser_id() %>"><img class="small_pic" src="<%=here_user.getUser_pic_src() %>"></a>
                	 <%
                	 	}
                	  %>
                </div>
                
                <div id="middle_right_bottom">
                	<b id="recent_focus_by">最近被他们关注了</b><br>
                	<%
                		for(Relation here_relation:new_fans_list){
                			int from_id = here_relation.getRelation_user_from();
                			User here_user = userBiz.user_get_by_user_id(from_id);
                		
                	 %>
                	 	<a class="recent_focus_by_pic" href="other_user.jsp?other_user_id=<%=here_user.getUser_id() %>"><img class="small_pic" src="<%=here_user.getUser_pic_src() %>"></a>
                	 <%
                	 	}
                	  %>

                </div>
            </div>
        </div>
        <div id="bottom">
        	<!-- 这里放置底部信息界面-->
            <b>@CopyRight© 2015  jichaofdu</b>
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
    	<!---提问框代码---->
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