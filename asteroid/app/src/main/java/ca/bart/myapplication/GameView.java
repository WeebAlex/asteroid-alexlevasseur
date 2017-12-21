package ca.bart.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Iterator;
import java.util.LinkedList;

public class GameView extends View implements Constants{

    public static final String TAG = "GameView";

    private LinkedList<GameObject> gameObjects = new LinkedList<>();

    private int cx, cy;
    private float scale;
    private float spawnInterval = 0.5f;
    private float timeUntilSpawn = 0.0f;
    private Asteroid[] asteroids = new Asteroid[100];
    private int asteroidIndex = 0;

    public GameView(Context context) {
        this(context, null);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        gameObjects.add(new Ship());
        gameObjects.add(new Earth());

        for(int i = 0; i < asteroids.length; i++)
        {
            asteroids[i] = new Asteroid();
            gameObjects.add(asteroids[i]);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        cx = w / 2;
        cy = h / 2;
        int min = Math.min(cx, cy);
        scale = min / 100.0f;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.translate(cx, cy);
        canvas.scale(scale, -scale);

        /*
        Paint blueOutline = new Paint();
        blueOutline.setColor(Color.BLUE);
        blueOutline.setStyle(Paint.Style.STROKE);
        blueOutline.setStrokeWidth(2);


        Helper.drawPolygon(canvas, blueOutline, 100, 4);
        Helper.drawPolygon(canvas, blueOutline, 100, 6);
        */

        for (GameObject gameObject : gameObjects) {
            gameObject.onDraw(canvas);
        }
    }

    public void update() {

        for (GameObject gameObject : gameObjects) {
            gameObject.update();
        }

        AsteroidSpawner();
    }

    public void AsteroidSpawner()
    {
        timeUntilSpawn += DELTA_TIME;

        if(timeUntilSpawn >= spawnInterval)
        {
            timeUntilSpawn = 0.0f;
            asteroids[asteroidIndex++] = new Asteroid();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        for (Iterator<GameObject> i = gameObjects.descendingIterator(); i.hasNext(); ) {
            GameObject gameObject = i.next();
            if (gameObject.onTouchEvent(event)) {
                return true;
            }
        }
        return super.onTouchEvent(event);
    }
}
