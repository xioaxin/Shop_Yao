package com.example.shop.mino.controller;

import com.example.shop.common.vo.R;
import com.example.shop.mino.config.MinioConfig;
import com.example.shop.mino.service.MinoService;
import io.minio.messages.Bucket;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description MinoController
 * @Author zpx
 * @Date 2023/12/24 15:04
 * @Version 1.0
 */
@RestController
@Slf4j
public class MinoController {
    @Autowired
    private MinoService minoService;
    @Autowired
    private MinioConfig minioConfig;

    @GetMapping("/bucketExists")
    public R bucketExists(@RequestParam("bucketName") String bucketName) {
        return R.ok().put("bucketName", minoService.bucketExists(bucketName));
    }

    @GetMapping("/makeBucket")
    public R makeBucket(String bucketName) {
        return R.ok().put("bucketName", minoService.makeBucket(bucketName));
    }

    @GetMapping("/removeBucket")
    public R removeBucket(String bucketName) {
        return R.ok().put("bucketName", minoService.removeBucket(bucketName));
    }

    @GetMapping("/getAllBuckets")
    public R getAllBuckets() {
        List<Bucket> allBuckets = minoService.getAllBuckets();
        return R.ok().put("allBuckets", allBuckets);
    }

    @PostMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file) {
        String objectName = minoService.upload(file);
        if (null != objectName) {
            return R.ok().put("url", (minioConfig.getEndpoint() + "/" + minioConfig.getBucketName() + "/" + objectName));
        }
        return R.error();
    }

    @GetMapping("/preview")
    public R preview(@RequestParam("fileName") String fileName) {
        return R.ok().put("fileName", minoService.preview(fileName));
    }

    @GetMapping("/download")
    public R download(@RequestParam("fileName") String fileName, HttpServletResponse res) {
        minoService.download(fileName, res);
        return R.ok();
    }

    @PostMapping("/delete")
    public R remove(String url) {
        String objName = url.substring(url.lastIndexOf(minioConfig.getBucketName() + "/") + minioConfig.getBucketName().length() + 1);
        minoService.removeFileByName(objName);
        return R.ok().put("objName", objName);
    }

}
