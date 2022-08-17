package com.amath.speechtotext;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class SpeechToText extends Fragment {

    //private static final int RESULT_OK = 101;
    ImageView imageView,newPage,saveWork,moreOption;
    TextView textView;
    String mText;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 101;

    public SpeechToText(){



    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page1,container,false);

        imageView = view.findViewById(R.id.btnSpeak);
        textView = view.findViewById(R.id.text);
        newPage = view.findViewById(R.id.newPage);
        saveWork = view.findViewById(R.id.saveWork);


        newPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

            }
        });

        saveWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mText = textView.getText().toString().trim();

                if (mText.isEmpty()){

                    Toast.makeText(requireContext(), "Speech is yet to be converted to text.", Toast.LENGTH_SHORT).show();

                }else{

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                        if (ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){

                            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

                            requestPermissions(permissions,WRITE_EXTERNAL_STORAGE_CODE);

                        }else {

                            saveToTxtFile(mText);

                        }

                    }else {

                        saveToTxtFile(mText);

                    }

                }

            }
        });




        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition");
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                    textView.setText("");
                }
                catch(ActivityNotFoundException e)
                {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW,   Uri.parse("https://market.android.com/details?id=com.amath.speechtotext"));
                    startActivity(browserIntent);

                }

            }
        });

        return view;

    }

    private void saveToTxtFile(String mText) {

        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss",
                Locale.getDefault()).format(System.currentTimeMillis());

   try   {

       //path external storage
    File path = Environment.getExternalStorageDirectory();
    //create folder name "My file
       File dir = new File(path + "/SpeechToText/");
       dir.mkdirs();

       String fileName = "SpeechToText" + timeStamp + ".txt"; //e.g MyFile_20180623_152322.txt

       File file  = new File(dir,fileName);
       FileWriter fw = new FileWriter(file.getAbsoluteFile());//use to character in files
       BufferedWriter bw  = new BufferedWriter(fw);
       bw.write(mText);
       bw.close();

       Toast.makeText(requireContext(), fileName+ " is saved to \n" +dir , Toast.LENGTH_SHORT).show();

   }catch (Exception e){

       Toast.makeText(requireContext(), e.getMessage() , Toast.LENGTH_SHORT).show();
   }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == Activity.RESULT_OK && data != null) {

                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                textView.setText(result.get(0));

            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){

            case WRITE_EXTERNAL_STORAGE_CODE:

                if (grantResults.length > 0 && grantResults[0] ==  PackageManager.PERMISSION_GRANTED){

                    saveToTxtFile(mText);

                }else{

                    Toast.makeText(requireContext(), "Storage permission is required to save data", Toast.LENGTH_SHORT).show();

                }

        }
    }
}
