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
    private void likePost(@Payload HashMap<String, Object> likeAct){
        postService.likePost(likeAct);
    }

    @RabbitListener(queues = "${broker.queue.comment.like}")
    private void likeComments(@Payload HashMap<String, Object> likeAct){
        commentService.likeComment(likeAct);
    }
}
