/**
 * @author Frederik Schönfeldt, Göran Kirchhoff & Muhialdeen Ibrahim
 * @version 1.0
 * @since 1.0
 *
 * History:
 * - %
 */

package de.fhfl.gpsmyass;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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

    public LocationListener locationListener;

    private static final String TAG = "hsflController";
    private final int zweiUndVierzig = 42;

    private TextView debugView;
    private Button shootButton;
    private Button restartButton;
    private Button resetButton;
    private String str;

    public MeinTollesView gpsMap;

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

        // Register the listener with the Location Manager to receive location updates
        // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener); // via WLAN
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, zweiUndVierzig);
//        }

        // Acquire a reference to the system Location Manager
//        final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Sensorobjekt bekommen
        mysensormanager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        gyroskop = mysensormanager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        // UI Elemente finden..
        debugView = (TextView) findViewById(R.id.debug_info);
        debugView.setText("Anwendung gestartet! (onCreate)");

        shootButton = (Button) findViewById(R.id.btn_shoot);
        restartButton = (Button) findViewById(R.id.btn_restart);
        resetButton = (Button) findViewById(R.id.btn_reset);

        // GPSMap View finden..
        gpsMap = (MeinTollesView) findViewById(R.id.GpsMap);



        shootButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Log.i(TAG, "btn_shoot: onClick()");
                mySpaceShip.fire();
                // Define a listener that responds to location updates
//                locationListener = new LocationListener() {
//                    public void onLocationChanged(Location location) {
//                        // Called when a new location is found by the network location provider.
//                        str = "onLocationChanged(Location: " + location.toString() + ")";
//
//
//                        double y = location.getLatitude();
//                        double x = location.getLongitude();
//
//
//                        Log.d(TAG, str);
//                        debugView.setText(str);
//
//                       /*
//                            Beispielrechnung
//                            Umrechnung GPS Koordinaten => Pixel Koordinaten
//                            xGPSmax - xGPSmin = xGPS_Spektrum
//                            xPixel = xPixel_Spektrum
//
//                            Eingabe: xGPS Koordinate (muss dann -xGPSmin gerechnet werden)
//                            Ergebnis: xGPS_Koord_in_Spektrum
//
//                            xGPS_Spektrum / xGPS_Koord_in_Spektrum = Prozentuale Position im Spektrum
//
//                            Diese Prozente können dann benutzt werden, um die Pixel Koordinate auszurechnen.
//
//                         */
//                        // GPS Koordinaten ausrechnen
//                        gpsMap.calculateCoordinates(x,y);
//                        // GPS Map neu zeichnen lassen
//                        gpsMap.invalidate();
//
//                        //GpsMap.drawGpsCoordinates(test, x, y);
//                    }
//
//                    public void onStatusChanged(String provider, int status, Bundle extras) {
//                        Log.d(TAG, "onStatusChanged(provider: " + provider + " status: " + status + " extras: " + extras.toString());
//                        Log.d(TAG, str);
//                        debugView.setText(str);
//                    }
//
//                    public void onProviderEnabled(String provider) {
//                        str = "onProviderEnabled(provider: " + provider;
//                        Log.d(TAG, str);
//                        debugView.setText(str);
//                    }
//
//                    public void onProviderDisabled(String provider) {
//                        str = "onProviderDisabled(provider: " + provider;
//                        Log.d(TAG, str);
//                        debugView.setText(str);
//                    }
//                };
//
//
//                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
//                        0, 0, locationListener); // via GPS
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            // Bei Klick die Updates des Listeners deaktivieren..
            @Override
            public void onClick(View v) {
                Log.i(TAG, "btn_restart: onClick()");
//                locationManager.removeUpdates(locationListener); // https://stackoverflow.com/a/39367402
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i(TAG, "btn_reset: onClick()");
//                gpsMap.calculateCoordinates(-100000000,-100000000);
                // GPS Map neu zeichnen lassen
                gpsMap.invalidate();
            }
        });

    }

    @Override
    protected void onStart() {
        Log.i(TAG, "Controller.onStart()");
        super.onStart();

        if(gyroskop != null) { // Prüfen ob der Sensor aktiv bzw. vorhanden ist
            mysensormanager.registerListener(this, gyroskop, SensorManager.SENSOR_DELAY_GAME);
        }
        else {
            Log.e(TAG, "Controller.onStart(): Gyroscope not found!!!");
            // Hier Toast/Meldung ausgeben, dass Gyroskop im Handy fehlt..
        }
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "Controller.onStop()");
        super.onStop();
        if (gyroskop != null) {
            mysensormanager.unregisterListener(this);
        }
        else {
            Log.e(TAG, "Controller.onStop(): Gyroscope not found!!!");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.v(TAG, "Controller.onSensorChanged()");

        // Gucken, ob die Map (MeinTollesView) schon ready ist
        if(MeinTollesView.viewIsReady) {
            // Spaceship bekommenee
            mySpaceShip = gpsMap.mySpaceShip;

            // Hier passiert der Spaß
            float yRot = event.values[0];
            float xRot = event.values[1];
            float zRot = event.values[2];

            // Spaceship bei Sensordatenänderung bewegen.. !TODO dirty
            mySpaceShip.move(xRot*10, 0); // !TODO ggf auch Y Koordinaten übergeben yRot*100
            gpsMap.invalidate();
            Log.v(TAG, "X Rotation: " + Float.toString(xRot) + "   Y Rotation: " + Float.toString(yRot) + "   Z Rotation: " + Float.toString(zRot));

        } else {

            Log.i(TAG, "Controller.onSensorChanged(): View is not ready yet!");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Muss wohl nicht verwendet werden aber vorhanden sein..
    }
}
