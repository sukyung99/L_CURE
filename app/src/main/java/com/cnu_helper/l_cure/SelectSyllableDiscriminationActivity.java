package com.cnu_helper.l_cure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SelectSyllableDiscriminationActivity extends AppCompatActivity {
    Button back, one, two, three, speaker;
    private int quizCount = 1;
    private List<Words> word_list; // words 리스트
    private String imgName;
    private String get_word, wrong_word_1, wrong_word_2;
    private int var = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_syllable_discrimination);

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
    }

    private void makeQuestion() {

        Random random = new Random();
        int index = random.nextInt(word_list.size());
        get_word = word_list.get(index).getWord();       // 단어
        imgName = word_list.get(index).getImg_name();    // 이미지

        // 첫 글자 저장
        String question = Character.toString(get_word.charAt(0));

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
                if (quizCount == 5) {
                    Intent intent = new Intent(getApplicationContext(), SelectImprovingSkillsActivity.class);
                    startActivityForResult(intent,5000);
                    intent = new Intent(getApplicationContext(), PopupActivity.class);
                    intent.putExtra("number", 7);
                    intent.putExtra("imgName", imgName);
                    startActivityForResult(intent,5000);

                } else {
                    quizCount++;
                    Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                    intent.putExtra("number", 7);
                    intent.putExtra("imgName", imgName);
                    startActivityForResult(intent,5000);
                    makeQuestion();
                }
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                intent.putExtra("number", 8);
                startActivityForResult(intent,5000);
                // again
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                intent.putExtra("number", 8);
                startActivityForResult(intent,5000);
                // again
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
}
