$(document).ready(function(){
    $("#login-form").submit(function(e){
        e.preventDefault();
        var username = $("input[name='username']").val();
        var password = $("input[name='password']").val();
		//console.log(username, password);
        if(!username){
            $("input[name='username']").css("border-bottom", "2px solid red")
        } 
        
        if(!password){
            $("input[name='password']").css("border-bottom", "2px solid red")
        }
        if(username && password){
            $.post({
			url: "/WebProject/rest/users/login",
			contentType: 'application/json',
			data: JSON.stringify({"username": username, "password":password}),
			success: function(user, status){
				if(status != 200){
					$("#error").show();
				}else{
//					console.log(user);
					sessionStorage.setItem("user-role", user.role);
					if (user.role == 'ADMIN') {
						window.location.replace('/WebProject/HTML/admin/adminHomePage.html');
					} else if (user.role == 'BASICUSER') {
						window.location.replace('/WebProject/HTML/home.html');
					}
				}
			}
			
		});
        }
            
    });
});