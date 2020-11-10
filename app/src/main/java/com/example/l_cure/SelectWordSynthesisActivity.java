package com.example.l_cure;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class SelectWordSynthesisActivity extends AppCompatActivity {
    Button back, setting;
    Button mic;
    TextView word1, word2;
    private List<Words> word_list; // words 리스트
    private List<Words> new_word_list = new ArrayList<>();
    private int quizCount = 1;
    private Words answer_word;
    private int word_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_word_synthesis);

        word1 = (TextView) findViewById(R.id.word_1);
        word2 = (TextView) findViewById(R.id.word_2);

        // words 데이터베이스 load
        initLoadDB();

        // get word list
        for (int i = 0; i < word_list.size(); i++) {
            if (word_list.get(i).getWord().length() > 1) {
                new_word_list.add(word_list.get(i));
            }
        }

        // 뒤로가기 버튼
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                return;
            }
        });

        // get random word
        random_word();

        // click mic button
        mic = findViewById(R.id.mic);
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            // 음성 인식
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "말하세요");
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 20);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                startActivityForResult(intent, 1);
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


    // 단어 분할하기
    private String[] split_word(String word) {
        String[] split = new String[2];
        int index ;
        if(word.length() == 2) {
            index = 1;
        } else {
            Random random = new Random();
            index = random.nextInt(word.length()-2) + 1;
        }

        split[0] = word.substring(0, index);
        split[1] = word.substring(index);
        return split;
    }

    // 랜덤으로 단어 뽑기
    private void random_word() {
        Random random = new Random();
        int index = random.nextInt(new_word_list.size());
        String word = new_word_list.get(index).getWord(); // 단어

        String[] split_word = split_word(word);
        word1.setText(split_word[0]);
        word2.setText(split_word[1]);
        word_index = index;
        answer_word = new_word_list.get(index);
    }

    private void showNext(){
        quizCount++;
        new_word_list.remove(word_index);
        random_word();
    }

    // 음성 인식 결과
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            ArrayList<String> inputs = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS); // input answer 리스트
            String input ;
            boolean correct = false;
            int chance = 3; // input 후보 개수
            for (int i=0; i<chance; i++) {
                input = inputs.get(i);
                if (input.equals(answer_word.getWord())) {
                    correct = true;
                    break;
                }
            }

            if (correct) {
                // correct answer
                if(quizCount==5) {
                    Intent intent = new Intent(getApplicationContext(), SelectImprovingSkillsActivity.class);
                    startActivityForResult(intent,5000);
                    intent = new Intent(getApplicationContext(), PopupActivity.class);
                    intent.putExtra("number", 7);
                    intent.putExtra("imgName", answer_word.getImg_name());
                    startActivityForResult(intent,5000);
                } else {
                    Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                    intent.putExtra("number", 7);
                    intent.putExtra("imgName", answer_word.getImg_name());
                    startActivityForResult(intent,5000);

                    showNext();
                }
            } else{
                // wrong answer
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                intent.putExtra("number", 8);
                startActivityForResult(intent,5000);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}

