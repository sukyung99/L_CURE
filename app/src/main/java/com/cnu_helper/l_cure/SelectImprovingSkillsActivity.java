package com.cnu_helper.l_cure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SelectImprovingSkillsActivity extends AppCompatActivity {
    private Button back;
    private Button syllable_count, word_synthesis, phonemic_fractionation;
    private Button phonemic_synthesis, phonemic_substitution, syllabic_discrimination;
    String sex, speed, voice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_improving_skills);

        Intent intent = getIntent();
        sex = intent.getStringExtra("sex");
        speed = intent.getStringExtra("speed");
        voice = intent.getStringExtra("voice");

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
                intent.putExtra("sex", sex);
                intent.putExtra("speed", speed);
                intent.putExtra("voice", voice);
                startActivityForResult(intent,5000);
            }
        });

        word_synthesis = findViewById(R.id.word_synthesis);
        word_synthesis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                intent.putExtra("number", 2);
                intent.putExtra("sex", sex);
                intent.putExtra("speed", speed);
                intent.putExtra("voice", voice);
                startActivityForResult(intent,5000);
            }
        });

        phonemic_fractionation = findViewById(R.id.phonemic_fractionation);
        phonemic_fractionation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                intent.putExtra("number", 3);
                intent.putExtra("sex", sex);
                intent.putExtra("speed", speed);
                intent.putExtra("voice", voice);
                startActivityForResult(intent,5000);
            }
        });

        phonemic_synthesis = findViewById(R.id.phonemic_synthesis);
        phonemic_synthesis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                intent.putExtra("number", 4);
                intent.putExtra("sex", sex);
                intent.putExtra("speed", speed);
                intent.putExtra("voice", voice);
                startActivityForResult(intent,5000);
            }
        });

        phonemic_substitution = findViewById(R.id.phonemic_substitution);
        phonemic_substitution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                intent.putExtra("number", 5);
                intent.putExtra("sex", sex);
                intent.putExtra("speed", speed);
                intent.putExtra("voice", voice);
                startActivityForResult(intent,5000);
            }
        });

        syllabic_discrimination = findViewById(R.id.syllabic_discrimination);
        syllabic_discrimination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                intent.putExtra("number", 6);
                intent.putExtra("sex", sex);
                intent.putExtra("speed", speed);
                intent.putExtra("voice", voice);
                startActivityForResult(intent,5000);
            }
        });
    }
}
