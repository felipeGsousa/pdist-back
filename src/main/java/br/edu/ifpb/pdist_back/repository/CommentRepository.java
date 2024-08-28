package br.edu.ifpb.pdist_back.repository;

import br.edu.ifpb.pdist_back.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findByUserId(String userId);

}
