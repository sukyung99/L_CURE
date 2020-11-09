package com.example.l_cure;

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
import java.util.Random;

public class SelectWordSynthesisActivity extends AppCompatActivity {
    Button back, setting;
    Button mic;
    TextView word1, word2;
    private List<Words> word_list; // words 리스트
    private List<Words> new_word_list;
    private int quizCount = 1;
    private String answer_word;
    private int word_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_word_synthesis);

        // words 데이터베이스 load
        initLoadDB();

        // get word list
        for (int i = 0; i < word_list.size(); i++) {
            if (word_list.get(i).getWord().length() < 4) {
                new_word_list = word_list;
            }
        }

        // get random word
        random_word();

        // 뒤로가기 버튼
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                return;
            }
        });

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

    // 음성 인식 결과
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            ArrayList<String> inputs = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS); // answer 리스트
            int chance = 2;
            for (int i=0; i<chance; i++) {
                String input = inputs.get(i);
                if (input.equals(answer_word)) {
                    // correct

                    if(quizCount==5){

                    } else {
                        quizCount++;
                        new_word_list.remove(word_index);
                        showNext();
                    }
                    break;
                } else{
                    // again

                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showNext(){
        random_word();
    }

    /*
    func: 문자열 자르기
    param: word(단어)
    return: 분리된 단어 배열
     */
    private String[] split_word(String word) {
        String[] split = new String[2];

        Random random = new Random();
        int split_index = random.nextInt(word.length());
        while( (split_index < 1) || (split_index >= word.length()-1) ) {
            split_index = random.nextInt(word.length());
        }

        split[0] = word.substring(0, split_index);
        split[1] = word.substring(split_index);
        return split;
    }

    private void random_word() {
        Random random = new Random();
        int index = random.nextInt(new_word_list.size());
        String get_word = new_word_list.get(index).getWord(); // 단어
        while(get_word.length() < 2) { // 길이 1 이하의 단어는 제외시킴.
            get_word = new_word_list.get(index).getWord();
        }

        word1 = (TextView) findViewById(R.id.word_1);
        word2 = (TextView) findViewById(R.id.word_2);
        String[] split_word = split_word(get_word);
        word1.setText(split_word[0]);
        word2.setText(split_word[1]);
        answer_word = get_word;

        this.word_index = index;
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

