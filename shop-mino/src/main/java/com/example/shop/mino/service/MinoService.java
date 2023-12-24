package com.example.shop.mino.service;

import io.minio.messages.Bucket;
import io.minio.messages.Item;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Description OSS对象服务
 * @Author zpx
 * @Date 2023/12/24 15:05
 * @Version 1.0
 */
public interface MinoService {
    /**
     * 判断bucket是否存在
     *
     * @param bucketName bucket名称
     * @return
     */
    public Boolean bucketExists(String bucketName);

    /**
     * 新建bucket
     *
     * @param bucketName bucket名称
     * @return
     */
    public Boolean makeBucket(String bucketName);

    /**
     * 删除bucket
     *
     * @param bucketName bucket名称
     * @return
     */
    public Boolean removeBucket(String bucketName);

    /**
     * 获取所有bucket列表
     *
     * @return
     */
    public List<Bucket> getAllBuckets();

    /**
     * 上传文件
     *
     * @param file 文件
     * @return
     */
    public String upload(MultipartFile file);

    /**
     * 预览图片
     *
     * @param fileName
     * @return
     */
    public String preview(String fileName);

    /**
     * 文件下载
     *
     * @param fileName 文件名称
     * @param res
     */
    public void download(String fileName, HttpServletResponse res);

    /**
     * 查看存储bucket内文件对象信息
     *
     * @return
     */
    public List<Item> listObjects();

    /**
     * 通过文件名删除文件
     * @param fileName
     * @return
     */
    public boolean removeFileByName(String fileName);
}
