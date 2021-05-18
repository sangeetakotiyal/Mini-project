package com.grimnirdesign.scrollingshooter;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.grimnirdesigns.scrollingshooter.R;


public class GameMenu extends Activity {

    private Button mStartButton;
    private Button mInstructionsButton;
    private Button mAboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mStartButton = (Button) findViewById(R.id.startButton);
        mInstructionsButton = (Button) findViewById(R.id.instructionsButton);
        mAboutButton = (Button) findViewById(R.id.aboutButton);

        mInstructionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new InstructionsDialogFragment();
                newFragment.show(getFragmentManager(), "Instructions");
            }
        });

        mAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new AboutDialogFragment();
                newFragment.show(getFragmentManager(), "About");
            }
        });

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

            }
        });
    }


}
