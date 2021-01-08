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

public class SelectPhonemicSubstitutionActivity extends AppCompatActivity {
    Button back;
    Button mic;
    TextView tv_word, tv_origin_p, tv_new_p;
    private List<Words> word_list; // words 리스트
    private List<Words> new_word_list = new ArrayList<>();
    private int quizCount = 1;
    private Words answer_word;
    private int word_index;
    private String prev_word, test;
    String sex, speed, voice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_phonemic_substitution);

        Intent intent =  getIntent();
        test = intent.getStringExtra("test");
        sex = intent.getStringExtra("sex");
        speed = intent.getStringExtra("speed");
        voice = intent.getStringExtra("voice");

        tv_word = (TextView) findViewById(R.id.word);
        tv_origin_p = (TextView) findViewById(R.id.origin_phonemic);
        tv_new_p = (TextView) findViewById(R.id.new_phonemic);
        mic = (Button) findViewById(R.id.mic);

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
        randomWord();

        // click board button
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

    // 랜덤으로 단어 뽑기
    private void randomWord() {
        tv_word.setText("");
        tv_origin_p.setText("");
        tv_new_p.setText("");

        Random random = new Random();
        int index = random.nextInt(new_word_list.size());
        word_index = index;
        String word = new_word_list.get(index).getWord(); // 단어
        answer_word = new_word_list.get(index);

        // 초성, 중성, 종성 중 하나 변환
        String jaso[] = changeOneJaso(word).split("");

        // set textview
        tv_word.setText(prev_word);
        tv_origin_p.setText(jaso[0]);
        tv_new_p.setText(jaso[1]);
    }

    /*
    param: word
    return:  not changing jaso, changing jaso
     */
    private String changeOneJaso(String s) {
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
        char origin_jaso = ' ';
        char new_jaso = ' ';
        int change = -1;
        char ch = s.charAt(0);

        jong = ch - 0xAC00;
        cho = jong / (21 * 28);
        jong = jong % (21 * 28);
        jwung = jong / 28;
        jong = jong % 28;

        // 초성, 중성, 종성 중 무엇을 바꿀 것인지 정함
        Random random = new Random();
        if (jong == 0) change = random.nextInt(2);
        else  change = random.nextInt(3); // change - 0: chosung, 1: jwungsung, 2: jongsung

        int new_cho = cho;
        int new_jwung = jwung;
        int new_jong = jong;

        if (change == 0) {
            // 초성 변환
            do {
                random = new Random();
                new_cho = random.nextInt(ChoSung.length);
            } while (new_cho == cho || ChoSung[new_cho] == JongSung[jong]) ;
            origin_jaso = ChoSung[cho];
            new_jaso = ChoSung[new_cho];
        } else if (change == 1) {
            // 중성 변환
            do {
                random = new Random();
                new_jwung = random.nextInt(JwungSung.length);
            } while (new_jwung == jwung) ;
            origin_jaso = JwungSung[jwung];
            new_jaso = JwungSung[new_jwung];
        }

        if (jong != 0) { // c가 0이 아니면, 즉 받침이 있으면
            if (change == 2) {
                // 종성 변환
                do {
                    random = new Random();
                    new_jong = random.nextInt(JongSung.length-1) + 1;
                } while (new_jong == jong || JongSung[new_jong] == ChoSung[cho]);
                origin_jaso = JongSung[jong];
                new_jaso = JongSung[new_jong];
            }
        }

        this.prev_word = "" + combineJaso(new_cho, new_jwung, new_jong);

        return "" + new_jaso + origin_jaso;
    }

    // 초성, 중성, 종성 합치는 함수
    private char combineJaso(int chosung, int jwungsung, int jongsung) {
        int x = (chosung * 21 * 28) + (jwungsung * 28) + jongsung;
        return (char) (x + 0xAC00);
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
                    correct = true;
                    break;
                }
            }

            // 실력 알아보기 아닌 경우
            if (test == null || !test.split("_")[0].equals("test")) {
                if (correct) {
                    // correct answer
                    if(quizCount==5) {
                        Intent intent = new Intent(getApplicationContext(), SelectImprovingSkillsActivity.class);
                        startActivityForResult(intent,5000);
                        intent = new Intent(getApplicationContext(), PopupActivity.class);
                        intent.putExtra("number", 7);
                        intent.putExtra("imgName", answer_word.getImg_name());
                        intent.putExtra("sex", sex);
                        intent.putExtra("speed", speed);
                        intent.putExtra("voice", voice);
                        startActivityForResult(intent,5000);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                        intent.putExtra("number", 7);
                        intent.putExtra("imgName", answer_word.getImg_name());
                        intent.putExtra("sex", sex);
                        intent.putExtra("speed", speed);
                        intent.putExtra("voice", voice);
                        startActivityForResult(intent,5000);

                        showNext();
                    }
                } else{
                    // wrong answer
                    Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                    intent.putExtra("number", 8);
                    intent.putExtra("sex", sex);
                    intent.putExtra("speed", speed);
                    intent.putExtra("voice", voice);
                    startActivityForResult(intent,5000);
                }
            }
            else if (test.split("_")[0].equals("test")) {
                if (correct) test = test + "o";
                else test = test + "x";

                Intent intent = new Intent(getApplicationContext(), SelectSyllableDiscriminationActivity.class);
                intent.putExtra("test", test);
                intent.putExtra("sex", sex);
                intent.putExtra("speed", speed);
                intent.putExtra("voice", voice);
                startActivityForResult(intent, 5000);
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showNext(){
        quizCount++;
        new_word_list.remove(word_index);
        randomWord();
    }
}
