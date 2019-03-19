package com.test.yanglao.vo;

import java.util.Date;

public class DeviceIdListVo {
    private Integer id;

    private Integer deviceId;

    private String deviceName;

    private Date createTime;

    private Date updateTime;

    public DeviceIdListVo(Integer id, Integer deviceId, String deviceName, Date createTime, Date updateTime) {
        this.id = id;
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public DeviceIdListVo() {
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

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
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
