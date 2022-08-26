package com.milesforce.mwbewb.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.milesforce.mwbewb.EngagementFragments.LevelsCustomAdapter;
import com.milesforce.mwbewb.Model.ConnectionModel;
import com.milesforce.mwbewb.Model.LevelsModel;
import com.milesforce.mwbewb.Model.UserToken;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;
import com.milesforce.mwbewb.Retrofit.SuccessModel;
import com.milesforce.mwbewb.Utils.BatterPercentage;
import com.milesforce.mwbewb.Utils.BatteryModel;
import com.milesforce.mwbewb.Utils.CallRecord;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.milesforce.mwbewb.Utils.ConstantUtills.AccessToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.Bde_user_id;
import static com.milesforce.mwbewb.Utils.ConstantUtills.IS_INCOMMING_EVENT;
import static com.milesforce.mwbewb.Utils.ConstantUtills.IS_OUTGOING_EVENT;
import static com.milesforce.mwbewb.Utils.ConstantUtills.SaveToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.VERSION_NUMBER;

public class EventTriggerActivity extends AppCompatActivity implements View.OnClickListener {
    int person_id, can_id, Mobile_id;
    ActionBar actionBar;
    Toolbar toolbar;
    private View search_bar;
    private BottomNavigationView bottomNavigationView;
    DrawerLayout drawer;
    boolean isOpen = false;
    int mCurrentTab = 0;
    boolean doubleBackToExitPressedOnce = false;
    private ViewPager view_pager_engagement;
    private TabLayout tab_layoutEngagementform;
    FloatingActionButton call, visit, email, whatsup;
    AppCompatSpinner appconpact_spinner_levels;
    ArrayList<LevelsModel> spinnerLevelList;
    EditText date_picker_;
    Calendar myCalendar, LeadsCalendar;
    AppCompatImageButton bt_menu_back_from_caller, addLeadForm, search_icon;
    ImageView endCall_btn, addEngagementForm;
    public static final int PERMISSION_ACCESS_CALL_PHONE = 20;
    RadioGroup radioGroup, radioGroupForReference;
    LinearLayout referal, direct, referal_layout, reference_layout_spinner;
    AppCompatSpinner ewbspinner, mwbspinner, levels_spinner, city_spinner, direct_spinner, milesSpos_spinner;
    EditText direct_reference;
    ArrayList<String> levelsArrayList = new ArrayList<>();
    ArrayList<String> ewbArraylist = new ArrayList<>();
    ArrayList<String> mwbArrayList = new ArrayList<>();
    ArrayList<String> cityArrayList = new ArrayList<>();
    ArrayList<String> directSourceArraylist = new ArrayList<>();
    ArrayList<String> milesSpos_spinnerArrayList = new ArrayList<>();
    CallRecord callRecord;
    ApiClient apiClient;
    SharedPreferences sharedPreferences;
    String bde_user_id;
    Handler pusherHandler;
    Runnable PusherRunnable;
    String accessToken;
    ArrayList<String> ConnectionTypeArrayList;
    ArrayList<ConnectionModel> connectionModelArrayList;
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
    String CPAChecked_lead = " ";
    String CMAChecked_lead = " ";
    String DAChecked_lead = " ";
    String LEADSOURCE = " ";
    String LeadDetails = " ";
    String LEADCity = " ";
    String LeadLevels = " ";
    Realm realm;

    TextView compose_text, preview_text, title_header, textview_header;
    LinearLayout preview_layout, compose_layout;
    EditText subject_edit, compose_edit, content_whatsUp;
    WebView webView;
    AssetManager assetManager;
    String PreviewData;
    RadioGroup responce_radio_group;
    String RadioBtnResponseType = null;
    EditText engagement_description;
    AppCompatCheckBox cpaCheckbox, cmaCheckbox,iiml_fa_checked,iiml_sf_checked;

    static String CPAChecked = " ";
    static String CPMChecked = " ";
    static String IIMLFAChecked = " ";
    static String IIMLSFCHECKED = " ";

    static String DaChecked = " ";
    static String RPACheckd = " ";
    static String FOFOChecked = " ";
    static String WCBAChecked = " ";
    static String IIMLChecked = " ";
    static String ConnectionStatus = " ";
    static String SELECTED_STATUS = " ";
    static String CoursesData = null;
    String previousEngagement = null;
    String courses, levels, user_name, person_name;
    EditText latestEngagement;
    LinearLayout nextTimeLayout, engagement_main_form,level_layout_;
    String LevelsSelected;
    private static long nextCallTimeStamp = 0;
    ProgressBar add_engagement_progress;
    AppCompatSpinner appconpact_spinner_connectionstatus;
    AlertDialog alert;
    EditText b2c_lead_name, b2c_lead_mobile, b2c_lead_email, b2c_lead_education, b2c_lead_company, b2c_lead_designation, b2c_lead_experiance, b2c_lead_engagement, b2c_lead_nextCall_picker_;
    EditText b2bcr_leadname, b2bcr_leadmobile, b2bcr_lead_email, b2b_cr_lead_company, b2b_cr_lead_designation, b2b_cr_lead_experiance, b2b_cr_lead_engagement, direct_reference_cr;
    EditText lead_b2b_ir_name, lead_b2b_ir_mobile, lead_b2b_ir_email, lead_b2b_ir_institute, lead_b2b_ir_designation, lead_b2b_ir_engagement, lead_b2b_ir_reference;
    String MWbLeadCourseData = " ";
    LinearLayout user_info_snakebar;
    ProgressBar add_b2b_progresss, b2b_cr_progress, b2b_ir_progress;
    String other_check_radio_text;
    ConstraintLayout constrain_layout;
    BatteryModel batteryModel;
    LevelsCustomAdapter levelsCustomAdapter;
    UserToken userToken;
    RelativeLayout invite_webinar;
    DatePickerDialog datePickerDialog;
    int MAX_DATE_PRE_SELECT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_trigger);
        getData();
        apiClient = ApiUtills.getAPIService();
        realm = Realm.getDefaultInstance();
        if (realm.where(UserToken.class).findFirst() != null) {
            userToken = realm.where(UserToken.class).findFirst();
        }
        String Token = userToken.getAccessToken();


        sharedPreferences = getApplicationContext().getSharedPreferences(SaveToken, MODE_PRIVATE);
        accessToken = Token;
        bde_user_id = sharedPreferences.getString(Bde_user_id, null);
        myCalendar = Calendar.getInstance();
        person_id = getIntent().getExtras().getInt("person_id");
        can_id = getIntent().getExtras().getInt("can_id");
        previousEngagement = getIntent().getExtras().getString("previousEngagement");
        courses = getIntent().getExtras().getString("courses");
        levels = getIntent().getExtras().getString("levels");
        user_name = getIntent().getExtras().getString("user_name");
        Mobile_id = getIntent().getExtras().getInt("mobile_id");
        person_name = getIntent().getExtras().getString("person_name");
        String token_new = getIntent().getExtras().getString("token_new");
        showCustomDialog(accessToken, levels, courses, previousEngagement);
        LevelsSelected = levels;
        CoursesData = courses;
        batteryModel = new BatterPercentage().getBattertPercentage(getApplicationContext());


    }

    private void showCustomDialog(final String user_accessToken, final String levels, final String courses, final String previousEngagement) {
        if (levels.equals("M7") || levels.equals("M6") || levels.equals("M5") || levels.equals("M4") || levels.equals("M4-") || levels.equals("L6") || levels.equals("L5") || levels.equals("L4") || levels.equals("L4-")) {
            if (spinnerLevelList != null) {
                spinnerLevelList.clear();
            }
            spinnerLevelList = new ArrayList<>();
            spinnerLevelList.add(new LevelsModel("M7", "M7- Enrolled"));
            spinnerLevelList.add(new LevelsModel("M6", "M6 - Visited & Ready to Enroll"));
            spinnerLevelList.add(new LevelsModel("M5", "M5 - Visited & Positive"));
            spinnerLevelList.add(new LevelsModel("M4", "M4 - Visited but Postponed"));
            spinnerLevelList.add(new LevelsModel("M4-", "M4- - Visited but not interested"));
        } else {
            if (spinnerLevelList != null) {
                spinnerLevelList.clear();
            }
            spinnerLevelList = new ArrayList<>();
            spinnerLevelList.add(new LevelsModel("M3+", "M3+ - Called & Coming"));
            spinnerLevelList.add(new LevelsModel("M3", "M3 - Called & positive"));
            spinnerLevelList.add(new LevelsModel("M2", "M2 - Did not visit & Postponed"));
            spinnerLevelList.add(new LevelsModel("M1", "M1 - Did not visit & not interested"));
            spinnerLevelList.add(new LevelsModel("M7", "M7- Enrolled"));
        }

        nextTimeLayout = findViewById(R.id.nextTimeLayout);
        engagement_main_form = findViewById(R.id.engagement_main_form);
        latestEngagement = findViewById(R.id.latestEngagement);
        latestEngagement.setText(previousEngagement);
        textview_header = findViewById(R.id.textview_header);
        date_picker_ = findViewById(R.id.date_picker_);
        constrain_layout = findViewById(R.id.constrain_layout);
        level_layout_ = findViewById(R.id.level_layout_);
        date_picker_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlertForDatepicker();
            }
        });
        textview_header.setText(person_name + " - " + levels);

        appconpact_spinner_levels = findViewById(R.id.appconpact_spinner_levels);
        appconpact_spinner_connectionstatus = findViewById(R.id.appconpact_spinner_connectionstatus);
        add_engagement_progress = findViewById(R.id.add_engagement_progress);
        responce_radio_group = findViewById(R.id.responce_radio_group);
        engagement_description = findViewById(R.id.engagement_description);
        invite_webinar = findViewById(R.id.invite_webinar);
        invite_webinar.setOnClickListener(this);


        cpaCheckbox = findViewById(R.id.cpa_checked);
        cmaCheckbox = findViewById(R.id.cma_checked);
        iiml_fa_checked = findViewById(R.id.iiml_fa_checked);
        iiml_sf_checked = findViewById(R.id.iiml_sf_checked);



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
        iiml_fa_checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IIMLFAChecked = "IIML-FA";
                } else {
                    IIMLFAChecked = " ";
                }
            }
        });

        iiml_sf_checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IIMLSFCHECKED = "IIML-SF";
                } else {
                    IIMLSFCHECKED = " ";
                }
            }
        });
        responce_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.same_response) {
                    Toast.makeText(getApplicationContext(), "same Response", Toast.LENGTH_SHORT).show();
                    engagement_description.setText(previousEngagement);
                }
                if (checkedId == R.id.new_) {
                    engagement_description.setText("");
                }
            }
        });

        ((ImageButton) findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                AudioManager manager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
           /*     if (manager.getMode() == AudioManager.MODE_IN_CALL) {
                    if (IS_INCOMMING_EVENT == true) {
                        IS_INCOMMING_EVENT = true;
                    }
                    if (IS_OUTGOING_EVENT == true) {
                        IS_OUTGOING_EVENT = true;
                    }

                } else {
                    if (IS_INCOMMING_EVENT == true) {
                        IS_INCOMMING_EVENT = false;
                    }
                    if (IS_OUTGOING_EVENT == true) {
                        IS_OUTGOING_EVENT = false;
                    }

                }*/
                //   startActivity(new Intent(EventTriggerActivity.this, MainActivity.class));
                Intent it = new Intent(EventTriggerActivity.this, MainActivity.class);
                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(it);
                finish();
                finishAffinity();
            }
        });
        ((Button) findViewById(R.id.bt_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                if (isvalidateForm()) {
                    if (CoursesData == null) {
                        CoursesData = "CPA";
                    }
                    if (LevelsSelected == null) {
                        LevelsSelected = "L1";
                    }
                    if (ConnectionStatus.equals("CD")) {
                        if (engagement_description.getText().toString().equals(" ")) {
                            engagement_description.setError("Please fill engagement description..");
                            Toast.makeText(EventTriggerActivity.this, "Please fill engagement description..", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    if ((!LevelsSelected.equals("M7")) && nextCallTimeStamp == 0) {
                        date_picker_.setError("Please select the Date...");
                        Toast.makeText(EventTriggerActivity.this, "Please select Date", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    add_engagement_progress.setVisibility(View.VISIBLE);
                    apiClient.AddEngagement(ConnectionStatus, "", can_id, person_id, person_name, LevelsSelected, CoursesData, engagement_description.getText().toString().trim(), "call", 0, nextCallTimeStamp, "Yes", 0, "No", batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), VERSION_NUMBER, "Bearer " + user_accessToken, "application/json").enqueue(new Callback<SuccessModel>() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                            try {
                                if (response.body().getStatus().equals("success")) {
                                    add_engagement_progress.setVisibility(View.GONE);
                                    showSnakeBar(response.body().getMessage());
                                    AudioManager manager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                                   /* if (manager.getMode() == AudioManager.MODE_IN_CALL) {
                                        if (IS_INCOMMING_EVENT == true) {
                                            IS_INCOMMING_EVENT = true;
                                        }
                                        if (IS_OUTGOING_EVENT == true) {
                                            IS_OUTGOING_EVENT = true;
                                        }

                                    } else {
                                        if (IS_INCOMMING_EVENT == true) {
                                            IS_INCOMMING_EVENT = false;
                                        }
                                        if (IS_OUTGOING_EVENT == true) {
                                            IS_OUTGOING_EVENT = false;
                                        }
                                    }*/

                                    // startActivity(new Intent(EventTriggerActivity.this, MainActivity.class));
                                    Intent it = new Intent(EventTriggerActivity.this, MainActivity.class);
                                    it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(it);
                                    finish();
                                    finishAffinity();
                                } else {
                                    add_engagement_progress.setVisibility(View.GONE);
                                    showSnakeBar(response.body().getMessage());
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                add_engagement_progress.setVisibility(View.GONE);
                                showSnakeBar(e.getMessage());
                            }

                        }

                        @Override
                        public void onFailure(Call<SuccessModel> call, Throwable t) {
                            t.printStackTrace();
                            add_engagement_progress.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Some Thing went wrong please try after some time ", Toast.LENGTH_SHORT).show();
                            showSnakeBar(t.getMessage());
                        }
                    });
                }
            }
        });

      /*  // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerLevelList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        appconpact_spinner_levels.setAdapter(dataAdapter);

        appconpact_spinner_levels.setSelection(spinnerLevelList.indexOf(levels));*/

        levelsCustomAdapter = new LevelsCustomAdapter(this, R.layout.listitems_layout, R.id.levels_items, spinnerLevelList);
        appconpact_spinner_levels.setAdapter(levelsCustomAdapter);
        // appconpact_spinner_levels.setEnabled(false);
        appconpact_spinner_levels.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LevelsModel levelsModel = spinnerLevelList.get(position);
                LevelsSelected = levelsModel.getLevelCode();
                myCalendar = Calendar.getInstance();
                myCalendar.set(Calendar.YEAR, myCalendar.get(Calendar.YEAR));
                myCalendar.set(Calendar.MONTH, myCalendar.get(Calendar.MONTH));
                MAX_DATE_PRE_SELECT = 0;
                if (LevelsSelected.equals("M6")){
                    myCalendar.add(Calendar.DAY_OF_MONTH, 7);
                    MAX_DATE_PRE_SELECT = 14;
                }
                if (LevelsSelected.equals("M5")){
                    myCalendar.add(Calendar.DAY_OF_MONTH, 7);
                    MAX_DATE_PRE_SELECT = 14;
                }
                if (LevelsSelected.equals("M4")){
                    myCalendar.add(Calendar.DAY_OF_MONTH, 90);
                    MAX_DATE_PRE_SELECT = 180;
                }
                if (LevelsSelected.equals("M4-")){
                    myCalendar.add(Calendar.DAY_OF_MONTH, 120);
                    MAX_DATE_PRE_SELECT = 180;
                }
                if (LevelsSelected.equals("M3+")){
                    myCalendar.add(Calendar.DAY_OF_MONTH, 7);
                    MAX_DATE_PRE_SELECT = 14;
                }
                if (LevelsSelected.equals("M3")){
                    myCalendar.add(Calendar.DAY_OF_MONTH, 14);
                    MAX_DATE_PRE_SELECT = 30;
                }
                if (LevelsSelected.equals("M2")){
                    myCalendar.add(Calendar.DAY_OF_MONTH, 90);
                    MAX_DATE_PRE_SELECT = 180;
                }
                if (LevelsSelected.equals("M1")){
                    myCalendar.add(Calendar.DAY_OF_MONTH, 120);
                    MAX_DATE_PRE_SELECT = 180;
                }
                updateLabel();




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpinnerSelection(appconpact_spinner_levels, levels);

        ArrayAdapter<String> connection_statusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ConnectionTypeArrayList);

        // Drop down layout style - list view with radio button
        connection_statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        appconpact_spinner_connectionstatus.setAdapter(connection_statusAdapter);

        appconpact_spinner_connectionstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    nextTimeLayout.setVisibility(View.VISIBLE);
                    SELECTED_STATUS = appconpact_spinner_connectionstatus.getSelectedItem().toString();
                    if (SELECTED_STATUS.equals("Connected / Discussed")) {
                        engagement_main_form.setVisibility(View.VISIBLE);
                        invite_webinar.setVisibility(View.GONE);
                        engagement_description.setText(" ");
                        ConnectionStatus = "CD";
                    }
                    else if(SELECTED_STATUS.equals("Not Lifting") || SELECTED_STATUS.equals("Connected / Wrong number") || SELECTED_STATUS.equals("Switched Off") || SELECTED_STATUS.equals("Connected / Never call back")){
                        if(spinnerLevelList.size() > 0){
                            spinnerLevelList.clear();
                            spinnerLevelList = new ArrayList<>();
                            if(levels.equals("M7")){
                                spinnerLevelList.add(new LevelsModel("M7", "M7- Enrolled"));
                            }
                            if(levels.equals("M6") || levels.equals("L6")){
                                spinnerLevelList.add(new LevelsModel("M6", "M6 - Visited & Ready to Enroll"));
                                spinnerLevelList.add(new LevelsModel("M5", "M5 - Visited & Positive"));
                            }if(levels.equals("M5") || levels.equals("L5")){
                                spinnerLevelList.add(new LevelsModel("M5", "M5 - Visited & Positive"));
                                spinnerLevelList.add(new LevelsModel("M4", "M4 - Visited but Postponed"));

                            }if(levels.equals("M4") || levels.equals("L4")){
                                spinnerLevelList.add(new LevelsModel("M4", "M4 - Visited but Postponed"));
                                spinnerLevelList.add(new LevelsModel("M4-", "M4- - Visited but not interested"));
                            }
                            if(levels.equals("M4-") || levels.equals("L4-")){
                                spinnerLevelList.add(new LevelsModel("M4-", "M4- - Visited but not interested"));
                                spinnerLevelList.add(new LevelsModel("M3+", "M3+ - Called & Coming"));
                            }
                            if(levels.equals("M3+") || levels.equals("L3+")){
                                spinnerLevelList.add(new LevelsModel("M3+", "M3+ - Called & Coming"));
                                spinnerLevelList.add(new LevelsModel("M3", "M3 - Called & positive"));
                            }
                            if(levels.equals("M3") || levels.equals("L3")){
                                spinnerLevelList.add(new LevelsModel("M3", "M3 - Called & positive"));
                                spinnerLevelList.add(new LevelsModel("M2", "M2 - Did not visit & Postponed"));
                            }
                            if(levels.equals("M2") || levels.equals("L2")){
                                spinnerLevelList.add(new LevelsModel("M2", "M2 - Did not visit & Postponed"));
                                spinnerLevelList.add(new LevelsModel("M1", "M1 - Did not visit & not interested"));
                            }
                            if(levels.equals("M1") || levels.equals("L1")){
                                spinnerLevelList.add(new LevelsModel("M1", "M1 - Did not visit & not interested"));
                            }
                            level_layout_.setVisibility(View.VISIBLE);
                            engagement_main_form.setVisibility(View.GONE);
                        }
                        levelsCustomAdapter = new LevelsCustomAdapter(EventTriggerActivity.this, R.layout.listitems_layout, R.id.levels_items, spinnerLevelList);
                        appconpact_spinner_levels.setAdapter(levelsCustomAdapter);
                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        String formattedDate = df.format(c);
                        if(previousEngagement == null||previousEngagement.equals(null)  || previousEngagement.equals("") || previousEngagement.equals(" ") || previousEngagement.equals("null")  ){
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
                        LevelsSelected = levels;
                        CoursesData = courses;
                    }
                    else {
                        level_layout_.setVisibility(View.GONE);
                        engagement_main_form.setVisibility(View.GONE);
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
                        LevelsSelected = levels;
                        CoursesData = courses;


                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if (courses.contains("CPA")) {
            cpaCheckbox.setChecked(true);
        }
        if (courses.contains("CMA")) {
              cmaCheckbox.setChecked(true);

        }
        if (courses.contains("IIML-FA")) {
            iiml_fa_checked.setChecked(true);
        }
        if (courses.contains("IIML-SF")) {
            iiml_sf_checked.setChecked(true);

        }



    }

    private void showSnakeBar(String message) {
        Snackbar snackbar = Snackbar
                .make(constrain_layout, String.valueOf(message), Snackbar.LENGTH_LONG);
        snackbar.show();
    }


    private void getData() {


        ewbArraylist.add("Kiran CPA M7-01");
        ewbArraylist.add("Rafi CPA M7-04");
        ewbArraylist.add("Usha CPA M7-05");
        ewbArraylist.add("Jai CPA M7-07");
        ewbArraylist.add("Us CPA M7-05");
        ewbArraylist.add("Jaikiran CPA M7-07");
        ewbArraylist.add("Ushasri CPA M7-05");
        ewbArraylist.add("Jagan CPA M7-07");

        mwbArrayList.add("Krishna Mohan L3");
        mwbArrayList.add(" Mohan Das L5");
        mwbArrayList.add(" Bunny Das L1");
        mwbArrayList.add(" Charan Das L4");
        mwbArrayList.add(" Das nandan L2");
        mwbArrayList.add(" Venkat L7");

        cityArrayList.add("Hyderabad");
        cityArrayList.add("Delhi");
        cityArrayList.add("Mumbai");
        cityArrayList.add("Chennai");
        cityArrayList.add("Bangalore");
        cityArrayList.add("Ernakulam");
        cityArrayList.add("Ahmadabad");
        cityArrayList.add("Jaipur");
        cityArrayList.add("Pune");


        directSourceArraylist.add("Google Ads");
        directSourceArraylist.add("Twitter");
        directSourceArraylist.add("WhatsApp");
        directSourceArraylist.add("Facebook");
        directSourceArraylist.add("Linkedin");
        directSourceArraylist.add("SMS");
        directSourceArraylist.add("Instagram");
        directSourceArraylist.add("Youtube");
        directSourceArraylist.add("CA Activity");
        directSourceArraylist.add("University");
        directSourceArraylist.add("Website");
        directSourceArraylist.add("Hording");
        directSourceArraylist.add("Word of mouth");
        directSourceArraylist.add("Corporates");


        milesSpos_spinnerArrayList.add("Rohini Sripada");
        milesSpos_spinnerArrayList.add("Akansha Singh ");
        milesSpos_spinnerArrayList.add("Rohan Chopra");
        milesSpos_spinnerArrayList.add("Rohini Sripada");
        milesSpos_spinnerArrayList.add("Allaka Rao");
        milesSpos_spinnerArrayList.add("Vishal Palmar");
        milesSpos_spinnerArrayList.add("Anoop Sharma");
        milesSpos_spinnerArrayList.add("Niraj Sachdev");
        milesSpos_spinnerArrayList.add("Amritha Thapar");
        milesSpos_spinnerArrayList.add("Neha Sidana");
        milesSpos_spinnerArrayList.add("Maria Divya");
        milesSpos_spinnerArrayList.add("Preeti Pawar");
        milesSpos_spinnerArrayList.add("Hadi Rajini");
        milesSpos_spinnerArrayList.add("Vidya Kaushaley");
        milesSpos_spinnerArrayList.add("Vikas Khosla");

        ConnectionTypeArrayList = new ArrayList<>();
        ConnectionTypeArrayList.add("Connected / Discussed");
        ConnectionTypeArrayList.add("Connected / Busy");
        ConnectionTypeArrayList.add("Connected / Never call back");
        ConnectionTypeArrayList.add("Connected / Wrong number");
        ConnectionTypeArrayList.add("Busy");
        ConnectionTypeArrayList.add("Not Lifting");
        ConnectionTypeArrayList.add("Not Reachable");
        ConnectionTypeArrayList.add("Disconnected");
        ConnectionTypeArrayList.add("Invalid Number");
        ConnectionTypeArrayList.add("Switched Off");

        connectionModelArrayList = new ArrayList<>();
        connectionModelArrayList.add(new ConnectionModel("CD", "Connected / Discussed"));
        connectionModelArrayList.add(new ConnectionModel("CB", "Connected / Busy"));
        connectionModelArrayList.add(new ConnectionModel("CN", "Connected / Never call back"));
        connectionModelArrayList.add(new ConnectionModel("CW", "Connected / Wrong number"));
        connectionModelArrayList.add(new ConnectionModel("B", "Busy"));
        connectionModelArrayList.add(new ConnectionModel("NL", "Not Lifting"));
        connectionModelArrayList.add(new ConnectionModel("NR", "Not Reachable"));
        connectionModelArrayList.add(new ConnectionModel("D", "Disconnected"));
        connectionModelArrayList.add(new ConnectionModel("IN", "Invalid Number"));
        connectionModelArrayList.add(new ConnectionModel("SO", "Switched Off"));


    }

    private boolean isvalidateForm() {
        if (cpaCheckbox.isChecked() && cmaCheckbox.isChecked()) {
            CoursesData = CPAChecked + "," + CPMChecked ;
        }else if (iiml_fa_checked.isChecked() && cpaCheckbox.isChecked() ) {
            CoursesData = IIMLFAChecked+ "," + CPAChecked;
        }
        else if (iiml_fa_checked.isChecked() && cmaCheckbox.isChecked() ) {
            CoursesData = IIMLFAChecked+ "," + CPMChecked;
        }
        else if (iiml_sf_checked.isChecked() && cpaCheckbox.isChecked() ) {
            CoursesData = IIMLSFCHECKED+ "," + CPAChecked;
        }
        else if (iiml_sf_checked.isChecked() && cmaCheckbox.isChecked() ) {
            CoursesData = IIMLSFCHECKED+ "," + CPMChecked;
        }
        else if (iiml_sf_checked.isChecked() && iiml_fa_checked.isChecked() ) {
            CoursesData = IIMLFAChecked+ "," + IIMLSFCHECKED;
        }
        else if (cpaCheckbox.isChecked()) {
            CoursesData = CPAChecked;
        } else if (cmaCheckbox.isChecked()) {
            CoursesData = CPMChecked;
        }
        else if (iiml_fa_checked.isChecked()) {
            CoursesData = IIMLFAChecked;
        }
        else if (iiml_sf_checked.isChecked()) {
            CoursesData = IIMLSFCHECKED;
        }
        if (CoursesData == null) {
            Toast.makeText(getApplicationContext(), "Please Select Course Type ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (engagement_description.getText().toString().isEmpty()) {
            engagement_description.setError("Please fill Engagement Type");
            return false;
        }
        /*if (date_picker_.getText().toString().isEmpty()) {
            date_picker_.setError("Please select date");
            return false;
        }*/
        return true;
    }

    private void openAlertForDatepicker() {
        datePickerDialog = new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        Calendar calendar = Calendar.getInstance();
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        if(MAX_DATE_PRE_SELECT != 0){
            calendar.add(Calendar.DAY_OF_MONTH, MAX_DATE_PRE_SELECT);
            datePickerDialog.getDatePicker().setMaxDate((calendar.getTimeInMillis()));

        }
        datePickerDialog .show();
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
        String myFormat = "dd - MM - yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date_picker_.setText(sdf.format(myCalendar.getTime()));
        try {
            Date date = sdf.parse(sdf.format(myCalendar.getTime()));
            long timestamp = date.getTime() / 1000L;
            nextCallTimeStamp = timestamp;

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        showSnakeBar("Do u want to exit from Engagement form...!");

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private void SpinnerSelection(AppCompatSpinner appconpact_spinner_levels, String levels) {

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


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.invite_webinar:
                Toast.makeText(this, ""+"invite webinar", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
