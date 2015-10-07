
$(function partB() {
    // Your codes here for part A
    // Your codes here for part A
	function replace(j) {
		for(var i = 0; i < 3; i++) {
			$($('.middle_left_button')[i]).removeClass("active");
			$($('.middle_left_pane')[i]).removeClass("active");
		}
		$($('.middle_left_button')[j]).addClass("active");
		$($('.middle_left_pane')[j]).addClass("active");
	}
	$($('.middle_left_button')[0]).click(function(){replace(0)});
	$($('.middle_left_button')[1]).click(function(){replace(1)});
	$($('.middle_left_button')[2]).click(function(){replace(2)});
});

document.getElementById("my_homepage").onclick = function to_my_info(){
		window.location.href="user_info.jsp";
}

//------------------------------发送消息专属区-------------------
var sendMessageObject;
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
document.getElementById("send_message_button").onclick = function send_message(){
	sendMessageObject = getXmlHttpRequest();
	var receiver_nick = document.getElementById("to_receiver_nickname").value;
	var receiver_content = document.getElementById("to_receiver_content").value;
	var url = "sendMessageServlet";
	var data = "receiver_nick=" + receiver_nick + "&receiver_content=" + receiver_content;
	sendMessageObject.open("post",url,true);
	sendMessageObject.setRequestHeader("Content-Type",
	 	"application/x-www-form-urlencoded"); 
	//回调处理函数指定为 callback();
	sendMessageObject.onreadystatechange = handle_send_message_result; 
	sendMessageObject.send(data); 
}

function handle_send_message_result(){
	if(sendMessageObject.readyState == 4){
		var resultstr = sendMessageObject.responseText; 
		var obj = JSON.parse(resultstr);
		var status = obj["status"];
		if(status == "ok"){
			alert("私信发送成功");
			window.location.href = window.location.href;
		}else if(status == "error"){
			alert("用户存在，但消息发送失败");
		}else{
			alert("接收方不存在");
		}
	}
}
//-------------------------------用于点击按钮之后改变私信的状态--------------------------
var change_message_status_obj;

function change_message_status(ev){
	//alert("按钮触发");
	var which = ev;
	var message_id_var = which.nextSibling;
	var message_id = message_id_var.innerHTML;
	//alert(message_id);
	var url = "changeMessageStatusServlet";
	var data = "message_id=" + message_id;
	hange_message_status_obj = getXmlHttpRequest();
	hange_message_status_obj.open("post",url,true);
	hange_message_status_obj.setRequestHeader("Content-Type",
	 	"application/x-www-form-urlencoded"); 
	//回调处理函数指定为 callback();
	hange_message_status_obj.onreadystatechange = handle_change_status_result; 
	hange_message_status_obj.send(data); 
}

function handle_change_status_result(){
	if(hange_message_status_obj.readyState == 4){
		alert("操作成功");
		window.location.href = window.location.href;
	}
}




