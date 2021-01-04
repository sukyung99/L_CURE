package com.cnu_helper.l_cure;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class SelectMyWordActivity extends AppCompatActivity {
    private List<Words> word_list; // words 리스트
    private Words word;

    private Button tv_firstword, tv_secondword, tv_thirdword;
    private TextView tv_writing_word;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_select_my_word);

        // 단어 db 로드
        initLoadDB();

        tv_firstword = (Button) findViewById(R.id.first_word);
        tv_secondword = (Button) findViewById(R.id.second_word);
        tv_thirdword = (Button) findViewById(R.id.third_word);
        tv_writing_word = (TextView) findViewById(R.id.writing_word);

        word = word_list.get(9); // TEST] word=가게

//        tv_writing_word.setText(word.getWord());

        WritingView view = new WritingView(this);
        setContentView(view);

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
