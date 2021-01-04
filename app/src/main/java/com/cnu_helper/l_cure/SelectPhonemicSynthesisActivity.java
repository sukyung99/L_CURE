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
import java.util.Random;

public class SelectPhonemicSynthesisActivity extends AppCompatActivity {
    Button back, setting;
    Button board;
    TextView tv_chosung, tv_jwungsung, tv_jongsung, tv_word;
    private List<Words> word_list; // words 리스트
    private List<Words> new_word_list = new ArrayList<>();
    private int quizCount = 1;
    private Words answer_word;
    private int word_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_phonemic_synthesis);

        tv_chosung = (TextView) findViewById(R.id.chosung);
        tv_jwungsung = (TextView) findViewById(R.id.jwungsung);
        tv_jongsung = (TextView) findViewById(R.id.jongsung);
        tv_word = (TextView) findViewById(R.id.word);
        board = (Button) findViewById(R.id.board);

        // words 데이터베이스 load
        initLoadDB();

        // 한 글자 단어만 추출
        for (int i = 0; i < word_list.size(); i++) {
            if (word_list.get(i).getWord().length() == 1) {
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

        // click board button
        board.setOnClickListener(new View.OnClickListener() {
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

    // 랜덤으로 단어 뽑기
    private void random_word() {
        tv_word.setText("");
        tv_chosung.setText("");
        tv_jwungsung.setText("");
        tv_jongsung.setText("");

        Random random = new Random();
        int index = random.nextInt(new_word_list.size());
        word_index = index;
        String word = new_word_list.get(index).getWord(); // 단어
        answer_word = new_word_list.get(index);

        // 초성, 중성, 종성 분리
        String jaso[] = hangulToJaso(word).split("");

        // set textview
        tv_chosung.setText(jaso[0]);
        tv_jwungsung.setText(jaso[1]);
        if(jaso.length > 2) tv_jongsung.setText(jaso[2]);

//        // FOR TEST //
//        tv_word.setText(answer_word.getWord());
    }

    /*
    param: word
    return: 초성 중성 종성 (예: 감 -> ㄱㅏㅁ)
     */
    private static String hangulToJaso(String s) {
        // ㄱ             ㄲ            ㄴ               ㄷ            ㄸ             ㄹ
        // ㅁ             ㅂ            ㅃ               ㅅ            ㅆ             ㅇ
        // ㅈ             ㅉ           ㅊ                ㅋ            ㅌ              ㅍ      ㅎ
        char[] ChoSung   = { 0x3131, 0x3132, 0x3134, 0x3137, 0x3138, 0x3139,
                0x3141, 0x3142, 0x3143, 0x3145, 0x3146, 0x3147,
                0x3148, 0x3149, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };


        // ㅏ            ㅐ             ㅑ             ㅒ            ㅓ             ㅔ
        // ㅕ            ㅖ              ㅗ           ㅘ            ㅙ              ㅚ
        // ㅛ            ㅜ              ㅝ           ㅞ             ㅟ             ㅠ
        // ㅡ           ㅢ              ㅣ
        char[] JwungSung = { 0x314f, 0x3150, 0x3151, 0x3152, 0x3153, 0x3154,
                0x3155, 0x3156, 0x3157, 0x3158, 0x3159, 0x315a,
                0x315b, 0x315c, 0x315d, 0x315e, 0x315f, 0x3160,
                0x3161, 0x3162, 0x3163 };

        //         ㄱ            ㄲ             ㄳ            ㄴ              ㄵ
        // ㄶ             ㄷ            ㄹ             ㄺ            ㄻ              ㄼ
        // ㄽ             ㄾ            ㄿ              ㅀ            ㅁ             ㅂ
        // ㅄ            ㅅ             ㅆ             ㅇ            ㅈ             ㅊ
        // ㅋ            ㅌ            ㅍ              ㅎ
        char[] JongSung  = { 0,      0x3131, 0x3132, 0x3133, 0x3134, 0x3135,
                0x3136, 0x3137, 0x3139, 0x313a, 0x313b, 0x313c,
                0x313d, 0x313e, 0x313f, 0x3140, 0x3141, 0x3142,
                0x3144, 0x3145, 0x3146, 0x3147, 0x3148, 0x314a,
                0x314b, 0x314c, 0x314d, 0x314e };

        int cho, jwung, jong; // 자소 버퍼: 초성/중성/종성 순
        String result = "";
        char ch = s.charAt(0);

        if (ch >= 0xAC00 && ch <= 0xD7A3) { // "AC00:가" ~ "D7A3:힣" 에 속한 글자면 분해
            jong = ch - 0xAC00;
            cho = jong / (21 * 28);
            jong = jong % (21 * 28);
            jwung = jong / 28;
            jong = jong % 28;

            result = result + ChoSung[cho] + JwungSung[jwung];
            if (jong != 0) result = result + JongSung[jong] ; // c가 0이 아니면, 즉 받침이 있으면
        } else {
            result = result + ch;
        }

        return result;
    }

    // 음성 인식 결과
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            ArrayList<String> inputs = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS); // input answer 리스트
            String input = "";
            boolean correct = false;
            int chance = 3; // input 후보 개수
            for (int i=0; i<chance; i++) {
                if (i > inputs.size() - 1) break;
                input = inputs.get(i);
                if (input.equals(answer_word.getWord())) {
                    tv_word.setText(input);
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
                tv_word.setText(inputs.get(0));
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                intent.putExtra("number", 8);
                startActivityForResult(intent,5000);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showNext(){
        quizCount++;
        new_word_list.remove(word_index);
        random_word();
    }
}
