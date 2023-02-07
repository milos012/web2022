$(document).ready(function(){
	$("a[id='logout']").bind("click", logout);

	$(".tabs").tabs();
	
	$("a[id='searchBtn']").bind('click', () => {
		var searchValue = $("input[id='search']").val();
//		console.log(searchValue);
		$.get({
			url: "/WebProject/rest/users/" + searchValue + "/posts",
			contentType: 'application/json',
			success: (data, status) => {
				if (status == 'success') {
//					console.log(data);
					createPostsForUser(data);
				}
			}
		})
//		$("input[id='search']").val('');
		
	});
	
	$.get({
		url: "/WebProject/rest/users/adminAll",
		contentType: 'application/json',
		success: (data, status) => {
			if (status == 'success') {
				createUserTable(data);
			}
		}
		
	});
	

});


const createPostsForUser = (posts) => {
	var postsDiv = document.getElementById('posts');
	console.log(posts);
	postsDiv.innerHTML = '';
	posts.forEach((post, index) => {
		
		const card = "<div class='col s4'> <div class='card'> <div class='card-image'> <img src='" + post.slika + "'> <span class='card-title'>Card Title</span><a class='btn-floating halfway-fab waves-effect waves-light red' name='delete-post'>delete</a> </div> <div class='card-content'> <p> " + post.tekst + "</p> </div> </div> </div>"
		if (!post.deleted) {
			postsDiv.innerHTML += card;
		}
		
	});
	var deletes = document.getElementsByName('delete-post');
	deletes.forEach((el, index) => {
		el.addEventListener('click', () => {
			var username = $("input[id='search']").val();
			$.ajax({
				url: '/WebProject/rest/users/' + username + '/post/' + posts[index].tekst,
				type: 'DELETE',
				success: (data, status) => {
					if(status == 'success') {
						createPostsForUser(data);
					}
				}
			});
		})
	});
}

const createUserTable = (users) => {
//	console.log(users);
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
	c.innerHTML = "Role";
	c = r.insertCell(4);
	c.innerHTML = "Blocked";
	c = r.insertCell(5);
	c.innerHTML = "";
	users.forEach((user, index) => {
		r = t.insertRow(index + 1);
		c = r.insertCell(0);
        c.innerHTML = user.firstName;
        c = r.insertCell(1);
        c.innerHTML = user.lastName;
        c = r.insertCell(2);
        c.innerHTML = user.username;
        c = r.insertCell(3);
        c.innerHTML = user.role;
        c = r.insertCell(4);
        c.innerHTML = user.deleted;
		c = r.insertCell(5);
		c.innerHTML = "<button name=\"delete\" data-user=" + user.username + " data-rownum=" + (index+1) + " class=\"btn waves-effect waves-light\"> block/unblock </button>";
		
	});
	t.setAttribute("id", "table");
	document.getElementById("tab1").appendChild(t);
	setBlockUserBtnFunction();
	
}

const setBlockUserBtnFunction = () => {
	var deleteBtns = document.getElementsByName("delete");
	
	deleteBtns.forEach(btn => {
		btn.addEventListener('click', (ev) => {
			$.ajax({
				url: '/WebProject/rest/users/block/' + ev.target.getAttribute('data-user'),
				type: 'PUT',
				success: (data, status) => {
					console.log(data);
					if (data) {
						window.location.href = window.location.href;
					}
				}
			});
		});
	});
}

const logout = () => {
	$.get({
		url: "/WebProject/rest/users/logout",
		contentType: 'application/json',
		success: () => {
			window.location.assign("/WebProject/HTML/login.html");
		}
	})
}