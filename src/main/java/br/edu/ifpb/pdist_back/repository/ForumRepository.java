package br.edu.ifpb.pdist_back.repository;

import br.edu.ifpb.pdist_back.model.Forum;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ForumRepository extends MongoRepository<Forum, String> {

    List<Forum> findByUserId(String userId);

}
