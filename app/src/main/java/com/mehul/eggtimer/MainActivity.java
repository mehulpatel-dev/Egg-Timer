package com.mehul.eggtimer;
/*
    Name: Egg Timer
    Author: Mehul Patel
    Date: 01/24/2020
    Description: An egg timer that allows the user to use the slider to set a time and press the
                 button to begin count down
 */

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // creating variables for the seekbar, text view, button, count down timer, and boolean to track
    // if counter active
    SeekBar timerBar;
    TextView timerText;
    Button timerButton;
    Boolean counterIsActive = false;
    CountDownTimer countDown;

    // method to update the current status of the timer in the text view and add a 0 in front of the
    // single digit numbers
    public void updateTimer (int secondsLeft){
        int minutes = (int) secondsLeft / 60;
        int seconds = secondsLeft - minutes * 60;

        String secondString = Integer.toString(seconds);
        if(seconds <= 9){
            secondString = "0" + secondString;
        }

        timerText.setText(Integer.toString(minutes) + ":" + Integer.toString(seconds));
    }

    //method to reset the timer to default
    public void resetTimer(){
        timerText.setText("0:30");
        timerBar.setEnabled(true);
        timerBar.setProgress(30);
        countDown.cancel();
        timerButton.setText("Start");
        counterIsActive = false;
    }

    //method runs when the button is pressed
    public void startStop (View v) {
        // condition to run code, if counter/countdown is not active then set it to active,
        // grey out/disable seekbar so user can not slide it mid countdown, and change text on
        // button to Stop
        if (counterIsActive = false) {
            counterIsActive = true;
            timerBar.setEnabled(false);
            timerButton.setText("Stop");

            //test to check if button is pressed
            System.out.println("button pressed");

            // declare the countDown variable and set the number of milliseconds until completed and
            // the interval it runs at
            // adding the 1 tenth of a second (100millisecond) will allow to address the delay towards
            // the end of the counter.
            countDown = new CountDownTimer(timerBar.getProgress() * 1000 + 100, 1000) {

                //This method updates the timer by calling the updateTimer method at every interval
                // long l is the seconds left in millisecond
                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);
                }

                //sounds the airhorn audio and resets the timer by calling the resetTimer() method
                @Override
                public void onFinish() {
                    MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mPlayer.start();
                    resetTimer();
                }
            }.start();
        }else{
            resetTimer();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //declaring the variables for the seek bar, text view, and button
        timerBar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerText = (TextView) findViewById(R.id.timerTextView);
        timerButton = (Button) findViewById(R.id.timerButton);

        //set max length of the seek bar to 60 and default(starting point) at 30
        timerBar.setMax(600);
        timerBar.setProgress(30);

        //creating/setting a listener on the seek bar for any changes that occur to it
        timerBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            //this method calles the updateTimer() method and pushes the i value(progress or current
            // place on the seek bar) to it when the seek bar is changed/slid

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
