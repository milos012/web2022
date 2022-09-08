$(document).ready(function(){
    $("#login-form").submit(function(e){
        e.preventDefault();
        var username = $("input[name='username']").val();
        var password = $("input[name='password']").val();
        if(!username){
            $("input[name='username']").css("border-bottom", "2px solid red")
        } 
        
        if(!password){
            $("input[name='password']").css("border-bottom", "2px solid red")
        }
        if(username && password){
            $.get({
			url: "/WebProject/rest/users/login",
			contentType: 'application/json',
			data: {"username": username, "password":password},
			success: function(user){
				if(user == null){
					$("#error").show();
				}else{
					window.location.href = "../HTML/home.html";
				}
			}
		})
        }
            
    });
});