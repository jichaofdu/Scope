<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
   <%@page import="biz.*" %>
   <%@page import="entity.*" %>
   <%@page import="java.util.List" %>
   <%@page import="java.util.ArrayList" %>
   <%
		System.out.println("[Begin]user_welcome.jsp");
		User user = (User)request.getSession().getAttribute("user");
		if(user == null){
   			response.sendRedirect("index.jsp");
   			return;
   		}
   		System.out.println("[Tip]已经获取登录的用户信息");
   		List<Relation> focus_list = new ArrayList<Relation>();
   		RelationBiz relationBiz = RelationBiz.getInstance();
   		focus_list = relationBiz.relation_list_by_userfrom(user.getUser_id());
   		System.out.println("[Tip]已经获取关注列表");
   		int[] focus_array = new int[focus_list.size()];
   		for(int i = 0;i < focus_list.size();i++){
   			focus_array[i] = focus_list.get(i).getRelation_user_to();
   		}
   		System.out.println("[Tip]已经将关注列表转化为数组");
   		RecentlyActiveItemBiz activeBiz = RecentlyActiveItemBiz.getInstance();
   		List<RecentlyActiveItem> active_list = activeBiz.get_recently_active_items(5, focus_array);
   		System.out.println("[Tip]获取活跃信息");
   		
   		
   		
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
        <title>用户首页</title>
        <link rel="stylesheet" href="css/user_welcome.css">
        <link rel="stylesheet" href="css/navigate.css">
        <link rel="stylesheet" href="css/ask_pane.css">
        <link rel="stylesheet" href="css/change_info_pane.css">
        <script src="lib/jquery-2.1.3.min.js"></script>
        <script type="text/javascript" src="js/user_welcome.js" defer charset="utf-8"></script>
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
                <button class="nav_element title active" id="first_page"><a class="nav_sign_link" >首页</a></button>
                <button class="nav_element title" id="discovery"><a class="nav_sign_link" >发现</a></button>
                <button class="nav_element title" id="topic"><a class="nav_sign_link" >话题</a></button>
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
        	<%
        		for(RecentlyActiveItem activeItem:active_list){
        			int answer_id = activeItem.getItem_questionanswer_id();
        			User here_user = UserBiz.getInstance().user_get_by_user_id(activeItem.getItem_user_id());
        			Question here_question = QuestionBiz.getInstance().question_get_by_question_id(activeItem.getItem_question_id());
        			if(answer_id == -1){
        						
        	 %>
			            <div class="active_item_block_ask">
			            	<a href="other_user.jsp?other_user_id=<%=here_user.getUser_id()%>"><img class="active_ask_user_img" src="<%=here_user.getUser_pic_src() %>"/></a>
			            	<div class="active_ask_top_div">
			            		<span class="active_ask_nick_tip"><span class="active_ask_nick"><%=here_user.getUser_nickname() %></span>提出了问题</span>
			            	</div>
			            	<div class="active_ask_middle_div">
			            		<span class="active_ask_title"><a class="question_link" href="user_question.jsp?question_id=<%=here_question.getQuestion_id() %>"><%=here_question.getQuestion_title() %></a></span>
			            	</div>
			                <div class="active_ask_bottom_div">
			                	<span class="active_ask_answer_num_tip">回答数：<span class="active_ask_answer_num"><%=here_question.getQuestion_numofanswer() %></span></span>
			                	<span class="active_ask_time"><%=activeItem.getItem_active_time() %></span>
			                </div>	
			            </div>        	 			
        	 		<% }else{		
        	 			QuestionAnswer here_answer = QuestionAnswerBiz.getInstance().get_questionanswer_by_questionanswer_id(activeItem.getItem_questionanswer_id());
        	 		%>
			            <div class="active_item_block_answer">
			            	<img class="active_answer_user_img" src="<%=here_user.getUser_pic_src() %>"/>
			            	<div class="active_answer_top_div">
			            		<span class="active_answer_nick_tip"><span class="active_answer_nick"><%=here_user.getUser_nickname() %></span>回答了问题</span>
			            	</div>
			            	<div class="active_answer_middle_div">
			            	    <span class="active_answer_title"><a class="question_link" href="user_question.jsp?question_id=<%=here_question.getQuestion_id() %>"><%=here_question.getQuestion_title() %></a></span>
			            	</div>
			            	<div class="active_answer_bottom_div">
			            		<p class="active_answer_content"><%=here_answer.getQuestionanswer_content() %></p>
			                	<span class="active_answer_time"><%=activeItem.getItem_active_time() %></span>
			                </div>	
			            </div>        	 		
        			<% } %>
        	<%} %>
        	      
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