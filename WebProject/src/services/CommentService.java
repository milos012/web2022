package services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import models.Comment;

public class CommentService {
	private ArrayList<Comment> comments;
	private String path;
	
	
	public CommentService() {
		comments = new ArrayList<Comment>();
	}
	
	public CommentService(String folderPath) {
		comments = new ArrayList<Comment>();
		this.path = folderPath + File.separator + "comments.json";
		loadComments();
	}
	
	private void loadComments() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<Comment> komentari = Arrays.asList(mapper.readValue(Paths.get(path).toFile(), Comment[].class));

			for (Comment k : komentari) {
				comments.add(k);
			}

		} catch (IOException e) {
			System.out.println("Error while loading comments!");
		}
	}
	
	private void writeComments() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(Paths.get(path).toFile(), comments);
		} catch (IOException e) {
			System.out.println("Error while writing comments!");
		}
	}
	
	public void addComment(Comment k) {		
		comments.add(k);		
		writeComments();
	}
	
}
