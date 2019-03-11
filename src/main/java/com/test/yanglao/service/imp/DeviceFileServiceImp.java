package com.test.yanglao.service.imp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.yanglao.common.ResponseCode;
import com.test.yanglao.common.ServerResponse;
import com.test.yanglao.dao.DeviceFileMapper;
import com.test.yanglao.dao.DeviceIdMapper;
import com.test.yanglao.pojo.DeviceFile;
import com.test.yanglao.pojo.DeviceId;
import com.test.yanglao.service.DeviceFileService;
import com.test.yanglao.vo.DeviceFileListVo;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("DeviceService")
public class DeviceFileServiceImp implements DeviceFileService {

    @Resource
    private Environment env;

    @Resource
    private DeviceFileMapper deviceFileMapper;

    @Resource
    private DeviceIdMapper deviceIdMapper;

    @Override
    public ServerResponse<DeviceFile> fileSave(DeviceFile deviceFile, MultipartFile file) {

        //配置文件的储存根目录
        String rootPath = env.getProperty("env.file.path");
        //日期路径
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fullPath = rootPath + File.separator + dateFormat.format(new Date());
        Path rootLocation = Paths.get(fullPath);
        try {
            if (!Files.isDirectory(rootLocation)) {
                Files.createDirectories(rootLocation);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ServerResponse.createByErrorMessage("无法初始化储存位置");
        }

        if (file == null) {
            return ServerResponse.createByErrorMessage("文件为null");
        }

        //路径组合
        Path filePath = rootLocation.resolve(deviceFile.getFileName());
        deviceFile.setFilePath(filePath.toString());

        try {
            if (file.getInputStream() != null) {
                //文件写入
                Files.copy(file.getInputStream(), filePath);
            }
        } catch (FileAlreadyExistsException e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("文件已存在");
        } catch (IOException e) {
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("文件写入错误");
        }

        return ServerResponse.createBySuccess("文件写入成功", deviceFile);
    }

    @Override
    public ServerResponse<String> fileUpload(DeviceFile deviceFile, MultipartFile file) {

        int resultCount = deviceIdMapper.checkDeviceId(deviceFile.getDeviceId());
        if (resultCount == 0) {
            return ServerResponse.createByErrorCodeMessage(
                    ResponseCode.DEVICE_NULL.getCode(),"设备号未注册");
        }

        deviceFile.setFileName(file.getOriginalFilename());
        deviceFile.setFileType(file.getContentType());

        ServerResponse<DeviceFile> response = this.fileSave(deviceFile, file);
        if (!response.isSuccess()) {
            return ServerResponse.createByErrorMessage(response.getMsg());
        }

        resultCount = deviceFileMapper.insert(response.getData());
        if (resultCount > 0) {
            return ServerResponse.createBySuccessMessage("文件上传成功");
        }
        return ServerResponse.createByErrorMessage("文件写入成功，数据库写入失败");
    }

    @Override
    public ResponseEntity<InputStreamResource> fileDownload(Integer fileId, Integer userId) {

        DeviceFile deviceFile = deviceFileMapper.selectByPrimaryKey(fileId);
        DeviceId deviceId = deviceIdMapper.selectByDeviceIdUserId(deviceFile.getDeviceId(), userId);
        if (deviceFile == null || deviceId == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        try {
            FileSystemResource file = new FileSystemResource(deviceFile.getFilePath());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(file.contentLength())
                    .contentType(MediaType.parseMediaType(deviceFile.getFileType()))
                    .body(new InputStreamResource(file.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ServerResponse<PageInfo> getFileListById(Integer deviceId, int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<DeviceFile> deviceFileList = deviceFileMapper.selectByDeviceId(deviceId);

        List<DeviceFileListVo> deviceFileListVoList = new ArrayList<>();
        for (DeviceFile item : deviceFileList) {
            DeviceFileListVo deviceFileListVo = assembleDeviceFileListVo(item);
            deviceFileListVoList.add(deviceFileListVo);
        }

        PageInfo pageInfo = new PageInfo(deviceFileList);
        pageInfo.setList(deviceFileListVoList);

        return ServerResponse.createBySuccess(pageInfo);
    }

    private DeviceFileListVo assembleDeviceFileListVo(DeviceFile deviceFile) {
        DeviceFileListVo deviceFileListVo = new DeviceFileListVo();
        deviceFileListVo.setId(deviceFile.getId());
        deviceFileListVo.setFileName(deviceFile.getFileName());
        deviceFileListVo.setFileType(deviceFile.getFileType());
        deviceFileListVo.setCreateTime(deviceFile.getCreateTime());

        return deviceFileListVo;
    }
}
