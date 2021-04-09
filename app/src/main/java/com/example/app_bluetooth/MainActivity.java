package com.example.app_bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> mDeviceList = new ArrayList<String>();
    private Set<BluetoothDevice> btDevices;
    static volatile BluetoothAdapter BTadapter = null;

    private BluetoothSocket mbtSocket;
    protected OutputStream outputStream = null;
    protected InputStream inputStream = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BTadapter = BluetoothAdapter.getDefaultAdapter();

        listView = (ListView) findViewById(R.id.lvopcoes);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object listItem = listView.getItemAtPosition(position);
                String deviceMac = listItem.toString().substring(0, listItem.toString().indexOf("-") - 1);
                setDeviceBluetooth(deviceMac);
            }
        });

        Button btnListar = findViewById(R.id.btnListar);
        btnListar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getBluetoothPairedDevices(listView);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void getBluetoothPairedDevices(final ListView listView) {
        mDeviceList.clear();
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "This device not support bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableAdapter, 0);
            }
            Set<BluetoothDevice> all_devices = bluetoothAdapter.getBondedDevices();
            if (all_devices.size() > 0) {
                for (BluetoothDevice currentDevice : all_devices) {
                    mDeviceList.add(currentDevice.getAddress() + " - " + currentDevice.getName());
                    listView.setAdapter(new ArrayAdapter<>(getApplication(), android.R.layout.simple_list_item_1, mDeviceList));
                }
            }
        }
    }

    private void setDeviceBluetooth(final String deviceMAC) {
        final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        this.btDevices = BTadapter.getBondedDevices();
        if (deviceMAC != null) {
            if (this.btDevices != null) {
                Iterator var3 = this.btDevices.iterator();
                while (var3.hasNext()) {
                    final BluetoothDevice btDevice = (BluetoothDevice) var3.next();
                    if (deviceMAC.equals(btDevice.getAddress())) {
                        Thread connectThread = new Thread(new Runnable() {
                            public void run() {
                                try {
                                    mbtSocket = btDevice.createRfcommSocketToServiceRecord(SPP_UUID);
                                    mbtSocket.connect();
                                    outputStream = mbtSocket.getOutputStream();
                                    inputStream = mbtSocket.getInputStream();
                                    Log.i("GERTEC", "Impressora Connectada: " + deviceMAC);
                                } catch (IOException var4) {
                                    IOException ex = var4;
                                    try {
                                        ex.printStackTrace();
                                        mbtSocket.close();
                                    } catch (IOException var3) {
                                        Log.i("GERTEC ERROR", var3.getMessage());
                                        var3.printStackTrace();
                                    }

                                    mbtSocket = null;
                                    setDeviceBluetooth(deviceMAC);
                                }
                            }
                        });
                        connectThread.start();
                    }
                }
            }

        }
    }
}
