
window.onload = send_message;

var get_more_obj;

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
//在页面加载时，先从数据库获取到一批信息
document.getElementById('get_more').onclick = function get_more(){
	//alert("点击按钮事件触发");
	send_message();
}
//用户点击获取更多按钮，获取更多按钮从数据库再获取一批信息（上一次的截止信息储存在页面中）
function send_message(){
	get_more_obj = getXmlHttpRequest();
	var num = document.getElementById('num').innerHTML;
	//alert(num);
	var url = "getMoreDiscoveryServlet";
	var data = "num=" + num;
	get_more_obj.open("post",url,true);
	get_more_obj.setRequestHeader("Content-Type",
	 	"application/x-www-form-urlencoded"); 
	//回调处理函数指定为 callback();
	get_more_obj.onreadystatechange = handle_add_more_result; 
	get_more_obj.send(data); 
}

//然后将获取到的信息输入到页面中
function handle_add_more_result(){
	if(get_more_obj.readyState == 4){
		 //然后解析ajax信息为数组
		 //更新num值
         var list = eval(get_more_obj.responseText);
		 var result = "";
		 for(var j = 0;j < list.length;j++){
			 var id = list[j]["id"];
			 var title = list[j]["title"];
			 var time = list[j]["time"];
			 var answer = list[j]["answer"];
			 if(answer == "no_answer"){
				 result += "<div class='discovery_block'>" 
					       + "<div class='discovery_question_title'>"
				           + "<a class='question_link' href='user_question.jsp?question_id=" + id + "'><span class='title_span'>" + title +"</span></a>"
				           + "</div>"
				           + "<div class='discovery_question_info'>"
				           + "<span class='ask_text'>更新于</span>"
				           + "<span class='ask_time'>" + time + "</span>"
				           + "</div>"
				           + "</div>";	 
			 }else{
				 result += "<div class='discovery_block'>" 
				         +     "<div class='discovery_question_title'>"
			             +         "<a class='question_link' href='user_question.jsp?question_id=" + id + "'><span class='title_span'>" + title +"</span></a>"
			             +     "</div>"
			             +     "<div class='discovery_answer_content'>"
			             +         "<span>" + answer + "</span>"
			             +     "</div>"
			             +     "<div class='discovery_question_info'>"
			             +         "<span class='ask_text'>更新于</span>"
			             +         "<span class='ask_time'>" + time + "</span>"
			             +     "</div>"
			             + "</div>";	
			 }
		 }
		 document.getElementById("num").innerHTML = list.length;
		 document.getElementById("middle_left").innerHTML = result;
	}
}




