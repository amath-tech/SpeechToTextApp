package com.amath.speechtotext;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class TxtToSpeech extends Fragment {

    TextToSpeech textToSpeech;
    TextInputEditText text;
    ImageView imageView,newPage,textSize,saveToSd;
    ListView listView;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 101;
    String mmText;
    //SwipeRefreshLayout swipeRefreshLayout;

    public TxtToSpeech(){


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_page2,container,false);

        text  = view.findViewById(R.id.Text);
         imageView = view.findViewById(R.id.imageView);
         newPage = view.findViewById(R.id.newPage);
         textSize = view.findViewById(R.id.textSize);
         saveToSd = view.findViewById(R.id.saveToSd);
         //swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);

        newPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment mFragment = new TxtToSpeech();
                requireFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag, mFragment).commit();


                text.setTextSize(14);


            }
        });

         textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                if (i != TextToSpeech.ERROR){

                    textToSpeech.setLanguage(Locale.UK);

                }

            }
        });



         imageView.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View view) {


                 textToSpeech.speak(Objects.requireNonNull(text.getText()).toString(),TextToSpeech.QUEUE_FLUSH,null);

                 imageView.setImageAlpha(20);


             }
         });

        saveToSd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mmText = text.getText().toString().trim();

                if (mmText.isEmpty()){

                    Toast.makeText(requireContext(), "You have not enter text!", Toast.LENGTH_SHORT).show();

                }else{

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){

                            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

                            requestPermissions(permissions,WRITE_EXTERNAL_STORAGE_CODE);

                        }else {

                            saveToTxtFile(mmText);

                        }

                    }else {

                        saveToTxtFile(mmText);

                    }

                }

            }
        });


        textSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setMessage("Are you sure you want to increase your text size?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                text.setTextSize(20);

                                dialogInterface.dismiss();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                text.setTextSize(16);

                            }
                        }).show();



            }
        });

         return  view;

    }

    private void saveToTxtFile(String mText) {

        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss",
                Locale.getDefault()).format(System.currentTimeMillis());

        try   {

            //path external storage
            File path = Environment.getExternalStorageDirectory();
            //create folder name "My file
            File dir = new File(path + "/TextToSpeech/");
            dir.mkdirs();

            String fileName = "TextToSpeech" + timeStamp + ".txt"; //e.g MyFile_20180623_152322.txt

            File file  = new File(dir,fileName);
            FileWriter fw = new FileWriter(file.getAbsoluteFile());//use to character in files
            BufferedWriter bw  = new BufferedWriter(fw);
            bw.write(mmText);
            bw.close();

            Toast.makeText(requireContext(), fileName+ " is saved to \n" +dir , Toast.LENGTH_SHORT).show();

        }catch (Exception e){

            Toast.makeText(requireContext(), e.getMessage() , Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){

            case WRITE_EXTERNAL_STORAGE_CODE:

                if (grantResults.length > 0 && grantResults[0] ==  PackageManager.PERMISSION_GRANTED){

                    saveToTxtFile(mmText);

                }else{

                    Toast.makeText(requireContext(), "Storage permission is required to save data", Toast.LENGTH_SHORT).show();

                }

        }
    }

}



