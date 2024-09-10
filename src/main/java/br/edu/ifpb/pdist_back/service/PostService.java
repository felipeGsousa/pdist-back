package br.edu.ifpb.pdist_back.service;

import br.edu.ifpb.pdist_back.controller.CommentImpl;
import br.edu.ifpb.pdist_back.dto.FileDTO;
import br.edu.ifpb.pdist_back.dto.PostCreateDTO;
import br.edu.ifpb.pdist_back.dto.PostDTO;
import br.edu.ifpb.pdist_back.model.Comment;
import br.edu.ifpb.pdist_back.model.Forum;
import br.edu.ifpb.pdist_back.model.Post;
import br.edu.ifpb.pdist_back.producers.PostProducer;
import br.edu.ifpb.pdist_back.repository.ForumRepository;
import br.edu.ifpb.pdist_back.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ForumRepository forumRepository;
    @Autowired
    private CommentImpl commentImpl;
    @Autowired
    private PostProducer postProducer;

    public ResponseEntity<?> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostDTO> postDTOS = postsToDTO(posts);
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);
    }
    public ResponseEntity<?> getPost(String id){
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            PostDTO postDTO = postToDTO(post.get());
            return new ResponseEntity<>(postDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getForumPosts(String forumId){
        List<Post> posts = postRepository.findByForumId(forumId);
        List<PostDTO> postDTOS = postsToDTO(posts);
        return new ResponseEntity<>(postDTOS, HttpStatus.OK);
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

    public ResponseEntity<?> updatePost(String id, PostDTO postData) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            post.get().setTitle(postData.getTitle());
            post.get().setContent(postData.getContent());
            postRepository.save(post.get());
            return new ResponseEntity<>("Post has been updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<?> addPost(String forumId, PostCreateDTO postData) {
        Optional<Forum> forum = forumRepository.findById(forumId);
        if (forum.isPresent()) {
            Post post = new Post();

            post.setContent(postData.getContent());
            post.setTitle(postData.getTitle());
            post.setDate(new Date());
            post.setComments(new ArrayList<>());
            post.setUserId(postData.getUserId());
            post.setForumId(forumId);

            /*
            * Verificar file em PostDTO
            * Chamar microsserviço para salvar arquivo
            * Esperar resposta com ID
            * */

            if (!postData.getFileId().isEmpty()) {
                post.setFileId(postData.getFileId());
            } else {
                post.setFileId("");
            }

            if (!postData.getFile().getData().isEmpty()) {
                FileDTO fileDTO = new FileDTO();
                fileDTO.setData(Base64.getDecoder().decode(postData.getFile().getData()));
                fileDTO.setContentType(postData.getFile().getContentType());
                fileDTO.setFilename(postData.getFile().getFilename());
                fileDTO.setUserId(postData.getUserId());
                try {
                    post.setFileId(postProducer.storeFile(fileDTO));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            post.setDislikes(0L);
            post.setLikes(0L);

            Post savedPost = postRepository.save(post);

            forum.get().addPost(savedPost);

            forumRepository.save(forum.get());
            return new ResponseEntity<>(savedPost.getId().toString(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Forum not found", HttpStatus.NOT_FOUND);
    }

    public List<PostDTO> postsToDTO (List<Post> posts) {
        List<PostDTO> postDTOS = new ArrayList<>();
        for (Post post: posts) {
            PostDTO postDTO;

            postDTO = postToDTO(post);

            postDTOS.add(postDTO);
        }
        return postDTOS;
    }

    public PostDTO postToDTO (Post post) {
        PostDTO postDTO = new PostDTO();

        postDTO.setId(post.getId());
        postDTO.setDate(post.getDate());
        postDTO.setContent(post.getContent());
        postDTO.setDislikes(post.getDislikes());
        postDTO.setLikes(post.getLikes());
        postDTO.setComments(post.getComments());

        if (!post.getFileId().isEmpty()) {
            try {
                FileDTO file = postProducer.getFile(post.getFileId());
                if (file != null) {
                    postDTO.setFile(file);
                } else {
                    post.setFileId("");
                    postRepository.save(post);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        postDTO.setFileId(post.getFileId());

        return postDTO;
    }

    public void likePost(HashMap<String, Object> likeAct) {
        Optional<Post> postOpt = postRepository.findById((String) likeAct.get("id"));

        if (postOpt.isPresent()){
            Post post = postOpt.get();

            if ((boolean)likeAct.get("addLike")) {
                post.addLike();
            }
            if ((boolean)likeAct.get("subLike")) {
                post.subLike();
            }
            if ((boolean)likeAct.get("addDislike")) {
                post.addDislike();
            }
            if ((boolean)likeAct.get("subDislike")) {
                post.subDislike();
            }

            postRepository.save(post);
        }
    }
}
