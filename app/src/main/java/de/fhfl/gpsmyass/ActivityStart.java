package de.fhfl.gpsmyass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ActivityStart extends Activity {
    private static final String TAG = "hsflActivityStart";
    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "ActivityStart.onCreate():");
        setContentView(R.layout.activity_start);

        Button startGame = findViewById(R.id.start_game_btn);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_main);
                startActivity(new Intent(ActivityStart.this, Controller.class));
                finish();
            }
        };
        startGame.setOnClickListener(onClickListener);
    }


}