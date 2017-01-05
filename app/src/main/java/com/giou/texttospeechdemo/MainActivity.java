package com.giou.texttospeechdemo;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private final String TAG = MainActivity.class.getSimpleName();
    private TextToSpeech mTextToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSpeech();
        initView();
    }


    private void initSpeech() {
        mTextToSpeech = new TextToSpeech(this,this);
    }

    private void initView() {
        Button btnSpeech = (Button) findViewById(R.id.btn_speech);
        btnSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTextToSpeech != null && !mTextToSpeech.isSpeaking()) {
                    mTextToSpeech.setPitch(0.5f);// 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
                    mTextToSpeech.speak("已连接", TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }

    /**
     * 用来初始化TextToSpeech引擎
     * i:SUCCESS或ERROR这2个值
     * setLanguage设置语言，帮助文档里面写了有22种
     * TextToSpeech.LANG_MISSING_DATA：表示语言的数据丢失。
     * TextToSpeech.LANG_NOT_SUPPORTED:不支持
     */
    @Override
    public void onInit(int i) {
        Log.d(TAG,"i="+i);
        if (i == TextToSpeech.SUCCESS) {
            int result = mTextToSpeech.setLanguage(Locale.CHINA);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.d(TAG, "onInit 数据丢失或不支持");
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTextToSpeech.stop(); // 不管是否正在朗读TTS都被打断
        mTextToSpeech.shutdown(); // 关闭，释放资源
    }
}
