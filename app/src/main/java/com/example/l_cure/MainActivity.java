package com.example.l_cure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Words> word_list; // words 리스트
    TextView txtResult;

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
                startActivityForResult(intent,5000);
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

        txtResult = (TextView)findViewById(R.id.txtResult);

        // words 데이터베이스 load
        initLoadDB();

//        // word 출력
//        int index = 0;
//        String get_number = Integer.toString(word_list.get(index).getNumber()); // 단어의 번호
//        String get_word = word_list.get(index).getWord(); // 단어
//        String get_img_name = word_list.get(index).getImg_name(); // 단어 이미지 파일 이름
//        String get_img_extension = word_list.get(index).getImg_extension(); // 단어 이미지 파일 확장자명
//
//        TextView tv_word_num = (TextView) findViewById(R.id.word_number);
//        TextView tv_word = (TextView) findViewById(R.id.word);
//        TextView tv_word_img_name = (TextView) findViewById(R.id.word_img_name) ;
//        ImageView iv_word_img = (ImageView) findViewById(R.id.word_img);
//
//        tv_word_num.setText(get_number);
//        tv_word.setText(get_word);
//        tv_word_img_name.setText(get_img_name + get_img_extension);
//        int lid = this.getResources().getIdentifier(get_img_name, "drawable", this.getPackageName());
//        iv_word_img.setImageResource(lid) ;

    }

    //버튼
    public void mOnPopupClick(View v){
        //데이터 담아서 팝업(액티비티) 호출
        Intent intent = new Intent(this, PopupActivity.class);
        intent.putExtra("data", "Test Popup");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                //데이터 받기
                String result = data.getStringExtra("result");
                txtResult.setText(result);
            }
        }
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
