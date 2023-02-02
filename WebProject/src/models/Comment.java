package models;

public class Comment {
    private Buyer buyer;
    private Post post;
    private String commentText;
    private int rating;

    public Comment() {
    }

    public Comment(Buyer buyer, Post post, String commentText, int rating) {
        this.buyer = buyer;
        this.post = post;
        this.commentText = commentText;
        this.rating = rating;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
