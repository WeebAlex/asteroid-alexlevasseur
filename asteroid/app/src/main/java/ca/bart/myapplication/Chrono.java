package ca.bart.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Created by guifra on 2017-10-17.
 */

public class Chrono implements GameObject, Constants {

    private double time = 0;

    private Paint blueOutline = new Paint();

    private int touchId = -1;

    int cx, cy;

    Matrix matrix = new Matrix();
    float[] points = new float[2];

    public Chrono() {
        blueOutline.setColor(Color.BLUE);
        blueOutline.setStyle(Paint.Style.STROKE);
        blueOutline.setStrokeWidth(2);
    }

    @Override
    public void onDraw(Canvas canvas) {

        canvas.save();

        canvas.rotate(90);
        canvas.scale(1, -1);

        canvas.getMatrix().invert(matrix);

        canvas.drawCircle(0, 0, 100, blueOutline);

        canvas.rotate((float) (time * 6));

        canvas.drawLine(0, 0, 100, 0, blueOutline);

        canvas.restore();
    }

    @Override
    public void update() {

        if (touchId == -1) {
            time += DELTA_TIME;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int index = event.getActionIndex();
        int id = event.getPointerId(index);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:

                if (touchId != -1) {
                    return false;
                }
                touchId = id;

            case MotionEvent.ACTION_MOVE:

                if (touchId == id) {

                    points[0] = event.getX(index);
                    points[1] = event.getY(index);
                    matrix.mapPoints(points);

                    time = Math.toDegrees(Math.atan2(points[1], points[0])) / 6;
                    return true;
                }
                break;

            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                if (touchId == id) {
                    touchId = -1;
                    return true;
                }
                break;
        }

        return false;
    }
}
