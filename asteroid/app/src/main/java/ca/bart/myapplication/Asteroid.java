package ca.bart.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
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
    private double speed = DELTA_TIME * 100;
    public float targetDistance = 0.0f;
    private int colorSelect;
    private int asteroidSelect;
    public int asteroidSpawn;
    public int asteroidSpawnY = 150;
    private float angle = 10;

    public Asteroid() {
        colorSelect = random.nextInt(6);

        switch(colorSelect)
        {
            case 1: randomColor.setColor(Color.MAGENTA);
                break;
            case 2: randomColor.setColor(Color.BLACK);
                break;
            case 3: randomColor.setColor(Color.GREEN);
                break;
            case 4: randomColor.setColor(Color.RED);
                break;
            case 5: randomColor.setColor(Color.BLUE);
                break;
            case 6: randomColor.setColor(Color.CYAN);
                break;
        }

        randomColor.setStyle(Paint.Style.STROKE);
        randomColor.setStrokeWidth(1);
        asteroidSelect = 5 + random.nextInt(4);
        asteroidSpawn = random.nextInt(360);
    }

    public void onDraw(Canvas canvas) {

        canvas.save();

        canvas.rotate(asteroidSpawn);
        canvas.translate(0,asteroidSpawnY - targetDistance);
        canvas.rotate(angle += 1);
        Helper.drawPolygon(canvas, randomColor,5,asteroidSelect);

        canvas.restore();
    }

    public void update()
    {
        targetDistance += speed;
    }

    public boolean onTouchEvent(MotionEvent event)
    {
        return false;
    }
}
