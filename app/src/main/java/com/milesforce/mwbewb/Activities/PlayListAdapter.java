package com.milesforce.mwbewb.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.SuccessModel;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.ViewHolder> {

    Context context;
    ArrayList<BussinessCallRecorderModel> fileArrayList;
    MediaPlayer mediaPlayer;
    SharedPreferences sharedPreferences, TimeSharedPreference;
    String AccessToken;
    ApiClient apiClient;
    int position = 0;
    PlayListAdapter.ViewHolder holder;
    String timeString = "0";

    public PlayListAdapter(Context context, ArrayList<BussinessCallRecorderModel> fileArray) {
        this.context = context;
        this.fileArrayList = fileArray;
        sharedPreferences = context.getSharedPreferences("saveToken", MODE_PRIVATE);
        AccessToken = sharedPreferences.getString("AccessToken", null);

    }

    @NonNull
    @Override
    public PlayListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.play_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayListAdapter.ViewHolder holder, final int i) {
        holder.student_callerName.setText(fileArrayList.get(i).getPerson_name() + " - " + fileArrayList.get(i).getContact_type());
        holder.call_at_caller.setText(fileArrayList.get(i).getCreated_at());
        holder.caller_duration.setText("Duration :" + convertSecondsToHourMinuteSecondFormat(String.valueOf(fileArrayList.get(i).getCall_duration())));

    }


    @Override
    public int getItemCount() {
        return fileArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView student_callerName, company_name, caller_duration, time, call_at_caller;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            student_callerName = itemView.findViewById(R.id.student_callerName);
            call_at_caller = itemView.findViewById(R.id.call_at_caller);
            caller_duration = itemView.findViewById(R.id.caller_duration);


        }

    }

    public String convertSecondsToHourMinuteSecondFormat(String duration) {

        try {
            Long totalSecs = Long.valueOf(duration);
            Long hours = totalSecs / 3600;
            Long minutes = (totalSecs % 3600) / 60;
            Long seconds = totalSecs % 60;
            timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        } catch (Exception dd) {

        }
        return timeString;
    }


}
