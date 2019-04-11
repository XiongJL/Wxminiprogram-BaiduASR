package com.liwinon.ASR.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.liwinon.ASR.util.FileUtil;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;

@Service
public class FileServiceImpl implements FileService {

    //上传文件
    @Override
    public String uploadFile(String name, HttpServletRequest request) {
        // 根据小程序发过来的文件名进行接收
        MultipartFile file = ((MultipartHttpServletRequest) request).getFile(name);
        String contentType = file.getContentType(); //文件类型
        String fileName = file.getOriginalFilename(); // 文件名字
        System.out.println("type:" + contentType);
        System.out.println("fileName:" + fileName);
        String path = "D:/tmpFile/";  //临时文件路径  File.separator 得到的是 / ,在下一阶段会导致获取文件失败
        System.out.println("path:" + path);
        //获取Web服务器项目的真实物理路径
        //String realPath = request.getSession().getServletContext().getRealPath(path);
        //System.out.println("realPath: "+ realPath);
        //创建文件
        try {
            FileUtil.createFile(file.getBytes(), path, fileName);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "uploadFail";
        }
        return path + fileName;
    }

    /**
     * MP3 转换 pcm
     *
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @deprecated 废用
     */
    @Override
    public String mp3Convertpcm(String mp3path, String pcmpath) {
        File mp3 = new File(mp3path);
        File pcm = new File(pcmpath);
        if (mp3 == null || pcm == null)
            return "file handle null";
        try {
            //mp3 转为音频流
            MpegAudioFileReader mp = new MpegAudioFileReader();
            AudioInputStream mp3Stream = mp.getAudioInputStream(mp3);
            //pcm接收MP3流
            AudioInputStream pcmStream = AudioSystem.getAudioInputStream(AudioFormat.Encoding.PCM_SIGNED, mp3Stream);
            //将pcm流输出
            OutputStream os = new FileOutputStream(pcm);
            int bytesRead = 0;
            byte[] buffer = new byte[6666];  //缓冲区大小/字节
            while ((bytesRead = pcmStream.read(buffer, 0, 6666)) != -1) {
                os.write(buffer, 0, bytesRead); //写
            }
            os.close();
            pcmStream.close();
            mp3Stream.close();
        } catch (UnsupportedAudioFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return pcmpath;
    }


}
