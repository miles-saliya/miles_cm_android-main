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
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.milesforce.mwbewb.Activities.LoginActivity;
import com.milesforce.mwbewb.EngagementFragments.LevelsCustomAdapter;
import com.milesforce.mwbewb.Model.CandidatePersonaDetailsModel;
import com.milesforce.mwbewb.Model.DelaysModel;
import com.milesforce.mwbewb.Model.HistoryModel;
import com.milesforce.mwbewb.Model.LevelsModel;
import com.milesforce.mwbewb.Model.NetEnquiry;
import com.milesforce.mwbewb.Model.NetEnquiryData;
import com.milesforce.mwbewb.Model.UpdateCandidatePersonaDetailsModel;
import com.milesforce.mwbewb.Model.UserToken;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;
import com.milesforce.mwbewb.Retrofit.CommanApiClient;
import com.milesforce.mwbewb.Retrofit.CommanApiUtills;
import com.milesforce.mwbewb.Retrofit.SuccessModel;
import com.milesforce.mwbewb.Utils.AlertForAddB2CLeadForml;
import com.milesforce.mwbewb.Utils.BatterPercentage;
import com.milesforce.mwbewb.Utils.BatteryModel;
import com.milesforce.mwbewb.Utils.ConstantUtills;
import com.milesforce.mwbewb.Utils.CustomEngagementFormForUntracked;
import com.milesforce.mwbewb.Utils.CustomSearchAdapter;
import com.milesforce.mwbewb.Utils.NetEnquiryAdapter;
import com.milesforce.mwbewb.Utils.ResumeWork;
import com.milesforce.mwbewb.Utils.TriggerCallWIthTelephoneManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.milesforce.mwbewb.TabFragments.MissedCallAdapter.accessToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.IIML_TAB_CHANGE_CODE;
import static com.milesforce.mwbewb.Utils.ConstantUtills.NET_ENQUIRY_URL;
import static com.milesforce.mwbewb.Utils.ConstantUtills.VERSION_NUMBER;

/**
 * A simple {@link Fragment} subclass.
 */
public class NetEnquireFragment extends Fragment {

    SharedPreferences sharedPreferences;
    ApiClient apiClient;
    String AccessToken;
    RecyclerView net_enquiry_recyclerView;
    ProgressBar net_enquiry_progress, bottom_net_enquiry_Progress;
    FloatingActionButton flating_btn, net_enquiry_refresh_floating;
    NetEnquiryAdapter netEnquiryAdapter;
    ArrayList<NetEnquiryData> netEnquiryArrayList;
    ArrayList<NetEnquiryData> sortedNewEnquiryDataArrayList;
    int page = 1;
    SwipeRefreshLayout net_enquiry_SwipetoRefresh;
    Realm realm;

    UserToken userToken;
    String Access_token;
    RadioButton b2c_check, add_a_lead, irrelevant_check, netenquiery;
    CardView b2c_card, releationship_card;
    EditText relationship_name_untrack, et_search_clientsInUntracked;
    LinearLayout personal_save_btn, addLead_saveUpdateuntracked_form, addLead_untracked_form;
    ProgressBar untracke_progress;
    AppCompatSpinner untrackedLeadsSpinner_m2b;
    TextView untrack_user_info_details;
    FrameLayout snake_bar_top;
    ArrayList<DelaysModel> delaysModelArrayList;
    CustomSearchAdapter customSearchAdapter;
    String MWbLeadCourseData = "IIML-BA";

    AppCompatCheckBox b2c_cpa_check, b2c_cma_check,
            b2c_iiml_fa_check,
            b2c_iiml_ba_check, b2c_iiml_pa_check, b2c_iiml_hr_check, b2c_iitr_bf_check,
            b2c_iitr_dbe_check, b2c_iimlfa_check, b2c_iimlsf_check, b2c_CFA_check, b2c_FRM_check, b2c_USP_check;
    Calendar myCalendar, LeadsCalendar;

    EditText b2c_lead_name, b2c_lead_mobile, b2c_lead_email, b2c_lead_education, b2c_lead_company, b2c_lead_designation, b2c_lead_experiance, b2c_lead_engagement, b2c_lead_nextCall_picker_, b2c_international, b2c_country;
    static LinearLayout iiml_course_layout, ba_course_layout;
    LinearLayout user_info_snakebar;
    String LAST_TENDIGIT_MOBILE_NUMBER = null;
    private static long nextCallTimeStamp = 0;
    BatteryModel batteryModel;


    ProgressBar add_b2b_progresss, b2b_cr_progress, b2b_ir_progress;
    String other_check_radio_text = "others";
    EditText work_Ex_profiling, company_Name, work_Experience, edt_current_location;
    LinearLayout current_location;
    TextView indian_Professional, global_Professional_Qualification, ug_Graduate_Qualification, pg_Graduate_Qualification;
    boolean[] selectedIndian_Professional, selectGlobal_Professional, selectUG_Graduate_Qualification, selectPG_Graduate_Qualification;
    ArrayList<Integer> indianProfessionalLangList = new ArrayList<>();
    ArrayList<Integer> globalProfessionalLangList = new ArrayList<>();

    ArrayList<Integer> UG_Graduate_QualificationLangList = new ArrayList<>();

    ArrayList<Integer> PG_Graduate_QualificationLangList = new ArrayList<>();

    static String SELECTED_STATUS = " ";

    static String IIML_FA_Checked = " ";
    static String IIML_BA_Checked = " ";
    static String IIML_PA_Checked = " ";
    static String IIML_HR_Checked = " ";
    static String IITR_BF_Checked = " ";
    static String IITR_DBE_Checked = " ";
    static String CFA_Checked = " ";
    static String FRM_Checked = " ";
    static String USP_Checked = " ";
    AppCompatAutoCompleteTextView autoCompleteText_mwb_b2c;
    String CPAChecked_lead = " ";
    String CMAChecked_lead = " ";
    String IIML_FA_Checked_lead = " ";
    String IIML_SF_Checked_lead = " ";
    int mwb_id;
    CommanApiClient commanApiClient = CommanApiUtills.getAPIService();
    ArrayList<HistoryModel> historyModelArrayList;
    ArrayList<String> years = new ArrayList<String>();
    NetEnquiryData netEnquiryData;

    final String[] select_Indian_professional_Qualification = {
            "No other Indian Professional Qualification",
            "Qualified CA",
            "Qualified CS",
            "Qualified CWA",
            "Semi-Qualified CA-Drop-out",
            "Semi-Qualified CA-Pursuing",
            "Semi-Qualified CS-Drop-out",
            "Semi-Qualified CS-Pursuing",
            "Semi-Qualified CWA-Drop-out",
            "Semi-Qualified CWA-Pursuing",
    };
    final String[] select_Global_professional_Qualification = {
            "No other Global Professional Qualification",
            "Qualified ACCA",
            "Qualified CFA / FRM",
            "Qualified CMA",
            "Qualified CPA",
            "Semi-Qualified ACCA-Drop-out",
            "Semi-Qualified ACCA-Pursuing",
            "Semi-Qualified CFA/FRM -Drop-out",
            "Semi-Qualified CMA-Pursuing",
            "Semi-Qualified CMA-Drop-out",
            "Semi-Qualified CPA-Pursuing",
            "Semi-Qualified CPA-Drop-out",

    };
    final String[] UG_professional_Qualification = {
            "BCom",
            "BCom(P)",
            "B.Sc",
            "B.Sc(P)",
            "B.Tech",
            "B.Tech(P)",
            "BA",
            "BA(P)",
            "BAF",
            "BBA",
            "BBA(P)",
            "BBM",
            "BMS",
            "BMS(P)",
            "INTER",
            "LLB",
            "LLB(P)",
            "PUS",


    };
    final String[] PG_professional_Qualification = {
            "MBA(P)",
            "MCom",
            "BCom(P)",
            "MBA",
            "BA",
            "MCA",
            "MFA",
            "MFA(P)",
            "PG(P)",
            "PGD",
            "PGD(P)",
            "PGDM",
            "PGDPA",
            "PGDSF",


    };


    //my selff
    LinearLayout interested_Layout, looking_job;
    EditText engagement_description;
    String previousEngagement, courses, levels, user_name, person_name;

    static String ConnectionStatus = " ";


    AppCompatSpinner appconpact_spinner_interested_working, appconpact_spinner_looking_job, appconpact_spinner_graduation_Year,
            appconpact_spinner_global_Professional_Qualification, appconpact_spinner_UG_Qualification, appconpact_spinner_PG_Qualification;

    RadioButton IncomingCals_radio, referal_radio, IVR_radio;

    static String SELECTED_STATUS_interested_working = "";

    ArrayList<String> spinner_interested_workingArrayList = new ArrayList<String>();

    ArrayList<String> spinnerLookingJob_workingArrayList = new ArrayList<String>();

    LinearLayout referal, direct, referal_layout, reference_layout_spinner, corporate_company;
    ArrayList<String> ewbArraylist = new ArrayList<>();
    ArrayList<String> mwbArrayList = new ArrayList<>();
    static ArrayAdapter<String> cityAdpater;
    ArrayList<String> directSourceArraylist = new ArrayList<>();
    ArrayList<String> cityArrayList = new ArrayList<>();
    String LeadLevels = " ";

    AppCompatSpinner ewbspinner, mwbspinner, M_levels_spinner, U_levels_spinner, direct_spinner, milesSpos_spinner, appconpact_spinner_connectionstatus;
    ArrayList<LevelsModel> spinner_M_LevelList;
    LevelsCustomAdapter levelsCustomAdapter;

    RadioGroup radioGroupForReference;
    EditText direct_reference;
    ArrayList<LevelsModel> spinnerLevelList;
    ArrayList<String> ConnectionTypeArrayList = new ArrayList<>();

    TextView Source_test, Source_id;
    final String[] uLevelList = {
            "U0-Not interested in US/Canda",
            "U1-Interest expressed, dropped out",
            "U1+-Interest expressed, yet to graduate",
            "U2-Interest expressed, in pipeline (warm)",
            "U3- -Postponed",
            "U3 -Interest expressed, in pipeline (hot)",
            "U3+ -Zoom Attended(Very Hot)",
            "U3++ -Confirm Prospect for USP",
            "U4- -Application initiated, dropped out",
            "U4 -Application Initiate",
            "U4R - Refunded 20k",
            "U5 - Application done, I-20 yet to be received",
            "U5+ -Conditional Offer Received",
            "U6- -I-20 received, Dropped Out",
            "U6 - I 20 Rec. Yet to apply for Visa",
            "U6+ -I 20 Rec. Applied for Visa",
            "U7- -Visa Received But Dropped out",
            "U7 - Visa received",
            "U8 - IIM Indore",
            "U9 - Started program in USA",
            "U10 - Completed program in USA",
    };
    String LEADSOURCE = " ";
    String LeadDetails = " ";
    String LEADCity = " ";


    public NetEnquireFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        realm = Realm.getDefaultInstance();
        apiClient = ApiUtills.getAPIService();
        if (realm.where(UserToken.class).findFirst() != null) {
            userToken = realm.where(UserToken.class).findFirst();
            Access_token = userToken.getAccessToken();
        }
        return inflater.inflate(R.layout.fragment_net_enquire, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CustomDataPOints();
//        mwb_id = getArguments().getInt("id");
//        Log.d("mwb_id->", String.valueOf(mwb_id));
        InterestedWorkingArrayList();
        LookingJobWorkingArrayList();
        batteryModel = new BatterPercentage().getBattertPercentage(getActivity());


        net_enquiry_recyclerView = view.findViewById(R.id.net_enquiry_recyclerView);
        net_enquiry_SwipetoRefresh = view.findViewById(R.id.net_enquiry_SwipetoRefresh);
        snake_bar_top = view.findViewById(R.id.snake_bar_top);
        net_enquiry_SwipetoRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                net_enquiry_SwipetoRefresh.setRefreshing(false);
            }
        });
        net_enquiry_progress = view.findViewById(R.id.net_enquiry_progress);
        flating_btn = view.findViewById(R.id.flating_btn);
        net_enquiry_refresh_floating = view.findViewById(R.id.net_enquiry_refresh_floating);
        bottom_net_enquiry_Progress = view.findViewById(R.id.bottom_net_enquiry_Progress);
        net_enquiry_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // paginatedRecyclerview(net_enquiry_recyclerView);
        flating_btn.setImageBitmap(textAsBitmap(String.valueOf("0"), 40, Color.BLUE));
        net_enquiry_refresh_floating = view.findViewById(R.id.net_enquiry_refresh_floating);
        net_enquiry_refresh_floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sortedNewEnquiryDataArrayList.size() > 0) {
                    sortedNewEnquiryDataArrayList.clear();
                }
                getNetEnquiryData();
            }
        });
        getNetEnquiryData();

    }

    private void getNetEnquiryData() {
        net_enquiry_progress.setVisibility(View.VISIBLE);
        netEnquiryArrayList = new ArrayList<>();
        sortedNewEnquiryDataArrayList = new ArrayList<>();
        page = 1;
        apiClient.getNetEnquiresData(NET_ENQUIRY_URL, 1, "Bearer " + Access_token, "application/json").enqueue(new Callback<NetEnquiry>() {
            @Override
            public void onResponse(Call<NetEnquiry> call, Response<NetEnquiry> response) {
                try {
                    if (response.raw().code() == 515) {
                        new ResumeWork().stResumeWork(getContext());
                        return;
                    }
                    if (response.body() == null) {
                        int statusCode = response.raw().code();
                        if (statusCode == 401) {
                            //SessionLogout(getContext());
                            net_enquiry_progress.setVisibility(View.GONE);
                        }
                    } else {
                        List<NetEnquiryData> netEnquity = response.body().getNetEnquiryData();
                        netEnquiryArrayList.addAll(netEnquity);
                        ArrayList<NetEnquiryData> diallingNumberArrayLIst = getDiallingNUmberData(netEnquiryArrayList);

                        //  sortedNewEnquiryDataArrayList = removeDuplicates(diallingNumberArrayLIst);

                        sortedNewEnquiryDataArrayList = removeDuplicatesTest(diallingNumberArrayLIst);


                        netEnquiryAdapter = new NetEnquiryAdapter(getContext(), sortedNewEnquiryDataArrayList);
                        net_enquiry_recyclerView.setAdapter(netEnquiryAdapter);
                        netEnquiryAdapter.notifyDataSetChanged();

                        //  flating_btn.setImageBitmap(textAsBitmap(String.valueOf(response.body().getTotal()), 40, Color.BLUE));

                        flating_btn.setImageBitmap(textAsBitmap(String.valueOf(sortedNewEnquiryDataArrayList.size()), 40, Color.BLUE));

                        netEnquiryAdapter.onItemClickListners(new NetEnquiryAdapter.OnClickListner() {
                            @Override
                            public void itemSelected(View v, int position, String type) {
                                netEnquiryData = sortedNewEnquiryDataArrayList.get(position);
                                if (type.equals("mobile")) {
                                    if (netEnquiryData.getMobile() != null && netEnquiryData.getMobile() != "") {
                                        TriggerCallWIthTelephoneManager.TriggerCall(String.valueOf(netEnquiryData.getDialling_number()), getContext());
                                    }
                                }
                                if (type.equals("engagement")) {
                                    OpenUntrackedForm(netEnquiryData);
                                }

                            }
                        });

                        net_enquiry_progress.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    net_enquiry_progress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<NetEnquiry> call, Throwable t) {
                net_enquiry_progress.setVisibility(View.GONE);
                Toast.makeText(getContext(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private ArrayList<NetEnquiryData> removeDuplicatesTest(ArrayList<NetEnquiryData> diallingNumberArrayLIst) {
        Map<String, NetEnquiryData> cleanMap = new LinkedHashMap<String, NetEnquiryData>();

        for (int i = 0; i < diallingNumberArrayLIst.size(); i++) {
            cleanMap.put(diallingNumberArrayLIst.get(i).getDialling_number(), diallingNumberArrayLIst.get(i));
        }
        ArrayList<NetEnquiryData> list = new ArrayList<NetEnquiryData>(cleanMap.values());
        return list;
    }


    private ArrayList<NetEnquiryData> getDiallingNUmberData(ArrayList<NetEnquiryData> netEnquiryArrayList) {
        ArrayList<NetEnquiryData> netEnquiryDataArrayList = new ArrayList<>();
        for (int i = 0; i < netEnquiryArrayList.size(); i++) {
            NetEnquiryData netEnquiryData = new NetEnquiryData();
            netEnquiryData.setId(netEnquiryArrayList.get(i).getId());
            netEnquiryData.setIdentity(netEnquiryArrayList.get(i).getIdentity());
            netEnquiryData.setEnquiry_date(netEnquiryArrayList.get(i).getEnquiry_date());
            netEnquiryData.setEnquiry_date_unix(netEnquiryArrayList.get(i).getEnquiry_date_unix());
            netEnquiryData.setDetails(netEnquiryArrayList.get(i).getDetails());
            netEnquiryData.setCourse(netEnquiryArrayList.get(i).getCourse());
            netEnquiryData.setName(netEnquiryArrayList.get(i).getName());
            netEnquiryData.setMobile(netEnquiryArrayList.get(i).getMobile());
            netEnquiryData.setEmail(netEnquiryArrayList.get(i).getEmail());
            netEnquiryData.setCity(netEnquiryArrayList.get(i).getCity());
            netEnquiryData.setSuggested_city(netEnquiryArrayList.get(i).getSuggested_city());
            netEnquiryData.setSelected_spoc(netEnquiryArrayList.get(i).getSelected_spoc());
            netEnquiryData.setSelected_iiml_spoc(netEnquiryArrayList.get(i).getSelected_iiml_spoc());
            netEnquiryData.setStatus(netEnquiryArrayList.get(i).getStatus());
            netEnquiryData.setPerson_name(netEnquiryArrayList.get(i).getPerson_name());
            netEnquiryData.setPerson_id(netEnquiryArrayList.get(i).getPerson_id());
            netEnquiryData.setPerson_type(netEnquiryArrayList.get(i).getPerson_type());
            netEnquiryData.setMwb_id(netEnquiryArrayList.get(i).getMwb_id());
            netEnquiryData.setLevel(netEnquiryArrayList.get(i).getLevel());
            netEnquiryData.setPerson_type(netEnquiryArrayList.get(i).getPerson_type());
            netEnquiryData.setNet_enquiry_type(netEnquiryArrayList.get(i).getNet_enquiry_type());
            if (netEnquiryArrayList.get(i).getMobile().length() == 10) {
                netEnquiryData.setDialling_number(netEnquiryArrayList.get(i).getMobile());
            }
            if (netEnquiryArrayList.get(i).getMobile().length() > 10) {
                String Mobile_NUmber = netEnquiryArrayList.get(i).getMobile();
                String LastTenDigits = Mobile_NUmber.substring(Mobile_NUmber.length() - 10);
                netEnquiryData.setDialling_number(LastTenDigits);
            } else {
                netEnquiryData.setDialling_number(netEnquiryArrayList.get(i).getMobile());

            }
            netEnquiryDataArrayList.add(netEnquiryData);
        }
        return netEnquiryDataArrayList;

    }

    private void OpenUntrackedForm(final NetEnquiryData netEnquiryData) {
        final Dialog untracked_dialog = new Dialog(getContext());
        untracked_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        untracked_dialog.setContentView(R.layout.netenquiry_numberlayout);
        untracked_dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(untracked_dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        b2c_check = untracked_dialog.findViewById(R.id.b2c_check);
        add_a_lead = untracked_dialog.findViewById(R.id.add_a_lead);
        netenquiery = untracked_dialog.findViewById(R.id.netenquiery);

        irrelevant_check = untracked_dialog.findViewById(R.id.irrelevant_check);


        b2c_card = untracked_dialog.findViewById(R.id.b2c_card);

        releationship_card = untracked_dialog.findViewById(R.id.releationship_card);
        relationship_name_untrack = untracked_dialog.findViewById(R.id.relationship_name_untrack);
        personal_save_btn = untracked_dialog.findViewById(R.id.personal_save_btn);
        untracke_progress = untracked_dialog.findViewById(R.id.untracke_progress);
        et_search_clientsInUntracked = untracked_dialog.findViewById(R.id.et_search_clientsInUntracked);
        untrackedLeadsSpinner_m2b = untracked_dialog.findViewById(R.id.untrackedLeadsSpinner_m2b);
        untrack_user_info_details = untracked_dialog.findViewById(R.id.untrack_user_info_details);
        addLead_saveUpdateuntracked_form = untracked_dialog.findViewById(R.id.addLead_saveUpdateuntracked_form);
        addLead_untracked_form = untracked_dialog.findViewById(R.id.addLead_untracked_form);

        b2c_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    add_a_lead.setChecked(false);
                    irrelevant_check.setChecked(false);
                    b2c_card.setVisibility(View.VISIBLE);
                    releationship_card.setVisibility(View.GONE);
                    AddB2bUntrackedLead(untracked_dialog, netEnquiryData, "B2C");


                }
            }
        });

        add_a_lead.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    b2c_check.setChecked(false);
                    irrelevant_check.setChecked(false);
                    b2c_card.setVisibility(View.GONE);
                    releationship_card.setVisibility(View.GONE);
                    untracked_dialog.dismiss();
                    openAddLeadForm(Access_token, netEnquiryData.getId(), netEnquiryData.getName(), netEnquiryData.getMobile(), netEnquiryData.getEmail());
//                    new AlertForAddB2CLeadForml().AddB2cLeadForm(getContext(), Access_token,
//                    netEnquiryData.getMobile(),
//                            netEnquiryData.getEmail()
//                            ,netEnquiryData.getName(),
//                            "Net Enquiry",
//                            String.valueOf(netEnquiryData.getId()),1,netEnquiryData.getNet_enquiry_type());
                }
            }
        });
        irrelevant_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    b2c_check.setChecked(false);
                    add_a_lead.setChecked(false);
                    b2c_card.setVisibility(View.GONE);
                    releationship_card.setVisibility(View.VISIBLE);
                    saveAsIrrevalentData(untracked_dialog, netEnquiryData);

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

    private void CustomDataPOints() {
        ConnectionTypeArrayList = new ArrayList<>();
        ConnectionTypeArrayList.add("Connected / Busy");
        ConnectionTypeArrayList.add("Connected / Discussed");
        ConnectionTypeArrayList.add("Connected / Never call back");
        ConnectionTypeArrayList.add("Connected / Wrong number");
        ConnectionTypeArrayList.add("Connected / Not Interested");
        ConnectionTypeArrayList.add("Busy");
        ConnectionTypeArrayList.add("Not Lifting");
        ConnectionTypeArrayList.add("Not Reachable");
        ConnectionTypeArrayList.add("Disconnected");
        ConnectionTypeArrayList.add("Invalid Number");
        ConnectionTypeArrayList.add("Switched Off");
        ConnectionTypeArrayList.add("Communication barrier");
        ConnectionTypeArrayList.add("Not Educated");

    }

    private void InterestedWorkingArrayList() {
        spinner_interested_workingArrayList = new ArrayList<>();
        spinner_interested_workingArrayList.add("Yes");
        spinner_interested_workingArrayList.add("Yes but graduating in 2024 or after ");
        spinner_interested_workingArrayList.add("No");
        spinner_interested_workingArrayList.add("Maybe");
    }

    private void LookingJobWorkingArrayList() {
        spinnerLookingJob_workingArrayList = new ArrayList<>();
        spinnerLookingJob_workingArrayList.add("Yes");
        spinnerLookingJob_workingArrayList.add("No");
        spinnerLookingJob_workingArrayList.add("Maybe");
    }

    private void openAddLeadForm(String Access_token, int mwb_id, String name, String mobile, String email) {

        getCandidatePersonaDetails(Access_token, mwb_id);

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // before
//        net_enquiry_lead
        dialog.setContentView(R.layout.dialog_add_engagementform);
        interested_Layout = dialog.findViewById(R.id.interested_Layout);
        Source_test = dialog.findViewById(R.id.Source_test);
//        Source_test.setText();
        Source_id = dialog.findViewById(R.id.Source_id);
//        Source_id.setText();

        appconpact_spinner_interested_working = dialog.findViewById(R.id.appconpact_spinner_interested_working);
        ArrayAdapter<String> appconpact_spinner_interested_working_Adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinner_interested_workingArrayList);
        appconpact_spinner_interested_working_Adapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        appconpact_spinner_interested_working.setAdapter(appconpact_spinner_interested_working_Adapter);


        ArrayAdapter<String> appconpact_spinner_looking_job_Adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, spinnerLookingJob_workingArrayList);
        appconpact_spinner_looking_job_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        appconpact_spinner_graduation_Year = dialog.findViewById(R.id.appconpact_spinner_graduation_Year);
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear + 3; i >= 1995; i--) {
            years.add("");
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, years);
        appconpact_spinner_graduation_Year.setAdapter(adapter);

        dialog.setCancelable(true);
//        netenquiery = dialog.findViewById(R.id.netenquiery);
        referal_radio = dialog.findViewById(R.id.referal_radio);
        IVR_radio = dialog.findViewById(R.id.IVR_radio);
        IncomingCals_radio = dialog.findViewById(R.id.IncomingCals_radio);
//        direct_radio = dialog.findViewById(R.id.direct_radio);
//        corporate = dialog.findViewById(R.id.corporate);
//        university = dialog.findViewById(R.id.university);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
        referal = dialog.findViewById(R.id.reference_layout);
        direct = dialog.findViewById(R.id.direct_layout);
        corporate_company = dialog.findViewById(R.id.corporate_company_b2c);
        radioGroupForReference = dialog.findViewById(R.id.reference_radio_group);
        reference_layout_spinner = dialog.findViewById(R.id.reference_layout_spinner);
        ewbspinner = dialog.findViewById(R.id.MwbSpinner);
        mwbspinner = dialog.findViewById(R.id.ewbSpinner);
        direct_reference = dialog.findViewById(R.id.direct_reference);
        direct_spinner = dialog.findViewById(R.id.direct_spinner);
        M_levels_spinner = dialog.findViewById(R.id.M_levels_spinner);
        U_levels_spinner = dialog.findViewById(R.id.U_levels_spinner);
        appconpact_spinner_connectionstatus = dialog.findViewById(R.id.appconpact_spinner_connectionstatus);
        appconpact_spinner_connectionstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    SELECTED_STATUS = appconpact_spinner_connectionstatus.getSelectedItem().toString();
                    Log.d("SELECTED_STATUS------>", SELECTED_STATUS);
                    if (SELECTED_STATUS.equals("Connected / Discussed")) {
                        U_levels_spinner.setEnabled(true);
                        M_levels_spinner.setEnabled(true);
                        M_levels_spinner.setSelection(2);
                        U_levels_spinner.setSelection(6);
                        appconpact_spinner_interested_working.setEnabled(true);
                        edt_current_location.setEnabled(true);
                        engagement_description.setText(" ");
                        ConnectionStatus = "CD";
                        interested_Layout.setEnabled(true);
                        appconpact_spinner_interested_working.setEnabled(true);
//                        M_levels_spinner.setSelection(5);
                        looking_job.setEnabled(true);
                        appconpact_spinner_looking_job.setEnabled(true);
                        appconpact_spinner_graduation_Year.setEnabled(true);
                        edt_current_location.setEnabled(true);
                        work_Ex_profiling.setEnabled(true);
                        company_Name.setEnabled(true);
                        work_Experience.setEnabled(true);
                        indian_Professional.setEnabled(true);
                        global_Professional_Qualification.setEnabled(true);
                        ug_Graduate_Qualification.setEnabled(true);
                        pg_Graduate_Qualification.setEnabled(true);


                    } else if (SELECTED_STATUS.equals("Connected / Busy")) {

                        M_levels_spinner.setEnabled(false);
                        U_levels_spinner.setEnabled(false);
                        appconpact_spinner_interested_working.setEnabled(false);
                        edt_current_location.setEnabled(false);
                        M_levels_spinner.setSelection(2);
                        U_levels_spinner.setSelection(6);


                    } else if (SELECTED_STATUS.equals("Connected / Never call back")) {
                        U_levels_spinner.setEnabled(false);
                        M_levels_spinner.setEnabled(false);
                        appconpact_spinner_interested_working.setEnabled(false);


                        M_levels_spinner.setSelection(0);
                        U_levels_spinner.setSelection(1);


                    } else if (SELECTED_STATUS.equals("Connected / Not Interested")) {
                        U_levels_spinner.setEnabled(false);
                        M_levels_spinner.setEnabled(false);
                        appconpact_spinner_interested_working.setEnabled(false);
                        edt_current_location.setEnabled(false);
                        appconpact_spinner_interested_working.setEnabled(false);

                        M_levels_spinner.setSelection(0);
                        U_levels_spinner.setSelection(1);


                    } else if (SELECTED_STATUS.equals("Busy")) {
                        U_levels_spinner.setEnabled(false);
                        M_levels_spinner.setEnabled(false);
                        appconpact_spinner_interested_working.setEnabled(false);
                        edt_current_location.setEnabled(false);


                        M_levels_spinner.setSelection(2);

                        U_levels_spinner.setSelection(6);

                    } else if (SELECTED_STATUS.equals("Not Lifting")) {
                        U_levels_spinner.setEnabled(false);
                        M_levels_spinner.setEnabled(false);
                        appconpact_spinner_interested_working.setEnabled(false);
                        edt_current_location.setEnabled(false);


                        M_levels_spinner.setSelection(2);
                        U_levels_spinner.setSelection(6);

                    } else if (SELECTED_STATUS.equals("Not Reachable")) {
                        U_levels_spinner.setEnabled(false);
                        M_levels_spinner.setEnabled(false);
                        appconpact_spinner_interested_working.setEnabled(false);
                        edt_current_location.setEnabled(false);


                        M_levels_spinner.setSelection(2);
                        U_levels_spinner.setSelection(6);


                    } else if (SELECTED_STATUS.equals("Disconnected")) {
                        U_levels_spinner.setEnabled(false);
                        M_levels_spinner.setEnabled(false);
                        appconpact_spinner_interested_working.setEnabled(false);
                        edt_current_location.setEnabled(false);


                        M_levels_spinner.setSelection(2);
                        U_levels_spinner.setSelection(6);


                    } else if (SELECTED_STATUS.equals("Invalid Number")) {
                        U_levels_spinner.setEnabled(false);
                        M_levels_spinner.setEnabled(false);
                        appconpact_spinner_interested_working.setEnabled(false);
                        edt_current_location.setEnabled(false);


                        M_levels_spinner.setSelection(0);
                        U_levels_spinner.setSelection(1);


                    } else if (SELECTED_STATUS.equals("Switched Off")) {
                        U_levels_spinner.setEnabled(false);
                        M_levels_spinner.setEnabled(false);
                        appconpact_spinner_interested_working.setEnabled(false);
                        edt_current_location.setEnabled(false);


                        M_levels_spinner.setSelection(2);
                        U_levels_spinner.setSelection(6);

                    } else if (SELECTED_STATUS.equals("Communication barrier")) {
                        U_levels_spinner.setEnabled(false);
                        M_levels_spinner.setEnabled(false);
                        appconpact_spinner_interested_working.setEnabled(false);
                        edt_current_location.setEnabled(false);


                        M_levels_spinner.setSelection(0);
                        U_levels_spinner.setSelection(1);


                    } else if (SELECTED_STATUS.equals("Not Educated")) {
                        U_levels_spinner.setEnabled(false);
                        M_levels_spinner.setEnabled(false);
                        appconpact_spinner_interested_working.setEnabled(false);
                        edt_current_location.setEnabled(false);
                        M_levels_spinner.setSelection(0);
                        U_levels_spinner.setSelection(1);

                    }


                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    String formattedDate = df.format(c);
                    if (previousEngagement == null || previousEngagement.equals(null) || previousEngagement.equals("") || previousEngagement.equals(" ") || previousEngagement.equals("null")) {
                        engagement_description.setText(formattedDate + " - " + SELECTED_STATUS);
                    }
                    if (previousEngagement.contains(" | ")) {
                        int index = previousEngagement.indexOf(" | ");
                        String Result = previousEngagement.substring(index + 2);
                        String resul = Result;
                        engagement_description.setText(formattedDate + " - " + SELECTED_STATUS + " | " + Result);
                    } else {
                        engagement_description.setText(formattedDate + " - " + SELECTED_STATUS + " | " + previousEngagement);
                    }
//                    CoursesData = courses;
//                    } else {
//                    engagement_main_form.setVisibility(View.GONE);
                    //Gone

                    Date c1 = Calendar.getInstance().getTime();
                    SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
                    String formattedDate1 = df1.format(c1);
                    if (previousEngagement == null || previousEngagement.equals(null) || previousEngagement.equals("") || previousEngagement.equals(" ") || previousEngagement.equals("null")) {
                        engagement_description.setText(formattedDate1 + " - " + SELECTED_STATUS);
                    }
                    if (previousEngagement.contains(" | ")) {
                        int index = previousEngagement.indexOf(" | ");
                        String Result = previousEngagement.substring(index + 2);
                        String resul = Result;
                        engagement_description.setText(formattedDate + " - " + SELECTED_STATUS + " | " + Result);
                    } else {
                        engagement_description.setText(formattedDate + " - " + SELECTED_STATUS + " | " + previousEngagement);
                    }
//                    CoursesData = courses;

                } catch (Exception e) {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_M_LevelList = new ArrayList<>();
        spinner_M_LevelList.add(new LevelsModel("M1", "M1 :Did not visit & not interested"));
        spinner_M_LevelList.add(new LevelsModel("M2", "M2 :Did not visit & Postponed"));
        spinner_M_LevelList.add(new LevelsModel("M3", "M3 :Called & positive"));
        spinner_M_LevelList.add(new LevelsModel("M3+", "M3+ :Called & Coming"));
        spinner_M_LevelList.add(new LevelsModel("M3++", "M3++ :Ready to enroll - Not visited"));
//        ArrayAdapter<String> M_levels_spinner_Adapter = new ArrayAdapter<String>(getApplicationContext(),
//                android.R.layout.simple_spinner_item, spinner_M_LevelList);
        levelsCustomAdapter = new LevelsCustomAdapter(getActivity(), R.layout.listitems_layout, R.id.levels_items, spinner_M_LevelList);
        M_levels_spinner.setAdapter(levelsCustomAdapter);


        //my self
        U_levels_spinner = dialog.findViewById(R.id.U_levels_spinner);


        ArrayAdapter<String> U_levels_spinner_Adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, uLevelList);
        U_levels_spinner_Adapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        U_levels_spinner.setAdapter(U_levels_spinner_Adapter);
//        Log.d("GetULevelList_onList", String.valueOf(uLevelListModel));

        appconpact_spinner_looking_job = dialog.findViewById(R.id.appconpact_spinner_looking_job);
        looking_job = dialog.findViewById(R.id.looking_job);
        work_Ex_profiling = dialog.findViewById(R.id.work_Ex_profiling);
        work_Ex_profiling.setText("No work ex - Graduate");


        indian_Professional = dialog.findViewById(R.id.indian_Professional);
        global_Professional_Qualification = dialog.findViewById(R.id.global_Professional_Qualification);
        ug_Graduate_Qualification = dialog.findViewById(R.id.ug_Graduate_Qualification);
        pg_Graduate_Qualification = dialog.findViewById(R.id.pg_Graduate_Qualification);
        selectedIndian_Professional = new boolean[select_Indian_professional_Qualification.length];
        selectGlobal_Professional = new boolean[select_Global_professional_Qualification.length];
        selectUG_Graduate_Qualification = new boolean[UG_professional_Qualification.length];
        selectPG_Graduate_Qualification = new boolean[PG_professional_Qualification.length];


        company_Name = dialog.findViewById(R.id.company_Name);
        work_Experience = dialog.findViewById(R.id.work_Experience);
        //        current_location = dialog.findViewById(R.id.current_location);
//        edt_current_location = dialog.findViewById(R.id.edt_current_location);

        b2c_iiml_fa_check = dialog.findViewById(R.id.b2c_iiml_fa_check);
        b2c_iiml_ba_check = dialog.findViewById(R.id.b2c_iiml_ba_check);
        b2c_iiml_pa_check = dialog.findViewById(R.id.b2c_iiml_pa_check);
        b2c_iiml_hr_check = dialog.findViewById(R.id.b2c_iiml_hr_check);
        b2c_iitr_bf_check = dialog.findViewById(R.id.b2c_iitr_bf_check);
        b2c_iitr_dbe_check = dialog.findViewById(R.id.b2c_iitr_dbe_check);
        b2c_iimlfa_check = dialog.findViewById(R.id.b2c_iimlfa_check);
        b2c_CFA_check = dialog.findViewById(R.id.b2c_CFA_check);
        b2c_FRM_check = dialog.findViewById(R.id.b2c_FRM_check);
        b2c_USP_check = dialog.findViewById(R.id.b2c_USP_check);

        b2c_iimlsf_check = dialog.findViewById(R.id.b2c_iimlsf_check);
        b2c_cpa_check = dialog.findViewById(R.id.b2c_cpa_check);
        b2c_cma_check = dialog.findViewById(R.id.b2c_cma_check);
        b2c_lead_name = dialog.findViewById(R.id.b2c_lead_name);
        b2c_lead_name.setText(name);
        b2c_lead_mobile = dialog.findViewById(R.id.b2c_lead_mobile);
        b2c_lead_mobile.setText(mobile);
        b2c_lead_email = dialog.findViewById(R.id.b2c_lead_email);
        b2c_lead_email.setText(email);

//        b2c_lead_education = dialog.findViewById(R.id.b2c_lead_education);
//        b2c_lead_company = dialog.findViewById(R.id.b2c_lead_company);
//        b2c_lead_designation = dialog.findViewById(R.id.b2c_lead_designation);
//        b2c_lead_experiance = dialog.findViewById(R.id.b2c_lead_experiance);
        b2c_lead_engagement = dialog.findViewById(R.id.b2c_lead_engagement);
//        b2c_international = dialog.findViewById(R.id.international_city);
//        b2c_country = dialog.findViewById(R.id.country);
        b2c_lead_nextCall_picker_ = dialog.findViewById(R.id.b2c_lead_nextCall_picker_);
        add_b2b_progresss = dialog.findViewById(R.id.add_b2b_progresss);
        iiml_course_layout = dialog.findViewById(R.id.iiml_course_layout);
        ba_course_layout = dialog.findViewById(R.id.ba_course_layout);
        if (IIML_TAB_CHANGE_CODE == 0) {
            iiml_course_layout.setVisibility(View.GONE);
            ba_course_layout.setVisibility(View.VISIBLE);
        } else {
            iiml_course_layout.setVisibility(View.VISIBLE);
            ba_course_layout.setVisibility(View.GONE);
        }


        appconpact_spinner_interested_working.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SELECTED_STATUS_interested_working = appconpact_spinner_interested_working.getSelectedItem().toString();
                Log.d("SELECTED_1", SELECTED_STATUS_interested_working);

                if (SELECTED_STATUS_interested_working.equals("Yes")) {
                    appconpact_spinner_looking_job.setEnabled(false);
                    appconpact_spinner_looking_job.setClickable(false);
                    appconpact_spinner_looking_job.setAdapter(appconpact_spinner_looking_job_Adapter);

                } else {
                    appconpact_spinner_looking_job.setEnabled(true);
                    appconpact_spinner_looking_job.setClickable(true);
                    appconpact_spinner_looking_job.setAdapter(appconpact_spinner_looking_job_Adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        indian_Professional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select indian Professional");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(select_Indian_professional_Qualification, selectedIndian_Professional, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            indianProfessionalLangList.add(i);
                            // Sort array list
                            Collections.sort(indianProfessionalLangList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            indianProfessionalLangList.remove(Integer.valueOf(i));
                        }

                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < indianProfessionalLangList.size(); j++) {
                            // concat array value
                            stringBuilder.append(select_Indian_professional_Qualification[indianProfessionalLangList.get(j)]);
                            // check condition
                            if (j != indianProfessionalLangList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        indian_Professional.setText(stringBuilder.toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedIndian_Professional.length; j++) {
                            // remove all selection
                            selectedIndian_Professional[j] = false;
                            // clear language list
                            indianProfessionalLangList.clear();
                            // clear text view value
                            indian_Professional.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });

        global_Professional_Qualification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select Global Professional");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(select_Global_professional_Qualification, selectGlobal_Professional, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            globalProfessionalLangList.add(i);
                            // Sort array list
                            Collections.sort(globalProfessionalLangList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            globalProfessionalLangList.remove(Integer.valueOf(i));
                        }

                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < globalProfessionalLangList.size(); j++) {
                            // concat array value
                            stringBuilder.append(select_Global_professional_Qualification[globalProfessionalLangList.get(j)]);
                            // check condition
                            if (j != globalProfessionalLangList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        global_Professional_Qualification.setText(stringBuilder.toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectGlobal_Professional.length; j++) {
                            // remove all selection
                            selectGlobal_Professional[j] = false;
                            // clear language list
                            globalProfessionalLangList.clear();
                            // clear text view value
                            global_Professional_Qualification.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });

        ug_Graduate_Qualification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select UG Graduate");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(UG_professional_Qualification, selectUG_Graduate_Qualification, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            UG_Graduate_QualificationLangList.add(i);
                            // Sort array list
                            Collections.sort(UG_Graduate_QualificationLangList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            UG_Graduate_QualificationLangList.remove(Integer.valueOf(i));
                        }

                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < UG_Graduate_QualificationLangList.size(); j++) {
                            // concat array value
                            stringBuilder.append(UG_professional_Qualification[UG_Graduate_QualificationLangList.get(j)]);
                            // check condition
                            if (j != UG_Graduate_QualificationLangList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        ug_Graduate_Qualification.setText(stringBuilder.toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectUG_Graduate_Qualification.length; j++) {
                            // remove all selection
                            selectUG_Graduate_Qualification[j] = false;
                            // clear language list
                            UG_Graduate_QualificationLangList.clear();
                            // clear text view value
                            ug_Graduate_Qualification.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });

        pg_Graduate_Qualification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Select UG Graduate");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(PG_professional_Qualification, selectPG_Graduate_Qualification, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            PG_Graduate_QualificationLangList.add(i);
                            // Sort array list
                            Collections.sort(PG_Graduate_QualificationLangList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            PG_Graduate_QualificationLangList.remove(Integer.valueOf(i));
                        }

                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < PG_Graduate_QualificationLangList.size(); j++) {
                            // concat array value
                            stringBuilder.append(PG_professional_Qualification[PG_Graduate_QualificationLangList.get(j)]);
                            // check condition
                            if (j != PG_Graduate_QualificationLangList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        pg_Graduate_Qualification.setText(stringBuilder.toString());
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectPG_Graduate_Qualification.length; j++) {
                            // remove all selection
                            selectPG_Graduate_Qualification[j] = false;
                            // clear language list
                            PG_Graduate_QualificationLangList.clear();
                            // clear text view value
                            pg_Graduate_Qualification.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });


        work_Experience.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String inputValue = s.toString();

                if (inputValue.equals("0")) {
                    work_Ex_profiling.setText("No work ex - Graduate");
                } else if (inputValue.equals("")) {
                    work_Ex_profiling.setText("No work ex - Graduate");
                } else if (inputValue.equals("00")) {
                    work_Ex_profiling.setText("No work ex - Graduate");
                } else {
                    work_Ex_profiling.setText("Working Professional");

                }

            }
        });
        b2c_iiml_fa_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IIML_FA_Checked = "IIML-FT";

                } else {
                    IIML_FA_Checked = "";

                }
            }
        });

        b2c_CFA_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CFA_Checked = "CFA";
                } else {
                    CFA_Checked = "";
                }
            }
        });
        b2c_FRM_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FRM_Checked = "CFA";
                } else {
                    FRM_Checked = "";
                }
            }
        });

        b2c_USP_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    USP_Checked = "CFA";
                } else {
                    USP_Checked = "";
                }
            }
        });

        b2c_iiml_ba_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IIML_BA_Checked = "IIML-BA";
                } else {
                    IIML_BA_Checked = "";
                }
            }
        });
        b2c_iiml_pa_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IIML_PA_Checked = "IIML-PM";
                } else {
                    IIML_PA_Checked = "";
                }
            }
        });
        b2c_iiml_hr_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IIML_HR_Checked = "IIML-HR";
                } else {
                    IIML_HR_Checked = "";
                }
            }
        });
        b2c_iitr_bf_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IITR_BF_Checked = "IITR-BF";
                } else {
                    IITR_BF_Checked = "";
                }
            }
        });
        b2c_iitr_dbe_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IITR_DBE_Checked = "IITR-DB";
                } else {
                    IITR_DBE_Checked = "";
                }
            }
        });


        autoCompleteText_mwb_b2c = dialog.findViewById(R.id.autoCompleteText_mwb_b2c);
        b2c_iimlfa_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IIML_FA_Checked_lead = "IIML-FA";
                } else {
                    IIML_FA_Checked_lead = " ";
                }

            }
        });
        b2c_iimlsf_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IIML_SF_Checked_lead = "IIML-SF";
                } else {
                    IIML_SF_Checked_lead = " ";
                }

            }
        });
        b2c_cpa_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CPAChecked_lead = "CPA";
                } else {
                    CPAChecked_lead = " ";
                }
            }
        });
        b2c_cma_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CMAChecked_lead = "CMA";
                } else {
                    CMAChecked_lead = " ";
                }
            }
        });

        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ((Button) dialog.findViewById(R.id.bt_save_add)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SELECTED_STATUS.equals("Connected / Discussed") || (SELECTED_STATUS.equals("Connected / Not Interested"))) {
                    if (appconpact_spinner_interested_working.getSelectedItem().equals("Yes")) {

                        if (appconpact_spinner_graduation_Year.getSelectedItem().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Please enter graduation Year", Toast.LENGTH_SHORT).show();

                        } else if (b2c_lead_engagement.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Please add engagement details and Please select source details ", Toast.LENGTH_SHORT).show();

                        } else {
                            UpdateCandidatePersonaDetails();
                            SaveMWbB2CLead(dialog);

                        }
                    } else {

                        if (appconpact_spinner_graduation_Year.getSelectedItem().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Please enter graduation Year", Toast.LENGTH_SHORT).show();
                        } else if (company_Name.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Please enter your Company name", Toast.LENGTH_SHORT).show();
                        } else if (work_Experience.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Please enter your Work Experience", Toast.LENGTH_SHORT).show();
                        } else if (indian_Professional.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Please select your indian Professional", Toast.LENGTH_SHORT).show();

                        } else if (global_Professional_Qualification.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Please select your global professional qualification", Toast.LENGTH_SHORT).show();

                        } else if (ug_Graduate_Qualification.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Please select your UG qualification", Toast.LENGTH_SHORT).show();
                        } else if (b2c_lead_engagement.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Please add engagement details and Please select source details ", Toast.LENGTH_SHORT).show();

                        } else {
                            UpdateCandidatePersonaDetails();
                            SaveMWbB2CLead(dialog);
                        }

                    }


                } else {
                    UpdateCandidatePersonaDetails();
                    SaveMWbB2CLead(dialog);
                }


            }
        });
        IncomingCals_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    referal.setVisibility(View.GONE);
                    direct.setVisibility(View.GONE);
                    reference_layout_spinner.setVisibility(View.GONE);
                    LEADSOURCE = "Incoming Cals";
                    IVR_radio.setChecked(false);
                    referal_radio.setChecked(false);
                    corporate_company.setVisibility(View.GONE);
                }
            }
        });
        referal_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    referal.setVisibility(View.VISIBLE);
                    direct.setVisibility(View.GONE);
                    LEADSOURCE = "Referral";
                    IncomingCals_radio.setChecked(false);
                    IVR_radio.setChecked(false);
                    corporate_company.setVisibility(View.GONE);
                }
            }
        });
        IVR_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    referal.setVisibility(View.GONE);
                    direct.setVisibility(View.GONE);
                    reference_layout_spinner.setVisibility(View.GONE);
                    LEADSOURCE = "IVR";
                    IncomingCals_radio.setChecked(false);
                    referal_radio.setChecked(false);
                    corporate_company.setVisibility(View.GONE);
                }
            }
        });

        radioGroupForReference.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.ewb_id) {
                    reference_layout_spinner.setVisibility(View.VISIBLE);
                    ewbspinner.setVisibility(View.VISIBLE);
                    mwbspinner.setVisibility(View.GONE);
                    direct_reference.setVisibility(View.GONE);
                }
                if (checkedId == R.id.mwb_id) {
                    reference_layout_spinner.setVisibility(View.VISIBLE);
                    ewbspinner.setVisibility(View.GONE);
                    mwbspinner.setVisibility(View.VISIBLE);
                    direct_reference.setVisibility(View.GONE);
                }
                if (checkedId == R.id.others_id) {
                    reference_layout_spinner.setVisibility(View.VISIBLE);
                    ewbspinner.setVisibility(View.GONE);
                    mwbspinner.setVisibility(View.GONE);
                    direct_reference.setVisibility(View.VISIBLE);
                    other_check_radio_text = "others";

                }
            }
        });
        /*MWB SPINNER*/
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mwbArrayList);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        mwbspinner.setAdapter(dataAdapter);

        mwbspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LeadDetails = mwbspinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /*EWB Spinner*/

        ArrayAdapter<String> ewbAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ewbArraylist);
        // Drop down layout style - list view with radio button
        ewbAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        ewbspinner.setAdapter(ewbAdapter);

        ewbspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LeadDetails = ewbspinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*DIRECT SPinner*/
        ArrayAdapter<String> directAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, directSourceArraylist);
        // Drop down layout style - list view with radio button
        directAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        direct_spinner.setAdapter(directAdapter);

        direct_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LeadDetails = direct_spinner.getSelectedItem().toString();
                String Data = LeadDetails;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerLevelList = new ArrayList<>();
        spinnerLevelList.add(new LevelsModel("M3++", "M3++ - Ready to enroll - Not visited"));
        spinnerLevelList.add(new LevelsModel("M3+", "M3+ - Called & Coming"));
        spinnerLevelList.add(new LevelsModel("M3", "M3 - Called & positive"));
        spinnerLevelList.add(new LevelsModel("M2", "M2 - Did not visit & postponed"));
        spinnerLevelList.add(new LevelsModel("M1", "M1 - Did not visit & not intersted"));


        ArrayAdapter<String> connection_statusAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ConnectionTypeArrayList);
        connection_statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appconpact_spinner_connectionstatus.setAdapter(connection_statusAdapter);


        M_levels_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
//                LevelsModel levelsModel = spinnerLevelList.get(position);
//                LeadLevels = levelsModel.getLevelCode();
                LeadLevels = M_levels_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//        b2c_lead_nextCall_picker_.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), leads_date, LeadsCalendar
//                        .get(Calendar.YEAR), LeadsCalendar.get(Calendar.MONTH),
//                        LeadsCalendar.get(Calendar.DAY_OF_MONTH));
//                datePickerDialog.getDatePicker().setMinDate(LeadsCalendar.getTimeInMillis());
//                datePickerDialog.show();
//            }
//        });


    }

    private void AddB2bUntrackedLead(final Dialog untracked_dialog, final NetEnquiryData netEnquiryData, final String B2C) {
        et_search_clientsInUntracked.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //  hideKeyboard();
                    if (delaysModelArrayList != null) {
                        delaysModelArrayList.clear();
                    }
                    getSearchedUSerData(Access_token, v.getText().toString(), B2C, untracked_dialog, netEnquiryData);
                    return true;
                }
                return false;
            }
        });

    }

    private void getSearchedUSerData(String accessToken, String person_name, final String B2C, final Dialog dialog, final NetEnquiryData netEnquiryData) {
        untracke_progress.setVisibility(View.VISIBLE);
        delaysModelArrayList = new ArrayList<>();
        untrack_user_info_details.setText("");

        apiClient.getSearchedLeads(person_name, B2C, "Bearer " + Access_token, "application/json").enqueue(new Callback<List<DelaysModel>>() {
            @Override
            public void onResponse(Call<List<DelaysModel>> call, Response<List<DelaysModel>> response) {
                try {
                    List<DelaysModel> getdelaysModel = response.body();
                    for (int i = 0; i < getdelaysModel.size(); i++) {
                        DelaysModel delaysModel = new DelaysModel();
                        delaysModel.setId(getdelaysModel.get(i).getId());
                        delaysModel.setIdentity(getdelaysModel.get(i).getIdentity());
                        delaysModel.setCan_id(getdelaysModel.get(i).getCan_id());
                        delaysModel.setPerson_id(getdelaysModel.get(i).getPerson_id());
                        delaysModel.setPerson_name(getdelaysModel.get(i).getPerson_name());
                        delaysModel.setCourses(getdelaysModel.get(i).getCourses());
                        delaysModel.setLevel(getdelaysModel.get(i).getLevel());
                        delaysModel.setEngagement_details(getdelaysModel.get(i).getEngagement_details());
                        delaysModel.setCompany(getdelaysModel.get(i).getCompany());
                        delaysModel.setDesignation(getdelaysModel.get(i).getDesignation());
                        delaysModel.setMiles_type(getdelaysModel.get(i).getMiles_type());
                        //    delaysModel.setIiml_level(getdelaysModel.get(i).getIiml_level());
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
                AddLeadWithPersonDetails(delaysModel, dialog, netEnquiryData, B2C);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void AddLeadWithPersonDetails(final DelaysModel delaysModel, final Dialog dialog, final NetEnquiryData netEnquiryData, final String B2c) {
        addLead_untracked_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                untracke_progress.setVisibility(View.VISIBLE);
                apiClient.markNetEnquiryWIthMwbID(delaysModel.getIdentity(), netEnquiryData.getMobile(), delaysModel.getId(), delaysModel.getPerson_id(), netEnquiryData.getPerson_name(), "Bearer " + Access_token, "application/json").enqueue(new Callback<SuccessModel>() {
                    @Override
                    public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                        try {
                            if (response.body().getStatus().equals("success")) {
                                new CustomEngagementFormForUntracked().addCustomEngagement(delaysModel, getContext(), AccessToken);
                                dialog.dismiss();
                                if (netEnquiryArrayList != null) {
                                    netEnquiryArrayList.clear();
                                }
                                getNetEnquiryData();

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


    private void saveAsIrrevalentData(final Dialog untracked_dialog, final NetEnquiryData netEnquiryData) {

        personal_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (relationship_name_untrack.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter Comments", Toast.LENGTH_SHORT).show();
                    return;
                }
                untracke_progress.setVisibility(View.VISIBLE);
                apiClient.markNetEnquiryInValid(netEnquiryData.getId(), relationship_name_untrack.getText().toString(), "Bearer " + Access_token, "application/json").enqueue(new Callback<SuccessModel>() {
                    @Override
                    public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                        try {
                            if (response.body().getStatus().equals("success")) {
                                untracked_dialog.dismiss();
                                ShowSnakeBar(response.body().getMessage());
                                untracke_progress.setVisibility(View.GONE);
                                if (netEnquiryArrayList != null) {
                                    netEnquiryArrayList.clear();
                                }
                                getNetEnquiryData();
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

                    }
                });
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

    private void paginatedRecyclerview(RecyclerView escalation_recyclerView) {
        escalation_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            netEnquiryAdapter.getItemCount() - 1) {
                        page = page + 1;
                        bottom_net_enquiry_Progress.setVisibility(View.VISIBLE);
                        apiClient.getNetEnquiresData(NET_ENQUIRY_URL, page, "Bearer " + Access_token, "application/json").enqueue(new Callback<NetEnquiry>() {
                            @Override
                            public void onResponse(Call<NetEnquiry> call, Response<NetEnquiry> response) {
                                try {

                                    ArrayList<NetEnquiryData> net_enquiry = response.body().getNetEnquiryData();
                                    netEnquiryArrayList.addAll(net_enquiry);
                                    netEnquiryAdapter.notifyDataSetChanged();
                                    bottom_net_enquiry_Progress.setVisibility(View.GONE);
                                } catch (Exception e) {
                                    bottom_net_enquiry_Progress.setVisibility(View.GONE);
                                    Toast.makeText(getContext(), String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<NetEnquiry> call, Throwable t) {
                                bottom_net_enquiry_Progress.setVisibility(View.GONE);
                                Toast.makeText(getContext(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                            }
                        });


                        //load more items code is here
                    }
                }
            }
        });
    }

    public void ShowSnakeBar(String s) {
        Snackbar snackbar = Snackbar
                .make(snake_bar_top, String.valueOf(s), 5000);
        snackbar.show();
    }


    public ArrayList<NetEnquiryData> removeDuplicates(ArrayList<NetEnquiryData> list) {
        Set<NetEnquiryData> set = new TreeSet(new Comparator<NetEnquiryData>() {

            @Override
            public int compare(NetEnquiryData o1, NetEnquiryData o2) {
                if (o1.getDialling_number().equals(o2.getDialling_number()) || o1.getDialling_number().equalsIgnoreCase(o2.getDialling_number()) || o1.getDialling_number().contains(o2.getDialling_number()) || o2.getDialling_number().contains(o1.getDialling_number())) {
                    if (o2.getDuplicateCount() == 0)
                        o2.setDuplicateCount(1);
                    o2.setDuplicateCount(o2.getDuplicateCount() + 1);
                    return 0;
                }
                return 1;
            }
        });
        set.addAll(list);

        final ArrayList newList = new ArrayList(set);
        return newList;
    }

    private void SaveMWbB2CLead(final Dialog dialog) {
        if (SELECTED_STATUS.equals(" ")) {
            Toast.makeText(getActivity(), "Please select Connection status ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (SELECTED_STATUS.equals("Connected / Discussed")) {
            ConnectionStatus = "CD";
        }
        if (SELECTED_STATUS.equals("Connected / Busy")) {
            ConnectionStatus = "CB";
        }
        if (SELECTED_STATUS.equals("Connected / Never call back")) {
            ConnectionStatus = "CN";
        }
        if (SELECTED_STATUS.equals("Connected / Wrong number")) {
            ConnectionStatus = "CW";
        }
        if (SELECTED_STATUS.equals("Busy")) {
            ConnectionStatus = "B";
        }
        if (SELECTED_STATUS.equals("Not Lifting")) {
            ConnectionStatus = "NL";
        }
        if (SELECTED_STATUS.equals("Not Reachable")) {
            ConnectionStatus = "NR";
        }
        if (SELECTED_STATUS.equals("Disconnected")) {
            ConnectionStatus = "D";
        }
        if (SELECTED_STATUS.equals("Invalid Number")) {
            ConnectionStatus = "IN";
        }
        if (SELECTED_STATUS.equals("Switched Off")) {
            ConnectionStatus = "SO";
        }
        if (b2c_lead_name.getText().toString().trim().isEmpty()) {
            b2c_lead_name.setError("please enter user name");
        }
        if ((b2c_lead_mobile.getText().toString().isEmpty()) && (b2c_lead_email.getText().toString().isEmpty())) {
            Toast.makeText(getActivity(), "Please fill either Email or Mobile number.....!", Toast.LENGTH_SHORT).show();
        } else {
            if (isValidateMwbForm()) {
                if (other_check_radio_text.equals("others")) {
                    LeadDetails = direct_reference.getText().toString();
                }
                add_b2b_progresss.setVisibility(View.VISIBLE);
                if (b2c_lead_mobile.getText().toString().trim().length() == 10) {
                    LAST_TENDIGIT_MOBILE_NUMBER = b2c_lead_mobile.getText().toString().trim();
                }
                if (b2c_lead_mobile.getText().toString().trim().length() > 10) {
                    String Mobile_NUmber = b2c_lead_mobile.getText().toString().trim();
                    LAST_TENDIGIT_MOBILE_NUMBER = Mobile_NUmber.substring(Mobile_NUmber.length() - 10);
                }
                if (b2c_lead_mobile.getText().toString().trim().length() < 10) {
                    Toast.makeText(getActivity(), "Please enter 10 digits mobile number", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }
//                String international_city = b2c_international.getText().toString();
//                String country_ = b2c_country.getText().toString();
                apiClient.AddMwbLead(b2c_lead_name.getText().toString().trim(),
                        LeadLevels, MWbLeadCourseData,
                        "B2C",
                        "b2c_lead_company.getText().toString()",
                        "b2c_lead_designation.getText().toString()",
                        "b2c_lead_experiance.getText().toString()",
                        "  b2c_lead_education.getText().toString(),",
                        LEADCity,
                        "", LEADSOURCE, LeadDetails, b2c_lead_email.getText().toString().trim()
                        , LAST_TENDIGIT_MOBILE_NUMBER, b2c_lead_engagement.getText().toString().trim(),
                        nextCallTimeStamp, 0, batteryModel.getBattey_percentage(),
                        batteryModel.getCharging_status(), VERSION_NUMBER, "international_city",
                        "country_", ConnectionStatus, "", U_levels_spinner.getSelectedItem().toString(), "Bearer " + accessToken, "application/json").enqueue(new Callback<SuccessModel>() {
                    @Override
                    public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                        try {
                            if (response.body() == null) {
                                int statusCode = response.raw().code();
                                if (statusCode > 399 && statusCode < 500) {
                                    SessionLogout(getContext());
                                }
                                dialog.dismiss();
                            } else {
                                if (response.body().getStatus().equals("success")) {
                                    dialog.dismiss();
                                    ShowSnakeBar(response.body().getMessage());
                                    add_b2b_progresss.setVisibility(View.GONE);
                                }
                                if (response.body().getStatus().equals("error")) {
                                    ShowSnakeBar(response.body().getMessage());
                                    String spam_number_check = response.body().getMessage();
                                    add_b2b_progresss.setVisibility(View.GONE);
                                    if (spam_number_check.contains("spam")) {
                                        showSpamDialog(dialog, LAST_TENDIGIT_MOBILE_NUMBER, b2c_lead_mobile.getText().toString().trim(), "Bearer " + accessToken);
                                    } else {
                                        ShowSnakeBar(response.body().getMessage());
                                        add_b2b_progresss.setVisibility(View.GONE);
                                        //  dialog.dismiss();

                                    }
                                } else {
                                    //dialog.dismiss();
                                    ShowSnakeBar(response.body().getMessage());
                                    add_b2b_progresss.setVisibility(View.GONE);
                                }

                            }
                        } catch (Exception e) {
                            ShowSnakeBar(e.getMessage());
                            add_b2b_progresss.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessModel> call, Throwable t) {
                        ShowSnakeBar(t.getMessage());
                        add_b2b_progresss.setVisibility(View.GONE);
                    }
                });
            }
        }


    }


    private void showSpamDialog(Dialog dialog, final String last_tendigit_mobile_number, String
            original_number, final String accessToken) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(dialog.getContext());
        builder1.setMessage("This number(" + original_number + " ) is marked as spam. Would you like to remove it from Spam ?");
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Remove from Spam",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        removeThisNumberFromSpamApi(dialog, last_tendigit_mobile_number, accessToken);
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void removeThisNumberFromSpamApi(DialogInterface dialog, String
            last_tendigit_mobile_number, String bearerAccessToken) {
        apiClient.removeFromSpam(last_tendigit_mobile_number, bearerAccessToken).enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                if (response.body().getStatus().equals("success")) {
                    Toast.makeText(getContext(), "Removed the number from spam. Now add a lead with this number", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        dialog.dismiss();
    }

    private boolean isValidateMwbForm() {
        if (b2c_iiml_fa_check.isChecked() && b2c_iiml_ba_check.isChecked()) {
            MWbLeadCourseData = IIML_FA_Checked + "," + IIML_BA_Checked;
        }
        if (b2c_iiml_fa_check.isChecked()) {
            MWbLeadCourseData = IIML_FA_Checked;
        }
        if (b2c_iiml_ba_check.isChecked()) {
            MWbLeadCourseData = IIML_BA_Checked;
        }
        if (b2c_iiml_pa_check.isChecked()) {
            MWbLeadCourseData = IIML_PA_Checked;
        }
        if (b2c_iiml_hr_check.isChecked()) {
            MWbLeadCourseData = IIML_HR_Checked;
        }
        if (b2c_iitr_bf_check.isChecked()) {
            MWbLeadCourseData = IITR_BF_Checked;
        }
        if (b2c_iitr_dbe_check.isChecked()) {
            MWbLeadCourseData = IITR_DBE_Checked;
        }
        if (b2c_iiml_fa_check.isChecked() && b2c_iitr_dbe_check.isChecked()) {
            MWbLeadCourseData = IIML_FA_Checked + "," + IITR_DBE_Checked;
        }
        if (b2c_iiml_ba_check.isChecked() && b2c_iitr_dbe_check.isChecked()) {
            MWbLeadCourseData = IIML_BA_Checked + "," + IITR_DBE_Checked;
        }
        if (b2c_iitr_bf_check.isChecked() && b2c_iitr_dbe_check.isChecked()) {
            MWbLeadCourseData = IITR_BF_Checked + "," + IITR_DBE_Checked;
        }
        if (b2c_iiml_fa_check.isChecked() && b2c_iitr_bf_check.isChecked()) {
            MWbLeadCourseData = IIML_FA_Checked + "," + IITR_BF_Checked;
        }
        if (b2c_iiml_ba_check.isChecked() && b2c_iitr_bf_check.isChecked()) {
            MWbLeadCourseData = IIML_BA_Checked + "," + IITR_BF_Checked;
        }
        if (b2c_iiml_fa_check.isChecked() && b2c_iiml_pa_check.isChecked()) {
            MWbLeadCourseData = IIML_FA_Checked + "," + IIML_PA_Checked;
        }
        if (b2c_iiml_ba_check.isChecked() && b2c_iiml_pa_check.isChecked()) {
            MWbLeadCourseData = IIML_BA_Checked + "," + IIML_PA_Checked;
        }
        if (b2c_iiml_ba_check.isChecked() && b2c_iiml_hr_check.isChecked()) {
            MWbLeadCourseData = IIML_BA_Checked + "," + IIML_HR_Checked;
        }
        if (b2c_iiml_fa_check.isChecked() && b2c_iiml_hr_check.isChecked()) {
            MWbLeadCourseData = IIML_FA_Checked + "," + IIML_HR_Checked;
        }
        if (b2c_iiml_pa_check.isChecked() && b2c_iiml_hr_check.isChecked()) {
            MWbLeadCourseData = IIML_PA_Checked + "," + IIML_HR_Checked;
        }
        if (b2c_cpa_check.isChecked() && b2c_cma_check.isChecked()) {
            MWbLeadCourseData = CPAChecked_lead + "," + CMAChecked_lead;
        }
        if (b2c_iimlfa_check.isChecked() && b2c_iimlsf_check.isChecked()) {
            MWbLeadCourseData = IIML_FA_Checked_lead + "," + IIML_SF_Checked_lead;
            ;
        }
        if (b2c_cpa_check.isChecked()) {
            MWbLeadCourseData = CPAChecked_lead;
        }
        if (b2c_cma_check.isChecked()) {
            MWbLeadCourseData = CMAChecked_lead;
        }
        if (b2c_iimlfa_check.isChecked()) {
            MWbLeadCourseData = IIML_FA_Checked_lead;
        }
        if (b2c_iimlsf_check.isChecked()) {
            MWbLeadCourseData = IIML_SF_Checked_lead;
        }
        if (b2c_CFA_check.isChecked()) {
            MWbLeadCourseData = CFA_Checked;

        }
        if (b2c_FRM_check.isChecked()) {
            MWbLeadCourseData = FRM_Checked;

        }
        if (b2c_USP_check.isChecked()) {
            MWbLeadCourseData = USP_Checked;

        }
//        if (b2c_cpa_check.isChecked() && b2c_cma_check.isChecked() && b2c_da_check.isChecked()) {
//            MWbLeadCourseData = CPAChecked_lead + "," + CMAChecked_lead + "," + DAChecked_lead;
//        } else if (b2c_cpa_check.isChecked() && b2c_cma_check.isChecked()) {
//            MWbLeadCourseData = CPAChecked_lead + "," + CMAChecked_lead;
//            // return false;
//        } else if (b2c_cma_check.isChecked() && b2c_da_check.isChecked()) {
//            MWbLeadCourseData = CMAChecked_lead + "," + DAChecked_lead;
//            // return false;
//        } else if (b2c_cpa_check.isChecked()&&wcba_checked.isChecked()){
//            MWbLeadCourseData = CPAChecked_lead + "," + WCBAChecked;
//        }else if (b2c_cma_check.isChecked()&&wcba_checked.isChecked()){
//            MWbLeadCourseData = CMAChecked_lead + "," + WCBAChecked;
//        }
//        else if (b2c_cpa_check.isChecked()&&iiml_checked.isChecked()){
//            MWbLeadCourseData = CPAChecked_lead + "," + IIMLChecked;
//        }else if (b2c_cma_check.isChecked()&&iiml_checked.isChecked()){
//            MWbLeadCourseData = CMAChecked_lead + "," + IIMLChecked;
//        }
//        else if (b2c_cpa_check.isChecked() && b2c_da_check.isChecked()) {
//            MWbLeadCourseData = CPAChecked_lead + "," + DAChecked_lead;
//            // return false;
//        }
//        else if (wcba_checked.isChecked()&&iiml_checked.isChecked()){
//            MWbLeadCourseData = WCBAChecked + "," + IIMLChecked;
//        }
//
//        else if (b2c_cpa_check.isChecked()) {
//            MWbLeadCourseData = CPAChecked_lead;
//        } else if (b2c_cma_check.isChecked()) {
//            MWbLeadCourseData = CMAChecked_lead;
//        } else if (b2c_da_check.isChecked()) {
//            MWbLeadCourseData = DAChecked_lead;
//        } else if (rpa_cpa_check.isChecked()) {
//            MWbLeadCourseData = RPA_Checked_lead;
//        } else if (fofo_cma_check.isChecked()) {
//            MWbLeadCourseData = FOFO_Checked_lead;
//        }else if (wcba_checked.isChecked()){
//            MWbLeadCourseData=WCBAChecked;
//        }else if (iiml_checked.isChecked()){
//            MWbLeadCourseData=IIMLChecked;
//        }
        return true;
    }

    private void SessionLogout(Context context) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(ConstantUtills.AccessToken);
        editor.apply();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }


    private void getCandidatePersonaDetails(String accessToken, int mwb_id) {
        historyModelArrayList = new ArrayList<>();
        commanApiClient.getCandidatePersonaDetails(mwb_id, "Bearer " + accessToken, "application/json").enqueue(new Callback<CandidatePersonaDetailsModel>() {
            @Override
            public void onResponse(Call<CandidatePersonaDetailsModel> call, Response<CandidatePersonaDetailsModel> response) {
                try {
                    if (response.body() == null) {
                        int statusCode = response.raw().code();
                        if (statusCode > 399 && statusCode < 500) {
                            SessionLogout(getContext());
                        }
                    } else {
                        CandidatePersonaDetailsModel candidatePersonaDetailsModel = response.body();

                        company_Name.setText(candidatePersonaDetailsModel.getCompany());
                        work_Experience.setText(candidatePersonaDetailsModel.getYearsOfExperience().toString());
                        indian_Professional.setText(candidatePersonaDetailsModel.getIndianProfessionalQualification());
                        global_Professional_Qualification.setText(candidatePersonaDetailsModel.getGlobalProfessionalQualification());
                        ug_Graduate_Qualification.setText(candidatePersonaDetailsModel.getUgQualification());
                        pg_Graduate_Qualification.setText(candidatePersonaDetailsModel.getPgQualification());


                        if (candidatePersonaDetailsModel.getPathwayValue() != null) {
                            for (int i = 0; i < spinner_interested_workingArrayList.size(); i++) {
                                if (spinner_interested_workingArrayList.get(i).equals(candidatePersonaDetailsModel.getPathwayValue())) {
                                    appconpact_spinner_interested_working.setSelection(i);
                                    break;
                                }
                            }


                        }
                        if (candidatePersonaDetailsModel.getPlacementAssistance() != null) {
                            for (int i = 0; i < spinnerLookingJob_workingArrayList.size(); i++) {
                                if (spinnerLookingJob_workingArrayList.get(i).equals(candidatePersonaDetailsModel.getPlacementAssistance())) {
                                    appconpact_spinner_looking_job.setSelection(i);
                                    break;
                                }
                            }
                        }

                        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
                        for (int i = thisYear + 3; i >= 1995; i--) {
                            years.add("");
                            years.add(Integer.toString(i));

                        }
                        if (candidatePersonaDetailsModel.getGraduationYear() != null) {
                            for (int i = 0; i < years.size(); i++) {
                                if (years.get(i).contains(candidatePersonaDetailsModel.getGraduationYear().toString())) {
                                    appconpact_spinner_graduation_Year.setSelection(i);
                                    break;
                                }
                            }
                        }


                    }
                } catch (Exception e) {

                }
            }


            @Override
            public void onFailure(Call<CandidatePersonaDetailsModel> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
            }
        });
    }

    private void UpdateCandidatePersonaDetails() {
        commanApiClient.UpdateCandidatePersonaDetails(
                String.valueOf(netEnquiryData.getId()),
                company_Name.getText().toString().trim(),
                "location",
                appconpact_spinner_interested_working.getSelectedItem().toString(),
                appconpact_spinner_looking_job.getSelectedItem().toString(),
                indian_Professional.getText().toString().trim(),
                global_Professional_Qualification.getText().toString().trim(),
                ug_Graduate_Qualification.getText().toString().trim(),
                pg_Graduate_Qualification.getText().toString().trim(),
                work_Experience.getText().toString().trim(),
                appconpact_spinner_graduation_Year.getSelectedItem().toString(),
                "Bearer " + Access_token, "application/json").enqueue(new Callback<UpdateCandidatePersonaDetailsModel>() {
            @Override
            public void onResponse(Call<UpdateCandidatePersonaDetailsModel> call, Response<UpdateCandidatePersonaDetailsModel> response) {
                UpdateCandidatePersonaDetailsModel updateCandidatePersonaDetailsModel = response.body();

                Log.d("onResponse_Update", updateCandidatePersonaDetailsModel.getStatus());

            }

            @Override
            public void onFailure(Call<UpdateCandidatePersonaDetailsModel> call, Throwable t) {
                Log.d("UpdateCandidate_Failure", t.getMessage());
//                openAlert(String.valueOf(t.getMessage()));
            }
        });


    }


}
