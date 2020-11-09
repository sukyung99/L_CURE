package com.example.l_cure;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Random;

public class SelectSyllableCountActivity extends AppCompatActivity {
    Button back, setting;
    Button one, two, three;
    TextView word;
    private List<Words> word_list; // words 리스트
    private List<Words> new_word_list;
    private int quizCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_syllable_count);

        // words 데이터베이스 load
        initLoadDB();

        for (int i = 0; i < word_list.size(); i++) {
            if (word_list.get(i).getWord().length() < 4) {
                new_word_list = word_list;
            }
        }

        Random random = new Random();
        int index = random.nextInt(new_word_list.size());

        String get_word = new_word_list.get(index).getWord(); // 단어

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

            } else {
                quizCount++;
                showNext();
            }
        } else {
            // again

        }
    }

    public void showNext(){
        Random random = new Random();
        int index = random.nextInt(new_word_list.size());

        String get_word = new_word_list.get(index).getWord(); // 단어

        word = (TextView) findViewById(R.id.word);
        word.setText(get_word);
    }

//    @Override
//    public void onClick(View view) {
//        switch(view.getId()){
//            case R.id.back:
//                onBackPressed();
//                return;
//        }
//
//        one = findViewById(R.id.one);
//        if(one.getText().equals(word.getText().length())){
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("정답입니다!");
//            builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    // 정답 사진 다이어로그 띄우기
//                    android.app.AlertDialog.Builder inputBuilder = new android.app.AlertDialog.Builder(SelectSyllableCountActivity.this);
//                    inputBuilder.setTitle("사진으로 보는 정답");
//
////                    final ImageView iv = new ImageView(CountNumActivity.this);
////                    iv.setImageResource(rightImage); // 사진 소스는 여기!!!!
////                    inputBuilder.setView(iv);
//
//                    inputBuilder.setPositiveButton("다음으로 넘어가기", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            if(quizCount==5){
////                                showResult();
//                            }
//                            else{
//                                quizCount++;
//                                showNext();
//                            }
//                        }
//                    });
//
//                    inputBuilder.show();
//                }
//            });
//            builder.show();
//        }
//    }

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