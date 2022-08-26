package com.milesforce.mwbewb.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.milesforce.mwbewb.EngagementFragments.LevelsCustomAdapter;
import com.milesforce.mwbewb.Model.ClassData;
import com.milesforce.mwbewb.Model.ClassModel;
import com.milesforce.mwbewb.Model.DelaysModel;
import com.milesforce.mwbewb.Model.LevelsModel;
import com.milesforce.mwbewb.Model.MobileSearchModel;
import com.milesforce.mwbewb.Model.SearchWithId;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;
import com.milesforce.mwbewb.Retrofit.SuccessModel;
import com.milesforce.mwbewb.Utils.CustomCallAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class TakeAttendance extends AppCompatActivity implements View.OnClickListener, SurfaceHolder.Callback,
        Camera.ShutterCallback, Camera.PictureCallback {

    AppCompatImageButton bt_menu_back_tomain;
    ImageView take_image;
    CardView camera_card_attendence;
    Camera mCamera;
    SurfaceView mPreview;
    int currentCameraId =0;
    String filePath ;
    File pictureFile;
    LinearLayout upload_layout;

    ApiClient apiClient = ApiUtills.getAPIService();
    String accessToken;
    SharedPreferences sharedPreferences;
    ProgressBar attendance_progress;
    DelaysModel delaysModel;
    TextView person_Info_id,person_Info_name,person_Info_city;
    AppCompatSpinner class_spinner;
    ArrayList<ClassData>classDataArrayList;
    String CLASS_ID="";
    CustomCallAdapter customCallAdapter;
    CardView Student_info_card,card_info_card;
    EditText et_search_clients_phone;
    ArrayList<DelaysModel>attandence_arrayList;
    TextView attendance_number;
    LinearLayout register_student_layout,capturePic_layout,reset_pic_layout;

    TextView class_name,class_batch,class_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
        sharedPreferences = getApplicationContext().getSharedPreferences(SaveToken, MODE_PRIVATE);
        accessToken = sharedPreferences.getString(AccessToken, null);

        bt_menu_back_tomain=findViewById(R.id.bt_menu_back_tomain);
        attendance_progress=findViewById(R.id.attendance_progress);
        attendance_number=findViewById(R.id.attendance_number);
        bt_menu_back_tomain.setOnClickListener(this);
        take_image=findViewById(R.id.take_image);
        take_image.setOnClickListener(this);
        person_Info_id=findViewById(R.id.person_Info_id);
        person_Info_name=findViewById(R.id.person_Info_name);
        person_Info_city=findViewById(R.id.person_Info_city);
        class_spinner=findViewById(R.id.class_spinner);
        camera_card_attendence=findViewById(R.id.camera_card_attendence);
        upload_layout=findViewById(R.id.upload_layout);
        Student_info_card=findViewById(R.id.Student_info_card);
        et_search_clients_phone=findViewById(R.id.et_search_clients_phone);
        register_student_layout=findViewById(R.id.register_student_layout);
        capturePic_layout=findViewById(R.id.capturePic_layout);
        reset_pic_layout=findViewById(R.id.reset_pic_layout);
        card_info_card=findViewById(R.id.class_info_card);
        class_name=findViewById(R.id.class_info);
        class_batch=findViewById(R.id.batch_name_title);
        class_date=findViewById(R.id.class_date_info);

        getClassData(accessToken);

        // appconpact_spinner_levels.setEnabled(false);
        class_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    card_info_card.setVisibility(View.VISIBLE);
                    camera_card_attendence.setVisibility(View.GONE);
                    ClassData classData = classDataArrayList.get(position);
                    CLASS_ID = String.valueOf(classData.getId());
                    class_name.setText(classData.getClass_name());
                    class_batch.setText(classData.getBatch());
                    class_date.setText(getStringTimeStamp(classData.getClass_start_date()));
                    UpdateAttendenceData(CLASS_ID);
                }catch (Exception e){

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        et_search_clients_phone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (et_search_clients_phone.getText().length() == 10) {
                        hideKeyboard();
                        getSearchedUSerData(accessToken, v.getText().toString());
                        return true;
                    } else {
                        Toast.makeText(TakeAttendance.this, "Please enter 10 digits mobile number", Toast.LENGTH_SHORT).show();
                    }

                }
                return false;
            }
        });





    }

    private void getSearchedUSerData(String accessToken, String toString) {
        attendance_progress.setVisibility(View.VISIBLE);
        apiClient.searchWithPhone(toString, "Bearer " + accessToken, "application/json").enqueue(new Callback<SearchWithId>() {
            @Override
            public void onResponse(Call<SearchWithId> call, Response<SearchWithId> response) {
                try {
                    if (response.body().getStatus().equals("success")){
                        delaysModel=response.body().getDelaysModel();
                        person_Info_id.setText(response.body().getDelaysModel().getIdentity());
                        person_Info_name.setText(response.body().getDelaysModel().getPerson_name());
                        person_Info_city.setText(response.body().getDelaysModel().getCity());
                        attendance_progress.setVisibility(View.GONE);
                        register_student_layout.setVisibility(View.VISIBLE);
                    }else {
                        Toast.makeText(TakeAttendance.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        attendance_progress.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    e.getMessage();
                    attendance_progress.setVisibility(View.GONE);
                    Toast.makeText(TakeAttendance.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<SearchWithId> call, Throwable t) {
                t.getMessage();
                attendance_progress.setVisibility(View.GONE);
                Toast.makeText(TakeAttendance.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getClassData(String accessToken) {
        attendance_progress.setVisibility(View.VISIBLE);
        classDataArrayList=new ArrayList<>();
        apiClient.getClassesData("Bearer "+accessToken,"application/json").enqueue(new Callback<ClassModel>() {
            @Override
            public void onResponse(Call<ClassModel> call, Response<ClassModel> response) {
                try {
                    if (response.body().getStatus().equals("success")) {
                        classDataArrayList= response.body().getData();
                        attendance_progress.setVisibility(View.GONE);
                        customCallAdapter = new CustomCallAdapter(getApplicationContext(), R.layout.class_layout, R.id.levels_items, classDataArrayList);
                        class_spinner.setAdapter(customCallAdapter);
                    }




                }catch (Exception e){
                    Toast.makeText(TakeAttendance.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    attendance_progress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ClassModel> call, Throwable t) {
                Toast.makeText(TakeAttendance.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                attendance_progress.setVisibility(View.GONE);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_menu_back_tomain:
                finish();
                break;
            case R.id.take_image:
                TakeStudentPic();
                break;
        }

    }

    private void TakeStudentPic() {
        try {
            camera_card_attendence.setVisibility(View.VISIBLE);
            mPreview = (SurfaceView)findViewById(R.id.camera_preview);
            mPreview.getHolder().addCallback(this);
            mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        }catch (Exception e){
            Toast.makeText(this, ""+e.getStackTrace(), Toast.LENGTH_SHORT).show();
        }

//        Camera.Parameters params = mCamera.getParameters();
//        mCamera.setDisplayOrientation(90);
//        params.setJpegQuality(100);
    }

    public void captureStudentAttendencePic(View view) {
        capturePic_layout.setVisibility(View.GONE);
        reset_pic_layout.setVisibility(View.VISIBLE);
        mCamera.takePicture(null,null,mPicture);

    }

    public void resetStudentAttendencePic(View view) {
        mCamera.startPreview();
        person_Info_id.setText("");
        person_Info_name.setText("");
        person_Info_city.setText("");
        capturePic_layout.setVisibility(View.VISIBLE);
        reset_pic_layout.setVisibility(View.GONE);
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
        if(holder.getSurface() == null)//check if the surface is ready to receive camera data
            return;

        try{
            mCamera.stopPreview();
        } catch (Exception e){
            //this will happen when you are trying the camera if it's not running
        }

        //now, recreate the camera preview
        try{

          /*  //set the camera display orientation lock
            Camera.Parameters params = mCamera.getParameters();
            List<Camera.Size> sizes = params.getSupportedPreviewSizes();
            List<String>focusModes=params.getSupportedFocusModes();
            Log.d("focus_modes",""+focusModes);
            if(focusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)){
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            } else
            if(focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)){
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            }
//
            Camera.Size optimalSize = getOptimalPreviewSize(sizes,width,height);
            params.setPreviewSize(optimalSize.width,optimalSize.height);
            mCamera.setDisplayOrientation(90);
            params.setJpegQuality(100);
            params.setRotation(90);


            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();*/

            Camera.Parameters params = mCamera.getParameters();
            List<Camera.Size> sizes = params.getSupportedPreviewSizes();
            Camera.Size optimalSize = getOptimalPreviewSize(sizes,width,height);
            params.setPreviewSize(optimalSize.width,optimalSize.height);
            mCamera.setDisplayOrientation(90);
             params.setJpegQuality(50);
            params.setRotation(270);
            if(sizes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)){
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            } else
            if(sizes.contains(Camera.Parameters.FOCUS_MODE_AUTO)){
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            }

            mCamera.setParameters(params);
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();

        } catch(Exception exp){
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

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
    @Override
    public void onPause() {
        super.onPause();
        if (mCamera!=null){
            mCamera.stopPreview();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        int numCams = Camera.getNumberOfCameras();
        if(numCams > 0){
            try{
                mCamera = Camera.open(0);
                mCamera.startPreview();
            } catch (RuntimeException ex){
                Toast.makeText(getApplicationContext(), "SomeThing went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCamera!=null){
            mCamera.stopPreview();
        }
        Log.d("CAMERA","Destroy");
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
            try{
                SendFileToServer(pictureFile);
            }catch (Exception e){

            }

        }
    };
    private void SendFileToServer(File pictureFile) {
        attendance_progress.setVisibility(View.VISIBLE);
        byte[] data = null;
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("image", pictureFile.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), pictureFile));
        builder.addFormDataPart("class_id",CLASS_ID);
        MultipartBody requestBody = builder.build();
        apiClient.searchWithStudentPic(requestBody,"Bearer "+accessToken,"application/json").enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                try {
                    if (response.body().getStatus().equals("success")){
                        mCamera.startPreview();
                        attendance_progress.setVisibility(View.GONE);
                      //  Toast.makeText(TakeAttendance.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        UpdateAttendenceData(CLASS_ID);
                        showAlert("Alert",String.valueOf(response.body().getMessage()));

                    }else if(response.body().getStatus().equals("error")) {
                        if (response.body().getMessage().equals("Cannot find the lead")){
                            attendance_progress.setVisibility(View.GONE);
                           // Toast.makeText(TakeAttendance.this, ""+"Lead Picture is not Registered yet.Please Register now", Toast.LENGTH_SHORT).show();
                            Student_info_card.setVisibility(View.VISIBLE);
                            showAlert("Alert",String.valueOf(response.body().getMessage()));
                        }else {
                            mCamera.startPreview();
                          //  Log.d("error_find",response.body().getMessage());
                            attendance_progress.setVisibility(View.GONE);
                            showAlert("Alert",String.valueOf(response.body().getMessage()));
                        }

                    }

                }catch (Exception e){
                    attendance_progress.setVisibility(View.GONE);
                    showAlert(String.valueOf("Alert"),String.valueOf(e.getMessage()));
                }

            }
            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                attendance_progress.setVisibility(View.GONE);
                showAlert(String.valueOf("Alert"),String.valueOf(t.getMessage()));
            }
        });
    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {

        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio=(double)h / w;

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


    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void RegisterPicWIthThisCan(View view) {
        if (pictureFile!=null){
            RegisterPicWithStudent(pictureFile);
        }


    }
    private void RegisterPicWithStudent(File pictureFile) {
        attendance_progress.setVisibility(View.VISIBLE);
        byte[] data = null;
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("image", pictureFile.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), pictureFile));
        builder.addFormDataPart("identity",String.valueOf(delaysModel.getIdentity()));
        builder.addFormDataPart("class_id",CLASS_ID);
        File file = new File("");
        MultipartBody requestBody = builder.build();
        apiClient.registerWithIndentity(requestBody,"Bearer "+accessToken,"application/json").enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                try {
                    if (response.body().getStatus().equals("success")){
                        mCamera.startPreview();
                        attendance_progress.setVisibility(View.GONE);
                        UpdateAttendenceData(CLASS_ID);
                        Student_info_card.setVisibility(View.GONE);
                        register_student_layout.setVisibility(View.GONE);
                        capturePic_layout.setVisibility(View.VISIBLE);
                        reset_pic_layout.setVisibility(View.GONE);
                        showAlert("Alert",String.valueOf(response.body().getMessage()));
                    }else if(response.body().getStatus().equals("error")){
                        attendance_progress.setVisibility(View.GONE);
                        capturePic_layout.setVisibility(View.VISIBLE);
                        reset_pic_layout.setVisibility(View.GONE);
                        register_student_layout.setVisibility(View.GONE);
                        mCamera.startPreview();
                        Student_info_card.setVisibility(View.GONE);
                        showAlert("Alert",String.valueOf(response.body().getMessage()));

                    } else {
                        attendance_progress.setVisibility(View.GONE);
                        capturePic_layout.setVisibility(View.VISIBLE);
                        reset_pic_layout.setVisibility(View.GONE);
                        showAlert("Alert",String.valueOf(response.body().getMessage()));
                    }

                }catch (Exception e){
                    attendance_progress.setVisibility(View.GONE);
                    capturePic_layout.setVisibility(View.VISIBLE);
                    reset_pic_layout.setVisibility(View.GONE);
                    showAlert(String.valueOf("Alert"),String.valueOf(e.getMessage()));

                }

            }
            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                attendance_progress.setVisibility(View.GONE);
                capturePic_layout.setVisibility(View.VISIBLE);
                reset_pic_layout.setVisibility(View.GONE);
                showAlert(String.valueOf("Alert"),String.valueOf(t.getMessage()));
            }
        });


    }

    private void UpdateAttendenceData(String class_id) {
        attandence_arrayList=new ArrayList<>();
        if (attandence_arrayList.size()>0){
            attandence_arrayList.clear();
        }
        apiClient.getAttandance(class_id,"Bearer "+accessToken,"application/json").enqueue(new Callback<MobileSearchModel>() {
            @Override
            public void onResponse(Call<MobileSearchModel> call, Response<MobileSearchModel> response) {
                try {
                    if (response.body().getStatus().equals("success")){
                        List<DelaysModel>delaysModelList=response.body().getDelaysModel();
                        attandence_arrayList.addAll(delaysModelList);
                        attendance_number.setText(String.valueOf(attandence_arrayList.size()));
                    }else {
                        Toast.makeText(TakeAttendance.this, ""+response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(TakeAttendance.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<MobileSearchModel> call, Throwable t) {
                Toast.makeText(TakeAttendance.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void ShowAttendPeopleData(View view) {
        Intent intent=new Intent(TakeAttendance.this,AttendenceActivity.class);
        intent.putExtra("student_list",attandence_arrayList);
        startActivity(intent);
    }
    public void showAlert(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        Dialog d = builder.create();
        d.show();

    }

    public void OpenCameraVIew(View view) {
        card_info_card.setVisibility(View.GONE);
        TakeStudentPic();
    }

    private String getStringTimeStamp(String class_time) {
        Date date = new Date(Long.valueOf(class_time)*1000);
        DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        String formatted = format.format(date);
        return formatted;
    }

}
