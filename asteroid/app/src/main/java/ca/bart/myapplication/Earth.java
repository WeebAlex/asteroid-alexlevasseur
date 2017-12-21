package ca.bart.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Created by 1630099 on 2017-11-03.
 */

public class Earth implements GameObject
{
    private Paint blueOutline = new Paint();
    public int earthHP = 100;

    public Earth() {
        blueOutline.setColor(Color.BLUE);
        blueOutline.setStyle(Paint.Style.STROKE);
        blueOutline.setStrokeWidth(1);
    }

    public void onDraw(Canvas canvas) {

        canvas.save();

        canvas.drawCircle(0, 0, 10, blueOutline);

        canvas.restore();
    }

    public void update() {

    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
