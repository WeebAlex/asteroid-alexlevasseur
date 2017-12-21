package ca.bart.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Created by 1630099 on 2017-10-31.
 */

public class Ship implements GameObject, Constants {

    private Paint blueOutline = new Paint();


    private int touchId = -1;
    public double angle;

    public boolean fireLaser = false;

    Matrix matrix = new Matrix();
    float[] points = new float[2];

    public Ship() {
        blueOutline.setColor(Color.BLUE);
        blueOutline.setStyle(Paint.Style.STROKE);
        blueOutline.setStrokeWidth(1);

        //redOutline.setColor(Color.RED);
        //redOutline.setStyle(Paint.Style.STROKE);
        //redOutline.setStrokeWidth(2);
    }

    public void onDraw(Canvas canvas) {

        canvas.save();

        AddShip(canvas);

        canvas.restore();
    }

    public void update() {
    }

    private void AddShip(Canvas canvas)
    {
        canvas.getMatrix().invert(matrix);

        canvas.rotate(-45);

        canvas.rotate((float) (angle));

        canvas.translate(14,14);

        canvas.rotate(45);

        Helper.drawTriangle(canvas,blueOutline, 8, 45, true);
    }

    private int getIndex(MotionEvent event)
    {
        int idx = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) & MotionEvent.ACTION_POINTER_INDEX_SHIFT;

        return idx;
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

                int touchCounter = event.getPointerCount();
                for(int t = 0; t < touchCounter; t++)
                {
                    int currentId = event.getPointerId(t);
                    if (touchId == id || currentId == touchId) {

                        points[0] = event.getX(index);
                        points[1] = event.getY(index);
                        matrix.mapPoints(points);

                        angle = Math.toDegrees(Math.atan2(points[1], points[0]));
                        return true;
                    }
                }
                break;

            case MotionEvent.ACTION_POINTER_UP:

                int currentId = event.getPointerId(getIndex(event));
                if (currentId == touchId)
                {
                    fireLaser = true;
                }

            case MotionEvent.ACTION_UP:

                if (touchId == id)
                {
                    fireLaser = true;
                }

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
