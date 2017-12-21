package ca.bart.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class MainActivity extends Activity implements Constants {

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            update();
        }
    };

    private long lastUpdate = System.nanoTime();
    private double timer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onResume() {
        super.onResume();

        update();
    }

    @Override
    protected void onPause() {
        super.onPause();

        handler.removeCallbacks(runnable);
    }

    public void update() {

        long nanoSeconds = System.nanoTime();
        double deltaTime = 0.000000001 * (nanoSeconds - lastUpdate);
        lastUpdate = nanoSeconds;

        timer += deltaTime;

        while (timer > DELTA_TIME) {

            timer -= deltaTime;

            GameView gameView = (GameView) findViewById(R.id.gameView);
            gameView.update();
            gameView.invalidate();
        }

        handler.postDelayed(runnable, (long)(1000 * DELTA_TIME));
    }


}
