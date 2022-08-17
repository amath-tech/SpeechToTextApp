package com.amath.speechtotext;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


public class Settings extends Fragment {

    LinearLayout first,sixth,fourth,fifth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings,container,false);

         first = view.findViewById(R.id.first);
         fourth = view.findViewById(R.id.fourth);
         fifth = view.findViewById(R.id.fifth);
         sixth = view.findViewById(R.id.sixth);


         first.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {


                 AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                 builder.setTitle("About")
                         .setMessage("This is a speech to text app. \n" +
                                 " Created by Amath Eo \n" +
                                 " Develop in 2022, Version 1.0.0")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                    }
                }).show();
             }
         });






        fourth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Frequently asked questions")
                        .setMessage("Ok so let me try to answer a few commonly asked questions. \n \n \n" +
                                " Can I use this app offline? \n Yes,  but you must be on Android 4.0+ \n \n" +
                                "Where are files saved to? \n Files are saved on an internal or external SD card. \n \n" +
                                "If there is anything to tell the developer or if you discover bug, Even if it is suggestions send an email to amath.eo000000@gmail.com ")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                }).show();

            }
        });


        fifth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              Intent sendIntent =  new Intent();
              sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out this app: http://play.google.com/store/apps/details?id=com.amath.speechtotext;");
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, "Share app via");
                startActivity(shareIntent);

            }
        });

        sixth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse
                            ("http://play.google.com/store/apps/details?id=com.amath.speechtotext;")));


                }catch (ActivityNotFoundException e){

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse
                            ("http://play.google.com/store/apps/details?id=com.amath.speechtotext")));

                }

            }
        });

        return view;

    }
}
