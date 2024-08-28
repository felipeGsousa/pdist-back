package br.edu.ifpb.pdist_back.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "posts")
public class Post {
    @Id
    private String id;
    private String title;
    private String content;
    private Date date;
    private Long likes;
    private Long dislikes;
    private String user;
    private String userId;
    private String fileId;
    @DBRef
    private List<Comment> comments;

    public Post() {
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Long getDislike() {
        return dislikes;
    }

    public void addDislike() {
        this.dislikes += 1;
    }

    public void subDislike() {
        this.dislikes -= 1;
    }

    public void setDislike(Long dislikes) {
        this.dislikes = dislikes;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
