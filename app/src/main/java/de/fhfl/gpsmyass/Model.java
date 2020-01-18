/**
 * @author Frederik Schönfeldt, Göran Kirchhoff & Muhialdeen Ibrahim
 * @version 1.0
 * @since 1.0
 *
 * History:
 * - 12.01.2020 Model hinzugefügt
 * - 18.01.2020  - Kommentare aktualisiert, Objekte aus den anderen Klassen in das Model refakturiert
 *               - Konfigurationsvariablen hinzugefügt, wie z.B. die Geschwindigkeit der Bullets
 */
package de.fhfl.gpsmyass;

import android.os.CountDownTimer;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

import de.fhfl.gpsmyass.Asteroid;
import de.fhfl.gpsmyass.Bullet;
import de.fhfl.gpsmyass.Spaceship;

import static de.fhfl.gpsmyass.MeinTollesView.mySpaceShip;

public class Model {
    private static final String TAG = "hsflModel";
    public static Spaceship mySpaceship;
    public static ArrayList<Bullet> myBullets = new ArrayList<Bullet>();
    public static ArrayList<Asteroid> myAsteroids = new ArrayList<Asteroid>();

    // Konfigurationsvariablen, könnten an eine Schwierigkeitsstufe gebunden werden
    public static int bulletSpeed = 10;
    public static int asteroidSpeed = 3;

    // Werte für Countdown
    private static int myCounter = 0;
    private static CountDownTimer myTimer = new CountDownTimer(Long.MAX_VALUE, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            Log.d(TAG, "Model.myTimer.onTick():" + myCounter);

            if(myCounter % 3 == 0) {
                spawnAsteroid(100, 0); // !TODO mit Model Werten austauschen, aktuell wird der As(s)teroid immer fix an einer Stelle gespawned
            }

            myCounter++;
        }

        @Override
        public void onFinish() {
            Log.w(TAG, "Model.myTimer.onFinish(): Das hätte nie passieren dürfen");
            // Das darf nie passieren :-D
        }
    };

    public static void startTimer() {
        myTimer.start();
    }

    public int getMyCounter() {
        return myCounter;
    }

    private static void spawnAsteroid(int x, int y) {
        myAsteroids.add(new Asteroid(x,y));
    }; // !TODO Scoping muss evtl. angepasst werden, wenn mehrere Asteroiden aufgrund einer Explosion entstehen sollen..

    // !TODO Alles ins Model überführen!
}
