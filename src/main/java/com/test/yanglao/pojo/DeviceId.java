package com.test.yanglao.pojo;

import java.util.Date;

public class DeviceId {
    private Integer id;

    private Integer deviceId;

    private Integer userId;

    private Date createTime;

    private Date updateTime;

    public DeviceId(Integer id, Integer deviceId, Integer userId, Date createTime, Date updateTime) {
        this.id = id;
        this.deviceId = deviceId;
        this.userId = userId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public DeviceId() {
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}