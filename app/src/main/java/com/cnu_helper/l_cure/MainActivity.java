package com.cnu_helper.l_cure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.kakao.sdk.newtoneapi.SpeechRecognizeListener;
import com.kakao.sdk.newtoneapi.SpeechRecognizerClient;
import com.kakao.sdk.newtoneapi.SpeechRecognizerManager;
import com.kakao.sdk.newtoneapi.TextToSpeechClient;
import com.kakao.sdk.newtoneapi.TextToSpeechListener;
import com.kakao.sdk.newtoneapi.TextToSpeechManager;



import java.security.MessageDigest;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements TextToSpeechListener, SpeechRecognizeListener {
    private static final int REQUEST_CODE_AUDIO_AND_WRITE_EXTERNAL_STORAGE = 0;
    private static final String TAG = "kakao";
    Button learn_skills, improving_skills, my_own_words;
    Button setting;
    private TextToSpeechClient ttsClient;
    private String state_text = null;
    private static final String SEARCH = "SEARCH";
    private static final String REASK = "REASK";
    private static final String YES = "YES";
    private static final String NO = "NO";
    String sex, speed, voice;
    private String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // l-cure 도움말 창
        SharedPreferences preferences = getSharedPreferences("a", MODE_PRIVATE);
        int firstviewshow = preferences.getInt("First", 0);
        if(firstviewshow != 1) {
            Intent intent = new Intent(MainActivity.this, FirstStartActivity.class);
            startActivity(intent);
        }

        // 도움말 버튼
        Button btn_information = (Button) findViewById(R.id.information);
        btn_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FirstStartActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        sex = intent.getStringExtra("sex");
        speed = intent.getStringExtra("speed");
        voice = intent.getStringExtra("voice");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_AUDIO_AND_WRITE_EXTERNAL_STORAGE);
        }

        // 음성합성 초기화
        SpeechRecognizerManager.getInstance().initializeLibrary(this);
        TextToSpeechManager.getInstance().initializeLibrary(getApplicationContext());

        getHashKey(); // 키해시 찾기 (kakao api)

        setting = findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectSettingsActivity.class);
                startActivityForResult(intent, 5000);
            }
        });

        learn_skills = findViewById(R.id.learn_skills);
        learn_skills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                test = "test";
                intent.putExtra("test", test);
                intent.putExtra("number", 11);
                intent.putExtra("sex", sex);
                intent.putExtra("speed", speed);
                intent.putExtra("voice", voice);
                startActivityForResult(intent,5000);
            }
        });

        improving_skills = findViewById(R.id.improving_skills);
        improving_skills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectImprovingSkillsActivity.class);
                test = "no";
                intent.putExtra("test", test);
                intent.putExtra("sex", sex);
                intent.putExtra("speed", speed);
                intent.putExtra("voice", voice);
                startActivityForResult(intent, 5000);
            }
        });

        my_own_words = findViewById(R.id.my_own_words);
        my_own_words.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                test = "no";
                intent.putExtra("test", test);
                intent.putExtra("number", 10);
                intent.putExtra("sex", sex);
                intent.putExtra("speed", speed);
                intent.putExtra("voice", voice);
                startActivityForResult(intent,5000);
            }
        });
    }

    private void getHashKey(){
        try{
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String key = new String(Base64.encode(md.digest(), 0));
                Log.d("Hash key:", "!!!!!!!"+key+"!!!!!!"); // print hash key
            }
        } catch (Exception e){
            Log.e("name not found", e.toString());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TextToSpeechManager.getInstance().finalizeLibrary();
        SpeechRecognizerManager.getInstance().finalizeLibrary();
    }

    private void handleError(int errorCode) {
        String errorText;
        switch (errorCode) {
            case TextToSpeechClient.ERROR_NETWORK:
                errorText = "네트워크 오류";
                break;
            case TextToSpeechClient.ERROR_NETWORK_TIMEOUT:
                errorText = "네트워크 지연";
                break;
            case TextToSpeechClient.ERROR_CLIENT_INETRNAL:
                errorText = "음성합성 클라이언트 내부 오류";
                break;
            case TextToSpeechClient.ERROR_SERVER_INTERNAL:
                errorText = "음성합성 서버 내부 오류";
                break;
            case TextToSpeechClient.ERROR_SERVER_TIMEOUT:
                errorText = "음성합성 서버 최대 접속시간 초과";
                break;
            case TextToSpeechClient.ERROR_SERVER_AUTHENTICATION:
                errorText = "음성합성 인증 실패";
                break;
            case TextToSpeechClient.ERROR_SERVER_SPEECH_TEXT_BAD:
                errorText = "음성합성 텍스트 오류";
                break;
            case TextToSpeechClient.ERROR_SERVER_SPEECH_TEXT_EXCESS:
                errorText = "음성합성 텍스트 허용 길이 초과";
                break;
            case TextToSpeechClient.ERROR_SERVER_UNSUPPORTED_SERVICE:
                errorText = "음성합성 서비스 모드 오류";
                break;
            case TextToSpeechClient.ERROR_SERVER_ALLOWED_REQUESTS_EXCESS:
                errorText = "허용 횟수 초과";
                break;
            default:
                errorText = "정의하지 않은 오류";
                break;
        }

        final String statusMessage = errorText + " (" + errorCode + ")";

        Log.i(TAG, statusMessage);
    }

    @Override
    public void onReady() {//모든 하드웨어및 오디오 서비스가 모두 준비 된 다음 호출
        Log.d("MainActivity", "모든 준비가 완료 되었습니다.");
    }

    @Override
    public void onBeginningOfSpeech() { //사용자가 말하기 시작하는 순간 호출
        Log.d("MainActivity", "말하기 시작 했습니다.");
    }

    @Override
    public void onEndOfSpeech() {//사용자가 말하기를 끝냈다고 판단되면 호출
        Log.d("MainActivity", "말하기가 끝났습니다.");
    }

    @Override
    public void onError(int errorCode, String errorMsg) {
        handleError(errorCode);

    }

    @Override
    public void onPartialResult(String partialResult) {//인식된 음성 데이터를 문자열로 알려 준다.

    }


    @Override
    public void onResults(Bundle results) { //음성 입력이 종료된것으로 판단하고 서버에 질의를 모두 마치고 나면 호출
        final StringBuilder builder = new StringBuilder();

        final ArrayList<String> texts = results.getStringArrayList(SpeechRecognizerClient.KEY_RECOGNITION_RESULTS);
        ArrayList<Integer> confs = results.getIntegerArrayList(SpeechRecognizerClient.KEY_CONFIDENCE_VALUES);

        Log.d("MainActivity", "Result: " + texts);

        for (int i = 0; i < texts.size(); i++) {
            builder.append(texts.get(i));
            builder.append(" (");
            builder.append(confs.get(i).intValue());
            builder.append(")\n");
        }

        //모든 콜백함수들은 백그라운드에서 돌고 있기 때문에 메인 UI를 변경할려면 runOnUiThread를 사용해야 한다.
        final Activity activity = this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (activity.isFinishing()) return;

                Button button = findViewById(R.id.setting);
                button.setText(texts.get(0));
                if (state_text.equals(SEARCH))
                    state_text = REASK;
                else if (button.getText().equals("응") || button.getText().equals("네") || button.getText().equals("예"))
                    state_text = YES;
                else state_text = NO;
            }
        });
    }

    @Override
    public void onAudioLevel(float audioLevel) {

    }

    @Override
    public void onFinished() {
        int intSentSize = ttsClient.getSentDataSize();
        int intRecvSize = ttsClient.getReceivedDataSize();

        final String strInacctiveText = "onFinished() SentSize : " + intSentSize + " RecvSize : " + intRecvSize;

        Log.i(TAG, strInacctiveText);

        ttsClient = null;
    }

}
