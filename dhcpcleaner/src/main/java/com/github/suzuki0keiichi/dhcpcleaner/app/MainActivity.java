package com.github.suzuki0keiichi.dhcpcleaner.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Process process = Runtime.getRuntime().exec("su");

            DataOutputStream out = new DataOutputStream(process.getOutputStream());

            try {
                out.writeBytes("rm -f /data/misc/dhcp/*");
                out.flush();
            } finally {
                out.close();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            try {
                String line;
                while ((line = reader.readLine()) != null) {
                }
            } finally {
                process.destroy();
                reader.close();
            }

            Handler handler = new Handler();

            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "remove succeeded", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            Log.e("dhcp cleaner", "remove failed", e);
        }


        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
