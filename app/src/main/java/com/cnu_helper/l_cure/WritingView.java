package com.cnu_helper.l_cure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

public class WritingView extends View {

    private Paint paint = new Paint();
    private Path path = new Path();
    private int x,y;

    public WritingView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.BLACK); // 색상
        paint.setStyle(Paint.Style.STROKE); //STROKE속성을 이용하여 테두리...선...
        paint.setStrokeWidth(3); //두께
        canvas.drawPath(path, paint); //path객체가 가지고 있는 경로를 화면에 그린다...
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

        //View의 onDraw()를 호출하는 메소드...
        invalidate();

        return true;
    }
}
