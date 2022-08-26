package com.milesforce.mwbewb.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.milesforce.mwbewb.Model.ModelForId;
import com.milesforce.mwbewb.Model.TokenDataModel;
import com.milesforce.mwbewb.Model.TokenModel;
import com.milesforce.mwbewb.Model.UserToken;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;
import com.milesforce.mwbewb.Utils.BatterPercentage;
import com.milesforce.mwbewb.Utils.BatteryModel;
import com.milesforce.mwbewb.Utils.CallRecord;
import com.milesforce.mwbewb.Utils.ConstantUtills;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.milesforce.mwbewb.Utils.ConstantUtills.AccessToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.Bde_user_id;
import static com.milesforce.mwbewb.Utils.ConstantUtills.SaveToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.USER_NAME;
import static com.milesforce.mwbewb.Utils.PusherClass.InstalisedPusher;

public class LoginActivity extends AppCompatActivity {
    public static int PERMISSIONS_REQUEST_CALL_PRIVILEGED = 1;
    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 9000;
    ApiClient apiClient;
    TextInputEditText user_name, password;
    SharedPreferences sharedPreferences;
    ProgressBar login_progressbar;
    ConstraintLayout loginsnakebar;
    CallRecord callRecord;
    String bde_user_id;
    Realm realm;
    GoogleSignInClient mGoogleSignInClient;
    public static final int RC_SIGN_IN = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_login);
        apiClient = ApiUtills.getAPIService();
        BatteryModel batteryModel = new BatterPercentage().getBattertPercentage(getApplicationContext());
        if (realm.where(UserToken.class).findFirst()!=null){
            UserToken access_token = realm.where(UserToken.class).findFirst();
            Log.d("user_access_token",access_token.getAccessToken());
        }
        sharedPreferences = getSharedPreferences(SaveToken, MODE_PRIVATE);
        if (sharedPreferences.getString(AccessToken, null) != null) {
            bde_user_id = sharedPreferences.getString(Bde_user_id, null);
            if (bde_user_id != null) {
                getPubNumServie(bde_user_id);
            }
            /*Call Record Service */
            callRecord = new CallRecord.Builder(getApplicationContext())
                    /*  .setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION)
                      .setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
                      .setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) // optional & default value
                      // optional & default value
                      // optional & default value
                      // optional & default value ->Ex: RecordFileName_incoming.amr || RecordFileName_outgoing.amr*/
                    .build();
            callRecord.startCallReceiver();

            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        user_name = findViewById(R.id.user_name);
        password = findViewById(R.id.password);
        //user_name.setText("imran.mohammad@mileseducation.com");
        // user_name.setText("hyderabad@mileseducation.com");
        // password.setText("Management@01");
        login_progressbar = findViewById(R.id.login_progressbar);
        loginsnakebar = findViewById(R.id.loginsnakebar);

        if (checkAndRequestPermissions()) {
            // carry on the normal flow, as the case of  permissions  granted.
        }

        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PRIVILEGED) != PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CALL_PRIVILEGED)) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CALL_PRIVILEGED},
                            PERMISSIONS_REQUEST_CALL_PRIVILEGED);
                }
            }
        }
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken("360383923261-ppk7gerj3afi09g091ks366cuhv82nsj.apps.googleusercontent.com")
                .setHostedDomain("mileseducation.com")
                .build();
         mGoogleSignInClient = GoogleSignIn.getClient(this, gso);




    }

    public void NavigateMainActivity(View view) {
        // startActivity(new Intent(LoginActivity.this, MainActivity.class));
        if (user_name.getText().toString().isEmpty()) {
            user_name.setError("Please enter user name");
        } else if (password.getText().toString().isEmpty()) {
            password.setError("Please enter password");
        } else {
            LoginWithUserNameAndPassword();
        }


    }

    private void LoginWithUserNameAndPassword() {
        login_progressbar.setVisibility(View.VISIBLE);
        apiClient.getAccessTokenFromLogin(user_name.getText().toString(), password.getText().toString()).enqueue(new Callback<TokenDataModel>() {
            @Override
            public void onResponse(Call<TokenDataModel> call, Response<TokenDataModel> response) {
                try {

                    if (response.body().getStatus_code() == 200) {
                        /*Call Record Service */
                        callRecord = new CallRecord.Builder(getApplicationContext())
                                /* .setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION)
                                 .setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
                                 .setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) // optional & default value*/
                                // optional & default value
                                // optional & default value
                                // optional & default value ->Ex: RecordFileName_incoming.amr || RecordFileName_outgoing.amr
                                .build();
                        callRecord.startCallReceiver();
                        getUserToken(response.body().getResponse().getAccess_token(),user_name.getText().toString().trim());
                    } else {
                        login_progressbar.setVisibility(View.GONE);
                        opensnakeBar("Please enter valid credentials");
                    }
                } catch (Exception e) {
                    login_progressbar.setVisibility(View.GONE);
                    opensnakeBar(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<TokenDataModel> call, Throwable t) {
                login_progressbar.setVisibility(View.GONE);
                opensnakeBar("Some thing went wrong");
            }
        });
    }


    private boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS);

        int writContacts = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CONTACTS);

        int call_Phone = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE);

        int readPhone_state = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE);
        int readCall_Log = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CALL_LOG);

        int outGoingCall = ContextCompat.checkSelfPermission(this, Manifest.permission.PROCESS_OUTGOING_CALLS);
        int External_storage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int write_storage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int Record_audio = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int modifiedState = ContextCompat.checkSelfPermission(this, Manifest.permission.MODIFY_PHONE_STATE);
        int camera_permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);


        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS);
        }

        if (writContacts != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_CONTACTS);
        }
        if (call_Phone != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (readPhone_state != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (readCall_Log != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CALL_LOG);
        }
        if (outGoingCall != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.PROCESS_OUTGOING_CALLS);
        }
        if (write_storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (External_storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (Record_audio != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECORD_AUDIO);
        }
        if (modifiedState != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.MODIFY_PHONE_STATE);
        }
        if (camera_permission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            return false;
        }
        return true;
    }

    public void opensnakeBar(String s) {
        Snackbar snackbar = Snackbar
                .make(loginsnakebar, s, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void getUserToken( final  String accessToken, final String User_email) {
        apiClient.getUserIdForPusherSubscription("Bearer " + accessToken, "application/json").enqueue(new Callback<ModelForId>() {
            @Override
            public void onResponse(Call<ModelForId> call, Response<ModelForId> response) {
                try {
                    if(response.raw().code() == 515){
                        login_progressbar.setVisibility(View.GONE);
                        Intent intent = new Intent(LoginActivity.this,StartWorkActivity.class);
                        intent.putExtra("accessToken",accessToken);
                        intent.putExtra("user_enail",User_email);
                        startActivity(intent);
                        return;

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
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } catch (Exception e) {
                    login_progressbar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelForId> call, Throwable t) {
                login_progressbar.setVisibility(View.GONE);
            }
        });
    }

    private void openAlertForFailueLogin() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please login in web application and start a session.");
        alertDialogBuilder.setNeutralButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void getPubNumServie(String mobileNumber) {
        InstalisedPusher(mobileNumber, getApplicationContext());
    }

    public void LoginWIthGoogle(View view) {
        login_progressbar.setVisibility(View.VISIBLE);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String token = account.getIdToken();
            String user_email = account.getEmail();
            if (!token.isEmpty()){
                Log.d("toekndjkvsfjk",token);
                GetSignWithGoogle(token,user_email);
            }
        } catch (ApiException e) {
            opensnakeBar(String.valueOf(e.getMessage()));
            login_progressbar.setVisibility(View.GONE);
        }
    }

    private void GetSignWithGoogle(String token, final  String Email) {
        String GoogleURL = "getAccessTokenFromGoogleIdToken/"+token;
        apiClient.getAccessTokenFromGoogle(GoogleURL).enqueue(new Callback<TokenModel>() {
            @Override
            public void onResponse(Call<TokenModel> call, Response<TokenModel> response) {
                try {
                    if (response.raw().code() == 200) {
                        /*Call Record Service */
                        callRecord = new CallRecord.Builder(getApplicationContext())
                                .build();
                        callRecord.startCallReceiver();
                        getUserToken(response.body().getAccess_token(),Email);
                    } else {
                        login_progressbar.setVisibility(View.GONE);
                        opensnakeBar(String.valueOf(response.raw().message()));
                    }
                } catch (Exception e) {
                    login_progressbar.setVisibility(View.GONE);
                    opensnakeBar(e.getMessage());
                }
            }
            @Override
            public void onFailure(Call<TokenModel> call, Throwable t) {
                login_progressbar.setVisibility(View.GONE);
                opensnakeBar(t.getMessage());

            }
        });
    }
}
