/**
 * @author Frederik Schönfeldt, Göran Kirchhoff & Muhialdeen Ibrahim
 * @version 1.0
 * @since 1.0
 *
 * History:
 * - %
 */
package de.fhfl.gpsmyass;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

public class Moveable extends Drawable implements Serializable {
    private static final String TAG = "hsflMoveable";

    // Benötigte Variablen definieren
    protected transient float x = 0;
    protected transient float y = 0;

    private static int screenWidth;
    private static int screenHeight;

    // Zentrum vom Moveable
    protected float centerX, centerY;

    private Bitmap myBitmap;
    protected Paint myPaint;

    public void init(/* Bitmap bitmap */) { // !TODO Bitmap implementieren
//        this.myBitmap = bitmap; // !TODO Bitmap implementieren

//        float pixelPerTimeTic = speed * stTicDurationS;
//        xSpeed = (float) Math.cos((double) direction*Math.PI/180f) * pixelPerTimeTic;
//        ySpeed = (float) Math.sin((double) direction*Math.PI/180f) * pixelPerTimeTic;  // Achtung: y waechst in neg. Richtung

//        centerX = bitmap.getWidth()/2; // !TODO Bitmap implementieren
//        centerY = bitmap.getHeight()/2; // !TODO Bitmap implementieren

        myPaint = new Paint();
        myPaint.setStyle(Paint.Style.FILL);
        myPaint.setColor(Color.WHITE);
    }

    public void move(float getX, float getY) {
        Log.v(TAG, "Moveable.move()");
        x = getX;
        y = getY;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawCircle(x,y, 20, myPaint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }

    public float getX() {
        return this.x;
    }

    public float getY() { return this.y; }
}
