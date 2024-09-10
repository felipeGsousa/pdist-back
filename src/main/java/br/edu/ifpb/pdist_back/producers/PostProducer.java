package br.edu.ifpb.pdist_back.producers;

import br.edu.ifpb.pdist_back.dto.FileDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;

@Component
public class PostProducer {

    final RabbitTemplate rabbitTemplate;
    public PostProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setReplyTimeout(1000000);
    }

    @Value(value = "${broker.queue.post.file}")
    private String postFileRoutingKey;

    @Value(value = "${broker.queue.file.post}")
    private String fileRoutingKey;

    public String storeFile(FileDTO fileDTO){
        String fileId = (String) rabbitTemplate.convertSendAndReceive("", postFileRoutingKey, fileDTO);
        System.out.println(fileId);
        return fileId;
    }

    public FileDTO getFile(String fileId){
        Map<String,Object> fileMapDto = (Map<String, Object>) rabbitTemplate.convertSendAndReceive("", fileRoutingKey, fileId);
        FileDTO fileDTO = new FileDTO();
        if (fileMapDto != null) {
            System.out.println();
            fileDTO.setId((String) fileMapDto.get("id"));
            fileDTO.setUserId((String) fileMapDto.get("userId"));
            String data = (String) fileMapDto.get("data");
            fileDTO.setData(Base64.getDecoder().decode(data));
            fileDTO.setContentType((String) fileMapDto.get("contentType"));
            fileDTO.setFilename((String) fileMapDto.get("filename"));
            return fileDTO;
        }

        return null;
    }
}
