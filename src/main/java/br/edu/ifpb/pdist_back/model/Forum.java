package br.edu.ifpb.pdist_back.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "forums")
public class Forum {
    @Id
    private String id;
    private String name;
    private Date created;
    private String topic;
    private String description;
    private byte[] banner;
    private String ownerName;
    private String userId;
    @DBRef
    private List<Post> posts;
    private List<String> users;

    public Forum() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getBanner() {
        return banner;
    }

    public void setBanner(byte[] banner) {
        this.banner = banner;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String ownerId) {
        this.userId = ownerId;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public void addUser(String user){
        this.users.add(user);
    }
    public void removeUser(String user){
        this.users.remove(user);
    }
}
