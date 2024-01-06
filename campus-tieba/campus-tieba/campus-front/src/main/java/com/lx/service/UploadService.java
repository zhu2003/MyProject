package com.lx.service;

import com.lx.common.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
    /*
    * 上传文件
    * */
    Result upload(MultipartFile file, HttpServletRequest request);
    /*
    * 获取图片
    * */
    Result getImg(String imgName);
}
