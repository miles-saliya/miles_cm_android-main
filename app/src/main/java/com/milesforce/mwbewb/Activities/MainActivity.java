package com.milesforce.mwbewb.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.RecoverableSecurityException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.milesforce.mwbewb.BotttomNavigationFragments.CallLogFragment;
import com.milesforce.mwbewb.BotttomNavigationFragments.WorkLogFragment;
import com.milesforce.mwbewb.EngagementFragments.LevelsCustomAdapter;
import com.milesforce.mwbewb.Model.LevelsModel;
import com.milesforce.mwbewb.Model.MobileNumberModel;
import com.milesforce.mwbewb.Model.ULevelModel;
import com.milesforce.mwbewb.Model.UserToken;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;
import com.milesforce.mwbewb.Retrofit.CommanApiClient;
import com.milesforce.mwbewb.Retrofit.CommanApiUtills;
import com.milesforce.mwbewb.Retrofit.NinjaApiClient;
import com.milesforce.mwbewb.Retrofit.NinjaApiUtills;
import com.milesforce.mwbewb.Retrofit.SpocUtilizationModel;
import com.milesforce.mwbewb.Retrofit.SuccessModel;
import com.milesforce.mwbewb.LocalCallRecordingsActivity;
import com.milesforce.mwbewb.Utils.BatterPercentage;
import com.milesforce.mwbewb.Utils.BatteryModel;
import com.milesforce.mwbewb.Utils.CallRecord;
import com.milesforce.mwbewb.Utils.ConstantUtills;
import com.milesforce.mwbewb.Utils.GetCallLogService;
import com.milesforce.mwbewb.Utils.ResumeWork;
import com.milesforce.mwbewb.Utils.SyncAllMyCallRecords;
import com.milesforce.mwbewb.Utils.Tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.milesforce.mwbewb.Utils.ConstantUtills.AccessToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.Bde_user_id;
import static com.milesforce.mwbewb.Utils.ConstantUtills.IIML_TAB_CHANGE_CODE;
import static com.milesforce.mwbewb.Utils.ConstantUtills.SaveToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.USER_NAME;
import static com.milesforce.mwbewb.Utils.ConstantUtills.VERSION_NUMBER;
import static com.milesforce.mwbewb.Utils.PusherClass.UnScribeChannel;
import static com.milesforce.mwbewb.Utils.PusherClass.subscribeChannel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int AUDIO_RECORDER_FOLDER = 0;
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
    TextView txtversion;
    Calendar myCalendar, LeadsCalendar;
    AppCompatImageButton bt_menu_back_from_caller, addLeadForm, search_icon, work_status;
    ImageView endCall_btn, addEngagementForm;
    public static final int PERMISSION_ACCESS_CALL_PHONE = 20;
    RadioGroup radioGroupForReference;
    LinearLayout referal, direct, referal_layout, reference_layout_spinner, corporate_company;
    AppCompatSpinner ewbspinner, mwbspinner, M_levels_spinner, U_levels_spinner, city_spinner, direct_spinner, milesSpos_spinner, appconpact_spinner_connectionstatus;
    ArrayList<LevelsModel> spinner_M_LevelList;
    EditText direct_reference;

    ArrayList<String> levelsArrayList = new ArrayList<>();
    ArrayList<String> ewbArraylist = new ArrayList<>();
    ArrayList<String> mwbArrayList = new ArrayList<>();
    ArrayList<String> cityArrayList = new ArrayList<>();
    ArrayList<String> directSourceArraylist = new ArrayList<>();
    ArrayList<String> milesSpos_spinnerArrayList = new ArrayList<>();
    ArrayList<String> ConnectionTypeArrayList = new ArrayList<>();
    CallRecord callRecord;
    ApiClient apiClient;
    CommanApiClient commanApiClient;
    NinjaApiClient ninjaApiClient;
    SharedPreferences sharedPreferences, localTimeSharedPreference;
    String bde_user_id;
    Handler pusherHandler;
    Handler CallDataHandler;
    Handler callRecordingHandler;
    Runnable PusherRunnable;
    Runnable callLogRunnable;
    Runnable callRecordingRunnable;
    String accessToken;

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
    String IIML_FA_Checked_lead = " ";
    String IIML_SF_Checked_lead = " ";
    String DAChecked_lead = " ";
    String LEADSOURCE = " ";
    String LeadDetails = " ";
    String LEADCity = " ";
    String LeadLevels = " ";
    String RPA_Checked_lead = " ";
    String FOFO_Checked_lead = " ";
    // String MWbLeadCourseData = "CPA";
    String MWbLeadCourseData = "IIML-BA";
    BatteryModel batteryModel;
    static String SELECTED_STATUS = " ";
    static String ConnectionStatus = " ";
    AppCompatAutoCompleteTextView autoCompleteText_mwb_b2c;
    Realm realm;
    File file_to_upload;
    String LAST_TENDIGIT_MOBILE_NUMBER = null;
    GoogleSignInClient mGoogleSignInClient;
    private final String[] corporates = {
            "Accenture",
            "AIG",
            "American Express",
            "Aon Hewitt",
            "BMC Software",
            "Deloitte & Touche (DTT-US) - AUDIT",
            "EY Assurance",
            "Flex",
            "JP Morgan",
            "KPMG GDC",
            "VMware",
            "Genpact",
            "MacMillan",
            "Maersk Global Services",
            "Metlife US",
            "Prime Healthcare",
            "World Bank",
            "Advantage One Tax",
            "EA Sports",
            "Philips",
            "Tata Power",
            "Alexion Pharmaceuticals Inc",
            "Axa Business Services",
            "Barclays",
            "Capgemini",
            "Citrin Cooperman",
            "Diageo",
            "EY Tax",
            "FIS",
            "Ford Motors",
            "Grant Thornton (US TAX)",
            "Infoblox",
            "Invesco",
            "Johnson & Johnson",
            "Max Life Insurance (Previously, New York life Insurance )",
            "PWC (SDC-US)",
            "Qualcomm",
            "Redhat",
            "Sendan KSA",
            "GE Capital / Synchrony",
            "United Health Group (UHG)",
            "WNS Global Services (P) Ltd",
            "TE Connectivity",
            "BDO LLP",
            "Cognizant",
            "Continuum Managed Solutions Private Limited",
            "DuPont",
            "EXL Services",
            "IHG",
            "Allergan India",
            "Blackbuck",
            "Avaya",
            "Juniper Networks",
            "CGI",
            "Taxevo Inc",
            "AEON",
            "Flowserve",
            "Amazon",
            "Zenwork",
            "Sintex"};

    private String[] universities = {"Jain University",
            "St Joseph's College Of Commerce",
            "Dayananda Sagar University",
            "Christ University",
            "Kristu Jayanti College",
            "Garden City University",
            "Cambridge Group of Institutions",
            "Mount Carmel College",
            "St Joseph's College Of Commerce (Lalbagh)",
            "ISBR College",
            "Guukul Institute of Management",
            "Presidency University",
            "Jyoti Nivas",
            "Jagran Lakecity University",
            "Bhopal School of Social Studies",
            "Chitkara University",
            "Loyola College",
            "Dwaraka Doss Goverdhan Doss Vaishnav College",
            "SRM Institute Of Science And Technology",
            "SRM ITD",
            "B. S. Abdur Rahman Crescent Institute Of Science And Technology",
            "Ethiraj College",
            "Graphic Era University",
            "Manava Rachana University",
            "KL University",
            "Amity University",
            "Loyola Academy Degree And Pg College",
            "Government City College",
            "St.Ann’s College For Women",
            "Hindi Mahavidyala",
            "Daly College Business School",
            "IIS University",
            "Kanoria PG Mahila Mahavidyalaya",
            "Kaziranga University",
            "Adamas University",
            "CMS",
            "Amity University",
            "Lovely Professional University",
            "BMCC",
            "ASM Group Of Institutes",
            "Ness Wadia College of Commerce",
            "Shikshan Prasarak Mandal’s Sir Parashurambhau College",
            "Dr. D. Y. Patil Institute of Management & Research",
            "The Poona Gujarati Kelvani Mandal's Haribhai V. Desai College",
            "Pratibha College of Commerce And Computer Studies",
            "Indira College of Commerce and Science",
            "MMCC college",
            "MATS University",
            "Auro University",
            "Naipunnya Institute Of Management",
            "Graphic Era Hill University",
            "Kejriwal Institute of Management & Development Studies",
            "Doon Business School",
            "BFIT",
            "Uttaranchal University",
            "RIMT",
            "St Alberts College"};


    TextView compose_text, preview_text, title_header, textview_header, utilization_, calls_;
    LinearLayout preview_layout, compose_layout;
    EditText subject_edit, compose_edit, content_whatsUp;
    WebView webView;
    AssetManager assetManager;
    String PreviewData;
    RadioGroup responce_radio_group;
    String RadioBtnResponseType = null;
    EditText engagement_description;
    AppCompatCheckBox b2c_cpa_check, b2c_cma_check,
            b2c_iiml_fa_check,
            b2c_iiml_ba_check, b2c_iiml_pa_check, b2c_iiml_hr_check, b2c_iitr_bf_check,
            b2c_iitr_dbe_check, b2c_iimlfa_check, b2c_iimlsf_check, b2c_CFA_check, b2c_FRM_check, b2c_USP_check;

    static String CPAChecked = " ";
    static String CPMChecked = " ";
    static String DaChecked = " ";
    static String WCBAChecked = " ";
    static String IIMLChecked = " ";
    static String IIML_FA_Checked = " ";
    static String IIML_BA_Checked = " ";
    static String IIML_PA_Checked = " ";
    static String IIML_HR_Checked = " ";
    static String IITR_BF_Checked = " ";
    static String IITR_DBE_Checked = " ";
    static String CFA_Checked = " ";
    static String FRM_Checked = " ";
    static String USP_Checked = " ";
    static String CoursesData = null;
    int person_id, can_id, Mobile_id;

    String previousEngagement, courses, levels, user_name, person_name;
    EditText latestEngagement;
    LinearLayout nextTimeLayout, engagement_main_form;
    String LevelsSelected;
    private static long nextCallTimeStamp = 0;
    ProgressBar add_engagement_progress;
    AlertDialog alert;
    EditText b2c_lead_name, b2c_lead_mobile, b2c_lead_email, b2c_lead_education, b2c_lead_company, b2c_lead_designation, b2c_lead_experiance, b2c_lead_engagement, b2c_lead_nextCall_picker_, b2c_international, b2c_country;
    EditText b2bcr_leadname, b2bcr_leadmobile, b2bcr_lead_email, b2b_cr_lead_company, b2b_cr_lead_designation, b2b_cr_lead_experiance, b2b_cr_lead_engagement, direct_reference_cr;
    EditText lead_b2b_ir_name, lead_b2b_ir_mobile, lead_b2b_ir_email, lead_b2b_ir_institute, lead_b2b_ir_designation, lead_b2b_ir_engagement, lead_b2b_ir_reference;

    LinearLayout user_info_snakebar;
    ProgressBar add_b2b_progresss, b2b_cr_progress, b2b_ir_progress;
    String other_check_radio_text;

    RadioButton IncomingCals_radio, referal_radio, IVR_radio;
    LevelsCustomAdapter levelsCustomAdapter;
    UserToken userToken;
    GetCallLogService getCallLogService;
    static ArrayAdapter<String> cityAdpater;

    static LinearLayout iiml_course_layout, ba_course_layout;


    String directoryPath;
    int currentapiVersion = android.os.Build.VERSION.SDK_INT;
    int versioncode = android.os.Build.VERSION_CODES.R;

    //my selff
    LinearLayout interested_Layout, looking_job;

    AppCompatSpinner appconpact_spinner_interested_working, appconpact_spinner_looking_job, appconpact_spinner_graduation_Year,
            appconpact_spinner_global_Professional_Qualification, appconpact_spinner_UG_Qualification, appconpact_spinner_PG_Qualification;


    static String SELECTED_STATUS_interested_working = "";

    ArrayList<String> spinner_interested_workingArrayList = new ArrayList<String>();

    ArrayList<String> spinnerLookingJob_workingArrayList = new ArrayList<String>();


    EditText work_Ex_profiling, company_Name, work_Experience, edt_current_location;
    LinearLayout current_location;
    TextView indian_Professional, global_Professional_Qualification, ug_Graduate_Qualification, pg_Graduate_Qualification;
    boolean[] selectedIndian_Professional, selectGlobal_Professional, selectUG_Graduate_Qualification, selectPG_Graduate_Qualification;
    ArrayList<Integer> indianProfessionalLangList = new ArrayList<>();
    ArrayList<Integer> globalProfessionalLangList = new ArrayList<>();

    ArrayList<Integer> UG_Graduate_QualificationLangList = new ArrayList<>();

    ArrayList<Integer> PG_Graduate_QualificationLangList = new ArrayList<>();

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
    List<String> MLevelList = new ArrayList<>();
//    List<String> uLevelList = new ArrayList<>();

    List<ULevelModel> uLevelListModel = new ArrayList<>();
    final String[] uLevelList = {
            " ",
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

//      spinnerLevelList.add(new LevelsModel("M3++", "M3++ - Ready to enroll - Not visited"));
//        spinnerLevelList.add(new LevelsModel("M3+", "M3+ - Called & Coming"));
//        spinnerLevelList.add(new LevelsModel("M3", "M3 - Called & positive"));
//        spinnerLevelList.add(new LevelsModel("M2", "M2 - Did not visit & postponed"));
//        spinnerLevelList.add(new LevelsModel("M1", "M1 - Did not visit & not intersted"));

//    final String[] mLevelList = {
//            " ",
//            "M3++ - Ready to enroll - Not visited",
//            "M3+ - Called & Coming",
//            "M3 - Called & positive",
//            "M2 - Did not visit & postponed",
//            "M1 - Did not visit & not intersted",
//    };
//    narendra.podugu@serialxtech.com
//    Podugu@1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_main);
//        txtversion = findViewById(R.id.txtversion);

        apiClient = ApiUtills.getAPIService();
        commanApiClient = CommanApiUtills.getAPIService();
        ninjaApiClient = NinjaApiUtills.getAPIService();
        sharedPreferences = getApplicationContext().getSharedPreferences(SaveToken, MODE_PRIVATE);
        localTimeSharedPreference = getApplicationContext().getSharedPreferences("timestamp", MODE_PRIVATE);
        accessToken = sharedPreferences.getString(AccessToken, null);
        bde_user_id = sharedPreferences.getString(Bde_user_id, null);
        batteryModel = new BatterPercentage().getBattertPercentage(getApplicationContext());
        //  initToolbar();
        initNavigationMenu();
        Tools.setSystemBarColor(this);
        initComponent();
        getUtilizationData(accessToken);
        InterestedWorkingArrayList();
        LookingJobWorkingArrayList();
        CustomDataPOints();

        if (realm.where(UserToken.class).findFirst() != null) {
            userToken = realm.where(UserToken.class).findFirst();

        }


        /*if (bde_user_id != null) {
            getPubNumServie(bde_user_id);
        } else {
            getUserToken(accessToken);
        }*/
        try {
            String data = getIntent().getStringExtra("engagement");
            if (data.equals("engagement")) {
                person_id = getIntent().getExtras().getInt("person_id");
                can_id = getIntent().getExtras().getInt("can_id");
                previousEngagement = getIntent().getExtras().getString("previousEngagement");
                courses = getIntent().getExtras().getString("courses");
                levels = getIntent().getExtras().getString("levels");
                user_name = getIntent().getExtras().getString("user_name");
                Mobile_id = getIntent().getExtras().getInt("mobile_id");
                person_name = getIntent().getExtras().getString("person_name");
                String token_new = getIntent().getExtras().getString("token_new");
                //   showCustomDialog(token_new, levels, courses, previousEngagement);
                LevelsSelected = levels;
                CoursesData = courses;

            }
        } catch (Exception e) {

        }

//        spinnerLevelList = new ArrayList<>();
       /* spinnerLevelList.add(new LevelsModel("M6", "M6 - Visited & Ready to Enroll"));
        spinnerLevelList.add(new LevelsModel("M5", "M5 - Visited & Positive"));
        spinnerLevelList.add(new LevelsModel("M4", "M4 - Visited but not interested"));
        spinnerLevelList.add(new LevelsModel("M4-", "M4- - Visited but not interested"));
        spinnerLevelList.add(new LevelsModel("M3+", "M3+ - Called & Coming"));*/
//        spinnerLevelList.add(new LevelsModel("M3++", "M3++ - Ready to enroll - Not visited"));
//        spinnerLevelList.add(new LevelsModel("M3+", "M3+ - Called & Coming"));
//        spinnerLevelList.add(new LevelsModel("M3", "M3 - Called & positive"));
//        spinnerLevelList.add(new LevelsModel("M2", "M2 - Did not visit & postponed"));
//        spinnerLevelList.add(new LevelsModel("M1", "M1 - Did not visit & not intersted"));

      /*  spinnerLevelList.add(new LevelsModel("M2", "M2 - Did not visit & Postponed"));
        spinnerLevelList.add(new LevelsModel("M1", "M1 - Did not visit & not interested"));*/
       /* spinnerLevelList.add(new LevelsModel("L6", "L6 - Visited & Ready to Enroll(last Batch) :but did not join"));
        spinnerLevelList.add(new LevelsModel("L5", "L5 - Visited & Positive (Last Batch); but did not join"));
        spinnerLevelList.add(new LevelsModel("L4", "L4 - Visited (Last Batch); but postponed & did not join"));
        spinnerLevelList.add(new LevelsModel("L4-", "L4- - Visited (Last batch); 'NL/S.off' or 'Not Interested'"));
        spinnerLevelList.add(new LevelsModel("L3+", "L3+ - Called & Coming (Last batch)"));
        spinnerLevelList.add(new LevelsModel("L3", "L3 - Called & Positive (Last Batch); but did not visit"));
        spinnerLevelList.add(new LevelsModel("L2", "L2 - Called (Last Batch); but did not visit & postponed"));
        spinnerLevelList.add(new LevelsModel("L1", "L1 - Called (Last batch); 'NL/S.off' or 'Not Interested' "));
*/
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
        cityArrayList.add("Bangalore");
        cityArrayList.add("Chennai");
        cityArrayList.add("Ernakulam");
        cityArrayList.add("Ahmedabad");
        cityArrayList.add("International");
        cityArrayList.add("Global");
        cityArrayList.add("Pune");
        cityArrayList.add("Kolkata");
        cityArrayList.add("Jaipur");


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






        /*  *//*Call Record Service *//*
        callRecord = new CallRecord.Builder(this)
                .setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION)
                .setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
                .setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB) // optional & default value
                // optional & default value
                // optional & default value
                // optional & default value ->Ex: RecordFileName_incoming.amr || RecordFileName_outgoing.amr
                .build();
        callRecord.startCallReceiver();*/
        /*Checking pusher status for every 5 seconds*/
        checkPusherStatus();
        //  SendCallLOgForEveryMinute(userToken.getAccessToken());
        SendCallRecordingForEveryTenMinutes();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken("360383923261-ppk7gerj3afi09g091ks366cuhv82nsj.apps.googleusercontent.com")
                .setHostedDomain("mileseducation.com")
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

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


    private void SendCallRecordingForEveryTenMinutes() {
        callRecordingHandler = new Handler();
        callRecordingHandler.postDelayed(callRecordingRunnable = new Runnable() {
            @Override
            public void run() {
//                CallRecordingAsync callRecordingAsync = new CallRecordingAsync();
//                callRecordingAsync.execute();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    UploadFilesToServer();
                }
                callRecordingHandler.postDelayed(callRecordingRunnable, 180000);

            }
        }, 180000);


    }


    private void SendCallLOgForEveryMinute(String accessToken) {
        Log.d("for_every_call", accessToken);
        CallDataHandler = new Handler();
        CallDataHandler.postDelayed(callLogRunnable = new Runnable() {
            @Override
            public void run() {
                Log.d("for_every_call", String.valueOf(localTimeSharedPreference.getString("eventTime", null)));
                if (localTimeSharedPreference.getString("eventTime", null) != null) {
                    getCallLogService = new GetCallLogService();
                    getCallLogService.getCallLog(getApplicationContext());
                }
                CallDataHandler.postDelayed(callLogRunnable, 12000);
            }
        }, 12000);

    }

    private void getUtilizationData(String accessToken) {
        apiClient.getSpocUtilization("Bearer " + accessToken, "application/json").enqueue(new Callback<SpocUtilizationModel>() {
            @Override
            public void onResponse(Call<SpocUtilizationModel> call, Response<SpocUtilizationModel> response) {
                // String data = String.valueOf(response.body());
                try {
                    if (response.raw().code() == 515) {
                        new ResumeWork().stResumeWork(MainActivity.this);
                        return;
                    }
                    utilization_.setText("  EF - " + response.body().getCall_utilization());
                    calls_.setText(" | " + response.body().getNo_of_calls() + " Calls ");
                    Toast.makeText(MainActivity.this, "UT Updated", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    // Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SpocUtilizationModel> call, Throwable t) {
                // Toast.makeText(MainActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SaveContactProgramatically() {

    }


    private void checkPusherStatus() {
        pusherHandler = new Handler();
        pusherHandler.postDelayed(PusherRunnable = new Runnable() {
            @Override
            public void run() {
                if (ConstantUtills.PUSHER_STATUS.equals("DISCONNECTED")) {
                    if (isConnected()) {
                        Log.d("pusher_status", "status Check connected");
                        // getPubNumServie(bde_user_id);
                        subscribeChannel();
                    }

                }
                pusherHandler.postDelayed(PusherRunnable, 5000);
            }

        }, 5000);
    }

   /* private void getUserToken(String accessToken) {
        apiClient.getUserIdForPusherSubscription("Bearer " + accessToken, "application/json").enqueue(new Callback<ModelForId>() {
            @Override
            public void onResponse(Call<ModelForId> call, Response<ModelForId> response) {
                try {
                    int numb = response.body().getUser_id();
                    bde_user_id = String.valueOf(numb);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Bde_user_id, bde_user_id);
                    editor.commit();
                    getPubNumServie(bde_user_id);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelForId> call, Throwable t) {

            }
        });
    }*/

   /* private void getPubNumServie(String mobileNumber) {
        InstalisedPusher(mobileNumber, getApplicationContext());
    }*/

    private void initNavigationMenu() {
        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        View headerView = nav_view.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.email_header_view);
        navUsername.setText(sharedPreferences.getString(USER_NAME, null));

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       /* ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();*/
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_addlead:
                        openAddLeadForm();
                        break;
                    case R.id.nav_syncMyRecords:
                        new SyncAllMyCallRecords().SyncMyCallLog(MainActivity.this, accessToken);
                        Log.d("MyApp", "nav_syncMyRecords selected");
                        startActivity(new Intent(getApplicationContext(), MyCallRecordsActivity.class));
                        break;
                    case R.id.nav_syncMyCallData:
                        new SyncAllMyCallRecords().SyncMyCallLog(MainActivity.this, accessToken);
                        break;
                    case R.id.nav_update_UT:
                        new SyncAllMyCallRecords().SyncMyCallLog(MainActivity.this, accessToken);
                        getUtilizationData(accessToken);
                        break;
                    case R.id.nav_logout:
                        stopUserSession(accessToken);
                        break;
                    case R.id.nav_b2c:
                        openAddLeadForm();
                        break;
                    case R.id.nav_b2b_cr:
                        //  openAddLeadForB2BCr();
                        break;
                    case R.id.nav_b2b_ir:
                        //  openAddLeadFormFroB2bIr();
                        break;
                    case R.id.ewb:
                        startActivity(new Intent(MainActivity.this, EWBSRActivity.class));
                        break;
                    case R.id.testing_sync:
                        new SyncAllMyCallRecords().SyncMyCallLog(MainActivity.this, accessToken);
                        startActivity(new Intent(MainActivity.this, LocalCallRecordingsActivity.class));
                        break;
                    case R.id.add_classs:
                        startActivity(new Intent(MainActivity.this, AddClassActivity.class));
                        break;
                    case R.id.register_student_pic:
                        startActivity(new Intent(MainActivity.this, RegisteredStudent.class));
                        break;
                    case R.id.take_attendance:
                        startActivity(new Intent(MainActivity.this, TakeAttendance.class));
                        break;

                }

                // actionBar.setTitle(item.getItemId());
                drawer.closeDrawers();
                isOpen = false;
                return true;
            }
        });

        // open drawer at start
//        drawer.openDrawer(GravityCompat.START);
    }

    private void stopUserSession(final String accessToken) {
        apiClient.updateWorkingStatus("stopped", "Bearer " + accessToken).enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                try {
                    if (response.body().getStatus().equals("success")) {
                        Toast.makeText(MainActivity.this, "Successfully updated!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.getMessage();
                }

            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(AccessToken);
        editor.remove(Bde_user_id);
        editor.apply();
        UnScribeChannel();
        deleteRealmData();
        try {
            mGoogleSignInClient.signOut();
        } catch (Exception e) {

        }
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();

    }

    private void deleteRealmData() {
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<UserToken> result = realm.where(UserToken.class).findAll();
                    result.clear();


                }
            });
        } catch (Exception e) {

        }
    }

    /* private void initToolbar() {
         toolbar = (Toolbar) findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);
         actionBar = getSupportActionBar();
         actionBar.setDisplayHomeAsUpEnabled(true);
         actionBar.setHomeButtonEnabled(true);
         getSupportActionBar().setDisplayShowTitleEnabled(false);
         Tools.setSystemBarColor(this);
     }*/

    private void initComponent() {
        search_bar = (View) findViewById(R.id.search_bar);
        addLeadForm = findViewById(R.id.addLeadForm);
        work_status = findViewById(R.id.work_status);
        work_status.setOnClickListener(this);
        addLeadForm.setOnClickListener(this);
        search_icon = findViewById(R.id.search_icon);
        search_icon.setOnClickListener(this);
        user_info_snakebar = findViewById(R.id.user_info_snakebar);
        utilization_ = findViewById(R.id.utilization_);
        calls_ = findViewById(R.id.calls_);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_call:
                        mCurrentTab = 0;
                        fragmentTransaction.replace(R.id.container_view, new CallLogFragment());
                        fragmentTransaction.commit();
                        break;
                    case R.id.navigation_work:
                        mCurrentTab = 1;
                        fragmentTransaction.replace(R.id.container_view, new WorkLogFragment());
                        fragmentTransaction.commit();
                        break;
                }
                return true;
            }
        });


        // display image
        ((AppCompatImageButton) findViewById(R.id.bt_menu)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpen) {
                    drawer.openDrawer(GravityCompat.START);
                    isOpen = true;
                } else {
                    drawer.closeDrawers();
                    isOpen = false;
                }

            }
        });


        Tools.setSystemBarColor(this, R.color.grey_5);
        Tools.setSystemBarLight(this);
        performTabClick(mCurrentTab);
    }

    private void performTabClick(int tab) {
        View view;
        if (tab == 0) view = bottomNavigationView.findViewById(R.id.navigation_call);
        else if (tab == 1) view = bottomNavigationView.findViewById(R.id.navigation_work);
        else view = bottomNavigationView.findViewById(R.id.navigation_call);
        view.performClick();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    private void initViews() {
        bt_menu_back_from_caller = findViewById(R.id.bt_menu_back_from_caller);
        bt_menu_back_from_caller.setOnClickListener(this);
        endCall_btn = findViewById(R.id.endCall_btn);
        endCall_btn.setOnClickListener(this);
        addEngagementForm = findViewById(R.id.addEngagementForm);
        addEngagementForm.setOnClickListener(this);
    }

/*    private void showCustomDialog(final String user_accessToken, final String levels, final String courses, final String previousEngagement) {
        sharedPreferences = getApplicationContext().getSharedPreferences(SaveToken, MODE_PRIVATE);
        final String accessToken_new = sharedPreferences.getString(AccessToken, null);
        spinnerLevelList = new ArrayList<>();
        spinnerLevelList.add("M1");
        spinnerLevelList.add("M2");
        spinnerLevelList.add("M3");
        spinnerLevelList.add("M4");
        spinnerLevelList.add("M5");
        spinnerLevelList.add("M6");
        spinnerLevelList.add("M7");
        spinnerLevelList.add("L1");
        spinnerLevelList.add("L2");
        spinnerLevelList.add("L3");
        spinnerLevelList.add("L4");
        spinnerLevelList.add("L5");
        spinnerLevelList.add("L6");
        spinnerLevelList.add("L7");

        ConnectionTypeArrayList = new ArrayList<>();
        ConnectionTypeArrayList.add("Not Reachable");
        ConnectionTypeArrayList.add("Not Lifting");
        ConnectionTypeArrayList.add("Switched Off");
        ConnectionTypeArrayList.add("Disconnected");
        ConnectionTypeArrayList.add("Busy");
        ConnectionTypeArrayList.add("Connected / Never call back");
        ConnectionTypeArrayList.add("Connected / Busy");
        ConnectionTypeArrayList.add("Connected / Discussed");
        myCalendar = Calendar.getInstance();

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_event);
        dialog.setCancelable(true);
        nextTimeLayout = dialog.findViewById(R.id.nextTimeLayout);
        engagement_main_form = dialog.findViewById(R.id.engagement_main_form);
        latestEngagement = dialog.findViewById(R.id.latestEngagement);
        latestEngagement.setText(previousEngagement);
        textview_header = dialog.findViewById(R.id.textview_header);
        date_picker_ = dialog.findViewById(R.id.date_picker_);
        date_picker_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlertForDatepicker();
            }
        });
        textview_header.setText(person_name + " - " + levels);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        appconpact_spinner_levels = dialog.findViewById(R.id.appconpact_spinner_levels);
        appconpact_spinner_connectionstatus = dialog.findViewById(R.id.appconpact_spinner_connectionstatus);
        add_engagement_progress = dialog.findViewById(R.id.add_engagement_progress);
        responce_radio_group = dialog.findViewById(R.id.responce_radio_group);
        engagement_description = dialog.findViewById(R.id.engagement_description);

        cpaCheckbox = dialog.findViewById(R.id.cpa_checked);
        cmaCheckbox = dialog.findViewById(R.id.cma_checked);
        dacheckbox = dialog.findViewById(R.id.da_checked);

        //  selectDatapicker(dialog);
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();
        dialog.getWindow().setAttributes(lp);

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
        dacheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    DaChecked = "DA";
                } else {
                    DaChecked = " ";
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

        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ((Button) dialog.findViewById(R.id.bt_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  dialog.dismiss();
                if (isvalidateForm()) {
                    if (CoursesData == null) {
                        CoursesData = "CPA";
                    }
                    if (LevelsSelected == null) {
                        LevelsSelected = "L1";
                    }


                    add_engagement_progress.setVisibility(View.VISIBLE);
                    apiClient.AddEngagement("", can_id, person_id, person_name, LevelsSelected, CoursesData, engagement_description.getText().toString().trim(), "call", 0, nextCallTimeStamp, "Yes", 0, batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), "Bearer " + user_accessToken, "application/json").enqueue(new Callback<SuccessModel>() {
                        @Override
                        public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                            try {
                                if (response.body().getStatus().equals("success")) {
                                    dialog.dismiss();
                                    add_engagement_progress.setVisibility(View.GONE);
                                    openAlert(response.body().getMessage());
                                } else {
                                    dialog.dismiss();
                                    add_engagement_progress.setVisibility(View.GONE);
                                    openAlert(response.body().getMessage());
                                }

                            } catch (Exception e) {
                                dialog.dismiss();
                                e.printStackTrace();
                                add_engagement_progress.setVisibility(View.GONE);
                                openAlert(e.getMessage());
                            }

                        }

                        @Override
                        public void onFailure(Call<SuccessModel> call, Throwable t) {
                            t.printStackTrace();
                            add_engagement_progress.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Some Thing went wrong please try after some time ", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            openAlert(t.getMessage());
                        }
                    });
                }
            }
        });

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerLevelList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        appconpact_spinner_levels.setAdapter(dataAdapter);

        appconpact_spinner_levels.setSelection(spinnerLevelList.indexOf(levels));


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
                    String text = appconpact_spinner_connectionstatus.getSelectedItem().toString();
                    if (appconpact_spinner_connectionstatus.getSelectedItem().toString().equals("Connected / Discussed")) {
                        engagement_main_form.setVisibility(View.VISIBLE);
                        engagement_description.setText(" ");
                    } else {
                        engagement_main_form.setVisibility(View.GONE);
                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        String formattedDate = df.format(c);

                        if (previousEngagement.contains(" | ")) {
                            int index = previousEngagement.indexOf(" | ");
                            String Result = previousEngagement.substring(index + 2);
                            String resul = Result;
                            engagement_description.setText(formattedDate + " - " + text + " | " + Result);

                        } else {
                            engagement_description.setText(formattedDate + " - " + text + " | " + previousEngagement);
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


        if (courses.equals("CPA,CMA,DA")) {
            cpaCheckbox.setChecked(true);
            cmaCheckbox.setChecked(true);
            dacheckbox.setChecked(true);
        }
        if (courses.equals("CPA,CMA")) {
            cpaCheckbox.setChecked(true);
            cmaCheckbox.setChecked(true);
        }
        if (courses.equals("CMA,DA")) {
            cmaCheckbox.setChecked(true);
            dacheckbox.setChecked(true);
        }
        if (courses.equals("CPA,DA")) {
            cpaCheckbox.setChecked(true);
            dacheckbox.setChecked(true);
        }

        if (courses.equals("CPA")) {
            cpaCheckbox.setChecked(true);
        }
        if (courses.equals("CMA")) {
            cmaCheckbox.setChecked(true);
        }
        if (courses.equals("DA")) {
            dacheckbox.setChecked(true);
        }


    }*/

    private void selectDatapicker(Dialog dialog) {
        date_picker_ = dialog.findViewById(R.id.date_picker_);
        date_picker_.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addLeadForm:
                // openAddLeadForm();
                openLeadOptionsForm();
                break;
            case R.id.search_icon:
                startActivity(new Intent(MainActivity.this, SearchClient.class));
                break;
            case R.id.work_status:
                OpenWorkStatus();
                break;


        }
    }

    private void OpenWorkStatus() {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, work_status);
        popupMenu.getMenuInflater().inflate(R.menu.work_options, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.take_break:
                        OpenAlertForWorkStatus("paused", 1);
                        break;
                    case R.id.leave_for_day:
                        OpenAlertForWorkStatus("stopped", 0);
                        break;


                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void OpenAlertForWorkStatus(String stopped, final int i) {
        final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create(); //Read Update
        alertDialog.setTitle("Confirm");
        if (i == 0) {
            alertDialog.setMessage("Are you sure you want to stop?");
        }
        if (i == 1) {
            alertDialog.setMessage("Are you sure you want to pause?");
        }
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (i == 0) {
                    stopUserSession(accessToken);
                }
                if (i == 1) {
                    UpdadtingWorkingStatus("paused", 1);
                }
            }
        });
        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();

            }
        });
        alertDialog.show();

    }

    private void UpdadtingWorkingStatus(String status, int i) {
        apiClient.updateWorkingStatus(status, "Bearer " + accessToken).enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                try {
                    if (response.raw().code() == 515) {
                        Toast.makeText(MainActivity.this, String.valueOf(response.body().getMessage()), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (response.body().getStatus().equals("success")) {
                        new ResumeWork().stResumeWork(MainActivity.this);

                    } else {
                        Toast.makeText(MainActivity.this, String.valueOf(response.body().getMessage()), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {

            }
        });
    }

    private void openLeadOptionsForm() {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, addLeadForm);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.b2c:
                        openAddLeadForm();
                        break;
//                    case R.id.b2b_cr:
//                        //  openAddLeadForB2BCr();
//                        break;
//                    case R.id.b2b_ir:
//                        //  openAddLeadFormFroB2bIr();
//                        break;


                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void openAddLeadFormFroB2bIr() {
        LEADSOURCE = " ";
        LeadDetails = " ";
        LeadDetails = " ";
        LEADCity = " ";
        other_check_radio_text = " ";
        LeadsCalendar = Calendar.getInstance();
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.add_leadforb2b_ir);
        dialog.setCancelable(true);
        referal_radio = dialog.findViewById(R.id.referal_radio);
        IVR_radio = dialog.findViewById(R.id.IVR_radio);
        IncomingCals_radio = dialog.findViewById(R.id.IncomingCals_radio);

        corporate_company = dialog.findViewById(R.id.corporate_company_b2b_ir);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
        referal = dialog.findViewById(R.id.reference_layout);
        direct = dialog.findViewById(R.id.direct_layout);
        radioGroupForReference = dialog.findViewById(R.id.reference_radio_group);
        reference_layout_spinner = dialog.findViewById(R.id.reference_layout_spinner);
        ewbspinner = dialog.findViewById(R.id.MwbSpinner);
        mwbspinner = dialog.findViewById(R.id.ewbSpinner);
        direct_spinner = dialog.findViewById(R.id.direct_spinner);
        city_spinner = dialog.findViewById(R.id.city_spinner);
        milesSpos_spinner = dialog.findViewById(R.id.milesSpos_spinner);
        lead_b2b_ir_name = dialog.findViewById(R.id.lead_b2b_ir_name);
        lead_b2b_ir_mobile = dialog.findViewById(R.id.lead_b2b_ir_mobile);
        lead_b2b_ir_email = dialog.findViewById(R.id.lead_b2b_ir_email);
        lead_b2b_ir_institute = dialog.findViewById(R.id.lead_b2b_ir_institute);
        lead_b2b_ir_designation = dialog.findViewById(R.id.lead_b2b_ir_designation);
        lead_b2b_ir_engagement = dialog.findViewById(R.id.lead_b2b_ir_engagement);
        b2c_lead_nextCall_picker_ = dialog.findViewById(R.id.lead_b2b_ir_timepicker);
        lead_b2b_ir_reference = dialog.findViewById(R.id.lead_b2b_ir_reference);
        b2b_ir_progress = dialog.findViewById(R.id.b2b_ir_progress);
        autoCompleteText_mwb_b2c = dialog.findViewById(R.id.autoCompleteText_mwb_b2c);
        b2c_lead_nextCall_picker_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, leads_date, LeadsCalendar.get(Calendar.YEAR), LeadsCalendar.get(Calendar.MONTH), LeadsCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
                saveMwbLeadBEBIR(dialog);

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
                    direct.setVisibility(View.VISIBLE);
                    reference_layout_spinner.setVisibility(View.GONE);
                    LEADSOURCE = "IVR";

                    referal_radio.setChecked(false);
                    corporate_company.setVisibility(View.GONE);
                }
            }
        });
//        corporate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    referal.setVisibility(View.GONE);
//                    direct.setVisibility(View.GONE);
//                    reference_layout_spinner.setVisibility(View.GONE);
//                    LEADSOURCE = "Corporate";
//                    netenquiery.setChecked(false);
//                    direct_radio.setChecked(false);
//                    referal_radio.setChecked(false);
//                    university.setChecked(false);
//                    corporate_company.setVisibility(View.VISIBLE);
//                    autoCompleteText_mwb_b2c.setText("");
//                    getAutoCompleteData();
//                }
//            }
//        });
//        university.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    referal.setVisibility(View.GONE);
//                    direct.setVisibility(View.GONE);
//                    reference_layout_spinner.setVisibility(View.GONE);
//                    LEADSOURCE = "University";
//                    netenquiery.setChecked(false);
//                    direct_radio.setChecked(false);
//                    referal_radio.setChecked(false);
//                    corporate.setChecked(false);
//                    corporate_company.setVisibility(View.VISIBLE);
//                    autoCompleteText_mwb_b2c.setText("");
//                    getUniversitiesData();
//                }
//            }
//        });
        radioGroupForReference.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.ewb_id) {
                    reference_layout_spinner.setVisibility(View.VISIBLE);
                    ewbspinner.setVisibility(View.VISIBLE);
                    mwbspinner.setVisibility(View.GONE);
                    lead_b2b_ir_reference.setVisibility(View.GONE);
                }
                if (checkedId == R.id.mwb_id) {
                    reference_layout_spinner.setVisibility(View.VISIBLE);
                    ewbspinner.setVisibility(View.GONE);
                    mwbspinner.setVisibility(View.VISIBLE);
                    lead_b2b_ir_reference.setVisibility(View.GONE);
                }
                if (checkedId == R.id.others_id) {
                    reference_layout_spinner.setVisibility(View.VISIBLE);
                    ewbspinner.setVisibility(View.GONE);
                    mwbspinner.setVisibility(View.GONE);
                    lead_b2b_ir_reference.setVisibility(View.VISIBLE);
                    other_check_radio_text = "others";
                }
            }
        });
        /*MWB SPINNER*/
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, mwbArrayList);
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

        ArrayAdapter<String> ewbAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, ewbArraylist);
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
        ArrayAdapter<String> directAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, directSourceArraylist);
        // Drop down layout style - list view with radio button
        directAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        direct_spinner.setAdapter(directAdapter);

        direct_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LeadDetails = direct_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        cityAdpater = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, cityArrayList);
        // Drop down layout style - list view with radio button
        cityAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        city_spinner.setAdapter(cityAdpater);

        city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LEADCity = city_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> levelsAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, milesSpos_spinnerArrayList);
        // Drop down layout style - list view with radio button
        levelsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        milesSpos_spinner.setAdapter(levelsAdapter);
    }

    private void saveMwbLeadBEBIR(final Dialog dialog) {
        if (lead_b2b_ir_name.getText().toString().trim().isEmpty()) {
            lead_b2b_ir_name.setError("Please enter name");
        }
        if ((lead_b2b_ir_email.getText().toString().isEmpty()) || (lead_b2b_ir_mobile.getText().toString().isEmpty())) {

            Toast.makeText(this, "Please enter either email or mobile ", Toast.LENGTH_SHORT).show();
        } else {
            if (other_check_radio_text.equals("others")) {
                LeadDetails = lead_b2b_ir_reference.getText().toString();
            }
            b2b_ir_progress.setVisibility(View.VISIBLE);
            apiClient.AddMwbLead(lead_b2b_ir_name.getText().toString().trim(), "M1", "None", "B2BIR", lead_b2b_ir_institute.getText().toString().trim(), lead_b2b_ir_designation.getText().toString().trim(), " ", " ", LEADCity, "", LEADSOURCE, LeadDetails, lead_b2b_ir_email.getText().toString(), lead_b2b_ir_mobile.getText().toString(), lead_b2b_ir_engagement.getText().toString().trim(), nextCallTimeStamp, 0, batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), VERSION_NUMBER, "", "", ConnectionStatus, "",
                    U_levels_spinner.getSelectedItem().toString(), "Bearer " + accessToken, "application/json").enqueue(new Callback<SuccessModel>() {
                @Override
                public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                    try {
                        if (response.body() == null) {
                            int statusCode = response.raw().code();
                            if (statusCode > 399 && statusCode < 500) {
                                ClearSession();
                            }
                            dialog.dismiss();
                        } else {
                            if (response.body().getStatus().equals("success")) {
                                showSnakebar(response.body().getMessage());
                                dialog.dismiss();
                                b2b_ir_progress.setVisibility(View.GONE);
                            } else {
                                showSnakebar(response.body().getMessage());
                                dialog.dismiss();
                                b2b_ir_progress.setVisibility(View.GONE);
                            }


                        }
                    } catch (Exception e) {
                        showSnakebar(e.getMessage());
                        dialog.dismiss();
                        b2b_ir_progress.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<SuccessModel> call, Throwable t) {
                    showSnakebar(t.getMessage());
                    dialog.dismiss();
                    b2b_cr_progress.setVisibility(View.GONE);
                }
            });
        }
    }

    private void ClearSession() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(AccessToken);
        editor.apply();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }


    private void openAddLeadForB2BCr() {
        LEADSOURCE = " ";
        LeadDetails = " ";
        LeadDetails = " ";
        LEADCity = " ";
        other_check_radio_text = " ";
        LeadsCalendar = Calendar.getInstance();
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.addleadfor_btb_cr);
        dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
        IncomingCals_radio = dialog.findViewById(R.id.IncomingCals_radio);
        referal_radio = dialog.findViewById(R.id.referal_radio);
        IVR_radio = dialog.findViewById(R.id.IVR_radio);

        corporate_company = dialog.findViewById(R.id.corporate_company_b2b_cr);
//        corporate = dialog.findViewById(R.id.corporate);
//        university = dialog.findViewById(R.id.university);
        referal = dialog.findViewById(R.id.reference_layout);
        direct = dialog.findViewById(R.id.direct_layout);
        radioGroupForReference = dialog.findViewById(R.id.reference_radio_group);
        reference_layout_spinner = dialog.findViewById(R.id.reference_layout_spinner);
        ewbspinner = dialog.findViewById(R.id.MwbSpinner);
        mwbspinner = dialog.findViewById(R.id.ewbSpinner);
        direct_reference_cr = dialog.findViewById(R.id.direct_reference_cr);
        direct_spinner = dialog.findViewById(R.id.direct_spinner);
        city_spinner = dialog.findViewById(R.id.city_spinner);
        milesSpos_spinner = dialog.findViewById(R.id.milesSpos_spinner);
        b2bcr_leadname = dialog.findViewById(R.id.b2bcr_leadname);
        b2bcr_leadmobile = dialog.findViewById(R.id.b2bcr_leadmobile);
        b2bcr_lead_email = dialog.findViewById(R.id.b2bcr_lead_email);
//        b2b_cr_lead_company = dialog.findViewById(R.id.b2b_cr_lead_company);
//        b2b_cr_lead_designation = dialog.findViewById(R.id.b2b_cr_lead_designation);
//        b2b_cr_lead_experiance = dialog.findViewById(R.id.b2b_cr_lead_experiance);
        b2b_cr_lead_engagement = dialog.findViewById(R.id.b2b_cr_lead_engagement);
        b2c_lead_nextCall_picker_ = dialog.findViewById(R.id.date_picker_);
        b2b_cr_progress = dialog.findViewById(R.id.b2b_cr_progress);
        autoCompleteText_mwb_b2c = dialog.findViewById(R.id.autoCompleteText_mwb_b2c);
        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ((Button) dialog.findViewById(R.id.bt_save_add)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddLeadMwbB2bcr(dialog);

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
                    IVR_radio.setChecked(false);
                    IncomingCals_radio.setChecked(false);

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
//        corporate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    referal.setVisibility(View.GONE);
//                    direct.setVisibility(View.GONE);
//                    reference_layout_spinner.setVisibility(View.GONE);
//                    LEADSOURCE = "Corporate";
//                    netenquiery.setChecked(false);
//                    direct_radio.setChecked(false);
//                    referal_radio.setChecked(false);
//                    university.setChecked(false);
//                    corporate_company.setVisibility(View.VISIBLE);
//                    autoCompleteText_mwb_b2c.setText("");
//                    getAutoCompleteData();
//
//                }
//            }
//        });
//        university.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    referal.setVisibility(View.GONE);
//                    direct.setVisibility(View.GONE);
//                    reference_layout_spinner.setVisibility(View.GONE);
//                    LEADSOURCE = "University";
//                    netenquiery.setChecked(false);
//                    direct_radio.setChecked(false);
//                    referal_radio.setChecked(false);
//                    corporate.setChecked(false);
//                    corporate_company.setVisibility(View.VISIBLE);
//                    autoCompleteText_mwb_b2c.setText("");
//                    getUniversitiesData();
//                }
//            }
//        });
        radioGroupForReference.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.ewb_id) {
                    reference_layout_spinner.setVisibility(View.VISIBLE);
                    ewbspinner.setVisibility(View.VISIBLE);
                    mwbspinner.setVisibility(View.GONE);
                    direct_reference_cr.setVisibility(View.GONE);
                }
                if (checkedId == R.id.mwb_id) {
                    reference_layout_spinner.setVisibility(View.VISIBLE);
                    ewbspinner.setVisibility(View.GONE);
                    mwbspinner.setVisibility(View.VISIBLE);
                    direct_reference_cr.setVisibility(View.GONE);
                }
                if (checkedId == R.id.others_id) {
                    reference_layout_spinner.setVisibility(View.VISIBLE);
                    ewbspinner.setVisibility(View.GONE);
                    mwbspinner.setVisibility(View.GONE);
                    direct_reference_cr.setVisibility(View.VISIBLE);
                    other_check_radio_text = "others";
                    /*LeadDetails*/
                }
            }
        });
        /*MWB SPINNER*/
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, mwbArrayList);
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

        ArrayAdapter<String> ewbAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, ewbArraylist);
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
        ArrayAdapter<String> directAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, directSourceArraylist);
        // Drop down layout style - list view with radio button
        directAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        direct_spinner.setAdapter(directAdapter);

        direct_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LeadDetails = direct_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> cityAdpater = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, cityArrayList);
        // Drop down layout style - list view with radio button
        cityAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        city_spinner.setAdapter(cityAdpater);
        city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LEADCity = city_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> levelsAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, milesSpos_spinnerArrayList);
        // Drop down layout style - list view with radio button
        levelsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        milesSpos_spinner.setAdapter(levelsAdapter);


        b2c_lead_nextCall_picker_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, leads_date, LeadsCalendar.get(Calendar.YEAR), LeadsCalendar.get(Calendar.MONTH), LeadsCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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

    private void openAddLeadForm() {
        other_check_radio_text = "";
        LeadsCalendar = Calendar.getInstance();
        GetMLevelList(accessToken);


        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_add_engagementform);
        interested_Layout = dialog.findViewById(R.id.interested_Layout);

        appconpact_spinner_interested_working = dialog.findViewById(R.id.appconpact_spinner_interested_working);
        ArrayAdapter<String> appconpact_spinner_interested_working_Adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, spinner_interested_workingArrayList);
        appconpact_spinner_interested_working_Adapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        appconpact_spinner_interested_working.setAdapter(appconpact_spinner_interested_working_Adapter);


        ArrayAdapter<String> appconpact_spinner_looking_job_Adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, spinnerLookingJob_workingArrayList);
        appconpact_spinner_looking_job_Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        appconpact_spinner_graduation_Year = dialog.findViewById(R.id.appconpact_spinner_graduation_Year);
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear + 3; i >= 1995; i--) {
            years.add("");
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, years);
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
        city_spinner = dialog.findViewById(R.id.city_spinner);
        M_levels_spinner = dialog.findViewById(R.id.M_levels_spinner);
        U_levels_spinner = dialog.findViewById(R.id.U_levels_spinner);
        appconpact_spinner_connectionstatus = dialog.findViewById(R.id.appconpact_spinner_connectionstatus);
//        appconpact_spinner_connectionstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                try {
////                    nextTimeLayout.setVisibility(View.VISIBLE);
//                    String text = appconpact_spinner_connectionstatus.getSelectedItem().toString();
//                    Log.d("SELECTED_text------>",text);
//                    if (appconpact_spinner_connectionstatus.getSelectedItem().toString().equals("Connected / Discussed")) {
//                        engagement_main_form.setVisibility(View.VISIBLE);
//                        engagement_description.setText(" ");
//                        M_levels_spinner.setEnabled(true);
//                        U_levels_spinner.setEnabled(true);
//
//                    } else {
//                        M_levels_spinner.setEnabled(false);
//                        U_levels_spinner.setEnabled(false);
//                        engagement_main_form.setVisibility(View.GONE);
//                        Date c = Calendar.getInstance().getTime();
//                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//                        String formattedDate = df.format(c);
//
//                        if (previousEngagement.contains(" | ")) {
//                            int index = previousEngagement.indexOf(" | ");
//                            String Result = previousEngagement.substring(index + 2);
//                            String resul = Result;
//                            engagement_description.setText(formattedDate + " - " + text + " | " + Result);
//
//                        } else {
//                            engagement_description.setText(formattedDate + " - " + text + " | " + previousEngagement);
//                        }
//                        LevelsSelected = levels;
//                        CoursesData = courses;
//
//                    }
//                } catch (Exception e) {
//
//
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        appconpact_spinner_connectionstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
//                    nextTimeLayout.setVisibility(View.VISIBLE);
                    SELECTED_STATUS = appconpact_spinner_connectionstatus.getSelectedItem().toString();
                    Log.d("SELECTED_STATUS------>", SELECTED_STATUS);
//                    if (User_levels.equals("M6")) {
//                        myCalendar.add(Calendar.DAY_OF_MONTH, 1);
//                        MAX_DATE_PRE_SELECT = 30;
//                    }
//                    if (User_levels.equals("M5") || (User_levels.equals("M3"))) {
//                        myCalendar.add(Calendar.DAY_OF_MONTH, 1);
//                        MAX_DATE_PRE_SELECT = 60;
//                    }
//                    if (User_levels.equals("M3++") || (User_levels.equals("M3+"))) {
//                        myCalendar.add(Calendar.DAY_OF_MONTH, 1);
//                        MAX_DATE_PRE_SELECT = 40;
//                    }

//                    if ((SELECTED_STATUS.equals("Connected / Discussed"))) {
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
                    CoursesData = courses;
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
                    CoursesData = courses;

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
        levelsCustomAdapter = new LevelsCustomAdapter(getApplicationContext(), R.layout.listitems_layout, R.id.levels_items, spinner_M_LevelList);
        M_levels_spinner.setAdapter(levelsCustomAdapter);


        //my self
        U_levels_spinner = dialog.findViewById(R.id.U_levels_spinner);

        GetULevelList(accessToken);

        ArrayAdapter<String> U_levels_spinner_Adapter = new ArrayAdapter<String>(getApplicationContext(),
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
        b2c_lead_mobile = dialog.findViewById(R.id.b2c_lead_mobile);
        b2c_lead_email = dialog.findViewById(R.id.b2c_lead_email);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
                            Toast.makeText(getApplicationContext(), "Please enter graduation Year", Toast.LENGTH_SHORT).show();

                        } else if (edt_current_location.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Please enter Current Location", Toast.LENGTH_SHORT).show();
                        } else if (b2c_lead_engagement.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Please add engagement details and Please select source details ", Toast.LENGTH_SHORT).show();

                        } else {
                            SaveMWbB2CLead(dialog);

                        }
                    } else {

                        if (appconpact_spinner_graduation_Year.getSelectedItem().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Please enter graduation Year", Toast.LENGTH_SHORT).show();
                        } else if (edt_current_location.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Please enter Current Location", Toast.LENGTH_SHORT).show();
                        } else if (company_Name.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Please enter your Company name", Toast.LENGTH_SHORT).show();
                        } else if (work_Experience.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Please enter your Work Experience", Toast.LENGTH_SHORT).show();
                        } else if (indian_Professional.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Please select your indian Professional", Toast.LENGTH_SHORT).show();

                        } else if (global_Professional_Qualification.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Please select your global professional qualification", Toast.LENGTH_SHORT).show();

                        } else if (ug_Graduate_Qualification.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Please select your UG qualification", Toast.LENGTH_SHORT).show();
                        } else if (b2c_lead_engagement.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Please add engagement details and Please select source details ", Toast.LENGTH_SHORT).show();

                        } else {
                            SaveMWbB2CLead(dialog);
                        }

                    }


                } else {
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
//        corporate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    referal.setVisibility(View.GONE);
//                    direct.setVisibility(View.GONE);
//                    reference_layout_spinner.setVisibility(View.GONE);
//                    LEADSOURCE = "Corporate";
//                    netenquiery.setChecked(false);
//                    direct_radio.setChecked(false);
//                    referal_radio.setChecked(false);
//                    university.setChecked(false);
//                    corporate_company.setVisibility(View.VISIBLE);
//                    autoCompleteText_mwb_b2c.setText("");
//                    getAutoCompleteData();
//                    //  getUniversitiesData();
//
//
//                }
//            }
//        });
//        university.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    referal.setVisibility(View.GONE);
//                    direct.setVisibility(View.GONE);
//                    reference_layout_spinner.setVisibility(View.GONE);
//                    LEADSOURCE = "University";
//                    netenquiery.setChecked(false);
//                    direct_radio.setChecked(false);
//                    referal_radio.setChecked(false);
//                    corporate.setChecked(false);
//                    corporate_company.setVisibility(View.VISIBLE);
//                    autoCompleteText_mwb_b2c.setText("");
//                    getUniversitiesData();
//                    //getAutoCompleteData("getUniversities");
//                }
//            }
//        });
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
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, mwbArrayList);
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

        ArrayAdapter<String> ewbAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, ewbArraylist);
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
        ArrayAdapter<String> directAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, directSourceArraylist);
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


        cityAdpater = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, cityArrayList);
        // Drop down layout style - list view with radio button
        cityAdpater.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        city_spinner.setAdapter(cityAdpater);

        city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LEADCity = city_spinner.getSelectedItem().toString();
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

//        levelsCustomAdapter = new LevelsCustomAdapter(this, R.layout.listitems_layout, R.id.levels_items, spinnerLevelList);
//        M_levels_spinner.setAdapter(levelsCustomAdapter);
//        M_levels_spinner.setEnabled(false);

        ArrayAdapter<String> connection_statusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ConnectionTypeArrayList);
        connection_statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appconpact_spinner_connectionstatus.setAdapter(connection_statusAdapter);
//        appconpact_spinner_connectionstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                SELECTED_STATUS = appconpact_spinner_connectionstatus.getSelectedItem().toString();
//                Log.d("SELECTED_STATUS-->", SELECTED_STATUS);
//                if (SELECTED_STATUS.equals("Connected / Discussed")) {
////                    interested_Layout.setVisibility(View.VISIBLE);
//
//                    interested_Layout.setEnabled(true);
//                    appconpact_spinner_interested_working.setEnabled(true);
//                    looking_job.setEnabled(true);
//                    appconpact_spinner_looking_job.setEnabled(true);
//                    appconpact_spinner_graduation_Year.setEnabled(true);
//                    current_location.setEnabled(true);
//                    work_Ex_profiling.setEnabled(true);
//                    company_Name.setEnabled(true);
//                    work_Experience.setEnabled(true);
//                    indian_Professional.setEnabled(true);
//                    global_Professional_Qualification.setEnabled(true);
//                    ug_Graduate_Qualification.setEnabled(true);
//                    pg_Graduate_Qualification.setEnabled(true);
//                } else if (SELECTED_STATUS.equals("Connected / Not interested")) {
////                    interested_Layout.setVisibility(View.VISIBLE);
//                    interested_Layout.setEnabled(true);
//                    appconpact_spinner_interested_working.setEnabled(true);
//                    looking_job.setEnabled(true);
//                    appconpact_spinner_looking_job.setEnabled(true);
//                    appconpact_spinner_graduation_Year.setEnabled(true);
//                    current_location.setEnabled(true);
//                    work_Ex_profiling.setEnabled(true);
//                    company_Name.setEnabled(true);
//                    work_Experience.setEnabled(true);
//                    indian_Professional.setEnabled(true);
//                    global_Professional_Qualification.setEnabled(true);
//                    ug_Graduate_Qualification.setEnabled(true);
//                    pg_Graduate_Qualification.setEnabled(true);
//                } else {
////                    interested_Layout.setVisibility(View.GONE);
//                    interested_Layout.setEnabled(false);
//                    appconpact_spinner_interested_working.setEnabled(false);
//                    looking_job.setEnabled(false);
//                    appconpact_spinner_looking_job.setEnabled(false);
//                    appconpact_spinner_graduation_Year.setEnabled(false);
//                    current_location.setEnabled(false);
//                    work_Ex_profiling.setEnabled(false);
//                    company_Name.setEnabled(false);
//                    work_Experience.setEnabled(false);
//                    indian_Professional.setEnabled(false);
//                    global_Professional_Qualification.setEnabled(false);
//                    ug_Graduate_Qualification.setEnabled(false);
//                    pg_Graduate_Qualification.setEnabled(false);
//
//
//                }
//                Toast.makeText(MainActivity.this, "" + SELECTED_STATUS, Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        U_levels_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                LevelsModel levelsModel = spinner_U_LevelList.get(position);
//                str_U_levels = levelsModel.getLevelCode();
//                myCalendar = Calendar.getInstance();
//                myCalendar.set(Calendar.YEAR, myCalendar.get(Calendar.YEAR));
//                myCalendar.set(Calendar.MONTH, myCalendar.get(Calendar.MONTH));
//
//
//                if (U_levels_spinner.equals("U0") || (U_levels_spinner.equals("U1")) || (U_levels_spinner.equals("U1+")) || (U_levels_spinner.equals("U2"))
//                        || (U_levels_spinner.equals("U3-"))
//                ) {
//                    spinner_M_LevelList.clear();
//
//                    spinner_M_LevelList.add(new LevelsModel("M6", "M6 - Visited & Ready to Enroll"));
//                    spinner_M_LevelList.add(new LevelsModel("M5", "M5 - Visited & Positive"));
//                    spinner_M_LevelList.add(new LevelsModel("M4", "M4 - Visited but Postponed"));
//                    spinner_M_LevelList.add(new LevelsModel("M4-", "M4- - Visited but not interested"));
//                    spinner_M_LevelList.add(new LevelsModel("M3++", "M3++ - Ready to  enrolled -  not visited"));
//                    spinner_M_LevelList.add(new LevelsModel("M3+", "M3+ - Called & Coming"));
//                    spinner_M_LevelList.add(new LevelsModel("M3", "M3 - Called & positive"));
//                    spinner_M_LevelList.add(new LevelsModel("M2", "M2 - Did not visit & Postponed"));
//                    spinner_M_LevelList.add(new LevelsModel("M1", "M1 - Did not visit & not interested"));
//
//
//                } else if (U_levels_spinner.equals("U3") || (U_levels_spinner.equals("U3+")) || (U_levels_spinner.equals("U3++"))
//                ) {
//                    spinner_M_LevelList.clear();
//
//                    spinner_M_LevelList.add(new LevelsModel("M6", "M6 - Visited & Ready to Enroll"));
//                    spinner_M_LevelList.add(new LevelsModel("M5", "M5 - Visited & Positive"));
//                    spinner_M_LevelList.add(new LevelsModel("M3++", "M3++ - Ready to  enrolled -  not visited"));
//                    spinner_M_LevelList.add(new LevelsModel("M3+", "M3+ - Called & Coming"));
//                    spinner_M_LevelList.add(new LevelsModel("M3", "M3 - Called & positive"));
//                } else if (U_levels_spinner.equals("U5") || (U_levels_spinner.equals("U6"))
//                ) {
//                    spinner_M_LevelList.clear();
//                    spinner_M_LevelList.add(new LevelsModel("M6", "M6 - Visited & Ready to Enroll"));
//                    spinner_M_LevelList.add(new LevelsModel("M5", "M5 - Visited & Positive"));
//                } else if (U_levels_spinner.equals("U4")) {
//                    spinner_M_LevelList.clear();
//
//                    spinner_M_LevelList.add(new LevelsModel("M4", "M4 - Visited but Postponed"));
//                    spinner_M_LevelList.add(new LevelsModel("M4-", "M4- - Visited but not interested"));
//                    spinner_M_LevelList.add(new LevelsModel("M6", "M6 - Visited & Ready to Enroll"));
//                    spinner_M_LevelList.add(new LevelsModel("M5", "M5 - Visited & Positive"));
//
//                } else if (U_levels_spinner.equals("U7-") || (U_levels_spinner.equals("U7R") || (U_levels_spinner.equals("U7")))) {
//                    spinner_M_LevelList.clear();
//                    spinner_M_LevelList.add(new LevelsModel(" ", " "));
//                    appconpact_spinner_M_levels.setSelection(0);
//
//                } else {
//                    spinner_M_LevelList.clear();
//                    spinner_M_LevelList.add(new LevelsModel(" ", " "));
//                    appconpact_spinner_M_levels.setSelection(0);
//
//                }
//
//
//
//                updateLabel();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


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

//        U_levels_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                LevelsModel levelsModel = spinnerLevelList.get(position);
//                LeadLevels = levelsModel.getLevelCode();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        b2c_lead_nextCall_picker_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, leads_date, LeadsCalendar
                        .get(Calendar.YEAR), LeadsCalendar.get(Calendar.MONTH),
                        LeadsCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(LeadsCalendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        apiClient.getSpocCity("Bearer " + accessToken).

                enqueue(new Callback<SuccessModel>() {
                    @Override
                    public void onResponse
                            (Call<SuccessModel> call, Response<SuccessModel> response) {
                        try {
                            if (response.body() == null) {
                            } else {
                                String City = response.body().getCity();//the value you want the position for
                                int spinnerPosition = cityAdpater.getPosition(City);
                                city_spinner.setSelection(spinnerPosition);
                                LEADCity = City;
                                // Toast.makeText(MainActivity.this, ""+LEADCity, Toast.LENGTH_SHORT).show();

                            }
                        } catch (Exception e) {

                        }

                    }

                    @Override
                    public void onFailure(Call<SuccessModel> call, Throwable t) {

                    }
                });


    }

    private void getUniversitiesData() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getApplicationContext(), android.R.layout.select_dialog_item, universities);
        autoCompleteText_mwb_b2c.setThreshold(1);
        autoCompleteText_mwb_b2c.setAdapter(adapter);
        autoCompleteText_mwb_b2c.showDropDown();
        autoCompleteText_mwb_b2c.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LeadDetails = (String) parent.getItemAtPosition(position);
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
            }
        });
    }

    private void getAutoCompleteData() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getApplicationContext(), android.R.layout.select_dialog_item, corporates);

        autoCompleteText_mwb_b2c.setThreshold(1);
        autoCompleteText_mwb_b2c.setAdapter(adapter);
        autoCompleteText_mwb_b2c.showDropDown();
        autoCompleteText_mwb_b2c.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LeadDetails = (String) parent.getItemAtPosition(position);
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
            }
        });

    }

    private void openAlertForDatepicker() {
        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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


    public void openAlert(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

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

    DatePickerDialog.OnDateSetListener leads_date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            LeadsCalendar.set(Calendar.YEAR, year);
            LeadsCalendar.set(Calendar.MONTH, monthOfYear);
            LeadsCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd - MM - yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            b2c_lead_nextCall_picker_.setText(sdf.format(LeadsCalendar.getTime()));
            try {
                Date date = sdf.parse(sdf.format(LeadsCalendar.getTime()));
                long timestamp = date.getTime() / 1000L;
                nextCallTimeStamp = timestamp;

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    };

    private void SaveMWbB2CLead(final Dialog dialog) {
        if (SELECTED_STATUS.equals(" ")) {
            Toast.makeText(this, "Please select Connection status ", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Please fill either Email or Mobile number.....!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(this, "Please enter 10 digits mobile number", Toast.LENGTH_SHORT).show();
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
                                    ClearSession();
                                }
                                dialog.dismiss();
                            } else {
                                if (response.body().getStatus().equals("success")) {
                                    dialog.dismiss();
                                    showSnakebar(response.body().getMessage());
                                    add_b2b_progresss.setVisibility(View.GONE);
                                }
                                if (response.body().getStatus().equals("error")) {
                                    showSnakebar(response.body().getMessage());
                                    String spam_number_check = response.body().getMessage();
                                    add_b2b_progresss.setVisibility(View.GONE);
                                    if (spam_number_check.contains("spam")) {
                                        showSpamDialog(dialog, LAST_TENDIGIT_MOBILE_NUMBER, b2c_lead_mobile.getText().toString().trim(), "Bearer " + accessToken);
                                    } else {
                                        showSnakebar(response.body().getMessage());
                                        add_b2b_progresss.setVisibility(View.GONE);
                                        //  dialog.dismiss();

                                    }
                                } else {
                                    //dialog.dismiss();
                                    showSnakebar(response.body().getMessage());
                                    add_b2b_progresss.setVisibility(View.GONE);
                                }

                            }
                        } catch (Exception e) {
                            showSnakebar(e.getMessage());
                            add_b2b_progresss.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessModel> call, Throwable t) {
                        showSnakebar(t.getMessage());
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
                    Toast.makeText(MainActivity.this, "Removed the number from spam. Now add a lead with this number", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
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


    public void showSnakebar(String s) {
        Snackbar snackbar = Snackbar
                .make(user_info_snakebar, s, 10000);
        snackbar.show();
    }

    private void AddLeadMwbB2bcr(final Dialog dialog) {

        if (b2bcr_leadname.getText().toString().trim().isEmpty()) {
            b2bcr_leadname.setError("Please enter name");
        }
        if ((b2bcr_lead_email.getText().toString().isEmpty()) || (b2bcr_leadmobile.getText().toString().isEmpty())) {

            Toast.makeText(this, "Please enter either email or mobile ", Toast.LENGTH_SHORT).show();
        } else {
            if (other_check_radio_text.equals("others")) {
                LeadDetails = direct_reference_cr.getText().toString();
            }
            b2b_cr_progress.setVisibility(View.VISIBLE);
            apiClient.AddMwbLead(b2bcr_leadname.getText().toString().trim(), "M1",
                            "None", "B2BCR", b2b_cr_lead_company.getText().toString().trim(),
                            b2b_cr_lead_designation.getText().toString().trim(),
                            b2b_cr_lead_experiance.getText().toString().trim(), " ", LEADCity,
                            "", LEADSOURCE, LeadDetails, b2bcr_lead_email.getText().toString(),
                            b2bcr_leadmobile.getText().toString(), b2b_cr_lead_engagement.getText().toString().trim(),
                            nextCallTimeStamp, 0, batteryModel.getBattey_percentage(),
                            batteryModel.getCharging_status(), VERSION_NUMBER, "", "",
                            ConnectionStatus, "", U_levels_spinner.getSelectedItem().toString(), "Bearer " + accessToken, "application/json")
                    .enqueue(new Callback<SuccessModel>() {
                        @Override
                        public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                            try {
                                if (response.body() == null) {
                                    int statusCode = response.raw().code();
                                    if (statusCode > 399 && statusCode < 500) {
                                        ClearSession();
                                    }
                                    dialog.dismiss();
                                } else {
                                    if (response.body().getStatus().equals("success")) {
                                        showSnakebar(response.body().getMessage());
                                        dialog.dismiss();
                                        b2b_cr_progress.setVisibility(View.GONE);
                                    } else {
                                        showSnakebar(response.body().getMessage());
                                        dialog.dismiss();
                                        b2b_cr_progress.setVisibility(View.GONE);
                                    }

                                }
                            } catch (Exception e) {
                                showSnakebar(e.getMessage());
                                dialog.dismiss();
                                b2b_cr_progress.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<SuccessModel> call, Throwable t) {
                            showSnakebar(t.getMessage());
                            dialog.dismiss();
                            b2b_cr_progress.setVisibility(View.GONE);
                        }
                    });
        }
    }


//    private class CallRecordingAsync extends AsyncTask<Void, String, String> {
//        @RequiresApi(api = Build.VERSION_CODES.O)
//        protected String doInBackground(Void... strings) {
//            Path filePath = null;
//            try {
//                if (currentapiVersion >= versioncode && currentapiVersion != versioncode) {
//                    directoryPath = Environment.getExternalStorageDirectory() + "/Recordings/Call";
//                } else {
//                    directoryPath = Environment.getExternalStorageDirectory() + "/Call";
//                }
//
//
//                File directory = new File(directoryPath);
//                File[] files = directory.listFiles();
//                Log.d("Files", "Size: " + files.length);
//                File file = new File(files[0].getPath());
//                Date lastModDate = new Date(file.lastModified());
//                filePath = file.toPath();
//                Log.d("filePath", filePath.toString());
//
//
//
//
//                Log.d("extension", extension);
//                BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);
//                long createdAt = attr.creationTime().toMillis();
//                Log.d("aync_test", String.valueOf(createdAt));
//                Log.d("aync_test", "Pre signed Url Start");
//                generatePresignedUrl(String.valueOf(filePath.getFileName()), String.valueOf(createdAt), userToken.getAccessToken());
//                Log.d("aync_test", "Pre signed Url Start");
//                if (filePath != null) {
//                    return String.valueOf(filePath.getFileName());
//                }
//
//            } catch (Exception e) {
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            try {
//                DeleteFileFromLocal(s);
//                Log.d("aync_test", s + "PostExecute");
//            } catch (Exception e) {
//
//            }
//
//        }
//    }

    private void generatePresignedUrl(final String file, String s, final String Access_token) {
        long timeStamp = System.currentTimeMillis();
        Log.d("aync_test", String.valueOf(timeStamp));
        Log.d("aync_test", String.valueOf(s));


        Log.d("extension", file);
        String extension = file.substring(file.indexOf(".") - 0);
        Log.d("extension", extension);

//        apiClient.getGeneratedPresignedUrl(s, "Bearer " + Access_token, "application/json")
        commanApiClient.getGeneratedPresignedUrl(s, extension, "Bearer " + Access_token, "application/json")
                .enqueue(new Callback<SuccessModel>() {
                    @Override
                    public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                        if (response.body().getUrl() != null) {
                            String URL = response.body().getUrl();
                            Log.d("aync_test", URL);
                            if (isConnected()) {
                                SendFileDataToServer(file, URL);
                            } else {
                                Toast.makeText(MainActivity.this,
                                        "No Internet Connection Found....!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessModel> call, Throwable t) {
                        if (isConnected()) {
//                            Toast.makeText(MainActivity.this, "Some thing went wrong....!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "No Internet Connection Found....!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
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
//                Toast.makeText(MainActivity.this, "Some thing went wrong....!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void GetULevelList(String Access_token) {
        ninjaApiClient.getULevels("Bearer " + Access_token, "application/json").enqueue(new Callback<List<ULevelModel>>() {
            @Override
            public void onResponse(Call<List<ULevelModel>> call, Response<List<ULevelModel>> response) {
                uLevelListModel = response.body();
//                uLevelList=uLevelListModel.get(0).getuLevel();

//                List<MobileNumberModel> mobileNumberModels = response.body();
//                for (int i = 0; i < mobileNumberModels.size(); i++) {
//                    MobileNumberModel mobileNumberModel = new MobileNumberModel();
//                    mobileNumberModel.setPhone_number(mobileNumberModels.get(i).getPhone_number());
//                    mobileNumberModel.setMasked_number(mobileNumberModels.get(i).getMasked_number());
//                    mobileNumberModel.setId(mobileNumberModels.get(i).getId());
//                    mobileNumberModelArrayList.add(mobileNumberModel);
//                }
//                List<ULevelModel> uLevelListModel=response.body();
//
//                for (int i = 0; i < uLevelListModel.size(); i++) {
////                    uLevelListModel.
//                }
            }

            @Override
            public void onFailure(Call<List<ULevelModel>> call, Throwable t) {

            }
        });
    }

//    private void GetULevelList(String Access_token) {
//        ninjaApiClient.getULevels("Bearer " + Access_token, "application/json").enqueue(new Callback<List<String>>() {
//            @Override
//            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
//                if (response.isSuccessful()) {
////                    List<String> stringList = response.body();
//                    uLevelList=response.body();
//                    Log.d("GetULevelList_onList", String.valueOf(uLevelList));
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<String>> call, Throwable t) {
//                Log.d("GetULevelList_onFailure", String.valueOf(t.getMessage()));
//                Toast.makeText(MainActivity.this, "Some thing went wrong....!", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }


    private void DeleteFileFromLocal(String file_name) {
        Log.d("aync_test_path", file_name);
        Log.d("Build.BRAND", Build.BRAND.trim());
        Log.d("currentapiVersion", String.valueOf(currentapiVersion));
        Log.d("versioncode", String.valueOf(versioncode));
        if(Build.BRAND.trim().contains("samsung")){
            if (currentapiVersion >= versioncode && currentapiVersion != versioncode) {
                directoryPath = Environment.getExternalStorageDirectory() + "/Recordings/Call";
            } else {
                directoryPath = Environment.getExternalStorageDirectory() + "/Call";
            }
        }else {
            directoryPath = Environment.getExternalStorageDirectory() + "/MIUI/sound_recorder/call_rec";


        }
        Log.d("directoryPath", directoryPath);
        try {
            File directory = new File(directoryPath);
            File[] files = directory.listFiles();

            for (int i = 0; i < files.length; i++) {
                if (files[i].toString().equals(file_name)) {
                    new File(directory, files[i].getName()).delete();
                }
            }

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                UploadFilesToServer();
//            }


        } catch (Exception e) {
            Toast.makeText(this, "Some thing went wrong.Please try after some time", Toast.LENGTH_SHORT).show();
        }

    }

    private void SendFileDataToServer(final String fileName, final String Url) {

        File file = new File(fileName);
        Log.d("PathlookingUp===>", fileName);
        RequestBody requestBody = null;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] buf;
            buf = new byte[in.available()];
            while (in.read(buf) != -1) ;
            requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), buf);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("UploadBinaryFileBody", String.valueOf(requestBody));
        Log.d("UploadBinaryFileURL", Url);

        apiClient.UploadBinaryFile(Url, requestBody).enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Toast.makeText(MainActivity.this, "Uploaded Successfully....!!", Toast.LENGTH_SHORT).show();
                    try {
                        Log.d("SendFileDataToServer", String.valueOf(response.body()));
                        DeleteFileFromLocal(fileName);

                    } catch (Exception e) {
                        Log.d("uploadfailed", e.getMessage());
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("aync_test", "uploadfailed" + t.getMessage());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void UploadFilesToServer() {
        try {
            if(Build.BRAND.trim().contains("samsung")){
                if (currentapiVersion >= versioncode && currentapiVersion != versioncode) {
                    directoryPath = Environment.getExternalStorageDirectory() + "/Recordings/Call";
                } else {
                    directoryPath = Environment.getExternalStorageDirectory() + "/Call";
                }
            }else {
                directoryPath = Environment.getExternalStorageDirectory() + "/MIUI/sound_recorder/call_rec";
            }
            File directory = new File(directoryPath);
            File[] files = directory.listFiles();
            if (files.length > 0) {
                Log.d("aync_test", String.valueOf(files.length));
                AudioManager manager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                if (manager.getMode() == AudioManager.MODE_IN_CALL) {
                    Log.d("aync_test", String.valueOf("OnCall"));
                    if (files.length > 2) {
                        Log.d("aync_test", String.valueOf("OnCall" + files.length));
                        File file = new File(files[1].getPath());
                        Log.d("testing_file1", file.toString());
                        Date lastModDate = new Date(file.lastModified());
                        Path filePath = file.toPath();
                        Log.d("testing_file2", filePath.toString());
                        BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);
                        long createdAt = attr.creationTime().toMillis();
                        Log.d("aync_test", String.valueOf(createdAt));
                        Log.d("aync_test", "Pre signed Url Start");
                        if (filePath != null) {
                            generatePresignedUrl(String.valueOf(file), String.valueOf(createdAt), userToken.getAccessToken());
                        }
                    }
                } else {
                    if (files.length > 0) {
                        Log.d("aync_test", String.valueOf("offCall" + files.length));
                        File file = new File(files[0].getPath());
                        Date lastModDate = new Date(file.lastModified());
                        Path filePath = file.toPath();
                        BasicFileAttributes attr = Files.readAttributes(filePath, BasicFileAttributes.class);
                        long createdAt = attr.creationTime().toMillis();
                        Log.d("aync_test", String.valueOf(createdAt));
                        Log.d("aync_test", "Pre signed Url Start");
                        if (filePath != null) {
                            generatePresignedUrl(String.valueOf(file), String.valueOf(createdAt), userToken.getAccessToken());
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.d("UploadFilesToServer", e.getMessage());
        }

    }


}
