package com.example.l_cure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    Button learn_skills, improving_skills, my_own_words;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        learn_skills = findViewById(R.id.learn_skills);
        learn_skills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), SelectImprovingSkillsActivity.class);
//                startActivityForResult(intent,5000);
            }
        });

        improving_skills = findViewById(R.id.improving_skills);
        improving_skills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectImprovingSkillsActivity.class);
                startActivityForResult(intent, 5000);
            }
        });

        my_own_words = findViewById(R.id.my_own_words);
        my_own_words.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), SelectImprovingSkillsActivity.class);
//                startActivityForResult(intent,5000);
            }
        });
    }

}
