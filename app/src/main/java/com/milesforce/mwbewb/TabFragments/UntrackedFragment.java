package com.milesforce.mwbewb.TabFragments;


import android.app.AlertDialog;
import android.app.Dialog;
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
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.milesforce.mwbewb.Activities.LoginActivity;
import com.milesforce.mwbewb.Model.CallLogs;
import com.milesforce.mwbewb.Model.DashboardDatum;
import com.milesforce.mwbewb.Model.DelaysModel;
import com.milesforce.mwbewb.Model.UntrackedCallsModel;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;
import com.milesforce.mwbewb.Retrofit.SuccessModel;
import com.milesforce.mwbewb.Utils.AlertForAddB2BCRLeadForm;
import com.milesforce.mwbewb.Utils.AlertForAddB2CLeadForml;
import com.milesforce.mwbewb.Utils.BatteryModel;
import com.milesforce.mwbewb.Utils.ConstantUtills;
import com.milesforce.mwbewb.Utils.CustomEngagementFormForUntracked;
import com.milesforce.mwbewb.Utils.CustomSearchAdapter;
import com.milesforce.mwbewb.Utils.ResumeWork;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.milesforce.mwbewb.Utils.ConstantUtills.SaveToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.VERSION_NUMBER;
import static com.milesforce.mwbewb.Utils.ConstantUtills.fragment_token;

/**
 * A simple {@link Fragment} subclass.
 */
public class UntrackedFragment extends Fragment {
    RecyclerView untrackedRecyclerView;
    UntrackedAdapter untrackedAdapter;
    ApiClient apiClient;
    ArrayList<CallLogs> untrackedModelArrayList = new ArrayList<CallLogs>();
    ArrayList<DashboardDatum> DashboardDatumlArrayList;

    SwipeRefreshLayout untrackedSwipeToRefresh;
    String AccessToken;
    int page_number = 1;
    ProgressBar untracked_progress, bottom_untrackedProgress;
    FloatingActionButton floating_untracked, untracked_refresh_floating;
    TextView mobile_number_header;
    RadioButton b2c_check, b2b_ir_check, b2b_cr_check, personal_check, employee_check, spam_check, vendor_check;
    CardView b2c_card, b2b_ic_card, b2b_cr_card, releationship_card;
    LinearLayout submit_card;
    String LeadCourses = " ";
    String LeadLeveles = " ";
    String LeadName, LeadMobile, LeadEmail, LeadCity;
    String LeadEducation = " ";
    String LeadCompany = "";
    String LeadDesignation = " ";
    String LEadExperiance = " ";
    String LeadEngagement = " ";
    long nextFollowUpDate;
    String leadSource, leadSource_details;
    FrameLayout snake_bar_top;
    EditText relationship_name_untrack, relationship_untrack, milesEmployee_untracked, et_search_clientsInUntracked;
    LinearLayout personal_save_btn, milesEmployee_name_Card, addLead_saveUpdateuntracked_form, addLead_untracked_form;
    ProgressBar untracke_progress;
    TextView untrack_user_info_details;

    ArrayList<DelaysModel> delaysModelArrayList;
    AppCompatSpinner untrackedLeadsSpinner_m2b;
    CustomSearchAdapter customSearchAdapter;
    DelaysModel delaysModel;
    SharedPreferences sharedPreferences;
    AlertDialog alert;
    BatteryModel batteryModel;


    public UntrackedFragment() {
        // Required empty public constructor
    }


    public static UntrackedFragment newInstance() {
        UntrackedFragment fragment = new UntrackedFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        apiClient = ApiUtills.getAPIService();

        sharedPreferences = getContext().getSharedPreferences(SaveToken, MODE_PRIVATE);
        AccessToken = getArguments().getString(fragment_token);
        return inflater.inflate(R.layout.fragment_untracked, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        untrackedRecyclerView = view.findViewById(R.id.untrackedRecyclerView);


        getUntrackedData();
    }

    private void getUntrackedData() {
        untracked_progress.setVisibility(View.VISIBLE);
        DashboardDatumlArrayList = new ArrayList<>();
        apiClient.getUntrackedCallLogsMobileApp(1, "Bearer " + AccessToken, "").enqueue(new Callback<UntrackedCallsModel>() {
            @Override
            public void onResponse(Call<UntrackedCallsModel> call, Response<UntrackedCallsModel> response) {
                try {
                    if (response.raw().code() == 515) {
                        new ResumeWork().stResumeWork(getContext());
                        return;
                    }
                    if (response.body() == null) {
                        int statusCode = response.raw().code();
                        if (statusCode == 401) {
                            SessionLogout(getContext());
                            untracked_progress.setVisibility(View.GONE);
                        }
                    } else {
                        List<DashboardDatum> untrackedModelList = response.body().getDashboardData();
                        for (int i = 0; i < untrackedModelList.size(); i++) {
                            DashboardDatum dashboardDatum = new DashboardDatum();
                            dashboardDatum.setId(untrackedModelList.get(i).getId());
                            dashboardDatum.setPersonId(untrackedModelList.get(i).getPersonId());
                            dashboardDatum.setPersonName(untrackedModelList.get(i).getPersonName());
                            dashboardDatum.setPhoneNumber(untrackedModelList.get(i).getPhoneNumber());
                            dashboardDatum.setDirectory(untrackedModelList.get(i).getDirectory());
                            dashboardDatum.setCreatedAt(untrackedModelList.get(i).getCreatedAt());
                            dashboardDatum.setCallDuration(untrackedModelList.get(i).getCallDuration());
                            dashboardDatum.setTime(untrackedModelList.get(i).getTime());
                            DashboardDatumlArrayList.add(dashboardDatum);
                        }


                        untrackedAdapter = new UntrackedAdapter(getContext(), DashboardDatumlArrayList);
                        untrackedRecyclerView.setHasFixedSize(true);
                        untrackedRecyclerView.setAdapter(untrackedAdapter);
                        untrackedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        untrackedAdapter.notifyDataSetChanged();


                        floating_untracked.setImageBitmap(textAsBitmap(String.valueOf(DashboardDatumlArrayList.size()), 40, Color.BLUE));
                        untrackedAdapter.setOnItemClickListner(new UntrackedAdapter.OnClickListner() {
                            @Override
                            public void OnItemCLicked(View view, int position) {
                                DashboardDatum dashboardDatum = DashboardDatumlArrayList.get(position);
                                OpenUntrackedForm(dashboardDatum);

                            }
                        });
                        untrackedSwipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                            @Override
                            public void onRefresh() {
                                untrackedSwipeToRefresh.setRefreshing(false);
                            }
                        });
                        untracked_progress.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    untracked_progress.setVisibility(View.GONE);
//                    showAlertForStatus(String.valueOf(response.body().getMessage()));
                }

            }

            @Override
            public void onFailure(Call<UntrackedCallsModel> call, Throwable t) {
                Log.d("UNTRACKED onFailure", t.toString());
                untracked_progress.setVisibility(View.GONE);
                showAlertForStatus(String.valueOf(t.getMessage()));
                //  showAlertForStatus("Some thing went wrong .Please Try again after some time..!");
            }
        });


    }

//    private void getUntrackedData() {
//        untracked_progress.setVisibility(View.VISIBLE);
//        untrackedModelArrayList = new ArrayList<>();
//        apiClient.getUntrackedCallLogs(1, "Bearer " + AccessToken, "").enqueue(new Callback<UntrackedMainModel>() {
//            @Override
//            public void onResponse(Call<UntrackedMainModel> call, Response<UntrackedMainModel> response) {
//                try {
//                    if (response.raw().code() == 515) {
//                        new ResumeWork().stResumeWork(getContext());
//                        return;
//                    }
//                    if (response.body() == null) {
//                        int statusCode = response.raw().code();
//                        if (statusCode == 401) {
//                            SessionLogout(getContext());
//                            untracked_progress.setVisibility(View.GONE);
//                        }
//                    } else {
//                        List<CallLogs> untrackedModelList = response.body().getDashboard_data();
//                        for (int i = 0; i < untrackedModelList.size(); i++) {
//                            CallLogs callLogs = new CallLogs();
//                            callLogs.setId(untrackedModelList.get(i).getId());
//                            callLogs.setPerson_id(untrackedModelList.get(i).getPerson_id());
//                            callLogs.setPerson_name(untrackedModelList.get(i).getPerson_name());
//                            callLogs.setPhone_number(untrackedModelList.get(i).getPhone_number());
//                            callLogs.setDirectory(untrackedModelList.get(i).getDirectory());
//                            callLogs.setCreated_at(untrackedModelList.get(i).getCreated_at());
//                            callLogs.setCall_duration(untrackedModelList.get(i).getCall_duration());
//                            callLogs.setTime(untrackedModelList.get(i).getTime());
//                            untrackedModelArrayList.add(callLogs);
//                        }
//
//
//
//
//
//                        UntrackedAdapter untrackedAdapter = new UntrackedAdapter(getContext(), untrackedModelArrayList);
//                        untrackedRecyclerView.setHasFixedSize(true);
//                        untrackedRecyclerView.setAdapter(untrackedAdapter);
//                        untrackedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//                        untrackedAdapter.notifyDataSetChanged();
//
//
//
//                        floating_untracked.setImageBitmap(textAsBitmap(String.valueOf(untrackedModelArrayList.size()), 40, Color.BLUE));
//                        untrackedAdapter.setOnItemClickListner(new UntrackedAdapter.OnClickListner() {
//                            @Override
//                            public void OnItemCLicked(View view, int position) {
//                                CallLogs callLogs = untrackedModelArrayList.get(position);
//                                OpenUntrackedForm(callLogs);
//                            }
//                        });
//                        untrackedSwipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                            @Override
//                            public void onRefresh() {
//                                untrackedSwipeToRefresh.setRefreshing(false);
//                            }
//                        });
//                        untracked_progress.setVisibility(View.GONE);
//                    }
//
//                } catch (Exception e) {
//                    untracked_progress.setVisibility(View.GONE);
//                    showAlertForStatus(String.valueOf(response.body().getMessage()));
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<UntrackedMainModel> call, Throwable t) {
//                Log.d("UNTRACKED onFailure",t.toString());
//                untracked_progress.setVisibility(View.GONE);
//                 showAlertForStatus(String.valueOf(t.getMessage()));
//                //  showAlertForStatus("Some thing went wrong .Please Try again after some time..!");
//            }
//        });
//
//
//    }

    private void SessionLogout(Context context) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(ConstantUtills.AccessToken);
        editor.apply();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }


    private void initViews(View view) {
        untrackedSwipeToRefresh = view.findViewById(R.id.untrackedSwipeToRefresh);
        untrackedRecyclerView = view.findViewById(R.id.untrackedRecyclerView);
        untracked_progress = view.findViewById(R.id.untracked_progress);
        bottom_untrackedProgress = view.findViewById(R.id.bottom_untrackedProgress);
        // getPaginatedRecyclerView(untrackedRecyclerView);
        floating_untracked = view.findViewById(R.id.floating_untracked);
        untracked_refresh_floating = view.findViewById(R.id.untracked_refresh_floating);
        floating_untracked.setImageBitmap(textAsBitmap(String.valueOf(0), 40, Color.BLUE));
        snake_bar_top = view.findViewById(R.id.snake_bar_top);
        untracked_refresh_floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (untrackedModelArrayList.size() > 0) {
                    untrackedModelArrayList.clear();
                }
                getUntrackedData();
            }
        });
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
                            untrackedAdapter.getItemCount() - 1) {
                        page_number = page_number + 1;
                        bottom_untrackedProgress.setVisibility(View.VISIBLE);
                        apiClient.getUntrackedCallLogs(page_number, "Bearer " + AccessToken, "application/json").enqueue(new Callback<UntrackedMainModel>() {
                            @Override
                            public void onResponse(Call<UntrackedMainModel> call, Response<UntrackedMainModel> response) {
                                try {
                                    if (response.body() == null) {
                                        int statusCode = response.raw().code();
                                        if (statusCode > 399 && statusCode < 500) {
                                            SessionLogout(getContext());
                                            untracked_progress.setVisibility(View.GONE);
                                        }
                                    } else {
                                        List<CallLogs> untrackedModelList = response.body().getDashboard_data().getData();
                                        for (int i = 0; i < untrackedModelList.size(); i++) {
                                            CallLogs callLogs = new CallLogs();
                                            callLogs.setId(untrackedModelList.get(i).getId());
                                            callLogs.setPerson_id(untrackedModelList.get(i).getPerson_id());
                                            callLogs.setPerson_name(untrackedModelList.get(i).getPerson_name());
                                            callLogs.setPhone_number(untrackedModelList.get(i).getPhone_number());
                                            callLogs.setDirectory(untrackedModelList.get(i).getDirectory());
                                            callLogs.setCreated_at(untrackedModelList.get(i).getCreated_at());
                                            callLogs.setCall_duration(untrackedModelList.get(i).getCall_duration());
                                            callLogs.setTime(untrackedModelList.get(i).getTime());
                                            untrackedModelArrayList.add(callLogs);
                                        }
                                        untrackedAdapter.notifyDataSetChanged();
                                        bottom_untrackedProgress.setVisibility(View.GONE);

                                    }
                                } catch (Exception e) {
                                    bottom_untrackedProgress.setVisibility(View.GONE);
                                    Toast.makeText(getContext(), String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<UntrackedMainModel> call, Throwable t) {
                                bottom_untrackedProgress.setVisibility(View.GONE);
                                Toast.makeText(getContext(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                            }
                        });


                        //load more items code is here
                    }
                }

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

    private void OpenUntrackedForm(final DashboardDatum callLogs) {
        final Dialog untracked_dialog = new Dialog(getContext());
        untracked_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        untracked_dialog.setContentView(R.layout.untrackednumberlayout);
        untracked_dialog.setCancelable(true);
        mobile_number_header = untracked_dialog.findViewById(R.id.mobile_number_header);
        mobile_number_header.setText(callLogs.getPhoneNumber());
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(untracked_dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        b2c_check = untracked_dialog.findViewById(R.id.b2c_check);
        b2b_ir_check = untracked_dialog.findViewById(R.id.b2b_ir_check);
        b2b_cr_check = untracked_dialog.findViewById(R.id.b2b_cr_check);
        personal_check = untracked_dialog.findViewById(R.id.personal_check);
        employee_check = untracked_dialog.findViewById(R.id.employee_check);
        spam_check = untracked_dialog.findViewById(R.id.spam_check);
        vendor_check = untracked_dialog.findViewById(R.id.vendor_check);
        b2c_card = untracked_dialog.findViewById(R.id.b2c_card);
        b2b_ic_card = untracked_dialog.findViewById(R.id.b2b_ic_card);
        b2b_cr_card = untracked_dialog.findViewById(R.id.b2b_cr_card);
        releationship_card = untracked_dialog.findViewById(R.id.releationship_card);
        submit_card = untracked_dialog.findViewById(R.id.submit_card);
        relationship_name_untrack = untracked_dialog.findViewById(R.id.relationship_name_untrack);
        relationship_untrack = untracked_dialog.findViewById(R.id.relationship_untrack);
        personal_save_btn = untracked_dialog.findViewById(R.id.personal_save_btn);
        untracke_progress = untracked_dialog.findViewById(R.id.untracke_progress);
        milesEmployee_name_Card = untracked_dialog.findViewById(R.id.milesEmployee_name_Card);
        milesEmployee_untracked = untracked_dialog.findViewById(R.id.milesEmployee_untracked);
        et_search_clientsInUntracked = untracked_dialog.findViewById(R.id.et_search_clientsInUntracked);
        untrackedLeadsSpinner_m2b = untracked_dialog.findViewById(R.id.untrackedLeadsSpinner_m2b);
        untrack_user_info_details = untracked_dialog.findViewById(R.id.untrack_user_info_details);
        addLead_saveUpdateuntracked_form = untracked_dialog.findViewById(R.id.addLead_saveUpdateuntracked_form);
        addLead_untracked_form = untracked_dialog.findViewById(R.id.addLead_untracked_form);


        b2c_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    b2b_ir_check.setChecked(false);
                    b2b_cr_check.setChecked(false);
                    personal_check.setChecked(false);
                    employee_check.setChecked(false);
                    vendor_check.setChecked(false);
                    spam_check.setChecked(false);
                    b2c_card.setVisibility(View.VISIBLE);
                    b2b_ic_card.setVisibility(View.GONE);
                    b2b_cr_card.setVisibility(View.GONE);
                    releationship_card.setVisibility(View.GONE);
                    submit_card.setVisibility(View.GONE);
                    milesEmployee_name_Card.setVisibility(View.GONE);
                    AddB2bUntrackedLead(untracked_dialog, callLogs, "B2C");
                }
            }
        });
        b2b_ir_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    b2c_check.setChecked(false);
                    b2b_cr_check.setChecked(false);
                    personal_check.setChecked(false);
                    employee_check.setChecked(false);
                    spam_check.setChecked(false);
                    vendor_check.setChecked(false);
                    b2c_card.setVisibility(View.VISIBLE);
                    b2b_ic_card.setVisibility(View.GONE);
                    b2b_cr_card.setVisibility(View.GONE);
                    releationship_card.setVisibility(View.GONE);
                    submit_card.setVisibility(View.GONE);
                    milesEmployee_name_Card.setVisibility(View.GONE);
                    AddB2bUntrackedLead(untracked_dialog, callLogs, "B2BIR");
                }
            }
        });
        b2b_cr_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    b2c_check.setChecked(false);
                    b2b_ir_check.setChecked(false);
                    personal_check.setChecked(false);
                    employee_check.setChecked(false);
                    spam_check.setChecked(false);
                    vendor_check.setChecked(false);
                    b2c_card.setVisibility(View.VISIBLE);
                    b2b_ic_card.setVisibility(View.GONE);
                    b2b_cr_card.setVisibility(View.GONE);
                    releationship_card.setVisibility(View.GONE);
                    submit_card.setVisibility(View.GONE);
                    milesEmployee_name_Card.setVisibility(View.GONE);
                    AddB2bUntrackedLead(untracked_dialog, callLogs, "B2BCR");
                }
            }
        });
        personal_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    b2c_check.setChecked(false);
                    b2b_ir_check.setChecked(false);
                    b2b_cr_check.setChecked(false);
                    employee_check.setChecked(false);
                    spam_check.setChecked(false);
                    vendor_check.setChecked(false);
                    b2c_card.setVisibility(View.GONE);
                    b2b_ic_card.setVisibility(View.GONE);
                    b2b_cr_card.setVisibility(View.GONE);
                    releationship_card.setVisibility(View.VISIBLE);
                    submit_card.setVisibility(View.GONE);
                    ContactAsPersonal(callLogs.getPhoneNumber(), untracked_dialog);
                    milesEmployee_name_Card.setVisibility(View.GONE);
                }
            }
        });
        employee_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    b2c_check.setChecked(false);
                    b2b_ir_check.setChecked(false);
                    b2b_cr_check.setChecked(false);
                    personal_check.setChecked(false);
                    spam_check.setChecked(false);
                    vendor_check.setChecked(false);
                    b2c_card.setVisibility(View.GONE);
                    b2b_ic_card.setVisibility(View.GONE);
                    b2b_cr_card.setVisibility(View.GONE);
                    releationship_card.setVisibility(View.GONE);
                    submit_card.setVisibility(View.VISIBLE);
                    milesEmployee_name_Card.setVisibility(View.VISIBLE);
                    SaveContactNumberAsMilesEmployee(untracked_dialog, callLogs.getPhoneNumber());
                }
            }
        });
        spam_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    b2c_check.setChecked(false);
                    b2b_ir_check.setChecked(false);
                    b2b_cr_check.setChecked(false);
                    personal_check.setChecked(false);
                    employee_check.setChecked(false);
                    vendor_check.setChecked(false);
                    b2c_card.setVisibility(View.GONE);
                    b2b_ic_card.setVisibility(View.GONE);
                    b2b_cr_card.setVisibility(View.GONE);
                    releationship_card.setVisibility(View.GONE);
                    submit_card.setVisibility(View.VISIBLE);
                    milesEmployee_name_Card.setVisibility(View.VISIBLE);
                    milesEmployee_untracked.setHint("Info");
                    saveNumberAsSpam(untracked_dialog, callLogs.getPhoneNumber());
                }
            }
        });
        vendor_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    b2c_check.setChecked(false);
                    b2b_ir_check.setChecked(false);
                    b2b_cr_check.setChecked(false);
                    personal_check.setChecked(false);
                    spam_check.setChecked(false);
                    b2c_card.setVisibility(View.GONE);
                    b2b_ic_card.setVisibility(View.GONE);
                    b2b_cr_card.setVisibility(View.GONE);
                    releationship_card.setVisibility(View.GONE);
                    submit_card.setVisibility(View.VISIBLE);
                    milesEmployee_untracked.setHint("Vendor Name");
                    milesEmployee_name_Card.setVisibility(View.VISIBLE);
                    saveAsVendor(untracked_dialog, callLogs.getPhoneNumber());
                    // SaveContactNumberAsMilesEmployee(untracked_dialog, callLogs.getPhone_number());
                }
            }
        });


        ((ImageButton) untracked_dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                untracked_dialog.dismiss();
            }
        });

        untracked_dialog.show();
        untracked_dialog.setCanceledOnTouchOutside(false);
        untracked_dialog.getWindow().setAttributes(lp);
    }

    private void saveAsVendor(final Dialog untracked_dialog, final String phone_number) {


        submit_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                untracke_progress.setVisibility(View.VISIBLE);
                apiClient.AddUntrackedAsVendor(milesEmployee_untracked.getText().toString().trim(), phone_number, batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), VERSION_NUMBER, "Bearer " + AccessToken, "application/json").enqueue(new Callback<SuccessModel>() {
                    @Override
                    public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                        try {
                            if (response.body().getStatus().equals("success")) {
                                untracked_dialog.dismiss();
                                ShowSnakeBar(response.body().getMessage());
                                untracke_progress.setVisibility(View.GONE);
                                if (untrackedModelArrayList != null) {
                                    untrackedModelArrayList.clear();
                                }
                                getUntrackedData();
                            } else {
                                ShowSnakeBar(response.body().getMessage());
                                untracke_progress.setVisibility(View.GONE);
                            }

                        } catch (Exception e) {
                            untracked_dialog.dismiss();
                            ShowSnakeBar(e.getMessage());
                            untracke_progress.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onFailure(Call<SuccessModel> call, Throwable t) {
                        untracked_dialog.dismiss();
                        ShowSnakeBar(t.getMessage());
                        untracke_progress.setVisibility(View.GONE);
                    }
                });
            }
        });


    }

    private void AddB2bUntrackedLead(final Dialog untracked_dialog, final DashboardDatum callLogs, final String B2C) {
        et_search_clientsInUntracked.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //  hideKeyboard();
                    if (delaysModelArrayList != null) {
                        delaysModelArrayList.clear();
                    }
                    getSearchedUSerData(AccessToken, v.getText().toString(), B2C, untracked_dialog, callLogs);
                    return true;
                }
                return false;
            }
        });

        addLead_saveUpdateuntracked_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                untracked_dialog.dismiss();
                if (B2C.equals("B2C")) {
                    new AlertForAddB2CLeadForml().AddB2cLeadForm(getContext(), AccessToken, callLogs.getPhoneNumber(), "", "", "", "", 0, "");
                }
                if (B2C.equals("B2BCR")) {
                    new AlertForAddB2BCRLeadForm().addb2bcrLeadForm(getContext(), AccessToken, callLogs.getPhoneNumber());
                }
                if (B2C.equals("B2BIR")) {
                    new AlertForAddB2BIRLeadForm().addb2bIRLeadForm(getContext(), AccessToken, callLogs.getPhoneNumber());
                }


            }
        });


    }

    private void getSearchedUSerData(String accessToken, String person_name, final String B2C, final Dialog dialog, final DashboardDatum callLogs) {
        untracke_progress.setVisibility(View.VISIBLE);
        delaysModelArrayList = new ArrayList<>();
        untrack_user_info_details.setText("");

        apiClient.getSearchedLeads(person_name, B2C, "Bearer " + accessToken, "application/json").enqueue(new Callback<List<DelaysModel>>() {
            @Override
            public void onResponse(Call<List<DelaysModel>> call, Response<List<DelaysModel>> response) {
                try {
                    List<DelaysModel> getdelaysModel = response.body();
                    for (int i = 0; i < getdelaysModel.size(); i++) {
                        DelaysModel delaysModel = new DelaysModel();
                        delaysModel.setCan_id(getdelaysModel.get(i).getCan_id());
                        delaysModel.setPerson_id(getdelaysModel.get(i).getPerson_id());
                        delaysModel.setPerson_name(getdelaysModel.get(i).getPerson_name());
                        delaysModel.setCourses(getdelaysModel.get(i).getCourses());
                        delaysModel.setLevel(getdelaysModel.get(i).getLevel());
                        delaysModel.setEngagement_details(getdelaysModel.get(i).getEngagement_details());
                        delaysModel.setCompany(getdelaysModel.get(i).getCompany());
                        delaysModel.setDesignation(getdelaysModel.get(i).getDesignation());
                        delaysModel.setMiles_type(getdelaysModel.get(i).getMiles_type());
                        delaysModel.setIdentity(getdelaysModel.get(i).getIdentity());
                        delaysModelArrayList.add(delaysModel);
                    }
                    customSearchAdapter = new CustomSearchAdapter(getContext(), delaysModelArrayList);

                    untrackedLeadsSpinner_m2b.setAdapter(customSearchAdapter);

                    customSearchAdapter.notifyDataSetChanged();


                    if (delaysModelArrayList.size() == 0) {
                        untrack_user_info_details.setText("Sorry did not find the person look for. Please add a person..!");
                    }
                    untracke_progress.setVisibility(View.GONE);

                } catch (Exception e) {
                    Toast.makeText(getContext(), String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
                    untracke_progress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<DelaysModel>> call, Throwable t) {
                Toast.makeText(getContext(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                untracke_progress.setVisibility(View.GONE);

            }
        });

        untrackedLeadsSpinner_m2b.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DelaysModel delaysModel = delaysModelArrayList.get(position);
                untrack_user_info_details.setText(delaysModel.getEngagement_details());
                AddLeadWithPersonDetails(delaysModel, dialog, callLogs, B2C);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void AddLeadWithPersonDetails(final DelaysModel delaysModel, final Dialog dialog, final DashboardDatum callLogs, final String B2c) {
        addLead_untracked_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                untracke_progress.setVisibility(View.VISIBLE);
                apiClient.AddUntrackedToExistingPerson(delaysModel.getPerson_id(), delaysModel.getPerson_name(), delaysModel.getCan_id(), delaysModel.getMiles_type(), callLogs.getPhoneNumber(), batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), VERSION_NUMBER, "Bearer " + AccessToken, "application/json").enqueue(new Callback<SuccessModel>() {
                    @Override
                    public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                        try {
                            if (response.body().getStatus().equals("success")) {
                                new CustomEngagementFormForUntracked().addCustomEngagement(delaysModel, getContext(), AccessToken);
                                dialog.dismiss();
                                if (untrackedModelArrayList != null) {
                                    untrackedModelArrayList.clear();
                                }
                                getUntrackedData();

                            } else {
                                ShowSnakeBar(response.body().getMessage());
                                untrack_user_info_details.setText(response.body().getMessage());
                            }
                            untracke_progress.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.getMessage();
                            ShowSnakeBar(String.valueOf(e.getMessage()));
                            untrack_user_info_details.setText(String.valueOf(e.getMessage()));
                        }

                    }

                    @Override
                    public void onFailure(Call<SuccessModel> call, Throwable t) {
                        untracke_progress.setVisibility(View.GONE);
                        t.getMessage();
                        ShowSnakeBar(String.valueOf(t.getMessage()));
                        untrack_user_info_details.setText(String.valueOf(t.getMessage()));
                    }
                });
            }
        });
    }

    private void SaveContactNumberAsMilesEmployee(final Dialog untracked_dialog,
                                                  final String phone_number) {
        submit_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (milesEmployee_untracked.getText().toString().isEmpty()) {
                    milesEmployee_untracked.setError("Please Enter Name");
                } else {
                    untracke_progress.setVisibility(View.VISIBLE);
                    apiClient.AddUntrackedAsMilesemployee(milesEmployee_untracked.getText().toString().trim(), phone_number, batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), VERSION_NUMBER, "Bearer " + AccessToken, "application/json").enqueue(new Callback<SuccessModel>() {
                        @Override
                        public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                            try {
                                if (response.body().getStatus().equals("success")) {
                                    untracked_dialog.dismiss();
                                    ShowSnakeBar(response.body().getMessage());
                                    untracke_progress.setVisibility(View.GONE);
                                    if (untrackedModelArrayList != null) {
                                        untrackedModelArrayList.clear();
                                    }
                                    getUntrackedData();
                                } else {
                                    untracked_dialog.dismiss();
                                    ShowSnakeBar(response.body().getMessage());
                                    untracke_progress.setVisibility(View.GONE);
                                }
                            } catch (Exception e) {
                                untracked_dialog.dismiss();
                                ShowSnakeBar(e.getMessage());
                                untracke_progress.setVisibility(View.GONE);
                            }

                        }

                        @Override
                        public void onFailure(Call<SuccessModel> call, Throwable t) {
                            untracked_dialog.dismiss();
                            ShowSnakeBar(t.getMessage());
                            untracke_progress.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });

    }


    private void saveNumberAsSpam(final Dialog untracked_dialog, final String phone_number) {
        submit_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                untracke_progress.setVisibility(View.VISIBLE);
                apiClient.AddUntrackedAsSpam(phone_number, milesEmployee_untracked.getText().toString().trim(), batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), VERSION_NUMBER, "Bearer " + AccessToken, "application/json").enqueue(new Callback<SuccessModel>() {
                    @Override
                    public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                        try {
                            if (response.body().getStatus().equals("success")) {
                                untracked_dialog.dismiss();
                                ShowSnakeBar(response.body().getMessage());
                                untracke_progress.setVisibility(View.GONE);
                                if (untrackedModelArrayList != null) {
                                    untrackedModelArrayList.clear();
                                }
                                getUntrackedData();
                            } else {
                                ShowSnakeBar(response.body().getMessage());
                                untracke_progress.setVisibility(View.GONE);
                            }

                        } catch (Exception e) {
                            untracked_dialog.dismiss();
                            ShowSnakeBar(e.getMessage());
                            untracke_progress.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onFailure(Call<SuccessModel> call, Throwable t) {
                        untracked_dialog.dismiss();
                        ShowSnakeBar(t.getMessage());
                        untracke_progress.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private void ContactAsPersonal(final String phone_number, final Dialog dialog) {

        personal_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (relationship_name_untrack.getText().toString().isEmpty()) {
                    relationship_name_untrack.setError("Enter name");
                }
                if (relationship_untrack.getText().toString().isEmpty()) {
                    relationship_untrack.setError("Enter Relationship");
                } else {
                    untracke_progress.setVisibility(View.VISIBLE);
                    apiClient.AddUntrackedAsPersonal(relationship_name_untrack.getText().toString(), relationship_untrack.getText().toString().trim(), phone_number, batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), VERSION_NUMBER, "Bearer " + AccessToken, "application/json").enqueue(new Callback<SuccessModel>() {
                        @Override
                        public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                            try {
                                if (response.body().getStatus().equals("success")) {
                                    dialog.dismiss();
                                    ShowSnakeBar(response.body().getMessage());
                                    untracke_progress.setVisibility(View.GONE);
                                    if (untrackedModelArrayList != null) {
                                        untrackedModelArrayList.clear();
                                    }
                                    getUntrackedData();
                                } else {
                                    ShowSnakeBar(response.body().getMessage());
                                    untracke_progress.setVisibility(View.GONE);
                                }

                            } catch (Exception e) {
                                dialog.dismiss();
                                ShowSnakeBar(e.getMessage());
                                untracke_progress.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<SuccessModel> call, Throwable t) {
                            dialog.dismiss();
                            ShowSnakeBar(t.getMessage());
                            untracke_progress.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }


    public void ShowSnakeBar(String s) {
        Snackbar snackbar = Snackbar
                .make(snake_bar_top, String.valueOf(s), 5000);
        snackbar.show();
    }

    /*private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getContext(). getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
*/
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
