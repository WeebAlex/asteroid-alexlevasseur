package ca.bart.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import java.util.Random;
/**
 * Created by 1630099 on 2017-11-03.
 */

public class Asteroid implements GameObject, Constants
{


    private Paint randomColor = new Paint();
    private Random random = new Random();
    private double speed = DELTA_TIME * 50;
    private float distanceToEarth = 0.0f;
    private int colorSelect;
    private int asteroidSelect;
    private int asteroidSpawn;
    private int touchId = -1;
    private double angle;


    Matrix matrix = new Matrix();
    float[] points = new float[2];

    public Asteroid() {
        colorSelect = random.nextInt(6);
        if(colorSelect == 5)
        {
            randomColor.setColor(Color.BLUE);
        }
        else if(colorSelect == 4)
        {
            randomColor.setColor(Color.RED);
        }
        else if(colorSelect == 3)
        {
            randomColor.setColor(Color.GREEN);
        }
        else if(colorSelect == 2)
        {
            randomColor.setColor(Color.BLACK);
        }
        else if(colorSelect == 1)
        {
            randomColor.setColor(Color.MAGENTA);
        }
        else
        {
            randomColor.setColor(Color.CYAN);
        }
        randomColor.setStyle(Paint.Style.STROKE);
        randomColor.setStrokeWidth(1);
        asteroidSelect = 5 + random.nextInt(4);
        asteroidSpawn = random.nextInt(360);
    }

    public void onDraw(Canvas canvas) {

        canvas.save();
        canvas.translate(0,120 - distanceToEarth);
        canvas.rotate(asteroidSpawn);
        Helper.drawPolygon(canvas, randomColor,5,asteroidSelect);

        canvas.restore();
    }

    public void update()
    {
        distanceToEarth += speed;
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        return false;
    }
}
