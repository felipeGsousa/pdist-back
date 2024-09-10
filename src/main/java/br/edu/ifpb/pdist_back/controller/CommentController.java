package br.edu.ifpb.pdist_back.controller;

import br.edu.ifpb.pdist_back.dto.CommentDTO;
import br.edu.ifpb.pdist_back.model.Comment;
import br.edu.ifpb.pdist_back.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping(path = "/")
    public ResponseEntity<?> getAllComments() {
        try {
            return commentService.getAllComments();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getComment(@PathVariable String id) {
        try {
            return commentService.getComment(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}/user_comments")
    public ResponseEntity<?> getUserComments(@PathVariable String id) {
        try {
            return commentService.getUserComments(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable String id) {
        try {
            return commentService.deleteComment(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateComment(@PathVariable String id, @RequestBody Comment comment) {
        try {
            return commentService.updateComment(id, comment);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    //Classes DTO para criação e edição dos dados. DTO de post recebe um
    // multipartFIle que será utilizado para chamar o serviço de arquivos através de mensageria
    @PostMapping(path = "/{id}/new")
    public ResponseEntity<?> addComment(@PathVariable String id, CommentDTO comment) {
        try {
            return commentService.addComment(id, comment);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/{id}/newTo")
    public ResponseEntity<?> addCommentTo(@PathVariable String id, CommentDTO comment) {
        try {
            return commentService.addCommentTo(id, comment);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
