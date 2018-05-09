$(function() {
	$("form").validate({
		rules : {
			computerName : "required"
		},

		messages : {
			computerName : "Please enter the name of the computer"
		},

		submitHandler : function(form) {
			form.submit();
		}
	});
});


$("input[name='introduced']").change(function() {
	$("input[name='discontinued']").attr("min", $(this).val());
})