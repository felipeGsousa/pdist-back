package br.edu.ifpb.pdist_back.repository;

import br.edu.ifpb.pdist_back.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {

    List<Post> findByUserId(String userId);

}
