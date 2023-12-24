package com.example.shop.mino.service.impl;

import cn.hutool.Hutool;
import com.alibaba.nacos.common.utils.UuidUtils;
import com.example.shop.mino.config.MinioConfig;
import com.example.shop.mino.controller.MinoController;
import com.example.shop.mino.service.MinoService;
import com.example.shop.mino.utils.MinoUtils;
import io.micrometer.common.util.StringUtils;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Description MinoService实现类
 * @Author zpx
 * @Date 2023/12/24 15:10
 * @Version 1.0
 */
@Service
@Slf4j
public class MinoImpl implements MinoService {
    @Resource
    private MinioConfig minioConfig;
    @Resource
    private MinioClient minioClient;

    @Override
    public Boolean bucketExists(String bucketName) {
        Boolean found;
        try {
            found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.error("check bucket name exists fail: " + e.getMessage());
            return false;
        }
        return found;
    }

    @Override
    public Boolean makeBucket(String bucketName) {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.error("make bucket fail: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Boolean removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.error("remove bucket fail: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Bucket> getAllBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            log.error("get all buckets fail: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public String upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isBlank(originalFilename)) {
            throw new RuntimeException("the name of upload file is empty");
        }
        String fileName = UuidUtils.generateUuid() + originalFilename.substring(originalFilename.lastIndexOf("."));
        String objectName = MinoUtils.getNowDateLongStr("yyyy-MM/dd") + "/" + fileName;
        // TODO: 更改不同的分片方法
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder().bucket(minioConfig.getBucketName()).object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1).contentType(file.getContentType()).build();
            minioClient.putObject(objectArgs);       // upload file
        } catch (Exception e) {
            log.error("up load file fail: " + e.getMessage());
            return null;
        }
        return fileName;
    }

    @Override
    public String preview(String fileName) {
        GetPresignedObjectUrlArgs build = new GetPresignedObjectUrlArgs().builder().bucket(minioConfig.getBucketName()).object(fileName).method(Method.GET).build();
        try {
            String url = minioClient.getPresignedObjectUrl(build);
            return url;
        } catch (Exception e) {
            log.error("preview file fail: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void download(String fileName, HttpServletResponse res) {
        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(minioConfig.getBucketName())
                .object(fileName).build();
        try (GetObjectResponse response = minioClient.getObject(objectArgs)) {
            byte[] buf = new byte[1024];
            int len;
            try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()) {
                while ((len = response.read(buf)) != -1) {
                    os.write(buf, 0, len);
                }
                os.flush();
                byte[] bytes = os.toByteArray();
                res.setCharacterEncoding("utf-8");
                // res.setContentType("application/force-download");
                res.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
                try (ServletOutputStream stream = res.getOutputStream()) {
                    stream.write(bytes);
                    stream.flush();
                }
            }
        } catch (Exception e) {
            log.error("download file fail: " + e.getMessage());
        }
    }

    @Override
    public List<Item> listObjects() {
        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket(minioConfig.getBucketName()).build());
        List<Item> items = new ArrayList<>();
        try {
            for (Result<Item> result : results) {
                items.add(result.get());
            }
        } catch (Exception e) {
            log.error("lists of bucket fail: " + e.getMessage());
            return null;
        }
        return items;
    }

    @Override
    public boolean removeFileByName(String fileName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(minioConfig.getBucketName()).object(fileName).build());
        } catch (Exception e) {
            log.error("remove file fail:" + e.getMessage());
            return false;
        }
        return true;
    }
}
