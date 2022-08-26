package com.milesforce.mwbewb.IIML;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.milesforce.mwbewb.EngagementFragments.LevelsCustomAdapter;
import com.milesforce.mwbewb.Model.LevelsModel;
import com.milesforce.mwbewb.Model.UserToken;
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

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.milesforce.mwbewb.Utils.ConstantUtills.VERSION_NUMBER;

/**
 * A simple {@link Fragment} subclass.
 */
public class IIMLNewEngagement extends Fragment implements View.OnClickListener {
    FloatingActionButton call;
    Realm realm;
    UserToken userToken;
    String previousEngagement = null;
    String courses, user_name, phone_number;
    String levels ;
    String IIML_LEVEL;
    int person_id, can_id;
    String LevelsSelected = null;
    String User_levels = " ";
    String NewLevel = " ";
    static String ConnectionStatus = " ";
    private static long nextCallTimeStamp = 0;
    Calendar myCalendar;
    ArrayList<String> spinnerLevelList;
    LinearLayout nextTimeLayout, engagement_main_form,level_changes_new;
    EditText latestEngagement;
    TextView compose_text, preview_text, title_header, textview_header;
    AppCompatSpinner appconpact_spinner_levels, appconpact_spinner_connectionstatus;
    ProgressBar add_engagement_progress;
    RadioGroup responce_radio_group;
    EditText engagement_description;
    static String SELECTED_STATUS = " ";
    ArrayList<String> ConnectionTypeArrayList;
    ArrayList<String> levelsArrayList;
    static String CoursesData = null;
    EditText date_picker_;
    ArrayAdapter<String> levelArrayAdapter;
    AppCompatCheckBox iiml_checked,iiml_pa_checked,iiml_fa_checked,iiml_hr_checked,iitr_bf_checked,iitr_dbe_checked;
    static String IIML_BA_Course = " ";
    static String IIML_PA_Course = "";
    static String IIML_FA_Course = "";
    static String IIML_HR_Course = "";
    static String IITR_BF_Course = "";
    static  String IITR_DBE_Checked= " ";
    static  String FINAL_SELECTED_CourseData=" ";

    ApiClient apiClient;
    AlertDialog alert;
    DatePickerDialog datePickerDialog;
    int MAX_DATE_PRE_SELECT = 0;

    public IIMLNewEngagement() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        realm = Realm.getDefaultInstance();
        if (realm.where(UserToken.class).findFirst() != null) {
            userToken = realm.where(UserToken.class).findFirst();
        }
        apiClient = ApiUtills.getAPIService();

        person_id = getArguments().getInt("person_id");
        can_id = getArguments().getInt("can_id");
        previousEngagement = getArguments().getString("previousEngagement");
        courses = getArguments().getString("courses");
        levels = getArguments().getString("levels");
        user_name = getArguments().getString("user_name");
        phone_number = getArguments().getString("phone_number");
        IIML_LEVEL = getArguments().getString("iimllevels");
        LevelsSelected = IIML_LEVEL;
        User_levels = IIML_LEVEL;
        NewLevel = IIML_LEVEL;
        IIML_BA_Course = courses;
        if (courses.contains("IIML-BA")) {
            IIML_BA_Course = "IIML-BA";

        }
        if (courses.contains("IIML-FA")) {
            IIML_BA_Course = "IIML-FA";
        }
        if (courses.contains("IIML-PM")) {
            IIML_BA_Course = "IIML-PM";
        }
        Log.d("IIML_BA_Course", IIML_BA_Course);
        return inflater.inflate(R.layout.fragment_iimlnew_engagement, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        call = view.findViewById(R.id.call_);
        call.setOnClickListener(this);
        CustomDataPOints();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_:
                showCustomDialog(user_name, NewLevel);
                break;
            case R.id.date_picker_:
                openAlertForDatepicker();
                break;
        }


    }

    private void showCustomDialog(final String user_name, final String level) {
        nextCallTimeStamp = 0;
        ConnectionStatus = "";
        spinnerLevelList = new ArrayList<>();
        myCalendar = Calendar.getInstance();
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.iiml_dialog_event);
        dialog.setCancelable(true);

        nextTimeLayout = dialog.findViewById(R.id.nextTimeLayout);
        engagement_main_form = dialog.findViewById(R.id.engagement_main_form);
        level_changes_new = dialog.findViewById(R.id.level_changes_new);
        latestEngagement = dialog.findViewById(R.id.latestEngagement);
        latestEngagement.setText(previousEngagement);
        textview_header = dialog.findViewById(R.id.textview_header);
        textview_header.setText(user_name + " - " + levels);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        appconpact_spinner_levels = dialog.findViewById(R.id.appconpact_spinner_levels);
        appconpact_spinner_connectionstatus = dialog.findViewById(R.id.appconpact_spinner_connectionstatus);
        add_engagement_progress = dialog.findViewById(R.id.add_engagement_progress);
        responce_radio_group = dialog.findViewById(R.id.responce_radio_group);
        engagement_description = dialog.findViewById(R.id.engagement_description);
        engagement_description.setText("");
        iiml_checked = dialog.findViewById(R.id.iiml_checked);
        iiml_pa_checked = dialog.findViewById(R.id.iiml_pa_checked);
        iiml_fa_checked = dialog.findViewById(R.id.iiml_fa_checked);
        iiml_hr_checked = dialog.findViewById(R.id.iiml_hr_checked);
        iitr_bf_checked = dialog.findViewById(R.id.iitr_bf_checked);
        iitr_dbe_checked = dialog.findViewById(R.id.iitr_dbe_checked);
        iitr_bf_checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    IITR_BF_Course = "IITR-BF";
                }else {
                    IITR_BF_Course = " ";

                }

            }
        });
        iitr_dbe_checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    IITR_DBE_Checked = "IITR-DB";
                }else {
                    IITR_DBE_Checked = " ";

                }
            }
        });

        iiml_hr_checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IIML_HR_Course = "IIML-HR";

                } else {
                    IIML_HR_Course = " ";

                }

            }
        });
        iiml_checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IIML_BA_Course = "IIML-BA";

                } else {
                    IIML_BA_Course = " ";

                }
            }
        });
        iiml_pa_checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IIML_PA_Course = "IIML-PM";

                } else {
                    IIML_PA_Course = " ";

                }
            }
        });
        iiml_fa_checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    IIML_FA_Course = "IIML-FA";

                } else {
                    IIML_FA_Course = " ";

                }
            }
        });


        if (courses.contains("IIML-BA")) {
            iiml_checked.setChecked(true);

        }
        if (courses.contains("IIML-PM")) {
            iiml_pa_checked.setChecked(true);

        }
        if (courses.contains("IIML-FA")) {
            iiml_fa_checked.setChecked(true);
        }
        if (courses.contains("IIML-HR")) {
            iiml_hr_checked.setChecked(true);
        }
        if(courses.contains("IITR-BF")){
            iitr_bf_checked.setChecked(true);
        }
        if (courses.contains("IITR-DB")){
            iitr_dbe_checked.setChecked(true);
        }
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

        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ((Button) dialog.findViewById(R.id.bt_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitCallEngagementForm(dialog);

            }
        });


        levelArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, levelsArrayList);
        levelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appconpact_spinner_levels.setAdapter(levelArrayAdapter);
        appconpact_spinner_levels.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NewLevel = appconpact_spinner_levels.getSelectedItem().toString();
                myCalendar = Calendar.getInstance();
                myCalendar.set(Calendar.YEAR, myCalendar.get(Calendar.YEAR));
                myCalendar.set(Calendar.MONTH, myCalendar.get(Calendar.MONTH));
                MAX_DATE_PRE_SELECT = 0;
                if (NewLevel.equals("Cold")){
                    myCalendar.add(Calendar.DAY_OF_MONTH, 0);
                    MAX_DATE_PRE_SELECT = 0;
                }
                if (NewLevel.equals("Hot")){
                    myCalendar.add(Calendar.DAY_OF_MONTH, 7);
                    MAX_DATE_PRE_SELECT = 7;
                }
                if (NewLevel.equals("Very Hot")){
                    myCalendar.add(Calendar.DAY_OF_MONTH, 7);
                    MAX_DATE_PRE_SELECT = 7;
                }
                if (NewLevel.equals("Warm")){
                    myCalendar.add(Calendar.DAY_OF_MONTH, 7);
                    MAX_DATE_PRE_SELECT = 7;
                }
                if (NewLevel.equals("Application Received")){
                    myCalendar.add(Calendar.DAY_OF_MONTH, 7);
                    MAX_DATE_PRE_SELECT = 7;
                }
                if (NewLevel.equals("Exam-Fail")){
                    myCalendar.add(Calendar.DAY_OF_MONTH, 7);
                    MAX_DATE_PRE_SELECT = 7;
                }
                if (NewLevel.equals("Exam-Pass-Dropout")){
                    myCalendar.add(Calendar.DAY_OF_MONTH, 7);
                    MAX_DATE_PRE_SELECT = 7;
                }
                if (NewLevel.equals("Exam-Fail-Dropout")){
                    myCalendar.add(Calendar.DAY_OF_MONTH, 7);
                    MAX_DATE_PRE_SELECT = 7;
                }
                if (NewLevel.equals("Exam-Noshow-Dropout")){
                    myCalendar.add(Calendar.DAY_OF_MONTH, 7);
                    MAX_DATE_PRE_SELECT = 7;
                }
                if (NewLevel.equals("Enquiry")){
                    myCalendar.add(Calendar.DAY_OF_MONTH, 7);
                    MAX_DATE_PRE_SELECT = 7;
                }
                if (NewLevel.equals("IIML-FA-M7")){
                    myCalendar.add(Calendar.DAY_OF_MONTH, 7);
                    MAX_DATE_PRE_SELECT = 7;
                }
                if (NewLevel.equals("CM-Yet-To-Connect")){
                    myCalendar.add(Calendar.DAY_OF_MONTH, 7);
                    MAX_DATE_PRE_SELECT = 7;
                }
                if (NewLevel.equals("Exam-Pass")){
                    myCalendar.add(Calendar.DAY_OF_MONTH, 7);
                    MAX_DATE_PRE_SELECT = 7;
                }
                if (NewLevel.equals("IIML-BA-M7") || NewLevel.equals("IIML-PM-M7") || NewLevel.equals("IIML-HR-M7") || NewLevel.equals("IITR-BF-M7") ){
                    myCalendar.add(Calendar.DAY_OF_MONTH, 7);
                    MAX_DATE_PRE_SELECT = 7;
                }
                updateLabel();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String myString = NewLevel; //the value you want the position for
        int spinnerPosition = levelArrayAdapter.getPosition(myString);
        //set the default according to value
        appconpact_spinner_levels.setSelection(spinnerPosition);


        ArrayAdapter<String> connection_statusAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, ConnectionTypeArrayList);

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
                        engagement_description.setText(" ");
                        ConnectionStatus = "CD";
                        level_changes_new.setVisibility(View.VISIBLE);
                    }else if(SELECTED_STATUS.equals("Not Lifting") || SELECTED_STATUS.equals("Connected / Wrong number") || SELECTED_STATUS.equals("Switched Off") || SELECTED_STATUS.equals("Connected / Never call back")) {
                         if(levelsArrayList.size()>0){
                             levelsArrayList.clear();
                             levelsArrayList = new ArrayList<>();
                             if(NewLevel.equals("Cold")){
                                 levelsArrayList.add("Cold");
                                 levelsArrayList.add("Hot");
                             }
                             if(NewLevel.equals("Hot")){
                                 levelsArrayList.add("Hot");
                                 levelsArrayList.add("Warm");
                             }
                             if(NewLevel.equals("Very Hot")){
                                 levelsArrayList.add("Hot");
                                 levelsArrayList.add("Warm");
                             }
                             if(NewLevel.equals("Warm")){
                                 levelsArrayList.add("Warm");
                                 levelsArrayList.add("Application Sent");
                             }
                             if(NewLevel.equals("Application Received")){
                                 levelsArrayList.add("Application Received");
                                 levelsArrayList.add("Exam-Pass");
                             }
                             if(NewLevel.equals("Exam-Pass")){
                                 levelsArrayList.add("Exam-Pass");
                                 levelsArrayList.add("Exam-Fail");
                             }
                             if(NewLevel.equals("Exam-Fail")){
                                 levelsArrayList.add("Exam-Fail");
                                 levelsArrayList.add("Exam-Pass-Dropout");
                             }
                             if(NewLevel.equals("Exam-Pass-Dropout")){
                                 levelsArrayList.add("Exam-Pass-Dropout");
                                 levelsArrayList.add("Exam-Fail-Dropout");
                             }
                             if(NewLevel.equals("Exam-Fail-Dropout")){
                                 levelsArrayList.add("Exam-Noshow-Dropout");
                                 levelsArrayList.add("Enquiry");
                             }
                             if(NewLevel.equals("Enquiry")){
                                 levelsArrayList.add("Enquiry");
                                 levelsArrayList.add("CM-Yet-To-Connect");
                             }
                             if(NewLevel.equals("CM-Yet-To-Connect")){
                                 levelsArrayList.add("CM-Yet-To-Connect");
                             }
                             if(NewLevel.equals("IIML-FA-M7")){
                                 levelsArrayList.add("IIML-FA-M7");
                             }
                             if(NewLevel.equals("IIML-BA-M7")){
                                 levelsArrayList.add("IIML-BA-M7");
                             }
                             if(NewLevel.equals("IIML-PM-M7")){
                                 levelsArrayList.add("IIML-PM-M7");
                             }
                             if(NewLevel.equals("IIML-HR-M7")){
                                 levelsArrayList.add("IIML-HR-M7");
                             }
                             if(NewLevel.equals("IITR-BF-M7")){
                                 levelsArrayList.add("IITR-BF-M7");
                             }

                             if(NewLevel.equals(" ")){
                                 levelsArrayList.add("Cold");
                                 levelsArrayList.add("Very Hot");
                                 levelsArrayList.add("Hot");
                                 levelsArrayList.add("Warm");
                                 levelsArrayList.add("Application Received");
                                 levelsArrayList.add("Exam-Pass");
                                 levelsArrayList.add("Exam-Fail");
                                 levelsArrayList.add("Exam-Pass-Dropout");
                                 levelsArrayList.add("Exam-Fail-Dropout");
                                 levelsArrayList.add("Exam-Noshow-Dropout");
                                 levelsArrayList.add("IIML-FA-M7");
                                 levelsArrayList.add("IIML-BA-M7");
                                 levelsArrayList.add("IIML-PM-M7");
                                 levelsArrayList.add("IIML-HR-M7");
                                 levelsArrayList.add("IITR-BF-M7");
                                 levelsArrayList.add("CM-Yet-To-Connect");

                             }
                             engagement_main_form.setVisibility(View.GONE);
                             level_changes_new.setVisibility(View.VISIBLE);
                         }
                        levelArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, levelsArrayList);
                        levelArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        appconpact_spinner_levels.setAdapter(levelArrayAdapter);
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
                    else {
                        engagement_main_form.setVisibility(View.GONE);
                        level_changes_new.setVisibility(View.GONE);
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


        selectDatapicker(dialog);
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();
        dialog.getWindow().setAttributes(lp);

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


        if (ConnectionStatus.equals("CD")) {
            if (engagement_description.getText().toString().equals(" ")) {
                engagement_description.setError("Please fill engagement description..");
                Toast.makeText(getContext(), "Please fill engagement description..", Toast.LENGTH_SHORT).show();
                return;
            }
            if (NewLevel.equals(" ")) {
                Toast.makeText(getContext(), "Please Select Level..", Toast.LENGTH_SHORT).show();
                return;
            }
            if(isValidateForm()){

            }
            if (FINAL_SELECTED_CourseData.equals(" ")) {
                Toast.makeText(getContext(), "Please select Course" + IIML_BA_Course, Toast.LENGTH_SHORT).show();
                return;
            }
        }
//        if (nextCallTimeStamp == 0) {
//            date_picker_.setError("Please select the Date...");
//            Toast.makeText(getContext(), "Please select Date", Toast.LENGTH_SHORT).show();
//            return;
//        }
        add_engagement_progress.setVisibility(View.VISIBLE);
        apiClient.AddEngagementForIIML(levels,ConnectionStatus, "", can_id, person_id, user_name, NewLevel, FINAL_SELECTED_CourseData, engagement_description.getText().toString().trim(), "call", 0, nextCallTimeStamp, " ", 0, "No", "", "", VERSION_NUMBER, "Bearer " + userToken.getAccessToken(), "application/json").enqueue(new Callback<SuccessModel>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                try {
                    if (response.body() == null) {
                        int statusCode = response.raw().code();
                        if (statusCode > 399 && statusCode < 500) {
                            // SessionLogout(getContext());
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


        Log.d("Final_Submit_Form", ConnectionStatus + "" + can_id + person_id + user_name + User_levels + IIML_BA_Course + engagement_description.getText().toString().trim() + "call" + 0 + nextCallTimeStamp + 0 + "No");


    }

    private boolean isValidateForm() {
        if (iiml_checked.isChecked() && iiml_fa_checked.isChecked()) {
            FINAL_SELECTED_CourseData = IIML_FA_Course + "," + IIML_BA_Course;
        }
        if (iiml_checked.isChecked()) {
            FINAL_SELECTED_CourseData = IIML_BA_Course;
        }
        if (iiml_fa_checked.isChecked()) {
            FINAL_SELECTED_CourseData = IIML_FA_Course;
        }
        if(iiml_pa_checked.isChecked()){
            FINAL_SELECTED_CourseData = IIML_PA_Course;
        }
        if(iiml_hr_checked.isChecked()){
            FINAL_SELECTED_CourseData = IIML_HR_Course;
        }

        if(iitr_bf_checked.isChecked()){
            FINAL_SELECTED_CourseData = IITR_BF_Course ;

        }
        if(iitr_dbe_checked.isChecked()){
            FINAL_SELECTED_CourseData = IITR_DBE_Checked;
        }
        if(iiml_fa_checked.isChecked() && iitr_dbe_checked.isChecked()){
            FINAL_SELECTED_CourseData =IIML_FA_Course + ","+ IITR_DBE_Checked;
        }
        if(iiml_checked.isChecked() && iitr_dbe_checked.isChecked()){
            FINAL_SELECTED_CourseData =IIML_BA_Course + ","+ IITR_DBE_Checked;
        }
        if(iitr_bf_checked.isChecked() && iitr_dbe_checked.isChecked()){
            FINAL_SELECTED_CourseData =IITR_BF_Course + ","+ IITR_DBE_Checked;
        }
        if (iiml_pa_checked.isChecked() && iitr_bf_checked.isChecked()) {
            FINAL_SELECTED_CourseData = IIML_PA_Course + "," + IITR_BF_Course;
        }
        if (iiml_fa_checked.isChecked() && iitr_bf_checked.isChecked()) {
            FINAL_SELECTED_CourseData = IIML_FA_Course + "," + IITR_BF_Course;
        }

        if(iiml_fa_checked.isChecked() && iiml_pa_checked.isChecked()){
            FINAL_SELECTED_CourseData =IIML_FA_Course + ","+ IIML_PA_Course;
        }
        if(iiml_checked.isChecked() && iiml_pa_checked.isChecked()){
            FINAL_SELECTED_CourseData =IIML_BA_Course + ","+ IIML_PA_Course;
        }
        if(iiml_checked.isChecked() && iiml_hr_checked.isChecked()){
            FINAL_SELECTED_CourseData =IIML_BA_Course + ","+ IIML_HR_Course;
        }
        if(iiml_fa_checked.isChecked() && iiml_hr_checked.isChecked()){
            FINAL_SELECTED_CourseData =IIML_FA_Course + ","+ IIML_HR_Course;
        }
        if(iiml_pa_checked.isChecked() && iiml_hr_checked.isChecked()){
            FINAL_SELECTED_CourseData =IIML_PA_Course + ","+ IIML_HR_Course;
        }

        return true;
    }

    private void selectDatapicker(Dialog dialog) {
        date_picker_ = dialog.findViewById(R.id.date_picker_);
        date_picker_.setOnClickListener(this);

    }

    private void CustomDataPOints() {
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

        levelsArrayList = new ArrayList<>();
        levelsArrayList.add(" ");
        levelsArrayList.add("Cold");
        levelsArrayList.add("Very Hot");
        levelsArrayList.add("Hot");
        levelsArrayList.add("Warm");
        levelsArrayList.add("Application Received");
        levelsArrayList.add("Exam-Pass");
        levelsArrayList.add("Exam-Fail");
        levelsArrayList.add("Exam-Pass-Dropout");
        levelsArrayList.add("Exam-Fail-Dropout");
        levelsArrayList.add("Exam-Noshow-Dropout");
        levelsArrayList.add("IIML-FA-M7");
        levelsArrayList.add("IIML-BA-M7");
        levelsArrayList.add("IIML-PM-M7");
        levelsArrayList.add("IIML-HR-M7");
        levelsArrayList.add("IITR-BF-M7");
        levelsArrayList.add("CM-Yet-To-Connect");


    }

    private void openAlertForDatepicker() {
         datePickerDialog = new DatePickerDialog(getContext(), date, myCalendar
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
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
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

}
