/**
 * @author Frederik Schönfeldt, Göran Kirchhoff & Muhialdeen Ibrahim
 * @version 1.0
 * @since 1.0
 *
 * History:
 * - 12.12.2019 hinzugefügt
 */
package de.fhfl.gpsmyass;

import android.util.Log;

public class Spaceship extends Moveable {
    public Spaceship(float x, float y) {
        super();
        this.init();
        this.move(x,y);
    }

    @Override
    public void move(float getX, float getY) {
        Log.v("Spaceship", "Moveable.move()");
        if(getX > 1.0 || getX < -1.0) {
            x += getX;
        }
        if(getY > 1.0 || getY < -1.0) {
            y += getY;
        }
    }

    public void fire() {
        Bullet oneBullet = new Bullet(x, y-40);
        Model.myBullets.add(oneBullet);
    }
}
