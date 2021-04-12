package com.example.app_bluetooth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button buttonStartConnection = findViewById(R.id.buttonStartConnection);
        Button buttonFinishConnection = findViewById(R.id.buttonFinishConnection);
        Button buttonRFBYPRESENCE = findViewById(R.id.buttonRFBYPRESENCE);
        Button buttonRFON = findViewById(R.id.buttonRFON);
        Button buttonRFOFF = findViewById(R.id.buttonRFOFF);

        buttonStartConnection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    //COMMAND TO RF OFF - #01
                    byte[] abytes = {35, 48, 49, 92, 114, 92, 110};
                    MainActivity.outputStream.write(abytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonFinishConnection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    //COMMAND TO RF OFF - #00
                    byte[] abytes = {35, 48, 48, 92, 114, 92, 110};
                    MainActivity.outputStream.write(abytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonRFON.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    //COMMAND TO OFF - #6400
                    byte[] abytes = {35, 54, 52, 48, 48, 92, 114, 92, 110};
                    MainActivity.outputStream.write(abytes);
                    //MainActivity.outputStream.write(new byte[]{0x23,6,4,0,0,0x0d,0x0a});
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonRFBYPRESENCE.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    //COMMAND TO RF BY PRESENCE - #641
                    byte[] abytes = {35, 54, 52, 49, 92, 114, 92, 110};
                    MainActivity.outputStream.write(abytes);
                    //MainActivity.outputStream.write(new byte[]{0x23,6,4,1,0x0d,0x0a});
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonRFOFF.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    //COMMAND TO RF OFF - #6401
                    byte[] abytes = {35, 54, 52, 48, 49, 92, 114, 92, 110};
                    MainActivity.outputStream.write(abytes);
                    //MainActivity.outputStream.write(new byte[]{0x23,6,4,0,1,0x0d,0x0a});
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
