package com.milesforce.mwbewb;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.milesforce.mwbewb.Activities.BussinessCallRecorderModel;
import com.milesforce.mwbewb.Activities.MainActivity;
import com.milesforce.mwbewb.Activities.PlayListAdapter;
import com.milesforce.mwbewb.Activities.RecordsModel;
import com.milesforce.mwbewb.Model.GetRecords;
import com.milesforce.mwbewb.Model.UserToken;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;
import com.milesforce.mwbewb.Retrofit.SuccessModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocalCallRecordingsActivity extends AppCompatActivity {
    ArrayList<RecordsModel> recordsModelArrayList;
    ArrayList<File> fileArrayList;
    ArrayList<String> FilesName = new ArrayList<>();
    TextView testing_check;
    ApiClient apiClient;
    Realm realm;
    ArrayList<BussinessCallRecorderModel> bussinessCallRecorderModels;
    ArrayList<GetRecords> getRecordsArrayList;
    ArrayList<BussinessCallRecorderModel> ForNumberOfFiles;
    ArrayList<BussinessCallRecorderModel> GetAllLocalReorderData;
    ConstraintLayout snake_bar;
    String Access_token;
    TextView upload_status_fileInfo;
    RecyclerView myRecordsRecyclerview;
    PlayListAdapter playListAdapter;
    int position = 1;
    ProgressBar upload_progress_bar;
    int NUMNER_OF_FILES = 0;
    int UPLOADED_NUMBER_OF_FILES = 0;
    int INDEX_OF_FILE = 0;
    String directoryPath;
    int currentapiVersion = android.os.Build.VERSION.SDK_INT;
    int versioncode = android.os.Build.VERSION_CODES.R;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_local_call_recordings);
        apiClient = ApiUtills.getAPIService();
        snake_bar = findViewById(R.id.snake_bar);
        upload_status_fileInfo = findViewById(R.id.upload_status_fileInfo);
        myRecordsRecyclerview = findViewById(R.id.myRecordsRecyclerview);
        myRecordsRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        upload_progress_bar = findViewById(R.id.upload_progress_bar);
        getFilesFromDirectory();


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getFilesFromDirectory() {
        upload_progress_bar.setVisibility(View.VISIBLE);
        fileArrayList = new ArrayList<>();
        if (fileArrayList != null) {
            fileArrayList.clear();
        }
        recordsModelArrayList = new ArrayList<>();
        if (recordsModelArrayList != null) {
            recordsModelArrayList.clear();
        }
        try {
            Log.d("currentapiVersion", String.valueOf(currentapiVersion));
            Log.d("versioncode", String.valueOf(versioncode));
            if (currentapiVersion >= versioncode && currentapiVersion!=versioncode) {
                directoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Recordings/Call";
            } else {
                directoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Call";
            }
            Log.d("directoryPath===>", directoryPath);
            File directory = new File(directoryPath);
            File[] files = directory.listFiles();
            Log.d("Files", "Size: " + files.length);
            for (int i = 0; i < files.length; i++) {
                RecordsModel recordsModel = new RecordsModel();
                recordsModel.setFileName(String.valueOf(files[i]));
                File file = new File(files[i].getPath());
                Date lastModDate = new Date(file.lastModified());
                Path filePath = file.toPath();
                BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);
                long createdAt = attr.creationTime().toMillis();
                recordsModel.setFileCreatedAt(String.valueOf(createdAt));
                fileArrayList.add(files[i]);
                FilesName.add(String.valueOf(files[i]));
                recordsModelArrayList.add(recordsModel);
            }
        } catch (Exception e) {

        }
        if (realm.where(UserToken.class).findFirst() != null) {
            UserToken access_token = realm.where(UserToken.class).findFirst();
            Access_token = access_token.getAccessToken();
            getBussinessCallDataFromCallData(access_token.getAccessToken());
        }

    }

    private void getBussinessCallDataFromCallData(String accessToken) {
        bussinessCallRecorderModels = new ArrayList<>();
        getRecordsArrayList = new ArrayList<>();
        apiClient.getBussinessCallRecorder("Bearer " + accessToken, "application/json").enqueue(new Callback<List<BussinessCallRecorderModel>>() {
            @Override
            public void onResponse(Call<List<BussinessCallRecorderModel>> call, Response<List<BussinessCallRecorderModel>> response) {
                try {
                    List<BussinessCallRecorderModel> callRecorderModels = response.body();
                    for (int a = 0; a < callRecorderModels.size(); a++) {
                        BussinessCallRecorderModel bussinessCallRecorderModel = new BussinessCallRecorderModel();
                        bussinessCallRecorderModel.setCompany_name(callRecorderModels.get(a).getCompany_name());
                        bussinessCallRecorderModel.setPerson_name(callRecorderModels.get(a).getPerson_name());
                        bussinessCallRecorderModel.setTime(callRecorderModels.get(a).getTime());
                        bussinessCallRecorderModel.setContact_type(callRecorderModels.get(a).getContact_type());
                        bussinessCallRecorderModel.setCall_duration(callRecorderModels.get(a).getCall_duration());
                        bussinessCallRecorderModel.setCreated_at(callRecorderModels.get(a).getCreated_at());
                        long retryDate = Long.parseLong(callRecorderModels.get(a).getTime());
                        Timestamp original = new Timestamp(retryDate);
                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(original.getTime());
                        cal.add(Calendar.SECOND, callRecorderModels.get(a).getCall_duration() + 60);
                        Timestamp later = new Timestamp(cal.getTime().getTime());
                        long endtime = later.getTime();
                        bussinessCallRecorderModel.setStart_time(callRecorderModels.get(a).getTime());
                        bussinessCallRecorderModel.setEnd_time(String.valueOf(endtime));
                        bussinessCallRecorderModels.add(bussinessCallRecorderModel);
                    }
                    if (bussinessCallRecorderModels.size() > 0) {
                        getFilesWithCompareUrl(bussinessCallRecorderModels);
                    } else {

                        DeleteAllLocalFiles();

                    }

                } catch (Exception e) {
                }

            }

            @Override
            public void onFailure(Call<List<BussinessCallRecorderModel>> call, Throwable t) {
            }
        });
    }

    private void getFilesWithCompareUrl(ArrayList<BussinessCallRecorderModel> getAllLocalReorderData) {
        ForNumberOfFiles = new ArrayList<>();
        GetAllLocalReorderData = getFilesFromResponce(getAllLocalReorderData);
        ForNumberOfFiles.addAll(GetAllLocalReorderData);
        if (GetAllLocalReorderData.size() > 0) {
            playListAdapter = new PlayListAdapter(LocalCallRecordingsActivity.this, GetAllLocalReorderData);
            myRecordsRecyclerview.setAdapter(playListAdapter);
            playListAdapter.notifyDataSetChanged();
            NUMNER_OF_FILES = GetAllLocalReorderData.size();
            upload_status_fileInfo.setText(UPLOADED_NUMBER_OF_FILES + " out of " + String.valueOf(NUMNER_OF_FILES));
        } else {
            Snackbar snackbar = Snackbar
                    .make(snake_bar, "No Records Found....!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        upload_progress_bar.setVisibility(View.GONE);

    }

    private ArrayList<BussinessCallRecorderModel> getFilesFromResponce(ArrayList<BussinessCallRecorderModel> bussinessCallRecorderModels) {
        ArrayList<BussinessCallRecorderModel> filteredFiles = new ArrayList<>();
        for (int i = 0; i < recordsModelArrayList.size(); i++) {
            for (int j = 0; j < bussinessCallRecorderModels.size(); j++) {
                long first_file_created = Long.valueOf(recordsModelArrayList.get(i).getFileCreatedAt());

                long call_start = Long.valueOf(bussinessCallRecorderModels.get(j).getStart_time());
                long call_end = Long.valueOf(bussinessCallRecorderModels.get(j).getEnd_time());

                if (first_file_created > call_start && first_file_created < call_end) {
                    BussinessCallRecorderModel bussinessCallRecorderModel = new BussinessCallRecorderModel();
                    bussinessCallRecorderModel.setCompany_name(bussinessCallRecorderModels.get(j).getCompany_name());
                    bussinessCallRecorderModel.setPerson_name(bussinessCallRecorderModels.get(j).getPerson_name());
                    bussinessCallRecorderModel.setFile_name(recordsModelArrayList.get(i).getFileName());
                    bussinessCallRecorderModel.setCall_duration(bussinessCallRecorderModels.get(j).getCall_duration());
                    bussinessCallRecorderModel.setTime(bussinessCallRecorderModels.get(j).getTime());
                    bussinessCallRecorderModel.setContact_type(bussinessCallRecorderModels.get(j).getContact_type());
                    filteredFiles.add(bussinessCallRecorderModel);
                    Log.d("generate_files", recordsModelArrayList.get(i).getFileName());
                }
            }
        }
        return filteredFiles;
    }


    public void backToMainActivityFromCallRecording(View view) {
        Intent intent = new Intent(LocalCallRecordingsActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void UploadUserRecorordsToServer(View view) {

        if (GetAllLocalReorderData.size() > 0) {

            UPLOAD_FILE_TO_SERVER(INDEX_OF_FILE);

            /*for (int i = 0; i < GetAllLocalReorderData.size(); i++) {
                if (isConnected()) {
                    upload_progress_bar.setVisibility(View.VISIBLE);
                    CheckFileIsExisting(GetAllLocalReorderData.get(i).getFile_name(), i, GetAllLocalReorderData.get(i).getTime());


                    //  generatePresignedUrl(GetAllLocalReorderData.get(i).getFile_name(), i, GetAllLocalReorderData.get(i).getTime());
                } else {
                    Snackbar snackbar = Snackbar
                            .make(snake_bar, "No Internet Connection Found....!.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }*/
        } else {
            Snackbar snackbar = Snackbar
                    .make(snake_bar, "No Records Found....!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private void UPLOAD_FILE_TO_SERVER(int index_of_file) {
        if (isConnected()) {
            upload_progress_bar.setVisibility(View.VISIBLE);
            CheckFileIsExisting(GetAllLocalReorderData.get(index_of_file).getFile_name(), index_of_file, GetAllLocalReorderData.get(index_of_file).getTime());
            //  generatePresignedUrl(GetAllLocalReorderData.get(i).getFile_name(), i, GetAllLocalReorderData.get(i).getTime());
        } else {
            Snackbar snackbar = Snackbar
                    .make(snake_bar, "No Internet Connection Found....!.", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    private void CheckFileIsExisting(final String file_name, final int index, final String time) {
        try {
            Log.d("call_quee",file_name);
            Log.d("call_quee",""+index);
            Log.d("call_quee",""+time);

        }catch (Exception e){
            Log.d("call_quee",file_name+e.getMessage());
        }

        apiClient.checkCallRecordingExistance(time, "Bearer " + Access_token, "application/json").enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                try {
                    if (response.code() == 200) {
                        if (response.body().getExists().equals("no")) {
                            generatePresignedUrl(file_name, index, time);
                            UPLOADED_NUMBER_OF_FILES = UPLOADED_NUMBER_OF_FILES + 1;
                            Log.d("call_quee",String.valueOf(INDEX_OF_FILE)+"Success");
                        }
                        if (response.body().getExists().equals("yes")) {
                            DeleteFileFromLocal(file_name);
                            INDEX_OF_FILE = INDEX_OF_FILE+1;
                            UPLOAD_FILE_TO_SERVER(INDEX_OF_FILE);
                            UPLOADED_NUMBER_OF_FILES = UPLOADED_NUMBER_OF_FILES + 1;
                            Log.d("call_quee",String.valueOf(INDEX_OF_FILE)+"Error");
                        }

                    } else {
                        Toast.makeText(LocalCallRecordingsActivity.this, "Some thing went wrong", Toast.LENGTH_SHORT).show();
                        Log.d("call_quee",String.valueOf(INDEX_OF_FILE)+"Error in else");
                    }

                    Log.d("checkFile", response.body().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("call_quee",String.valueOf(INDEX_OF_FILE)+"Error in catch"+e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                Log.d("call_quee",String.valueOf(INDEX_OF_FILE)+"Error in failure"+t.getMessage());
                Toast.makeText(LocalCallRecordingsActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void DeleteFileFromLocal(String file_name) {
        try {
            try {
                Log.d("currentapiVersion", String.valueOf(currentapiVersion));
                Log.d("versioncode", String.valueOf(versioncode));
                if (currentapiVersion >= versioncode && currentapiVersion!=versioncode) {
                    directoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Recordings/Call";
                } else {
                    directoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Call";
                }
                Log.d("directoryPath===>", directoryPath);
                File directory = new File(directoryPath);
                File[] files = directory.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].toString().equals(file_name)) {
                        new File(directory, files[i].getName()).delete();
                        Toast.makeText(this, "File Already Uploaded", Toast.LENGTH_SHORT).show();
                        GetAllLocalReorderData.remove(0);
                        playListAdapter.notifyItemRemoved(0);
                        upload_status_fileInfo.setText(UPLOADED_NUMBER_OF_FILES + " out of " + String.valueOf(NUMNER_OF_FILES));
                    }
                }
            } catch (Exception e) {
                Toast.makeText(this, "Some thing went wrong.Please try after some time", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public boolean isConnected() {

        ConnectivityManager mgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mgr.getActiveNetworkInfo();

        if (netInfo != null) {
            if (netInfo.isConnected()) {
                // Internet Available
                return true;
            } else {
                //No internet
                return false;
            }
        } else {
            //No internet
        }
        return false;
    }

    private void generatePresignedUrl(final String file, final int index, String s) {
        long timeStamp = System.currentTimeMillis();
        Log.d("time_stamp", String.valueOf(timeStamp));
        Log.d("PresignedUrl", String.valueOf(s));
        apiClient.getGeneratedPresignedUrl(s, "Bearer " + Access_token, "application/json").enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                if (response.body().getUrl() != null) {
                    String URL = response.body().getUrl();
                    Log.d("PresignedUrl", URL);
                    Log.d("call_quee","pre signed"+""+URL);

                    if (isConnected()) {
                        SendFileDataToServer(file, URL, index);
                    } else {
                        Snackbar snackbar = Snackbar
                                .make(snake_bar, "No Internet Connection Found....!", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                    //recordsModelArrayList.clear();

                }
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                if (isConnected()) {
                    Snackbar snackbar = Snackbar
                            .make(snake_bar, "Some thing went wrong....!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    Snackbar snackbar = Snackbar
                            .make(snake_bar, "No Internet Connection Found....!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }


            }
        });
    }

    private void SendFileDataToServer(final String fileName, final String Url, final int index) {
        File file = new File(fileName);
        Log.d("aync_test",fileName);
        RequestBody requestBody = null;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] buf;
            buf = new byte[in.available()];
            while (in.read(buf) != -1) ;
            requestBody = RequestBody
                    .create(MediaType.parse("application/octet-stream"), buf);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        apiClient.UploadBinaryFile(Url, requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Toast.makeText(LocalCallRecordingsActivity.this, "Uploaded Successfully....!!", Toast.LENGTH_SHORT).show();
                    upload_status_fileInfo.setText(UPLOADED_NUMBER_OF_FILES + " out of " + String.valueOf(NUMNER_OF_FILES));
                    position = position + 1;
                    try {

                        INDEX_OF_FILE = INDEX_OF_FILE+1;
                        Log.d("FileUploading ",String.valueOf(INDEX_OF_FILE));
                        UPLOAD_FILE_TO_SERVER(INDEX_OF_FILE);
                        GetAllLocalReorderData.remove(INDEX_OF_FILE);
                        playListAdapter.notifyItemRemoved(INDEX_OF_FILE);
                        upload_progress_bar.setVisibility(View.GONE);
                        Log.d("call_quee",Url+INDEX_OF_FILE);
                    } catch (Exception e) {
                        Log.d("call_quee",Url+INDEX_OF_FILE+e.getMessage());
                    }
                    /*if (NUMNER_OF_FILES==UPLOADED_NUMBER_OF_FILES){
                        DeleteAllLocalFiles();
                    }*/


                }
                upload_progress_bar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("call_quee",Url+INDEX_OF_FILE+t.getMessage());
                t.printStackTrace();
                if (fileArrayList.size() == 0) {
                } else {
                }
                if (isConnected()) {
                    Snackbar snackbar = Snackbar
                            .make(snake_bar, "Some thing went wrong. Please try again....!", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    Snackbar snackbar = Snackbar
                            .make(snake_bar, "No Internet Connection Found....!.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
                upload_progress_bar.setVisibility(View.GONE);
            }
        });
    }


    private void DeleteAllLocalFiles() {
        try {
            try {
                Log.d("currentapiVersion", String.valueOf(currentapiVersion));
                Log.d("versioncode", String.valueOf(versioncode));
                if (currentapiVersion >= versioncode && currentapiVersion!=versioncode) {
                    directoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Recordings/Call";
                } else {
                    directoryPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Call";
                }
                Log.d("directoryPath===>", directoryPath);


                File directory = new File(directoryPath);
                File[] files = directory.listFiles();
                for (int i = 0; i < files.length; i++) {
                    new File(directory, files[i].getName()).delete();
                }
                Toast.makeText(this, "All Local Files Deleted", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(this, "Some thing went wrong.Please try after some time", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

}
