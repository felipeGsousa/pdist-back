package br.edu.ifpb.pdist_back.service;

import br.edu.ifpb.pdist_back.grpc.FileDTO;
import br.edu.ifpb.pdist_back.grpc.FileServiceGrpc;
import br.edu.ifpb.pdist_back.grpc.GetFileRequest;
import br.edu.ifpb.pdist_back.grpc.UploadFileResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class FileServiceGrpcClient {

    private final FileServiceGrpc.FileServiceBlockingStub stub;

    public FileServiceGrpcClient() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("35.212.94.98", 50051)
                .usePlaintext()
                .build();
        this.stub = FileServiceGrpc.newBlockingStub(channel);
    }

    public String uploadFile(String userId, String filename, String contentType, byte[] data) {
        FileDTO fileDTO = FileDTO.newBuilder()
                .setId(userId)
                .setFilename(filename)
                .setContentType(contentType)
                .setData(com.google.protobuf.ByteString.copyFrom(data))
                .build();

        UploadFileResponse response = stub.uploadFile(fileDTO);
        return response.getId();
    }

    public FileDTO getFileById(String fileId) {
        GetFileRequest request = GetFileRequest.newBuilder()
                .setId(fileId)
                .build();
        return stub.getFileById(request);
    }

}
