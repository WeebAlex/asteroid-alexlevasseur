package ca.bart.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.graphics.Matrix;

/**
 * Created by Alex on 11/4/2017.
 */

public class Laser implements GameObject,Constants{

    private double laserLingerTime = 0.0f;
    public double angle;

    private Paint redOutline = new Paint();

    private int touchId = -1;

    private Ship ship = new Ship();

    public boolean laser = false;

    Matrix matrix;
    //float[] points = new float[2];

    public Laser(boolean fireLaser, double shipAngle, Matrix shipMatrix) {
        laser = fireLaser;
        angle = shipAngle;
        matrix = shipMatrix;

        redOutline.setColor(Color.RED);
        redOutline.setStyle(Paint.Style.STROKE);
        redOutline.setStrokeWidth(2);
    }

    public void onDraw(Canvas canvas) {

        canvas.save();

        AddLaser(canvas);

        canvas.restore();
    }

    public void update() {

    }

    private void AddLaser(Canvas canvas)
    {
        if(laser)
        {
            canvas.getMatrix().invert(matrix);

            canvas.rotate(-45);

            canvas.rotate((float) (angle));

            canvas.translate(14,14);

            canvas.drawLine(0, 0, 120, 120, redOutline);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
