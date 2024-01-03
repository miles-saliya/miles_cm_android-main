package com.milesforce.mwbewb.Utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Handler;
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
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.milesforce.mwbewb.Activities.MainActivity;
import com.milesforce.mwbewb.EngagementFragments.LevelsCustomAdapter;
import com.milesforce.mwbewb.Model.CallLogs;
import com.milesforce.mwbewb.Model.LevelsModel;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;
import com.milesforce.mwbewb.Retrofit.SuccessModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;
import static com.milesforce.mwbewb.Utils.ConstantUtills.IIML_TAB_CHANGE_CODE;
import static com.milesforce.mwbewb.Utils.ConstantUtills.VERSION_NUMBER;

public class AlertForAddB2CLeadForml {
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
    EditText date_picker_;
    Calendar myCalendar, LeadsCalendar;
    AppCompatImageButton bt_menu_back_from_caller, addLeadForm, search_icon;
    ImageView endCall_btn, addEngagementForm;
    public static final int PERMISSION_ACCESS_CALL_PHONE = 20;
    RadioGroup radioGroup, radioGroupForReference;
    LinearLayout referal, direct, referal_layout, reference_layout_spinner;
    AppCompatSpinner ewbspinner, mwbspinner, M_levels_spinner, city_spinner, direct_spinner, milesSpos_spinner,connection_status_spinner;
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
    String MWbLeadCourseData = " ";
    static String IIML_FA_Checked = " ";
    static String IIML_BA_Checked = " ";
    static String IIML_PA_Checked = " ";
    static  String IIML_HR_Checked= " ";
    static  String IITR_BF_Checked= " ";
    static  String IITR_DBE_Checked= " ";
    static String SELECTED_STATUS = " ";
    static String ConnectionStatus = " ";
    String LAST_TENDIGIT_MOBILE_NUMBER = null;
    String NETENQUIRY_TYPE = "";

    TextView compose_text, preview_text, title_header, textview_header;
    LinearLayout preview_layout, compose_layout;
    EditText subject_edit, compose_edit, content_whatsUp;
    WebView webView;
    AssetManager assetManager;
    String PreviewData;
    RadioGroup responce_radio_group;
    String RadioBtnResponseType = null;
    EditText engagement_description;
    AppCompatCheckBox b2c_cpa_check,b2c_cma_check, b2c_iiml_fa_check, b2c_iiml_ba_check,b2c_iiml_pa_check,b2c_iiml_hr_check,b2c_iitr_bf_check,b2c_iitr_dbe_check,b2c_iimlfa_check,b2c_iimlsf_check;
    static String CPAChecked = " ";
    static String CPMChecked = " ";
    static String DaChecked = " ";
    static String WCBAChecked = " ";
    static String IIMLChecked = " ";
    static String CoursesData = null;
    int person_id, can_id, Mobile_id;
    String previousEngagement, courses, levels, user_name, person_name;
    EditText latestEngagement;
    LinearLayout nextTimeLayout, engagement_main_form;
    String LevelsSelected;
    private static long nextCallTimeStamp = 0;
    ProgressBar add_engagement_progress;
    AppCompatSpinner appconpact_spinner_connectionstatus;
    AlertDialog alert;
    ArrayList<LevelsModel> spinnerLevelList;
    EditText b2c_lead_name, b2c_lead_mobile, b2c_lead_email, b2c_lead_education, b2c_lead_company, b2c_lead_designation, b2c_lead_experiance, b2c_lead_engagement, b2c_lead_nextCall_picker_,b2c_international,b2c_country;
    EditText b2bcr_leadname, b2bcr_leadmobile, b2bcr_lead_email, b2b_cr_lead_company, b2b_cr_lead_designation, b2b_cr_lead_experiance, b2b_cr_lead_engagement, direct_reference_cr;
    EditText lead_b2b_ir_name, lead_b2b_ir_mobile, lead_b2b_ir_email, lead_b2b_ir_institute, lead_b2b_ir_designation, lead_b2b_ir_engagement, lead_b2b_ir_reference;
    LinearLayout user_info_snakebar, corporate_company;
    ProgressBar add_b2b_progresss, b2b_cr_progress, b2b_ir_progress;
    String other_check_radio_text;
    Context context;
    BatteryModel batteryModel;
    RadioButton netenquiery, referal_radio, direct_radio, corporate, university;
    LevelsCustomAdapter levelsCustomAdapter;
    LinearLayout iiml_course_layout,ba_course_layout;
    ArrayAdapter<String> cityAdpater;
    private String[] corporates = {
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
    AppCompatAutoCompleteTextView autoCompleteText_mwb_b2c;
    CardView source_layout_layout,source_info_layout;
    TextView souceText ,SourceID;
    AppCompatSpinner U_levels_spinner;
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


    public void AddB2cLeadForm(final Context context, String accessToken, String callLogs,String email,String name,String source,String sourceId ,int type,String netEnquiryType) {
        NETENQUIRY_TYPE = netEnquiryType;
        batteryModel = new BatterPercentage().getBattertPercentage(context);
        apiClient = ApiUtills.getAPIService();
        ContentOdData();
        this.accessToken = accessToken;
        this.context = context;
        other_check_radio_text = "";
        LeadsCalendar = Calendar.getInstance();
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_add_engagementform);
        dialog.setCancelable(true);
        U_levels_spinner=dialog.findViewById(R.id.U_levels_spinner);
        ArrayAdapter<String> U_levels_spinner_Adapter = new ArrayAdapter<String>(dialog.getContext(),
                android.R.layout.simple_spinner_item, uLevelList);
        U_levels_spinner_Adapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        U_levels_spinner.setAdapter(U_levels_spinner_Adapter);
        corporate_company = dialog.findViewById(R.id.corporate_company_b2c);
        netenquiery = dialog.findViewById(R.id.netenquiery);
        referal_radio = dialog.findViewById(R.id.referal_radio);
        direct_radio = dialog.findViewById(R.id.direct_radio);
        corporate = dialog.findViewById(R.id.corporate);
        university = dialog.findViewById(R.id.university);
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
        source_layout_layout = dialog.findViewById(R.id.source_layout_layout);
        ewbspinner = dialog.findViewById(R.id.MwbSpinner);
        mwbspinner = dialog.findViewById(R.id.ewbSpinner);
        direct_reference = dialog.findViewById(R.id.direct_reference);
        direct_spinner = dialog.findViewById(R.id.direct_spinner);
        city_spinner = dialog.findViewById(R.id.city_spinner);
        M_levels_spinner = dialog.findViewById(R.id.M_levels_spinner);
        b2c_cpa_check = dialog.findViewById(R.id.b2c_cpa_check);
        b2c_cma_check = dialog.findViewById(R.id.b2c_cma_check);
        b2c_iimlfa_check = dialog.findViewById(R.id.b2c_iimlfa_check);
        b2c_iimlsf_check = dialog.findViewById(R.id.b2c_iimlsf_check);
        b2c_lead_name = dialog.findViewById(R.id.b2c_lead_name);
        b2c_lead_mobile = dialog.findViewById(R.id.b2c_lead_mobile);
        b2c_lead_email = dialog.findViewById(R.id.b2c_lead_email);
//        b2c_lead_education = dialog.findViewById(R.id.b2c_lead_education);
//        b2c_lead_company = dialog.findViewById(R.id.b2c_lead_company);
//        b2c_lead_designation = dialog.findViewById(R.id.b2c_lead_designation);
//        b2c_lead_experiance = dialog.findViewById(R.id.b2c_lead_experiance);
        b2c_lead_engagement = dialog.findViewById(R.id.b2c_lead_engagement);
        b2c_lead_nextCall_picker_ = dialog.findViewById(R.id.b2c_lead_nextCall_picker_);
        b2c_iiml_fa_check = dialog.findViewById(R.id.b2c_iiml_fa_check);
        b2c_iiml_ba_check = dialog.findViewById(R.id.b2c_iiml_ba_check);
        b2c_iiml_pa_check = dialog.findViewById(R.id.b2c_iiml_pa_check);
        b2c_iiml_hr_check = dialog.findViewById(R.id.b2c_iiml_hr_check);
        b2c_iitr_bf_check = dialog.findViewById(R.id.b2c_iitr_bf_check);
        b2c_iitr_dbe_check = dialog.findViewById(R.id.b2c_iitr_dbe_check);
//        b2c_international  =dialog.findViewById(R.id.international_city);
        connection_status_spinner = dialog.findViewById(R.id.appconpact_spinner_connectionstatus);
//        b2c_country  = dialog.findViewById(R.id.country);
        souceText = dialog.findViewById(R.id.Source_test);
        SourceID = dialog.findViewById(R.id.Source_id);
        iiml_course_layout = dialog.findViewById(R.id.iiml_course_layout);
        ba_course_layout = dialog.findViewById(R.id.ba_course_layout);
        if(IIML_TAB_CHANGE_CODE == 0){
            iiml_course_layout.setVisibility(View.GONE);
            ba_course_layout.setVisibility(View.VISIBLE);
        }
        if(IIML_TAB_CHANGE_CODE == 1){
            iiml_course_layout.setVisibility(View.VISIBLE);
            ba_course_layout.setVisibility(View.GONE);

        }
        b2c_iiml_fa_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IIML_FA_Checked = "IIML-FA";

                } else {
                    IIML_FA_Checked = "";

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
                if(isChecked){
                    IITR_BF_Checked = "IITR-BF";
                }else {
                    IITR_BF_Checked = "";
                }
            }
        });
        b2c_iitr_dbe_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    IITR_DBE_Checked = "IITR-DB";
                }else {
                    IITR_DBE_Checked="";
                }
            }
        });



        autoCompleteText_mwb_b2c = dialog.findViewById(R.id.autoCompleteText_mwb_b2c);
        source_info_layout = dialog.findViewById(R.id.source_info_layout);

        add_b2b_progresss = dialog.findViewById(R.id.add_b2b_progresss);
        b2c_lead_mobile.setText(callLogs);
        // b2c_lead_mobile.setFocusable(false);
        b2c_lead_email.setText(email);
        b2c_lead_name.setText(name);
        if(type == 1){
            source_layout_layout.setVisibility(View.GONE);
            source_info_layout.setVisibility(View.VISIBLE);
            LEADSOURCE = source;
            LeadDetails = sourceId;
            souceText.setText(LEADSOURCE);
            SourceID.setText(LeadDetails);
        }else {
            source_layout_layout.setVisibility(View.VISIBLE);
            source_info_layout.setVisibility(View.GONE);
        }


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


        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ((Button) dialog.findViewById(R.id.bt_save_add)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveMWbB2CLead(dialog);
            }
        });

        netenquiery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    referal.setVisibility(View.GONE);
                    direct.setVisibility(View.GONE);
                    reference_layout_spinner.setVisibility(View.GONE);
                    LEADSOURCE = "Net Enquiry";
                    direct_radio.setChecked(false);
                    corporate.setChecked(false);
                    referal_radio.setChecked(false);
                    university.setChecked(false);
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
                    direct_radio.setChecked(false);
                    corporate.setChecked(false);
                    netenquiery.setChecked(false);
                    university.setChecked(false);
                    corporate_company.setVisibility(View.GONE);
                }
            }
        });
        direct_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    referal.setVisibility(View.GONE);
                    direct.setVisibility(View.VISIBLE);
                    reference_layout_spinner.setVisibility(View.GONE);
                    LEADSOURCE = "Direct";
                    netenquiery.setChecked(false);
                    corporate.setChecked(false);
                    referal_radio.setChecked(false);
                    university.setChecked(false);
                    corporate_company.setVisibility(View.GONE);
                }
            }
        });
        corporate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    referal.setVisibility(View.GONE);
                    direct.setVisibility(View.GONE);
                    reference_layout_spinner.setVisibility(View.GONE);
                    LEADSOURCE = "Corporate";
                    netenquiery.setChecked(false);
                    direct_radio.setChecked(false);
                    referal_radio.setChecked(false);
                    university.setChecked(false);
                    corporate_company.setVisibility(View.VISIBLE);
                    getAutoCompleteData();
                }
            }
        });
        university.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    referal.setVisibility(View.GONE);
                    direct.setVisibility(View.GONE);
                    reference_layout_spinner.setVisibility(View.GONE);
                    LEADSOURCE = "University";
                    netenquiery.setChecked(false);
                    direct_radio.setChecked(false);
                    referal_radio.setChecked(false);
                    corporate.setChecked(false);
                    corporate_company.setVisibility(View.VISIBLE);
                    getUniversitiesData();

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
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, mwbArrayList);
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

        ArrayAdapter<String> connection_statusAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, ConnectionTypeArrayList);
        connection_statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        connection_status_spinner.setAdapter(connection_statusAdapter);
        connection_status_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SELECTED_STATUS = connection_status_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /*EWB Spinner*/

        ArrayAdapter<String> ewbAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, ewbArraylist);
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
        ArrayAdapter<String> directAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, directSourceArraylist);
        // Drop down layout style - list view with radio button
        directAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        direct_spinner.setAdapter(directAdapter);

        direct_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LeadDetails = direct_spinner.getSelectedItem().toString();
                Toast.makeText(context, ""+LeadDetails, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        cityAdpater = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, cityArrayList);
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

        levelsCustomAdapter = new LevelsCustomAdapter(context, R.layout.listitems_layout, R.id.levels_items,
                spinnerLevelList);
        M_levels_spinner.setAdapter(levelsCustomAdapter);
        M_levels_spinner.setEnabled(false);
        apiClient.getSpocCity("Bearer "+accessToken).enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                try {
                    if(response.body() ==null){
                    }else {
                        String City = response.body().getCity();//the value you want the position for
                        int spinnerPosition = cityAdpater.getPosition(City);
                        city_spinner.setSelection(spinnerPosition);
                        LEADCity = City;
                        Toast.makeText(context, ""+LEADCity, Toast.LENGTH_SHORT).show();

                    }
                }catch (Exception e){

                }

            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {

            }
        });

     /*   ArrayAdapter<String> levelsAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, levelsArrayList);
        // Drop down layout style - list view with radio button
        levelsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        levels_spinner.setAdapter(levelsAdapter);*/
        M_levels_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                LevelsModel levelsModel = spinnerLevelList.get(position);
                LeadLevels = levelsModel.getLevelCode();
                Toast.makeText(context, LeadLevels, Toast.LENGTH_SHORT).show();
                // Toast.makeText(context, LeadLevels, Toast.LENGTH_SHORT).show();

                /*LeadLevels = levels_spinner.getSelectedItem().toString();
                Toast.makeText(context,LeadLevels, Toast.LENGTH_SHORT).show();*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        b2c_lead_nextCall_picker_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(context, leads_date, LeadsCalendar
                        .get(Calendar.YEAR), LeadsCalendar.get(Calendar.MONTH),
                        LeadsCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(LeadsCalendar.getTimeInMillis());
                datePickerDialog .show();
            }
        });
    }

    private void ContentOdData() {
        spinnerLevelList = new ArrayList<>();
        /*spinnerLevelList.add(new LevelsModel("M6", "M6 - Visited & Ready to Enroll"));
        spinnerLevelList.add(new LevelsModel("M5", "M5 - Visited & Positive"));
        spinnerLevelList.add(new LevelsModel("M4", "M4 - Visited but not interested"));
        spinnerLevelList.add(new LevelsModel("M4-", "M4- - Visited but not interested"));
        spinnerLevelList.add(new LevelsModel("M3+", "M3+ - Called & Coming"));*/
        spinnerLevelList.add(new LevelsModel("M3", "M3 - Called & positive"));
       /* spinnerLevelList.add(new LevelsModel("M2", "M2 - Did not visit & Postponed"));
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
        ConnectionTypeArrayList = new ArrayList<>();
        ConnectionTypeArrayList.add("Connected / Busy");
        ConnectionTypeArrayList.add("Connected / Discussed");
        ConnectionTypeArrayList.add("Connected / Never call back");
        ConnectionTypeArrayList.add("Connected / Wrong number");
        ConnectionTypeArrayList.add("Busy");
        ConnectionTypeArrayList.add("Not Lifting");
        ConnectionTypeArrayList.add("Not Reachable");
        ConnectionTypeArrayList.add("Disconnected");
        ConnectionTypeArrayList.add("Invalid Number");
        ConnectionTypeArrayList.add("Switched Off");

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
            Toast.makeText(context, "Please select Connection status ", Toast.LENGTH_SHORT).show();
            return ;
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
            Toast.makeText(context, "Please fill either Email or Mobile number.....!", Toast.LENGTH_SHORT).show();
        } else {
            if (other_check_radio_text.equals("others")) {
                LeadDetails = direct_reference.getText().toString();
            }
            if (isValidateMwbForm()) {
                if(MWbLeadCourseData.equals(" ")){
                    Toast.makeText(context, "Please select course.....!", Toast.LENGTH_SHORT).show();
                    return;
                }
                add_b2b_progresss.setVisibility(View.VISIBLE);
                if(b2c_lead_mobile.getText().toString().trim().length() == 10){
                    LAST_TENDIGIT_MOBILE_NUMBER = b2c_lead_mobile.getText().toString().trim();
                }if(b2c_lead_mobile.getText().toString().trim().length() > 10){
                    String Mobile_NUmber = b2c_lead_mobile.getText().toString().trim();
                    LAST_TENDIGIT_MOBILE_NUMBER = Mobile_NUmber.substring(Mobile_NUmber.length() - 10);
                }
                if(b2c_lead_mobile.getText().toString().trim().length() < 10){
                    Toast.makeText(context, "Please enter 10 digits mobile number", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }
                String international = b2c_international.getText().toString().trim();
                String country_intr = b2c_country.getText().toString().trim();
                apiClient.AddMwbLead(b2c_lead_name.getText().toString().trim(), LeadLevels, MWbLeadCourseData, "B2C", b2c_lead_company.getText().toString(), b2c_lead_designation.getText().toString(), b2c_lead_experiance.getText().toString(), b2c_lead_education.getText().toString(), LEADCity, "", LEADSOURCE, LeadDetails, b2c_lead_email.getText().toString().trim(), LAST_TENDIGIT_MOBILE_NUMBER, b2c_lead_engagement.getText().toString().trim(), nextCallTimeStamp, 0, batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), VERSION_NUMBER,international,country_intr, ConnectionStatus,NETENQUIRY_TYPE,U_levels_spinner.getSelectedItem().toString(),"Bearer " + accessToken, "application/json").enqueue(new Callback<SuccessModel>() {
                    @Override
                    public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                        try {
                            if (response.body() == null) {
                                int statusCode = response.raw().code();
                                if (statusCode > 399 && statusCode < 500) {
                                    Toast.makeText(context, "Yours Session is Expired..Please Try aftre Login...", Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            } else {
                                if (response.body().getStatus().equals("success")) {
                                    dialog.dismiss();
                                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    add_b2b_progresss.setVisibility(View.GONE);
                                }if (response.body().getStatus().equals("error")) {
                                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    String spam_number_check = response.body().getMessage();
                                    add_b2b_progresss.setVisibility(View.GONE);
                                    if(spam_number_check.contains("spam")){
                                        showSpamDialog(dialog,LAST_TENDIGIT_MOBILE_NUMBER,b2c_lead_mobile.getText().toString().trim(),"Bearer " + accessToken);
                                    }else {
                                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        add_b2b_progresss.setVisibility(View.GONE);
                                        dialog.dismiss();

                                    }
                                }

                                else {
                                    //   dialog.dismiss();
                                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    add_b2b_progresss.setVisibility(View.GONE);
                                }
                            }
                        } catch (Exception e) {
                            Toast.makeText(context, String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
                            add_b2b_progresss.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessModel> call, Throwable t) {
                        Toast.makeText(context, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                        add_b2b_progresss.setVisibility(View.GONE);
                    }
                });
            }
        }

    }

    private void showSpamDialog(Dialog dialog, final String last_tendigit_mobile_number,String original_number, final String accessToken) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(dialog.getContext());
        builder1.setMessage("This number("+original_number+" ) is marked as spam. Would you like to remove it from Spam ?");
        builder1.setCancelable(false);


        builder1.setPositiveButton(
                "Remove from Spam",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        removeThisNumberFromSpamApi(dialog,last_tendigit_mobile_number,accessToken);
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
    private void removeThisNumberFromSpamApi(DialogInterface dialog, String last_tendigit_mobile_number, String bearerAccessToken) {
        apiClient.removeFromSpam(last_tendigit_mobile_number,bearerAccessToken).enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                if(response.body().getStatus().equals("success")){
                    Toast.makeText(context, "Removed the number from spam. Now add a lead with this number", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, ""+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
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
        if(b2c_iiml_pa_check.isChecked()){
            MWbLeadCourseData = IIML_PA_Checked;
        }
        if(b2c_iiml_hr_check.isChecked()){
            MWbLeadCourseData = IIML_HR_Checked;
        }
        if(b2c_iitr_bf_check.isChecked()){
            MWbLeadCourseData = IITR_BF_Checked ;

        }
        if(b2c_iitr_dbe_check.isChecked()){
            MWbLeadCourseData = IITR_DBE_Checked;
        }
        if(b2c_iiml_fa_check.isChecked() && b2c_iitr_dbe_check.isChecked()){
            MWbLeadCourseData =IIML_FA_Checked + ","+ IITR_DBE_Checked;
        }
        if(b2c_iiml_ba_check.isChecked() && b2c_iitr_dbe_check.isChecked()){
            MWbLeadCourseData =IIML_BA_Checked + ","+ IITR_DBE_Checked;
        }
        if(b2c_iitr_bf_check.isChecked() && b2c_iitr_dbe_check.isChecked()){
            MWbLeadCourseData =IITR_BF_Checked + ","+ IITR_DBE_Checked;
        }
        if (b2c_iiml_pa_check.isChecked() && b2c_iitr_bf_check.isChecked()) {
            MWbLeadCourseData = IIML_PA_Checked + "," + IITR_BF_Checked;
        }
        if (b2c_iiml_fa_check.isChecked() && b2c_iitr_bf_check.isChecked()) {
            MWbLeadCourseData = IIML_FA_Checked + "," + IITR_BF_Checked;
        }
        if(b2c_iiml_fa_check.isChecked() && b2c_iiml_pa_check.isChecked()){
            MWbLeadCourseData =IIML_FA_Checked + ","+ IIML_PA_Checked;
        }
        if(b2c_iiml_ba_check.isChecked() && b2c_iiml_pa_check.isChecked()){
            MWbLeadCourseData =IIML_BA_Checked + ","+ IIML_PA_Checked;
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
        if (b2c_cpa_check.isChecked()) {
            MWbLeadCourseData = CPAChecked_lead;
        }
        if ( b2c_cma_check.isChecked()) {
            MWbLeadCourseData = CMAChecked_lead;
        }
        if(b2c_iimlfa_check.isChecked()){
            MWbLeadCourseData = IIML_FA_Checked_lead;
        }
        if(b2c_iimlsf_check.isChecked()){
            MWbLeadCourseData = IIML_SF_Checked_lead;
        }
        if(b2c_iimlfa_check.isChecked() && b2c_iimlsf_check.isChecked()){
            MWbLeadCourseData = IIML_FA_Checked_lead+ "," + IIML_SF_Checked_lead;;
        }
        return true;
    }

    private void getUniversitiesData() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (context, android.R.layout.select_dialog_item, universities);

        autoCompleteText_mwb_b2c.setThreshold(3);
        autoCompleteText_mwb_b2c.setAdapter(adapter);
        autoCompleteText_mwb_b2c.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LeadDetails = (String) parent.getItemAtPosition(position);
              /*  InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);*/

                if (view != null) {
                    InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_SHOWN);

                }




            }
        });
    }

    private void getAutoCompleteData() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (context, android.R.layout.select_dialog_item, corporates);

        autoCompleteText_mwb_b2c.setThreshold(3);
        autoCompleteText_mwb_b2c.setAdapter(adapter);
        autoCompleteText_mwb_b2c.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LeadDetails = (String) parent.getItemAtPosition(position);
                InputMethodManager in = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
            }
        });
    }
}
