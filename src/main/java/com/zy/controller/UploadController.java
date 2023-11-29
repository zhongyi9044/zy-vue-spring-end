package com.zy.controller;

import com.zy.pojo.Result;
import com.zy.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class UploadController {

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
//        获取文件原始名字
        String fileOriginalName=file.getOriginalFilename();
//        截取.png拼接文件名,避免名字相同文件覆盖
        String fileName= UUID.randomUUID().toString()+fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
        String url=AliOssUtil.uploadFile(fileName,file.getInputStream());
        return Result.success(url);
    }
}
