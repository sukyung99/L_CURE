package com.cnu_helper.l_cure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SelectMyWordStageActivity extends AppCompatActivity {
    private List<Words> word_list; // words 리스트
    private List<Words> new_word_list  = new ArrayList<>();; // 추출한 words 리스트

    private ArrayAdapter<String> adapter;
    private ListView lv_word_list;
    private ArrayList<String> arr_word_list;

    private Button back;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_my_word_stage);

        // 뒤로가기 버튼
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                return;
            }
        });

        // listview 세팅
        arr_word_list  = new ArrayList<>();
        lv_word_list = (ListView) findViewById(R.id.show_word_list);
        adapter = new ArrayAdapter<>(this, R.layout.my_word_stage_list_item_1, arr_word_list);
        lv_word_list.setAdapter(adapter);


        // 단어 db 로드
        initLoadDB();

        // 한 글자 단어만 추출
        for (int i = 0; i < word_list.size(); i++) {
            if (word_list.get(i).getWord().length() == 1) {
                new_word_list.add(word_list.get(i));
                arr_word_list.add(word_list.get(i).getWord());
            }
        }
        // listview 갱신
        adapter.notifyDataSetChanged();

        // click listener 추가
        lv_word_list.setOnItemClickListener(listener);
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

    // 단어 클릭했을 떄, 나만의 단어 만드는 페이지로 넘어감.
    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            //Toast.makeText(SelectMyWordStageActivity.this, arr_word_list.get(position), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), SelectMyWordActivity.class);
            Words word = null;
            for(int i=0; i<new_word_list.size(); i++){
                if(arr_word_list.get(position).equals(new_word_list.get(i).getWord())) {
                    word = new_word_list.get(i);
                    break;
                }
            }

            intent.putExtra("my_word", word);
            startActivityForResult(intent, 5000);
        }
    };
}
