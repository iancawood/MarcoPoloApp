package iancawood.marcopolo;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class SMSListenerService extends Service {

    public static final String TAG = "SERVICE";
    private SMSListener mSMSListener;
    private IntentFilter mIntentFilter;

    public SMSListenerService() {
        Log.v(TAG, "Service constructor");
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        Log.v(TAG, "Service created");

        mSMSListener = new SMSListener();
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(mSMSListener, mIntentFilter);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        Log.v(TAG, "service destroyed");

        // Unregister the SMS receiver
        unregisterReceiver(mSMSListener);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG, "service on bind");

        throw new UnsupportedOperationException("Not yet implemented");
    }
}