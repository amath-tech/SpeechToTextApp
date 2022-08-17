package com.amath.speechtotext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import hotchemi.android.rate.AppRate;

public class MainActivity extends AppCompatActivity {

   BottomNavigationView bottomNavigationView;
   
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppRate.with(this)

                // default 10
                .setInstallDays(10)

                // default 10
                .setLaunchTimes(10)

                // default 1
                .setRemindInterval(10)
                .monitor();

        // Show a dialogue
        // if meets conditions
        AppRate
                .showRateDialogIfMeetsConditions(
                        this);

        bottomNavigationView = findViewById(R.id.bottom_navigation);


        myFragment(new SpeechToText());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.speechToText){

                    myFragment(new SpeechToText());

                }
                if (item.getItemId() == R.id.textToSpeech){

                    myFragment(new TxtToSpeech());

                }

                if (item.getItemId() == R.id.settings){

                    myFragment(new Settings());

                }

                return true;
            }
        });

    }



    private  void myFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();


    }

}