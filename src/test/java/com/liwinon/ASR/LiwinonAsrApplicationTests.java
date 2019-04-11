package com.liwinon.ASR;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baidu.aip.speech.AipSpeech;
import com.liwinon.ASR.service.FileService;
import com.liwinon.ASR.util.FileUtil;
import com.liwinon.ASR.util.SpeechApi;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LiwinonAsrApplicationTests {
    @Autowired
    FileService files;

    @Test
    public void contextLoads() {

    }

    @Test
    public void mp3() {
        //files.mp3Convertpcm("F:\\mp3\\Dog.mp3", "F:\\mp3\\Dog.pcm");
//		try {
//			FileUtil.convertMP32PCM("F:\\mp3\\a.mp3", "F:\\mp3\\a.pcm");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }

    @Test
    public void baiduapi() {
//        AipSpeech client = SpeechApi.getInstance().getClient();
//        JSONObject res = client.asr("F:\\mp3\\c.wav", "wav", 16000, null);
//        System.out.println(res);
//        System.out.println(res.get("result"));
    }

}
