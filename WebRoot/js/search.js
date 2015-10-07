
$(function partB() {
    // Your codes here for part A
    // Your codes here for part A
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







