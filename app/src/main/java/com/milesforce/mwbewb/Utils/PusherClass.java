package com.milesforce.mwbewb.Utils;

import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.IBinder;
import android.provider.BaseColumns;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.milesforce.mwbewb.R;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;

import org.json.JSONObject;

public class PusherClass extends Service {
    static Pusher pusher;
    static Channel channel;

    public static void InstalisedPusher(String number, final Context context) {
        PusherOptions options = new PusherOptions();
        options.setCluster("ap2");
        pusher = new Pusher("3c1ab08d2bd45a32b9eb", options);
        channel = pusher.subscribe("miles_cm");
        /* pusher = new Pusher("426103b2991489001efe", options);
         channel = pusher.subscribe("my-channel");*/
        if (number != null) {
            channel.bind(number, new SubscriptionEventListener() {
                @Override
                public void onEvent(String channelName, String eventName, final String data) {
                    try {
                        final JSONObject obj = new JSONObject(data);
                        String number = obj.getString("message");
                        // String person_name = obj.getString("person_name");
                        AudioManager manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                        if (manager.getMode() == AudioManager.MODE_IN_CALL) {
                            Log.d("status", "No Call Accept");
                        } else {
                            TriggerCallWIthTelephoneManager.TriggerCall(number, context);
                        }


                        // getPersonContactDetails(number, person_name, context);

                    } catch (Exception e) {
                        //  Toast.makeText(context, String.valueOf(e), Toast.LENGTH_SHORT).show();
                        String error = e.getMessage().toString();
                    }
                }
            });


        }
        pusher.connect();


        pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange connectionStateChange) {
                Log.d("Pusher_state", "State changed from " + connectionStateChange.getPreviousState() + " to " + connectionStateChange.getCurrentState());
                System.out.println("State changed to " + connectionStateChange.getCurrentState() +
                        " from " + connectionStateChange.getPreviousState());
                Log.d("pusher_status", String.valueOf(connectionStateChange.getCurrentState()));
                String STATUS = String.valueOf(connectionStateChange.getCurrentState());
                if (STATUS.equals("DISCONNECTED")) {
                    ConstantUtills.PUSHER_STATUS = "DISCONNECTED";
                }
                if (STATUS.equals("CONNECTING")) {
                    ConstantUtills.PUSHER_STATUS = "CONNECTING";
                }
                if (STATUS.equals("CONNECTED")) {
                    ConstantUtills.PUSHER_STATUS = "CONNECTED";
                }
            }

            @Override
            public void onError(String s, String s1, Exception e) {
                try {
                    Log.d("Pusher_state", s + " " + s1);
                    System.out.println("There was a problem connecting!");
                } catch (Exception ek) {

                }
            }
        }, ConnectionState.ALL);

    }

    public static void UnScribeChannel() {
        pusher.unsubscribe("miles_cm");
        // pusher.unsubscribe("my-channel");

        /*my-channel*/
    }

    public static void subscribeChannel() {
        try {
            //   channel = pusher.subscribe("my-channel");
            pusher.connect();
        } catch (Exception e) {

        }

    }


    private static void getPersonContactDetails(String number, String person_name, Context context) {
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        String name = "?";

        ContentResolver contentResolver = context.getContentResolver();
        Cursor contactLookup = contentResolver.query(uri, new String[]{BaseColumns._ID,
                ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        try {
            if (contactLookup != null && contactLookup.getCount() > 0) {
                contactLookup.moveToNext();
                name = contactLookup.getString(contactLookup.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
            } else {
                saveContactsProgramatically(number, person_name, context);
            }
        } finally {
            if (contactLookup != null) {
                contactLookup.close();
            }
        }
    }

    private static void saveContactsProgramatically(String number, String person_name, Context context) {
        ContentValues values = new ContentValues();
        values.put(Contacts.People.NUMBER, number);
        values.put(Contacts.People.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM);
        values.put(Contacts.People.LABEL, person_name);
        values.put(Contacts.People.NAME, person_name);
        Uri dataUri = context.getContentResolver().insert(Contacts.People.CONTENT_URI, values);
        Uri updateUri = Uri.withAppendedPath(dataUri, Contacts.People.Phones.CONTENT_DIRECTORY);
        values.clear();
        values.put(Contacts.People.Phones.TYPE, Contacts.People.TYPE_MOBILE);
        values.put(Contacts.People.NUMBER, number);
        updateUri = context.getContentResolver().insert(updateUri, values);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}