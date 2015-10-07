<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
       <%@page import="biz.*" %>
   <%@page import="entity.*" %>
   <%@page import="java.util.List" %>
   <%@page import="java.util.ArrayList" %>
   <%
        User user;
        int status;
      	if(request.getParameter("user_id") == null){
      		user = (User)request.getSession().getAttribute("user");
      		status = 0;
   		}else{
   			int user_id = Integer.parseInt((String)request.getParameter("user_id"));
   			UserBiz userBiz = UserBiz.getInstance();
   			user = userBiz.user_get_by_user_id(user_id);
   			status = 1;
   		}
   		if(user == null){
   			System.out.println("[Error]user_message未获得登录用户");
   	        response.sendRedirect("index.jsp");
   			return;
   		}
   		MessageBiz messageBiz = MessageBiz.getInstance();
   		List<Message> message_list_my_receive = messageBiz.message_list_by_receiver_id(user.getUser_id());
   		List<Message> message_list_my_send = messageBiz.message_list_by_sender_id(user.getUser_id());
   		List<User> receiver_user_list = new ArrayList<User>();
   		List<User> sender_user_list = new ArrayList<User>();
   		int send_size = message_list_my_send.size();
   		for(int i = 0;i < send_size;i++){
   			Message message = message_list_my_send.get(i);
   			User here_user = UserBiz.getInstance().user_get_by_user_id(message.getReceiver());
   			receiver_user_list.add(here_user);
   		}
   		int receive_size = message_list_my_receive.size();
   		for(int i = 0;i < receive_size;i++){
   			Message message = message_list_my_receive.get(i);
   			User here_user = UserBiz.getInstance().user_get_by_user_id(message.getSender());
   			sender_user_list.add(here_user);
   		}
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
        <title>用户私信界面</title>
        <link rel="stylesheet" href="css/my_message.css">
        <link rel="stylesheet" href="css/navigate.css">
        <link rel="stylesheet" href="css/ask_pane.css">
        <link rel="stylesheet" href="css/change_info_pane.css">
        <script src="lib/jquery-2.1.3.min.js"></script>
        <script type="text/javascript" src="js/my_message.js" defer charset="utf-8"></script>
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
                <button class="middle_left_button active" id="my_question">我发送的私信</button>
                <button class="middle_left_button" id="my_answer">我收到的私信</button> 
                <button class="middle_left_button" id="my_send">我要发送私信</button>        
                <div class="middle_left_pane active" id="middle_left_question">
                	<% int i = 0;
					   for(Message message: message_list_my_send){
					   		String temp_nick = receiver_user_list.get(i).getUser_nickname();
					   %>
                			<div class="message_block">
                				<div class="message_block_div">
                					<span class="message_receiver_tip">接收方：<span class="message_receiver"><%=temp_nick %></span></span>
                				</div>
                				<div class="message_block_div">
                					<p class="message_content"><%=message.getContent() %></p>
                				</div>
                				<div class="message_time_div">
                					<span class="message_time_tip">发送于</span>
		                			<span class="message_time"><%=message.getSend_time() %></span>
                				</div>
                			</div>
				    <% 	
						   i++;
				       }
					%>
                </div>
                <div class="middle_left_pane" id="middle_left_answer">
                    <% int j = 0;
					   for(Message message: message_list_my_receive){
					   		String temp_nick = sender_user_list.get(j).getUser_nickname();
					   %>
						<div class="message_block">
	                		<div class="message_block_div">
	                			<span class="message_sender_tip">发送方：<span class="message_sender"><%=temp_nick %></span></span>
	                		</div>
	                		<div class="message_block_div">
	                			<p class="message_content"><%=message.getContent() %></p>
	                		</div>
	                		<div class="message_time_div">
	                			<span class="message_time_tip">接收于</span>
			                	<span class="message_time"><%=message.getSend_time() %></span>
	                		</div>
	                	<%
	                		if(message.isIs_read() == false){
	                	 %>
	                		<div class="message_change_status">
	                			<input type="button" value="设为已读" class="message_status_button" onclick="change_message_status(this)"/><div class="temp_message_id"><%=message.getMessage_id() %></div>
	                		</div>
	                	<%
	                		}
	                	 %>
	                	</div>
				    <% 	
						   j++;
				       }
					%>
                </div>
                <div class="middle_left_pane" id="middle_left_send">
		            <div class="send_div_to">
		                <div class="to_div">
							<span class="to_tip">发送给（昵称）：</span>
							<input type="text" class="to" id="to_receiver_nickname"/>
						</div>
						<div class="to_div">
							<span class="to_content_tip">私信内容：</span>
							<textarea class="to_content" id="to_receiver_content"></textarea>
						</div>
						<input type="button" class="send_message" id="send_message_button" value="发送"/>
	                </div>
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