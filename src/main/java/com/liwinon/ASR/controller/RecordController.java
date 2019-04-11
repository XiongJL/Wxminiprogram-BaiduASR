package com.liwinon.ASR.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baidu.aip.speech.AipSpeech;
import com.liwinon.ASR.service.FileService;
import com.liwinon.ASR.util.FileUtil;
import com.liwinon.ASR.util.MP3ToWav;
import com.liwinon.ASR.util.SpeechApi;

@RestController
public class RecordController {
    @Autowired
    FileService fileSerive;

    @PostMapping(value = "/asr/getRecoding")
    //通过session获取openid,用以区分用户 String session,
    public String getFileURL(HttpServletRequest request) {
        //String openid =
        String url = fileSerive.uploadFile("file", request);
        System.out.println(url);
        //调用转码接口
        String wavfilepath = url.replaceAll("mp3", "wav");
        System.out.println("pcmpaht: " + wavfilepath);
        boolean ok = MP3ToWav.byteToWav(MP3ToWav.getBytes(url), wavfilepath);
        if (!ok) {
            FileUtil.delFile(wavfilepath);
            FileUtil.delFile(url);
            return "convert fail";
        }
        //调用语音识别接口
        AipSpeech client = SpeechApi.getInstance().getClient();
        JSONObject res = client.asr(wavfilepath, "wav", 16000, null);
        //{"result":["您好，您还吗？"],"err_msg":"success.","sn":"161297329221554880326","corpus_no":"6678160150736547470","err_no":0}
        System.out.println(res);
        //["您好，您还吗？"]

        //删除文件
        FileUtil.delFile(wavfilepath);
        FileUtil.delFile(url);

        if (res.has("err_no") && 0 == (int) res.get("err_no")) {  //如果是正确的
            System.out.println(res.get("result"));
            return res.get("result").toString().replaceAll("\\[", "").replaceAll("\"", "").replaceAll("\\]", "").replaceAll("。", "").replaceAll("？", "");

        } else {
            return "asr fail";
        }

    }
}
