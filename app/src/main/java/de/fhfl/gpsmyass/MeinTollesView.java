/** Custom View Klasse für GPS Karte
 * @author Frederik Schönfeldt, Göran Kirchhoff & Muhialdeen Ibrahim
 * @version 1.4
 * @since 1.3
 *
 * History:
 * - 1.3 Eigenes View auf dem gezeichnet werden kann
 * - 1.4 Anpassungen für Asteroids
 * - 18.01.2020 2.0 - Statische Zugriffe auf das Model, um Objekte wie Asteroids, Bullets & Spaceship zu bekommen
 *                  - Alte Codefragmente und Kommentare entfernt
 *                  - Überladener Konstruktor MeinTollesView(x,y) als Standard genommen, alten Konstruktor entfernt
 */
package de.fhfl.gpsmyass;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MeinTollesView extends View {
    private static final String TAG = "hsflMeinTollesView";
    private Paint myPaint;

    // von: Android More Apps pdf
    private Bitmap fhMap;
    private Rect rView;
    private Rect rKarte;

    private int pixelX = 0;
    private int pixelY = 0;

    // Breite und Höhe des Views in Pixel
    private float pixWidth;
    private float pixHeight;

    public static Boolean viewIsReady = false;

    // Statischer Zugriff auf das Model
    public static Spaceship mySpaceShip = Model.mySpaceship;
    public static ArrayList<Bullet> myBullets = Model.myBullets;

    // dieser Konstruktor wird aufgerufen, wenn das GUI-Element in der XML-Datei definiert ist
    public MeinTollesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.v(TAG, "MeinTollesView(Context context, AttributeSet attrs):  ");
        // TODO Auto-generated constructor stub
        myPaint = new Paint();
        myPaint.setStyle(Paint.Style.FILL);
        myPaint.setTextSize(40);
        myPaint.setStrokeWidth(5);
        myPaint.setColor(Color.BLACK);

        fhMap = BitmapFactory.decodeResource(getResources(), R.drawable.spacy_background);
        rView = new Rect();
        rKarte = new Rect();
        rKarte.set(0,0,fhMap.getWidth(),fhMap.getHeight());

    }

    // https://developer.android.com/reference/android/view/View#onSizeChanged(int,%20int,%20int,%20int)
    // https://stackoverflow.com/a/4530473
    // onSizeChanged() wird aufgerufen, wenn sich die Abmessungen des Views ändern.
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // Pixel Abmessungen des Views
        pixWidth = (int)this.getWidth();
        pixHeight = (int)this.getHeight();
        Log.d(TAG, "MeinTollesView.onSizeChanged(): pixWidth: " + pixWidth + " pixHeight: " + pixHeight);

        mySpaceShip = new Spaceship(pixWidth/2,pixHeight-100); // !TODO ggf. über Model-Methode mit Parameter laufen lassen..

        // für andere Klassen, um zu wissen, dass die Breite und Höhe nun gesetzt sind.
        viewIsReady = true;
        Log.i(TAG, "MeinTollesView.onSizeChanged(): View ist nun ready");
    }

    @Override
    protected void onDraw(Canvas canvas)   // Hier die Grafik ausgeben
    {
        super.onDraw(canvas);
        Log.v(TAG, "onDraw():  ");

        //Controller activity = (Controller) this.getContext();
        rView.set(0, 0, this.getWidth(), this.getHeight());

        // Karte darstellen
        canvas.drawBitmap(fhMap, rKarte, rView, myPaint);

        mySpaceShip.draw(canvas);

        // Alle Bullets & Asteroiden an ihrer aktuellen Position zeichnen..
        for(Bullet oneBullet : myBullets) {
            oneBullet.move(oneBullet.getX(), oneBullet.getY()-Model.bulletSpeed);
            oneBullet.draw(canvas);
        }

        for(Asteroid oneAsteroid : Model.myAsteroids) {
            oneAsteroid.move(oneAsteroid.getX(), oneAsteroid.getY()+Model.asteroidSpeed);
            oneAsteroid.draw(canvas);
        }

        // ein Text
        // canvas.drawText("don't forget the droids", 10, 25, myPaint);

    }

}
