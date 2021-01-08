package com.cnu_helper.l_cure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class PopupActivity extends Activity {
    private int number;
    TextView txtText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //타이틀바 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //데이터 가져오기
        Intent intent = getIntent();
        number = intent.getIntExtra("number",0);

        if(number==1){
            setContentView(R.layout.session01_popup);
        }else if(number==2){
            setContentView(R.layout.session02_popup);
        }else if(number==3){
            setContentView(R.layout.session03_popup);
        }else if(number==4){
            setContentView(R.layout.session04_popup);
        }else if(number==5){
            setContentView(R.layout.session05_popup);
        }else if(number==6){
            setContentView(R.layout.session06_popup);
        }else if(number==7){
            setContentView(R.layout.rightanswer_popup);
            String imgName = intent.getStringExtra("imgName");
            ImageView imgView = (ImageView) findViewById(R.id.word_img);
            int lid = this.getResources().getIdentifier(imgName, "drawable", this.getPackageName());
            imgView.setImageResource(lid);
        }else if(number==8){
            setContentView(R.layout.wronganswer_popup);
        }else if(number==9){
            setContentView(R.layout.knowing_popup);
            String[] test = intent.getStringArrayExtra("test");
            TextView textView_1 = findViewById(R.id.textView1);
            textView_1.setText("음절 세기 : " + test[0]);
            TextView textView_2 = findViewById(R.id.textView2);
            textView_2.setText("단어 합성 : " + test[1]);
            TextView textView_3 = findViewById(R.id.textView3);
            textView_3.setText("음소 분절 : " + test[2]);
            TextView textView_4 = findViewById(R.id.textView4);
            textView_4.setText("음소 합성 : " + test[3]);
            TextView textView_5 = findViewById(R.id.textView5);
            textView_5.setText("음소 대치 : " + test[4]);
            TextView textView_6 = findViewById(R.id.textView6);
            textView_6.setText("음절 변별 : " + test[5]);
        }
    }

    //확인 버튼 클릭
    public void mOnClose(View v){
        if(number==1){
            onBackPressed();
            Intent intent = new Intent(getApplicationContext(), SelectSyllableCountActivity.class);
            intent.putExtra("result", "Close Popup");
            setResult(RESULT_OK, intent);
            startActivityForResult(intent,5000);
        }
        else if(number==2){
            onBackPressed();
            Intent intent = new Intent(getApplicationContext(), SelectWordSynthesisActivity.class);
            intent.putExtra("result", "Close Popup");
            setResult(RESULT_OK, intent);
            startActivityForResult(intent,5000);
        }
        else if(number==3){
            onBackPressed();
            Intent intent = new Intent(getApplicationContext(), SelectPhonemicSegmentationActivity.class);
            intent.putExtra("result", "Close Popup");
            setResult(RESULT_OK, intent);
            startActivityForResult(intent,5000);
        }
        else if(number==4){
            onBackPressed();
            Intent intent = new Intent(getApplicationContext(), SelectPhonemicSynthesisActivity.class);
            intent.putExtra("result", "Close Popup");
            setResult(RESULT_OK, intent);
            startActivityForResult(intent,5000);
        }
        else if(number==5){
            onBackPressed();
            Intent intent = new Intent(getApplicationContext(), SelectPhonemicSubstitutionActivity.class);
            intent.putExtra("result", "Close Popup");
            setResult(RESULT_OK, intent);
            startActivityForResult(intent,5000);
        }
        else if(number==6){
            onBackPressed();
            Intent intent = new Intent(getApplicationContext(), SelectSyllableDiscriminationActivity.class);
            intent.putExtra("result", "Close Popup");
            setResult(RESULT_OK, intent);
            startActivityForResult(intent,5000);
        }
        else {
            onBackPressed();
            return;
        }
    }
}
