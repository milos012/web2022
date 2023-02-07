var fileContent;
var email1;
var username1;


const logout = () => {
	$.get({
		url: "/WebProject/rest/users/logout",
		contentType: 'application/json',
		success: () => {
			window.location.assign("/WebProject/HTML/login.html");
		}
	})
}

$(document).ready(function() {

	$("a[id='logout']").bind("click", logout);

	$(".tabs").tabs();

	$.get({
		url : "/WebProject/rest/users/user",
		contentType: "application/json",
		success: function(user){

			email1 = user.email;
			username1 = user.username;
			
			
            
		}
	})

	$.get({
		url: "/WebProject/rest/users/all",
		contentType: 'application/json',
		success: (data) => {
			if (data != null) {
				createUserTable(data);
			}
		}
			
	});


	$.get({
		url: "/WebProject/rest/users/friends",
		contentType: 'application/json',
		success: (data) => {
			if (data != null) {
				createFriendsTable(data);
			}
		}
			
	});

	$.get({
		url: "/WebProject/rest/friendrequests/getAll",
		contentType: 'application/json',
		success: (data) => {
			if (data != null) {
				
				createFRTable(data);
			}
		}
			
	});
		
	
	
})

const createUserTable = (users) => {
	console.log(users);
	var c, r, t;
	t = document.createElement('table');
	r = t.insertRow(0); 
	c = r.insertCell(0);
	c.innerHTML = "First Name";
	c = r.insertCell(1);
	c.innerHTML = "Last Name";
	c = r.insertCell(2);
	c.innerHTML = "Username";
	c = r.insertCell(3);
	c.innerHTML = "Email";
	c = r.insertCell(4);
	c.innerHTML = "Birthday";
	c = r.insertCell(5);
	c.innerHTML = "View";
	c = r.insertCell(6);
	c.innerHTML = "FR";
	users.forEach((user, index) => {
		r = t.insertRow(index + 1);
		c = r.insertCell(0);
		c.innerHTML = user.firstName;
		c = r.insertCell(1);
		c.innerHTML = user.lastName;
		c = r.insertCell(2);
		c.innerHTML = user.username;
		c = r.insertCell(3);
		c.innerHTML = user.email;
		c = r.insertCell(4);
		c.innerHTML = user.dateOfBirth;
		c = r.insertCell(5);
		c.innerHTML = "<button name=\"otvoriProfil\" data-user=" + user.username + " data-rownum=" + (index+1) + " class=\"btn waves-effect waves-light\"> view </button>";
		c = r.insertCell(6);
		c.innerHTML = "<button name=\"posaljiReq\" data-user=" + user.username + " data-rownum=" + (index+1) + " class=\"btn waves-effect waves-light\"> send </button>";	
			
	});
	t.setAttribute("id", "table");
	document.getElementById("tab2").appendChild(t);
		
}


const createFriendsTable = (users) => {
	console.log("prijatelji");
	console.log(users);
	var c, r, t;
	t = document.createElement('table');
	r = t.insertRow(0); 
	c = r.insertCell(0);
	c.innerHTML = "First Name";
	c = r.insertCell(1);
	c.innerHTML = "Last Name";
	c = r.insertCell(2);
	c.innerHTML = "Username";
	c = r.insertCell(3);
	c.innerHTML = "Email";
	c = r.insertCell(4);
	c.innerHTML = "Birthday";
	c = r.insertCell(5);
	c.innerHTML = "View";
	c = r.insertCell(6);
	c.innerHTML = "Delete";
	users.forEach((user, index) => {
		r = t.insertRow(index + 1);
		c = r.insertCell(0);
		c.innerHTML = user.firstName;
		c = r.insertCell(1);
		c.innerHTML = user.lastName;
		c = r.insertCell(2);
		c.innerHTML = user.username;
		c = r.insertCell(3);
		c.innerHTML = user.email;
		c = r.insertCell(4);
		c.innerHTML = user.dateOfBirth;
		c = r.insertCell(5);
		c.innerHTML = "<button name=\"otvoriProfil\" data-user=" + user.username + " data-rownum=" + (index+1) + " class=\"btn waves-effect waves-light\"> view </button>";
		c = r.insertCell(6);
		c.innerHTML = "<button name=\"obrisiPrijatelja\" data-user=" + user.username + " data-rownum=" + (index+1) + " class=\"btn waves-effect waves-light\"> delete </button>";	
			
	});
	t.setAttribute("id", "table");
	document.getElementById("tab3").appendChild(t);
		
}


const createFRTable = (friendrequests) => {
	console.log("zahtevi");
	console.log(friendrequests);
	var c, r, t;
	t = document.createElement('table');
	r = t.insertRow(0); 
	c = r.insertCell(0);
	c.innerHTML = "Primalac";
	c = r.insertCell(1);
	c.innerHTML = "Posiljalac";
	c = r.insertCell(2);
	c.innerHTML = "DateSent";
	c = r.insertCell(3);
	c.innerHTML = "Accept";
	c = r.insertCell(4);
	c.innerHTML = "Reject";
	friendrequests.forEach((friendrequest, index) => {
		r = t.insertRow(index + 1);
		c = r.insertCell(0);
		c.innerHTML = friendrequest.primalac.username;
		c = r.insertCell(1);
		c.innerHTML = friendrequest.posiljalac.username;
		c = r.insertCell(2);
		c.innerHTML = friendrequest.dateSent;
		c = r.insertCell(3);
		c.innerHTML = "<button name=\"prihvatiFR\" freqA='" + JSON.stringify(friendrequest) + "' data-rownum=" + (index+1) + " class=\"btn waves-effect waves-light\"> accept </button>";
		c = r.insertCell(4);
		c.innerHTML = "<button name=\"odbijFR\" data-user=" + friendrequest.primalac.username + " data-rownum=" + (index+1) + " class=\"btn waves-effect waves-light\"> reject </button>";	
			
	});
	t.setAttribute("id", "table");
	document.getElementById("tab4").appendChild(t);
	setAcceptBtnFunction();
		
}

const setAcceptBtnFunction = () => {
	var deleteBtns = document.getElementsByName("prihvatiFR");
	
	deleteBtns.forEach(btn => {
		btn.addEventListener('click', (ev) => {

			
			$.post({
				url: "/WebProject/rest/friendrequests/accept",
				data: JSON.stringify(ev.target.getAttribute('freqA')),
				contentType: "application/json",
				success: function(bul){
					console.log("poslato na bek");
					window.location.href = window.location.href;
				}
			})

		});
	});
}