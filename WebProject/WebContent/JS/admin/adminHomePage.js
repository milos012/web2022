$(document).ready(function(){
	$("a[id='logout']").bind("click", logout);
	
	$.get({
		url: "/WebProject/rest/users",
		contentType: 'application/json',
	})
});


const logout = () => {
	$.get({
		url: "/WebProject/rest/users/logout",
		contentType: 'application/json',
		success: () => {
			console.log('a');
			window.location.assign("/WebProject/HTML/login.html");
		}
	})
}