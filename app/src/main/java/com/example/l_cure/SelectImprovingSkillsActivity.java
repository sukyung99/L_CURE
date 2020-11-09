package com.example.l_cure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectImprovingSkillsActivity extends AppCompatActivity {
    Button back, setting;
    Button syllable_count, word_synthesis, phonemic_fractionation;
    Button phonemic_synthesis, phonemic_substitution, syllabic_discrimination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_improving_skills);

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
//                Intent intent = new Intent(getApplicationContext(), SelectSyllableCountActivity.class);
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                startActivityForResult(intent,5000);
            }
        });
    }
}
