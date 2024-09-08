package br.edu.ifpb.pdist_back.controller;

import br.edu.ifpb.pdist_back.dto.PostCreateDTO;
import br.edu.ifpb.pdist_back.dto.PostDTO;
import br.edu.ifpb.pdist_back.model.Post;
import br.edu.ifpb.pdist_back.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping(path = "/")
    public ResponseEntity<?> getAllPosts() {
        try {
            return postService.getAllPosts();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getPost(@PathVariable String id) {
        try {
            return postService.getPost(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}/forum_posts")
    public ResponseEntity<?> getForumPosts(@PathVariable String id) {
        try {
            return postService.getForumPosts(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}/user_posts")
    public ResponseEntity<?> getUserPosts(@PathVariable String id) {
        try {
            return postService.getUserPosts(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deletePost(@PathVariable String id) {
        try {
            return postService.deletePost(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updatePost(@PathVariable String id, PostDTO post) {
        try {
            return postService.updatePost(id, post);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/{id}/new")
    public ResponseEntity<?> addPost(@PathVariable String id, @RequestBody PostCreateDTO post) {
        try {
            return postService.addPost(id, post);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
