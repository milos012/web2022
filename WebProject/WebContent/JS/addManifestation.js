var fileContent;

function readFile(input) {
  let file = input.files[0];

  let reader = new FileReader();

  reader.readAsDataURL(file);

  reader.onload = function() {
    console.log(reader.result);
    fileContent = reader.result;
  };
}

function showManifestationTypes(){
	var select = $("select[name='manifestationType']");
	const types = [
  		"CONCERT",
  		"FESTIVAL",
  		"THEATRE",
		"OTHER"
	];
	for(let type of types){
		let option = $("<option class='type-option' value='type'>"+type+"</option>")
		select.append(option);
	}
}

$(document).ready(function() {
	$.get({
		url : "/WebProject/rest/users/user",
		contentType: "application/json",
		success: function(user){
			if(!user || user.role != "SELLER"){
				window.location.href = "../HTML/home.html";
			}else{
				showManifestationTypes();
			}
		}
	})
	
	$("#manifestation-form").submit(function(e){
		e.preventDefault();
		
		let name = $("input[name='name']").val();
		let manifestationType= $("select[name='manifestationType'] option:selected").val();
		let capacity = $("input[name='capacity']").val();
		let manifestationDateTime = $("input[name='manifestationDateTime']").val();
		let priceOfRegularTicket = $("input[name='priceOfRegularTicket']").val();
		let address = $("input[name='address']").val();
		
		let err = false;
		
		if(!name){
			$("input[name='name']").css("border-bottom", "2px solid red");
			err = true;
		}
		
		if(!capacity || parseInt(capacity) < 0){
			$("input[name='capacity']").css("border-bottom", "2px solid red");
			err = true;
		}
		
		if(!manifestationDateTime){
			$("input[name='manifestationDateTime']").css("border-bottom", "2px solid red");
			err = true;
		}
		
		if(!priceOfRegularTicket || parseInt(priceOfRegularTicket) < 0){
			$("input[name='priceOfRegularTicket']").css("border-bottom", "2px solid red");
			err = true;
		}
		
		if(!address){
			$("input[name='address']").css("border-bottom", "2px solid red");
			err = true;
			
		}
		
		if(!err){
			location = {"address": address};
			
			$.post({
				url: "/WebProject/rest/manifestations/addManifestation",
				data: JSON.stringify({"name": name, "manifestationType": manifestationType, "capacity": capacity, "manifestationDateTime": manifestationDateTime, "priceOfRegularTicket":priceOfRegularTicket, "isActive":false, "location":location, "posterPath": fileContent}),
				contentType: "application/json",
				success: function(b){
					if(b == "false")
						$("input[name='address']").css("border-bottom", "2px solid red");
					else{
						window.location.href ="../HTML/home.html";
					}
				}
			})
		}
	})
})