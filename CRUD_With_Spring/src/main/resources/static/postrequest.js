$(document).ready(function() {
    $("#customerForm").submit(function(event) {
		event.preventDefault();
		ajaxPost();
	});
    function ajaxPost(){
    	var formData = {
        		name : $("#name").val(),
        		surname : $("#surname").val()
        	}
    	$.ajax({
    		type : "POST",
			contentType : "application/json",
			url : window.location + "add",
			data : JSON.stringify(formData),
			success : function() {
				$("#postResultDiv").hide().html("<div class='alert alert-success'>" +
						"<strong>Registration successful !</strong></div>").slideDown("slow");
				if($("#table").is(":visible")) {
					$("#getAll").click();
				}		
				console.log(result);
			},
			error : function(e) {
				$("#postResultDiv").html("<div class='alert alert-danger'><strong>Error.</strong></div>");
				console.log("ERROR: ", e);
			}
		});
    	resetData();
    }
    function resetData(){
    	$("#name").val("");
    	$("#surname").val("");
    }
});