package br.edu.ifpb.pdist_back.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "comments")
public class Comment {
    @Id
    private String id;
    private Long likes;
    private Long dislikes;
    private String userName;
    private String userPhoto;
    private String userId;
    private String data;
    @DBRef
    private List<Comment> comments;
    private Date date;

    public Comment() {
    }

    public String getId() {
        return id;
    }

    public Long getLikes() {
        return likes;
    }

    public void addLike() {
        this.likes += 1;
    }

    public void subLike() {
        this.likes -= 1;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getDislikes() {
        return dislikes;
    }

    public void addDislike() {
        this.dislikes += 1;
    }

    public void subDislike() {
        this.dislikes -= 1;
    }

    public void setDislikes(Long dislikes) {
        this.dislikes = dislikes;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
