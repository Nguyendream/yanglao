package com.test.yanglao;

import com.test.yanglao.common.ServerResponse;
import com.test.yanglao.pojo.DeviceFile;
import com.test.yanglao.service.DeviceFileService;
import org.apache.catalina.Server;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YanglaoApplicationTests {

    @Resource
    private DeviceFileService deviceFileService;

    @Test
    public void contextLoads() {

        ServerResponse<DeviceFile> response = deviceFileService.fileSave(null, null);
    }

}
