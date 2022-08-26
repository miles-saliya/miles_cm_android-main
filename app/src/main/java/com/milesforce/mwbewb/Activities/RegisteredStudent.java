package com.milesforce.mwbewb.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.milesforce.mwbewb.Model.DelaysModel;
import com.milesforce.mwbewb.Model.MobileSearchModel;
import com.milesforce.mwbewb.Model.SearchWithId;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;
import com.milesforce.mwbewb.Retrofit.SuccessModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.milesforce.mwbewb.Utils.ConstantUtills.AccessToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.SaveToken;

public class RegisteredStudent extends AppCompatActivity implements View.OnClickListener, SurfaceHolder.Callback,
        Camera.ShutterCallback, Camera.PictureCallback {

    AppCompatImageButton appCompatImageButton;
    CardView cardView;
    Camera mCamera;
    SurfaceView mPreview;
    int currentCameraId = 0;
    String filePath;
    File pictureFile;
    LinearLayout upload_layout, capturePic_layout;
    EditText et_search_clients;
    ApiClient apiClient = ApiUtills.getAPIService();
    String accessToken;
    SharedPreferences sharedPreferences;
    TextView person_Info_id, person_Info_name, person_Info_city;
    DelaysModel delaysModel;
    ProgressBar add_photo_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_student);
        sharedPreferences = getApplicationContext().getSharedPreferences(SaveToken, MODE_PRIVATE);
        accessToken = sharedPreferences.getString(AccessToken, null);
        person_Info_id = findViewById(R.id.person_Info_id);
        person_Info_name = findViewById(R.id.person_Info_name);
        person_Info_city = findViewById(R.id.person_Info_city);
        add_photo_progress = findViewById(R.id.add_photo_progress);


        appCompatImageButton = findViewById(R.id.bt_menu_back_tomain);
        appCompatImageButton.setOnClickListener(this);
        cardView = findViewById(R.id.camera_card);
        upload_layout = findViewById(R.id.upload_layout);
        capturePic_layout = findViewById(R.id.capturePic_layout);
        et_search_clients = findViewById(R.id.et_search_clients);
        et_search_clients.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (et_search_clients.getText().length() > 3) {
                        hideKeyboard();
                        getSearchedUSerData(accessToken, v.getText().toString());
                        return true;
                    } else {
                        showSnakeBarForSearch("Please enter at least 3 char..");
                    }

                }
                return false;
            }
        });


    }

    private void getSearchedUSerData(String accessToken, String toString) {
        apiClient.searchWithCanId(toString, "Bearer " + accessToken, "application/json").enqueue(new Callback<SearchWithId>() {
            @Override
            public void onResponse(Call<SearchWithId> call, Response<SearchWithId> response) {
                try {
                    if (response.body().getStatus().equals("success")) {
                        delaysModel = response.body().getDelaysModel();
                        person_Info_id.setText(response.body().getDelaysModel().getIdentity());
                        person_Info_name.setText(response.body().getDelaysModel().getPerson_name());
                        person_Info_city.setText(response.body().getDelaysModel().getCity());
                        Toast.makeText(RegisteredStudent.this, "" + response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisteredStudent.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.getMessage();
                }

            }

            @Override
            public void onFailure(Call<SearchWithId> call, Throwable t) {
                t.getMessage();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_menu_back_tomain:
                finish();
        }
    }

    public void TakePicture(View view) {

        try {
            cardView.setVisibility(View.VISIBLE);
            mPreview = (SurfaceView) findViewById(R.id.camera_preview);
            mPreview.getHolder().addCallback(this);
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

//        Camera.Parameters params = mCamera.getParameters();
////        params.setRotation(90);
////        params.setJpegQuality(100);
    }

    public void reTakePicture(View view) {
        if (mCamera != null) {
            mCamera.startPreview();
        }


    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        FileOutputStream fos = null;
        try {
            filePath = "/sdcard/test.jpg";
            fos = new FileOutputStream(
                    filePath);
            fos.write(data);
            fos.close();
            //Log.d("Log", "onPictureTaken - wrote bytes: " + data.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("Log", "onPictureTaken - wrote bytes: " + data.length);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Log", "onPictureTaken - wrote bytes: " + data.length);
        } finally {
            Intent i = getIntent();
            i.putExtra("Path", filePath);
            setResult(RESULT_OK, i);
            finish();
        }
        camera.startPreview();
    }

    @Override
    public void onShutter() {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(mPreview.getHolder());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//        Camera.CameraInfo info = new Camera.CameraInfo();
//        Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_FRONT, info);
//        int rotation = this.getWindowManager().getDefaultDisplay().getRotation();
//        int degrees = 0;
//        switch (rotation) {
//            case Surface.ROTATION_0: degrees = 0; break; //Natural orientation
//            case Surface.ROTATION_90: degrees = 90; break; //Landscape left
//            case Surface.ROTATION_180: degrees = 180; break;//Upside down
//            case Surface.ROTATION_270: degrees = 270; break;//Landscape right
//        }
//        int rotate = (info.orientation - degrees + 360) % 360;
//
//        //STEP #2: Set the 'rotation' parameter
//        Camera.Parameters params = mCamera.getParameters();
//        params.setRotation(rotate);
//        mCamera.setParameters(params);
//        mCamera.setDisplayOrientation(90);
//        mCamera.startPreview();
        if (holder.getSurface() == null)//check if the surface is ready to receive camera data
            return;

        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            //this will happen when you are trying the camera if it's not running
        }

        //now, recreate the camera preview
        try {

            //set the camera display orientation lock

            Camera.Parameters params = mCamera.getParameters();
            List<Camera.Size> sizes = params.getSupportedPreviewSizes();
            Camera.Size optimalSize = getOptimalPreviewSize(sizes, width, height);
            params.setPreviewSize(optimalSize.width, optimalSize.height);
            mCamera.setDisplayOrientation(90);
            params.setJpegQuality(50);
            params.setRotation(270);
            if (sizes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            } else if (sizes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            }

            mCamera.setParameters(params);
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (Exception exp) {
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCamera != null) {
            mCamera.stopPreview();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCamera != null) {
            mCamera.stopPreview();
        }
        Log.d("CAMERA", "Destroy");
    }

    Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.d("data", data.toString());
/*
            try {
                String str = new String(data, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }*/
            pictureFile = getOutputMediaFile();
            if (pictureFile == null) {
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {

            } catch (IOException e) {
            }
            try {
                SendFileToServer(pictureFile);
            } catch (Exception e) {

            }

        }
    };

    private void SendFileToServer(File pictureFile) {
        add_photo_progress.setVisibility(View.VISIBLE);
        byte[] data = null;
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("image", pictureFile.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), pictureFile));
        builder.addFormDataPart("identity", String.valueOf(delaysModel.getIdentity()));
        MultipartBody requestBody = builder.build();
        apiClient.registerWithIndentity(requestBody, "Bearer " + accessToken, "application/json").enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                try {
                    if (response.body().getStatus().equals("success")) {
                        mCamera.startPreview();
                        add_photo_progress.setVisibility(View.GONE);
                        Toast.makeText(RegisteredStudent.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(RegisteredStudent.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        add_photo_progress.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    Toast.makeText(RegisteredStudent.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    add_photo_progress.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                Toast.makeText(RegisteredStudent.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                add_photo_progress.setVisibility(View.GONE);
            }
        });


    }

    private File getOutputMediaFile() {
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "MyCameraApp");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    public void captureStudentPic(View view) {
        //    upload_layout.setVisibility(View.VISIBLE);
        mCamera.takePicture(null, null, mPicture);
    }

    public void UploadToServer(View view) {
        // SendFileToServer(pictureFile);

    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showSnakeBarForSearch(String message) {
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {

        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) h / w;

        if (sizes == null) return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

}

