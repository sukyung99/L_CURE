package com.example.l_cure;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SelectSyllableCountActivity extends AppCompatActivity {
    Button back, setting;
    Button one, two, three;
    TextView word;
    private List<Words> word_list; // words 리스트
    private List<Words> new_word_list = new ArrayList<>();;
    private int quizCount = 1;
    String imgName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_syllable_count);

        // words 데이터베이스 load
        initLoadDB();

        for (int i = 0; i < word_list.size(); i++) {
            if (word_list.get(i).getWord().length() < 4) {
                new_word_list.add(word_list.get(i));
            }
        }

        Random random = new Random();
        int index = random.nextInt(new_word_list.size());

        String get_word = new_word_list.get(index).getWord();       // 단어
        imgName = new_word_list.get(index).getImg_name();    // 이미지

        word = (TextView) findViewById(R.id.word);
        word.setText(get_word);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                return;
            }
        });

        one = findViewById(R.id.one);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCorrect(one);
            }
        });
        two = findViewById(R.id.two);
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCorrect(two);
            }
        });
        three = findViewById(R.id.three);
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCorrect(three);
            }
        });

        new_word_list.remove(index);
    }

    private void verifyCorrect(Button one) {
        if (one.getText().equals(Integer.toString(word.getText().length()))) {
            // correct
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
                showNext();
            }
        } else {
            Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
            intent.putExtra("number", 8);
            startActivityForResult(intent,5000);
            // again
        }
    }

    public void showNext(){
        Random random = new Random();
        int index = random.nextInt(new_word_list.size());

        String get_word = new_word_list.get(index).getWord(); // 단어

        word = (TextView) findViewById(R.id.word);
        word.setText(get_word);
        imgName = new_word_list.get(index).getImg_name();
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
