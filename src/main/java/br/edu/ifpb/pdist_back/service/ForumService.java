package br.edu.ifpb.pdist_back.service;

import br.edu.ifpb.pdist_back.controller.CommentImpl;
import br.edu.ifpb.pdist_back.dto.ForumDTO;
import br.edu.ifpb.pdist_back.model.Comment;
import br.edu.ifpb.pdist_back.model.Forum;
import br.edu.ifpb.pdist_back.model.Post;
import br.edu.ifpb.pdist_back.repository.ForumRepository;
import br.edu.ifpb.pdist_back.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ForumService {

    @Autowired
    private ForumRepository forumRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentImpl commentImpl;

    public ResponseEntity<?> getAllForums() {
        List<Forum> forums = forumRepository.findAll();
        return new ResponseEntity<>(forums, HttpStatus.OK);
    }

    public ResponseEntity<?> getForum(String id){
        Optional<Forum> forum = forumRepository.findById(id);
        if (forum.isPresent()) return new ResponseEntity<>(forum.get(), HttpStatus.OK);
        return new ResponseEntity<>("Forum not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getUserForums(String id) {
        List<Forum> forums = forumRepository.findByUserId(id);
        return new ResponseEntity<>(forums, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteForum(String id){
        Optional<Forum> forum = forumRepository.findById(id);
        if (forum.isPresent()) {
            for (Post post: forum.get().getPosts()) {
                for (Comment comment: post.getComments()){
                    commentImpl.deleteComments(comment);
                }
                postRepository.delete(post);
            }
            forumRepository.delete(forum.get());
            return new ResponseEntity<>("Forum has been deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Forum not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> updateForum(String id, Forum forumData) {
        Optional<Forum> forum = forumRepository.findById(id);
        if (forum.isPresent()) {
            forum.get().setName(forumData.getName());
            forum.get().setTopic(forumData.getTopic());
            forum.get().setBanner(forumData.getBanner());
            forum.get().setDescription(forumData.getDescription());
            forumRepository.save(forum.get());
            return new ResponseEntity<>("Forum has been updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Forum not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> addForum(ForumDTO forumData) {
        Forum forum = new Forum();
        byte[] banner = Base64.getDecoder().decode(forumData.getBanner());
        forum.setName(forumData.getName());
        forum.setTopic(forumData.getTopic());
        forum.setBanner(banner);
        forum.setDescription(forumData.getDescription());
        forum.setCreated(new Date());
        forum.setUserId(forumData.getUserId());
        forum.setUsers(new ArrayList<>());
        forum.setPosts(new ArrayList<>());

        forum.addUser(forum.getUserId());
        System.out.println(banner.toString());
        Forum createdForum = forumRepository.save(forum);

        return new ResponseEntity<>(createdForum, HttpStatus.CREATED);
    }
}
