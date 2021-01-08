package com.cnu_helper.l_cure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.security.MessageDigest;


public class MainActivity extends AppCompatActivity {
    private Button learn_skills, improving_skills, my_own_words;
    private String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getHashKey(); // 키해시 찾기 (kakao api)

        learn_skills = findViewById(R.id.learn_skills);
        learn_skills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectSyllableCountActivity.class);
                test = "test";
                intent.putExtra("test", test);
                startActivityForResult(intent,5000);
            }
        });

        improving_skills = findViewById(R.id.improving_skills);
        improving_skills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectImprovingSkillsActivity.class);
                test = "no";
                intent.putExtra("test", test);
                startActivityForResult(intent, 5000);
            }
        });

        my_own_words = findViewById(R.id.my_own_words);
        my_own_words.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SelectMyWordStageActivity.class);
                test = "no";
                intent.putExtra("test", test);
                startActivityForResult(intent,5000);
            }
        });
    }

    private void getHashKey(){
        try{
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String key = new String(Base64.encode(md.digest(), 0));
                Log.d("Hash key:", "!!!!!!!"+key+"!!!!!!"); // print hash key
            }
        } catch (Exception e){
            Log.e("name not found", e.toString());
        }
    }

}
