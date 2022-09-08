$.noConflict()
function getDateTime(datetime){	
	var month={"01": "Januar",
            "02": "Februar",
            "03": "Mart",
            "04": "April",
            "05": "Maj",
            "06": "Jun",
            "07": "Jul",
            "08": "Avgust",
            "09": "Septembar",
            "10": "Oktobar",
            "11": "Novembar",
            "12": "Decembar"}

	
    var date = datetime.split("T")[0];
    var time = datetime.split("T")[1];

    return date.split("-")[2] + ". "+month[date.split("-")[1]] + " " + date.split("-")[0] + ". u " + time.split(":")[0] +":"+time.split(":")[1];
}


function postaviPolja(user){
	$("input[name='username']").val(user.username);
	$("input[name='password']").val(user.password);
	$("input[name='firstName']").val(user.firstName);
	$("input[name='lastName']").val(user.lastName);
	$("input[name='dateOfBirth']").val(user.dateOfBirth);
	$("input[name='gender']").val(user.gender);
	$("input[name='role']").val(user.role);
	
	if(user.role == "BUYER"){
		$("#profil-dugme1").text("Manifestations");
		$("#profil-dugme2").text("Tickets");
		$("input[name='userTypeName']").val(user.userTypeName);
		
	} else if(user.role == "ADMIN"){
		$("#profil-dugme1").text("Register new seller");
		$("#profil-dugme2").text("New manifestations");
		$("#profil-dugme3").text("Users");
		$("#profil-dugme4").text("Tickets");
	}else{
		$("#profil-dugme1").text("Add manifestation");
		$("#profil-dugme2").text("My manifestations");
		$("#profil-dugme3").text("Sold tickets");
	}
}

function dodajRedKorisnika(korisnik){
	if(!korisnik.deleted){
		let tr;
	    if(korisnik.role == "SELLER")
	        tr = $("<tr class='td-korisnik prodavac' id='"+korisnik.username+"'></tr>");
	    else
	        tr = $("<tr class='td-korisnik kupac' id='"+korisnik.username+"'></tr>");
	    let slika = $("<td><img class='slika-user' src='../images/"+korisnik.gender+".png' height='50px'></td>")
	    let username = $("<td><p class='username'>"+korisnik.username+"</p></td>")
	    let imePrezime =  $("<td><p class='imepre'>"+korisnik.firstName + " " + korisnik.lastName+"</p></td>")
	    let rodjen = $("<td><p class='rodjen'>Rodjen:"+ korisnik.dateOfBirth+"</p></td>")
	    let uloga =  $("<td><p class='uloga'>"+ korisnik.role+"</p></td>")
	    if(korisnik.role =='BUYER' && korisnik.points)
	    	uloga = $("<td><p class='uloga'>"+ korisnik.role+"</p><img src='../images/coin.png' height='30px'>"+ korisnik.points +"</td>")
	    let brisi = $("<td></td>");
	    if(korisnik.role != "ADMIN")
	   		brisi =  $("<td><button class='brisi' onclick='brisi(\""+korisnik.username+"\")'>Obrisi</button></td>")
	
	    
	    tr.append(slika);
	    tr.append(username);
	    tr.append(imePrezime)
	    tr.append(rodjen);
	    tr.append(uloga);
	    tr.append(brisi);
	    $("#tabela-karata").append(tr);
	}
}

function dodajRedKarte(nazivi, karta){
	let tr;

    if(karta.tipKarte == "REGULAR"){
    	 tr = $("<tr class='reg-tr' id='"+karta.id+"'></tr>");
    } else if(karta.tipKarte == "FAN_PIT"){
    	tr = $("<tr class='fan-tr' id='"+karta.id+"'></tr>");
    }else if(karta.tipKarte == "VIP"){
    	tr = $("<tr class='vip-tr' id='"+karta.id+"'></tr>");
    }
    let slika = $("<td><img class='slika-karta' src='../images/default.png' height='50px'></td>")
    let idKarte = $("<td><p class='idKarte'>"+karta.id+"</p></td>")
    let kupac =  $("<td><p class='kupac'>Kupuje: "+karta.buyer+"</p></td>") 
    tr.append(slika);
    tr.append(idKarte);
    tr.append(prodavac);
    tr.append(kupac);
    let id = (karta.manifestation).toString();
    let datumVreme = $("<td><p class='datum-vreme'>"+ getManifestationDateTime(karta.manifestationDateTime)+"</p></td>")
    let cena =  $("<td><p class='cenaKarte'>"+ karta.price + " din</p></td>")
	let tipKarte = $("<td><p class='tip'>"+ karta.ticketType + "</p></td>");
	let status = $("<td><p class='tip'>"+ karta.ticketStatus + "</p></td>");
	
    tr.append(datumVreme);
    tr.append(cena);
    tr.append(tipKarte);
    tr.append(status);
    	

    $("#tabela-karata").append(tr);
   
}

function brisi(username){
    $.get({
        url: "/WebProject/rest/users/delete?username=" + username,
        contentType: "application/json",
        success: function(){
			$("#"+ username).remove();
        }
   })
}

function obrisiDivPretrage()
{
	$("#PretragaTabele").empty();
}