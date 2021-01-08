package com.cnu_helper.l_cure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectMyWordActivity extends AppCompatActivity implements ViewStub.OnClickListener{
    private Words word;
    private TextView tv_my_word, tv_writing_word;
    private Button  back, btn_speaker, btn_photo, btn_save;

    private WritingView writingview;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_my_word);

        word = (Words) getIntent().getSerializableExtra("my_word");

        tv_my_word = (TextView) findViewById(R.id.my_word);
        tv_writing_word = (TextView) findViewById(R.id.writing_word);
        btn_speaker = (Button) findViewById(R.id.speaker);
        btn_photo = (Button) findViewById(R.id.show_photo);

        // 뒤로가기 버튼
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                return;
            }
        });

        // 화면에 word 세팅
        tv_my_word.setText(word.getWord());
        tv_writing_word.setText(word.getWord());

        // 이미지 띄우기
        btn_photo.setOnClickListener(new View.OnClickListener() {
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

        writingview = (WritingView) findViewById(R.id.writing_view);
        btn_save = (Button) findViewById(R.id.save);
        btn_save.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                writingview.save(SelectMyWordActivity.this);
                break;

        }
    }
}
