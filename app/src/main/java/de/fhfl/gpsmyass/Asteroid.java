/**
 * @author Frederik Schönfeldt, Göran Kirchhoff & Muhialdeen Ibrahim
 * @version 1.0
 * @since 1.0
 *
 * History:
 * - 12.01.2020 hinzugefügt
 */
package de.fhfl.gpsmyass;

public class Asteroid extends Moveable {
    Asteroid(float x, float y) {
        super();
        this.init();
        this.move(x,y);
    }
}
