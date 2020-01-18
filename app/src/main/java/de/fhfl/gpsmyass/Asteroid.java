/**
 * @author Frederik Schönfeldt, Göran Kirchhoff & Muhialdeen Ibrahim
 * @version 1.0
 * @since 1.0
 *
 * History:
 * - 12.01.2020 hinzugefügt
 */
package de.fhfl.gpsmyass;

import android.graphics.Canvas;

import androidx.annotation.NonNull;

public class Asteroid extends Moveable {
    Asteroid(float x, float y) {
        super();
        this.init();
        this.move(x,y);
    }
    /* !TODO !IMPORTANT GRAFIKEN EINFÜGEN
    @Override
    public void draw(@NonNull Canvas canvas) { // !TODO eigene Draws für aufbauende Objekte erstellen
        canvas.drawCircle(x,y, 20, myPaint);
    }*/
}
