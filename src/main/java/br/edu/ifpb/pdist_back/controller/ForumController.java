package br.edu.ifpb.pdist_back.controller;

import br.edu.ifpb.pdist_back.dto.ForumDTO;
import br.edu.ifpb.pdist_back.model.Forum;
import br.edu.ifpb.pdist_back.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/forums")
public class ForumController {

    @Autowired
    private ForumService forumService;

    @GetMapping(path = "/")
    public ResponseEntity<?> getAllForum () {
        try {
            return forumService.getAllForums();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getForum (@PathVariable String id) {
        try {
            return forumService.getForum(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}/user_forums")
    public ResponseEntity<?> getUserForums (@PathVariable String id) {
        try {
            return forumService.getUserForums(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteForum (@PathVariable String id) {
        try {
            return forumService.deleteForum(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateForum(@PathVariable String id, @RequestBody ForumDTO forum) {
        try {
            return forumService.updateForum(id, forum);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/new")
    public ResponseEntity<?> addForum(@RequestBody ForumDTO forum) {
        try {
            return forumService.addForum(forum);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/join/{id}")
    public ResponseEntity<?> joinForum(@PathVariable String id, @RequestBody String userId) {
        try {
            return forumService.joinForum(id, userId);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/leave/{id}")
    public ResponseEntity<?> leaveForum(@PathVariable String id, @RequestBody String userId) {
        try {
            return forumService.leaveForum(id, userId);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
