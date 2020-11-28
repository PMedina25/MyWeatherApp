package com.example.my_weather_app.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.my_weather_app.R;
import com.example.my_weather_app.util.Constants;
import com.firebase.ui.auth.AuthUI;

public class MainActivity extends AppCompatActivity {

    /* Declaration of Button Views */
    private Button mButtonWeatherStation;
    private Button mButtonCurrentWeather;

    /* Declaration of the objects required to access the network settings */
    private WifiManager mWifiManager;
    private ConnectivityManager connectivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* Call setTheme before creation of any View */
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* We initiate the buttons */
        mButtonWeatherStation = (Button) findViewById(R.id.buttonWeatherStation);
        mButtonCurrentWeather = (Button) findViewById(R.id.buttonCurrentWeather);

        /* Weather station button OnClickListener */
        mButtonWeatherStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WeatherStationActivity.class);
                startActivity(intent);
            }
        });

        /* Current weather measurement button OnClickListener */
        mButtonCurrentWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OpenWeatherMapActivity.class);
                startActivity(intent);
            }
        });
        showNetworkNotification();
    }

    /**
     * Method that will display a pop up notification in case the network is disconnected.
     * If it is disconnected, the pop up will show up with a button which is able to enable
     * the WiFi connection
     */
    private void showNetworkNotification() {

        /* Initialization of the ConnectivityManager and WifiManager objects */
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        mWifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        /* Boolean variables which store if WiFi and Mobile connection are enabled */
        boolean isWifiConn = false;
        boolean isMobileConn = false;

        /* We check all the network types to know which one are connected */
        for (Network network : connectivityManager.getAllNetworks()) {
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                isWifiConn |= networkInfo.isConnected();
            }
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn |= networkInfo.isConnected();
            }
        }

        /* If WiFi and Mobile connection are disconnected, the pop up will be displayed */
        if (!isWifiConn && !isMobileConn) {

            /* Creation of AlertDialog.Builder */
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

            /* We set the title of the dialog */
            alertDialogBuilder.setTitle(Constants.NETWORK_DISABLED_ALERT_TITLE);

            /* We set the message, buttons and the icon for the dialog */
            alertDialogBuilder
                    .setMessage(Constants.NETWORK_ALERT_MESSAGE)
                    .setCancelable(false)
                    .setIcon(R.drawable.network)
                    .setPositiveButton(Constants.NETWORK_ALERT_POSITIVE_BUTTON, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // Enable network
                            mWifiManager.setWifiEnabled(true);
                        }
                    })
                    .setNegativeButton(Constants.NETWORK_ALERT_NEGATIVE_BUTTON, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
            alertDialogBuilder.show();
        }
    }

    /**
     * This method inflates the menu
     * @param menu Menu that contains the sign out item
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * This method will determine the functionality of the
     * sign out item, which will sign out from Firebase
     * @param item This corresponds to the sign out item
     * @return true or super.onOptionsItemSelected({@param item})
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                // sign out
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}