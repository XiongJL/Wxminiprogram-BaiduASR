package com.liwinon.ASR.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    //上传文件
    String uploadFile(String name, HttpServletRequest request);
    //mp3 转换pcm

    /**
     * @deprecated
     */
    String mp3Convertpcm(String mp3filepath, String pcmfilepath);
}
