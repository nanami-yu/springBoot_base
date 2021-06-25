package com.nanami.kafka.controller;


import com.nanami.kafka.service.BaiduService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @author fengyu
 * @version 1.0
 * @Title: 百度人脸识别控制层
 * @date 2020/11/30 16:08
 */
@RestController
public class BaiduController {
    @Autowired
    private BaiduService baiduService;

    /**
     * @Description: 调用百度api上传图片
     *
     * @param file
     * @author fengyu
     * @date 2020/12/1 9:30
     * @return java.lang.String
     */
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        baiduService.detect(file);
        return "";
    }

    /**
     * @Description:调用百度api 检查是否为本人
     *
     * @param file
     * @author fengyu
     * @date 2020/12/1 9:31
     * @return java.lang.String
     */
    @PostMapping("/api/check")
    public String check(@RequestParam("file") MultipartFile file) throws IOException {
        baiduService.faceSearch(file);
        return "";
    }

}
