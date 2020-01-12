/**
 * @author Frederik Schönfeldt, Göran Kirchhoff & Muhialdeen Ibrahim
 * @version 1.0
 * @since 1.0
 *
 * History:
 * - 12.01.2020 Model hinzugefügt
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
    public Spaceship mySpaceship;
    public ArrayList<Bullet> myBullets;
    public ArrayList<Asteroid> myAsteroids = new ArrayList<>();

    // Werte für Countdown
    private int myCounter = 0;
    private CountDownTimer myTimer = new CountDownTimer(Long.MAX_VALUE, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            Log.d(TAG, "Model.myTimer.onTick():" + myCounter);

            if(myCounter % 3 == 0) {
                spawnAsteroid(100, 0); // !TODO mit Model Werten austauschen
            }

            myCounter++;
        }

        @Override
        public void onFinish() {
            Log.w(TAG, "Model.myTimer.onFinish(): Das hätte nie passieren dürfen");
            // Das darf nie passieren :-D
        }
    };

    public void startTimer() {
        myTimer.start();
    }

    public int getMyCounter() {
        return myCounter;
    }

    public void spawnAsteroid(int x, int y) {
        myAsteroids.add(new Asteroid(x,y));
    };
    // !TODO Alles ins Model überführen!
}
