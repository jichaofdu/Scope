
/*这两个函数用于在注册页面和登录页面之间切换*/
document.getElementById("to_register").onclick = function convert_to_register() {
 	l = document.getElementById("register_part");
 	l.style.display = "block";
	k = document.getElementById("login_part");
	k.style.display = "none";
}
document.getElementById("to_login").onclick = function convert_to_login() {
 	l = document.getElementById("register_part");
 	l.style.display = "none";
	k = document.getElementById("login_part");
	k.style.display = "block";
}
/*
 用户名长度为2～16字符。
 电子邮箱地址应当格式正确。
 密码只能包括英文和数字，长度为6～16字符，且不能是纯数字。
 所有字段均不能为空且格式要正确。
*/

function check_password(obj){
	var password = obj.value;
	//alert("password:" + password);
	var length = password.length;
	var password_form_1 = /^[0-9]*$/; //全是数字
	var password_form_2 = /^[A-Za-z0-9]+$/;
	if(length < 6 || length > 16){//长度不符合规定
		//alert("error:pass length");
		return 0;	
	}
	if(password_form_1.test(password)){//全是数字不符合规定
		//alert("error:number");
		return 0;
	}
	if(password_form_2.test(password)){//现在符合规定了
		//alert("error:ok");
		return 1;
	}
	//alert("error:end pass chenck");
	return 0;
}
function check_email(obj) {
	var email = obj.value;
	//alert("email:" + email);
	var email_form = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
	if(email_form.test(email)){
		//alert("email:ok");
		return 1;
	}
	//alert("email:fail");
	return 0;
}
function check_username(obj){
	var username = obj.value;
	//alert("username:" + username);
	var username_length = username.length;
	if(username_length < 2 || username_length > 16){
		//alert("username lenth fail");
		return 0;	
	}
	return 1;
}
//报错提示
function show_login_error() {
	l = document.getElementById("password_wrong");
	l.style.display = "block";
}
function show_register_error() {
	l = document.getElementById("register_error");
	l.style.display = "block";
}

var registerAjaxObject;
var loginAjaxObjects;

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

/*注册操作*/
//定义发送消息函数
document.getElementById("register").onclick = function register(){
	   //首先获取到所有消息
		var nickname_obj = document.getElementById("register_nickname");
		var email_obj = document.getElementById("register_mail");
		var password_obj = document.getElementById("register_password");
		var password_again_obj = document.getElementById("register_password_again"); 
	   //然后在js进行验证
		var password_check = check_password(password_obj);
		var username_check = check_username(nickname_obj); 
		var mail_check = check_email(email_obj);
		if(password_again_obj.value == password_obj.value && password_check == 1 && username_check == 1 && mail_check == 1){
			//向servlet提交信息
		    /******** post 方式请求 ********/
			registerAjaxObject = getXmlHttpRequest();
			var url = "registerServlet";
			var data = "nick=" + nickname_obj.value + "&email=" + email_obj.value + "&password=" 
								+ password_obj.value + "&password_again=" + password_again_obj.value; //发送的消息
			registerAjaxObject.open("post",url,true);
			registerAjaxObject.setRequestHeader("Content-Type",
			   "application/x-www-form-urlencoded"); 
			registerAjaxObject.onreadystatechange = handle_register_result; 
			registerAjaxObject.send(data); 
		}else{
			show_register_error();
		}
}

function handle_register_result(){
	if(registerAjaxObject.readyState==4){
		alert("进入处理结果程序");
		var resultstr = registerAjaxObject.responseText; 
		alert(resultstr);
		var obj = JSON.parse(resultstr);
		var status = obj[0]["status"];
		alert(status);
		if(status == "ok"){
			alert("begin jump");
			window.location.href = 'discovery.jsp';
		}else if(status == "error"){
			show_register_error();
		}
	}
}

document.getElementById("login").onclick = function login(){
	//首先获取到所有消息
	var username_obj = document.getElementById("user_name");
	var password_obj = document.getElementById("user_password");
   //然后在js进行验证
	var password_check = check_password(password_obj);
	var username_check = check_email(username_obj);
	if(password_check == 1 && username_check == 1){
		//向servlet提交信息
	    /******** post 方式请求 ********/
		loginAjaxObject = getXmlHttpRequest();
		var url = "loginServlet";
		var data = "username=" + username_obj.value + "&password=" + password_obj.value ; //发送的消息
		loginAjaxObject.open("post",url,true);
		loginAjaxObject.setRequestHeader("Content-Type",
		   "application/x-www-form-urlencoded"); 
		//回调处理函数指定为 callback();
		loginAjaxObject.onreadystatechange = handle_login_result; 
		loginAjaxObject.send(data); 
	}else{
		show_login_error();
	}
}

function handle_login_result(){
	if(loginAjaxObject.readyState==4){
		//alert("进入处理程序");
		var resultstr = loginAjaxObject.responseText; 
		//alert("result:" + resultstr);
		var obj = JSON.parse(resultstr);
		var status = obj["status"];
		//alert("status:" + status);
		if(status == "ok"){
			//alert("begin jump");
			//show_ask();
			window.location.href = 'user_welcome.jsp';
		}else if(status == "error"){
			show_login_error();
		}
	}
}












