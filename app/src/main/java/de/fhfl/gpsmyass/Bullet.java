/**
 * @author Frederik Schönfeldt, Göran Kirchhoff & Muhialdeen Ibrahim
 * @version 1.0
 * @since 1.0
 *
 * History:
 * - 12.12.2019 hinzugefügt
 */

package de.fhfl.gpsmyass;

public class Bullet extends Moveable {
    public Bullet(float x, float y) {
        super();
        this.init();
        this.move(x,y);
    }
}
