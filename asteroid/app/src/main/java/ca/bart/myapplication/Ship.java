package ca.bart.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Created by 1630099 on 2017-10-31.
 */

public class Ship implements GameObject {

    private Paint blueOutline = new Paint();

    private int touchId = -1;
    private double angle;

    Matrix matrix = new Matrix();
    float[] points = new float[2];

    public Ship() {
        blueOutline.setColor(Color.BLUE);
        blueOutline.setStyle(Paint.Style.STROKE);
        blueOutline.setStrokeWidth(1);
    }

    public void onDraw(Canvas canvas) {

        canvas.save();

        AddShip(canvas);

        canvas.restore();
    }

    public void update() {

    }

    public void AddShip(Canvas canvas)
    {
        canvas.getMatrix().invert(matrix);

        canvas.rotate(-45);

        canvas.rotate((float) (angle));

        canvas.translate(14,14);

        canvas.rotate(45);

        Helper.drawTriangle(canvas,blueOutline, 8, 45, true);
    }

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

                    angle = Math.toDegrees(Math.atan2(points[1], points[0]));
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
