package com.test.yanglao.service;

import com.github.pagehelper.PageInfo;
import com.test.yanglao.common.ServerResponse;
import com.test.yanglao.pojo.DeviceFile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface DeviceFileService {

    ServerResponse<DeviceFile> fileSave(DeviceFile deviceFile, MultipartFile file);

    ServerResponse<String> fileUpload(DeviceFile deviceFile, MultipartFile file);

    ResponseEntity<InputStreamResource> fileDownload(Integer fileId, Integer userId);

    ServerResponse<PageInfo> getFileListById(Integer deviceId, int pageNum, int pageSize);
}
