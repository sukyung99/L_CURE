package com.cnu_helper.l_cure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.sdk.newtoneapi.SpeechRecognizerManager;
import com.kakao.sdk.newtoneapi.TextToSpeechClient;
import com.kakao.sdk.newtoneapi.TextToSpeechListener;
import com.kakao.sdk.newtoneapi.TextToSpeechManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SelectSyllableDiscriminationActivity extends AppCompatActivity implements TextToSpeechListener{
    Button back, one, two, three, speaker;
    private int quizCount = 1;
    private List<Words> word_list; // words 리스트
    private String imgName;
    private String get_word, wrong_word_1, wrong_word_2;
    private int var = 0;
    private String question, test;
    String sex, speed, voice;
    private TextToSpeechClient ttsClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_syllable_discrimination);

        Intent intent =  getIntent();
        test = intent.getStringExtra("test");
        sex = intent.getStringExtra("sex");
        speed = intent.getStringExtra("speed");
        voice = intent.getStringExtra("voice");

        // 도움말 버튼
        Button btn_information = (Button) findViewById(R.id.information);
        btn_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ImprovingStartActivity.class);
                startActivity(intent);
            }
        });

        // words 데이터베이스 load
        initLoadDB();
        makeQuestion();

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                return;
            }
        });

        speaker = findViewById(R.id.speaker);
        speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ttsClient = new TextToSpeechClient.Builder()
                        .setSpeechMode(TextToSpeechClient.NEWTONE_TALK_2)     // 음성합성방식
                        .setSpeechSpeed(0.9D)
                        .setSpeechVoice(TextToSpeechClient.VOICE_WOMAN_READ_CALM)
                        .setListener(SelectSyllableDiscriminationActivity.this)
                        .build();
                ttsClient.play(question);
                ttsClient = null;
                return;
            }
        });
    }

    private void makeQuestion() {

        Random random = new Random();
        int index = random.nextInt(word_list.size());
        get_word = word_list.get(index).getWord();       // 단어
        imgName = word_list.get(index).getImg_name();    // 이미지

        // 첫 글자 저장
        question = Character.toString(get_word.charAt(0));

        do {
            var ++;
            wrong_word_1 = word_list.get((index + var) % word_list.size()).getWord();
        } while (wrong_word_1.charAt(0) == get_word.charAt(0));

        do {
            var ++;
            wrong_word_2 = word_list.get((index + var) % word_list.size()).getWord();
        } while (wrong_word_2.charAt(0) == get_word.charAt(0) || wrong_word_2.charAt(0) == wrong_word_1.charAt(0));

        var = random.nextInt(3);

        switch (var) {
            case 0:
                one = findViewById(R.id.word_1);
                one.setText(get_word);
                two = findViewById(R.id.word_2);
                two.setText(wrong_word_1);
                three = findViewById(R.id.word_3);
                three.setText(wrong_word_2);
                break;
            case 1:
                one = findViewById(R.id.word_2);
                one.setText(get_word);
                two = findViewById(R.id.word_1);
                two.setText(wrong_word_1);
                three = findViewById(R.id.word_3);
                three.setText(wrong_word_2);
                break;
            case 2:
                one = findViewById(R.id.word_3);
                one.setText(get_word);
                two = findViewById(R.id.word_1);
                two.setText(wrong_word_1);
                three = findViewById(R.id.word_2);
                three.setText(wrong_word_2);
                break;
        }

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (test == null || !test.split("_")[0].equals("test")) {
                    if (quizCount == 5) {
                        Intent intent = new Intent(getApplicationContext(), SelectImprovingSkillsActivity.class);
                        startActivityForResult(intent,5000);
                        intent = new Intent(getApplicationContext(), PopupActivity.class);
                        intent.putExtra("number", 7);
                        intent.putExtra("imgName", imgName);
                        sex = intent.getStringExtra("sex");
                        speed = intent.getStringExtra("speed");
                        voice = intent.getStringExtra("voice");
                        startActivityForResult(intent,5000);
                    } else {
                        quizCount++;
                        Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                        intent.putExtra("number", 7);
                        intent.putExtra("imgName", imgName);
                        sex = intent.getStringExtra("sex");
                        speed = intent.getStringExtra("speed");
                        voice = intent.getStringExtra("voice");
                        startActivityForResult(intent,5000);
                        makeQuestion();
                    }
                }
                else if (test.split("_")[0].equals("test")) {
                    testStageResult("o");
                }
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (test == null || !test.split("_")[0].equals("test")) {
                    Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                    intent.putExtra("number", 8);
                    sex = intent.getStringExtra("sex");
                    speed = intent.getStringExtra("speed");
                    voice = intent.getStringExtra("voice");
                    startActivityForResult(intent,5000);
                    // again
                }
                else if (test.split("_")[0].equals("test")) {
                    testStageResult("x");
                }
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (test == null || !test.split("_")[0].equals("test")) {
                    Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                    intent.putExtra("number", 8);
                    sex = intent.getStringExtra("sex");
                    speed = intent.getStringExtra("speed");
                    voice = intent.getStringExtra("voice");
                    startActivityForResult(intent,5000);
                    // again
                }
                else if (test.split("_")[0].equals("test")) {
                    testStageResult("x");
                }
            }
        });

    }

    private void initLoadDB() {
        DataAdapter mDbHelper = new DataAdapter(this.getApplicationContext());
        mDbHelper.createDatabase();
        mDbHelper.open();

        // db에 있는 값들을 model을 적용해서 넣는다.
        word_list = mDbHelper.getTableData();

        // db 닫기
        mDbHelper.close();
    }

    private void testStageResult (String this_res) {
        test = test + this_res;
        String[] result = test.split("_")[1].split(""); // 여기가 실력알아보기 결과!!! (예시: ooxxox)
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent,5000);

        intent = new Intent(getApplicationContext(), PopupActivity.class);
        intent.putExtra("test", result);
        intent.putExtra("number", 9);
        startActivityForResult(intent,5000);
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
