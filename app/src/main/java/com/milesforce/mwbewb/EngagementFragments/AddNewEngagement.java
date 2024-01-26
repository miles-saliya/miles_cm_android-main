package com.milesforce.mwbewb.EngagementFragments;

//test

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.milesforce.mwbewb.Activities.LoginActivity;
import com.milesforce.mwbewb.Model.CandidatePersonaDetailsModel;
import com.milesforce.mwbewb.Model.EngagemmentHistoryModel;
import com.milesforce.mwbewb.Model.HistoryModel;
import com.milesforce.mwbewb.Model.LevelsModel;
import com.milesforce.mwbewb.Model.TeamModel;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;
import com.milesforce.mwbewb.Retrofit.CommanApiClient;
import com.milesforce.mwbewb.Retrofit.CommanApiUtills;
import com.milesforce.mwbewb.Retrofit.NinjaApiClient;
import com.milesforce.mwbewb.Retrofit.NinjaApiUtills;
import com.milesforce.mwbewb.Retrofit.SuccessModel;
import com.milesforce.mwbewb.Utils.AlertForMobileNumebrsForWhatsup;
import com.milesforce.mwbewb.Utils.BatterPercentage;
import com.milesforce.mwbewb.Utils.BatteryModel;
import com.milesforce.mwbewb.Utils.ConstantUtills;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.milesforce.mwbewb.Utils.ConstantUtills.SaveToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.VERSION_NUMBER;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewEngagement extends Fragment implements View.OnClickListener {
    private ViewPager view_pager_engagement;
    private TabLayout tab_layoutEngagementform;
    FloatingActionButton call, visit, email, whatsup;
    ArrayList<HistoryModel> historyModelArrayList;
    RecyclerView HistoryRecyclerView;
    HistoryAdapter historyAdapter;

    AppCompatSpinner appconpact_spinner_M_levels, appconpact_spinner_U_levels, appconpact_spinner_connectionstatus;
    LinearLayout level_M_layout, level_U_layout;
    LevelsModel levelsModel = new LevelsModel();  // Proper initialization

    ArrayList<LevelsModel> spinner_M_LevelList;
    //    ArrayList<LevelsModel> spinner_U_LevelList;
    List<LevelsModel> spinner_U_LevelList = new ArrayList<>();

    ArrayList<LevelsModel> spinnerLevelListvisit = new ArrayList<>();

    ArrayList<String> ConnectionTypeArrayList;

    ArrayList<String> templets;
    EditText date_picker_;
    Calendar myCalendar;
    TextView compose_text, preview_text, title_header, textview_header;
    LinearLayout preview_layout, compose_layout;
    EditText subject_edit, compose_edit, content_whatsUp;
    AppCompatSpinner appconpact_spinner_levels_email;
    WebView webView;
    AssetManager assetManager;
    String PreviewData;
    RadioGroup responce_radio_group;
    String RadioBtnResponseType = null;
    EditText engagement_description;
    AppCompatCheckBox cpaCheckbox, cmaCheckbox, CPA_AA_checked, iimlsf_checked, iimlfa_checked, CFA_checked, FRM_checked, USP_checked;
    static String CPAChecked = " ";
    static String CPMChecked = " ";
    static String IIMLFAChecked = " ";
    static String IIMLSFChecked = " ";
    static String CFAChecked = " ";
    static String FRMChecked = "";
    static String USPChecked = "";

    RelativeLayout invite_webinar;

    static String CoursesData = null;

    String AccessToken;

    int person_id, can_id;

    String previousEngagement = null;

    String courses, m_levels, u_levels, user_name, phone_number;
    EditText latestEngagement;
    LinearLayout nextTimeLayout, engagement_main_form, level_layout_;
    String LevelsSelected = null;
    private static long nextCallTimeStamp = 0;
    ProgressBar add_engagement_progress;
    ApiClient apiClient = ApiUtills.getAPIService();
    CommanApiClient
            commanApiClient = CommanApiUtills.getAPIService();


    AlertDialog alert;
    EditText engagement_description_visit, date_picker_visit;
    String User_levels = " ";
    String str_U_levels = " ";

    SharedPreferences sharedPreferences;
    BatteryModel batteryModel;
    LevelsCustomAdapter levelsCustomAdapter;
    String NewLevel;
    static String ConnectionStatus = " ";
    static String SELECTED_STATUS = "";
    static String Enrollment_status = "No";
    static CheckBox enroll_check;
    DatePickerDialog datePickerDialog;
    int MAX_DATE_PRE_SELECT = 0;
    String USER_TEAM = "CM";


    //my self
    LinearLayout interested_working, looking_job, graduation_Year, interested_Layout, current_location_layout;
    AppCompatSpinner appconpact_spinner_interested_working, appconpact_spinner_looking_job,
            appconpact_spinner_graduation_Year;
    ArrayList<String> years = new ArrayList<String>();

    static String SELECTED_STATUS_interested_working = "";

    ArrayList<String> spinner_interested_workingArrayList;
    ArrayList<String> spinnerLookingJob_workingArrayList;

    EditText work_Ex_profiling, company_Name, edt_work_Experience, edt_current_location;
    TextView indian_Professional, global_Professional_Qualification, ug_Graduate_Qualification, pg_Graduate_Qualification;
    boolean[] selectedIndian_Professional, selectGlobal_Professional, selectUG_Graduate_Qualification, selectPG_Graduate_Qualification;
    ArrayList<Integer> indianProfessionalLangList = new ArrayList<>();
    ArrayList<Integer> globalProfessionalLangList = new ArrayList<>();

    ArrayList<Integer> UG_Graduate_QualificationLangList = new ArrayList<>();

    ArrayList<Integer> PG_Graduate_QualificationLangList = new ArrayList<>();

    NinjaApiClient ninjaApiClient;

    List<String> MLevelList = new ArrayList<>();
//    List<String> uLevelList = new ArrayList<>();


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

//    final String[] uLevelList = {
//            "U0-Not interested in US/Canda",
//            "U1-Interest expressed, dropped out",
//            "U1+-Interest expressed, yet to graduate",
//            "U2-Interest expressed, in pipeline (warm)",
//            "U3- -Postponed",
//            "U3 -Interest expressed, in pipeline (hot)",
//            "U3+ -Zoom Attended(Very Hot)",
//            "U3++ -Confirm Prospect for USP",
//            "U4- -Application initiated, dropped out",
//            "U4 -Application Initiate",
//            "U4R - Refunded 20k",
//            "U5 - Application done, I-20 yet to be received",
//            "U5+ -Conditional Offer Received",
//            "U6- -I-20 received, Dropped Out",
//            "U6 - I 20 Rec. Yet to apply for Visa",
//            "U6+ -I 20 Rec. Applied for Visa",
//            "U7- -Visa Received But Dropped out",
//            "U7 - Visa received",
//            "U8 - IIM Indore",
//            "U9 - Started program in USA",
//            "U10 - Completed program in USA",
//    };
//


    public AddNewEngagement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sharedPreferences = getContext().getSharedPreferences(SaveToken, MODE_PRIVATE);
        return inflater.inflate(R.layout.fragment_add_new_engagement, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AccessToken = getArguments().getString("token");
        person_id = getArguments().getInt("person_id");
        can_id = getArguments().getInt("can_id");
        previousEngagement = getArguments().getString("previousEngagement");
        courses = getArguments().getString("courses");
        m_levels = getArguments().getString("levels");
        user_name = getArguments().getString("user_name");
        phone_number = getArguments().getString("phone_number");

        u_levels = getArguments().getString("iiml_level");

        if (u_levels != null)
            if (u_levels.equals("U4R")) {
                u_levels = "U7R";
            }
        LevelsSelected = m_levels;
        str_U_levels = u_levels;
        User_levels = m_levels;
        NewLevel = m_levels;
        CustomDataPOints();

        ninjaApiClient = NinjaApiUtills.getAPIService();
//        GetMLevelList(AccessToken);
//        GetULevelList(AccessToken);
        InterestedWorkingArrayList();
        LookingJobWorkingArrayList();
        getTeam(AccessToken);
        initViews(view);
        getCandidatePersonaDetails(AccessToken, person_id);
        batteryModel = new BatterPercentage().getBattertPercentage(getContext());

    }

    private void getTeam(String AccessToken) {
        apiClient.getTeam("Bearer " + AccessToken).enqueue(new Callback<TeamModel>() {
            @Override
            public void onResponse(Call<TeamModel> call, Response<TeamModel> response) {
                try {
                    if (response.raw().code() == 200) {
                        USER_TEAM = response.body().getTeam();
                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<TeamModel> call, Throwable t) {

            }
        });
    }

    private void CustomDataPOints() {
        ConnectionTypeArrayList = new ArrayList<>();
        ConnectionTypeArrayList.add("");
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
        spinner_interested_workingArrayList.add("Yes but graduating in 2024 or after");
        spinner_interested_workingArrayList.add("No");
        spinner_interested_workingArrayList.add("Maybe");
    }

    private void LookingJobWorkingArrayList() {
        spinnerLookingJob_workingArrayList = new ArrayList<>();
        spinnerLookingJob_workingArrayList.add("Yes");
        spinnerLookingJob_workingArrayList.add("No");
        spinnerLookingJob_workingArrayList.add("Maybe");
    }

    private void initViews(View view) {
        call = view.findViewById(R.id.call_);
        call.setOnClickListener(this);

        visit = view.findViewById(R.id.visit_);
        visit.setOnClickListener(this);

        email = view.findViewById(R.id.email_);
        email.setOnClickListener(this);

        whatsup = view.findViewById(R.id.whatsup_);
        whatsup.setOnClickListener(this);


    }

    private void showCustomDialog(final String user_name, final String Mlevel, final String Ulevel) {

        getCandidatePersonaDetails(AccessToken, person_id);

        Toast.makeText(getContext(), USER_TEAM, Toast.LENGTH_SHORT).show();
        nextCallTimeStamp = 0;
        ConnectionStatus = "";


        if (Mlevel.equals("M7") || Mlevel.equals("M7#") || Mlevel.equals("M7-JJ") || Mlevel.equals("M7D2") || Mlevel.equals("M7D1") || Mlevel.equals("M8+")
                || Mlevel.equals("M7-") || Mlevel.equals("M7+") || Mlevel.equals("M7X") || Mlevel.equals("M8-") || Mlevel.equals("M8")
                || Mlevel.equals("M9-") || Mlevel.equals("M9") || Mlevel.equals("M10")) {
            if (spinner_M_LevelList != null) {
                spinner_M_LevelList.clear();
            }
            spinner_M_LevelList = new ArrayList<>();
            spinner_M_LevelList.add(new LevelsModel("M10", "M10 - Alumini (30 days)"));
            spinner_M_LevelList.add(new LevelsModel("M9", "M9 - One exam cleared (30 days)"));
            spinner_M_LevelList.add(new LevelsModel("M9-", "M9- - Drop-out (after clearing an exam) (once every 3 months)"));
            spinner_M_LevelList.add(new LevelsModel("M8+", "M8+ - Exam Registrations"));
            spinner_M_LevelList.add(new LevelsModel("M8", "M8 - Evaluation (30 days)"));
            spinner_M_LevelList.add(new LevelsModel("M8-", "M8- - Drop-out (after evaluation) (once every 3 months)"));
            spinner_M_LevelList.add(new LevelsModel("M7X", "M7X - Do Not Ever Call (NFD - Blank)"));
            spinner_M_LevelList.add(new LevelsModel("M7+", "M7+ LMS demo completed (30-60 days)"));
            spinner_M_LevelList.add(new LevelsModel("M7-", "M7- - Drop-out (30 days)"));
            spinner_M_LevelList.add(new LevelsModel("M7D2", "M7D2 - Loan Defaulter"));
            spinner_M_LevelList.add(new LevelsModel("M7D1", "M7D1 - Defaulter"));
            spinner_M_LevelList.add(new LevelsModel("M7#", "M7# - Onboarding Done"));
            spinner_M_LevelList.add(new LevelsModel("M7-JJ", "M7-JJ (Non Miles Jain Enrolled Candidates)"));
            spinner_M_LevelList.add(new LevelsModel("M7", "M7- Enrolled"));


        } else {
            if (Mlevel.equals("M6") || Mlevel.equals("M5") || Mlevel.equals("M4") || Mlevel.equals("M4-")) {
                if (spinner_M_LevelList != null) {
                    spinner_M_LevelList.clear();
                }
                spinner_M_LevelList = new ArrayList<>();
                spinner_M_LevelList.add(new LevelsModel("M6", "M6 - Visited & Ready to Enroll"));
                spinner_M_LevelList.add(new LevelsModel("M5", "M5 - Visited & Positive"));
                spinner_M_LevelList.add(new LevelsModel("M4", "M4 - Visited but Postponed"));
                spinner_M_LevelList.add(new LevelsModel("M4-", "M4- - Visited but not interested"));
            } else {
                if (spinner_M_LevelList != null) {
                    spinner_M_LevelList.clear();
                }
                spinner_M_LevelList = new ArrayList<>();
                spinner_M_LevelList.add(new LevelsModel("M3+", "M3+ - Called & Coming"));
                spinner_M_LevelList.add(new LevelsModel("M3", "M3 - Called & positive"));
                spinner_M_LevelList.add(new LevelsModel("M2", "M2 - Did not visit & Postponed"));
                spinner_M_LevelList.add(new LevelsModel("M1", "M1 - Did not visit & not interested"));
                spinner_M_LevelList.add(new LevelsModel("M7", "M7- Enrolled"));
            }
        }
        if (USER_TEAM.equals("SR")) {
            if (spinner_M_LevelList != null) {
                spinner_M_LevelList.clear();
            }
            spinner_M_LevelList = new ArrayList<>();
            spinner_M_LevelList.add(new LevelsModel("M10", "M10 - Alumini (30 days)"));
            spinner_M_LevelList.add(new LevelsModel("M9", "M9 - One exam cleared (30 days)"));
            spinner_M_LevelList.add(new LevelsModel("M9-", "M9- - Drop-out (after clearing an exam) (once every 3 months)"));
            spinner_M_LevelList.add(new LevelsModel("M8+", "M8+ - Exam Registrations"));
            spinner_M_LevelList.add(new LevelsModel("M8", "M8 - Evaluation (30 days)"));
            spinner_M_LevelList.add(new LevelsModel("M8-", "M8- - Drop-out (after evaluation) (once every 3 months)"));
            spinner_M_LevelList.add(new LevelsModel("M7D2", "M7D2 - Loan Defaulter"));
            spinner_M_LevelList.add(new LevelsModel("M7D1", "M7D1 - Defaulter"));
            spinner_M_LevelList.add(new LevelsModel("M7#", "M7# - Onboarding Done"));
            spinner_M_LevelList.add(new LevelsModel("M7-JJ", "M7-JJ (Non Miles Jain Enrolled Candidates)"));
            spinner_M_LevelList.add(new LevelsModel("M7X", "M7X - Do Not Ever Call (NFD - Blank)"));
            spinner_M_LevelList.add(new LevelsModel("M7+", "M7+ LMS demo completed (30-60 days)"));
            spinner_M_LevelList.add(new LevelsModel("M7", "M7- Enrolled"));
            spinner_M_LevelList.add(new LevelsModel("M7-", "M7- - Drop-out (30 days)"));
            spinner_M_LevelList.add(new LevelsModel("M6", "M6 - Visited & Ready to Enroll"));
            spinner_M_LevelList.add(new LevelsModel("M5", "M5 - Visited & Positive"));
            spinner_M_LevelList.add(new LevelsModel("M4", "M4 - Visited but Postponed"));
            spinner_M_LevelList.add(new LevelsModel("M4-", "M4- - Visited but not interested"));
            spinner_M_LevelList.add(new LevelsModel("M3+", "M3+ - Called & Coming"));
            spinner_M_LevelList.add(new LevelsModel("M3", "M3 - Called & positive"));
            spinner_M_LevelList.add(new LevelsModel("M2", "M2 - Did not visit & Postponed"));
            spinner_M_LevelList.add(new LevelsModel("M1", "M1 - Did not visit & not interested"));

        }
        myCalendar = Calendar.getInstance();
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_event);
        dialog.setCancelable(true);
        nextTimeLayout = dialog.findViewById(R.id.nextTimeLayout);
        engagement_main_form = dialog.findViewById(R.id.engagement_main_form);
        level_layout_ = dialog.findViewById(R.id.level_layout_);
        latestEngagement = dialog.findViewById(R.id.latestEngagement);
        latestEngagement.setOnClickListener(this);
        latestEngagement.setText(previousEngagement);
        textview_header = dialog.findViewById(R.id.textview_header);
        if (u_levels == null) {
            textview_header.setText(user_name + " - " + m_levels);

        } else {
            textview_header.setText(user_name + " - " + m_levels + " - " + str_U_levels);

        }
        if (Mlevel.equals("M6")) {
            myCalendar.add(Calendar.DAY_OF_MONTH, 1);
            MAX_DATE_PRE_SELECT = 30;
        }
        if (Mlevel.equals("M5") || (Mlevel.equals("M3"))) {
            myCalendar.add(Calendar.DAY_OF_MONTH, 1);
            MAX_DATE_PRE_SELECT = 60;
        }
        if (Mlevel.equals("M3++") || (Mlevel.equals("M3+"))) {
            myCalendar.add(Calendar.DAY_OF_MONTH, 1);
            MAX_DATE_PRE_SELECT = 40;
        }

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        appconpact_spinner_M_levels = dialog.findViewById(R.id.appconpact_spinner_M_levels);
        appconpact_spinner_U_levels = dialog.findViewById(R.id.appconpact_spinner_U_levels);
        appconpact_spinner_connectionstatus = dialog.findViewById(R.id.appconpact_spinner_connectionstatus);
        level_M_layout = dialog.findViewById(R.id.level_M_layout);
        level_U_layout = dialog.findViewById(R.id.level_U_layout);
        interested_working = dialog.findViewById(R.id.interested_working);
        //my self
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
        edt_work_Experience = dialog.findViewById(R.id.edt_work_Experience);
        appconpact_spinner_interested_working = dialog.findViewById(R.id.appconpact_spinner_interested_working);


        ArrayAdapter<String> appconpact_spinner_interested_working_Adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinner_interested_workingArrayList);
        appconpact_spinner_interested_working_Adapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        appconpact_spinner_interested_working.setAdapter(appconpact_spinner_interested_working_Adapter);
        appconpact_spinner_looking_job = dialog.findViewById(R.id.appconpact_spinner_looking_job);


        ArrayAdapter<String> appconpact_spinner_looking_job_Adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerLookingJob_workingArrayList);
        appconpact_spinner_looking_job_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        appconpact_spinner_graduation_Year = dialog.findViewById(R.id.appconpact_spinner_graduation_Year);

        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear + 3; i >= 1995; i--) {
            years.add("");
            years.add(Integer.toString(i));
        }

//        Log.d("Testing M Leave", m_levels);
//        Log.d("Testing U Leave", str_U_levels);

//        int selectedIndex = getIndex(appconpact_spinner_M_levels, m_levels);
//        Log.d("Testing U Leave Index", String.valueOf(selectedIndex));

//        YourCustomAdapter customAdapter = new YourCustomAdapter(context, R.layout.your_item_layout, items);
//        Spinner spinner = findViewById(R.id.your_spinner_id);
//        spinner.setAdapter(customAdapter);
        levelsCustomAdapter = new LevelsCustomAdapter(getContext(), R.layout.listitems_layout, R.id.levels_items, spinner_M_LevelList);
        appconpact_spinner_M_levels.setAdapter(levelsCustomAdapter);
        levelsCustomAdapter.notifyDataSetChanged();

        levelsCustomAdapter = new LevelsCustomAdapter(getContext(), R.layout.listitems_layout, R.id.levels_items, spinner_U_LevelList);
        appconpact_spinner_U_levels.setAdapter(levelsCustomAdapter);
        levelsCustomAdapter.notifyDataSetChanged();

//        int position = levelsCustomAdapter.getPosition();

//        if (position != -1) {
//            appconpact_spinner_M_levels.setSelection(position);
//        } else {
//            // Handle the case where the item is not found in the adapter's data
//        }

//        for (int i = 0; i < spinner_M_LevelList.size(); i++) {
//            if (spinner_M_LevelList.get(i).getLevelCode().equalsIgnoreCase(m_levels)) {
//                appconpact_spinner_M_levels.setSelection(i);
//                break;
//            }
//        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, years);
        appconpact_spinner_graduation_Year.setAdapter(adapter);
        if (spinner_M_LevelList.size() > 0) {
            spinner_M_LevelList.clear();
            spinner_M_LevelList = new ArrayList<>();
            if (Mlevel.equals("M1") || (Mlevel.equals("M2")) || (Mlevel.equals("M3") || (Mlevel.equals("M3+")) || (Mlevel.equals("M3++") || (Mlevel.equals(" "))))) {
                //1
                spinner_M_LevelList.add(new LevelsModel("M1", "M1 :Did not visit & not interested"));
                spinner_M_LevelList.add(new LevelsModel("M2", "M2 :Did not visit & Postponed"));
                spinner_M_LevelList.add(new LevelsModel("M3", "M3 :Called & positive"));
                spinner_M_LevelList.add(new LevelsModel("M3+", "M3+ :Called & Coming"));
                spinner_M_LevelList.add(new LevelsModel("M3++", "M3++ :Ready to enroll - Not visited"));
                //2
                spinner_M_LevelList.add(new LevelsModel("M4", "M4 :Visited but Postponed"));
                spinner_M_LevelList.add(new LevelsModel("M4-", "M4- :Visited but not interested"));
                spinner_M_LevelList.add(new LevelsModel("M5", "M5 :Visited & Positive"));
                spinner_M_LevelList.add(new LevelsModel("M6", "M6 :Visited & Ready to Enroll"));
                //3
                spinner_M_LevelList.add(new LevelsModel("M7", "M7 :Enrolled"));
                //4
                spinner_M_LevelList.add(new LevelsModel("M7-", "M7- :Drop-out (30 days),"));
                spinner_M_LevelList.add(new LevelsModel("M7-JJ", "M7-JJ :Non Miles Jain Enrolled Candidates (30-60 days),"));
                spinner_M_LevelList.add(new LevelsModel("M7X", "M7X :Do Not Ever Call (NFD - Blank),"));
                //5
                spinner_M_LevelList.add(new LevelsModel("M7+", "M7+ :LMS demo completed (30-60 days),"));
//                spinner_M_LevelList.add(new LevelsModel("M7#", "M7# :Onboarding Done (30-60 days),"));
//                spinner_M_LevelList.add(new LevelsModel("M8", "M8 :Evaluation (30 days),"));
//                //6
//                spinner_M_LevelList.add(new LevelsModel("M8+", "M8+ :Exam Registration,"));
//                //7
//                spinner_M_LevelList.add(new LevelsModel("M8-", "M8- :Drop-out (after evaluation) (once every 3 months),"));
//                //8
//                spinner_M_LevelList.add(new LevelsModel("M9", "M9 : One exam cleared (30 days)"));
//                spinner_M_LevelList.add(new LevelsModel("M9-", "M9- : Drop-out (after clearing an exam) (once every 3 months),"));
//                //9
//                spinner_M_LevelList.add(new LevelsModel("M10", "M10 : Alumini (30 days)"));
//                //10
//                spinner_M_LevelList.add(new LevelsModel("M7-IR", "M7-IR"));
//                spinner_M_LevelList.add(new LevelsModel("M7-INT", "M7-INT"));
//                spinner_M_LevelList.add(new LevelsModel("M7D1", "M7D1 : Defaulter,"));
//                spinner_M_LevelList.add(new LevelsModel("M7D2", "M7D2 : Loan Defaulter,"));

            }
            if (Mlevel.equals("M4") || (Mlevel.equals("M4-")) || (Mlevel.equals("M5")) || (Mlevel.equals("M6"))) {
                //2
                spinner_M_LevelList.add(new LevelsModel("M4", "M4 :Visited but Postponed"));
                spinner_M_LevelList.add(new LevelsModel("M4-", "M4- :Visited but not interested"));
                spinner_M_LevelList.add(new LevelsModel("M5", "M5 :Visited & Positive"));
                spinner_M_LevelList.add(new LevelsModel("M6", "M6 :Visited & Ready to Enroll"));
                //3
                spinner_M_LevelList.add(new LevelsModel("M7", "M7 :Enrolled"));
//                //4
//                spinner_M_LevelList.add(new LevelsModel("M7-", "M7- :Drop-out (30 days),"));
//                spinner_M_LevelList.add(new LevelsModel("M7-JJ", "M7-JJ :Non Miles Jain Enrolled Candidates (30-60 days),"));
//                spinner_M_LevelList.add(new LevelsModel("M7X", "M7X :Do Not Ever Call (NFD - Blank),"));
//                //5
//                spinner_M_LevelList.add(new LevelsModel("M7+", "M7+ :LMS demo completed (30-60 days),"));
//                spinner_M_LevelList.add(new LevelsModel("M7#", "M7# :Onboarding Done (30-60 days),"));
//                spinner_M_LevelList.add(new LevelsModel("M8", "M8 :Evaluation (30 days),"));
//                //6
//                spinner_M_LevelList.add(new LevelsModel("M8+", "M8+ :Exam Registration,"));
//                //7
//                spinner_M_LevelList.add(new LevelsModel("M8-", "M8- :Drop-out (after evaluation) (once every 3 months),"));
//                //8
//                spinner_M_LevelList.add(new LevelsModel("M9", "M9 : One exam cleared (30 days)"));
//                spinner_M_LevelList.add(new LevelsModel("M9-", "M9- : Drop-out (after clearing an exam) (once every 3 months),"));
//                //9
//                spinner_M_LevelList.add(new LevelsModel("M10", "M10 : Alumini (30 days)"));
//                //10
//                spinner_M_LevelList.add(new LevelsModel("M7-IR", "M7-IR"));
//                spinner_M_LevelList.add(new LevelsModel("M7-INT", "M7-INT"));
//                spinner_M_LevelList.add(new LevelsModel("M7D1", "M7D1 : Defaulter,"));
//                spinner_M_LevelList.add(new LevelsModel("M7D2", "M7D2 : Loan Defaulter,"));
            }
            if (Mlevel.equals("M7")) {
                //3
                spinner_M_LevelList.add(new LevelsModel("M7", "M7 :Enrolled"));
                //4
                spinner_M_LevelList.add(new LevelsModel("M7-", "M7- :Drop-out (30 days),"));
                spinner_M_LevelList.add(new LevelsModel("M7-JJ", "M7-JJ :Non Miles Jain Enrolled Candidates (30-60 days),"));
                spinner_M_LevelList.add(new LevelsModel("M7X", "M7X :Do Not Ever Call (NFD - Blank),"));
                //5
                spinner_M_LevelList.add(new LevelsModel("M7+", "M7+ :LMS demo completed (30-60 days),"));
                spinner_M_LevelList.add(new LevelsModel("M7#", "M7# :Onboarding Done (30-60 days),"));
                spinner_M_LevelList.add(new LevelsModel("M8", "M8 :Evaluation (30 days),"));
                //6
                spinner_M_LevelList.add(new LevelsModel("M8+", "M8+ :Exam Registration,"));
                //7
                spinner_M_LevelList.add(new LevelsModel("M8-", "M8- :Drop-out (after evaluation) (once every 3 months),"));
                //8
                spinner_M_LevelList.add(new LevelsModel("M9", "M9 : One exam cleared (30 days)"));
                spinner_M_LevelList.add(new LevelsModel("M9-", "M9- : Drop-out (after clearing an exam) (once every 3 months),"));
                //9
                spinner_M_LevelList.add(new LevelsModel("M10", "M10 : Alumini (30 days)"));
                //10
                spinner_M_LevelList.add(new LevelsModel("M7-IR", "M7-IR"));
                spinner_M_LevelList.add(new LevelsModel("M7-INT", "M7-INT"));
                spinner_M_LevelList.add(new LevelsModel("M7D1", "M7D1 : Defaulter,"));
                spinner_M_LevelList.add(new LevelsModel("M7D2", "M7D2 : Loan Defaulter,"));

            }
            if (Mlevel.equals("M7-") || (Mlevel.equals("M7-JJ") || (Mlevel.equals("M7X")))) {
                //4
                spinner_M_LevelList.add(new LevelsModel("M7-", "M7- :Drop-out (30 days),"));
                spinner_M_LevelList.add(new LevelsModel("M7-JJ", "M7-JJ :Non Miles Jain Enrolled Candidates (30-60 days),"));
                spinner_M_LevelList.add(new LevelsModel("M7X", "M7X :Do Not Ever Call (NFD - Blank),"));
                //5
                spinner_M_LevelList.add(new LevelsModel("M7+", "M7+ :LMS demo completed (30-60 days),"));
                spinner_M_LevelList.add(new LevelsModel("M7#", "M7# :Onboarding Done (30-60 days),"));
                spinner_M_LevelList.add(new LevelsModel("M8", "M8 :Evaluation (30 days),"));
                //6
                spinner_M_LevelList.add(new LevelsModel("M8+", "M8+ :Exam Registration,"));
                //7
                spinner_M_LevelList.add(new LevelsModel("M8-", "M8- :Drop-out (after evaluation) (once every 3 months),"));
                //8
                spinner_M_LevelList.add(new LevelsModel("M9", "M9 : One exam cleared (30 days)"));
                spinner_M_LevelList.add(new LevelsModel("M9-", "M9- : Drop-out (after clearing an exam) (once every 3 months),"));
                //9
                spinner_M_LevelList.add(new LevelsModel("M10", "M10 : Alumini (30 days)"));
                //10
                spinner_M_LevelList.add(new LevelsModel("M7-IR", "M7-IR"));
                spinner_M_LevelList.add(new LevelsModel("M7-INT", "M7-INT"));
                spinner_M_LevelList.add(new LevelsModel("M7D1", "M7D1 : Defaulter,"));
                spinner_M_LevelList.add(new LevelsModel("M7D2", "M7D2 : Loan Defaulter,"));
            }
            if (Mlevel.equals("M7+") || (Mlevel.equals("M7#")) || (Mlevel.equals("M8"))) {
                //5
                spinner_M_LevelList.add(new LevelsModel("M7+", "M7+ :LMS demo completed (30-60 days),"));
                spinner_M_LevelList.add(new LevelsModel("M7#", "M7# :Onboarding Done (30-60 days),"));
                spinner_M_LevelList.add(new LevelsModel("M8", "M8 :Evaluation (30 days),"));
                //6
                spinner_M_LevelList.add(new LevelsModel("M8+", "M8+ :Exam Registration,"));
                //7
                spinner_M_LevelList.add(new LevelsModel("M8-", "M8- :Drop-out (after evaluation) (once every 3 months),"));
                //8
                spinner_M_LevelList.add(new LevelsModel("M9", "M9 : One exam cleared (30 days)"));
                spinner_M_LevelList.add(new LevelsModel("M9-", "M9- : Drop-out (after clearing an exam) (once every 3 months),"));
                //9
                spinner_M_LevelList.add(new LevelsModel("M10", "M10 : Alumini (30 days)"));
                //10
                spinner_M_LevelList.add(new LevelsModel("M7-IR", "M7-IR"));
                spinner_M_LevelList.add(new LevelsModel("M7-INT", "M7-INT"));
                spinner_M_LevelList.add(new LevelsModel("M7D1", "M7D1 : Defaulter,"));
                spinner_M_LevelList.add(new LevelsModel("M7D2", "M7D2 : Loan Defaulter,"));
            }
            if (Mlevel.equals("M8+")) {
                //6

                spinner_M_LevelList.add(new LevelsModel("M8+", "M8+ :Exam Registration,"));
                //7
                spinner_M_LevelList.add(new LevelsModel("M8-", "M8- :Drop-out (after evaluation) (once every 3 months),"));
                //8
                spinner_M_LevelList.add(new LevelsModel("M9", "M9 : One exam cleared (30 days)"));
                spinner_M_LevelList.add(new LevelsModel("M9-", "M9- : Drop-out (after clearing an exam) (once every 3 months),"));
                //9
                spinner_M_LevelList.add(new LevelsModel("M10", "M10 : Alumini (30 days)"));
                //10
                spinner_M_LevelList.add(new LevelsModel("M7-IR", "M7-IR"));
                spinner_M_LevelList.add(new LevelsModel("M7-INT", "M7-INT"));
                spinner_M_LevelList.add(new LevelsModel("M7D1", "M7D1 : Defaulter,"));
                spinner_M_LevelList.add(new LevelsModel("M7D2", "M7D2 : Loan Defaulter,"));
            }
            if (Mlevel.equals("M8-")) {
                //7
                spinner_M_LevelList.add(new LevelsModel("M8-", "M8- :Drop-out (after evaluation) (once every 3 months),"));
                //8
                spinner_M_LevelList.add(new LevelsModel("M9", "M9 : One exam cleared (30 days)"));
                spinner_M_LevelList.add(new LevelsModel("M9-", "M9- : Drop-out (after clearing an exam) (once every 3 months),"));
                //9
                spinner_M_LevelList.add(new LevelsModel("M10", "M10 : Alumini (30 days)"));
                //10
                spinner_M_LevelList.add(new LevelsModel("M7-IR", "M7-IR"));
                spinner_M_LevelList.add(new LevelsModel("M7-INT", "M7-INT"));
                spinner_M_LevelList.add(new LevelsModel("M7D1", "M7D1 : Defaulter,"));
                spinner_M_LevelList.add(new LevelsModel("M7D2", "M7D2 : Loan Defaulter,"));
            }
            if (Mlevel.equals("M9") || (Mlevel.equals("M9-"))) {
                //8
                spinner_M_LevelList.add(new LevelsModel("M9", "M9 : One exam cleared (30 days)"));
                spinner_M_LevelList.add(new LevelsModel("M9-", "M9- : Drop-out (after clearing an exam) (once every 3 months),"));
                //9
                spinner_M_LevelList.add(new LevelsModel("M10", "M10 : Alumini (30 days)"));
                //10
                spinner_M_LevelList.add(new LevelsModel("M7-IR", "M7-IR"));
                spinner_M_LevelList.add(new LevelsModel("M7-INT", "M7-INT"));
                spinner_M_LevelList.add(new LevelsModel("M7D1", "M7D1 : Defaulter,"));
                spinner_M_LevelList.add(new LevelsModel("M7D2", "M7D2 : Loan Defaulter,"));
            }
            if (Mlevel.equals("M10")) {
                //9
                spinner_M_LevelList.add(new LevelsModel("M10", "M10 : Alumini (30 days)"));
                //10
                spinner_M_LevelList.add(new LevelsModel("M7-IR", "M7-IR"));
                spinner_M_LevelList.add(new LevelsModel("M7-INT", "M7-INT"));
                spinner_M_LevelList.add(new LevelsModel("M7D1", "M7D1 : Defaulter,"));
                spinner_M_LevelList.add(new LevelsModel("M7D2", "M7D2 : Loan Defaulter,"));
            }
            if (Mlevel.equals("M7-IR") || (Mlevel.equals("M7-INT")) || (Mlevel.equals("M7D1")) || (Mlevel.equals("M7D2"))) {
                //10
                spinner_M_LevelList.add(new LevelsModel("M7-IR", "M7-IR"));
                spinner_M_LevelList.add(new LevelsModel("M7-INT", "M7-INT"));
                spinner_M_LevelList.add(new LevelsModel("M7D1", "M7D1 : Defaulter,"));
                spinner_M_LevelList.add(new LevelsModel("M7D2", "M7D2 : Loan Defaulter,"));
            }
//            if (levels.equals("M7")) {
//                spinnerLevelList.add(new LevelsModel("M7", "M7- Enrolled"));
//            }
//            if (levels.equals("M6") || levels.equals("L6")) {
//                spinnerLevelList.add(new LevelsModel("M6", "M6 - Visited & Ready to Enroll"));
//                spinnerLevelList.add(new LevelsModel("M5", "M5 - Visited & Positive"));
//            }
//            if (levels.equals("M5") || levels.equals("L5")) {
//                spinnerLevelList.add(new LevelsModel("M5", "M5 - Visited & Positive"));
//                spinnerLevelList.add(new LevelsModel("M4", "M4 - Visited but Postponed"));
//
//            }
//            if (levels.equals("M4") || levels.equals("L4")) {
//                spinnerLevelList.add(new LevelsModel("M4", "M4 - Visited but Postponed"));
//                spinnerLevelList.add(new LevelsModel("M4-", "M4- - Visited but not interested"));
//            }
//            if (levels.equals("M4-") || levels.equals("L4-")) {
//                spinnerLevelList.add(new LevelsModel("M4-", "M4- - Visited but not interested"));
//                spinnerLevelList.add(new LevelsModel("M3+", "M3+ - Called & Coming"));
//            }
//            if (levels.equals("M3+") || levels.equals("L3+")) {
//                spinnerLevelList.add(new LevelsModel("M3+", "M3+ - Called & Coming"));
//                spinnerLevelList.add(new LevelsModel("M3", "M3 - Called & positive"));
//            }
//            if (levels.equals("M3") || levels.equals("L3")) {
//                spinnerLevelList.add(new LevelsModel("M3", "M3 - Called & positive"));
//                spinnerLevelList.add(new LevelsModel("M2", "M2 - Did not visit & Postponed"));
//            }
//            if (levels.equals("M2") || levels.equals("L2")) {
//                spinnerLevelList.add(new LevelsModel("M2", "M2 - Did not visit & Postponed"));
//                spinnerLevelList.add(new LevelsModel("M1", "M1 - Did not visit & not interested"));
//            }
//            if (levels.equals("M1") || levels.equals("L1")) {
//                spinnerLevelList.add(new LevelsModel("M1", "M1 - Did not visit & not interested"));
//            }
//            level_layout_.setVisibility(View.VISIBLE);
//            engagement_main_form.setVisibility(View.GONE);
        }

        if (Ulevel == null) {
            //1
            spinner_U_LevelList.add(new LevelsModel("U0", "U0 :Not interested in US/Canda"));
            spinner_U_LevelList.add(new LevelsModel("U1", "U1 :Interest expressed, dropped out"));

            spinner_U_LevelList.add(new LevelsModel("U1+", "U1+ :Interest expressed, yet to graduate"));

            spinner_U_LevelList.add(new LevelsModel("U2", "U2 :Interest expressed, in pipeline (warm)"));
            spinner_U_LevelList.add(new LevelsModel("U3-", "U3- :Postponed"));
            spinner_U_LevelList.add(new LevelsModel("U3", "U3 :Interest expressed, in pipeline (hot)"));
            spinner_U_LevelList.add(new LevelsModel("U3+", "U3+ :Zoom Attended(Very Hot)"));
            spinner_U_LevelList.add(new LevelsModel("U3++", "U3++ :Ready to Enroll"));
            //2
            spinner_U_LevelList.add(new LevelsModel("U5", "U5 :Visited and Very Interested"));
            spinner_U_LevelList.add(new LevelsModel("U6", "U6 :Visited and Ready to Enroll"));
            spinner_U_LevelList.add(new LevelsModel("U4", "U4 :Visited but Postpone/Not Interested"));
            //3
            spinner_U_LevelList.add(new LevelsModel("U7", "U7 :Enrolled (20k)"));
            //4
            spinner_U_LevelList.add(new LevelsModel("U7-", "U7- :Application initiated, dropped out"));
            spinner_U_LevelList.add(new LevelsModel("U7R", "U7R :Refunded 20k"));
            spinner_U_LevelList.add(new LevelsModel("U7+", "U7+ :Application Initiated"));
            spinner_U_LevelList.add(new LevelsModel("U8", "U8 :Application done, I-20 yet to be received"));
            spinner_U_LevelList.add(new LevelsModel("U8+", "U8+ :Conditional Offer Received"));
            spinner_U_LevelList.add(new LevelsModel("U9-", "U9- :I-20 received, Dropped Out"));
            spinner_U_LevelList.add(new LevelsModel("U9", "U9 :I 20 Rec. Yet to apply for Visa"));
            spinner_U_LevelList.add(new LevelsModel("U9+", "U9+ :I 20 Rec. Applied for Visa"));
            spinner_U_LevelList.add(new LevelsModel("U10-", "U10- :Visa Received But Dropped out"));
            spinner_U_LevelList.add(new LevelsModel("U10", "U10 :Visa Received "));
            spinner_U_LevelList.add(new LevelsModel("U11", "U11 :IIM Indore "));
            spinner_U_LevelList.add(new LevelsModel("U11+", "U11+ :Started program in USA "));
            spinner_U_LevelList.add(new LevelsModel("U12", "U12 :Completed program in USA "));

        } else {
            if (Ulevel.equals("U0") || (Ulevel.equals("U1")) || (Ulevel.equals("U2")) || (Ulevel.equals("U3-")) ||
                    (Ulevel.equals("U3")) || (Ulevel.equals("U3+")) || (Ulevel.equals("U3++"))) {
                spinner_U_LevelList = new ArrayList<>();
                if (spinner_U_LevelList != null) {
//                    spinner_U_LevelList.clear();
                }
                //1
                spinner_U_LevelList.add(new LevelsModel("U0", "U0 :Not interested in US/Canda"));
                spinner_U_LevelList.add(new LevelsModel("U1", "U1 :Interest expressed, dropped out"));

                spinner_U_LevelList.add(new LevelsModel("U1+", "U1+ :Interest expressed, yet to graduate"));

                spinner_U_LevelList.add(new LevelsModel("U2", "U2 :Interest expressed, in pipeline (warm)"));
                spinner_U_LevelList.add(new LevelsModel("U3-", "U3- :Postponed"));
                spinner_U_LevelList.add(new LevelsModel("U3", "U3 :Interest expressed, in pipeline (hot)"));
                spinner_U_LevelList.add(new LevelsModel("U3+", "U3+ :Zoom Attended(Very Hot)"));
                spinner_U_LevelList.add(new LevelsModel("U3++", "U3++ :Ready to Enroll"));
                //2
                spinner_U_LevelList.add(new LevelsModel("U5", "U5 :Visited and Very Interested"));
                spinner_U_LevelList.add(new LevelsModel("U6", "U6 :Visited and Ready to Enroll"));
                spinner_U_LevelList.add(new LevelsModel("U4", "U4 :Visited but Postpone/Not Interested"));
                //3
                spinner_U_LevelList.add(new LevelsModel("U7", "U7 :Enrolled (20k)"));
                spinner_U_LevelList.add(new LevelsModel("U7-", "U7- :Application initiated, dropped out"));
                spinner_U_LevelList.add(new LevelsModel("U7R", "U7R :Refunded 20k"));
                spinner_U_LevelList.add(new LevelsModel("U7+", "U7+ :Application Initiated"));
                spinner_U_LevelList.add(new LevelsModel("U8", "U8 :Application done, I-20 yet to be received"));
                spinner_U_LevelList.add(new LevelsModel("U8+", "U8+ :Conditional Offer Received"));
                spinner_U_LevelList.add(new LevelsModel("U9-", "U9- :I-20 received, Dropped Out"));
                spinner_U_LevelList.add(new LevelsModel("U9", "U9 :I 20 Rec. Yet to apply for Visa"));
                spinner_U_LevelList.add(new LevelsModel("U9+", "U9+ :I 20 Rec. Applied for Visa"));
                spinner_U_LevelList.add(new LevelsModel("U10-", "U10- :Visa Received But Dropped out"));
                spinner_U_LevelList.add(new LevelsModel("U10", "U10 :Visa Received "));
                spinner_U_LevelList.add(new LevelsModel("U11", "U11 :IIM Indore "));
                spinner_U_LevelList.add(new LevelsModel("U11+", "U11+ :Started program in USA "));
                spinner_U_LevelList.add(new LevelsModel("U12", "U12 :Completed program in USA "));
            }
            if (Ulevel.equals("U5") || (Ulevel.equals("U6")) || (Ulevel.equals("U4"))) {
                spinner_U_LevelList = new ArrayList<>();
                if (spinner_U_LevelList != null) {
//                    spinner_U_LevelList.clear();
                }
                spinner_U_LevelList.add(new LevelsModel("U0", "U0 :Not interested in US/Canda"));
                spinner_U_LevelList.add(new LevelsModel("U1", "U1 :Interest expressed, dropped out"));

                spinner_U_LevelList.add(new LevelsModel("U1+", "U1+ :Interest expressed, yet to graduate"));

                spinner_U_LevelList.add(new LevelsModel("U2", "U2 :Interest expressed, in pipeline (warm)"));
                spinner_U_LevelList.add(new LevelsModel("U3-", "U3- :Postponed"));
                spinner_U_LevelList.add(new LevelsModel("U3", "U3 :Interest expressed, in pipeline (hot)"));
                spinner_U_LevelList.add(new LevelsModel("U3+", "U3+ :Zoom Attended(Very Hot)"));
                spinner_U_LevelList.add(new LevelsModel("U3++", "U3++ :Ready to Enroll"));

                //2
                spinner_U_LevelList.add(new LevelsModel("U5", "U5 :Visited and Very Interested"));
                spinner_U_LevelList.add(new LevelsModel("U6", "U6 :Visited and Ready to Enroll"));
                spinner_U_LevelList.add(new LevelsModel("U4", "U4 :Visited but Postpone/Not Interested"));
                //3
                spinner_U_LevelList.add(new LevelsModel("U7", "U7 :Enrolled (20k)"));
                spinner_U_LevelList.add(new LevelsModel("U7-", "U7- :Application initiated, dropped out"));
                spinner_U_LevelList.add(new LevelsModel("U7R", "U7R :Refunded 20k"));
                spinner_U_LevelList.add(new LevelsModel("U7+", "U7+ :Application Initiated"));
                spinner_U_LevelList.add(new LevelsModel("U8", "U8 :Application done, I-20 yet to be received"));
                spinner_U_LevelList.add(new LevelsModel("U8+", "U8+ :Conditional Offer Received"));
                spinner_U_LevelList.add(new LevelsModel("U9-", "U9- :I-20 received, Dropped Out"));
                spinner_U_LevelList.add(new LevelsModel("U9", "U9 :I 20 Rec. Yet to apply for Visa"));
                spinner_U_LevelList.add(new LevelsModel("U9+", "U9+ :I 20 Rec. Applied for Visa"));
                spinner_U_LevelList.add(new LevelsModel("U10-", "U10- :Visa Received But Dropped out"));
                spinner_U_LevelList.add(new LevelsModel("U10", "U10 :Visa Received "));
                spinner_U_LevelList.add(new LevelsModel("U11", "U11 :IIM Indore "));
                spinner_U_LevelList.add(new LevelsModel("U11+", "U11+ :Started program in USA "));
                spinner_U_LevelList.add(new LevelsModel("U12", "U12 :Completed program in USA "));
            }
            if (Ulevel.equals("U7") || (Ulevel.equals("U7-") || (Ulevel.equals("U7R")) || (Ulevel.equals("U4R")) || (Ulevel.equals("U7+")) || (Ulevel.equals("U8")) || (Ulevel.equals("U8+"))
                    || (Ulevel.equals("U9-")) || (Ulevel.equals("U9")) || (Ulevel.equals("U9+")) || (Ulevel.equals("U10-")) || (Ulevel.equals("U10"))
                    || (Ulevel.equals("U11")) || (Ulevel.equals("U11+")) || (Ulevel.equals("U12")) || (Ulevel.equals("U4R")))) {
                spinner_U_LevelList = new ArrayList<>();
                if (spinner_U_LevelList != null) {
//                    spinner_U_LevelList.clear();
                }
                //1
                spinner_U_LevelList.add(new LevelsModel("U0", "U0 :Not interested in US/Canda"));
                spinner_U_LevelList.add(new LevelsModel("U1", "U1 :Interest expressed, dropped out"));

                spinner_U_LevelList.add(new LevelsModel("U1+", "U1+ :Interest expressed, yet to graduate"));

                spinner_U_LevelList.add(new LevelsModel("U2", "U2 :Interest expressed, in pipeline (warm)"));
                spinner_U_LevelList.add(new LevelsModel("U3-", "U3- :Postponed"));
                spinner_U_LevelList.add(new LevelsModel("U3", "U3 :Interest expressed, in pipeline (hot)"));
                spinner_U_LevelList.add(new LevelsModel("U3+", "U3+ :Zoom Attended(Very Hot)"));
                spinner_U_LevelList.add(new LevelsModel("U3++", "U3++ :Ready to Enroll"));
                //2
                spinner_U_LevelList.add(new LevelsModel("U5", "U5 :Visited and Very Interested"));
                spinner_U_LevelList.add(new LevelsModel("U6", "U6 :Visited and Ready to Enroll"));
                spinner_U_LevelList.add(new LevelsModel("U4", "U4 :Visited but Postpone/Not Interested"));

                //3
                spinner_U_LevelList.add(new LevelsModel("U7", "U7 :Enrolled (20k)"));
                spinner_U_LevelList.add(new LevelsModel("U7-", "U7- :Application initiated, dropped out"));
                spinner_U_LevelList.add(new LevelsModel("U7R", "U7R :Refunded 20k"));
                spinner_U_LevelList.add(new LevelsModel("U7+", "U7+ :Application Initiated"));
                spinner_U_LevelList.add(new LevelsModel("U8", "U8 :Application done, I-20 yet to be received"));
                spinner_U_LevelList.add(new LevelsModel("U8+", "U8+ :Conditional Offer Received"));
                spinner_U_LevelList.add(new LevelsModel("U9-", "U9- :I-20 received, Dropped Out"));
                spinner_U_LevelList.add(new LevelsModel("U9", "U9 :I 20 Rec. Yet to apply for Visa"));
                spinner_U_LevelList.add(new LevelsModel("U9+", "U9+ :I 20 Rec. Applied for Visa"));
                spinner_U_LevelList.add(new LevelsModel("U10-", "U10- :Visa Received But Dropped out"));
                spinner_U_LevelList.add(new LevelsModel("U10", "U10 :Visa Received "));
                spinner_U_LevelList.add(new LevelsModel("U11", "U11 :IIM Indore "));
                spinner_U_LevelList.add(new LevelsModel("U11+", "U11+ :Started program in USA "));
                spinner_U_LevelList.add(new LevelsModel("U12", "U12 :Completed program in USA "));
            }


        }


//        ArrayAdapter<LevelsModel> LevelsModeladapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, spinner_U_LevelList);
//        LevelsModeladapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        appconpact_spinner_U_levels.setAdapter(adapter);


        levelsCustomAdapter = new LevelsCustomAdapter(getContext(), R.layout.listitems_layout, R.id.levels_items, spinner_M_LevelList);
        appconpact_spinner_M_levels.setAdapter(levelsCustomAdapter);
        levelsCustomAdapter.notifyDataSetChanged();

        levelsCustomAdapter = new LevelsCustomAdapter(getContext(), R.layout.listitems_layout, R.id.levels_items, spinner_U_LevelList);
        appconpact_spinner_U_levels.setAdapter(levelsCustomAdapter);
        levelsCustomAdapter.notifyDataSetChanged();

        interested_Layout = dialog.findViewById(R.id.interested_Layout);
//        current_location_layout = dialog.findViewById(R.id.current_location_layout);
//        edt_current_location = dialog.findViewById(R.id.edt_current_location);

//        current_location.getTe
        add_engagement_progress = dialog.findViewById(R.id.add_engagement_progress);
        responce_radio_group = dialog.findViewById(R.id.responce_radio_group);
        engagement_description = dialog.findViewById(R.id.engagement_description);
        invite_webinar = dialog.findViewById(R.id.invite_webinar);
        invite_webinar.setOnClickListener(this);
        iimlfa_checked = dialog.findViewById(R.id.iimlfa_checked);
        iimlsf_checked = dialog.findViewById(R.id.iimlsf_checked);
        CFA_checked = dialog.findViewById(R.id.CFA_checked);
        FRM_checked = dialog.findViewById(R.id.FRM_checked);
        USP_checked = dialog.findViewById(R.id.USP_checked);


        cpaCheckbox = dialog.findViewById(R.id.cpa_checked);
        cmaCheckbox = dialog.findViewById(R.id.cma_checked);
        CPA_AA_checked = dialog.findViewById(R.id.CPA_AA_checked);
        engagement_description.setText("");

        indian_Professional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Select PG Graduate");

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

        edt_work_Experience.addTextChangedListener(new TextWatcher() {
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


        cpaCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CPAChecked = "CPA";
                } else {
                    CPAChecked = " ";
                }
            }
        });
        CFA_checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CFAChecked = "CFA";
                } else {
                    CFAChecked = " ";
                }
            }
        });
        FRM_checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FRMChecked = "FRM";
                } else {
                    FRMChecked = " ";
                }
            }
        });
        USP_checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    USPChecked = "USP";
                } else {
                    USPChecked = " ";
                }
            }
        });
        cmaCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CPMChecked = "CMA";
                } else {
                    CPMChecked = " ";
                }
            }
        });

        iimlfa_checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IIMLFAChecked = "IIML-FT";
                } else {
                    IIMLFAChecked = " ";
                }
            }
        });
        iimlsf_checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IIMLSFChecked = "IIML-SF";
                } else {
                    IIMLSFChecked = " ";
                }
            }
        });


        responce_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.same_response) {
                    Toast.makeText(getContext(), "same Response", Toast.LENGTH_SHORT).show();
                    engagement_description.setText(previousEngagement);
                }
                if (checkedId == R.id.new_) {
                    engagement_description.setText("");
                }
            }
        });


        appconpact_spinner_interested_working.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SELECTED_STATUS_interested_working = appconpact_spinner_interested_working.getSelectedItem().toString();
                Log.d("SELECTED_1", SELECTED_STATUS_interested_working);
                if (SELECTED_STATUS_interested_working.equals("Yes")) {
                    appconpact_spinner_looking_job.setEnabled(false);
                    appconpact_spinner_looking_job.setClickable(false);
                    appconpact_spinner_looking_job.setAdapter(appconpact_spinner_looking_job_Adapter);
                } else if (SELECTED_STATUS_interested_working.equals("No")) {
                    appconpact_spinner_U_levels.setSelection(0);
                    appconpact_spinner_looking_job.setEnabled(true);
                    appconpact_spinner_looking_job.setClickable(true);


                } else if (SELECTED_STATUS_interested_working.equals("Maybe")) {
                    appconpact_spinner_U_levels.setSelection(3);
                    appconpact_spinner_looking_job.setEnabled(true);
                    appconpact_spinner_looking_job.setClickable(true);

                } else if (SELECTED_STATUS_interested_working.equals("Yes but graduating in 2024 or after")) {
                    appconpact_spinner_U_levels.setSelection(2);
                    appconpact_spinner_looking_job.setEnabled(true);
                    appconpact_spinner_looking_job.setClickable(true);

                } else {
                    appconpact_spinner_looking_job.setEnabled(true);
                    appconpact_spinner_looking_job.setClickable(true);
                    appconpact_spinner_looking_job.setAdapter(appconpact_spinner_looking_job_Adapter);
                    appconpact_spinner_U_levels.setSelection(0);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ((Button) dialog.findViewById(R.id.bt_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SELECTED_STATUS.equals("Connected / Discussed") || (SELECTED_STATUS.equals("Connected / Not Interested"))) {
                    if (appconpact_spinner_interested_working.getSelectedItem().equals("Yes")) {


                        if (appconpact_spinner_graduation_Year.getSelectedItem().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Please enter graduation Year", Toast.LENGTH_SHORT).show();

                        } else if (edt_current_location.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Please enter Current Location", Toast.LENGTH_SHORT).show();
                        } else {
                            SubmitCallEngagementForm(dialog);
                        }
                    } else {

                        if (appconpact_spinner_graduation_Year.getSelectedItem().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Please enter graduation Year", Toast.LENGTH_SHORT).show();
                        } else if (edt_current_location.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Please enter Current Location", Toast.LENGTH_SHORT).show();
                        } else if (company_Name.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Please enter your Company name", Toast.LENGTH_SHORT).show();
                        } else if (edt_work_Experience.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Please enter your Work Experience", Toast.LENGTH_SHORT).show();
                        } else if (indian_Professional.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Please select your indian Professional", Toast.LENGTH_SHORT).show();

                        } else if (global_Professional_Qualification.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Please select your global professional qualification", Toast.LENGTH_SHORT).show();
                        } else if (ug_Graduate_Qualification.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Please select your UG qualification", Toast.LENGTH_SHORT).show();
                        } else if (pg_Graduate_Qualification.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Please select your PG qualification", Toast.LENGTH_SHORT).show();

                        } else {
                            SubmitCallEngagementForm(dialog);
                        }

                    }


                } else {
                    SubmitCallEngagementForm(dialog);
                }
            }
        });

        if (!spinner_M_LevelList.isEmpty()) {
            levelsCustomAdapter = new LevelsCustomAdapter(getContext(), R.layout.listitems_layout, R.id.levels_items, spinner_M_LevelList);
            appconpact_spinner_M_levels.setAdapter(levelsCustomAdapter);
            levelsCustomAdapter.notifyDataSetChanged();

        } else {
            Log.d("M Level Empty", "");

        }
        if (!spinner_U_LevelList.isEmpty()) {
            levelsCustomAdapter = new LevelsCustomAdapter(getContext(), R.layout.listitems_layout, R.id.levels_items, spinner_U_LevelList);
            appconpact_spinner_U_levels.setAdapter(levelsCustomAdapter);
            levelsCustomAdapter.notifyDataSetChanged();

        } else {
            Log.d("U Level Empty", "");
        }


        appconpact_spinner_M_levels.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinner_M_LevelList.isEmpty()) {
                    levelsModel = spinner_M_LevelList.get(position);
                }
                User_levels = levelsModel.getLevelCode();
                myCalendar = Calendar.getInstance();
                myCalendar.set(Calendar.YEAR, myCalendar.get(Calendar.YEAR));
                myCalendar.set(Calendar.MONTH, myCalendar.get(Calendar.MONTH));
                MAX_DATE_PRE_SELECT = 0;
                if (User_levels.equals("M6")) {
                    myCalendar.add(Calendar.DAY_OF_MONTH, 1);
                    MAX_DATE_PRE_SELECT = 30;
                }
                if (User_levels.equals("M5") || (User_levels.equals("M3"))) {
                    myCalendar.add(Calendar.DAY_OF_MONTH, 1);
                    MAX_DATE_PRE_SELECT = 60;
                }
                if (User_levels.equals("M3++") || (User_levels.equals("M3+"))) {
                    myCalendar.add(Calendar.DAY_OF_MONTH, 1);
                    MAX_DATE_PRE_SELECT = 40;
                }


                updateLabel();
                filterULevelList(str_U_levels);
//                parent.getAdapter().notifyAll();


            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        appconpact_spinner_U_levels.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (!spinner_U_LevelList.isEmpty()) {
                    levelsModel = spinner_U_LevelList.get(position);
                }
                str_U_levels = levelsModel.getLevelCode();
                myCalendar = Calendar.getInstance();
                myCalendar.set(Calendar.YEAR, myCalendar.get(Calendar.YEAR));
                myCalendar.set(Calendar.MONTH, myCalendar.get(Calendar.MONTH));
                MAX_DATE_PRE_SELECT = 0;
                if (!SELECTED_STATUS.equals("")) {

                    if (str_U_levels.equals("U0") || (str_U_levels.equals("U1")) || (str_U_levels.equals("U1+")) || (str_U_levels.equals("U2"))
                            || (str_U_levels.equals("U3-"))
                    ) {
                        spinner_M_LevelList.clear();
                        appconpact_spinner_M_levels.setEnabled(true);

                        spinner_M_LevelList.add(new LevelsModel("M6", "M6 - Visited & Ready to Enroll"));
                        spinner_M_LevelList.add(new LevelsModel("M5", "M5 - Visited & Positive"));
                        spinner_M_LevelList.add(new LevelsModel("M4", "M4 - Visited but Postponed"));
                        spinner_M_LevelList.add(new LevelsModel("M4-", "M4- - Visited but not interested"));
                        spinner_M_LevelList.add(new LevelsModel("M3++", "M3++ - Ready to  enrolled -  not visited"));
                        spinner_M_LevelList.add(new LevelsModel("M3+", "M3+ - Called & Coming"));
                        spinner_M_LevelList.add(new LevelsModel("M3", "M3 - Called & positive"));
                        spinner_M_LevelList.add(new LevelsModel("M2", "M2 - Did not visit & Postponed"));
                        spinner_M_LevelList.add(new LevelsModel("M1", "M1 - Did not visit & not interested"));

                        filterMLevelList(m_levels);

                    } else if (str_U_levels.equals("U3") || (str_U_levels.equals("U3+")) || (str_U_levels.equals("U3++"))
                    ) {
                        spinner_M_LevelList.clear();
                        appconpact_spinner_M_levels.setEnabled(true);

                        spinner_M_LevelList.add(new LevelsModel("M6", "M6 - Visited & Ready to Enroll"));
                        spinner_M_LevelList.add(new LevelsModel("M5", "M5 - Visited & Positive"));
                        spinner_M_LevelList.add(new LevelsModel("M3++", "M3++ - Ready to  enrolled -  not visited"));
                        spinner_M_LevelList.add(new LevelsModel("M3+", "M3+ - Called & Coming"));
                        spinner_M_LevelList.add(new LevelsModel("M3", "M3 - Called & positive"));
//                        appconpact_spinner_M_levels.setSelection(0);
                    } else if (str_U_levels.equals("U5") || (str_U_levels.equals("U6"))
                    ) {
                        spinner_M_LevelList.clear();
                        appconpact_spinner_M_levels.setEnabled(true);

                        spinner_M_LevelList.add(new LevelsModel("M6", "M6 - Visited & Ready to Enroll"));
                        spinner_M_LevelList.add(new LevelsModel("M5", "M5 - Visited & Positive"));
                    } else if (str_U_levels.equals("U4")) {
                        spinner_M_LevelList.clear();
                        appconpact_spinner_M_levels.setEnabled(true);

                        spinner_M_LevelList.add(new LevelsModel("M4", "M4 - Visited but Postponed"));
                        spinner_M_LevelList.add(new LevelsModel("M4-", "M4- - Visited but not interested"));
                        spinner_M_LevelList.add(new LevelsModel("M6", "M6 - Visited & Ready to Enroll"));
                        spinner_M_LevelList.add(new LevelsModel("M5", "M5 - Visited & Positive"));

                    } else if (str_U_levels.equals("U7-") || (str_U_levels.equals("U7R") || (str_U_levels.equals("U7")))) {
                        spinner_M_LevelList.clear();
                        spinner_M_LevelList.add(new LevelsModel("M4", "M4 - Visited but Postponed"));
                        spinner_M_LevelList.add(new LevelsModel("M4-", "M4- - Visited but not interested"));
                        spinner_M_LevelList.add(new LevelsModel("M6", "M6 - Visited & Ready to Enroll"));
                        spinner_M_LevelList.add(new LevelsModel("M5", "M5 - Visited & Positive"));

                    } else if (str_U_levels.equals("U7+") || (str_U_levels.equals("U8")) || (str_U_levels.equals("U8+")) ||
                            (str_U_levels.equals("U9-")) || (str_U_levels.equals("U9")) || (str_U_levels.equals("U9+")) ||
                            (str_U_levels.equals("U10-")) || (str_U_levels.equals("U10")) || (str_U_levels.equals("U11")) ||
                            (str_U_levels.equals("U11+")) ||
                            (str_U_levels.equals("U12"))) {

                        //1
                        spinner_U_LevelList.add(new LevelsModel("U0", "U0 :Not interested in US/Canda"));
                        spinner_U_LevelList.add(new LevelsModel("U1", "U1 :Interest expressed, dropped out"));

                        spinner_U_LevelList.add(new LevelsModel("U1+", "U1+ :Interest expressed, yet to graduate"));

                        spinner_U_LevelList.add(new LevelsModel("U2", "U2 :Interest expressed, in pipeline (warm)"));
                        spinner_U_LevelList.add(new LevelsModel("U3-", "U3- :Postponed"));
                        spinner_U_LevelList.add(new LevelsModel("U3", "U3 :Interest expressed, in pipeline (hot)"));
                        spinner_U_LevelList.add(new LevelsModel("U3+", "U3+ :Zoom Attended(Very Hot)"));
                        spinner_U_LevelList.add(new LevelsModel("U3++", "U3++ :Ready to Enroll"));
                        //2
                        spinner_U_LevelList.add(new LevelsModel("U5", "U5 :Visited and Very Interested"));
                        spinner_U_LevelList.add(new LevelsModel("U6", "U6 :Visited and Ready to Enroll"));
                        spinner_U_LevelList.add(new LevelsModel("U4", "U4 :Visited but Postpone/Not Interested"));
                        //3
                        spinner_U_LevelList.add(new LevelsModel("U7", "U7 :Enrolled (20k)"));
                        //4
                        spinner_U_LevelList.add(new LevelsModel("U7-", "U7- :Application initiated, dropped out"));
                        spinner_U_LevelList.add(new LevelsModel("U7R", "U7R :Refunded 20k"));
                        spinner_U_LevelList.add(new LevelsModel("U7+", "U7+ :Application Initiated"));
                        spinner_U_LevelList.add(new LevelsModel("U8", "U8 :Application done, I-20 yet to be received"));
                        spinner_U_LevelList.add(new LevelsModel("U8+", "U8+ :Conditional Offer Received"));
                        spinner_U_LevelList.add(new LevelsModel("U9-", "U9- :I-20 received, Dropped Out"));
                        spinner_U_LevelList.add(new LevelsModel("U9", "U9 :I 20 Rec. Yet to apply for Visa"));
                        spinner_U_LevelList.add(new LevelsModel("U9+", "U9+ :I 20 Rec. Applied for Visa"));
                        spinner_U_LevelList.add(new LevelsModel("U10-", "U10- :Visa Received But Dropped out"));
                        spinner_U_LevelList.add(new LevelsModel("U10", "U10 :Visa Received "));
                        spinner_U_LevelList.add(new LevelsModel("U11", "U11 :IIM Indore "));
                        spinner_U_LevelList.add(new LevelsModel("U11+", "U11+ :Started program in USA "));
                        spinner_U_LevelList.add(new LevelsModel("U12", "U12 :Completed program in USA "));
//                        appconpact_spinner_M_levels.setEnabled(false);

                    }


                    if (User_levels.equals("M6")) {
                        myCalendar.add(Calendar.DAY_OF_MONTH, 1);
                        MAX_DATE_PRE_SELECT = 30;
                    }
                    if (User_levels.equals("M5") || (User_levels.equals("M3"))) {
                        myCalendar.add(Calendar.DAY_OF_MONTH, 1);
                        MAX_DATE_PRE_SELECT = 60;
                    }
                    if (User_levels.equals("M3++") || (User_levels.equals("M3+"))) {
                        myCalendar.add(Calendar.DAY_OF_MONTH, 1);
                        MAX_DATE_PRE_SELECT = 40;
                    }
                    updateLabel();
                    levelsCustomAdapter.notifyDataSetChanged();
                    appconpact_spinner_M_levels.setSelection(0);

                } else {
                    Log.d("U LEVELS", "NO STATUS SELCTED");
                    level_M_layout.setVisibility(View.GONE);
                    level_U_layout.setVisibility(View.GONE);
                    date_picker_.setEnabled(false);
                    appconpact_spinner_U_levels.setEnabled(false);
                    appconpact_spinner_M_levels.setEnabled(false);
                    appconpact_spinner_interested_working.setEnabled(false);
                    appconpact_spinner_looking_job.setEnabled(false);
                    appconpact_spinner_graduation_Year.setEnabled(false);
                    work_Ex_profiling.setEnabled(false);
                    company_Name.setEnabled(false);
                    edt_work_Experience.setEnabled(false);
                    indian_Professional.setEnabled(false);
                    global_Professional_Qualification.setEnabled(false);
                    ug_Graduate_Qualification.setEnabled(false);
                    pg_Graduate_Qualification.setEnabled(false);


                }

                filterMLevelList(m_levels);


            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });


        if (USER_TEAM.equals("CM")) {
            SpinnerSelection(appconpact_spinner_M_levels, m_levels);
        }
        if (USER_TEAM.equals("SR")) {
            SpinnerForSelection(appconpact_spinner_M_levels, m_levels);
        }
        ArrayAdapter<String> connection_statusAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, ConnectionTypeArrayList);

        // Drop down layout style - list view with radio button
        connection_statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        appconpact_spinner_connectionstatus.setAdapter(connection_statusAdapter);


        appconpact_spinner_connectionstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    nextTimeLayout.setVisibility(View.VISIBLE);
                    SELECTED_STATUS = appconpact_spinner_connectionstatus.getSelectedItem().toString();
                    if (appconpact_spinner_connectionstatus.getSelectedItem().equals("Connected / Discussed")) {
                        level_M_layout.setVisibility(View.VISIBLE);
                        level_U_layout.setVisibility(View.VISIBLE);
                        appconpact_spinner_M_levels.setEnabled(true);
                        appconpact_spinner_U_levels.setEnabled(true);
                    } else if (appconpact_spinner_connectionstatus.getSelectedItem().equals("")) {
                        level_M_layout.setVisibility(View.GONE);
                        level_U_layout.setVisibility(View.GONE);

                    } else {
                        level_M_layout.setVisibility(View.VISIBLE);
                        level_U_layout.setVisibility(View.VISIBLE);
                        appconpact_spinner_M_levels.setEnabled(false);
                        appconpact_spinner_U_levels.setEnabled(false);

                    }
                    if (Mlevel.equals("M6")) {
                        myCalendar.add(Calendar.DAY_OF_MONTH, 1);
                        MAX_DATE_PRE_SELECT = 30;
                    }
                    if (Mlevel.equals("M5") || (Mlevel.equals("M3"))) {
                        myCalendar.add(Calendar.DAY_OF_MONTH, 1);
                        MAX_DATE_PRE_SELECT = 60;
                    }
                    if (Mlevel.equals("M3++") || (Mlevel.equals("M3+"))) {
                        myCalendar.add(Calendar.DAY_OF_MONTH, 1);
                        MAX_DATE_PRE_SELECT = 40;
                    }
                    if ((SELECTED_STATUS.equals("Connected / Discussed"))) {

                        if (SELECTED_STATUS.contains("Connected / Discussed")) {

                            level_M_layout.setVisibility(View.VISIBLE);
                            level_U_layout.setVisibility(View.VISIBLE);
                            appconpact_spinner_M_levels.setEnabled(true);
                            appconpact_spinner_U_levels.setEnabled(true);
                            engagement_main_form.setVisibility(View.VISIBLE);
                            level_layout_.setVisibility(View.VISIBLE);

                            invite_webinar.setVisibility(View.GONE);
                            appconpact_spinner_U_levels.setSelection(5);
                            appconpact_spinner_interested_working.setEnabled(true);
                            appconpact_spinner_looking_job.setEnabled(true);
                            appconpact_spinner_graduation_Year.setEnabled(true);
                            edt_current_location.setEnabled(true);
                            work_Ex_profiling.setEnabled(true);
                            company_Name.setEnabled(true);
                            edt_work_Experience.setEnabled(true);
                            indian_Professional.setEnabled(true);
                            global_Professional_Qualification.setEnabled(true);
                            ug_Graduate_Qualification.setEnabled(true);
                            pg_Graduate_Qualification.setEnabled(true);

                            engagement_description.setText(" ");
                            ConnectionStatus = "CD";
                            interested_Layout.setEnabled(true);
                            date_picker_.setEnabled(true);
                            looking_job.setEnabled(true);


                            if (u_levels.equals("U7R")) {
                                appconpact_spinner_U_levels.setEnabled(false);
                                appconpact_spinner_M_levels.setEnabled(false);
                                date_picker_.setEnabled(false);

                                int _temp1 = 0;

                                for (int i = 0; i < spinner_U_LevelList.size(); i++) {
                                    LevelsModel item = spinner_U_LevelList.get(i);
                                    if (Ulevel != null) {
                                        if (Ulevel.equals(item.getLevelCode())) { // Assuming getCode() gets the code of the LevelsModel
                                            _temp1 = i;
                                            break;
                                        }
                                    }

                                }
                                appconpact_spinner_U_levels.setSelection(_temp1);


                            } else {
                                appconpact_spinner_U_levels.setEnabled(true);
                                appconpact_spinner_M_levels.setEnabled(true);
                            }
                        } else if (SELECTED_STATUS.equals("Connected / Busy")) {

                            appconpact_spinner_U_levels.setEnabled(false);
                            appconpact_spinner_M_levels.setEnabled(false);
                            date_picker_.setEnabled(false);

                            if (Mlevel.equals(null) || Mlevel.isEmpty()) {
                                appconpact_spinner_M_levels.setSelection(2);
                            }
                            if (Ulevel.equals(null) || Ulevel.isEmpty()) {
                                appconpact_spinner_U_levels.setSelection(4);
                            }
                        } else if (SELECTED_STATUS.equals("Connected / Never call back")) {
                            appconpact_spinner_U_levels.setEnabled(false);
                            appconpact_spinner_M_levels.setEnabled(false);
                            date_picker_.setEnabled(false);

                            if (Mlevel.equals(null) || Mlevel.isEmpty()) {
                                appconpact_spinner_M_levels.setSelection(0);
                            }
                            if (Ulevel.equals(null) || Ulevel.isEmpty()) {
                                appconpact_spinner_U_levels.setSelection(0);

                            }
                        } else if (SELECTED_STATUS.equals("Connected / Not Interested")) {
                            appconpact_spinner_U_levels.setEnabled(false);
                            appconpact_spinner_M_levels.setEnabled(false);
                            date_picker_.setEnabled(false);

                            if (Mlevel.equals(null) || Mlevel.isEmpty()) {
                                appconpact_spinner_M_levels.setSelection(0);
                            }
                            if (Ulevel.equals(null) || Ulevel.isEmpty()) {
                                appconpact_spinner_U_levels.setSelection(0);

                            }
                        } else if (SELECTED_STATUS.equals("Busy")) {
                            appconpact_spinner_U_levels.setEnabled(false);
                            appconpact_spinner_M_levels.setEnabled(false);
                            if (Mlevel.equals(null) || Mlevel.isEmpty()) {
                                appconpact_spinner_M_levels.setSelection(2);
                            }
                            if (Ulevel.equals(null) || Ulevel.isEmpty()) {
                                appconpact_spinner_U_levels.setSelection(4);
                            }
                        } else if (SELECTED_STATUS.equals("Not Lifting")) {
                            appconpact_spinner_U_levels.setEnabled(false);
                            appconpact_spinner_M_levels.setEnabled(false);
                            date_picker_.setEnabled(false);

                            if (Mlevel.equals(null) || Mlevel.isEmpty()) {
                                appconpact_spinner_M_levels.setSelection(2);
                            }
                            if (Ulevel.equals(null) || Ulevel.isEmpty()) {
                                appconpact_spinner_U_levels.setSelection(4);
                            }
                        } else if (SELECTED_STATUS.equals("Not Reachable")) {
                            appconpact_spinner_U_levels.setEnabled(false);
                            appconpact_spinner_M_levels.setEnabled(false);
                            date_picker_.setEnabled(false);

                            if (Mlevel.equals(null) || Mlevel.isEmpty()) {
                                appconpact_spinner_M_levels.setSelection(2);
                            }
                            if (Ulevel.equals(null) || Ulevel.isEmpty()) {
                                appconpact_spinner_U_levels.setSelection(4);
                            }
                        } else if (SELECTED_STATUS.equals("Disconnected")) {
                            appconpact_spinner_U_levels.setEnabled(false);
                            appconpact_spinner_M_levels.setEnabled(false);
                            date_picker_.setEnabled(false);

                            if (Mlevel.equals(null) || Mlevel.isEmpty()) {
                                appconpact_spinner_M_levels.setSelection(2);
                            }
                            if (Ulevel.equals(null) || Ulevel.isEmpty()) {
                                appconpact_spinner_U_levels.setSelection(4);
                            }
                        } else if (SELECTED_STATUS.equals("Invalid Number")) {
                            appconpact_spinner_U_levels.setEnabled(false);
                            appconpact_spinner_M_levels.setEnabled(false);
                            date_picker_.setEnabled(false);

                            if (Mlevel.equals(null) || Mlevel.isEmpty()) {
                                appconpact_spinner_M_levels.setSelection(0);
                            }
                            if (Ulevel.equals(null) || Ulevel.isEmpty()) {
                                appconpact_spinner_U_levels.setSelection(0);
                            }
                        } else if (SELECTED_STATUS.equals("Switched Off")) {
                            appconpact_spinner_U_levels.setEnabled(false);
                            appconpact_spinner_M_levels.setEnabled(false);
                            date_picker_.setEnabled(false);

                            if (Mlevel.equals(null) || Mlevel.isEmpty()) {
                                appconpact_spinner_M_levels.setSelection(2);
                            }
                            if (Ulevel.equals(null) || Ulevel.isEmpty()) {
                                appconpact_spinner_U_levels.setSelection(4);
                            }
                        } else if (SELECTED_STATUS.equals("Communication barrier")) {
                            appconpact_spinner_U_levels.setEnabled(false);
                            appconpact_spinner_M_levels.setEnabled(false);
                            date_picker_.setEnabled(false);

                            if (Mlevel.equals(null) || Mlevel.isEmpty()) {
                                appconpact_spinner_M_levels.setSelection(0);
                            }
                            if (Ulevel.equals(null) || Ulevel.isEmpty()) {
                                appconpact_spinner_U_levels.setSelection(0);
                            }
                        } else if (SELECTED_STATUS.equals("Not Educated")) {
                            appconpact_spinner_U_levels.setEnabled(false);
                            appconpact_spinner_M_levels.setEnabled(false);
                            date_picker_.setEnabled(false);

                            if (Mlevel.equals(null) || Mlevel.isEmpty()) {
                                appconpact_spinner_M_levels.setSelection(0);
                            }
                            if (Ulevel.equals(null) || Ulevel.isEmpty()) {
                                appconpact_spinner_U_levels.setSelection(0);
                            }
                        }


                    } else if (SELECTED_STATUS.equals("Busy") || (SELECTED_STATUS.equals("Not Lifting"))
                            || (SELECTED_STATUS.equals("Not Reachable")) || (SELECTED_STATUS.equals("Connected / Busy"))
                            || (SELECTED_STATUS.equals("Disconnected")) || (SELECTED_STATUS.equals("Invalid Number"))
                            || (SELECTED_STATUS.equals("Switched Off")) || (SELECTED_STATUS.equals(" "))
                            || (SELECTED_STATUS.equals("Connected / Never call back")) ||
                            (SELECTED_STATUS.equals("Connected / Wrong number")) ||
                            (SELECTED_STATUS.equals("Connected / Not Interested")) || (SELECTED_STATUS.equals("Communication barrier"))
                            || (SELECTED_STATUS.equals("Not Educated"))) {

                        interested_Layout.setEnabled(false);
                        appconpact_spinner_interested_working.setEnabled(false);
                        looking_job.setEnabled(false);
                        date_picker_.setEnabled(false);

                        appconpact_spinner_U_levels.setEnabled(false);
                        appconpact_spinner_M_levels.setEnabled(false);
                        appconpact_spinner_looking_job.setEnabled(false);
                        appconpact_spinner_graduation_Year.setEnabled(false);
                        edt_current_location.setEnabled(false);
                        work_Ex_profiling.setEnabled(false);
                        company_Name.setEnabled(false);
                        edt_work_Experience.setEnabled(false);
                        indian_Professional.setEnabled(false);
                        global_Professional_Qualification.setEnabled(false);
                        ug_Graduate_Qualification.setEnabled(false);
                        pg_Graduate_Qualification.setEnabled(false);


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
                        LevelsSelected = m_levels;
                        CoursesData = courses;
                    } else {
                        engagement_main_form.setVisibility(View.GONE);
                        //Gone
                        level_layout_.setVisibility(View.VISIBLE);
                        invite_webinar.setVisibility(View.GONE);
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
                        LevelsSelected = m_levels;
                        CoursesData = courses;
                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        selectDatapicker(dialog);
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();
        dialog.getWindow().setAttributes(lp);

        if (courses.contains("CPA")) {
            cpaCheckbox.setChecked(true);
        }
        if (courses.contains("CMA")) {
            cmaCheckbox.setChecked(true);
        }
        if (courses.contains("CPA-AA")) {
            CPA_AA_checked.setChecked(true);
        }
        if (courses.contains("CFA")) {
            CFA_checked.setChecked(true);
        }
        if (courses.contains("FRM")) {
            FRM_checked.setChecked(true);
        }
        if (courses.contains("USP")) {
            USP_checked.setChecked(true);
        }

        if (courses.contains("IIML-FT")) {
            iimlfa_checked.setChecked(true);
        }
        if (courses.contains("IIML-SF")) {
            iimlsf_checked.setChecked(true);
        }


    }

    private void SubmitCallEngagementForm(final Dialog dialog) {

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
        if (isCallValidateForm()) {
            if (CoursesData == null) {
                CoursesData = "CPA";
            }
            if (CoursesData == null) {
                Toast.makeText(getContext(), "Please Select Course Type ", Toast.LENGTH_SHORT).show();
                return;
            }

            if (ConnectionStatus.equals("CD")) {
                if (engagement_description.getText().toString().equals(" ")) {
                    engagement_description.setError("Please fill engagement description..");
                    Toast.makeText(getContext(), "Please fill engagement description..", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if ((!User_levels.equals("M7")) && nextCallTimeStamp == 0) {
                date_picker_.setError("Please select the Date...");
                Toast.makeText(getContext(), "Please select Date", Toast.LENGTH_SHORT).show();


            } else {
                add_engagement_progress.setVisibility(View.VISIBLE);
                String engagement_data = engagement_description.getText().toString().trim();
                apiClient.AddEngagement(ConnectionStatus, "", can_id, person_id, user_name, User_levels, CoursesData, engagement_data, "call", 0, nextCallTimeStamp, " ", 0, "No", batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), VERSION_NUMBER, str_U_levels, "Bearer " + AccessToken, "application/json").enqueue(new Callback<SuccessModel>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                        try {
                            if (response.body() == null) {
                                int statusCode = response.raw().code();
                                if (statusCode > 399 && statusCode < 500) {
                                    SessionLogout(getContext());
                                    add_engagement_progress.setVisibility(View.GONE);
                                }
                                dialog.dismiss();
                                add_engagement_progress.setVisibility(View.GONE);
                            } else {
                                if (response.body().getStatus().equals("success")) {
                                    dialog.dismiss();
                                    add_engagement_progress.setVisibility(View.GONE);
                                    openAlert(response.body().getMessage());
                                } else {
                                    dialog.dismiss();
                                    openAlert(response.body().getMessage());
                                }
                            }

                        } catch (Exception e) {
                            dialog.dismiss();
                            add_engagement_progress.setVisibility(View.GONE);
                            openAlert(String.valueOf(e.getMessage()));
                        }

                    }

                    @Override
                    public void onFailure(Call<SuccessModel> call, Throwable t) {
                        t.printStackTrace();
                        add_engagement_progress.setVisibility(View.GONE);
                        dialog.dismiss();
                        openAlert(String.valueOf(t.getMessage()));
                    }
                });
            }
        }


    }


    private void SessionLogout(Context context) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(ConstantUtills.AccessToken);
        editor.apply();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    private boolean isvalidateForm() {

        if (cpaCheckbox.isChecked() && cmaCheckbox.isChecked()) {
            CoursesData = CPAChecked + "," + CPMChecked;
        } else if (iimlfa_checked.isChecked() && cpaCheckbox.isChecked()) {
            CoursesData = IIMLFAChecked + "," + CPAChecked;
        } else if (iimlfa_checked.isChecked() && cmaCheckbox.isChecked()) {
            CoursesData = IIMLFAChecked + "," + CPMChecked;
        } else if (iimlsf_checked.isChecked() && cpaCheckbox.isChecked()) {
            CoursesData = IIMLSFChecked + "," + CPAChecked;
        } else if (iimlsf_checked.isChecked() && cmaCheckbox.isChecked()) {
            CoursesData = IIMLSFChecked + "," + CPMChecked;
        } else if (iimlsf_checked.isChecked() && iimlfa_checked.isChecked()) {
            CoursesData = IIMLFAChecked + "," + IIMLSFChecked;
        } else if (cpaCheckbox.isChecked()) {
            CoursesData = CPAChecked;
        } else if (cmaCheckbox.isChecked()) {
            CoursesData = CPMChecked;
        } else if (iimlfa_checked.isChecked()) {
            CoursesData = IIMLFAChecked;
        } else if (iimlsf_checked.isChecked()) {
            CoursesData = IIMLSFChecked;
        } else if (CFA_checked.isChecked()) {
            CoursesData = CFAChecked;
        } else if (FRM_checked.isChecked()) {
            CoursesData = FRMChecked;
        } else if (USP_checked.isChecked()) {
            CoursesData = USPChecked;

        }

        return true;
    }


    private boolean isCallValidateForm() {
        if (cpaCheckbox.isChecked() && cmaCheckbox.isChecked()) {
            CoursesData = CPAChecked + "," + CPMChecked;
        } else if (iimlfa_checked.isChecked() && cpaCheckbox.isChecked()) {
            CoursesData = IIMLFAChecked + "," + CPAChecked;
        } else if (iimlfa_checked.isChecked() && cmaCheckbox.isChecked()) {
            CoursesData = IIMLFAChecked + "," + CPMChecked;
        } else if (iimlsf_checked.isChecked() && cpaCheckbox.isChecked()) {
            CoursesData = IIMLSFChecked + "," + CPAChecked;
        } else if (iimlsf_checked.isChecked() && cmaCheckbox.isChecked()) {
            CoursesData = IIMLSFChecked + "," + CPMChecked;
        } else if (iimlsf_checked.isChecked() && iimlfa_checked.isChecked()) {
            CoursesData = IIMLFAChecked + "," + IIMLSFChecked;
        } else if (cpaCheckbox.isChecked()) {
            CoursesData = CPAChecked;
        } else if (cmaCheckbox.isChecked()) {
            CoursesData = CPMChecked;
        } else if (iimlfa_checked.isChecked()) {
            CoursesData = IIMLFAChecked;
        } else if (iimlsf_checked.isChecked()) {
            CoursesData = IIMLSFChecked;
        }

        return true;
    }


    private void selectDatapicker(Dialog dialog) {
        date_picker_ = dialog.findViewById(R.id.date_picker_);
        date_picker_.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_:
                showCustomDialog(user_name, NewLevel, u_levels);
                break;
            case R.id.visit_:
                showCustomDialogForVisit(user_name);
                break;
            case R.id.email_:
                // checkEMailCallBack();
                //showCustomForEmail();
                new AlertForEmails().getEmailsWithPersonId(person_id, getContext(), AccessToken, user_name);
                break;
            case R.id.whatsup_:
               /* Uri uri = Uri.parse("smsto:" + "9885756629");
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                i.putExtra("sms_body", "djghjfg");
                i.putExtra(Intent.EXTRA_TEXT, "I'm the body.");
                i.setPackage("com.whatsapp");
                startActivity(i);*/
                new AlertForMobileNumebrsForWhatsup().getMobileNumberwithPersonId(person_id, getContext(), AccessToken, user_name);


                // showCustomDialogForWhatsUp();
                break;
            case R.id.date_picker_:
                openAlertForDatepicker();
                break;

            case R.id.invite_webinar:
                Toast.makeText(getContext(), "Invite Webinar", Toast.LENGTH_SHORT).show();
                break;

        }

    }

    private void checkEMailCallBack() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"chvenkatakrishna26@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "My Subject");
        String body = "<body class=\"clean-body\" style=\"margin: 0; padding: 0; -webkit-text-size-adjust: 100%; background-color: #D4E1F7;\">\n" +
                "<!--[if IE]><div class=\"ie-browser\"><![endif]-->\n" +
                "<table bgcolor=\"#D4E1F7\" cellpadding=\"0\" cellspacing=\"0\" class=\"nl-container\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; min-width: 320px; Margin: 0 auto; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #D4E1F7; width: 100%;\" valign=\"top\" width=\"100%\">\n" +
                "<tbody>\n" +
                "<tr style=\"vertical-align: top;\" valign=\"top\">\n" +
                "<td style=\"word-break: break-word; vertical-align: top; border-collapse: collapse;\" valign=\"top\">\n" +
                "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td align=\"center\" style=\"background-color:#D4E1F7\"><![endif]-->\n" +
                "<div style=\"background-color:#D4E1F7;\">\n" +
                "<div class=\"block-grid\" data-body-width-father=\"875px\" rel=\"col-num-container-box-father\" style=\"Margin: 0 auto; min-width: 320px; max-width: 875px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FAFAFA;\">\n" +
                "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FAFAFA;\">\n" +
                "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:#D4E1F7;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:875px\"><tr class=\"layout-full-width\" style=\"background-color:#FAFAFA\"><![endif]-->\n" +
                "<!--[if (mso)|(IE)]><td align=\"center\" width=\"875\" style=\"background-color:#FAFAFA;width:875px; border-top: 1px solid transparent; border-left: 1px solid transparent; border-bottom: 1px solid transparent; border-right: 1px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;background-color:#D4E1F7;\"><![endif]-->\n" +
                "<div class=\"col num12\" data-body-width-son=\"873\" rel=\"col-num-container-box-son\" style=\"min-width: 320px; max-width: 875px; display: table-cell; vertical-align: top;\">\n" +
                "<div style=\"background-color:#D4E1F7;width:100% !important;\">\n" +
                "<!--[if (!mso)&(!IE)]><!-->\n" +
                "<div style=\"border-top:1px solid transparent; border-left:1px solid transparent; border-bottom:1px solid transparent; border-right:1px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                "<!--<![endif]-->\n" +
                "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 0px; padding-bottom: 10px; font-family: 'Times New Roman', Georgia, serif\"><![endif]-->\n" +
                "<div style=\"color:#555555;font-family:TimesNewRoman, 'Times New Roman', Times, Beskerville, Georgia, serif;line-height:120%;padding-top:0px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n" +
                "<div style=\"font-family: TimesNewRoman, 'Times New Roman', Times, Beskerville, Georgia, serif; font-size: 12px; line-height: 14px; color: #555555;\">\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"><br/></p>\n" +
                "</div>\n" +
                "</div>\n" +
                "<!--[if mso]></td></tr></table><![endif]-->\n" +
                "<!--[if (!mso)&(!IE)]><!-->\n" +
                "</div>\n" +
                "<!--<![endif]-->\n" +
                "</div>\n" +
                "</div>\n" +
                "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div style=\"background-color:transparent;\">\n" +
                "<div class=\"block-grid\" data-body-width-father=\"875px\" rel=\"col-num-container-box-father\" style=\"Margin: 0 auto; min-width: 320px; max-width: 875px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FAFAFA;\">\n" +
                "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FAFAFA;\">\n" +
                "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:875px\"><tr class=\"layout-full-width\" style=\"background-color:#FAFAFA\"><![endif]-->\n" +
                "<!--[if (mso)|(IE)]><td align=\"center\" width=\"875\" style=\"background-color:#FAFAFA;width:875px; border-top: 1px solid #CBC8C8; border-left: 1px solid #CBC8C8; border-bottom: 0px solid transparent; border-right: 1px solid #CBC8C8;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n" +
                "<div class=\"col num12\" data-body-width-son=\"873\" rel=\"col-num-container-box-son\" style=\"min-width: 320px; max-width: 875px; display: table-cell; vertical-align: top;\">\n" +
                "<div style=\"width:100% !important;\">\n" +
                "<!--[if (!mso)&(!IE)]><!-->\n" +
                "<div style=\"border-top:1px solid #CBC8C8; border-left:1px solid #CBC8C8; border-bottom:0px solid transparent; border-right:1px solid #CBC8C8; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                "<!--<![endif]-->\n" +
                "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: 'Times New Roman', Georgia, serif\"><![endif]-->\n" +
                "<div style=\"color:#555555;font-family:TimesNewRoman, 'Times New Roman', Times, Beskerville, Georgia, serif;line-height:120%;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n" +
                "<div style=\"font-family: TimesNewRoman, 'Times New Roman', Times, Beskerville, Georgia, serif; font-size: 12px; line-height: 14px; color: #555555;\">\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"><strong>Dear Vanitha,</strong></p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"> </p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"> </p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">Greetings from Miles!</p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"> </p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">Please find below key highlights of US CPA &amp; CMA.</p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"> </p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"> </p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"><strong>Certified Public Accountant (CPA)</strong></p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">    <strong>✓</strong>  US equivalent of Indian CA</p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">    <strong>✓</strong>  12 months course</p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">    <strong>✓</strong>  Only 4 exams | Covered over 28 Sunday classes</p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">    <strong>✓</strong>  Exams in Dubai or US; may be held in India from 2018</p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">    <strong>✓</strong>  @Miles,India’s largest CPA review,\"Registered Course Provider\" with the AICPA,US support also includes CPA license &amp; placement with Big 4 &amp;  MNCs.</p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">         <a href=\"[weblink]\" rel=\"noopener\" style=\"color: #0068A5;\" target=\"_blank\">Read More</a></p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"> </p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"> </p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"><strong>Certified Management Accountant (CMA) </strong></p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">    <strong>✓</strong>  US equivalent of Indian CMA [earlier CWA]</p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">    <strong>✓</strong>  6 months Course</p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">    <strong>✓</strong>  Only 2 exams | Covered over 11 Sunday Classes</p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">    <strong>✓</strong>  Exams in India</p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">    <strong>✓</strong>  @Miles,IMA’s “official” India partner learn using \"official\" IMA study materials published by Wiley avail of IMA-Wiley bundle discounts and get placed MNCs.</p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">         <a href=\"[weblink]\" rel=\"noopener\" style=\"color: #0068A5;\" target=\"_blank\">Read More</a></p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"> </p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"> </p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">Give us a shout for any further info, we’d be happy to be of assistance. Looking forward to having you on-board soon as we sail towards destination CPA/CMA.</p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"> </p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\"> </p>\n" +
                "<p style=\"font-size: 12px; line-height: 14px; margin: 0;\">Cheers!</p>\n" +
                "</div>\n" +
                "</div>\n" +
                "<!--[if mso]></td></tr></table><![endif]-->\n" +
                "<!--[if (!mso)&(!IE)]><!-->\n" +
                "</div>\n" +
                "<!--<![endif]-->\n" +
                "</div>\n" +
                "</div>\n" +
                "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div style=\"background-color:transparent;\">\n" +
                "<div class=\"block-grid\" data-body-width-father=\"875px\" rel=\"col-num-container-box-father\" style=\"Margin: 0 auto; min-width: 320px; max-width: 875px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FAFAFA;\">\n" +
                "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FAFAFA;\">\n" +
                "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:875px\"><tr class=\"layout-full-width\" style=\"background-color:#FAFAFA\"><![endif]-->\n" +
                "<!--[if (mso)|(IE)]><td align=\"center\" width=\"875\" style=\"background-color:#FAFAFA;width:875px; border-top: 0px solid transparent; border-left: 1px solid #CBC8C8; border-bottom: 0px solid transparent; border-right: 1px solid #CBC8C8;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n" +
                "<div class=\"col num12\" data-body-width-son=\"873\" rel=\"col-num-container-box-son\" style=\"min-width: 320px; max-width: 875px; display: table-cell; vertical-align: top;\">\n" +
                "<div style=\"width:100% !important;\">\n" +
                "<!--[if (!mso)&(!IE)]><!-->\n" +
                "<div style=\"border-top:0px solid transparent; border-left:1px solid #CBC8C8; border-bottom:0px solid transparent; border-right:1px solid #CBC8C8; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                "<!--<![endif]-->\n" +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\" valign=\"top\" width=\"100%\">\n" +
                "<tbody>\n" +
                "<tr style=\"vertical-align: top;\" valign=\"top\">\n" +
                "<td class=\"divider_inner\" style=\"word-break: break-word; vertical-align: top; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-top: 10px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px; border-collapse: collapse;\" valign=\"top\">\n" +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"divider_content\" height=\"0\" role=\"presentation\" style=\"table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; border-top: 1px solid #BBBBBB; height: 0px;\" valign=\"top\" width=\"100%\">\n" +
                "<tbody>\n" +
                "<tr style=\"vertical-align: top;\" valign=\"top\">\n" +
                "<td height=\"0\" style=\"word-break: break-word; vertical-align: top; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; border-collapse: collapse;\" valign=\"top\"><span></span></td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "<!--[if (!mso)&(!IE)]><!-->\n" +
                "</div>\n" +
                "<!--<![endif]-->\n" +
                "</div>\n" +
                "</div>\n" +
                "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div style=\"background-color:transparent;\">\n" +
                "<div class=\"block-grid\" data-body-width-father=\"875px\" rel=\"col-num-container-box-father\" style=\"Margin: 0 auto; min-width: 320px; max-width: 875px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FAFAFA;\">\n" +
                "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FAFAFA;\">\n" +
                "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:transparent;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:875px\"><tr class=\"layout-full-width\" style=\"background-color:#FAFAFA\"><![endif]-->\n" +
                "<!--[if (mso)|(IE)]><td align=\"center\" width=\"875\" style=\"background-color:#FAFAFA;width:875px; border-top: 0px solid transparent; border-left: 1px solid #CBC8C8; border-bottom: 1px solid #CBC8C8; border-right: 1px solid #CBC8C8;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;\"><![endif]-->\n" +
                "<div class=\"col num12\" data-body-width-son=\"873\" rel=\"col-num-container-box-son\" style=\"min-width: 320px; max-width: 875px; display: table-cell; vertical-align: top;\">\n" +
                "<div style=\"width:100% !important;\">\n" +
                "<!--[if (!mso)&(!IE)]><!-->\n" +
                "<div style=\"border-top:0px solid transparent; border-left:1px solid #CBC8C8; border-bottom:1px solid #CBC8C8; border-right:1px solid #CBC8C8; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                "<!--<![endif]-->\n" +
                "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: 'Times New Roman', Georgia, serif\"><![endif]-->\n" +
                "<div style=\"color:#555555;font-family:TimesNewRoman, 'Times New Roman', Times, Beskerville, Georgia, serif;line-height:120%;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n" +
                "<div style=\"font-family: TimesNewRoman, 'Times New Roman', Times, Beskerville, Georgia, serif; font-size: 12px; line-height: 14px; color: #555555;\">\n" +
                "<p style=\"font-size: 14px; line-height: 12px; text-align: center; margin: 0;\"><span style=\"font-size: 10px;\">2017 © Miles Professional Education Private Limited. </span><br/><span style=\"font-size: 10px; line-height: 12px;\">Add our address to your Safe Sender list to ensure delivery.</span><br/><span style=\"font-size: 10px; line-height: 12px;\">Not interested? <a href=\"[weblink]\" rel=\"noopener\" style=\"color: #0068A5;\" target=\"_blank\">Click here</a>  to unsubscribe</span><br/><span style=\"font-size: 10px; line-height: 12px;\">Please send any comments about this email to <a href=\"[weblink]\" rel=\"noopener\" style=\"color: #0068A5;\" target=\"_blank\">support@</a> </span></p>\n" +
                "</div>\n" +
                "</div>\n" +
                "<!--[if mso]></td></tr></table><![endif]-->\n" +
                "<!--[if (!mso)&(!IE)]><!-->\n" +
                "</div>\n" +
                "<!--<![endif]-->\n" +
                "</div>\n" +
                "</div>\n" +
                "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "<div style=\"background-color:#D4E1F7;\">\n" +
                "<div class=\"block-grid\" data-body-width-father=\"875px\" rel=\"col-num-container-box-father\" style=\"Margin: 0 auto; min-width: 320px; max-width: 875px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; background-color: #FAFAFA;\">\n" +
                "<div style=\"border-collapse: collapse;display: table;width: 100%;background-color:#FAFAFA;\">\n" +
                "<!--[if (mso)|(IE)]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"background-color:#D4E1F7;\"><tr><td align=\"center\"><table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width:875px\"><tr class=\"layout-full-width\" style=\"background-color:#FAFAFA\"><![endif]-->\n" +
                "<!--[if (mso)|(IE)]><td align=\"center\" width=\"875\" style=\"background-color:#FAFAFA;width:875px; border-top: 0px solid transparent; border-left: 0px solid transparent; border-bottom: 0px solid transparent; border-right: 0px solid transparent;\" valign=\"top\"><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 0px; padding-left: 0px; padding-top:5px; padding-bottom:5px;background-color:#D4E1F7;\"><![endif]-->\n" +
                "<div class=\"col num12\" data-body-width-son=\"875\" rel=\"col-num-container-box-son\" style=\"min-width: 320px; max-width: 875px; display: table-cell; vertical-align: top;\">\n" +
                "<div style=\"background-color:#D4E1F7;width:100% !important;\">\n" +
                "<!--[if (!mso)&(!IE)]><!-->\n" +
                "<div style=\"border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;\">\n" +
                "<!--<![endif]-->\n" +
                "<!--[if mso]><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td style=\"padding-right: 10px; padding-left: 10px; padding-top: 10px; padding-bottom: 10px; font-family: 'Times New Roman', Georgia, serif\"><![endif]-->\n" +
                "<div style=\"color:#555555;font-family:TimesNewRoman, 'Times New Roman', Times, Beskerville, Georgia, serif;line-height:120%;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;\">\n" +
                "<div style=\"font-family: TimesNewRoman, 'Times New Roman', Times, Beskerville, Georgia, serif; font-size: 12px; line-height: 14px; color: #555555;\">\n" +
                "<p style=\"font-size: 14px; line-height: 16px; margin: 0;\"> </p>\n" +
                "</div>\n" +
                "</div>\n" +
                "<!--[if mso]></td></tr></table><![endif]-->\n" +
                "<!--[if (!mso)&(!IE)]><!-->\n" +
                "</div>\n" +
                "<!--<![endif]-->\n" +
                "</div>\n" +
                "</div>\n" +
                "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "<!--[if (mso)|(IE)]></td></tr></table></td></tr></table><![endif]-->\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "<!--[if (mso)|(IE)]></td></tr></table><![endif]-->\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "<!--[if (IE)]></div><![endif]-->\n" +
                "</body>";
        intent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(body));


       /* intent.putExtra(Intent.EXTRA_TEXT, new StringBuilder().append("<h2 style=\\\" margin-bottom:30px; line-height: 1.5;font-weight:600;\\\"><a target=\\\"_blank\\\" href=\\\"https://www.mileseducation.com/cma/Training-Videos\\\" style=\\\"color#3e4d5c\\\">\\n\" +\n" +
                "                            \"                            <strong class=\\\"national-title\\\">Introducing the world's favorite CPA &amp; CMA instructor - <br style=\\\" display:block; \\\"></strong>Varun\\n\" +\n" +
                "                            \"                            Jain,&nbsp;<span style=\\\"font-size:16px;\\\">CPA, CMA,\\n\" +\n" +
                "                            \"                                Harvard B-School\\n\" +\n" +
                "                            \"                                Alumnus</span> </a></h2>\\n\" +\n" +
                "                            \"                                <img src=\\\"https://assets.mileseducation.com/images/MilesEducation_Logo.png\\\">").toString());*/
        try {
            startActivityForResult(Intent.createChooser(intent, "Send E-Mail"), 123);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "There are no E-mail Clients installed on your Device.", Toast.LENGTH_LONG).show();
        }
    }


    private void showCustomDialogForVisit(String user_name) {

//        spinnerLevelListvisit = new ArrayList<>();
//        spinnerLevelListvisit.add(new LevelsModel("M7", "M7- Enrolled"));
//        spinnerLevelListvisit.add(new LevelsModel("M6", "M6 - Visited & Ready to Enroll"));
//        spinnerLevelListvisit.add(new LevelsModel("M5", "M5 - Visited & Positive"));
//        spinnerLevelListvisit.add(new LevelsModel("M4", "M4 - Visited but Postponed"));
//        spinnerLevelListvisit.add(new LevelsModel("M4-", "M4- - Visited but not interested"));
        myCalendar = Calendar.getInstance();
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_event_for_visit);
        dialog.setCancelable(true);

        engagement_description_visit = dialog.findViewById(R.id.engagement_description_visit);
        date_picker_visit = dialog.findViewById(R.id.date_picker_visit);
        textview_header = dialog.findViewById(R.id.textview_header);
        textview_header.setText(user_name + " - " + m_levels);
        appconpact_spinner_M_levels = dialog.findViewById(R.id.appconpact_spinner_levels_M_visit);
        appconpact_spinner_U_levels = dialog.findViewById(R.id.appconpact_spinner_U_levels);

        cpaCheckbox = dialog.findViewById(R.id.cpa_checked);
        cmaCheckbox = dialog.findViewById(R.id.cma_checked);
        iimlfa_checked = dialog.findViewById(R.id.iimlfa_checked);
        iimlsf_checked = dialog.findViewById(R.id.iimlsf_checked);
        add_engagement_progress = dialog.findViewById(R.id.add_engagement_progress);

        enroll_check = dialog.findViewById(R.id.enroll_check);
        enroll_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Enrollment_status = "Yes";
                } else {
                    Enrollment_status = "No";
                }
            }
        });

        cpaCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CPAChecked = "CPA";
                } else {
                    CPAChecked = " ";
                }
            }
        });

        cmaCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CPMChecked = "CMA";
                } else {
                    CPMChecked = " ";
                }
            }
        });
        iimlfa_checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IIMLFAChecked = "IIML-FT";
                } else {
                    IIMLFAChecked = " ";
                }
            }
        });
        iimlsf_checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IIMLSFChecked = "IIML-SF";
                } else {
                    IIMLSFChecked = " ";
                }
            }
        });


        if (courses.equals("CPA,CMA,DA,RPA,FoF(O),WCBA,IIML")) {
            cpaCheckbox.setChecked(true);
            cmaCheckbox.setChecked(true);
        }
        if (courses.equals("CPA,CMA")) {
            cpaCheckbox.setChecked(true);
            cmaCheckbox.setChecked(true);
        }
        if (courses.equals("CPA")) {
            cpaCheckbox.setChecked(true);
        }
        if (courses.equals("CMA")) {
            cmaCheckbox.setChecked(true);
        }
        if (courses.contains("CPA")) {
            cpaCheckbox.setChecked(true);
        }
        if (courses.contains("CMA")) {
            cmaCheckbox.setChecked(true);
        }
        if (courses.contains("IIML-FT")) {
            iimlfa_checked.setChecked(true);
        }
        if (courses.contains("IIML-SF")) {
            iimlsf_checked.setChecked(true);
        }
        if (courses.contains("USP")) {
            if (USP_checked != null) USP_checked.setChecked(true);
        }
        if (courses.contains("CFA")) {
            CFA_checked.setChecked(true);
        }
        if (courses.contains("FRM")) {
            FRM_checked.setChecked(true);
        }

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ((Button) dialog.findViewById(R.id.bt_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dialog.dismiss();

                submitUserDataForm(dialog);


            }
        });
        levelsCustomAdapter = new LevelsCustomAdapter(getContext(), R.layout.listitems_layout, R.id.levels_items, spinnerLevelListvisit);
        appconpact_spinner_M_levels.setAdapter(levelsCustomAdapter);
        levelsCustomAdapter.notifyDataSetChanged();

        if (appconpact_spinner_U_levels != null) {
            levelsCustomAdapter = new LevelsCustomAdapter(getContext(), R.layout.listitems_layout, R.id.levels_items, spinnerLevelListvisit);
            appconpact_spinner_U_levels.setAdapter(levelsCustomAdapter);
            levelsCustomAdapter.notifyDataSetChanged();
        }

        appconpact_spinner_M_levels.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LevelsModel levelsModel = spinnerLevelListvisit.get(position);
                User_levels = levelsModel.getLevelCode();
                myCalendar = Calendar.getInstance();
                myCalendar.set(Calendar.YEAR, myCalendar.get(Calendar.YEAR));
                myCalendar.set(Calendar.MONTH, myCalendar.get(Calendar.MONTH));
                if (User_levels.equals("M6")) {
                    myCalendar.add(Calendar.DAY_OF_MONTH, 7);
                }
                if (User_levels.equals("M5")) {
                    myCalendar.add(Calendar.DAY_OF_MONTH, 7);
                }
                if (User_levels.equals("M4")) {
                    myCalendar.add(Calendar.DAY_OF_MONTH, 90);
                }
                if (User_levels.equals("M4-")) {
                    myCalendar.add(Calendar.DAY_OF_MONTH, 120);
                }

                updateLabelForVisit();
                Log.d("User_levels", User_levels);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // SpinnerSelection(appconpact_spinner_levels, levels);




       /* // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerLevelList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        appconpact_spinner_levels.setAdapter(dataAdapter);

        appconpact_spinner_levels.setSelection(spinnerLevelList.indexOf(levels));*/

        /*appconpact_spinner_levels.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                User_levels = appconpact_spinner_levels.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/


        date_picker_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePickerForVisit();
            }
        });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setAttributes(lp);
       /* date_picker_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlertForDatepicker();
            }
        });


*/


        if (m_levels.equals("M6") || m_levels.equals("L6")) {
            appconpact_spinner_M_levels.setSelection(1);
        }
        if (m_levels.equals("M5") || m_levels.equals("L5")) {
            appconpact_spinner_M_levels.setSelection(2);
        }
        if (m_levels.equals("M7")) {
            appconpact_spinner_M_levels.setSelection(0);
            appconpact_spinner_M_levels.setEnabled(false);

        }
        if (m_levels.equals("M4") || m_levels.equals("L4")) {
            appconpact_spinner_M_levels.setSelection(3);
        }
        if (m_levels.equals("M4-") || m_levels.equals("L4-")) {
            appconpact_spinner_M_levels.setSelection(4);
        }


    }

    private void SpinnerForSelection(AppCompatSpinner appconpact_spinner_levels, String levels) {
        if (USER_TEAM.equals("SR")) {
            if (levels.equals("M10")) {
                appconpact_spinner_levels.setSelection(0);
                appconpact_spinner_levels.setEnabled(false);
            }
            if (levels.equals("M9")) {
                appconpact_spinner_levels.setSelection(1);
            }
            if (levels.equals("M9-")) {
                appconpact_spinner_levels.setSelection(2);
            }
            if (levels.equals("M8+")) {
                appconpact_spinner_levels.setSelection(3);
            }
            if (levels.equals("M8")) {
                appconpact_spinner_levels.setSelection(4);
            }
            if (levels.equals("M8-")) {
                appconpact_spinner_levels.setSelection(5);
            }
            if (levels.equals("M7D2")) {
                appconpact_spinner_levels.setSelection(6);
            }
            if (levels.equals("M7D1")) {
                appconpact_spinner_levels.setSelection(7);
            }
            if (levels.equals("M7#")) {
                appconpact_spinner_levels.setSelection(8);
            }
            if (levels.equals("M7-JJ")) {
                appconpact_spinner_levels.setSelection(9);
            }

            if (levels.equals("M7X")) {
                appconpact_spinner_levels.setSelection(10);
            }
            if (levels.equals("M7+")) {
                appconpact_spinner_levels.setSelection(11);
            }
            if (levels.equals("M7")) {
                appconpact_spinner_levels.setSelection(12);
            }
            if (levels.equals("M7-")) {
                appconpact_spinner_levels.setSelection(13);
            }
            if (levels.equals("M6")) {
                appconpact_spinner_levels.setSelection(14);
            }
            if (levels.equals("M5")) {
                appconpact_spinner_levels.setSelection(15);
            }
            if (levels.equals("M4")) {
                appconpact_spinner_levels.setSelection(16);
            }
            if (levels.equals("M4-")) {
                appconpact_spinner_levels.setSelection(17);
            }
            if (levels.equals("M3+")) {
                appconpact_spinner_levels.setSelection(18);
            }
            if (levels.equals("M3")) {
                appconpact_spinner_levels.setSelection(19);
            }
            if (levels.equals("M2")) {
                appconpact_spinner_levels.setSelection(20);
            }
            if (levels.equals("M1")) {
                appconpact_spinner_levels.setSelection(21);
            }

        }
    }

    private void SpinnerSelection(AppCompatSpinner appconpact_spinner_levels, String levels) {

        if (levels.equals("M7#") || levels.equals("M7-JJ") || levels.equals("M8+") || levels.equals("M7D1") || levels.equals("M7D2") || levels.equals("M7-") || levels.equals("M7+") || levels.equals("M7X") || levels.equals("M8-") || levels.equals("M8") || levels.equals("M9-") || levels.equals("M9") || levels.equals("M10")) {
            if (levels.equals("M10")) {
                appconpact_spinner_levels.setSelection(0);
                appconpact_spinner_levels.setEnabled(false);
            }
            if (levels.equals("M9")) {
                appconpact_spinner_levels.setSelection(1);
                appconpact_spinner_levels.setEnabled(false);
            }
            if (levels.equals("M9-")) {
                appconpact_spinner_levels.setSelection(2);
                appconpact_spinner_levels.setEnabled(false);
            }
            if (levels.equals("M8+")) {
                appconpact_spinner_levels.setSelection(3);
                appconpact_spinner_levels.setEnabled(false);
            }
            if (levels.equals("M8")) {
                appconpact_spinner_levels.setSelection(4);
                appconpact_spinner_levels.setEnabled(false);
            }
            if (levels.equals("M8-")) {
                appconpact_spinner_levels.setSelection(5);
                appconpact_spinner_levels.setEnabled(false);
            }
            if (levels.equals("M7X")) {
                appconpact_spinner_levels.setSelection(6);
                appconpact_spinner_levels.setEnabled(false);
            }
            if (levels.equals("M7+")) {
                appconpact_spinner_levels.setSelection(7);
                appconpact_spinner_levels.setEnabled(false);
            }
            if (levels.equals("M7-")) {
                appconpact_spinner_levels.setSelection(8);
                appconpact_spinner_levels.setEnabled(false);
            }
            if (levels.equals("M7D2")) {
                appconpact_spinner_levels.setSelection(9);
                appconpact_spinner_levels.setEnabled(false);
            }
            if (levels.equals("M7D1")) {
                appconpact_spinner_levels.setSelection(10);
                appconpact_spinner_levels.setEnabled(false);
            }
            if (levels.equals("M7#")) {
                appconpact_spinner_levels.setSelection(11);
                appconpact_spinner_levels.setEnabled(false);
            }
            if (levels.equals("M7-JJ")) {
                appconpact_spinner_levels.setSelection(12);
                appconpact_spinner_levels.setEnabled(false);
            }


        }
        if (levels.equals("M7") || levels.equals("M6") || levels.equals("M5") || levels.equals("M4") || levels.equals("M4-") || levels.equals("L7") || levels.equals("L6") || levels.equals("L5") || levels.equals("L4") || levels.equals("L4-")) {
            if (levels.equals("M7")) {
                appconpact_spinner_levels.setSelection(0);
                appconpact_spinner_levels.setEnabled(false);
            }
            if (levels.equals("M6") || levels.equals("L6")) {
                appconpact_spinner_levels.setSelection(1);
            }
            if (levels.equals("M5") || levels.equals("L5")) {
                appconpact_spinner_levels.setSelection(2);
            }
            if (levels.equals("M4") || levels.equals("L4")) {
                appconpact_spinner_levels.setSelection(3);
            }
            if (levels.equals("M4-") || levels.equals("L4-")) {
                appconpact_spinner_levels.setSelection(4);
            }
        } else {
            if (levels.equals("M3+") || levels.equals("L3+")) {
                appconpact_spinner_levels.setSelection(0);
            }
            if (levels.equals("M3") || levels.equals("L3")) {
                appconpact_spinner_levels.setSelection(1);
            }
            if (levels.equals("M2") || levels.equals("L2")) {
                appconpact_spinner_levels.setSelection(2);
            }
            if (levels.equals("M1") || levels.equals("L1")) {
                appconpact_spinner_levels.setSelection(3);
            }
        }


       /* if (levels.equals("M7")) {
            appconpact_spinner_levels.setSelection(0);
            appconpact_spinner_levels.setEnabled(false);
        }
        if (levels.equals("M6")) {
            appconpact_spinner_levels.setSelection(1);
        }
        if (levels.equals("M5")) {
            appconpact_spinner_levels.setSelection(2);
        }
        if (levels.equals("M4")) {
            appconpact_spinner_levels.setSelection(3);
        }
        if (levels.equals("M4-")) {
            appconpact_spinner_levels.setSelection(4);
        }


        if (levels.equals("M3+")) {
            appconpact_spinner_levels.setSelection(5);
        }
        if (levels.equals("M3")) {
            appconpact_spinner_levels.setSelection(6);
        }
        if (levels.equals("M2")) {
            appconpact_spinner_levels.setSelection(7);
        }
        if (levels.equals("M1")) {
            appconpact_spinner_levels.setSelection(8);
        }*/
        /*if (levels.equals("L6")) {
            appconpact_spinner_levels.setSelection(8);
        }
        if (levels.equals("L5")) {
            appconpact_spinner_levels.setSelection(9);
        }
        if (levels.equals("L4")) {
            appconpact_spinner_levels.setSelection(10);
        }
        if (levels.equals("L4-")) {
            appconpact_spinner_levels.setSelection(11);
        }
        if (levels.equals("L3+")) {
            appconpact_spinner_levels.setSelection(12);
        }
        if (levels.equals("L3")) {
            appconpact_spinner_levels.setSelection(13);
        }
        if (levels.equals("L2")) {
            appconpact_spinner_levels.setSelection(14);
        }
        if (levels.equals("L1")) {
            appconpact_spinner_levels.setSelection(15);
        }
*/

    }

    private void openTimePickerForVisit() {
        new DatePickerDialog(getContext(), date_visit, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void submitUserDataForm(final Dialog dialog) {
        if (isvalidateForm()) {
            if (CoursesData == null) {
                CoursesData = "CPA";
            }
            if (engagement_description_visit.getText().toString().equals("")) {
                engagement_description_visit.setError("Please enter Description");
                Toast.makeText(getContext(), "Please enter Description", Toast.LENGTH_SHORT).show();
                return;
            }
            if (nextCallTimeStamp == 0) {
                date_picker_visit.setError("Please select the date");
                Toast.makeText(getContext(), "Please select the date", Toast.LENGTH_SHORT).show();
                return;
            }

            if (enroll_check.isChecked()) {
                Enrollment_status = "Yes";
            } else {
                Enrollment_status = "No";
            }
            add_engagement_progress.setVisibility(View.VISIBLE);
            apiClient.AddEngagement(ConnectionStatus, phone_number, can_id, person_id, user_name, User_levels, CoursesData, engagement_description_visit.getText().toString().trim(), "visit", 0, nextCallTimeStamp, " ", 0, Enrollment_status, batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), VERSION_NUMBER, str_U_levels, "Bearer " + AccessToken, "application/json").enqueue(new Callback<SuccessModel>() {
                @Override
                public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                    try {
                        if (response.body() == null) {
                            int statusCode = response.raw().code();
                            if (statusCode > 399 && statusCode < 500) {
                                SessionLogout(getContext());
                                add_engagement_progress.setVisibility(View.GONE);
                            }
                            dialog.dismiss();
                            add_engagement_progress.setVisibility(View.GONE);
                        } else {
                            if (response.body().getStatus().equals("success")) {
                                dialog.dismiss();
                                add_engagement_progress.setVisibility(View.GONE);
                                openAlert(response.body().getMessage());
                            } else {
                                dialog.dismiss();
                                openAlert(response.body().getMessage());
                            }
                        }

                    } catch (Exception e) {
                        dialog.dismiss();
                        add_engagement_progress.setVisibility(View.GONE);
                        openAlert(String.valueOf(e.getMessage()));
                    }

                }

                @Override
                public void onFailure(Call<SuccessModel> call, Throwable t) {
                    t.printStackTrace();
                    add_engagement_progress.setVisibility(View.GONE);
                    dialog.dismiss();
                    openAlert(String.valueOf(t.getMessage()));
                }
            });


        }


    }

    private void showCustomDialogForWhatsUp() {

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_event_whatsup);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        content_whatsUp = dialog.findViewById(R.id.content_whatsUp);


        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ((ImageView) dialog.findViewById(R.id.bt_send_whatsup)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dialog.dismiss();
                if (content_whatsUp.getText().toString().isEmpty()) {
                    content_whatsUp.setError("Please fill content...");
                } else {
                    /*Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse("content://com.android.contacts/data/" + "9948605636"));
                    i.setType("text/plain");
                    i.setPackage("com.whatsapp");           // so that only Whatsapp reacts and not the chooser
                    i.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                    i.putExtra(Intent.EXTRA_TEXT, "I'm the body.");
                    startActivity(i);*/
                    Uri uri = Uri.parse("smsto:" + "9885756629");
                    Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                    i.putExtra("sms_body", "djghjfg");
                    i.putExtra(Intent.EXTRA_TEXT, "I'm the body.");
                    i.setPackage("com.whatsapp");
                    startActivity(i);
                }
            }
        });

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setAttributes(lp);


    }

    private void showCustomForEmail() {
        templets = new ArrayList<>();
        templets.add("custom");
        templets.add("template 1");
        templets.add("template 2");
        templets.add("template 3");


        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_event_email);
        dialog.setCancelable(true);

        preview_layout = dialog.findViewById(R.id.preview_layout);
        compose_layout = dialog.findViewById(R.id.compose_layout);
        subject_edit = dialog.findViewById(R.id.subject);
        compose_edit = dialog.findViewById(R.id.content);
        webView = dialog.findViewById(R.id.webView);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        appconpact_spinner_levels_email = dialog.findViewById(R.id.appconpact_spinner_levels_email);
        title_header = dialog.findViewById(R.id.title_header);


        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ((ImageView) dialog.findViewById(R.id.bt_send)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dialog.dismiss();
                if (isvalidateEmailForm()) {
                    Toast.makeText(getContext(), "Form is validate", Toast.LENGTH_SHORT).show();
                }
            }
        });

        (compose_text = dialog.findViewById(R.id.compose_text)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compose_text.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_round_bg_white));
                preview_text.setBackgroundDrawable(null);
                preview_layout.setVisibility(View.GONE);
                compose_layout.setVisibility(View.VISIBLE);
            }
        });


        (preview_text = dialog.findViewById(R.id.preview_text)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compose_text.setBackgroundDrawable(null);
                preview_text.setBackgroundDrawable(getResources().getDrawable(R.drawable.edit_text_round_bg_white));
                preview_layout.setVisibility(View.VISIBLE);
                compose_layout.setVisibility(View.GONE);
                compose_edit.getText().toString();
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        view.loadUrl("javascript:replace('replace1', 'new content 1')");
                        view.loadUrl("javascript:replace('replace2', 'new content 2')");
                    }
                });
                webView.loadDataWithBaseURL(null, compose_edit.getText().toString(), "text/html", "utf-8", null);
                title_header.setText("Subject : " + subject_edit.getText().toString().trim());
            }
        });

        // Creating adapter for spinner
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, templets);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        appconpact_spinner_M_levels.setAdapter(dataAdapter);
        appconpact_spinner_U_levels.setAdapter(dataAdapter);

        appconpact_spinner_M_levels.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s = dataAdapter.getItem(position).toString();
                if (s.equals("custom")) {
                    subject_edit.setText("");
                    compose_edit.setText("");
                }
                if (s.equals("template 1")) {
                    subject_edit.setText("This is the subject for Template 1");
                    compose_edit.setText("<h2 style=\" margin-bottom:30px; line-height: 1.5;font-weight:600;\"><a target=\"_blank\" href=\"https://www.mileseducation.com/cma/Training-Videos\" style=\"color#3e4d5c\">\n" +
                            "                            <strong class=\"national-title\">Introducing the world's favorite CPA &amp; CMA instructor - <br style=\" display:block; \"></strong>Varun\n" +
                            "                            Jain,&nbsp;<span style=\"font-size:16px;\">CPA, CMA,\n" +
                            "                                Harvard B-School\n" +
                            "                                Alumnus</span> </a></h2>\n" +
                            "                                <img src=\"https://assets.mileseducation.com/images/MilesEducation_Logo.png\">");
                    //  webView.getSettings().setJavaScriptEnabled(true);


                }
                if (s.equals("template 2")) {
                    subject_edit.setText("This is the subject for Template 2");
                    compose_edit.setText("<button>Template 3</button></br><img src=\"https://assets.mileseducation.com/images/MilesEducation_Logo.png\"  width=\"50\" height=\"50\">");
                   /* webView.getSettings().setJavaScriptEnabled(true);
                    webView.setWebViewClient(new WebViewClient() {
                        @Override
                        public void onPageFinished(WebView view, String url) {
                            super.onPageFinished(view, url);
                            view.loadUrl("javascript:replace('replace1', 'new content 1')");
                            view.loadUrl("javascript:replace('replace2', 'new content 2')");
                        }
                    });*/


                }
                if (s.equals("template 3")) {
                    subject_edit.setText("This is the subject for Template 3");
                    compose_edit.setText("<button>Template 3</button></br><img src=\"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRGBldq_31NcqDksHA5NODzpybvxW6YH8Zh05Oie4QhSJUfa8qxzw\"  width=\"50\" height=\"50\">");

                }

                compose_text.callOnClick();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        compose_text.callOnClick();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setAttributes(lp);


    }

    private boolean isvalidateEmailForm() {
        if (subject_edit.getText().toString().isEmpty()) {
            subject_edit.setError("Please fill subject");
            return false;
        }
        if (compose_edit.getText().toString().isEmpty()) {
            compose_edit.setError("Please fill content");
            return false;
        }
        return true;
    }

    private File createFileFromInputStream(InputStream inputStream) {
        try {
            File f = new File(String.valueOf(inputStream));
            OutputStream outputStream = new FileOutputStream(f);
            byte buffer[] = new byte[1024];
            int length = 0;

            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.close();
            inputStream.close();

            return f;
        } catch (IOException e) {
            //Logging exception
        }

        return null;
    }

    private void openAlertForDatepicker() {
        datePickerDialog = new DatePickerDialog(getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        Calendar calendar = Calendar.getInstance();
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        if (MAX_DATE_PRE_SELECT != 0) {
            calendar.add(Calendar.DAY_OF_MONTH, MAX_DATE_PRE_SELECT);
            datePickerDialog.getDatePicker().setMaxDate((calendar.getTimeInMillis()));

        }
        datePickerDialog.show();
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date_picker_.setText(sdf.format(myCalendar.getTime()));
        try {
            Date date = sdf.parse(sdf.format(myCalendar.getTime()));
            long timestamp = date.getTime() / 1000L;
            nextCallTimeStamp = timestamp;
            Log.d("pre-selected_date", String.valueOf(nextCallTimeStamp));

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void openAlert(String p) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(String.valueOf(p))
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

    DatePickerDialog.OnDateSetListener date_visit = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelForVisit();
        }

    };

    private void updateLabelForVisit() {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date_picker_visit.setText(sdf.format(myCalendar.getTime()));
        try {
            Date date = sdf.parse(sdf.format(myCalendar.getTime()));
            long timestamp = date.getTime() / 1000L;
            nextCallTimeStamp = timestamp;

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            // Mail is successfully send:
            if (resultCode == Activity.RESULT_OK) {
                // Do something
            } else {

                // Do something else
            }
        }
    }

    private void GetMLevelList(String Access_token) {
        ninjaApiClient.getMLevels("Bearer " + Access_token, "application/json").enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    MLevelList = response.body();
                    // Handle the list of strings here
                    Log.d("GetMLevelList_onList", String.valueOf(response.body()));

                }
            }


            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.d("GetMLevelList_onFailure", String.valueOf(t.getMessage()));
//                Toast.makeText(getContext(), "Some thing went wrong....!", Toast.LENGTH_SHORT).show();

            }
        });
    }

//    private void GetULevelList(String Access_token) {
//        ninjaApiClient.getULevels("Bearer " + Access_token, "application/json").enqueue(new Callback<List<String>>() {
//            @Override
//            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
//                if (response.isSuccessful()) {
//                    uLevelList = response.body();
//                    Log.d("GetULevelList_onList", String.valueOf(uLevelList));
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<String>> call, Throwable t) {
//                Log.d("GetULevelList_onFailure", String.valueOf(t.getMessage()));
//                Toast.makeText(getContext(), "Some thing went wrong....!", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }

//    private void getEngagementData(String accessToken, int person_id) {
//        historyModelArrayList = new ArrayList<>();
//        apiClient.getPersonengagement(page, person_id, "Bearer " + accessToken, "application/json").enqueue(new Callback<EngagemmentHistoryModel>() {
//            @Override
//            public void onResponse(Call<EngagemmentHistoryModel> call, Response<EngagemmentHistoryModel> response) {
//                try {
//                    if (response.body() == null) {
//                        int statusCode = response.raw().code();
//                        if (statusCode > 399 && statusCode < 500) {
//                            SessionLogout(getContext());
//                        }
//                    } else {
//                        List<HistoryModel> getHistoryModelList = response.body().getData();
//                        for (int i = 0; i < getHistoryModelList.size(); i++) {
//                            HistoryModel historyModel = new HistoryModel();
//                            historyModel.setAdded_by_name(getHistoryModelList.get(i).getAdded_by_name());
//                            historyModel.setDetails(getHistoryModelList.get(i).getDetails());
//                            historyModel.setLast_call(getHistoryModelList.get(i).getCreated_at());
//
//                            historyModelArrayList.add(historyModel);
//                        }
//                        historyAdapter = new HistoryAdapter(getContext(), historyModelArrayList);
//                        HistoryRecyclerView.setAdapter(historyAdapter);
//                        historyAdapter.notifyDataSetChanged();
//
////                        appconpact_spinner_interested_working.setSelection();
//
//                    }
//                } catch (Exception e) {
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<EngagemmentHistoryModel> call, Throwable t) {
//
//            }
//        });
//    }

    private void getCandidatePersonaDetails(String accessToken, int person_id) {
        historyModelArrayList = new ArrayList<>();
        commanApiClient.getCandidatePersonaDetails(person_id, "Bearer " + accessToken, "application/json").enqueue(new Callback<CandidatePersonaDetailsModel>() {
            //        commanApiClient.getCandidatePersonaDetails(1823257, "Bearer " + accessToken, "application/json").enqueue(new Callback<CandidatePersonaDetailsModel>() {
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

                        if (candidatePersonaDetailsModel.getPathwayValue() != null) {
                            for (int i = 0; i < spinner_interested_workingArrayList.size(); i++) {
                                if (spinner_interested_workingArrayList.get(i).contains(candidatePersonaDetailsModel.getPathwayValue())) {
                                    appconpact_spinner_interested_working.setSelection(i);
                                    break;
                                }
                            }

                        }
                        if (candidatePersonaDetailsModel.getPlacementAssistance() != null) {
                            for (int i = 0; i < spinnerLookingJob_workingArrayList.size(); i++) {
                                if (spinnerLookingJob_workingArrayList.get(i).contains(candidatePersonaDetailsModel.getPathwayValue())) {
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
                        if (candidatePersonaDetailsModel.getIndianProfessionalQualification() != null) {
                            for (int i = 0; i < select_Indian_professional_Qualification.length; i++) {
                                if (select_Indian_professional_Qualification[i].contains(candidatePersonaDetailsModel.getIndianProfessionalQualification())) {
                                    indian_Professional.setText(i);
                                    break;
                                }
                            }
                        }
                        indian_Professional.setText(candidatePersonaDetailsModel.getIndianProfessionalQualification());

                        if (candidatePersonaDetailsModel.getGlobalProfessionalQualification() != null) {
                            for (int i = 0; i < select_Global_professional_Qualification.length; i++) {
                                if (select_Global_professional_Qualification[i].contains(candidatePersonaDetailsModel.getGlobalProfessionalQualification())) {
                                    global_Professional_Qualification.setText(i);
                                    break;
                                }
                            }
                        }
                        global_Professional_Qualification.setText(candidatePersonaDetailsModel.getGlobalProfessionalQualification());

                        if (candidatePersonaDetailsModel.getUgQualification() != null) {
                            for (int i = 0; i < UG_professional_Qualification.length; i++) {
                                if (UG_professional_Qualification[i].contains(candidatePersonaDetailsModel.getUgQualification())) {
                                    ug_Graduate_Qualification.setText(i);
                                    break;
                                }
                            }
                        }
                        ug_Graduate_Qualification.setText(candidatePersonaDetailsModel.getUgQualification());

                        pg_Graduate_Qualification.setText(candidatePersonaDetailsModel.getPgQualification());

                        if (candidatePersonaDetailsModel.getPgQualification() != null) {
                            for (int i = 0; i < PG_professional_Qualification.length; i++) {
                                if (PG_professional_Qualification[i].contains(candidatePersonaDetailsModel.getPgQualification())) {
                                    pg_Graduate_Qualification.setText(i);
                                    break;
                                }
                            }
                        }


//                        if (candidatePersonaDetailsModel.getIndianProfessionalQualification() != null) {
//                            StringBuilder stringBuilder = new StringBuilder();
//                            for (int i = 0; i < select_Indian_professional_Qualification.length; i++) {
//                                if (Arrays.asList(select_Indian_professional_Qualification).get(i).contains(candidatePersonaDetailsModel.getIndianProfessionalQualification())) {
//                                    stringBuilder.append(select_Indian_professional_Qualification[indianProfessionalLangList.get(i)]);
//                                    break;
//                                }
//                            }
//                        }
                        company_Name.setText(candidatePersonaDetailsModel.getCompany().toString());
                        edt_work_Experience.setText(candidatePersonaDetailsModel.getYearsOfExperience().toString());


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

    private int getIndex(Spinner spinner, String item) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(item)) {
                return i;
            }
        }
        return -1;

    }

    private void filterMLevelList(String Mlevel) {
        int _temp = 0;
        for (int i = 0; i < spinner_M_LevelList.size(); i++) {
            LevelsModel item = spinner_M_LevelList.get(i);
            if (Mlevel != null) {
                if (Mlevel.equals(item.getLevelCode())) { // Assuming getCode() gets the code of the LevelsModel
                    _temp = i;
                    break;
                }
            }
        }
        appconpact_spinner_M_levels.setSelection(_temp);
    }

    private void filterULevelList(String Ulevel) {
        int _temp = 0;
        for (int i = 0; i < spinner_U_LevelList.size(); i++) {
            LevelsModel item = spinner_U_LevelList.get(i);
            if (Ulevel != null) {
                if (Ulevel.equals(item.getLevelCode())) { // Assuming getCode() gets the code of the LevelsModel
                    _temp = i;
                    break;
                }
            }
        }
        appconpact_spinner_U_levels.setSelection(_temp);
    }


}



