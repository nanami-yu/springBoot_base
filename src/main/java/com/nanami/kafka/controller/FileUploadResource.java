package com.nanami.kafka.controller;

import com.nanami.kafka.entity.User;
import com.nanami.kafka.utils.Base64Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.*;

/**
 * ClassName: FileUploadController <br/>
 * Function: 文件上传Controller
 * date: 2017年09月28日 下午10:24:36 <br/>
 *
 * @author 郭占博
 * @version 1.0
 * @since JDK 1.8
 */
@RestController
public class FileUploadResource {

    //log日志
    private final Logger log = LoggerFactory.getLogger(FileUploadResource.class);

    @Value(value = "${upload.suffix.excel}")
    private String suffixExcel;

    @Value(value = "${upload.suffix.word}")
    private String suffixWord;

    @Value(value = "${upload.suffix.image}")
    private String suffixImage;

    @Value(value = "${upload.path}")
    private String uploadPath;

    private final ResourceLoader resourceLoader;

    FileUploadResource(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * 上传文件方法
     * @param file 前台上传的文件对象
     * @return
     */
    @PostMapping(value = "/api/upload")
    public Map<String,Object> upload(MultipartFile file,@RequestParam String type)
    {
        Map<String,Object> resultMap = new HashMap<>();
        try {
            resultMap = doUpload(file);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 显示单张图片
     * @return
     */
    @GetMapping(value = "/api/show")
    public ResponseEntity showPhotos(String path){
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + path));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 执行文件上传
     *
     * @param file 上传对象
     * @throws Exception
     * @return 图片地址及名称
     */
    private Map<String, Object> doUpload(MultipartFile file) throws Exception
    {
        //上传目录地址
//        String uploadDir = "/upload";
        String uploadDir;

        // 获取文件后缀名
        // 如果没有后缀名，则用uuid代替，解决Contains问题，强制走到else中
        String suffix = String.valueOf(UUID.randomUUID());
        if (file.getOriginalFilename().contains(".")) {
            suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        }
        String fileName = file.getOriginalFilename();
        // 根据日期与类型生成目录
        uploadDir = uploadPath + "/" + LocalDate.now().toString() + "/图片/";;
        //如果目录不存在，自动创建文件夹
        File fileDir = new File(uploadDir);
        if(!fileDir.exists())
        {
            fileDir.mkdirs();
        }

        //上传文件名
        String filename = UUID.randomUUID() + suffix;
        //服务器端保存的文件对象
        File serverFile = new File(fileDir + "/" + filename);
        //将上传的文件写入到服务器端文件内
        file.transferTo(serverFile);

        Map<String,Object> result = new HashMap<>();

        // 原始文件名
        result.put("name",fileName);
        // 上传文件名：路径+uuid+后缀
        result.put("path","/services/api/show?path=" + uploadDir + filename);
        return result;
    }



}
