package com.test.yanglao.service;

import com.test.yanglao.common.ServerResponse;
import com.test.yanglao.pojo.DeviceFile;
import org.springframework.web.multipart.MultipartFile;

public interface DeviceFileService {

    ServerResponse<DeviceFile> fileSave(DeviceFile deviceFile, MultipartFile file);

    ServerResponse<String> fileUpload(DeviceFile deviceFile, MultipartFile file);

    //todo list
}
