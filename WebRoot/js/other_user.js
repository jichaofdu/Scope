
$(function partB() {
	function replace(j) {
		for(var i = 0; i < 2; i++) {
			$($('.middle_left_button')[i]).removeClass("active");
			$($('.middle_left_pane')[i]).removeClass("active");
		}
		$($('.middle_left_button')[j]).addClass("active");
		$($('.middle_left_pane')[j]).addClass("active");
	}
	$($('.middle_left_button')[0]).click(function(){replace(0)});
	$($('.middle_left_button')[1]).click(function(){replace(1)});
});



var cancel_focus_obj;
var add_focus_obj;

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


//点击【取消关注按钮之后的操作】
function cancel_focus(){
	//alert("按钮已点击");
	var from_id = document.getElementById("from_id").innerHTML;
	var to_id = document.getElementById("to_id").innerHTML;
	cancel_focus_obj = getXmlHttpRequest();
	var url = "cancelFocusServlet";
	var data = "from_id=" + from_id + "&to_id=" + to_id;
	cancel_focus_obj.open("post",url,true);
	cancel_focus_obj.setRequestHeader("Content-Type","application/x-www-form-urlencoded"); 
	//回调处理函数指定为 callback();
	cancel_focus_obj.onreadystatechange = handle_cancel_focus_result; 
	cancel_focus_obj.send(data); 
}

function handle_cancel_focus_result(){
	if(cancel_focus_obj.readyState==4){
		window.location.href = window.location.href;
	}
}
//点击【添加按钮之后的操作】
function add_focus(){
	//alert("按钮已点击");
	var from_id = document.getElementById("from_id").innerHTML;
	var to_id = document.getElementById("to_id").innerHTML;
	//alert("from:" + from_id);
	//alert("to:" + to_id);
	add_focus_obj = getXmlHttpRequest();
	var url = "addFocusServlet";
	var data = "from_id=" + from_id + "&to_id=" + to_id;
	add_focus_obj.open("post",url,true);
	//alert("open end");
	add_focus_obj.setRequestHeader("Content-Type","application/x-www-form-urlencoded"); 
	//回调处理函数指定为 callback();
	add_focus_obj.onreadystatechange = handle_add_focus_result; 
	add_focus_obj.send(data);
	
}

function handle_add_focus_result(){
	if(add_focus_obj.readyState==4){
		window.location.href = window.location.href;
	}
}