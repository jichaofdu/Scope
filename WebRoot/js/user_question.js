


var static_num_of_answer;


$(function partB() {

	function replace(j) {
		$($('.comment_block')[j]).toggleClass("active");
	}
	for(var i = 0;i < static_num_of_answer;i++){
		$($('.answer_comment_text')[i]).click(function(){
			alert("success");
			var which = $('.answer_comment_text')[i];
			var answer_id_var = which.nextSibling;
			var answer_id = answer_id_var.innerHTML;
			get_comment_obj = getXmlHttpRequest();
			var url = "getCommentsServlet";
			var data = "answer_id=" + answer_id;
			get_comment_obj.open("post",url,true);
			get_comment_obj.setRequestHeader("Content-Type","application/x-www-form-urlencoded"); 
			get_comment_obj.onreadystatechange = handle_get_comment_result; 
			get_comment_obj.send(data);
			replace(i);
		
		
		});
	}
});



function hasClass(obj, cls) {  
    return obj.className.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'));  
}  
  
function addClass(obj, cls) {  
    if (!this.hasClass(obj, cls)) obj.className += " " + cls;  
}  
  
function removeClass(obj, cls) {  
    if (hasClass(obj, cls)) {  
        var reg = new RegExp('(\\s|^)' + cls + '(\\s|$)');  
        obj.className = obj.className.replace(reg, ' ');  
    }  
}  
  
function toggleClass(obj,cls){  
    if(hasClass(obj,cls)){  
        removeClass(obj, cls);  
    }else{  
        addClass(obj, cls);  
    }  
} 
/*-------------------------获取到当前这个回答的评论------------------------------*/
var get_comment_obj;

function get_comment(ev){
	//如何获取到点击对象
	//对点击对象的获取存疑
	var which = ev;
	toggleClass(which,"active");
	alert(which.className);
	var answer_id_var = which.nextSibling;
	var answer_id = answer_id_var.innerHTML;
	alert(answer_id);
	get_comment_obj = getXmlHttpRequest();
	var url = "getCommentsServlet";
	var data = "answer_id=" + answer_id;
	get_comment_obj.open("post",url,true);
	get_comment_obj.setRequestHeader("Content-Type","application/x-www-form-urlencoded"); 
	get_comment_obj.onreadystatechange = handle_get_comment_result; 
	get_comment_obj.send(data);
}

function handle_get_comment_result(){
	if(get_comment_obj.readyState == 4){
		var list = eval(get_comment_obj.responseText);
		var result = "";
		for(var j = 0;j < list.length;j++){
			var content = list[j]["content"];
			var time = list[j]["time"];
			var nick = list[j]["nick"];
			var pic = list[j]["pic_src"];
			var responder = list[j]["responder"];
			result += "<div class='comment_item'>"
				    + 		"<div class='comment_item_user'>"
				    + 			"<a href='other_user.jsp?other_user_id=" + responder + "'><img src='" + pic + "' class='comment_item_head'/></a>"
				    + 			"<a class='comment_item_username_link' href='other_user.jsp?other_user_id=" + responder + "'><span class='comment_item_username'>" + nick + "</span></a>"
				    + 		"</div>"
				    + 		"<p class='comment_item_content'>" + content +"</p>"
				    +		"<span class='comment_item_footer'>" + time +"</span>"
				    + "</div>";
		}
		alert(result);
		document.getElementById("comment_blocks").innerHTML = result;
		alert(document.getElementById("comment_blocks").innerHTML);
	}
}
/*--------------------------提交评论--------------------------------*/
var postCommentObject;

function post_comment(event){
	//如何获取到点击对象
	//对点击对象的获取存疑
	var answer_id_var = which.nextSibling;
	var answer_id = answer_id_var.innerHTML;
	alert(answer_id);
	var comment_content = document.getElementById("add_comment_content").value;
	postCommentObject = getXmlHttpRequest();
	var url = "addCommentServlet";
	var data = "answer_id=" + answer_id + "&comment_content=" + comment_content;
	postCommentObject.open("post",url,true);
	postCommentObject.setRequestHeader("Content-Type","application/x-www-form-urlencoded"); 
	//回调处理函数指定为 callback();
	postCommentObject.onreadystatechange = handle_post_comment_result; 
	postCommentObject.send(data); 
}

function handle_post_comment_result(){
	if(postCommentObject.readyState==4){
		var resultstr = postCommentObject.responseText; 
		var obj = JSON.parse(resultstr);
		var status = obj["status"];
		if(status == "ok"){
			alert("评论提交成功");
			window.location.href = window.location.href;
		}else{
			alert("评论提交失败");
		}
	}
}






/*---------------------获取到更多的回答----------------*/
var get_more_answer_obj;

window.onload = get_more_answer;


//在页面加载时，先从数据库获取到一批信息
document.getElementById("get_more_answer_button").onclick = function get_more(){
	//alert("点击按钮事件触发");
	//alert("启动获取更多答案");
	get_more_answer_obj = getXmlHttpRequest();
	var num = document.getElementById('num').innerHTML;
	//需要在页面中创建存储question_id的区域
	var question_id = document.getElementById('question_id').innerHTML;
	//alert("当前问题的id" + question_id);
	//alert(num);
	var url = "getMoreAnswerServlet";
	var data = "num=" + num + "&question_id=" + question_id;
	//alert("data:" + data);
	get_more_answer_obj.open("post",url,true);
	get_more_answer_obj.setRequestHeader("Content-Type","application/x-www-form-urlencoded"); 
	get_more_answer_obj.onreadystatechange = handle_add_more_result; 
	get_more_answer_obj.send(data); 
}
//用户点击获取更多按钮，获取更多按钮从数据库再获取一批信息（上一次的截止信息储存在页面中）
function get_more_answer(){
	//alert("启动获取更多答案");
	get_more_answer_obj = getXmlHttpRequest();
	var num = document.getElementById('num').innerHTML;
	//需要在页面中创建存储question_id的区域
	var question_id = document.getElementById('question_id').innerHTML;
	//alert("当前问题的id" + question_id);
	//alert(num);
	var url = "getMoreAnswerServlet";
	var data = "num=" + num + "&question_id=" + question_id;
	//alert("data:" + data);
	get_more_answer_obj.open("post",url,true);
	get_more_answer_obj.setRequestHeader("Content-Type",
	 	"application/x-www-form-urlencoded"); 
	get_more_answer_obj.onreadystatechange = handle_add_more_result; 
	get_more_answer_obj.send(data); 
}


function handle_add_more_result(){
	if(get_more_answer_obj.readyState == 4){
		//alert("返回处理");
        var list = eval(get_more_answer_obj.responseText);
		var result = "";
		static_num_of_answer = list.length;
		for(var j = 0;j < list.length;j++){
			var answer_id = list[j]["id"];
			var content = list[j]["content"];
			var numofcomment = list[j]["numofcomment"];
			var time = list[j]["time"];
			var nick = list[j]["nick"];
			var pic_src = list[j]["pic_src"];
			var responder = list[j]["responder"];
			var description = list[j]["description"];
			result += "<div class='answer_block'>"
					+     "<div class='answer_user'>"
					+     	   "<a class='answer_name_link' href='other_user.jsp?other_user_id=" + responder + "'><img src='" + pic_src + "' class='answer_user_head'></a>"
					+          "<span class='answer_username'><a class='answer_name_link' href='other_user.jsp?other_user_id=" + responder + "'>" + nick + "</a></span>"
					+          "<span class='answer_user_sign'>" + description + "</span>"
					+     "</div>"
					+     "<p class='answer_content'>" + content + "</p>"
					+	  "<div class='answer_footer'>"	
					+         "<span class='answer_comment_text' onClick='get_comment(this)'>评论(" + numofcomment + ")</span><div class='post_answer_comment_id'>" + answer_id + "</div>"
					+         "<span class='answer_time'>" + time + "</span>"
					+     "</div>" 
					+     "<div class='comment_block'>"
					+         "<div class='sanjiao'></div>"
					+         "<div id='comment_blocks'></div>"
			        +         "<div class='send_comment_block' id='send_comment_block'>"
			        +	          "<input type='text' class='comment_block_input_text' id='add_comment_content'/>"
			        +             "<button id='add_comment_button' class='comment_block_button' onClick='post_comment(this)''>提交评论</button><div class='post_answer_comment_id'>" + answer_id + "</div>"
			        +         "</div>"
					+     "</div>"
					+ "</div>";
		}
		//alert("result:" + result);
		document.getElementById("answers").innerHTML = result;
		document.getElementById("num").innerHTML = list.length;
	}
}
/*--------------------------提交回答--------------------------------*/
var postAnswerObject;
//创建Ajax引擎对象 
function getXmlHttpRequest(){
    var xmlHttpRequest= "";
    if(window.XMLHttpRequest){ // 火狐
        xmlHttpRequest = new XMLHttpRequest();
    }
    else{ // IE
        xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return xmlHttpRequest;
}
document.getElementById("submit_answer_button").onclick = function post_answer(){
	var question_id = document.getElementById("question_id").innerHTML;
	var responder_id = document.getElementById("responder_id").innerHTML;
	var answer_content = document.getElementById("submit_answer_textarea").value;
	postAnswerObject = getXmlHttpRequest();
	var url = "addAnswerServlet";
	var data = "question_id=" + question_id + "&responder_id=" + responder_id + "&answer_content=" + answer_content;
	postAnswerObject.open("post",url,true);
	postAnswerObject.setRequestHeader("Content-Type","application/x-www-form-urlencoded"); 
	//回调处理函数指定为 callback();
	postAnswerObject.onreadystatechange = handle_post_answer_result; 
	postAnswerObject.send(data); 
}
function handle_post_answer_result(){
	if(postAnswerObject.readyState==4){
		var resultstr = postAnswerObject.responseText; 
		var obj = JSON.parse(resultstr);
		var status = obj["status"];
		if(status == "ok"){
			alert("答案提交成功");
			window.location.href = window.location.href;
		}else{
			alert("答案提交失败");
		}
	}
}
