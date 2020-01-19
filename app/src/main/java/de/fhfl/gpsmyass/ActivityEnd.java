package de.fhfl.gpsmyass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ActivityEnd extends Activity {
    private static final String TAG = "hsflActivityStart";
    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Model.gameOver = false;
        super.onCreate(savedInstanceState);
        Log.i(TAG, "ActivityEnd.onCreate():");
        setContentView(R.layout.activity_end);

        Button startGame = findViewById(R.id.start_game_btn);

        Model.myBullets = new ArrayList<Bullet>();
        Model.myAsteroids = new ArrayList<Asteroid>();

        TextView scoreView = (TextView)findViewById(R.id.scoreView);
        scoreView.setText("YOUR SCORE: \n" + Integer.toString(Model.score));

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.score = 0;
                setContentView(R.layout.activity_start);
                startActivity(new Intent(ActivityEnd.this, Controller.class));
                finish();
            }
        };
        startGame.setOnClickListener(onClickListener);
    }


}