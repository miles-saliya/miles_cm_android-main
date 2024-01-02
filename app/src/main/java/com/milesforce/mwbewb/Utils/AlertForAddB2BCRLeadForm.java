package com.milesforce.mwbewb.Utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
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
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.milesforce.mwbewb.Activities.MainActivity;
import com.milesforce.mwbewb.Model.CallLogs;
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

import static com.milesforce.mwbewb.Utils.ConstantUtills.IS_SUCCESS_CALL;
import static com.milesforce.mwbewb.Utils.ConstantUtills.VERSION_NUMBER;

public class AlertForAddB2BCRLeadForm {
    String accesssToken;
    Context context;
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
    ArrayList<String> spinnerLevelList;
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


    TextView compose_text, preview_text, title_header, textview_header;
    LinearLayout preview_layout, compose_layout;
    EditText subject_edit, compose_edit, content_whatsUp;
    WebView webView;
    AssetManager assetManager;
    String PreviewData;
    RadioGroup responce_radio_group;
    String RadioBtnResponseType = null;
    EditText engagement_description;
    AppCompatCheckBox cpaCheckbox, cmaCheckbox, dacheckbox, b2c_cpa_check, b2c_cma_check, b2c_da_check;
    static String CPAChecked = " ";
    static String CPMChecked = " ";
    static String DaChecked = " ";
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
    EditText b2c_lead_name, b2c_lead_mobile, b2c_lead_email, b2c_lead_education, b2c_lead_company, b2c_lead_designation, b2c_lead_experiance, b2c_lead_engagement, b2c_lead_nextCall_picker_;
    EditText b2bcr_leadname, b2bcr_leadmobile, b2bcr_lead_email, b2b_cr_lead_company, b2b_cr_lead_designation, b2b_cr_lead_experiance, b2b_cr_lead_engagement, direct_reference_cr;
    EditText lead_b2b_ir_name, lead_b2b_ir_mobile, lead_b2b_ir_email, lead_b2b_ir_institute, lead_b2b_ir_designation, lead_b2b_ir_engagement, lead_b2b_ir_reference;
    String MWbLeadCourseData = " ";
    LinearLayout user_info_snakebar, corporate_company;
    ProgressBar add_b2b_progresss, b2b_cr_progress, b2b_ir_progress;
    String other_check_radio_text;
    BatteryModel batteryModel;
    RadioButton netenquiery, referal_radio, direct_radio, corporate, university;
    AppCompatAutoCompleteTextView autoCompleteText_mwb_b2c;
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


    public void addb2bcrLeadForm(final Context context, String AccessToken, String callLogs) {
        batteryModel = new BatterPercentage().getBattertPercentage(context);
        ContentOdData();
        this.context = context;
        accessToken = AccessToken;
        LEADSOURCE = " ";
        LeadDetails = " ";
        LeadDetails = " ";
        LEADCity = " ";
        other_check_radio_text = " ";
        LeadsCalendar = Calendar.getInstance();
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.addleadfor_btb_cr);
        dialog.setCancelable(true);
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
        corporate_company = dialog.findViewById(R.id.corporate_company_b2b_cr);
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
        netenquiery = dialog.findViewById(R.id.netenquiery);
        referal_radio = dialog.findViewById(R.id.referal_radio);
        direct_radio = dialog.findViewById(R.id.direct_radio);
        corporate = dialog.findViewById(R.id.corporate);
        university = dialog.findViewById(R.id.university);
        autoCompleteText_mwb_b2c = dialog.findViewById(R.id.autoCompleteText_mwb_b2c);

        b2bcr_leadmobile.setText(callLogs);
        b2bcr_leadmobile.setFocusable(false);
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> cityAdpater = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, cityArrayList);
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


        ArrayAdapter<String> levelsAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, milesSpos_spinnerArrayList);
        // Drop down layout style - list view with radio button
        levelsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        milesSpos_spinner.setAdapter(levelsAdapter);


        b2c_lead_nextCall_picker_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, leads_date, LeadsCalendar.get(Calendar.YEAR), LeadsCalendar.get(Calendar.MONTH), LeadsCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void ContentOdData() {
        levelsArrayList.add("L1");
        levelsArrayList.add("L2");
        levelsArrayList.add("L3");
        levelsArrayList.add("L4");
        levelsArrayList.add("L5");
        levelsArrayList.add("L6");
        levelsArrayList.add("L7");
        levelsArrayList.add("M1");
        levelsArrayList.add("M2");
        levelsArrayList.add("M3");
        levelsArrayList.add("M4");
        levelsArrayList.add("M5");
        levelsArrayList.add("M6");
        levelsArrayList.add("M7");

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

    private void AddLeadMwbB2bcr(final Dialog dialog) {
        apiClient = ApiUtills.getAPIService();
        if (b2bcr_leadname.getText().toString().trim().isEmpty()) {
            b2bcr_leadname.setError("Please enter name");
        }
        if ((b2bcr_lead_email.getText().toString().isEmpty()) || (b2bcr_leadmobile.getText().toString().isEmpty())) {

            Toast.makeText(context, "Please enter either email or mobile ", Toast.LENGTH_SHORT).show();
        } else {
            if (other_check_radio_text.equals("others")) {
                LeadDetails = direct_reference_cr.getText().toString();
            }
            b2b_cr_progress.setVisibility(View.VISIBLE);
            apiClient.AddMwbLead(b2bcr_leadname.getText().toString().trim(), "M1", "None", "B2BCR", b2b_cr_lead_company.getText().toString().trim(), b2b_cr_lead_designation.getText().toString().trim(), b2b_cr_lead_experiance.getText().toString().trim(), " ", LEADCity, "", LEADSOURCE, LeadDetails, b2bcr_lead_email.getText().toString(), b2bcr_leadmobile.getText().toString(), b2b_cr_lead_engagement.getText().toString().trim(), nextCallTimeStamp, 0, batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), VERSION_NUMBER,"","","","","","Bearer " + accessToken, "application/json").enqueue(new Callback<SuccessModel>() {
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
                            if (response.body().getStatus().equals("success")){
                                Toast.makeText(context, String.valueOf(response.body().getMessage()), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                b2b_cr_progress.setVisibility(View.GONE);
                            }else {
                                Toast.makeText(context, String.valueOf(response.body().getMessage()), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                b2b_cr_progress.setVisibility(View.GONE);
                            }

                        }
                    } catch (Exception e) {
                        Toast.makeText(context, String.valueOf(response.body().getMessage()), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        b2b_cr_progress.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<SuccessModel> call, Throwable t) {
                    Toast.makeText(context, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    b2b_cr_progress.setVisibility(View.GONE);
                }
            });
        }
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

    private void getUniversitiesData() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (context, android.R.layout.select_dialog_item, universities);

        autoCompleteText_mwb_b2c.setThreshold(1);
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

    private void getAutoCompleteData() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (context, android.R.layout.select_dialog_item, corporates);

        autoCompleteText_mwb_b2c.setThreshold(1);
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
