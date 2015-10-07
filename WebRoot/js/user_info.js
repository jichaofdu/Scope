/**
 * 本部分函数用于user_info页面中的两个标签页的切换
 * 修改时间：2015-06-10 10:34
 */



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







