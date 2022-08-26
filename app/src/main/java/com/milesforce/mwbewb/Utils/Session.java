package com.milesforce.mwbewb.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.milesforce.mwbewb.Activities.LoginActivity;
import com.milesforce.mwbewb.Activities.MainActivity;

import static android.content.Context.MODE_PRIVATE;
import static com.milesforce.mwbewb.Utils.ConstantUtills.AccessToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.SaveToken;

public class Session {
    SharedPreferences sharedPreferences;

    public void SessionLogout(Context context) {
        sharedPreferences = context.getSharedPreferences(SaveToken, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(AccessToken);
        editor.apply();
      //  context.startActivity(new Intent(MainActivity.this, LoginActivity.class));

    }
}
