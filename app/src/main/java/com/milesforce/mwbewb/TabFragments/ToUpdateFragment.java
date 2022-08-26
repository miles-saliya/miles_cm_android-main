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
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.milesforce.mwbewb.Activities.AddEngagementActivity;
import com.milesforce.mwbewb.Activities.EWBSRActivity;
import com.milesforce.mwbewb.Activities.LoginActivity;
import com.milesforce.mwbewb.Activities.MainActivity;
import com.milesforce.mwbewb.Model.CallDataLogModel;
import com.milesforce.mwbewb.Model.CallLogDataModel;
import com.milesforce.mwbewb.Model.CallLogs;
import com.milesforce.mwbewb.Model.DelaysModel;
import com.milesforce.mwbewb.Model.SrModelInfo;
import com.milesforce.mwbewb.Model.WorkLogModel;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;
import com.milesforce.mwbewb.Retrofit.SuccessModel;
import com.milesforce.mwbewb.Utils.AlertForMobileNumberInfo;
import com.milesforce.mwbewb.Utils.CallRecord;
import com.milesforce.mwbewb.Utils.ResumeWork;
import com.milesforce.mwbewb.Utils.Session;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.milesforce.mwbewb.Utils.ConstantUtills.AccessToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.SaveToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.fragment_token;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToUpdateFragment extends Fragment {

    private BottomSheetBehavior mBehavior;
    private BottomSheetDialog mBottomSheetDialog;
    private View bottom_sheet;
    RecyclerView recyclerView;
    ToUpdateAdapter toUpdateAdapter;
    ArrayList<SrModelInfo> srModelInfos;
    SwipeRefreshLayout toUpdateSwipeTorefresh;
    String AccessToken_new;
    ApiClient apiClient;
    ArrayList<DelaysModel> callLogArrayList;
    int page_number = 1;
    ProgressBar progressbar, bottom_toupdateProgress;
    FloatingActionButton floating_toupdate, toUpdate_refresh_floating;
    CallRecord callRecord;
    SharedPreferences sharedPreferences;
    AlertDialog alert;
    LinearLayoutManager linearLayoutManager;


    public ToUpdateFragment() {
        // Required empty public constructor
    }

    public static ToUpdateFragment newInstance() {
        ToUpdateFragment fragment = new ToUpdateFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sharedPreferences = getContext().getSharedPreferences(SaveToken, MODE_PRIVATE);
        // accessToken = sharedPreferences.getString(AccessToken, null);
        AccessToken_new = getArguments().getString(fragment_token);
        apiClient = ApiUtills.getAPIService();
        return inflater.inflate(R.layout.fragment_to_update, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intiviews(view);
        getToUpdateData(AccessToken_new);


    }

    private void getToUpdateData(String accessToken) {
        progressbar.setVisibility(View.VISIBLE);
        callLogArrayList = new ArrayList<>();
        apiClient.getToUpdateData(1, "Bearer " + accessToken, "application/json").enqueue(new Callback<CallLogDataModel>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(final Call<CallLogDataModel> call, Response<CallLogDataModel> response) {
                try {
                    if(response.raw().code() == 515){
                        new ResumeWork().stResumeWork(getContext());
                        return;
                    }
                    if (response.body() == null) {
                        int statusCode = response.raw().code();
                        if (statusCode == 401) {
                            SessionLogout(getContext());
                            progressbar.setVisibility(View.GONE);
                        }
                        progressbar.setVisibility(View.GONE);
                    } else {
                        ArrayList<DelaysModel> getCallLogs = response.body().getDashboard_data();
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
                            callLogs.setIiml_level(getCallLogs.get(i).getIiml_level());
                            callLogs.setExperience(getCallLogs.get(i).getExperience());
                            callLogs.setSubmitted_documents(getCallLogs.get(i).getSubmitted_documents());
                            callLogs.setApplied_for_loan(getCallLogs.get(i).getApplied_for_loan());
                            callLogs.setLoan_status(getCallLogs.get(i).getLoan_status());
                            callLogs.setEducation_tags(getCallLogs.get(i).getEducation_tags());
                            if (getCallLogs.get(i).getDesignation()!=null){
                                callLogs.setDesignation(getCallLogs.get(i).getDesignation());
                            }else {
                                callLogs.setDesignation("");
                            }

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
                        toUpdateAdapter = new ToUpdateAdapter(getContext(), callLogArrayList);
                        recyclerView.setAdapter(toUpdateAdapter);
                        toUpdateAdapter.notifyDataSetChanged();
                        floating_toupdate.setImageBitmap(textAsBitmap(String.valueOf(callLogArrayList.size()), 40, Color.BLUE));
                        toUpdateAdapter.setOnItemClickListener(new ToUpdateAdapter.OnItemClickListener() {
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
                        progressbar.setVisibility(View.GONE);
                        toUpdateSwipeTorefresh.setRefreshing(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    progressbar.setVisibility(View.GONE);
                    toUpdateSwipeTorefresh.setRefreshing(false);
                    showAlertForStatus("Some Thing went wrong. please try after some time");
                }

            }

            @Override
            public void onFailure(Call<CallLogDataModel> call, Throwable t) {
                t.printStackTrace();
                progressbar.setVisibility(View.GONE);
                showAlertForStatus("Some Thing went wrong. please try after some time");
                toUpdateSwipeTorefresh.setRefreshing(false);
            }
        });
    }

    private void SessionLogout(Context context) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(AccessToken);
        editor.apply();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }


    private void intiviews(View view) {
        recyclerView = view.findViewById(R.id.toUpdateRecyclerView);
        toUpdateSwipeTorefresh = view.findViewById(R.id.toUpdateSwipeTorefresh);
        toUpdate_refresh_floating = view.findViewById(R.id.toUpdate_refresh_floating);
        bottom_sheet = view.findViewById(R.id.bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottom_sheet);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        progressbar = view.findViewById(R.id.progressbar);
        bottom_toupdateProgress = view.findViewById(R.id.bottom_toupdateProgress);
        floating_toupdate = view.findViewById(R.id.floating_toupdate);
        floating_toupdate.setImageBitmap(textAsBitmap(String.valueOf(0), 40, Color.BLUE));
        toUpdate_refresh_floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callLogArrayList.size() > 0) {
                    callLogArrayList.clear();
                }
                getToUpdateData(AccessToken_new);
            }
        });

        toUpdateSwipeTorefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                int page_number = 1;
                //  getToUpdateData(AccessToken);
                toUpdateSwipeTorefresh.setRefreshing(false);
            }
        });
        //getPaginatedRecyclerView(recyclerView);

        /*Call Record Service */
        /* callRecord = new CallRecord.Builder(getContext())
         *//* .setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION)
                 .setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
                 .setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)*//* // optional & default value
                // optional & default value
                // optional & default value
                // optional & default value ->Ex: RecordFileName_incoming.amr || RecordFileName_outgoing.amr
                .build();
        callRecord.startCallReceiver();*/


    }

   /* private void getPaginatedRecyclerView(RecyclerView recyclerView) {
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
                            toUpdateAdapter.getItemCount() - 1) {
                        page_number = page_number + 1;
                        bottom_toupdateProgress.setVisibility(View.VISIBLE);
                        apiClient.getToUpdateData(page_number, "Bearer " + AccessToken_new, "application/json").enqueue(new Callback<ModelForAllData>() {
                            @Override
                            public void onResponse(Call<ModelForAllData> call, Response<ModelForAllData> response) {
                                try {
                                    if (response.body() == null) {
                                        int statusCode = response.raw().code();
                                        if (statusCode > 399 && statusCode < 500) {
                                            SessionLogout(getContext());
                                            progressbar.setVisibility(View.GONE);
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
                                            callLogArrayList.add(callLogs);
                                        }
                                        toUpdateAdapter.notifyDataSetChanged();
                                        bottom_toupdateProgress.setVisibility(View.GONE);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    bottom_toupdateProgress.setVisibility(View.GONE);
                                    showAlertForStatus("Some Thing went wrong. please try after some time");
                                }

                            }

                            @Override
                            public void onFailure(Call<ModelForAllData> call, Throwable t) {
                                t.printStackTrace();
                                bottom_toupdateProgress.setVisibility(View.GONE);
                                showAlertForStatus("Some Thing went wrong. please try after some time");
                            }
                        });


                        //load more items code is here
                    }
                }

            }
        });
    }*/

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

 /*   private void showBottomSheetDialog() {
        if (mBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        final View view = getLayoutInflater().inflate(R.layout.sheet_list, null);

        ((View) view.findViewById(R.id.lyt_preview)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        ((View) view.findViewById(R.id.lyt_share)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        ((View) view.findViewById(R.id.lyt_get_link)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        ((View) view.findViewById(R.id.lyt_make_copy)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        mBottomSheetDialog = new BottomSheetDialog(getContext());
        mBottomSheetDialog.setContentView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });
    }*/

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
