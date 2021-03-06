/**
 * @author Frederik Schönfeldt, Göran Kirchhoff & Muhialdeen Ibrahim
 * @version 1.0
 * @since 1.0
 *
 * History:
 * - 01.12.2019 Implementierung
 * - 18.01.2020 Statische Referenz auf Model, statt klassenweite Initialisierung
 */

package de.fhfl.gpsmyass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Controller extends Activity implements SensorEventListener {
    //public static Model model = new Model();

    private static final String TAG = "hsflController";

    private TextView scoreView;
    private Button shootButton;

    public MeinTollesView spaceView;

    SensorManager mysensormanager;
    Sensor gyroskop;

    private Spaceship mySpaceShip;
    public static Boolean viewIsReady = false;

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate():");
        setContentView(R.layout.activity_main);

        // Sensorobjekt bekommen
        mysensormanager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyroskop = mysensormanager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // UI Elemente finden..
        shootButton = (Button) findViewById(R.id.btn_shoot);
        scoreView = (TextView) findViewById(R.id.score);

        // GPSMap View finden..
        spaceView = (MeinTollesView) findViewById(R.id.GpsMap);


        shootButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.d(TAG, "btn_shoot: onClick()");
                mySpaceShip.fire();
            }
        });

    }

    @Override
    protected void onStart() {
        Log.i(TAG, "Controller.onStart()");
        super.onStart();

        if(gyroskop != null) { // Prüfen ob der Sensor aktiv bzw. vorhanden ist
            mysensormanager.registerListener(this, gyroskop, SensorManager.SENSOR_DELAY_GAME);
            Model.startTimer();
        }
        else {
            Log.e(TAG, "Controller.onStart(): Gyroscope not found!!!");
            // !TODO Hier Toast/Meldung ausgeben, dass Gyroskop im Handy fehlt..
        }
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "Controller.onStop()");
        super.onStop();
        if (gyroskop != null) {
            mysensormanager.unregisterListener(this);
            Model.stopTimer();
        }
        else {
            Log.e(TAG, "Controller.onStop(): Gyroscope not found!!!");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.v(TAG, "Controller.onSensorChanged()");

        scoreView.setText("SCORE: " +Integer.toString(Model.score));

        if(Model.gameOver) {
            gameOver();
            Model.gameOver = false;
        }

        // Gucken, ob die Map (MeinTollesView) schon ready ist
        if(MeinTollesView.viewIsReady) {
            // Spaceship bekommen
            mySpaceShip = Model.mySpaceship;

            // Hier passiert der Spaß
            float yRot = event.values[0];
            float xRot = event.values[1];
            float zRot = event.values[2];

            // Spaceship bei Sensordatenänderung bewegen.. !TODO dirty
            mySpaceShip.move(xRot*10, 0); // !TODO ggf auch Y Koordinaten übergeben yRot*100
            spaceView.invalidate();
            // !DEBUG Log.v(TAG, "X Rotation: " + Float.toString(xRot) + "   Y Rotation: " + Float.toString(yRot) + "   Z Rotation: " + Float.toString(zRot));

        } else {
            Log.i(TAG, "Controller.onSensorChanged(): View is not ready yet!");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Muss wohl nicht verwendet werden aber vorhanden sein..
    }

    public void gameOver() {
        Model.setDefaults();
        Model.stopTimer();
        setContentView(R.layout.activity_end);
        startActivity(new Intent(Controller.this, ActivityEnd.class));
        finish();
    }
}
