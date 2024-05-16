package com.daniu.controller;

import com.daniu.annotation.SysLog;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.daniu.common.BaseResponse;
import com.daniu.common.ErrorCode;
import com.daniu.common.ResultUtils;
import com.daniu.exception.BusinessException;
import com.daniu.utils.file.minio.MinioUtils;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/minio")
public class MinioController {

    @Resource
    MinioUtils minioService;

    //列表
    @SysLog(module = "Minio服务", type = "查询Minio文件列表")
    @GetMapping("/list")
    public BaseResponse<List<String>> list() {
        List<String> strings = minioService.listObjects();
        return ResultUtils.success(strings);
    }

    //删除
    @SysLog(module = "Minio服务", type = "删除文件")
    @PutMapping("/delete")
    public BaseResponse<Boolean> delete(@RequestParam String filename) {
        minioService.deleteObject(filename);
        return ResultUtils.success(true);
    }

    //上传文件
    @SysLog(module = "Minio服务", type = "上传文件")
    @PostMapping("/upload")
    public BaseResponse<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            // todo 完善文件命名逻辑
            InputStream is = file.getInputStream(); //得到文件流
            String fileName = file.getOriginalFilename(); //文件名
            String newFileName = System.currentTimeMillis() + "." + StringUtils.substringAfterLast(fileName, ".");
            // todo 完善类型校验逻辑
            String contentType = file.getContentType();  //类型
            minioService.uploadObject(is, newFileName, contentType);
            return ResultUtils.success(newFileName);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "上传失败");
        }
    }

    //下载minio服务的文件
    @SysLog(module = "Minio服务", type = "下载文件")
    @GetMapping("/download")
    public void download(@RequestParam String filename, HttpServletResponse response) {
        try {
            InputStream fileInputStream = minioService.getObject(filename);
            // todo 完善文件命名逻辑
            String newFileName = System.currentTimeMillis() + "." + StringUtils.substringAfterLast(filename, ".");
            response.setHeader("Content-Disposition", "attachment;filename=" + newFileName);
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(fileInputStream, response.getOutputStream());
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "下载失败");
        }
    }

    //获取minio文件的下载地址
    @SysLog(module = "Minio服务", type = "获取文件下载地址")
    @GetMapping("/getHttpUrl")
    public BaseResponse<String> getHttpUrl(@RequestParam String filename) {
        try {
            String url = minioService.getObjectUrl(filename);
            return ResultUtils.success(url);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, e.getMessage());
        }
    }


}

