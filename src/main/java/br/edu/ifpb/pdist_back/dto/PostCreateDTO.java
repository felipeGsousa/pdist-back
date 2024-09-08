package br.edu.ifpb.pdist_back.dto;

public class PostCreateDTO {
    private String title;
    private String content;
    private String userId;
    private String fileId;
    private FileCreateDTO file;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public FileCreateDTO getFile() {
        return file;
    }

    public void setFile(FileCreateDTO file) {
        this.file = file;
    }
}
