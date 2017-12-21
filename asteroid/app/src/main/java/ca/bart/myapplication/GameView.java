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
    private int earthOffset = 15;
    private int asteroidWidth =  5;

    private float scale;
    private float timeUntilSpawn = 0.0f;

    private double[] lasersTimers = new double[100];
    private double laserLingerTime = 0.3f;

    private Asteroid[] asteroids = new Asteroid[100];
    private Laser[] lasers = new Laser[100];

    private Earth earth = new Earth();
    private Ship ship = new Ship();

    public GameView(Context context) {
        this(context, null);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        for(int i = 0; i < asteroids.length; i++)
        {
            asteroids[i] = null;
        }

        for(int i = 0; i < lasers.length; i++)
        {
            lasers[i] = null;
        }

        gameObjects.add(ship);
        gameObjects.add(earth);
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

        for (GameObject gameObject : gameObjects) {
            gameObject.onDraw(canvas);
        }
    }

    public void update() {

        for (GameObject gameObject : gameObjects) {
            gameObject.update();
        }

        AsteroidSpawner();
        LaserSpawner();
        CheckForEarth();
        CheckForAsteroid();
        LaserTimer();
    }

    public void AsteroidSpawner()
    {
        float spawnInterval = 0.5f;
        timeUntilSpawn += DELTA_TIME;

        if(timeUntilSpawn >= spawnInterval)
        {
            timeUntilSpawn = 0.0f;
            for(int i = 0; i < asteroids.length; i++)
            {
                if(asteroids[i] == null)
                {
                    asteroids[i] = new Asteroid();
                    gameObjects.add(asteroids[i]);
                    break;
                }
            }
        }
    }

    public void LaserSpawner()
    {
        if(ship.fireLaser)
        {
            for(int i = 0; i < lasers.length; i++)
            {
                if(lasers[i] == null)
                {
                    lasers[i] = new Laser(ship.fireLaser, ship.angle, ship.matrix);
                    gameObjects.add(lasers[i]);
                    ship.fireLaser = false;
                    break;
                }
            }
        }
    }

    private void LaserTimer()
    {
        for(int i = 0; i < lasers.length; i++)
        {
            if(lasers[i] != null)
            {
                lasersTimers[i] += DELTA_TIME;
                if (lasersTimers[i] >= laserLingerTime)
                {
                    gameObjects.remove(lasers[i]);
                    lasers[i] = null;
                    lasersTimers[i] = 0.0f;
                }
            }
        }
    }

    private void CheckForEarth()
    {
        for(int i = 0; i < asteroids.length; i++)
        {
            if(asteroids[i] != null)
            {
                if(asteroids[i].targetDistance >= asteroids[i].asteroidSpawnY - earthOffset)
                {
                    earth.earthHP -= 10;
                    gameObjects.remove(asteroids[i]);
                    asteroids[i] = null;
                }
            }
        }
    }

    private void CheckForAsteroid()
    {
        for(int i = 0; i < lasers.length; i++)
        {
            if(lasers[i] != null)
            {
                for(int j = 0; j < asteroids.length; j++)
                {
                    if(asteroids[j] != null)
                    {
                        if(lasers[i].angle <= asteroids[j].asteroidSpawn - 270 + asteroidWidth && lasers[i].angle >= asteroids[j].asteroidSpawn - 270 - asteroidWidth
                        || lasers[i].angle <= asteroids[j].asteroidSpawn + 90 + asteroidWidth && lasers[i].angle >= asteroids[j].asteroidSpawn + 90 - asteroidWidth)
                        {
                            gameObjects.remove(asteroids[j]);
                            asteroids[j] = null;
                        }
                    }
                }
            }
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
