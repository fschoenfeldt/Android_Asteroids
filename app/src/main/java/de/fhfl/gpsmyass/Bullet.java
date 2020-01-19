/**
 * @author Frederik Schönfeldt, Göran Kirchhoff & Muhialdeen Ibrahim
 * @version 1.0
 * @since 1.0
 *
 * History:
 * - 12.12.2019 hinzugefügt
 */

package de.fhfl.gpsmyass;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import androidx.annotation.NonNull;

public class Bullet extends Moveable {
    public Bullet(float x, float y) {
        super();
        this.init();
        this.move(x,y);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(MeinTollesView.myContext.getResources(), R.drawable.schuss2_transparent);
        canvas.drawBitmap(bitmap, x, y, myPaint);
    }
}
