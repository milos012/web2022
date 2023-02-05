package services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import models.Post;
import models.User;

public class PostService {
	private ArrayList<Post> posts;
	private String path;
	private ArrayList<Post> allPosts; //Both active and deleted
	private Comparator<Post> sorterDate;
	
	
	public PostService() {
		super();
	}

	public PostService(ArrayList<Post> posts, String path, ArrayList<Post> allPosts, Comparator<Post> sorterDate) {
		super();
		this.posts = posts;
		this.path = path;
		this.allPosts = allPosts;
		this.sorterDate = sorterDate;
	}
	
	public PostService(String path) {
		posts = new ArrayList<Post>();
		allPosts = new ArrayList<Post>();
		//initSorters();
		loadPosts(path);
	}
	
	
	private void loadPosts(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        //String data = filePath + File.separator + "data" + File.separator;
        String data = filePath + File.separator;

        path = data;
        try {
            List<Post> postList = Arrays.asList(mapper.readValue(Paths.get(data + "posts.json").toFile(), Post[].class));
            System.out.println("Ucitavanje karata uspesno===");

            for (Post t : postList) {
                posts.add(t);
            }

        } catch (IOException e) {
            System.out.println("Error while loading posts!");
        }
    }
	
	private Post getPost(String text) {
		for (Post u : posts) {
			if (u.getTekst() == text){
				return u;
			}
		}
		return null;
	}
	
	public void deletePost(String text) {
		for (Post u : posts) {
			if (u.getTekst() == text){
				u.setDeleted(true);
				ObjectMapper mapper = new ObjectMapper();
				try {
					mapper.writeValue(Paths.get(path + "posts.json").toFile(), getAllPosts());
					posts.remove(u);	// sklanjamo ga iz aktivnih postova
				} catch (IOException e) {
					System.out.println("Error! Writing posts to file was unsuccessful.");
				}
			}
		}
	}
	
	public Post addPost(Post p) {
		if (getPost(p.getTekst()) != null) {
			System.out.println("Post with that text already exists");
			return null;
		}
		
		Post novi = new Post(p);
		posts.add(novi);
		allPosts.add(novi);
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			mapper.writeValue(Paths.get(path + "posts.json").toFile(), getAllPosts());
			System.out.println("Post je uspesno kreiran");
			System.out.println("na putanji: + " + path);
		} catch (IOException e) {
			System.out.println("Error while writing post!");
			return null;
		}
		return novi;
	}

	public Collection<Post> getAllPosts() {
		return allPosts;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ArrayList<Post> getPosts() {
		return posts;
	}

	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}

	public void setAllPosts(ArrayList<Post> allPosts) {
		this.allPosts = allPosts;
	}
	
	

}
