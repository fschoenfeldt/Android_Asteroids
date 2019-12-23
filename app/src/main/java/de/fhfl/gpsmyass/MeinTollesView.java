/** Custom View Klasse für GPS Karte
 * @author Frederik Schönfeldt, Göran Kirchhoff & Muhialdeen Ibrahim
 * @version 1.4
 * @since 1.3
 *
 * History:
 * - 1.3 Eigenes View auf dem gezeichnet werden kann
 * - 1.4 Anpassungen für Asteroids
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
    public static Spaceship mySpaceShip;  // !TODO zum Model hinzufügen

    public static ArrayList<Bullet> myBullets = new ArrayList<Bullet>(); // !TODO zum Model hinzufügen


    public MeinTollesView(Context context) {
        super(context);
    }

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


        mySpaceShip = new Spaceship(pixWidth/2,pixHeight-100); // !TODO

        // !TODO sauberer machen!
        /*Bullet bulletOne = new Bullet(pixWidth/3, 100);
        Bullet bulletTwo = new Bullet(pixWidth/2, 200);

        myBullets.add(bulletOne);
        myBullets.add(bulletTwo);*/

        // für andere Klassen, um zu wissen, dass die Breite und Höhe nun gesetzt sind.
        viewIsReady = true;
        Log.i(TAG, "MeinTollesView.onSizeChanged(): View ist nun ready");
    }

    /*public MeinTollesView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }*/


    @Override
    protected void onDraw(Canvas canvas)   // Hier die Grafik ausgeben
    {
        super.onDraw(canvas);
        Log.v(TAG, "onDraw():  ");


        //Controller activity = (Controller) this.getContext();
        // Hintergrund fuellen
        //canvas.drawRGB(255, 255, 50);

        rView.set(0, 0, this.getWidth(), this.getHeight());

        // Karte darstellen
        canvas.drawBitmap(fhMap, rKarte, rView, myPaint);

        //double x = activity
        // eine Linie zeichnen
        //canvas.drawLine(5f, 5f, pixWidth-5f, pixHeight - 5f, myPaint);
        /*
        Links oben: 54.776627 -> x = 0
                    9.448113 -> y = 0
        Rechts unten: 54.774028 -> x = pixWidth (z.b 600px)
                      9.453253 -> y = pixHeight (z.b. 600px)

        Beispiel
          x: 54.7754
            y: 9.4524

                      locX =
         */
        // pixelX =
        // pixelY = ...
        //canvas.drawCircle(pixelX, pixelY, 20, myPaint);

        mySpaceShip.draw(canvas);

        // Alle Bullets an ihrer aktuellen Position zeichnen..
        for(Bullet oneBullet : myBullets) {
            oneBullet.move(oneBullet.getX(), oneBullet.getY()-3);
            oneBullet.draw(canvas);
        }

        // ein Text
        // canvas.drawText("don't forget the droids", 10, 25, myPaint);

    }

    /*public Spaceship getMySpaceShip() {
        return mySpaceShip;
    }*/

    // Koordinatengrenzen der Karte
/*    private double[] GpsLinksOben = {54.776627,9.448113}; // x,y
    private double[] GpsRechtsUnten = {54.774028, 9.453253}; // x,y

    private double GpsYSpektrum = GpsLinksOben[0] - GpsRechtsUnten[0];
    private double GpsXSpektrum = GpsRechtsUnten[1] - GpsLinksOben[1];

    public void calculateCoordinates(double currentPositionGpsX, double currentPositionGpsY) {

        Log.d(TAG, "calculateCoordinates()");
        Log.d(TAG, "pixWidth:" + pixWidth);
        Log.d(TAG, "pixHeight:" + pixHeight);


        // Berechnung X Achse
        double XCoordInSpectrum = currentPositionGpsX - GpsLinksOben[1];
        Log.d(TAG, "GpsXSpektrum: " + GpsXSpektrum);
        Log.d(TAG, "XCoordInSpectrum: " + XCoordInSpectrum);
        double XCoordInPercent = XCoordInSpectrum / GpsXSpektrum;
        Log.d(TAG, "XCoordInPercent: " + XCoordInPercent);
        double relativePixelPositionX = pixWidth * XCoordInPercent;

        Log.d(TAG, "relativePixelPositionX: " + relativePixelPositionX);

        pixelX = (int)relativePixelPositionX;

        // Berechnung Y Achse
        double YCoordInSpectrum = currentPositionGpsY - GpsRechtsUnten[0];
        Log.d(TAG, "GpsYSpektrum: " + GpsYSpektrum);
        Log.d(TAG, "YCoordInSpectrum: " + YCoordInSpectrum);
        double YCoordInPercent = YCoordInSpectrum / GpsYSpektrum;
        Log.d(TAG, "YCoordInPercent: " + YCoordInPercent);
        double relativePixelPositionY = pixHeight * (1.0-YCoordInPercent);

        Log.d(TAG, "relativePixelPositionY: " + relativePixelPositionY);

        pixelY = (int)relativePixelPositionY;

        // return;
    }*/

}
