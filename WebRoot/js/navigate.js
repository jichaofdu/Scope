/**
 * 本段代码为导航栏基本函数
 * 修改时间：2015-06-10 10:36
 */

document.getElementById("first_page").onclick = function to_welcome(){
	window.location.href="user_welcome.jsp";
}
document.getElementById("discovery").onclick = function to_welcome(){
	window.location.href="discovery.jsp";
}

/*下边三个函数完成【注销】操作，并返回至主页*/
var logoutObject;
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
document.getElementById("my_exit").onclick = function logout(){
	logoutObject = getXmlHttpRequest();
	var url = "logoutServlet";
	var data = "no data";
	logoutObject.open("post",url,true);
	logoutObject.setRequestHeader("Content-Type",
	 	"application/x-www-form-urlencoded"); 
	//回调处理函数指定为 callback();
	logoutObject.onreadystatechange = handle_logout_result; 
	logoutObject.send(data); 
}
function handle_logout_result(){
	if(logoutObject.readyState==4){
		window.location.href="index.jsp";
	}
}
/*用户点击【我的主页】按钮之后*/
document.getElementById("my_homepage").onclick = function to_my_info(){
	window.location.href="user_info.jsp";
}

/*用户点击【设置】按钮之后*/
document.getElementById("my_settings").onclick = function open_change_pane(){
		//alert("to changes");
		document.getElementById("change_user_info_pane").style.display = "block";
}

/*用户点击【提问】按钮之后*/
document.getElementById("ask_button").onclick = function show_ask(){
	var l = document.getElementById("ask_question_pane");
	l.style.display = "block";
}
/*用户点击【我的私信】按钮之后*/
document.getElementById("my_message").onclick = function open_change_pane() {
	window.location.href="user_message.jsp";
}

/*用户点击【搜索按钮之后】*/
var searchObject;
document.getElementById("search_button").onclick = function do_search(){
	var search_content = document.getElementById("search_text").value;
	//alert(search_content);
	searchObject = getXmlHttpRequest();
	var url = "searchServlet";
	var data = "search_content=" + search_content;
	//alert(data);
	searchObject.open("post",url,true);
	//alert("已经open search");
	searchObject.setRequestHeader("Content-Type",
	 	"application/x-www-form-urlencoded"); 
	//回调处理函数指定为 callback();
	searchObject.onreadystatechange = handle_search_result; 
	searchObject.send(data); 
}

function handle_search_result(){
	if(searchObject.readyState==4){
		//alert("结束");
		window.location.href="search.jsp";
	}
	
}

















