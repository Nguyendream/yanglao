package com.test.yanglao.pojo;

import java.util.Date;

public class DeviceLogs {
    private Integer id;

    private Integer deviceId;

    private Double temp;

    private Double humi;

    private Double gus;

    //todo 红外

    private Date createTime;

    public DeviceLogs(Integer id, Integer deviceId, Double temp, Double humi, Double gus, Date createTime) {
        this.id = id;
        this.deviceId = deviceId;
        this.temp = temp;
        this.humi = humi;
        this.gus = gus;
        this.createTime = createTime;
    }

    public DeviceLogs() {
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

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getHumi() {
        return humi;
    }

    public void setHumi(Double humi) {
        this.humi = humi;
    }

    public Double getGus() {
        return gus;
    }

    public void setGus(Double gus) {
        this.gus = gus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}