package com.lx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lx.common.Result;
import com.lx.entity.Img;
import com.lx.mapper.ImgMapper;
import com.lx.service.UploadService;
import com.lx.utils.ServletResponse;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.ServletRequestPathUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@PropertySource("classpath:application.yml")
public class UploadServiceImpl implements UploadService {
    @Value("${upload.filePath}")
    private String filePath;
    private String basePath = "/static/image/";
    @Autowired
    private ImgMapper imgMapper;
    @Override
    public Result upload(MultipartFile file,HttpServletRequest request){
        String oldName = null;
        String newName = null;
        try{
            byte[] bytes = file.getBytes();
            oldName = file.getOriginalFilename();
            newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."),oldName.length());
            Img img = new Img();
            img.setName(newName);
            img.setBinData(bytes);
            imgMapper.insertImg(img);
        }catch (Exception e){
            e.printStackTrace();
        }
        Map<String,String> map = new HashMap<>();
        map.put("imgUrl",request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ basePath + newName);
        return Result.ok(map);
    }
    /*
     * 获取图片
     * */
    @Override
    public Result getImg(String imgName) {
        LambdaQueryWrapper<Img> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Img::getName,imgName);
        Img img = imgMapper.selectOne(wrapper);
        if (img != null) {
            byte[] binData = img.getBinData();
            ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
            HttpServletResponse response = attributes.getResponse();
            try{
                response.getOutputStream().write(binData);
            }catch (Exception e){
                e.printStackTrace();
            }
            return Result.ok();
        }
        return Result.error();
    }

}
