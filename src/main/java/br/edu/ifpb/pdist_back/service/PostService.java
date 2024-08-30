package br.edu.ifpb.pdist_back.service;

import br.edu.ifpb.pdist_back.controller.CommentImpl;
import br.edu.ifpb.pdist_back.dto.PostDTO;
import br.edu.ifpb.pdist_back.model.Comment;
import br.edu.ifpb.pdist_back.model.Forum;
import br.edu.ifpb.pdist_back.model.Post;
import br.edu.ifpb.pdist_back.repository.CommentRepository;
import br.edu.ifpb.pdist_back.repository.ForumRepository;
import br.edu.ifpb.pdist_back.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ForumRepository forumRepository;
    @Autowired
    private CommentImpl commentImpl;

    public ResponseEntity<?> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostDTO> postDTOS = postsToDTO(posts);
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);
    }
    public ResponseEntity<?> getPost(String id){
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) return new ResponseEntity<>(post.get(), HttpStatus.OK);
        return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getUserPosts(String userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        List<PostDTO> postDTOS = postsToDTO(posts);
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);
    }
    public ResponseEntity<?> deletePost(String id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            for (Comment comment : post.get().getComments()){
                commentImpl.deleteComments(comment);
            }
            postRepository.delete(post.get());
            return new ResponseEntity<>("Post has been deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> updatePost(String id, Post postData) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            post.get().setTitle(postData.getTitle());
            post.get().setContent(postData.getContent());
            post.get().setFileId(postData.getFileId());
            postRepository.save(post.get());
            return new ResponseEntity<>("Post has been updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> addPost(String forumId, PostDTO postData) {
        Optional<Forum> forum = forumRepository.findById(forumId);
        if (forum.isPresent()) {
            Post post = new Post();

            post.setContent(postData.getContent());
            post.setTitle(postData.getTitle());
            post.setDate(new Date());
            post.setComments(new ArrayList<>());
            post.setUserId(postData.getUserId());
            if (postData.getFileId() != "") {
                post.setFileId(postData.getFileId());
            }
            post.setDislike(0L);
            post.setLikes(0L);

            Post savedPost = postRepository.save(post);

            forum.get().addPost(savedPost);

            forumRepository.save(forum.get());
            return new ResponseEntity<>(savedPost, HttpStatus.OK);
        }
        return new ResponseEntity<>("Forum not found", HttpStatus.NOT_FOUND);
    }

    public List<PostDTO> postsToDTO (List<Post> posts) {
        List<PostDTO> postDTOS = new ArrayList<>();
        for (Post post: posts) {
            PostDTO postDTO = new PostDTO();

            postDTO.setId(post.getId());
            postDTO.setDate(post.getDate());
            postDTO.setContent(post.getContent());
            postDTO.setDislikes(postDTO.getDislikes());
            postDTO.setLikes(postDTO.getLikes());

            postDTOS.add(postDTO);
        }
        return postDTOS;
    }
}
