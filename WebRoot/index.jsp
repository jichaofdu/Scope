<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@page import="biz.*" %>
   <%@page import="entity.*" %>
   <%@page import="java.util.List" %>
   <%
  	    request.setCharacterEncoding("UTF-8");
		UserBiz userBiz = UserBiz.getInstance();
		List<User> hotUserList = userBiz.getHotUser(10);
		QuestionBiz questionBiz = QuestionBiz.getInstance();
		List<Question> hotQuestionList = questionBiz.get_hot_question(7); 
   %>
   
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>眺望-首页</title>
        <link rel="stylesheet" href="css/index.css">
        <script type="text/javascript" src="js/index.js" defer charset="utf-8"></script>
    </head>
    <body>
    	<div id="top">
        	<div id="top_inner">
                <div id="top_left">
                    <b class="top_sign" id="top_sign_one">眺望</b>
                    <b class="top_sign" id="top_sign_two">世界从此不同</b>
                </div>
                <div id="top_right">
                    <div id="login_part">
                    	<div class="login">
                        	<b class="login_line" id="login_sign">登录眺望</b>
                       		<button class="login_line" id="to_register">切换至注册→ →</button>
                        </div>
                        <input class="login" id="user_name" type="text" value="13302010019@fudan.edu.cn" placeholder="输入用户名：注册时的邮箱">
                        <input class="login" id="user_password" type="password" value="140311jc" placeholder="输入密码">
                        <b class="login" id="password_wrong">密码与用户名不匹配，请重试</b>
                        <button class="login" id="login">登录</button>
                    </div>
                    <div id="register_part">
                        <div class="register">
                        	<b class="register_line" id="register_sign">注册账号</b>
                       		<button class="register_line" id="to_login">切换至登录→ →</button>
                        </div>
                        <input class="register" id="register_nickname" type="text" placeholder="请输入您的昵称（长度2-16）">
                        <input class="register" id="register_mail" type="text" placeholder="请输入您的邮箱">
                        <input class="register" id="register_password" type="password" placeholder="请输入您的密码（长度6-16，不可为纯数字）">
                        <input class="register" id="register_password_again" type="password" placeholder="请再次确认您的密码">
                        
                        <b class="register" id="register_error">用户名或密码设置有错误，请重试</b>
                        <button class="register" id="register">注册为新用户</button>
                    </div>
                </div>
            </div>
		</div>   
        <div id="middle">
        	<div id="middle_top">
                <b class="hot_title" id="hot_user_title">热门用户</b><br>
	                <%
						for(User user: hotUserList){%>
						<a class="hot_user" href="此处跳转到用户界面">
							<img class="user_img" src="<%=user.getUser_pic_src() %>"/>
						</a>
					<% 	}
					%> 
            </div>
            <div id="middle_bottom">
            	<b class="hot_title" id="hot_question_title">热门问题</b>
             	<%
					for(Question question: hotQuestionList){%>
					<b class="hot_questions">
					<a class="hot_link"href="user_question.jsp?question_id=<%=question.getQuestion_id() %>">- <%=question.getQuestion_title() %></a>
					</b>
				<% 	}
				%> 
            </div>
        </div>
        <div id="bottom">
        	<p id="copy_right">@jichaofdu 2015-3-29</p>
        </div>
        
        
          
        <!-------用户信息小框专属区--------> 
		<div class="user_info_pane">
        	<div class="user_pane_top">
            	<img class="user_pane_img" src="">
                <span class="user_pane_nickname">用户昵称</span>
                <p class="user_pane_description">测试一个签名</p>
            </div>
        	<div class="user_info_bottom">
            	<span class="user_pane_focus_tip">关注了<span class="user_pane_focus_num"></span>人</span>
                <span class="user_pane_fans_tip">粉丝数<span class="user_pane_fans_num"></span>人</span>
            </div>
        </div>
        
    </body>