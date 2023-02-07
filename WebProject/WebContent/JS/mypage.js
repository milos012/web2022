var fileContent;

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
			var dateOfBirth = user.dateOfBirth;
			var gender = user.gender;
			var accStatus = user.accStatus;
			var profPic = user.profilePicture;
			
			document.getElementById("punoime").innerHTML = firstname + " " + lastname;
			document.getElementById("email").innerHTML = email;
			document.getElementById("ime").innerHTML = firstname;
			document.getElementById("prezime").innerHTML = lastname;
			document.getElementById("datumr").innerHTML = dateOfBirth;
			document.getElementById("gender").innerHTML = gender;
			document.getElementById("accStatus").innerHTML = accStatus;

			$.each(user.posts, function(i, f) {
				var tblRow = "<tr>" + "<td>" + f.tekst + "</td>" +
				 "<td>" + "<img style='display:block; width:450px;height:250px;' src=" + f.slika + ">" +"</td>" + "</tr>"
				 $(tblRow).appendTo("#userdata tbody");
		   });
			
            
		}
	})
	


})