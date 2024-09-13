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
    private String forumId;
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
        this.likes += 1L;
    }

    public void subLike() {
        if (this.likes > 0L) {
            this.likes -= 1L;
        } else {
            this.likes = 0L;
        }
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getDislikes() {
        return dislikes;
    }

    public void addDislike() {
        this.dislikes += 1L;
    }

    public void subDislike() {
        if (this.dislikes > 0L) {
            this.dislikes -= 1L;
        } else {
            this.dislikes = 0L;
        }
    }

    public void setDislikes(Long dislikes) {
        this.dislikes = dislikes;
    }

    public String getForumId() {
        return forumId;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
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
