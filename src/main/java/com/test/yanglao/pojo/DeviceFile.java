package com.test.yanglao.pojo;

import java.util.Date;

public class DeviceFile {
    private Integer id;

    private Integer deviceId;

    private String fileName;

    private String fileType;

    private String filePath;

    private Date createTime;

    public DeviceFile(Integer id, Integer deviceId, String fileName, String fileType, String filePath, Date createTime) {
        this.id = id;
        this.deviceId = deviceId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.filePath = filePath;
        this.createTime = createTime;
    }

    public DeviceFile() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}