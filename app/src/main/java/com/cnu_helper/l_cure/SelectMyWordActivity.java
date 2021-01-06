package com.cnu_helper.l_cure;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SelectMyWordActivity extends AppCompatActivity {
    private List<Words> word_list; // words 리스트
    private List<Words> new_word_list  = new ArrayList<>();; // 추출한 words 리스트
    private Words word;
    private TextView tv_my_word, tv_writing_word;
    private Button  btn_speaker, btn_checker;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_my_word);

        tv_my_word = (TextView) findViewById(R.id.my_word);
        tv_writing_word = (TextView) findViewById(R.id.writing_word);
        btn_speaker = (Button) findViewById(R.id.speaker);
        btn_checker = (Button) findViewById(R.id.check);

        // 단어 db 로드
        initLoadDB();

        // 한 글자 단어만 추출
        for (int i = 0; i < word_list.size(); i++) {
            if (word_list.get(i).getWord().length() == 1) {
                new_word_list.add(word_list.get(i));
            }
        }

        // 화면에 word 세팅
        word = new_word_list.get(0);
        tv_my_word.setText(word.getWord());
        tv_writing_word.setText(word.getWord());

        // 이미지 띄우기
        btn_checker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                intent.putExtra("number", 7);
                intent.putExtra("imgName", word.getImg_name());
                startActivityForResult(intent,5000);
            }
        });

        // 단어 들려주기
        btn_speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 코드 추가..
            }
        });

    }

    // 데이터베이스 load
    private void initLoadDB() {
        DataAdapter mDbHelper = new DataAdapter(this.getApplicationContext());
        mDbHelper.createDatabase();
        mDbHelper.open();

        // db에 있는 값들을 model을 적용해서 넣는다.
        word_list = mDbHelper.getTableData();

        // db 닫기
        mDbHelper.close();
    }




}
