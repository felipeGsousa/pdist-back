package br.edu.ifpb.pdist_back.controller;

import br.edu.ifpb.pdist_back.interfaces.CommentInterface;
import br.edu.ifpb.pdist_back.model.Comment;
import br.edu.ifpb.pdist_back.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentImpl implements CommentInterface {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void deleteComments(Comment comment) {
        for (Comment nestedComment : comment.getComments()){
            deleteComments(nestedComment);
        }
        commentRepository.delete(comment);
    }

}
