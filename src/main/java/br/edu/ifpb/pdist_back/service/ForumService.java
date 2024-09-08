package br.edu.ifpb.pdist_back.service;

import br.edu.ifpb.pdist_back.controller.CommentImpl;
import br.edu.ifpb.pdist_back.dto.ForumDTO;
import br.edu.ifpb.pdist_back.dto.PostDTO;
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
        List<ForumDTO> forumDTOS = forumsToDTO(forums);
        return new ResponseEntity<>(forumDTOS, HttpStatus.OK);
    }

    public ResponseEntity<?> getForum(String id){
        Optional<Forum> forum = forumRepository.findById(id);
        if (forum.isPresent()) {
            ForumDTO forumDTO = forumToDTO(forum.get());
            return new ResponseEntity<>(forumDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>("Forum not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getUserForums(String id) {
        List<Forum> forums = forumRepository.findByUserId(id);
        List<ForumDTO> forumDTOS = forumsToDTO(forums);
        return new ResponseEntity<>(forumDTOS, HttpStatus.OK);
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

    public ResponseEntity<?> updateForum(String id, ForumDTO forumData) {
        Optional<Forum> forum = forumRepository.findById(id);
        if (forum.isPresent()) {
            byte[] banner = new byte[0];
            if (forumData.getBanner() != "") {
                banner = Base64.getDecoder().decode(forumData.getBanner());
            }
            forum.get().setName(forumData.getName());
            forum.get().setTopic(forumData.getTopic());
            forum.get().setBanner(banner);
            forum.get().setDescription(forumData.getDescription());
            forumRepository.save(forum.get());
            return new ResponseEntity<>("Forum has been updated", HttpStatus.OK);
        }
        return new ResponseEntity<>("Forum not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> joinForum(String forumId, String userId) {
        Optional<Forum> forum = forumRepository.findById(forumId);
        if (forum.isPresent()) {
            forum.get().addUser(userId);
            return new ResponseEntity<>("User joined", HttpStatus.OK);
        }
        return new ResponseEntity<>("Forum not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> leaveForum(String forumId, String userId) {
        Optional<Forum> forum = forumRepository.findById(forumId);
        if (forum.isPresent()) {
            forum.get().removeUser(userId);
            return new ResponseEntity<>("User leave", HttpStatus.OK);
        }
        return new ResponseEntity<>("Forum not found", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> addForum(ForumDTO forumData) {
        Forum forum = new Forum();
        byte[] banner = new byte[0];
        if (forumData.getBanner() != "") {
            banner = Base64.getDecoder().decode(forumData.getBanner());
        }
        forum.setName(forumData.getName());
        forum.setTopic(forumData.getTopic());
        forum.setBanner(banner);
        forum.setDescription(forumData.getDescription());
        forum.setCreated(new Date());
        forum.setUserId(forumData.getUserId());
        forum.setUsers(new ArrayList<>());
        forum.setPosts(new ArrayList<>());

        forum.addUser(forum.getUserId());
        Forum createdForum = forumRepository.save(forum);
        ForumDTO dto = forumToDTO(createdForum);

        return new ResponseEntity<>(dto ,HttpStatus.CREATED);
    }

    public ForumDTO forumToDTO(Forum forum) {
        ForumDTO forumDTO = new ForumDTO();
        forumDTO.setId(forum.getId());
        forumDTO.setPosts(postsToDTO(forum.getPosts()));
        forumDTO.setUsers(forum.getUsers());
        forumDTO.setUserId(forum.getUserId());
        forumDTO.setCreated(forum.getCreated());

        if (forum.getBanner() != null && forum.getBanner().length > 0) {
            forumDTO.setBanner(Base64.getEncoder().encodeToString(forum.getBanner()));
        }

        forumDTO.setDescription(forum.getDescription());
        forumDTO.setName(forum.getName());
        forumDTO.setTopic(forum.getTopic());
        return forumDTO;
    }
    public List<ForumDTO> forumsToDTO(List<Forum> forums) {
        List<ForumDTO> forumDTOS = new ArrayList<>();

        for (Forum forum: forums) {
            ForumDTO forumDTO = new ForumDTO();

            forumDTO.setId(forum.getId());
            forumDTO.setUsers(forum.getUsers());
            forumDTO.setUserId(forum.getUserId());
            forumDTO.setCreated(forum.getCreated());
            if (forum.getBanner() != null && forum.getBanner().length > 0) {
                forumDTO.setBanner(Base64.getEncoder().encodeToString(forum.getBanner()));
            }
            forumDTO.setDescription(forum.getDescription());
            forumDTO.setName(forum.getName());
            forumDTO.setTopic(forum.getTopic());
            forumDTOS.add(forumDTO);
        }
        return forumDTOS;
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
