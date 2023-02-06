var fileContent;

$(document).ready(function() {
	$.get({
		url : "/WebProject/rest/users/user",
		contentType: "application/json",
		success: function(user){
			if(!user){
				window.location.href = "../HTML/home.html";
			}
		}
	})
	
	$("#addpost-form").submit(function(e){
		e.preventDefault();
		//let tekst = $("input[name='tekst']").val();
		let poster = $("input[name='slika']").val();
		var poljeteksta = document.getElementById('tekst');
		let tekst = poljeteksta.value;
		
		
		//$("input[name='tekst']").css("border-bottom", "2px solid white");
		// $("input[name='slika']").css("border-bottom", "2px solid white");
		$("#error").css("color", "white");
		
		let greska = false;
		console.log(tekst);
		console.log(poster);
		if(!tekst){
			$("input[name='tekst']").css("border-bottom", "2px solid red");
			greska = true;
      		console.log("greska");
		}
		
		
		if(!greska){
      		console.log("usao u fju glavnu");
			
			$.post({
				url: "/WebProject/rest/posts/create",
				data: JSON.stringify({"slika": fileContent, "tekst": tekst,"komentari": null, "deleted":false}),
				contentType: "application/json",
				success: function(bul){
					console.log("poslato na bek");
					window.location.href ="../HTML/home.html";
				}
			})
      		console.log("poslato na bek ali nesto");
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

function dodajPoljaPosta(post){
	$("input[name='tekst']").val(post.naziv);
	
}