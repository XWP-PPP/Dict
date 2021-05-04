package com.example.dict;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.ResultBean;
import com.example.util.Constant;
import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTxtShowResult;//显示结果
    private ImageView mImgVoiceQuery;//语音查询
    private EditText mEditInput;//关键字
    private TextView mTxtStartQuery;//手动执行查询

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=5eccc4cc");
    }

    private void initView() {
        mTxtShowResult = findViewById(R.id.txt_show_result);
        mImgVoiceQuery = findViewById(R.id.img_voice_query);
        mImgVoiceQuery.setOnClickListener(this);
        mEditInput = findViewById(R.id.edt_key_word);
        mTxtStartQuery = findViewById(R.id.txt_start_query);
        mTxtStartQuery.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_start_query:
                queryData();//查询数据
                break;
            case R.id.img_voice_query:
                initSpeech(MainActivity.this);
                break;
        }
    }

    private void queryData() {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url(Constant.PATH_QUERY + mEditInput.getText().toString()).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          Toast.makeText(MainActivity.this, "查询数据失败", Toast.LENGTH_SHORT).show();
                      }
                  });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                final ResultBean resultBean = gson.fromJson(result, ResultBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTxtShowResult.setText(resultBean.toString());
                    }
                });
            }
        });
    }


    //初始化语音识别
    private void initSpeech(final Context context) {
        //1.创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(context, null);
        //2.设置accent、language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "en_us");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        //3.设置回调接口
        mDialog.setListener(new RecognizerDialogListener() {
            @Override
            public void onResult(RecognizerResult recognizerResult, boolean isLast) {
                if (isLast) {
                    //解析语音
                    //返回的result为识别后的汉字,直接赋值到TextView上即可
                    String result = parseVoice(recognizerResult.getResultString());
                    mEditInput.setText(result);
                }
            }

            @Override
            public void onError(SpeechError speechError) {

            }
        });
        //4.显示dialog，接收语音输入
        mDialog.show();
    }

    //解析语音json
    private String parseVoice(String resultString) {
        Gson gson = new Gson();
        Voice voiceBean = gson.fromJson(resultString, Voice.class);

        StringBuffer sb = new StringBuffer();
        ArrayList<Voice.WSBean> ws = voiceBean.ws;
        for (Voice.WSBean wsBean : ws) {
            String word = wsBean.cw.get(0).w;
            sb.append(word);
        }
        return sb.toString();
    }

     //语音对象封装
    public class Voice {

        public ArrayList<WSBean> ws;

        public class WSBean {
            public ArrayList<CWBean> cw;
        }

        public class CWBean {
            public String w;
        }
    }

}
