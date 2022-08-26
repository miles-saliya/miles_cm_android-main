package com.milesforce.mwbewb.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.milesforce.mwbewb.Activities.LoginActivity;
import com.milesforce.mwbewb.Activities.ResumeWorkActivity;
import com.milesforce.mwbewb.Activities.StartWorkActivity;

import static android.content.Context.MODE_PRIVATE;
import static com.milesforce.mwbewb.Utils.ConstantUtills.AccessToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.SaveToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.USER_NAME;

public class ResumeWork {
    SharedPreferences sharedPreferences;
    public void stResumeWork(Context context){
        sharedPreferences = context.getSharedPreferences(SaveToken, MODE_PRIVATE);
        Intent intent = new Intent(context, ResumeWorkActivity.class);
        intent.putExtra("accessToken",sharedPreferences.getString(AccessToken, null));
        intent.putExtra("user_enail",sharedPreferences.getString(USER_NAME, null));
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP  | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);




    }
}
