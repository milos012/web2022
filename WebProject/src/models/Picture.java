package models;

import java.util.List;

public class Picture {
	private String picURL;
	private List<Comment> comments;
		
	public Picture() {
		super();
	}

	public Picture(String picURL, List<Comment> comments) {
		super();
		this.picURL = picURL;
		this.comments = comments;
	}

	public String getPicURL() {
		return picURL;
	}

	public void setPicURL(String picURL) {
		this.picURL = picURL;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Picture [picURL=" + picURL + ", comments=" + comments + "]";
	}

}
