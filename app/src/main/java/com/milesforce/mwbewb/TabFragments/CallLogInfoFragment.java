package com.milesforce.mwbewb.TabFragments;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.milesforce.mwbewb.Activities.AddEngagementActivity;
import com.milesforce.mwbewb.Activities.EWBSRActivity;
import com.milesforce.mwbewb.Model.CallLogDataModel;
import com.milesforce.mwbewb.Model.DelaysModel;
import com.milesforce.mwbewb.Model.SrModelInfo;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;
import com.milesforce.mwbewb.Utils.CallLogAdapter;
import com.milesforce.mwbewb.Utils.CallRecord;
import com.milesforce.mwbewb.Utils.ConstantUtills;
import com.milesforce.mwbewb.Utils.ResumeWork;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.milesforce.mwbewb.Utils.ConstantUtills.SaveToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.fragment_token;

/**
 * A simple {@link Fragment} subclass.
 */
public class CallLogInfoFragment extends Fragment {
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;
    RecyclerView recyclerView;
    CallLogAdapter toUpdateAdapter;
    ArrayList<SrModelInfo> srModelInfos;
    SwipeRefreshLayout toUpdateSwipeTorefresh;
    String AccessToken_new;
    ApiClient apiClient;
    ArrayList<DelaysModel> callLogArrayList;
    int page_number = 1;
    ProgressBar progressbar, bottom_toupdateProgress, bottom_missed_progress, progressBar_call_log;
    FloatingActionButton floating_toupdate, toUpdate_refresh_floating;
    CallRecord callRecord;
    SharedPreferences sharedPreferences;
    AlertDialog alert;
    LinearLayoutManager linearLayoutManager;

    public CallLogInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        sharedPreferences = getContext().getSharedPreferences(SaveToken, MODE_PRIVATE);
        AccessToken_new = sharedPreferences.getString(ConstantUtills.AccessToken, null);
        apiClient = ApiUtills.getAPIService();
        return inflater.inflate(R.layout.fragment_call_log_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intiviews(view);
        getCallLogData(AccessToken_new);
    }

    private void getCallLogData(String accessToken_new) {
        progressBar_call_log.setVisibility(View.VISIBLE);
        callLogArrayList = new ArrayList<>();
        apiClient.getCallLogs("Bearer " + accessToken_new, "application/json").enqueue(new Callback<CallLogDataModel>() {
            @Override
            public void onResponse(Call<CallLogDataModel> call, Response<CallLogDataModel> response) {
                try {
                    if(response.raw().code() == 515){
                        new ResumeWork().stResumeWork(getContext());
                        progressBar_call_log.setVisibility(View.GONE);
                        return;
                    }
                    List<DelaysModel> getCallLogs = response.body().getDashboard_data();
                    for (int i = 0; i < getCallLogs.size(); i++) {
                        DelaysModel callLogs = new DelaysModel();
                        callLogs.setId(getCallLogs.get(i).getId());
                        callLogs.setPerson_id(getCallLogs.get(i).getPerson_id());
                        callLogs.setPerson_name(getCallLogs.get(i).getPerson_name());
                        callLogs.setPhone_number(getCallLogs.get(i).getPhone_number());
                        callLogs.setDirectory(getCallLogs.get(i).getDirectory());
                        callLogs.setCall_duration(getCallLogs.get(i).getCall_duration());
                        callLogs.setContact_type(getCallLogs.get(i).getContact_type());
                        callLogs.setTime(getCallLogs.get(i).getTime());
                        callLogs.setCall_duration(getCallLogs.get(i).getCall_duration());
                        callLogs.setCan_id(getCallLogs.get(i).getCan_id());
                        callLogs.setPerson_id(getCallLogs.get(i).getPerson_id());
                        callLogs.setPerson_name(getCallLogs.get(i).getPerson_name());
                        callLogs.setCourses(getCallLogs.get(i).getCourses());
                        callLogs.setLevel(getCallLogs.get(i).getLevel());
                        callLogs.setEducation(getCallLogs.get(i).getEducation());
                        callLogs.setDetails(getCallLogs.get(i).getEngagement_details());
                        callLogs.setLast_call(getCallLogs.get(i).getLast_call());
                        callLogs.setNext_call(getCallLogs.get(i).getNext_call());
                        callLogs.setCreated_at(getCallLogs.get(i).getCreated_at());
                        callLogs.setUpdated_at(getCallLogs.get(i).getUpdated_at());
                        callLogs.setCompany(getCallLogs.get(i).getCompany());
                        callLogs.setDesignation(getCallLogs.get(i).getDesignation());
                        callLogs.setExperience(getCallLogs.get(i).getExperience());
                        callLogs.setSubmitted_documents(getCallLogs.get(i).getSubmitted_documents());
                        callLogs.setApplied_for_loan(getCallLogs.get(i).getApplied_for_loan());
                        callLogs.setLoan_status(getCallLogs.get(i).getLoan_status());
                        callLogs.setIiml_level(getCallLogs.get(i).getIiml_level());
                        callLogs.setEducation_tags(getCallLogs.get(i).getEducation_tags());
                        if (getCallLogs.get(i).getCompany() != null) {
                            callLogs.setCompany(getCallLogs.get(i).getCompany());
                        } else {
                            callLogs.setCompany(" ");
                        }
                        if (getCallLogs.get(i).getSource() != null) {
                            callLogs.setSource(getCallLogs.get(i).getSource());
                        } else {
                            callLogs.setSource(" ");
                        }
                        callLogs.setIdentity(getCallLogs.get(i).getIdentity());
                        callLogArrayList.add(callLogs);
                    }
                    toUpdateAdapter = new CallLogAdapter(getContext(), callLogArrayList);
                    recyclerView.setAdapter(toUpdateAdapter);
                    toUpdateAdapter.notifyDataSetChanged();
                    floating_toupdate.setImageBitmap(textAsBitmap(String.valueOf(callLogArrayList.size()), 40, Color.BLUE));
                    toUpdateAdapter.setOnItemClickListener(new CallLogAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int pos, String type) {
                            DelaysModel callLogs = callLogArrayList.get(pos);
                            if (callLogs.getLevel().equals("M7")){
                                Intent intent = new Intent(getContext(), EWBSRActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("value", callLogs);
                                intent.putExtras(bundle);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }else {
                                Intent intent = new Intent(getContext(), AddEngagementActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("value", callLogs);
                                intent.putExtras(bundle);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                            /*Intent intent = new Intent(getContext(), AddEngagementActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("value", callLogs);
                            intent.putExtras(bundle);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);*/
                        }
                    });
                    progressBar_call_log.setVisibility(View.GONE);
                    toUpdateSwipeTorefresh.setRefreshing(false);
                } catch (Exception e) {
                    progressBar_call_log.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<CallLogDataModel> call, Throwable t) {
                progressBar_call_log.setVisibility(View.GONE);
            }
        });
    }

    private void intiviews(View view) {
        recyclerView = view.findViewById(R.id.callLog_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar_call_log = view.findViewById(R.id.progressBar_call_log);
        bottom_missed_progress = view.findViewById(R.id.bottom_missed_progress);
        toUpdateSwipeTorefresh = view.findViewById(R.id.call_LogSwipeTorefresh);
        toUpdate_refresh_floating = view.findViewById(R.id.missed_refresh_floating);
        floating_toupdate = view.findViewById(R.id.floating_missed);
        floating_toupdate.setImageBitmap(textAsBitmap(String.valueOf(0), 40, Color.BLUE));
        toUpdate_refresh_floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callLogArrayList.size() > 0) {
                    callLogArrayList.clear();
                }
                getCallLogData(AccessToken_new);
            }
        });
        toUpdateSwipeTorefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                toUpdateSwipeTorefresh.setRefreshing(false);
            }
        });
    }

    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }
}
