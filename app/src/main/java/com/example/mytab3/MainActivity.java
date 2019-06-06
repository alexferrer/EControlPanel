package com.example.mytab3;

import android.arch.lifecycle.ViewModelProviders;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mytab3.ui.main.PageViewModel;
import com.example.mytab3.ui.main.SectionsPagerAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {
    private PageViewModel model ;
    private Sensors sensors = Sensors.getInstance();
    private static final String TAG = "Main";
    private int num = 0;
    //private Handler mainHandler = new Handler();

    //bluetooth variables
    private BluetoothAdapter mBTAdapter;
    private Handler mHandler; // Our main handler that will receive callback notifications
    private ConnectedThread mConnectedThread; // bluetooth background worker thread to send and receive data
    private BluetoothSocket mBTSocket = null; // bi-directional client-to-client data path

    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // "random" unique identifier


    // #defines for identifying shared types between calling functions
    private final static int REQUEST_ENABLE_BT = 1; // used to identify adding bluetooth names
    private final static int MESSAGE_READ = 2; // used in bluetooth handler to identify message update
    private final static int CONNECTING_STATUS = 3; // used in bluetooth handler to identify message status


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG,"OnCreate ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        model = ViewModelProviders.of(this).get(PageViewModel.class);
        Log.e(TAG,model.toString());

        mBTAdapter = BluetoothAdapter.getDefaultAdapter(); // get a handle on the bluetooth radio
        Log.e(TAG,"calling for direct connection ");
        connectBT("98:D3:32:31:25:32");



        mHandler = new Handler(){
            public void handleMessage(android.os.Message msg){
                if(msg.what == MESSAGE_READ){
                    String readMessage = null;
                    try {
                        readMessage = new String((byte[]) msg.obj, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    //send the message to be processed
                    talk_to_frag1(readMessage);
                }

                /**
                if(msg.what == CONNECTING_STATUS){
                    if(msg.arg1 == 1)
                        mBluetoothStatus.setText("Connected to Device: " + (String)(msg.obj));
                    else
                        mBluetoothStatus.setText("Connection Failed");
                }***/
            }
        };


    }

    public void talk_to_frag1(String bt_data){
        num++;
        model.getCurrentName().setValue(""+num);

        //split incoming string, convert into array of strings
        //then parse individual data values
        //String(cnt)+","+String(mAmps)+","+String(mAIS)+","+String(mALT)+","+String(mVario)+","+String(mRPM)+","+String(mETemp)+","+String(mBTemp)+",END";
        String[] sArray = bt_data.split(",");
        //Load data strings from sensors into sensors helper class
        sensors.setmAmps(sArray[1]);
        sensors.setmVolts(sArray[2]);
        //Flight stats
        sensors.setmAIS( sArray[3] );
        sensors.setmAlt( sArray[4] );
        sensors.setmVario( sArray[5] );
        //Engine Stats
        sensors.setmRPM( sArray[6] );
        sensors.setmEngineTemp( sArray[7] );
        sensors.setmBatteryTemp( sArray[8] );

        
        //Power stats
        model.getmAmps().setValue( sensors.getmAmps() );
        model.getmVolts().setValue( sensors.getmVolts() );
        model.getmWatts().setValue( (sensors.getmWatts()  ) );
        model.getmWattsTime().setValue( sensors.getmWattsTime()  );
        //Flight stats
        model.getmAIS().setValue( sensors.getmAIS() );
        model.getmAlt().setValue( sensors.getmAlt() );
        model.getmVario().setValue( sensors.getmVario() );
        model.getmTotEnergy().setValue( sensors.getmTotEnergy()  );
        //Engine Stats
        model.getmRPM().setValue( sensors.getmRPM() );
        model.getmEngineTemp().setValue(  sensors.getmEngineTemp() );
        model.getmBatteryTemp().setValue( sensors.getmBatteryTemp() );
        //Alarms Stats
        model.getmBatterytempAlert().setValue( sensors.getmBatteryTempAlert() );
        model.getmEngineTempAlert().setValue( sensors.getmEngineTempAlert() );
        model.getmLowBatteryAlert().setValue( sensors.getmLowBatteryAlert());
    }


    // thread sample https://www.youtube.com/watch?v=QfQE1ayCzf8

  /*******************************************************
   *                 Bluetooth helper classes
   ********************************************************/
  private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
      return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
      //creates secure outgoing connection with BT device using UUID
  }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()
            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.available();
                    if(bytes != 0) {
                        SystemClock.sleep(1000); //pause and wait for rest of data. Adjust this depending on your sending speed.
                        bytes = mmInStream.available(); // how many bytes are ready to be read?
                        Log.w(TAG,"bytes available="+bytes);
                        bytes = mmInStream.read(buffer, 0, bytes); // record how many bytes we actually read
                        Log.w(TAG,"bytes read="+bytes);
                        mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                                .sendToTarget(); // Send the obtained bytes to the UI activity
                    }
                } catch (IOException e) {
                    e.printStackTrace();

                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(String input) {
            byte[] bytes = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(bytes);
                Log.e(TAG,"BT sent data to remote device");
            } catch (IOException e) { }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }


    private void connectBT(String btAddress) {
        final String address = "98:D3:32:31:25:32"; //btAddress;
        final String name = "HC-05";


        new Thread() {
            public void run() {
                boolean fail = false;
                Log.e(TAG, "BT attaching to device: " + address);
                BluetoothDevice device = mBTAdapter.getRemoteDevice(address);

                try {
                    mBTSocket = createBluetoothSocket(device);
                } catch (IOException e) {
                    fail = true;
                    Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                }
                // Establish the Bluetooth socket connection.
                try {
                    mBTSocket.connect();
                } catch (IOException e) {
                    try {
                        fail = true;
                        mBTSocket.close();
                        mHandler.obtainMessage(CONNECTING_STATUS, -1, -1)
                                .sendToTarget();
                    } catch (IOException e2) {
                        //insert code to deal with this
                        Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_SHORT).show();
                    }
                }
                if (fail == false) {
                    mConnectedThread = new ConnectedThread(mBTSocket);
                    mConnectedThread.start();

                    mHandler.obtainMessage(CONNECTING_STATUS, 1, -1, name)
                            .sendToTarget();
                }
            }
        }.start();
    }
}

