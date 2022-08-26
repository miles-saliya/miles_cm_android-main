package com.milesforce.mwbewb.Utils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.milesforce.mwbewb.EngagementFragments.LevelsCustomAdapter;
import com.milesforce.mwbewb.Model.DelaysModel;
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

import static com.milesforce.mwbewb.Utils.ConstantUtills.VERSION_NUMBER;

public class CustomEngagementFormForUntracked {
    Calendar myCalendar;
    private ViewPager view_pager_engagement;
    private TabLayout tab_layoutEngagementform;
    FloatingActionButton call, visit, email, whatsup;
    AppCompatSpinner appconpact_spinner_levels, appconpact_spinner_connectionstatus;
    ArrayList<LevelsModel> spinnerLevelList;
    ArrayList<String> ConnectionTypeArrayList;
    ArrayList<String> templets;
    EditText date_picker_;
    TextView compose_text, preview_text, title_header, textview_header;
    LinearLayout preview_layout, compose_layout;
    EditText subject_edit, compose_edit, content_whatsUp;
    WebView webView;
    AssetManager assetManager;
    String PreviewData;
    RadioGroup responce_radio_group;
    String RadioBtnResponseType = null;
    EditText engagement_description;
    AppCompatCheckBox cpaCheckbox, cmaCheckbox;
    static String CPAChecked = " ";
    static String CPMChecked = " ";
    static String DaChecked = " ";
    static String CoursesData = null;
    String AccessToken;
    int person_id, can_id;
    String previousEngagement, courses, levels, user_name;
    EditText latestEngagement;
    LinearLayout nextTimeLayout, engagement_main_form;
    String LevelsSelected = null;
    private static long nextCallTimeStamp = 0;
    ProgressBar add_engagement_progress;
    ApiClient apiClient = ApiUtills.getAPIService();
    AlertDialog alert;
    EditText engagement_description_visit, date_picker_visit;
    String User_levels = " ";
    Context context;
    String accesToken, phone_number;
    BatteryModel batteryModel;
    LevelsCustomAdapter levelsCustomAdapter;
    static String ConnectionStatus=" ";
    static String SELECTED_STATUS=" ";

    public void addCustomEngagement(DelaysModel delaysModel, final Context context, String AccessToken) {
        CustomDataPOints();
        person_id = delaysModel.getPerson_id();
        can_id = delaysModel.getCan_id();
        previousEngagement = delaysModel.getEngagement_details();
        courses = delaysModel.getCourses();
        levels = delaysModel.getLevel();
        user_name = delaysModel.getPerson_name();
        LevelsSelected = levels;
        this.context = context;
        accesToken = AccessToken;
        phone_number = delaysModel.getPhone_number();
        batteryModel = new BatterPercentage().getBattertPercentage(context);
        if (levels.equals("M7") || levels.equals("M6") || levels.equals("M5") || levels.equals("M4") || levels.equals("M4-") || levels.equals("L6") || levels.equals("L5") || levels.equals("L4") || levels.equals("L4-")) {
            if (spinnerLevelList != null) {
                spinnerLevelList.clear();
            }
            spinnerLevelList = new ArrayList<>();
            spinnerLevelList.add(new LevelsModel("M7", "M7- Enrolled"));
            spinnerLevelList.add(new LevelsModel("M6", "M6 - Visited & Ready to Enroll"));
            spinnerLevelList.add(new LevelsModel("M5", "M5 - Visited & Positive"));
            spinnerLevelList.add(new LevelsModel("M4", "M4 - Visited but not interested"));
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
        }

        myCalendar = Calendar.getInstance();
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_event);
        dialog.setCancelable(true);
        nextTimeLayout = dialog.findViewById(R.id.nextTimeLayout);
        engagement_main_form = dialog.findViewById(R.id.engagement_main_form);
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
        date_picker_ = dialog.findViewById(R.id.date_picker_);

        cpaCheckbox = dialog.findViewById(R.id.cpa_checked);
        cmaCheckbox = dialog.findViewById(R.id.cma_checked);

        date_picker_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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



        responce_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.same_response) {
                    Toast.makeText(context, "same Response", Toast.LENGTH_SHORT).show();
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
                    apiClient.AddEngagement(ConnectionStatus,phone_number, can_id, person_id, user_name, LevelsSelected, CoursesData, engagement_description.getText().toString().trim(), "call", 0, nextCallTimeStamp, " ", 0, "No",batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), VERSION_NUMBER, "Bearer " + accesToken, "application/json").enqueue(new Callback<SuccessModel>() {
                        @Override
                        public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                            try {
                                if (response.body().getStatus().equals("success")) {
                                    dialog.dismiss();
                                    add_engagement_progress.setVisibility(View.GONE);
                                    openAlert(response.body().getMessage());
                                } else {
                                    openAlert(response.body().getMessage());
                                }

                            } catch (Exception e) {
                                dialog.dismiss();
                                add_engagement_progress.setVisibility(View.GONE);
                                openAlert(response.body().getMessage());
                            }


                        }

                        @Override
                        public void onFailure(Call<SuccessModel> call, Throwable t) {
                            t.printStackTrace();
                            add_engagement_progress.setVisibility(View.GONE);
                            Toast.makeText(context, "Some Thing went wrong please try after some time ", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                }
            }
        });

        /*// Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, spinnerLevelList);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        appconpact_spinner_levels.setAdapter(dataAdapter);

        appconpact_spinner_levels.setSelection(spinnerLevelList.indexOf(levels));*/
        levelsCustomAdapter = new LevelsCustomAdapter(context, R.layout.listitems_layout, R.id.levels_items, spinnerLevelList);
        appconpact_spinner_levels.setAdapter(levelsCustomAdapter);
        appconpact_spinner_levels.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LevelsModel levelsModel = spinnerLevelList.get(position);
                LevelsSelected = levelsModel.getLevelCode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpinnerSelection(appconpact_spinner_levels, levels);


        ArrayAdapter<String> connection_statusAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, ConnectionTypeArrayList);

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
                        ConnectionStatus="CD";
                    } else {
                        engagement_main_form.setVisibility(View.GONE);
                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        String formattedDate = df.format(c);

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
                        if (SELECTED_STATUS.equals("Connected / Busy")) {
                            ConnectionStatus="CB";
                        }
                        if (SELECTED_STATUS.equals("Connected / Never call back")) {
                            ConnectionStatus="CN";
                        }
                        if (SELECTED_STATUS.equals("Connected / Wrong number")) {
                            ConnectionStatus="CW";
                        }
                        if (SELECTED_STATUS.equals("Busy")) {
                            ConnectionStatus="B";
                        }if (SELECTED_STATUS.equals("Not Lifting")) {
                            ConnectionStatus="NL";
                        }
                        if (SELECTED_STATUS.equals("Not Reachable")) {
                            ConnectionStatus="NR";
                        }
                        if (SELECTED_STATUS.equals("Disconnected")) {
                            ConnectionStatus="D";
                        }
                        if (SELECTED_STATUS.equals("Invalid Number")) {
                            ConnectionStatus="IN";
                        }
                        if (SELECTED_STATUS.equals("Switched Off")) {
                            ConnectionStatus="SO";
                        }

                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dialog.setCanceledOnTouchOutside(false);

        dialog.show();
        dialog.getWindow().setAttributes(lp);

        if (courses.equals("CPA,CMA")) {
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


    }

    private void CustomDataPOints() {
      /*  spinnerLevelList = new ArrayList<>();
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
        spinnerLevelList.add("L7");*/

        ConnectionTypeArrayList = new ArrayList<>();
        ConnectionTypeArrayList.add("Connected / Discussed");
        ConnectionTypeArrayList.add("Connected / Busy");
        ConnectionTypeArrayList.add("Connected / Never call back");
        ConnectionTypeArrayList.add("Connected / Wrong number");
        ConnectionTypeArrayList.add("Disconnected");
        ConnectionTypeArrayList.add("Busy");
        ConnectionTypeArrayList.add("Switched Off");
        ConnectionTypeArrayList.add("Not Lifting");
        ConnectionTypeArrayList.add("Not Reachable");
    }

    private boolean isvalidateForm() {
        if (cpaCheckbox.isChecked() && cmaCheckbox.isChecked()) {
            CoursesData = CPAChecked + "," + CPMChecked ;
        }else if (cpaCheckbox.isChecked()) {
            CoursesData = CPAChecked;
        } else if (cmaCheckbox.isChecked()) {
            CoursesData = CPMChecked;
        }
        if (CoursesData == null) {
            Toast.makeText(context, "Please Select Course Type ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (engagement_description.getText().toString().isEmpty()) {
            engagement_description.setError("Please fill Engagement Type");
            return false;
        }
        if (date_picker_.getText().toString().isEmpty()) {
            date_picker_.setError("Please select date");
            return false;
        }
        return true;
    }

    public void openAlert(String p) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
        String myFormat = "dd/MM/yyyy"; //In which you need put here
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
}


