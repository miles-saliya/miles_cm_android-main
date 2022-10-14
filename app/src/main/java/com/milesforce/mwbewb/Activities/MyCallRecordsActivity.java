package com.milesforce.mwbewb.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.milesforce.mwbewb.LocalCallRecordingsActivity;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;
import com.milesforce.mwbewb.Retrofit.CommanApiClient;
import com.milesforce.mwbewb.Retrofit.CommanApiUtills;
import com.milesforce.mwbewb.Retrofit.SuccessModel;
import com.milesforce.mwbewb.Utils.ConstantUtills;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.milesforce.mwbewb.Utils.ConstantUtills.SaveToken;

public class MyCallRecordsActivity extends AppCompatActivity {
    RecyclerView myRecordsRecyclerview;
    PlayListAdapter playListAdapter;
    int position = 1;

    TextView file_info_Text, uploaded_file;
    FloatingActionButton actionButton;
    ArrayList<File> fileArrayList;
    ArrayList<String> FilesName = new ArrayList<>();
    ArrayList<String> savedSamefiles = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;

    ApiClient apiClient;
    CommanApiClient commanApiClient;
    String extension;


    SharedPreferences sharedPreferences, TimeSharedPreference;
    String accessToken;
    String FILE;
    ProgressBar upload_progress_bar;
    ArrayList<BussinessCallRecorderModel> bussinessCallRecorderModels;
    ConstraintLayout snake_bar;
    ArrayList<BussinessCallRecorderModel> GetAllLocalReorderData;
    ArrayList<BussinessCallRecorderModel> ForNumberOfFiles;
    ArrayList<RecordsModel> recordsModelArrayList;
    TextView upload_status_fileInfo;
    int NUMNER_OF_FILES = 0;
    int UPLOADED_NUMBER_OF_FILES = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_call_records);
        myRecordsRecyclerview = findViewById(R.id.myRecordsRecyclerview);
        upload_status_fileInfo = findViewById(R.id.upload_status_fileInfo);
        sharedPreferences = getApplicationContext().getSharedPreferences(SaveToken, MODE_PRIVATE);
        accessToken = sharedPreferences.getString(ConstantUtills.AccessToken, null);
        snake_bar = findViewById(R.id.snake_bar);
        upload_progress_bar = findViewById(R.id.upload_progress_bar);
        myRecordsRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        apiClient = ApiUtills.getAPIService();
        commanApiClient = CommanApiUtills.getAPIService();

        getFilesFromDirectory();

    }

    private void getBussinessCallDataFromCallData(String accessToken) {
        bussinessCallRecorderModels = new ArrayList<>();
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
                        bussinessCallRecorderModel.setFilename(callRecorderModels.get(a).getTime() + ".mp3");
                        bussinessCallRecorderModel.setContact_type(callRecorderModels.get(a).getContact_type());
                        bussinessCallRecorderModel.setCall_duration(callRecorderModels.get(a).getCall_duration());
                        bussinessCallRecorderModel.setCreated_at(callRecorderModels.get(a).getCreated_at());
                        bussinessCallRecorderModels.add(bussinessCallRecorderModel);
                    }
                    if (bussinessCallRecorderModels.size() > 0) {
                        getFilesWithCompareUrl(bussinessCallRecorderModels);
                    } else {
                        DeleteAllLocalFiles();
                    }

                } catch (Exception e) {
                    upload_progress_bar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<List<BussinessCallRecorderModel>> call, Throwable t) {
                upload_progress_bar.setVisibility(View.GONE);
            }
        });
    }

    private void getFilesWithCompareUrl(ArrayList<BussinessCallRecorderModel> bussinessCallRecorderModels) {
        ForNumberOfFiles = new ArrayList<>();
        GetAllLocalReorderData = getFilesFromResponce(bussinessCallRecorderModels);
        ForNumberOfFiles.addAll(GetAllLocalReorderData);
        upload_status_fileInfo.setText("0" + " out of " + String.valueOf(ForNumberOfFiles.size()));
        if (GetAllLocalReorderData.size() > 0) {
            playListAdapter = new PlayListAdapter(MyCallRecordsActivity.this, GetAllLocalReorderData);
            myRecordsRecyclerview.setAdapter(playListAdapter);
            playListAdapter.notifyDataSetChanged();
            upload_progress_bar.setVisibility(View.GONE);
            NUMNER_OF_FILES = GetAllLocalReorderData.size();
        } else {
            upload_progress_bar.setVisibility(View.GONE);
            Snackbar snackbar = Snackbar
                    .make(snake_bar, "No Records Found....!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private ArrayList<BussinessCallRecorderModel> getFilesFromResponce(ArrayList<BussinessCallRecorderModel> bussinessCallRecorderModels) {
        ArrayList<BussinessCallRecorderModel> filteredFiles = new ArrayList<>();
        for (int i = 0; i < fileArrayList.size(); i++) {
            for (int j = 0; j < bussinessCallRecorderModels.size(); j++) {
                if (fileArrayList.get(i).getName().equals(bussinessCallRecorderModels.get(j).getFilename())) {
                    BussinessCallRecorderModel bussinessCallRecorderModel = new BussinessCallRecorderModel();
                    bussinessCallRecorderModel.setFilename(String.valueOf(fileArrayList.get(i)));
                    bussinessCallRecorderModel.setTime(bussinessCallRecorderModels.get(j).getTime());
                    bussinessCallRecorderModel.setPerson_name(bussinessCallRecorderModels.get(j).getPerson_name());
                    bussinessCallRecorderModel.setCompany_name(bussinessCallRecorderModels.get(j).getCompany_name());
                    bussinessCallRecorderModel.setContact_type(bussinessCallRecorderModels.get(j).getContact_type());
                    bussinessCallRecorderModel.setCreated_at(bussinessCallRecorderModels.get(j).getCreated_at());
                    bussinessCallRecorderModel.setCall_duration(bussinessCallRecorderModels.get(j).getCall_duration());
                    filteredFiles.add(bussinessCallRecorderModel);
                }
            }
        }
        return filteredFiles;
    }


    private void getFilesFromDirectory() {
        fileArrayList = new ArrayList<>();
        if (fileArrayList != null) {
            fileArrayList.clear();
        }
        recordsModelArrayList = new ArrayList<>();
        if (recordsModelArrayList != null) {
            recordsModelArrayList.clear();
        }
        try {
            String fileNmae = Environment.getExternalStorageDirectory() + "/MilesForceMwb";
            File directory = new File(fileNmae);
            File[] files = directory.listFiles();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());
            }
            Log.d("Files", "Size: " + files.length);
            for (int i = 0; i < files.length; i++) {
                RecordsModel recordsModel = new RecordsModel();
                recordsModel.setFileName(String.valueOf(files[i]));
                recordsModel.setPosition(0);
                recordsModel.setIs_Checked(false);
                fileArrayList.add(files[i]);
                FilesName.add(String.valueOf(files[i]));
                recordsModelArrayList.add(recordsModel);
                Log.d("Files", "FileName:" + files[i].getName());
            }

        } catch (Exception e) {

        }

        if (isConnected()) {
            getBussinessCallDataFromCallData(accessToken);
        } else {
            Snackbar snackbar = Snackbar
                    .make(snake_bar, "No Internet Connection Found....!.", Snackbar.LENGTH_LONG);
            snackbar.show();
        }


    }

    public void backToMainActivity(View view) {
        startActivity(new Intent(MyCallRecordsActivity.this, MainActivity.class));
        finish();
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

    public void UploadMyRecorordsToServer(View view) {
        if (GetAllLocalReorderData.size() > 0) {
            upload_progress_bar.setVisibility(View.VISIBLE);
            for (int i = 0; i < GetAllLocalReorderData.size(); i++) {
                if (isConnected()) {

                    Log.d("GetAllLocalReorderData", GetAllLocalReorderData.get(i).getFile_name());


                    String beforeExtension = GetAllLocalReorderData.get(i).getFile_name();

                    String extension = beforeExtension.substring(beforeExtension.indexOf(".") - 0);

                    Log.d("extension", extension);
                    CheckFileIsExisting(GetAllLocalReorderData.get(i).getFilename(), i, GetAllLocalReorderData.get(i).getTime());

                    //  generatePresignedUrl(GetAllLocalReorderData.get(i).getFilename(), i, GetAllLocalReorderData.get(i).getTime());
                } else {
                    Snackbar snackbar = Snackbar
                            .make(snake_bar, "No Internet Connection Found....!.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

            }
        } else {
            Snackbar snackbar = Snackbar
                    .make(snake_bar, "No Records Found....!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private void generatePresignedUrl(final String file,String extension, final int index, String s) {
        long timeStamp = System.currentTimeMillis();
        Log.d("time_stamp", String.valueOf(timeStamp));
        Log.d("PresignedUrl", String.valueOf(s));


//        apiClient.getGeneratedPresignedUrl(s, "Bearer " + accessToken, "application/json").enqueue(new Callback<SuccessModel>() {
        commanApiClient.getGeneratedPresignedUrl(s, extension,"Bearer " + accessToken, "application/json").enqueue(new Callback<SuccessModel>() {
        @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                if (response.body().getUrl() != null) {
                    String URL = response.body().getUrl();
                    Log.d("PresignedUrl", URL);

                    if (isConnected()) {
                        if (file != null) {
                            SendFileDataToServer(file, URL, index);
                        }
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
                upload_progress_bar.setVisibility(View.GONE);
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
                    Toast.makeText(MyCallRecordsActivity.this, "Uploaded Successfully....!!", Toast.LENGTH_SHORT).show();
                    upload_status_fileInfo.setText(UPLOADED_NUMBER_OF_FILES + " out of " + String.valueOf(NUMNER_OF_FILES));
                    //upload_status_fileInfo.setText(position + " out of " + String.valueOf(ForNumberOfFiles.size()));
                    position = position + 1;
                    try {

                        /*File dir = new File(Environment.getExternalStorageDirectory() + "/MilesForceMwb");
                        if (dir.exists()) {
                            String fileName_ = new File(fileName).getName();
                            File from = new File(dir, fileName_);
                            from.delete();
                        }*/
                        GetAllLocalReorderData.remove(0);
                        playListAdapter.notifyItemRemoved(0);
                        /*fileArrayList.get(index).delete();*/
                        /* upload_progress_bar.setVisibility(View.GONE);*/

                        /*if (GetAllLocalReorderData.size() == 0) {
                            DeleteAllFiles();
                        }*/

                       /* if (NUMNER_OF_FILES==UPLOADED_NUMBER_OF_FILES){
                          //  DeleteAllLocalFiles();
                        }
*/


                    } catch (Exception e) {
                        upload_progress_bar.setVisibility(View.GONE);
                        if (isConnected()) {
                            Snackbar snackbar = Snackbar
                                    .make(snake_bar, "Some thing went wrong. Please try again....!", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        } else {
                            Snackbar snackbar = Snackbar
                                    .make(snake_bar, "No Internet Connection Found....!.", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }

                    }
                    if (fileArrayList.size() == index) {
                        upload_progress_bar.setVisibility(View.GONE);
                    }

                }
                upload_progress_bar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                if (fileArrayList.size() == 0) {
                    upload_progress_bar.setVisibility(View.GONE);
                } else {
                    upload_progress_bar.setVisibility(View.GONE);
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
            }
        });
    }

    private void DeleteAllFiles() {
        try {
            String fileNmae = Environment.getExternalStorageDirectory() + "/MilesForceMwb";
            File directory = new File(fileNmae);

            File[] files = directory.listFiles();
            for (int i = 0; i < files.length; i++) {
                new File(directory, files[i].getName()).delete();
            }
        } catch (Exception e) {

        }
    }

    private void CheckFileIsExisting(final String file_name, final int index, final String time) {
        Log.d("CheckFileIsExisting", file_name);
         extension = file_name.substring(file_name.indexOf(".") - 0);
        Log.d("CheckFileIsExisting", extension);
//        apiClient.checkCallRecordingExistance(time, "Bearer " + accessToken, "application/json").enqueue(new Callback<SuccessModel>() {
        commanApiClient.checkCallRecordingExistance(time, extension,"Bearer " + accessToken, "application/json").enqueue(new Callback<SuccessModel>() {

            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                try {
                    if (response.code() == 200) {
                        if (response.body().getExists().equals("no")) {
                            generatePresignedUrl(file_name,extension, index, time);
                            UPLOADED_NUMBER_OF_FILES = UPLOADED_NUMBER_OF_FILES + 1;
                        }
                        if (response.body().getExists().equals("yes")) {
                            DeleteFileFromLocal(file_name);
                            UPLOADED_NUMBER_OF_FILES = UPLOADED_NUMBER_OF_FILES + 1;
                        }

                    } else {
                        Toast.makeText(MyCallRecordsActivity.this, "Some thing went wrong", Toast.LENGTH_SHORT).show();
                    }

                    Log.d("checkFile", response.body().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                Toast.makeText(MyCallRecordsActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void DeleteFileFromLocal(String file_name) {
        try {
            try {
                String fileName = Environment.getExternalStorageDirectory() + "/MilesForceMwb";
                File directory = new File(fileName);
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

    private void DeleteAllLocalFiles() {
        try {
            try {
                String fileName = Environment.getExternalStorageDirectory() + "/MilesForceMwb";
                File directory = new File(fileName);
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
