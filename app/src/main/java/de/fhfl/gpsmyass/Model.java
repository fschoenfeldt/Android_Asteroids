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

import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class Model {
    private static final String TAG = "hsflModel";
    public static Spaceship mySpaceship;
    public static ArrayList<Bullet> myBullets = new ArrayList<Bullet>();
    public static ArrayList<Asteroid> myAsteroids = new ArrayList<Asteroid>();

    // Konfigurationsvariablen, könnten an eine Schwierigkeitsstufe gebunden werden
    public static int bulletSpeed = 20; // Standard: 20
    public static int asteroidSpeed = 3; // Standard: 3
    public static int asteroidSpawnSpeed = 10;

    // Sonstige Variablen
    public static int score = 0;
    public static boolean gameOver = false;
    private static float viewHeight = 0;
    private static float viewWidth = 0;
    private static int countDownInterval = 100;

    // Werte für Countdown
    private static int myCounter = 0;
    private static final Random rand = new Random();

    private static CountDownTimer myTimer = new CountDownTimer(Long.MAX_VALUE, countDownInterval) {
        @Override
        public void onTick(long millisUntilFinished) {
            Log.d(TAG, "Model.myTimer.onTick():" + myCounter);
            int nextX = rand.nextInt((int)viewWidth - 50);
            int nextY = rand.nextInt((int)viewHeight);

            if(myCounter % asteroidSpawnSpeed == 0) {
                if(nextX < 50) {
                    nextX = 50;
                }
                spawnAsteroid(nextX, 0); // !TODO Y-Wert vielleicht immer 0, damit einfacher
            }

            if(myCounter % 60 == 0 && asteroidSpawnSpeed > 5) {
                asteroidSpawnSpeed--;
                asteroidSpeed++;
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

    public static void stopTimer() { myTimer.cancel(); }

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

        for(Asteroid oneAsteroid : myAsteroids) { //!INFO For of Schleife erlaubt keine Löschung von Objekten, während man durch das Objekt iteriert..
            for(Bullet oneBullet : myBullets) {
                // Kollisionsüberprüfung Bullet <---> Asteroid
                if(oneBullet.getX() - oneAsteroid.getX() < 50 && oneBullet.getX() - oneAsteroid.getX() > -50  && oneBullet.getY() - oneAsteroid.getY() < 50 && oneBullet.getY() - oneAsteroid.getY() > -50) { //!TODO Radius als Variable sodass Objekte verschieden Groß sein können
                    Log.i(TAG, "Model.checkCollision(): Kollision gefunden!");
                    asteroidToRemove = oneAsteroid;
                    bulletToRemove = oneBullet;
                    Model.score += 100;
                }
                // Kollisionsüberprüfung Bullet <---> Welt
                if(oneBullet.y < 10) {
                    bulletToRemove = oneBullet;
                }
            }
            // Kollisionsüberprüfung Asteroid <---> Welt
            if(oneAsteroid.getY() > myView.getHeight()) {
                asteroidToRemove = oneAsteroid;
            }
            // Kollisionsüberprüfung Asteroid <---> Spaceship
            if ( mySpaceship.getX() - oneAsteroid.getX() < 50 && mySpaceship.getX() - oneAsteroid.getX() > -70  && mySpaceship.getY() - oneAsteroid.getY() < 50 && mySpaceship.getY() - oneAsteroid.getY() > -50) {
                asteroidToRemove = oneAsteroid;
                Log.i(TAG, "Model.checkCollision(): Kollision zwischen Spieler und Asteroid!");
                gameOver = true;
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

    public static void setDefaults() {
        bulletSpeed = 20;
        asteroidSpeed = 3;
        asteroidSpawnSpeed = 10;
    }

    // !TODO Alles ins Model überführen!
}
