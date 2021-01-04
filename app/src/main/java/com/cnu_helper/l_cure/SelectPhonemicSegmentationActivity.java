package com.cnu_helper.l_cure;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class SelectPhonemicSegmentationActivity extends AppCompatActivity {
    Button back, setting;
    Button tv_chosung, tv_jwungsung, tv_jongsung, f_chosung, f_jwungsung, f_jongsung;
    TextView tv_word, asw_chosung, asw_jwungsung, asw_jongsung;
    private int quizCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_phonemic_segmentation);

        Random random = new Random();
        boolean set1 = random.nextBoolean();
        boolean set2 = random.nextBoolean();
        boolean set3 = random.nextBoolean();

        asw_chosung = (TextView) findViewById(R.id.chosung);
        asw_jwungsung = (TextView) findViewById(R.id.jwungsung);
        asw_jongsung = (TextView) findViewById(R.id.jongsung);

        if (set1) {
            tv_chosung = (Button) findViewById(R.id.one);
            f_chosung = (Button) findViewById(R.id.four);
        }
        else {
            tv_chosung = (Button) findViewById(R.id.four);
            f_chosung = (Button) findViewById(R.id.one);
        }

        if (set2) {
            tv_jwungsung = (Button) findViewById(R.id.two);
            f_jwungsung = (Button) findViewById(R.id.five);
        }
        else {
            tv_jwungsung = (Button) findViewById(R.id.five);
            f_jwungsung = (Button) findViewById(R.id.two);
        }

        if (set3) {
            tv_jongsung = (Button) findViewById(R.id.three);
            f_jongsung = (Button) findViewById(R.id.six);
        }
        else {
            tv_jongsung = (Button) findViewById(R.id.six);
            f_jongsung = (Button) findViewById(R.id.three);
        }

        tv_chosung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectButton(tv_chosung);
            }
        });

        f_chosung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectButton(f_chosung);
            }
        });

        tv_jwungsung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectButton(tv_jwungsung);
            }
        });

        f_jwungsung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectButton(f_jwungsung);
            }
        });

        tv_jongsung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectButton(tv_jongsung);
            }
        });

        f_jongsung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectButton(f_jongsung);
            }
        });

        tv_word = (TextView) findViewById(R.id.word);

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
    }

    // 랜덤으로 단어 뽑기
    private void random_word() {

        String blank = "";
        tv_word.setText("");
        tv_chosung.setText("");
        tv_jwungsung.setText("");
        tv_jongsung.setText("");

        // ㄱ    ㄲ   ㄴ   ㄷ   ㄸ   ㄹ
        // ㅁ    ㅂ   ㅃ   ㅅ   ㅆ   ㅇ
        // ㅈ    ㅉ   ㅊ   ㅋ   ㅌ   ㅍ   ㅎ
        char[] ChoSung   = { 0x3131, 0x3132, 0x3134, 0x3137, 0x3138, 0x3139,
                0x3141, 0x3142, 0x3143, 0x3145, 0x3146, 0x3147,
                0x3148, 0x3149, 0x314a, 0x314b, 0x314c, 0x314d, 0x314e };

        // ㅏ    ㅐ   ㅑ   ㅓ   ㅔ
        // ㅕ    ㅗ   ㅛ   ㅜ   ㅠ   ㅡ   ㅣ

        char[] JwungSung = { 0x314f, 0x3150, 0x3151, 0x3153, 0x3154,
                0x3155, 0x3157, 0x315b, 0x315c, 0x3160, 0x3161, 0x3163 };

        // ㄱ    ㄴ   ㄷ   ㄹ
        // ㅁ    ㅂ   ㅅ   ㅇ
        char[] JongSung  = {0x3131, 0x3134, 0x3137, 0x3139,
                0x3141, 0x3142, 0x3145, 0x3147 };
        // 1    4   7   8
        // 16   17  19  21

        Random random = new Random();
        int r1 = random.nextInt(ChoSung.length);
        char c1 = ChoSung[r1];
        char c2 = JwungSung[random.nextInt(JwungSung.length)];
        int r3 = random.nextInt(JongSung.length);
        char c3 = JongSung[r3];

        char f1 = ChoSung[random.nextInt(ChoSung.length)];
        char f2 = JwungSung[random.nextInt(JwungSung.length)];
        char f3 = JongSung[random.nextInt(JongSung.length)];

        while (f1 == c1)
            f1 = ChoSung[random.nextInt(ChoSung.length)];

        while (f2 == c2)
            f2 = JwungSung[random.nextInt(JwungSung.length)];

        while (f3 == c3)
            f3 = JongSung[random.nextInt(JongSung.length)];

        r3 = JongsungSwitch(r3);

        tv_chosung.setText(blank + c1);
        tv_jwungsung.setText(blank + c2);
        tv_jongsung.setText(blank + c3);
        f_chosung.setText(blank + f1);
        f_jwungsung.setText(blank + f2);
        f_jongsung.setText(blank + f3);
        tv_word.setText(blank + combine(r1, (int)c2, r3));
    }

    // 종성 Tagging
    private int JongsungSwitch(int num) {
        switch (num) {
            case 0: return 1;
            case 1: return 4;
            case 2: return 7;
            case 3: return 8;
            case 4: return 16;
            case 5: return 17;
            case 6: return 19;
            case 7: return 21;
            default:
                return 0;
        }
    }

    // 자모 합성
    private static char combine(int x1, int x2, int x3) {
        x2 = x2 - 0x314f;
        int x = (x1 * 21 * 28) + (x2 * 28) + x3;
        return (char)(x + 0xAC00);
    }

    private void selectButton(Button btn) {
        if (btn == tv_chosung || btn == f_chosung)
            asw_chosung.setText(btn.getText());
        else if (btn == tv_jwungsung || btn == f_jwungsung)
            asw_jwungsung.setText(btn.getText());
        else
            asw_jongsung.setText(btn.getText());
    }

    private void showNext(){
        quizCount++;
        random_word();
    }
}
