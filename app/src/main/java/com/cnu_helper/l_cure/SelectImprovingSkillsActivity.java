package com.cnu_helper.l_cure;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SelectImprovingSkillsActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private Button back, setting;
    private Button syllable_count, word_synthesis, phonemic_fractionation;
    private Button phonemic_synthesis, phonemic_substitution, syllabic_discrimination;
    private TextToSpeech speech;
    private String notification = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_improving_skills);
        speech = new TextToSpeech(this,this);

        speech.setLanguage(Locale.KOREAN);
        speech.setPitch(0.6f);
        speech.setSpeechRate(0.95f);
        speech.speak(notification, TextToSpeech.QUEUE_FLUSH, null);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                return;
            }
        });

        syllable_count = findViewById(R.id.syllable_count);
        syllable_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                intent.putExtra("number", 1);
                startActivityForResult(intent,5000);
            }
        });

        word_synthesis = findViewById(R.id.word_synthesis);
        word_synthesis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                intent.putExtra("number", 2);
                startActivityForResult(intent,5000);
            }
        });

        phonemic_fractionation = findViewById(R.id.phonemic_fractionation);
        phonemic_fractionation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                intent.putExtra("number", 3);
                startActivityForResult(intent,5000);
            }
        });

        phonemic_synthesis = findViewById(R.id.phonemic_synthesis);
        phonemic_synthesis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                intent.putExtra("number", 4);
                startActivityForResult(intent,5000);
            }
        });

        phonemic_substitution = findViewById(R.id.phonemic_substitution);
        phonemic_substitution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                intent.putExtra("number", 5);
                startActivityForResult(intent,5000);
            }
        });

        syllabic_discrimination = findViewById(R.id.syllabic_discrimination);
        syllabic_discrimination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                intent.putExtra("number", 6);
                startActivityForResult(intent,5000);
            }
        });
    }

    @Override
    public void onInit(int i) {
        speech.setLanguage(Locale.KOREAN);
        speech.setPitch(0.6f);
        speech.setSpeechRate(0.95f);
    }
}
