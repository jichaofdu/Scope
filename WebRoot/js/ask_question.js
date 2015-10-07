/**
 * 涉及到提问框的处理代码
 * 修改时间：2015-06-10 10:35
 */
var postQuestionObject;
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
document.getElementById("post_button").onclick = function post_question(){
	var titleObj = document.getElementById("title_text");
	var contentObj = document.getElementById("content_text");	
	var titleStr = titleObj.value;
	var contentStr = contentObj.value;
	postQuestionObject = getXmlHttpRequest();
	var url = "askQuestionServlet";
	var data = "title=" + titleStr + "&" + "content=" + contentStr; //发送的消息
	postQuestionObject.open("post",url,true);
	postQuestionObject.setRequestHeader("Content-Type",
	 	"application/x-www-form-urlencoded"); 
	//回调处理函数指定为 callback();
	postQuestionObject.onreadystatechange = handle_ask_result; 
	postQuestionObject.send(data); 
}
function handle_ask_result(){
	if(postQuestionObject.readyState==4){
		var resultstr = postQuestionObject.responseText; 
		var obj = JSON.parse(resultstr);
		var status = obj["status"];
		if(status == "ok"){
			alert("问题提交成功");
			var l = document.getElementById("ask_question_pane");
			l.style.display = "none";
		}else{
			alert("问题提交失败");
		}
	}
}
document.getElementById("cancel_pane").onclick = function cancel_ask(){
	var l = document.getElementById("ask_question_pane");
	l.style.display = "none";
}