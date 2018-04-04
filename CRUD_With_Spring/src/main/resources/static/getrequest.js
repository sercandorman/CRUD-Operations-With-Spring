$(document).ready(function() {
	$('#getAll').on('click', function(event) {
		event.preventDefault();
		ajaxGet();
	});
	$('#hideAll').on('click', function(event) {
		$("#getResultDiv").fadeOut("slow");
	});
	function ajaxGet() {
		$.ajax({
			type : "GET",
			url : window.location + "findAll",
			complete : function() {
				$("#getResultDiv").hide().load(location.href + " #getResultDiv>*", "").fadeIn("slow");
			},
			error : function(e) {
				$("#getResultDiv").html("<strong>HATA | ERROR</strong>");
				console.log("ERROR: ", e);
			}
		});
	}
});
