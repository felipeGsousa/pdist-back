package br.edu.ifpb.pdist_back.consumers;

import br.edu.ifpb.pdist_back.service.CommentService;
import br.edu.ifpb.pdist_back.service.PostService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class LikeConsumer {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @RabbitListener(queues = "${broker.queue.post.like}")
    private HashMap<String, Long> likePost(@Payload HashMap<String, Object> likeAct){
        return postService.likePost(likeAct);
    }

    @RabbitListener(queues = "${broker.queue.comment.like}")
    private HashMap<String, Long> likeComments(@Payload HashMap<String, Object> likeAct){
        return commentService.likeComment(likeAct);
    }
}
