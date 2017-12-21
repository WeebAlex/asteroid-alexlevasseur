package ca.bart.myapplication;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Helper {




    public static void drawTriangle(Canvas canvas, Paint paint, int size, float angle, boolean drawAdjacentSides) {

        canvas.save();

        for (int i = 0; i < 2; ++i) {

            canvas.save();
            {
                canvas.rotate(angle / 2);
                if (drawAdjacentSides) {
                    canvas.drawLine(0, 0, -size, 0, paint);
                }
                canvas.translate(-size, 0);
                canvas.rotate(90 - angle / 2);
                canvas.drawLine(0, 0, angle > 60 ? size : size / 2, 0, paint);
            }
            canvas.restore();
            canvas.scale(1, -1);
        }

        canvas.restore();
    }

    public static void drawPolygon(Canvas canvas, Paint paint, int size, int nbSides) {

        canvas.save();

        float angle = 360.0f / nbSides;

        for (int n = 0; n < nbSides; ++n) {
            Helper.drawTriangle(canvas, paint, size, angle, false);
            canvas.rotate(angle);
        }

        canvas.restore();
    }



}
