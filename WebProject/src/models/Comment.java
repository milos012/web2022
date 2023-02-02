package models;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import services.LocalDateDeserializer;
import services.LocalDateSerializer;

public class Comment {
    private User userCommenting;
    @JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate commentDate;
    @JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate editDate;

    public Comment() {
    }
    
	public Comment(User userCommenting, LocalDate commentDate, LocalDate editDate) {
		super();
		this.userCommenting = userCommenting;
		this.commentDate = commentDate;
		this.editDate = editDate;
	}


	public User getUserCommenting() {
		return userCommenting;
	}

	public void setUserCommenting(User userCommenting) {
		this.userCommenting = userCommenting;
	}

	public LocalDate getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(LocalDate commentDate) {
		this.commentDate = commentDate;
	}

	public LocalDate getEditDate() {
		return editDate;
	}

	public void setEditDate(LocalDate editDate) {
		this.editDate = editDate;
	}

	@Override
	public String toString() {
		return "Comment [userCommenting=" + userCommenting + ", commentDate=" + commentDate + ", editDate=" + editDate
				+ "]";
	}


   
}
