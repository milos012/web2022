$(document).ready(function(){
    $("#registration-form").submit(function(e){
        e.preventDefault();
        var username = $("input[name='username']").val();
        var password = $("input[name='password']").val();
        var firstName = $("input[name='firstName']").val();
        var lastName = $("input[name='lastName']").val();
        var gender = $("select[name='gender'] option:selected").val();
        var dateOfBirth = $("input[name='dateOfBirth']").val();
       
        var err = false;

        if(!username){
            err = true;
            $("input[name='username']").css("border-bottom", "2px solid red");
        }

        if(!password){
            err = true;
            $("input[name='password']").css("border-bottom", "2px solid red");
        }

        if(!firstName){
            err = true;
            $("input[name='firstName']").css("border-bottom", "2px solid red");
        }

        if(!lastName){
            err = true;
            $("input[name='lastName']").css("border-bottom", "2px solid red");
        }

        if(!dateOfBirth){
            err = true;
            $("input[name='dateOfBirth']").css("border-bottom", "2px solid red");
        }
            
        if(!err){
            $.post({
			url: "/WebProject/rest/users/register",
			contentType: 'application/json',
			data: JSON.stringify({"username": username, "password":password, "firstName": firstName, "lastName":lastName, "gender":gender, "dateOfBirth":dateOfBirth, "role":"BUYER", "deleted": false}),
			success: function(user){
				if(user == null){
					$("#error").show();
					$("input[name='username']").css("border-bottom", "2px solid red");
				}else{
					window.location.href = "../HTML/home.html";
				}
			}
		})
        }
    });
});