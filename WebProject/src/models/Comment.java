package models;

public class Comment {
    private Buyer buyer;
    private Manifestation manifestation;
    private String commentText;
    private int rating;

    public Comment() {
    }

    public Comment(Buyer buyer, Manifestation manifestation, String commentText, int rating) {
        this.buyer = buyer;
        this.manifestation = manifestation;
        this.commentText = commentText;
        this.rating = rating;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Manifestation getManifestation() {
        return manifestation;
    }

    public void setManifestation(Manifestation manifestation) {
        this.manifestation = manifestation;
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
