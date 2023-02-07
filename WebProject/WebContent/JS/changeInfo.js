var fileContent;
var username1;
var dateOfBirth1;

$(document).ready(function() {
	$.get({
		url : "/WebProject/rest/users/user",
		contentType: "application/json",
		success: function(user){
			if(!user){
				window.location.href = "../HTML/home.html";
			}

			var firstname = user.firstName;
			var lastname = user.firstName;
			var email = user.email;
			var dateOfBirth1 = user.dateOfBirth;
			var username1 = user.username;
			var gender = user.gender;
			var accStatus = user.accStatus;
			var profPic = user.profilePicture;
						           
		}
	})
	

    $("#changeUserInfo-form").submit(function(e){
		e.preventDefault();
		let newFName = $("input[name='newFirstName']").val();
        let newLName = $("input[name='newLastName']").val();
        let newPW = $("input[name='newPassword']").val();
        let newAccStatus = $("select[name='accStatus'] option:selected").val();
        let newGender = $("select[name='gender'] option:selected").val();


		// let poster = $("input[name='slika']").val();
		
		
		$("input[name='newFirstName']").css("border-bottom", "2px solid white");
        $("input[name='newLastName']").css("border-bottom", "2px solid white");
        $("input[name='newPassword']").css("border-bottom", "2px solid white");
		// $("input[name='slika']").css("border-bottom", "2px solid white");
		$("#error").css("color", "white");
		
		let greska = false;

		if(!newFName){
			$("input[name='newFirstName']").css("border-bottom", "2px solid red");
			greska = true;
      		console.log("greska");
		}

        if(!newLName){
			$("input[name='newLastName']").css("border-bottom", "2px solid red");
			greska = true;
      		console.log("greska");
		}

        if(!newPW){
			$("input[name='newPassword']").css("border-bottom", "2px solid red");
			greska = true;
      		console.log("greska");
		}
		
		
		if(!greska){
			
			$.post({
				url: "/WebProject/rest/users/edit",
				data: JSON.stringify({"profilePicture": fileContent, "firstName": newFName, "lastName": newLName, "password": newPW, "accStatus": newAccStatus, "gender": newGender}),
				contentType: "application/json",
				success: function(bul){
					console.log("poslato na bek");
					window.location.href ="../HTML/mypage.html";
				}
			})
      		
      		
		}
	})

})


function readFile(input) {
    let file = input.files[0];
  
    let reader = new FileReader();
  
    reader.readAsDataURL(file);
  
    reader.onload = function() {
      console.log(reader.result);
      fileContent = reader.result;
    };
  
  }