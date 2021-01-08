package com.cnu_helper.l_cure;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WritingView extends View {

    private Paint paint = new Paint();
    private Path path = new Path();
    private int x,y;

    public WritingView(Context context) {
        super(context);
    }

    public WritingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WritingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.BLUE); // 색상
        paint.setStyle(Paint.Style.STROKE); // 테두리, 선 등
        paint.setStrokeWidth(50); //두께
        canvas.drawPath(path, paint); //path 객체가 가지고 있는 경로를 화면에 그린다.
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 터치가 되는 시점부터 움직이는 모든 이벤트 동안의 좌표는 모두 int형으로 path안에 저장된다

        x = (int)event.getX();
        y = (int)event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                // 터치를 했을 떄
                path.moveTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                x = (int)event.getX();
                y = (int)event.getY();

                path.lineTo(x,y);
                break;
        }

        //View의 onDraw()를 호출하는 메소드
        invalidate();

        return true;
    }

    // 저장하기
    @SuppressLint("WrongThread")
    public void save (Context context) {
        this.setDrawingCacheEnabled(true);


        // 현재 날짜로 파일을 저장하기
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        String filename = dateString + "_drawImage.png";
//        try {
//            File file = new File(Environment.getExternalStorageDirectory(), filename);
//            if (file.createNewFile())
//                Log.d("save", "파일 생성 성공");
//            OutputStream outStream = new FileOutputStream(file);
//            screenshot.compress(Bitmap.CompressFormat.PNG, 100, outStream);
//            outStream.close();
//
//            // 갤러리에 변경을 알려줌
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                // 안드로이드 버전이 Kitkat 이상 일때
//                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                Uri contentUri = Uri.fromFile(file);
//                mediaScanIntent.setData(contentUri);
//                context.sendBroadcast(mediaScanIntent);
//            } else {
//                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
//            }
//
//            Toast.makeText(context.getApplicationContext(), "저장완료", Toast.LENGTH_LONG).show();
//        } catch (IOException e) {
//            Toast.makeText(context.getApplicationContext(), "저장 에러", Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        }
//        this.setDrawingCacheEnabled(false);

//        int writeCheck = ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        if(writeCheck != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(context.getApplicationContext(), "permission error", Toast.LENGTH_LONG).show();
//        }else {
//            Toast.makeText(context.getApplicationContext(), "permission ok", Toast.LENGTH_LONG).show();
//        }
////////////////////
        String file_state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(file_state) )
            Toast.makeText(context.getApplicationContext(), "permission state ok", Toast.LENGTH_LONG).show();

//        File file = new File(Environment.getExternalStorageDirectory().getPath()+filename);
        OutputStream out = null;
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root);
        myDir.mkdirs();
        File file = new File(myDir, filename);

        try{
            out = new FileOutputStream(file);
            if(out != null) {
                Bitmap screenshot = this.getDrawingCache();
                screenshot.compress(Bitmap.CompressFormat.PNG, 75, out);
                Toast.makeText(context.getApplicationContext(), "Capture success", Toast.LENGTH_LONG).show();
            } else Toast.makeText(context.getApplicationContext(), "Capture fail", Toast.LENGTH_LONG).show();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context.getApplicationContext(), "FileNotFound - error", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context.getApplicationContext(), "InOut - error", Toast.LENGTH_LONG).show();
        }
//////////////////
//        File storage = context.getCacheDir();
//        String path = storage.getPath();
//        File file = new File(storage, filename);
//        try{
//            Bitmap screenshot = this.getDrawingCache();
//            file.createNewFile();
//            FileOutputStream out = new FileOutputStream(file);
//            screenshot.compress(Bitmap.CompressFormat.PNG, 75, out);
//            out.close();
//            Toast.makeText(context.getApplicationContext(), "save ok: "+path, Toast.LENGTH_LONG).show();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            Toast.makeText(context.getApplicationContext(), "FileNotFound - error", Toast.LENGTH_LONG).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(context.getApplicationContext(), "InOut - error", Toast.LENGTH_LONG).show();
//        }

    }
}
