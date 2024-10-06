package com.example.sparrowcampus;

public class FileInModel {
    String filename, fileUrl, fileDesc;

    public FileInModel () {

    }

    public FileInModel(String filename, String fileUrl, String fileDesc) {
        this.filename = filename;
        this.fileUrl = fileUrl;
        this.fileDesc = fileDesc;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileDesc() {
        return fileDesc;
    }

    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc;
    }
}
