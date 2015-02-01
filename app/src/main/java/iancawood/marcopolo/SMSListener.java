package iancawood.marcopolo;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.Context;
import android.location.Location;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import java.util.Calendar;

public class SMSListener extends BroadcastReceiver
{
    private final String TAG = "LISTENER";
    private String smsSource = ""; // these should probably not be global :/
    private String smsBody = "";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.v(TAG, "TEXT MESSAGE RECEIVED!!!!");

        Bundle extras = intent.getExtras();

        String strMessage = "";

        if ( extras != null )
        {
            Object[] smsextras = (Object[]) extras.get( "pdus" );

            for ( int i = 0; i < smsextras.length; i++ )
            {
                SmsMessage smsmsg = SmsMessage.createFromPdu((byte[])smsextras[i]);

                smsBody = smsmsg.getMessageBody().toString();
                smsSource = smsmsg.getOriginatingAddress();

                Log.v(TAG, smsSource + " said: " + smsBody);

                // if the text message contains "marco"
                if (smsBody.toLowerCase().contains("marco")) {
                    Log.i(TAG, "hey listen!!!!!!!!!!!!!!!!!!!!!");

                    // these three lines are temporary, they just make the phone make noise
                    if (smsBody.toLowerCase().contains("beep")) {
                        Log.i(TAG, "beep");
                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        Ringtone r = RingtoneManager.getRingtone(context.getApplicationContext(), notification);
                        r.play();
                    }

                    // Create and set and alarm for 1 minute ahead of the current time

                        Log.i(TAG, "alarm");
                        Calendar c = Calendar.getInstance();
                        Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
                        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // remove this line of code if possible (http://stackoverflow.com/questions/3918517/calling-startactivity-from-outside-of-an-activity-context)
                        alarmIntent.putExtra(AlarmClock.EXTRA_MESSAGE, "Polo!");
                        alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, c.get(Calendar.HOUR_OF_DAY));
                        alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, c.get(Calendar.MINUTE) + 1);
                        context.startActivity(alarmIntent);
                    

                    // Replies to the sender of a text message containing google map link to phone's current location
                    if (smsSource.length() > 7) { // temporary solution to avoid replying to email, should fix this
                        if (smsBody.toLowerCase().contains("location")) {
                            Log.i(TAG, "location");
                            LocationResult locationResult = new LocationResult(){
                                @Override
                                public void gotLocation(Location location) {
                                    Log.v(TAG, "Location received");
                                    String msg = "";
                                    if (smsBody.toLowerCase().contains("json")) {
                                        msg = "{Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude() + "}"; // json
                                    } else {
                                        msg = "http://maps.google.com/maps?q=" + location.getLatitude() + "," + location.getLongitude(); // link to ggoogle
                                    }
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(smsSource, null, msg, null, null);
                                }
                            };
                            MyLocation myLocation = new MyLocation();
                            myLocation.getLocation(context, locationResult);
                        }
                    }
                }
            }

        }

    }
}