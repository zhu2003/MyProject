package com.lx.controller;

import com.lx.common.Result;
import com.lx.service.UploadService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@Slf4j
public class UploadController {
    @Autowired
    private UploadService uploadService;
    @PostMapping("/upload")
    public Result upload(MultipartFile file, HttpServletRequest request){
        return uploadService.upload(file,request);
    }

    @GetMapping("/static/image/{imgName}")
    public Result getImg(@PathVariable("imgName") String imgName){
        return uploadService.getImg(imgName);
    }
}
