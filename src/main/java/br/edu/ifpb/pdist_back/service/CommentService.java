package br.edu.ifpb.pdist_back.service;

import br.edu.ifpb.pdist_back.controller.CommentImpl;
import br.edu.ifpb.pdist_back.dto.CommentDTO;
import br.edu.ifpb.pdist_back.interfaces.CommentInterface;
import br.edu.ifpb.pdist_back.model.Comment;
import br.edu.ifpb.pdist_back.model.Post;
import br.edu.ifpb.pdist_back.repository.CommentRepository;
import br.edu.ifpb.pdist_back.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentImpl commentImpl;

    public ResponseEntity<?> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    public ResponseEntity<?> getComment(String id){
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) return new ResponseEntity<>(comment.get(), HttpStatus.OK);
        return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getUserComments(String userId) {
        List<Comment> comments = commentRepository.findByUserId(userId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteComment(String id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            commentImpl.deleteComments(comment.get());
            return new ResponseEntity<>("Comment has been deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> updateComment(String id, Comment commentData) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            comment.get().setData(commentData.getData());
            commentRepository.save(comment.get());
            return new ResponseEntity<>("Comment has been updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> addComment(String postId, CommentDTO commentData) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            Comment comment = new Comment();

            comment.setData(commentData.getData());
            comment.setUserId(commentData.getUserId());
            comment.setDate(new Date());
            comment.setLikes(0L);
            comment.setDislikes(0L);
            comment.setComments(new ArrayList<>());

            Comment savedComment = commentRepository.save(comment);

            post.get().addComment(savedComment);
            postRepository.save(post.get());

            return new ResponseEntity<>("Comment has been created", HttpStatus.OK);
        }
        return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> addCommentTo(String commentId, CommentDTO commentData) {
        Optional<Comment> commentP = commentRepository.findById(commentId);
        if (commentP.isPresent()) {
            Comment comment = new Comment();

            comment.setData(commentData.getData());
            comment.setUserId(commentData.getUserId());
            comment.setComments(new ArrayList<>());
            comment.setDate(new Date());
            comment.setLikes(0L);
            comment.setDislikes(0L);

            Comment savedComment = commentRepository.save(comment);

            commentP.get().addComment(savedComment);
            commentRepository.save(commentP.get());

            return new ResponseEntity<>("Comment has been created", HttpStatus.OK);
        }
        return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
    }

    public void likeComment(HashMap<String, Object> likeAct){
        Optional<Comment> commentOpt = commentRepository.findById((String) likeAct.get("id"));

        if (commentOpt.isPresent()){
            Comment comment = commentOpt.get();

            if ((boolean)likeAct.get("addLike")) {
                comment.addLike();
            }
            if ((boolean)likeAct.get("subLike")) {
                comment.subLike();
            }
            if ((boolean)likeAct.get("addDislike")) {
                comment.addDislike();
            }
            if ((boolean)likeAct.get("subDislike")) {
                comment.subDislike();
            }

            commentRepository.save(comment);
        }
    }
}
