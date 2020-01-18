/**
 * @author Frederik Schönfeldt, Göran Kirchhoff & Muhialdeen Ibrahim
 * @version 1.0
 * @since 1.0
 *
 * History:
 * - 12.01.2020 Model hinzugefügt
 * - 18.01.2020  - Kommentare aktualisiert, Objekte aus den anderen Klassen in das Model refakturiert
 *               - Konfigurationsvariablen hinzugefügt, wie z.B. die Geschwindigkeit der Bullets
 *               - Kollisionserkennung hinzugefügt
 */
package de.fhfl.gpsmyass;

import android.os.CountDownTimer;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;


import static de.fhfl.gpsmyass.MeinTollesView.mySpaceShip;

public class Model {
    private static final String TAG = "hsflModel";
    public static Spaceship mySpaceship;
    public static ArrayList<Bullet> myBullets = new ArrayList<Bullet>();
    public static ArrayList<Asteroid> myAsteroids = new ArrayList<Asteroid>();

    // Konfigurationsvariablen, könnten an eine Schwierigkeitsstufe gebunden werden
    public static int bulletSpeed = 10;
    public static int asteroidSpeed = 3;

    // Sonstige Variablen
    private static float viewHeight = 0;
    private static float viewWidth = 0;
    private static int countDownInterval = 300;

    // Werte für Countdown
    private static int myCounter = 0;
    private static final Random rand = new Random();

    private static CountDownTimer myTimer = new CountDownTimer(Long.MAX_VALUE, countDownInterval) {
        @Override
        public void onTick(long millisUntilFinished) {
            Log.d(TAG, "Model.myTimer.onTick():" + myCounter);
            int nextX = rand.nextInt((int)viewWidth);
            int nextY = rand.nextInt((int)viewHeight);

            if(myCounter % 3 == 0) {
                spawnAsteroid(nextX, 0); // !TODO Y-Wert vielleicht immer 0, damit einfacher
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
    };

    public static void checkCollision(MeinTollesView myView) {
        // Sehr hacky..
        viewHeight = myView.getHeight();
        viewWidth = myView.getWidth();

        // Bullets & Asteroiden auf Kollision prüfen
        Asteroid asteroidToRemove = null;
        Bullet bulletToRemove = null;

        for(Bullet oneBullet : myBullets) { //!INFO For of Schleife erlaubt keine Löschung von Objekten, während man durch das Objekt iteriert..
            for(Asteroid oneAsteroid : myAsteroids) {
                // Kollisionsüberprüfung Bullet <---> Asteroid
                if(oneBullet.getX() - oneAsteroid.getX() < 30 && oneBullet.getX() - oneAsteroid.getX() > -30  && oneBullet.getY() - oneAsteroid.getY() < 30 && oneBullet.getY() - oneAsteroid.getY() > -30) { //!TODO Radius als Variable sodass Objekte verschieden Groß sein können
                    Log.i(TAG, "Model.checkCollision(): Kollision gefunden!");
                    asteroidToRemove = oneAsteroid;
                    bulletToRemove = oneBullet;
                }

                // Kollisionsüberprüfung Asteroid <---> Welt
                if(oneAsteroid.getY() > myView.getHeight()) {
                    asteroidToRemove = oneAsteroid;
                }
            }

            // Kollisionsüberprüfung Bullet <---> Welt
            if(oneBullet.y < 10) {
                bulletToRemove = oneBullet;
            }

        }

        if(asteroidToRemove != null)
            removeAsteroid(asteroidToRemove);

        if(bulletToRemove != null)
            removeBullet(bulletToRemove);
    }

    private static void removeAsteroid(Asteroid asteroidToRemove) {
        myAsteroids.remove(asteroidToRemove);
    }
    private static void removeBullet(Bullet bulletToRemove) {
        myBullets.remove(bulletToRemove);
    }


    // !TODO Alles ins Model überführen!
}
