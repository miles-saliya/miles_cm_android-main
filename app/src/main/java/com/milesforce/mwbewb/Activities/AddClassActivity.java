package com.milesforce.mwbewb.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.milesforce.mwbewb.Utils.ConstantUtills.AccessToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.SaveToken;

public class AddClassActivity extends AppCompatActivity implements View.OnClickListener {

    AppCompatImageButton appCompatImageButton;
    EditText add_class_datePicker;
    String class_date = "";
    Calendar myCalendar;
    RadioGroup course_radio_group;
    TextView class_spinner_text;
    ArrayList<String> venues_arrayList;
    ArrayList<String> cpa_courses;
    ArrayList<String> cma_courses;
    ArrayList<String> trainer_name;
    AppCompatSpinner venues_spinner,course_spinner,trainer_spinner;
    ArrayAdapter<String>venueAdapter;
    ArrayAdapter<String>courseAdapter;
    ArrayAdapter<String>trainerAdapter;
    String COURSE_TYPE="";
    String VENUE_SELECTED="";
    String COURSE_SELECTED="";
    String TRAINER_SELECTED="";
    ProgressBar add_class_progress;
    ApiClient apiClient = ApiUtills.getAPIService();
    String accessToken;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        AddStaticData();
        sharedPreferences = getApplicationContext().getSharedPreferences(SaveToken, MODE_PRIVATE);
        accessToken = sharedPreferences.getString(AccessToken, null);
        appCompatImageButton = findViewById(R.id.bt_menu_back_tomain);
        appCompatImageButton.setOnClickListener(this);
        add_class_datePicker = findViewById(R.id.add_class_datePicker);
        add_class_datePicker.setOnClickListener(this);
        myCalendar = Calendar.getInstance();
        class_spinner_text = findViewById(R.id.class_spinner_text);
        venues_spinner=findViewById(R.id.venues_spinner);
        course_spinner=findViewById(R.id.course_spinner);
        trainer_spinner=findViewById(R.id.trainer_spinner);
        course_radio_group = findViewById(R.id.course_radio_group);
        add_class_progress=findViewById(R.id.add_class_progress);
        course_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.cpa_course) {
                    COURSE_TYPE="CPA";
                    courseAdapter=new ArrayAdapter<String>(AddClassActivity.this,android.R.layout.simple_spinner_item,cpa_courses);
                    courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    course_spinner.setAdapter(courseAdapter);
                } else if (checkedId == R.id.cma_course) {
                    COURSE_TYPE="CMA";
                    courseAdapter=new ArrayAdapter<String>(AddClassActivity.this,android.R.layout.simple_spinner_item,cma_courses);
                    courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    course_spinner.setAdapter(courseAdapter);
                }
            }
        });
        venueAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,venues_arrayList);
        venueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        venues_spinner.setAdapter(venueAdapter);

        trainerAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,trainer_name);
        trainerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trainer_spinner.setAdapter(trainerAdapter);
        venues_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                VENUE_SELECTED = venues_arrayList.get(position);
                Toast.makeText(AddClassActivity.this, ""+VENUE_SELECTED, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        course_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (COURSE_TYPE.equals("CPA")){
                    COURSE_SELECTED=cpa_courses.get(position);
                    Toast.makeText(AddClassActivity.this, ""+COURSE_SELECTED, Toast.LENGTH_SHORT).show();
                }else if(COURSE_TYPE.equals("CMA")){
                    COURSE_SELECTED=cma_courses.get(position);
                    Toast.makeText(AddClassActivity.this, ""+COURSE_SELECTED, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        trainer_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TRAINER_SELECTED=trainer_name.get(position);
                Toast.makeText(AddClassActivity.this, ""+TRAINER_SELECTED, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void AddStaticData() {
        venues_arrayList = new ArrayList<>();
        venues_arrayList.add("A-Block");
        venues_arrayList.add("B-Block");
        venues_arrayList.add("C-Block");
        venues_arrayList.add("D-Block");

        cpa_courses = new ArrayList<>();
        cpa_courses.add("AUD");
        cpa_courses.add("FAR");
        cpa_courses.add("BEC");
        cpa_courses.add("REG");

        cma_courses = new ArrayList<>();
        cma_courses.add("Exam Target-1");
        cma_courses.add("Exam Target-2");
        cma_courses.add("AUD");
        cma_courses.add("FAR");
        cma_courses.add("BEC");
        cma_courses.add("REG");

        trainer_name = new ArrayList<>();
        trainer_name.add("Durga Prasad");
        trainer_name.add("Brahmaiah Raju");
        trainer_name.add("Arun");
        trainer_name.add("Narasimham");
        trainer_name.add("Suchitra Patnayak");
        trainer_name.add("Birendra Maharana");
        trainer_name.add("Pavan Kumar");
        trainer_name.add("Amol Darak");










    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_menu_back_tomain:
                finish();
                break;
            case R.id.add_class_datePicker:
                openAlertForDatepicker();
                break;


        }
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
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        add_class_datePicker.setText(sdf.format(myCalendar.getTime()));
        try {
            Date date = sdf.parse(sdf.format(myCalendar.getTime()));
            long timestamp = date.getTime() / 1000L;
            class_date = String.valueOf(timestamp);
            Toast.makeText(this, "" + class_date, Toast.LENGTH_SHORT).show();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void AddClass(View view) {
        if (COURSE_TYPE.equals("")) {
            Toast.makeText(this, "Please select Course", Toast.LENGTH_SHORT).show();
            return;
        }if(class_date.equals("")) {
                Toast.makeText(this, "Please select class date", Toast.LENGTH_SHORT).show();
                return;
        }if (VENUE_SELECTED.equals("")) {
            Toast.makeText(this, "Please select Venue", Toast.LENGTH_SHORT).show();
            return;
        }if (COURSE_SELECTED.equals("")) {
            Toast.makeText(this, "Please select Course", Toast.LENGTH_SHORT).show();
            return;
        }if (TRAINER_SELECTED.equals("")) {
            Toast.makeText(this, "Please select Trainer", Toast.LENGTH_SHORT).show();
            return;
        }
        SendDataT0server(accessToken,class_date,VENUE_SELECTED,COURSE_TYPE,COURSE_SELECTED,TRAINER_SELECTED);







    }

    private void SendDataT0server(String accessToken, String class_date, String venue_selected, String course_type, String course_selected, String trainer_selected) {
        add_class_progress.setVisibility(View.VISIBLE);
        apiClient.addClass(class_date,venue_selected,course_type,course_selected,trainer_selected,"Bearer "+accessToken,"application/json").enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                try {
                    if (response.body().getStatus().equals("success")){
                        add_class_progress.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    add_class_progress.setVisibility(View.GONE);
                    Toast.makeText(AddClassActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                add_class_progress.setVisibility(View.GONE);
                Toast.makeText(AddClassActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
