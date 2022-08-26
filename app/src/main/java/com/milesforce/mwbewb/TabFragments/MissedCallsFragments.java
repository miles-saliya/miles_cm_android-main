package com.milesforce.mwbewb.TabFragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.milesforce.mwbewb.Activities.LoginActivity;
import com.milesforce.mwbewb.Model.CallData;
import com.milesforce.mwbewb.Model.DashBoardModel;
import com.milesforce.mwbewb.Model.DelaysModel;
import com.milesforce.mwbewb.Model.MissedCallLogModel;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;
import com.milesforce.mwbewb.Utils.ConstantUtills;
import com.milesforce.mwbewb.Utils.ResumeWork;
import com.milesforce.mwbewb.Utils.TriggerCallWIthTelephoneManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.milesforce.mwbewb.Utils.ConstantUtills.SaveToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.fragment_token;

/**
 * A simple {@link Fragment} subclass.
 */
public class MissedCallsFragments extends Fragment {
    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;
    RecyclerView missedRecyclerView;
    MissedCallAdapter missedCallAdapter;
    ApiClient apiClient;
    ArrayList<DelaysModel> missedCallModelArrayList;
    SwipeRefreshLayout missedSwipeTorefresh;
    String AccessToken;
    int page_number = 1;
    ProgressBar progressBar_missed, bottom_missed_progress;
    FloatingActionButton floating_missed, missed_refresh_floating;
    SharedPreferences sharedPreferences;
    AlertDialog alert;
    ArrayList<DashBoardModel> dashBoardModelArrayList;

    public MissedCallsFragments() {
        // Required empty public constructor
    }

    public static MissedCallsFragments newInstance() {
        MissedCallsFragments fragment = new MissedCallsFragments();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        apiClient = ApiUtills.getAPIService();
        AccessToken = getArguments().getString(fragment_token);
        sharedPreferences = getContext().getSharedPreferences(SaveToken, MODE_PRIVATE);
        return inflater.inflate(R.layout.fragment_missed_calls_fragments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intiViews(view);
        getMissedCalldata(1);
    }

    private void getMissedCalldata(int page_number) {
        progressBar_missed.setVisibility(View.VISIBLE);
        dashBoardModelArrayList = new ArrayList<DashBoardModel>();
        missedCallModelArrayList = new ArrayList<>();
        apiClient.getMissedCallsData(page_number, "Bearer " + AccessToken, "").enqueue(new Callback<MissedCallLogModel>() {
            @Override
            public void onResponse(Call<MissedCallLogModel> call, Response<MissedCallLogModel> response) {

                try {
                    if(response.raw().code() == 515){
                        new ResumeWork().stResumeWork(getContext());
                        return;
                    }
                    if (response.body() == null) {
                        int statusCode = response.raw().code();
                        showAlertForStatus(String.valueOf(response.raw().message()));
                        if (statusCode == 401) {
                            SessionLogout(getContext());
                            progressBar_missed.setVisibility(View.GONE);
                        }
                    } else {
                        ArrayList<DashBoardModel> getCallLogs = response.body().getDashboard_data();
                        for (int i = 0; i < getCallLogs.size(); i++) {
                            DashBoardModel dashBoardModel = new DashBoardModel();
                            CallData callData = getCallLogs.get(i).getCall_data();
                            CallData callLog = new CallData();
                            callLog.setId(callData.getId());
                            callLog.setPerson_id(callData.getPerson_id());
                            callLog.setPerson_name(callData.getPerson_name());
                            callLog.setPhone_number(callData.getPhone_number());
                            callLog.setDirectory(callData.getDirectory());
                            callLog.setCreated_at(callData.getCreated_at());
                            callLog.setCall_duration(callData.getCall_duration());
                            callLog.setTime(callData.getTime());
                            dashBoardModel.setCall_data(callLog);
                            /*Delays Model*/
                            DelaysModel delaysModel = getCallLogs.get(i).getMwb();
                            DelaysModel callLogs = new DelaysModel();
                            callLogs.setId(delaysModel.getId());
                            callLogs.setPerson_id(delaysModel.getPerson_id());
                            callLogs.setPerson_name(delaysModel.getPerson_name());
                            callLogs.setPhone_number(delaysModel.getPhone_number());
                            callLogs.setDirectory(delaysModel.getDirectory());
                            callLogs.setCall_duration(delaysModel.getCall_duration());
                            callLogs.setContact_type(delaysModel.getContact_type());
                            callLogs.setTime(delaysModel.getTime());
                            callLogs.setCall_duration(delaysModel.getCall_duration());
                            callLogs.setCan_id(delaysModel.getCan_id());
                            callLogs.setPerson_id(delaysModel.getPerson_id());
                            callLogs.setPerson_name(delaysModel.getPerson_name());
                            callLogs.setCourses(delaysModel.getCourses());
                            callLogs.setLevel(delaysModel.getLevel());
                            callLogs.setDetails(delaysModel.getEngagement_details());
                            callLogs.setLast_call(delaysModel.getLast_call());
                            callLogs.setNext_call(delaysModel.getNext_call());
                            callLogs.setCreated_at(delaysModel.getCreated_at());
                            callLogs.setUpdated_at(delaysModel.getUpdated_at());
                            callLogs.setCompany(delaysModel.getCompany());
                            callLogs.setDesignation(delaysModel.getDesignation());
                            dashBoardModel.setMwb(callLogs);
                            dashBoardModelArrayList.add(dashBoardModel);
                        }
                        missedCallAdapter = new MissedCallAdapter(getContext(), dashBoardModelArrayList, AccessToken);
                        missedRecyclerView.setAdapter(missedCallAdapter);
                        missedCallAdapter.notifyDataSetChanged();
                        missedCallAdapter.setOnItemClickListener(new MissedCallAdapter.OnItemClickOnListner() {
                            @Override
                            public void onItemClick(View view, DashBoardModel dashBoardModel) {
                                TriggerCallWIthTelephoneManager.TriggerCall(String.valueOf(dashBoardModel.getCall_data().getPhone_number()), getContext());
                            }
                        });
                        progressBar_missed.setVisibility(View.GONE);
                        floating_missed.setImageBitmap(textAsBitmap(String.valueOf(dashBoardModelArrayList.size()), 40, Color.BLUE));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    progressBar_missed.setVisibility(View.GONE);
                    showAlertForStatus(String.valueOf(response.message()));
                }

            }


            @Override
            public void onFailure(Call<MissedCallLogModel> call, Throwable t) {
                progressBar_missed.setVisibility(View.GONE);
               // showAlertForStatus(String.valueOf(t.getMessage()));
               // showAlertForStatus("Some thing went wrong. Try again some time");
                Log.d("error_new",String.valueOf(t.getMessage()));

            }

        });
    }

    private void SessionLogout(Context context) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(ConstantUtills.AccessToken);
        editor.apply();
        startActivity(new Intent(context, LoginActivity.class));
        getActivity().finish();
    }

    private void intiViews(View view) {
        missedSwipeTorefresh = view.findViewById(R.id.missedSwipeTorefresh);
        missedRecyclerView = view.findViewById(R.id.missedRecyclerView);
        missedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar_missed = view.findViewById(R.id.progressBar_missed);
        floating_missed = view.findViewById(R.id.floating_missed);
        bottom_missed_progress = view.findViewById(R.id.bottom_missed_progress);
        missed_refresh_floating = view.findViewById(R.id.missed_refresh_floating);
        missedSwipeTorefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                missedSwipeTorefresh.setRefreshing(false);
            }
        });
        missed_refresh_floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (missedCallModelArrayList.size() > 0) {
                    missedCallModelArrayList.clear();
                }
                getMissedCalldata(1);
            }
        });
        //  paninatedRecyclerView(missedRecyclerView);
        floating_missed.setImageBitmap(textAsBitmap(String.valueOf(0), 40, Color.BLUE));
    }

    /* private void paninatedRecyclerView(RecyclerView recyclerView) {
         recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
             @Override
             public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                 super.onScrollStateChanged(recyclerView, newState);
             }

             @RequiresApi(api = Build.VERSION_CODES.KITKAT)
             @Override
             public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                 super.onScrolled(recyclerView, dx, dy);
                 LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                 if (dx == 0 && dy == 0) {

                 } else {
                     if (Objects.requireNonNull(linearLayoutManager).findLastCompletelyVisibleItemPosition() ==
                             missedCallAdapter.getItemCount() - 1) {
                         page_number = page_number + 1;
                         bottom_missed_progress.setVisibility(View.VISIBLE);
                         apiClient.getMissedCallsData(page_number, "Bearer " + AccessToken, "application/json").enqueue(new Callback<ModelForAllData>() {
                             @Override
                             public void onResponse(Call<ModelForAllData> call, Response<ModelForAllData> response) {
                                 try {
                                     if (response.body() == null) {
                                         int statusCode = response.raw().code();
                                         if (statusCode > 399 && statusCode < 500) {
                                             SessionLogout(getContext());
                                             progressBar_missed.setVisibility(View.GONE);
                                         }
                                     } else {
                                         ArrayList<DelaysModel> getCallLogs = response.body().getDashboard_data().getData();
                                         for (int i = 0; i < getCallLogs.size(); i++) {
                                             DelaysModel callLogs = new DelaysModel();
                                             callLogs.setCan_id(getCallLogs.get(i).getCan_id());
                                             callLogs.setId(getCallLogs.get(i).getId());
                                             callLogs.setPerson_id(getCallLogs.get(i).getPerson_id());
                                             callLogs.setPerson_name(getCallLogs.get(i).getPerson_name());
                                             callLogs.setPhone_number(getCallLogs.get(i).getPhone_number());
                                             callLogs.setDirectory(getCallLogs.get(i).getDirectory());
                                             callLogs.setCall_duration(getCallLogs.get(i).getCall_duration());
                                             callLogs.setContact_type(getCallLogs.get(i).getContact_type());
                                             callLogs.setTime(getCallLogs.get(i).getTime());
                                             callLogs.setCall_duration(getCallLogs.get(i).getCall_duration());
                                             callLogs.setCourses(getCallLogs.get(i).getCourses());
                                             callLogs.setLevel(getCallLogs.get(i).getLevel());
                                             callLogs.setDetails(getCallLogs.get(i).getEngagement_details());
                                             callLogs.setLast_call(getCallLogs.get(i).getLast_call());
                                             callLogs.setNext_call(getCallLogs.get(i).getNext_call());
                                             callLogs.setCreated_at(getCallLogs.get(i).getCreated_at());
                                             callLogs.setUpdated_at(getCallLogs.get(i).getUpdated_at());
                                             callLogs.setCompany(getCallLogs.get(i).getCompany());
                                             callLogs.setDesignation(getCallLogs.get(i).getDesignation());
                                             missedCallModelArrayList.add(callLogs);
                                         }
                                         missedCallAdapter.notifyDataSetChanged();
                                         bottom_missed_progress.setVisibility(View.GONE);
                                     }
                                 } catch (Exception e) {
                                     e.printStackTrace();
                                     bottom_missed_progress.setVisibility(View.GONE);
                                     showAlertForStatus("Some thing went wrong. Try again some time");
                                 }

                             }

                             @Override
                             public void onFailure(Call<ModelForAllData> call, Throwable t) {
                                 t.printStackTrace();
                                 bottom_missed_progress.setVisibility(View.GONE);
                                 showAlertForStatus(t.getMessage());
                             }
                         });


                         //load more items code is here
                     }
                 }

             }
         });
     }
 */
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

    private void showAlertForStatus(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(String.valueOf(s))
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                        alert.dismiss();
                    }
                });
        alert = builder.create();
        alert.show();
    }
}
