/**
 * @author Frederik Schönfeldt, Göran Kirchhoff & Muhialdeen Ibrahim
 * @version 1.0
 * @since 1.0
 *
 * History:
 * - %
 */
package de.fhfl.gpsmyass;

import android.content.Context;
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

    protected Paint myPaint;

    public void init() {
        myPaint = new Paint();
        myPaint.setStyle(Paint.Style.FILL);
        myPaint.setColor(Color.WHITE);
    }

    public void move(float getX, float getY) {
        x = getX;
        y = getY;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawCircle(x,y, 20, myPaint); // !TODO Radius als Variable sodass Objekte verschieden Groß sein können
    }

    public float getX() { return this.x; }

    public float getY() { return this.y; }

    @Override
    public void setAlpha(int alpha) {
        // Muss angegeben, aber nicht benutzt werden
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        // Muss angegeben, aber nicht benutzt werden
    }

    @Override
    public int getOpacity() {
        // Muss angegeben, aber nicht benutzt werden
        return 0;
    }
}
