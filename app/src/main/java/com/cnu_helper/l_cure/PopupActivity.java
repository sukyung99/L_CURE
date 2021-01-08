package com.cnu_helper.l_cure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakao.sdk.newtoneapi.SpeechRecognizerManager;
import com.kakao.sdk.newtoneapi.TextToSpeechClient;
import com.kakao.sdk.newtoneapi.TextToSpeechListener;
import com.kakao.sdk.newtoneapi.TextToSpeechManager;

public class PopupActivity extends Activity implements TextToSpeechListener {
    private int number;
    private String speech_text;
    TextView txt;
    String sex, speed, voice;
    private TextToSpeechClient ttsClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //데이터 가져오기
        Intent intent = getIntent();
        number = intent.getIntExtra("number",0);
        sex = intent.getStringExtra("sex");
        speed = intent.getStringExtra("speed");
        voice = intent.getStringExtra("voice");

        SpeechRecognizerManager.getInstance().initializeLibrary(getApplicationContext());
        TextToSpeechManager.getInstance().initializeLibrary(getApplicationContext());

        if(number==1){
            setContentView(R.layout.session01_popup);
        }else if(number==2){
            setContentView(R.layout.session02_popup);
        }else if(number==3){
            setContentView(R.layout.session03_popup);
        }else if(number==4){
            setContentView(R.layout.session04_popup);
        }else if(number==5){
            setContentView(R.layout.session05_popup);
        }else if(number==6){
            setContentView(R.layout.session06_popup);
        }else if(number==7){
            setContentView(R.layout.rightanswer_popup);
            String imgName = intent.getStringExtra("imgName");
            ImageView imgView = (ImageView) findViewById(R.id.word_img);
            int lid = this.getResources().getIdentifier(imgName, "drawable", this.getPackageName());
            imgView.setImageResource(lid);
        }else if(number==8){
            setContentView(R.layout.wronganswer_popup);
        }else if(number==9){
            setContentView(R.layout.knowing_popup);
            String[] test = intent.getStringArrayExtra("test");
            TextView textView = findViewById(R.id.textView2);
            textView.setText("음절 세기 : " + test[0] +
                    "\n\n단어 합성 : " + test[1] +
                    "\n\n음소 분절 : " + test[2] +
                    "\n\n음소 합성 : " + test[3] +
                    "\n\n음소 대치 : " + test[4] +
                    "\n\n음절 변별 : " + test[5] + "\n");
        }

        String voiceType = null;
        if(sex == null){
            voiceType = TextToSpeechClient.VOICE_WOMAN_READ_CALM;
        }
        else if(sex.equals("man")){
            voiceType = TextToSpeechClient.VOICE_MAN_READ_CALM;
        }
        else if(sex.equals("woman")){
            voiceType = TextToSpeechClient.VOICE_WOMAN_READ_CALM;
        }

        double speechSpeed = 0;
        if(speed == null){
            speechSpeed = 0.9D;
        }
        else if(speed.equals("slow")){
            speechSpeed = 0.6D;
        }
        else if(speed.equals("regular")){
            speechSpeed = 0.9D;
        }
        else if(speed.equals("fast")){
            speechSpeed = 1.2D;
        }
        if(voice == null){
            ttsClient = new TextToSpeechClient.Builder()
                    .setSpeechMode(TextToSpeechClient.NEWTONE_TALK_2)     // 음성합성방식
                    .setSpeechSpeed(speechSpeed)
                    .setSpeechVoice(voiceType)
                    .setListener(PopupActivity.this)
                    .build();

            txt = findViewById(R.id.textView2);
            speech_text = txt.getText().toString();
            ttsClient.play(speech_text);
            ttsClient = null;
        }
        else if(voice.equals("off")){
            ttsClient = null;
        }
        else{
            ttsClient = new TextToSpeechClient.Builder()
                    .setSpeechMode(TextToSpeechClient.NEWTONE_TALK_2)     // 음성합성방식
                    .setSpeechSpeed(speechSpeed)
                    .setSpeechVoice(voiceType)
                    .setListener(PopupActivity.this)
                    .build();

            txt = findViewById(R.id.textView2);
            speech_text = txt.getText().toString();
            ttsClient.play(speech_text);
            ttsClient = null;
        }
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        if(number==1){
            onBackPressed();
            Intent intent = new Intent(getApplicationContext(), SelectSyllableCountActivity.class);
            intent.putExtra("result", "Close Popup");
            intent.putExtra("sex", sex);
            intent.putExtra("speed", speed);
            intent.putExtra("voice", voice);
            setResult(RESULT_OK, intent);
            startActivityForResult(intent,5000);
        }
        else if(number==2){
            onBackPressed();
            Intent intent = new Intent(getApplicationContext(), SelectWordSynthesisActivity.class);
            intent.putExtra("result", "Close Popup");
            intent.putExtra("sex", sex);
            intent.putExtra("speed", speed);
            intent.putExtra("voice", voice);
            setResult(RESULT_OK, intent);
            startActivityForResult(intent,5000);
        }
        else if(number==3){
            onBackPressed();
            Intent intent = new Intent(getApplicationContext(), SelectPhonemicSegmentationActivity.class);
            intent.putExtra("result", "Close Popup");
            intent.putExtra("sex", sex);
            intent.putExtra("speed", speed);
            intent.putExtra("voice", voice);
            setResult(RESULT_OK, intent);
            startActivityForResult(intent,5000);
        }
        else if(number==4){
            onBackPressed();
            Intent intent = new Intent(getApplicationContext(), SelectPhonemicSynthesisActivity.class);
            intent.putExtra("result", "Close Popup");
            intent.putExtra("sex", sex);
            intent.putExtra("speed", speed);
            intent.putExtra("voice", voice);
            setResult(RESULT_OK, intent);
            startActivityForResult(intent,5000);
        }
        else if(number==5){
            onBackPressed();
            Intent intent = new Intent(getApplicationContext(), SelectPhonemicSubstitutionActivity.class);
            intent.putExtra("result", "Close Popup");
            intent.putExtra("sex", sex);
            intent.putExtra("speed", speed);
            intent.putExtra("voice", voice);
            setResult(RESULT_OK, intent);
            startActivityForResult(intent,5000);
        }
        else if(number==6){
            onBackPressed();
            Intent intent = new Intent(getApplicationContext(), SelectSyllableDiscriminationActivity.class);
            intent.putExtra("result", "Close Popup");
            intent.putExtra("sex", sex);
            intent.putExtra("speed", speed);
            intent.putExtra("voice", voice);
            setResult(RESULT_OK, intent);
            startActivityForResult(intent,5000);
        }
        else if(number==9){
            onBackPressed();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("result", "Close Popup");
            intent.putExtra("sex", sex);
            intent.putExtra("speed", speed);
            intent.putExtra("voice", voice);
            setResult(RESULT_OK, intent);
            startActivityForResult(intent,5000);
        }
        else {
            onBackPressed();
            return;
        }
    }

    @Override
    public void onFinished() {

    }

    @Override
    public void onError(int code, String message) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TextToSpeechManager.getInstance().finalizeLibrary();
        SpeechRecognizerManager.getInstance().finalizeLibrary();
    }
}
