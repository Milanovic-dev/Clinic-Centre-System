/**
 * 
 */

$(document).ready(function()
{
	
	$.ajax({
		type: 'get',
		url: 'api/auth/sessionUser',
		complete: function(data){
			
			user = data.responseJSON
			
			if(user != undefined)
			{
				$('#drop_down_menu').removeAttr('hidden')
				$('#log_buttons').attr('hidden')
			}	
			if(user == undefined)
			{
				$('#drop_down_menu').attr('hidden')
				$('#log_buttons').removeAttr('hidden')
			}				
					
		}	
	})
	
	
	$('#navbar_prijava_btn').click(function(e){
		e.preventDefault()
		window.location.href = "login.html"
	})
	
	$('#navbar_registracija_btn').click(function(e){
		e.preventDefault()
		window.location.href = "register.html"
	})
	
	$('#dropdown-item_logout').on("click",function(e){
		e.preventDefault()

		$.ajax({
			type : 'POST',
			url: 'api/auth/logout',
			complete: function(data)
			{
				window.location.href = "index.html"
			}
		})
	})

})
