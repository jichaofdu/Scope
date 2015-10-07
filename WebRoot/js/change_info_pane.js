var infoChangeObject;
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

document.getElementById("post_change_info").onclick = function open_change_pane(){
	var descripion = document.getElementById("change_description_text").value;
	var password = document.getElementById("change_password_text").value;
	var password_again = document.getElementById("change_passwordagain_text").value;
	var pass_check = check_password(password);
	if(pass_check != 1 || password != password_again){
		document.getElementById("change_error_alert_div").style.display = "block";
		return;
	}
	infoChangeObject = getXmlHttpRequest();
	var url = "userInfoChangeServlet";
	var data = "description=" + descripion +"&password=" + password;
	infoChangeObject.open("post",url,true);
	infoChangeObject.setRequestHeader("Content-Type",
 		"application/x-www-form-urlencoded"); 
	//回调处理函数指定为 callback();
	infoChangeObject.onreadystatechange = handle_infochange_result; 
	infoChangeObject.send(data); 
}

function handle_infochange_result(){
	if(infoChangeObject.readyState==4){
		var resultstr = infoChangeObject.responseText; 
		var obj = JSON.parse(resultstr);
		var status = obj["status"];
		if(status == "ok"){
			alert("修改保存成功");
		}else if(status == "password_form_error"){
			alert("密码格式错误");
		}else{
			alert("信息保存错误");
		}
		document.getElementById("change_user_info_pane").style.display = "none";
	}
}

function check_password(obj){
	var password = obj;
	var length = password.length;
	var password_form_1 = /^[0-9]*$/; //全是数字
	var password_form_2 = /^[A-Za-z0-9]+$/;
	if(length < 6 || length > 16){//长度不符合规定
		return 0;	
	}
	if(password_form_1.test(password)){//全是数字不符合规定
		return 0;
	}
	if(password_form_2.test(password)){//现在符合规定了
		return 1;
	}
	return 0;
}
/*用户点击【设置】按钮之后*/
document.getElementById("my_settings").onclick = function open_change_pane(){
		document.getElementById("change_user_info_pane").style.display = "block";
}
document.getElementById("cancel_change_info").onclick = function close_change_pane(){
	//alert("close");
	document.getElementById("change_user_info_pane").style.display = "none";
}