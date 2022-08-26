package com.milesforce.mwbewb.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.milesforce.mwbewb.Model.ModelForId;
import com.milesforce.mwbewb.Model.UserToken;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;
import com.milesforce.mwbewb.Retrofit.SuccessModel;
import com.milesforce.mwbewb.Utils.ResumeWork;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.milesforce.mwbewb.Utils.ConstantUtills.AccessToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.Bde_user_id;
import static com.milesforce.mwbewb.Utils.ConstantUtills.SaveToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.USER_NAME;
import static com.milesforce.mwbewb.Utils.PusherClass.InstalisedPusher;

public class ResumeWorkActivity extends AppCompatActivity {
    String ACCESS_TOKEN = "" ;
    String USER_EMAIL = "";

    ApiClient apiClient;
    SharedPreferences sharedPreferences;
    ProgressBar progressbar;
    String bde_user_id;
    Realm realm;
    GoogleSignInClient mGoogleSignInClient;
    public static final int RC_SIGN_IN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_work);
        apiClient = ApiUtills.getAPIService();
        sharedPreferences = getSharedPreferences(SaveToken, MODE_PRIVATE);
        ACCESS_TOKEN = getIntent().getStringExtra("accessToken");
        USER_EMAIL = getIntent().getStringExtra("user_enail");

        progressbar = findViewById(R.id.progressbar);
    }

    public void ResumeWork(View view) {
        progressbar.setVisibility(View.VISIBLE);
        apiClient.updateWorkingStatus("resumed","Bearer "+ACCESS_TOKEN).enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                if(response.body().getStatus().equals("success")){
                    getUserToken(ACCESS_TOKEN,USER_EMAIL);

                }else {
                    Toast.makeText(ResumeWorkActivity.this, String.valueOf(response.body().getMessage()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                Toast.makeText(ResumeWorkActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getUserToken( final  String accessToken, final String User_email) {
        apiClient.getUserIdForPusherSubscription("Bearer " + accessToken, "application/json").enqueue(new Callback<ModelForId>() {
            @Override
            public void onResponse(Call<ModelForId> call, Response<ModelForId> response) {
                try {
                    if(response.raw().code() == 515){

                    }
                    int numb = response.body().getUser_id();
                    bde_user_id = String.valueOf(numb);
                    getPubNumServie(bde_user_id);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Bde_user_id, bde_user_id);
                    editor.putString(AccessToken, accessToken);
                    editor.putString(USER_NAME, User_email);
                    editor.commit();
                    try {
                        realm.beginTransaction();
                        UserToken userToken = realm.createObject(UserToken.class);
                        userToken.setAccessToken(accessToken);
                        realm.commitTransaction();
                    }catch (Exception e){
                    }
                    startActivity(new Intent(ResumeWorkActivity.this, MainActivity.class));
                    finish();
                } catch (Exception e) {
                    progressbar.setVisibility(View.GONE);
                    Toast.makeText(ResumeWorkActivity.this, String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelForId> call, Throwable t) {
                progressbar.setVisibility(View.GONE);
            }
        });
    }
    private void getPubNumServie(String mobileNumber) {
        InstalisedPusher(mobileNumber, getApplicationContext());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){

        }
        return false;
    }
}
