package iancawood.marcopolo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

public class Main extends Activity {

    public static final String TAG = "MAIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onSwitchClicked(View view) {
        boolean on = ((Switch) view).isChecked();

        if (on) {
            Log.v(TAG, "on");
            startService(new Intent(this, SMSListenerService.class));

        } else {
            Log.v(TAG, "off");
            stopService(new Intent(this, SMSListenerService.class));

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.v(TAG, "in options");

//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        //Criteria criteria = new Criteria();
//        //String provider = locationManager.getBestProvider(criteria, false);
//        String provider = "gps";
//        Location location = locationManager.getLastKnownLocation(provider);
//
//        Log.v(TAG, locationManager.toString());
//        Log.v(TAG, provider);
//        if (location == null) {
//            Log.v(TAG, "NULL");
//        } else {
//            Log.v(TAG, location.toString());
//        }

//        LocationResult locationResult = new LocationResult(){
//            @Override
//            public void gotLocation(Location location){
//
//            }
//        };
//        MyLocation myLocation = new MyLocation();
//        myLocation.getLocation(this, locationResult);

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
