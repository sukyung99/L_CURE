package com.cnu_helper.l_cure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SelectSettingsActivity extends AppCompatActivity {
    Button man, woman, slow, regular, fast, on, off, save;
    String sex, speed, voice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_settings);

        man = findViewById(R.id.man);
        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sex = "man";
            }
        });

        woman = findViewById(R.id.woman);
        woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sex = "woman";
            }
        });

        slow = findViewById(R.id.slow);
        slow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speed = "slow";
            }
        });

        regular = findViewById(R.id.regular);
        regular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speed = "regular";
            }
        });

        fast = findViewById(R.id.fast);
        fast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speed = "fast";
            }
        });

        on = findViewById(R.id.on);
        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voice = "on";
            }
        });

        off = findViewById(R.id.off);
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voice = "off";
            }
        });

        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("sex", sex);
                intent.putExtra("speed", speed);
                intent.putExtra("voice", voice);
                startActivityForResult(intent, 5000);
            }
        });
    }
}
