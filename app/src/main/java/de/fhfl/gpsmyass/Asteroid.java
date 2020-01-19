/**
 * @author Frederik Schönfeldt, Göran Kirchhoff & Muhialdeen Ibrahim
 * @version 1.0
 * @since 1.0
 *
 * History:
 * - 12.01.2020 hinzugefügt
 */
package de.fhfl.gpsmyass;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import androidx.annotation.NonNull;

public class Asteroid extends Moveable {
    Asteroid(float x, float y) {
        super();
        this.init();
        this.move(x,y);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(MeinTollesView.myContext.getResources(), R.drawable.asteroid_sw);
        canvas.drawBitmap(bitmap, x - bitmap.getWidth()/2, y - bitmap.getHeight()/2, myPaint);
    }
}
