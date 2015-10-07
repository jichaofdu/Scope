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
   		List<User> user_list = (List<User>)request.getSession().getAttribute("search_user_list");
   		List<Question> question_list = (List<Question>)request.getSession().getAttribute("search_question_list");
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
        <title>搜索界面</title>
        <link rel="stylesheet" href="css/search.css">
        <link rel="stylesheet" href="css/navigate.css">
        <link rel="stylesheet" href="css/ask_pane.css">
        <link rel="stylesheet" href="css/change_info_pane.css">
        <script src="lib/jquery-2.1.3.min.js"></script>
        <script type="text/javascript" src="js/search.js" defer charset="utf-8"></script>
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
                <button class="middle_left_button active" id="my_question">搜索结果（问题标题）</button>
                <button class="middle_left_button" id="my_answer">搜索结果（用户）</button>        
                <div class="middle_left_pane active" id="middle_left_question">
	                <%
						for(Question question: question_list){
						    UserBiz userBiz = UserBiz.getInstance();
						    User this_user = userBiz.user_get_by_user_id(question.getQuestion_asker_id());
						%>
							<div class="search_question_block">
		                   		<div class="search_question_title">
		                    		<a class="question_link" href="user_question.jsp?question_id=<%=question.getQuestion_id() %>"><span class="title_span"><%=question.getQuestion_title() %></span></a>     
		                    	</div>
		                    	<div class="search_question_info">
		                        	<a class="asker_link" href="other_user.jsp?other_user_id=<%=this_user.getUser_id() %>"><span class="ask_username"><%=this_user.getUser_nickname() %></span></a>
		                        	<span class="ask_text">更新于</span>
		                        	<span class="ask_time"><%=question.getQuestion_latest_change_time() %></span>
		                    	</div>
		                    </div>
					<% 	}
					%>                   
                </div>
                <div class="middle_left_pane" id="middle_left_answer">
	 				 <%
						for(User this_user: user_list){				
						%>
			 				<div class="search_user_block">
				 				<a href="other_user.jsp?other_user_id=<%=this_user.getUser_id() %>">
				 					<img class="search_user_pic" src="img/user_img/img7.jpg">
				 				</a>
		                        <div class="search_user_basic">
		                        	<p class="search_user_name"><%=this_user.getUser_nickname() %></p>
		                        	<p class="search_user_description"><%=this_user.getUser_description() %></p>
		                            <span class="search_fans_tip">粉丝数：<span><%=this_user.getUser_fans() %></span></span>
		                            <span class="search_focus_tip">关注数：<span><%=this_user.getUser_focus() %></span></span>
		                        </div>
		                    </div>
					<% 	}
					%>   
                </div>
            </div>
        </div>
        <div id="bottom">
        	<!-- 这里放置底部信息界面-->
            <b>@CopyRight© 2015  jichaofdu</b>
        </div>
        <div id="change_user_info_pane">
        	<p id="alert_tip">如果某项信息不需要修改则无需填入信息</p>
        	<div class="change_div" id="first_change_div">
            	<span class="change_tip">用户简介：</span> 
            	<textarea id="change_description_text"></textarea>
            </div>
            <div class="change_div">
           		<span class="change_tip">密码：</span>
            	<input id="change_password_text" type="password"/>
            </div>
            <div class="change_div">
            	<span class="change_tip">确认密码：</span>
                <input id="change_passwordagain_text" type="password"/>
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
        <!---------------->
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
    
    
    
    
    