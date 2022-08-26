package com.milesforce.mwbewb.TabFragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.milesforce.mwbewb.Model.AttendanceData;
import com.milesforce.mwbewb.Model.BooksData;
import com.milesforce.mwbewb.Model.CmaData;
import com.milesforce.mwbewb.Model.CpaData;
import com.milesforce.mwbewb.Model.EWBData;
import com.milesforce.mwbewb.Model.EwbDataModel;
import com.milesforce.mwbewb.Model.FeesData;
import com.milesforce.mwbewb.Model.SrModelInfo;
import com.milesforce.mwbewb.Model.StudentReferenceData;
import com.milesforce.mwbewb.Model.SuggestModel;
import com.milesforce.mwbewb.Model.UserToken;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;
import com.milesforce.mwbewb.Retrofit.SuccessModel;
import com.milesforce.mwbewb.Utils.AutoSuggestAdapter;
import com.milesforce.mwbewb.Utils.SRDataModel;
import com.milesforce.mwbewb.Utils.StudentRefferalAdapter;

import org.w3c.dom.Text;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class SrFragment extends Fragment implements View.OnClickListener {
    ArrayList<String> cityArrayList;
    ArrayList<String> eligibility;
    ArrayList<String> educationTags;
    Realm realm;
    ApiClient apiClient;
    UserToken accessToken;
    ArrayList<EWBData> ewbDataModelArrayList;
    TextView evaluation_id, nts_id, aud_one, far_one, bec_one, reg_one, aud_quater, far_quater, bec_quater, reg_quater, target_one, target_two, person_Info_details;
    EWBData ewb_data_model;
    View view_;
    String AccessToken;
    int person_id, can_id, mwb_id, applied_for_loan;
    String Courses, loan_status, education_tags, source, level, identity, batch;
    String personName, education, company, designation, experiance, documents_submitted;
    String URL_FOR_SR = "";
    RecyclerView student_referal_recycler;
    StudentRefferalAdapter studentRefferalAdapter;
    ImageView add_poa;
    static WindowManager.LayoutParams lp;
    AppCompatSpinner evaluation_spinner, nts_spinner, aud_spinner, far_spinner, bec_spinner, reg_spinner,target_two_spinner,target_one_spinner;
    ArrayList<String> evaluation_arrayList;
    ArrayList<String> nts_arrayList;
    ArrayList<String> aud_arrayList;
    ArrayList<String> far_arrayList;
    ArrayList<String> bec_arrayList;
    ArrayList<String> reg_arrayList;
    ArrayAdapter<String> evaluation_adapter;
    ArrayAdapter<String> nts_adapter;
    ArrayAdapter<String> aud_adapter;
    ArrayAdapter<String> far_adapter;
    ArrayAdapter<String> bec_adapter;
    ArrayAdapter<String> reg_adapter;

    ArrayAdapter<String> target_one_adapter;
    ArrayAdapter<String> target_two_adapter;


    String EVALUATION = "";
    String NTS = "";
    String AUD = "";
    String FAR = "";
    String BEC = "";
    String REG = "";
    String TARGET_ONE="";
    String TARGET_TWO="";

    RelativeLayout update_plan_off_action;
    ProgressBar sr_progress;
    ImageView add_student_reference;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    EditText reference_name_edit;
    AppCompatAutoCompleteTextView appCompatAutoCompleteTextView;
    AutoSuggestAdapter autoSuggestAdapter;
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private Handler handler;
    SuggestModel SUGGEST_MODEL = null;
    private static long nextCallTimeStamp = 0;
    AppCompatEditText appCompatEditText;
    ProgressBar sr_referral_progress;
    LinearLayout cpa_layout, cma_layout;


    public SrFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        realm = Realm.getDefaultInstance();
        person_id = getArguments().getInt("person_id");
        personName = getArguments().getString("person_name");
        Courses = getArguments().getString("courses");
        can_id = getArguments().getInt("can_id");
        mwb_id = getArguments().getInt("id");
        identity = getArguments().getString("identity");
        batch = getArguments().getString("batch");
        initDataForSpinners();
        return inflater.inflate(R.layout.fragment_sr, container, false);

    }

    private void initDataForSpinners() {
        evaluation_arrayList = new ArrayList<>();
        evaluation_arrayList.add("NA");
        evaluation_arrayList.add("Not yet initiated");
        evaluation_arrayList.add("Done");
        evaluation_arrayList.add("WIP");
        evaluation_arrayList.add("Yet to initiate");
        evaluation_arrayList.add("Awaiting PGDPA");

        aud_arrayList = new ArrayList<>();
        aud_arrayList.add("NA");
        aud_arrayList.add("Drop-Out");
        aud_arrayList.add("Cleared");
        aud_arrayList.add("Undecided");
        aud_arrayList.add("Q1-2019");
        aud_arrayList.add("Q2-2019");
        aud_arrayList.add("Q3-2019");
        aud_arrayList.add("Q4-2019");

        aud_arrayList.add("Q1-2020");
        aud_arrayList.add("Q2-2020");
        aud_arrayList.add("Q3-2020");
        aud_arrayList.add("Q4-2020");

        aud_arrayList.add("Q1-2021");
        aud_arrayList.add("Q2-2021");
        aud_arrayList.add("Q3-2021");
        aud_arrayList.add("Q4-2021");

        aud_arrayList.add("Q1-2022");
        aud_arrayList.add("Q2-2022");
        aud_arrayList.add("Q3-2022");
        aud_arrayList.add("Q4-2022");

        aud_arrayList.add("Q1-2023");
        aud_arrayList.add("Q2-2023");
        aud_arrayList.add("Q3-2023");
        aud_arrayList.add("Q4-2023");


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // addStaticDataToviews();
        evaluation_id = view.findViewById(R.id.evaluation_id);
        nts_id = view.findViewById(R.id.nts_id);
        aud_one = view.findViewById(R.id.aud_one);
        far_one = view.findViewById(R.id.far_one);
        bec_one = view.findViewById(R.id.bec_one);
        reg_one = view.findViewById(R.id.reg_one);
        aud_quater = view.findViewById(R.id.aud_quater);
        far_quater = view.findViewById(R.id.far_quater);
        bec_quater = view.findViewById(R.id.bec_quater);
        reg_quater = view.findViewById(R.id.reg_quater);
        add_poa = view.findViewById(R.id.add_poa);
        add_poa.setOnClickListener(this);
        cpa_layout = view.findViewById(R.id.cpa_layout);
        cma_layout = view.findViewById(R.id.cma_layout);
        target_one = view.findViewById(R.id.target_one);
        target_two = view.findViewById(R.id.target_two);
        student_referal_recycler = view.findViewById(R.id.student_referal_recycler);
        person_Info_details = view.findViewById(R.id.person_Info_details);
        add_student_reference = view.findViewById(R.id.add_student_reference);
        add_student_reference.setOnClickListener(this);

        view_ = view;
        apiClient = ApiUtills.getAPIService();
        if (realm.where(UserToken.class).findFirst() != null) {
            accessToken = realm.where(UserToken.class).findFirst();
        }
//        if (Courses.equals("CPA")) {
//            URL_FOR_SR = "http://milescmback.2x2.ninja/api/getCpaFromMwbId?mwb_id=" + mwb_id + "";
//            cma_layout.setVisibility(View.GONE);
//            cpa_layout.setVisibility(View.VISIBLE);
//        }
//        if (Courses.equals("CMA")) {
//            URL_FOR_SR = "http://milescmback.2x2.ninja/api/getCmaFromMwbId?mwb_id=" + mwb_id + "";
//            cma_layout.setVisibility(View.VISIBLE);
//            cpa_layout.setVisibility(View.GONE);
//        } else {
//            URL_FOR_SR = "http://milescmback.2x2.ninja/api/getCpaFromMwbId?mwb_id=" + mwb_id + "";
//            cma_layout.setVisibility(View.GONE);
//            cpa_layout.setVisibility(View.VISIBLE);
//        }
        URL_FOR_SR="getCandidateDetails?mwb_id="+mwb_id+"";
        getSrData(URL_FOR_SR);

    }

    private void getSrData(String URL_FOR_SR) {
        apiClient.getEwbDataWithSpocId(URL_FOR_SR, "Bearer " + accessToken.getAccessToken(), "application/json").enqueue(new Callback<SRDataModel>() {
            @Override
            public void onResponse(Call<SRDataModel> call, Response<SRDataModel> response) {
                try {
                    if(response.body().getStatus().equals("success")){

                      if(response.body().getData().getCpa() != null && response.body().getData().getCma() != null ){
                          Toast.makeText(getContext(), "Both Not Null", Toast.LENGTH_SHORT).show();
                      }
                      if(response.body().getData().getCpa() != null ){
                            Toast.makeText(getContext(), "Cpa Not Null", Toast.LENGTH_SHORT).show();
                      }
                        if(response.body().getData().getCma() != null ){
                            Toast.makeText(getContext(), "Cma Not Null", Toast.LENGTH_SHORT).show();
                        }





                    }





                   /* if (response.body().size()>0){
                        ewbDataModelArrayList = new ArrayList<>();
                        ewb_data_model = new EWBData();
                        ArrayList<EWBData> ewbData = response.body();
                        if (Courses.equals("CPA")){
                            ewb_data_model.setCpa(ewbData.get(0).getCpa());
                        }
                        if (Courses.equals("CMA")){
                            ewb_data_model.setCma(ewbData.get(0).getCma());
                        }

                        //ewb_data_model.setAttendance(ewbData.get(0).getAttendance());
//                    ewb_data_model.setBooks(ewbData.get(0).getBooks());
//                    ewb_data_model.setFees(ewbData.get(0).getFees());

                        ewb_data_model.setStudent_reference(ewbData.get(0).getStudent_reference());
                    }else {
                        Toast.makeText(getContext(), "No Data Found...", Toast.LENGTH_SHORT).show();
                    }*/

                } catch (Exception e) {
                 e.getMessage();
                }

                //initViews(view_);
            }

            @Override
            public void onFailure(Call<SRDataModel> call, Throwable t) {
                t.getMessage();
                initViews(view_);
                person_Info_details.setText(personName + "-" + " -"+Courses);
            }
        });

    }

    private void initViews(View view) {

        try {
            if (Courses.equals("CPA")) {
                evaluation_id.setText(ewb_data_model.getCpa().getEvaluation());
                nts_id.setText(ewb_data_model.getCpa().getNts());
               /* aud_one.setText(ewb_data_model.getAttendance().getAud() + " " + "%");
                far_one.setText(ewb_data_model.getAttendance().getFar() + " " + "%");
                bec_one.setText(ewb_data_model.getAttendance().getBec() + " " + "%");
                reg_one.setText(ewb_data_model.getAttendance().getReg() + " " + "%");*/
                aud_quater.setText(ewb_data_model.getCpa().getAud());
                far_quater.setText(ewb_data_model.getCpa().getFar());
                bec_quater.setText(ewb_data_model.getCpa().getBec());
                reg_quater.setText(ewb_data_model.getCpa().getReg());
                String User_info = ewb_data_model.getCpa().getPerson_name() + " " + ewb_data_model.getCpa().getBatch() + " " + ewb_data_model.getCpa().getEnrollment_date()+" "+Courses;
                person_Info_details.setText(User_info);

            } else {
                target_one.setText(ewb_data_model.getCma().getPart_1_exam_target());
                target_two.setText(ewb_data_model.getCma().getPart_2_exam_target());
                String User_info = ewb_data_model.getCma().getPerson_name() + " " + ewb_data_model.getCma().getBatch() + " " + ewb_data_model.getCma().getEnrollment_date()+" "+Courses;
                person_Info_details.setText(User_info);

            }
            if (ewb_data_model.getStudent_reference().size() > 0) {
                studentRefferalAdapter = new StudentRefferalAdapter(getContext(), ewb_data_model.getStudent_reference());
                student_referal_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
                student_referal_recycler.setAdapter(studentRefferalAdapter);
                studentRefferalAdapter.notifyDataSetChanged();
            }

        } catch (Exception e) {
            person_Info_details.setText(personName + "-" + " -"+Courses);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_poa:
                if (Courses.equals("CPA")) {
                    openPoaAlert();
                }
                if (Courses.equals("CMA")) {
                    openAlertForCMA();
                }if (Courses.equals("")){
                Toast.makeText(getContext(), "No Courses found.", Toast.LENGTH_SHORT).show();
            }
                break;
            case R.id.add_student_reference:
                openAlertForStudentReferel();
                break;
        }

    }

    private void openAlertForCMA() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.add_poa_cma);
        dialog.setCancelable(true);
        lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setAttributes(lp);
        ImageButton imageButton = dialog.findViewById(R.id.bt_close);
        /*evaluation_spinner,nts_spinner,aud_spinner,far_spinner,bec_spinner,reg_spinner;*/
        evaluation_spinner = dialog.findViewById(R.id.evaluation_spinner);
        nts_spinner = dialog.findViewById(R.id.nts_spinner);

        target_one_spinner = dialog.findViewById(R.id.target_one_spinner);
        target_two_spinner = dialog.findViewById(R.id.target_two_spinner);


        target_one_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, aud_arrayList);
        target_one_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        target_one_spinner.setAdapter(target_one_adapter);

        target_two_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, aud_arrayList);
        target_two_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        target_two_spinner.setAdapter(target_two_adapter);

        target_one_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TARGET_ONE = target_one_spinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        target_two_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TARGET_TWO = target_two_spinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        update_plan_off_action = dialog.findViewById(R.id.update_plan_off_action);
        sr_progress = dialog.findViewById(R.id.sr_progress);

        imageButton.setOnClickListener(this);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        update_plan_off_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TARGET_ONE.equals("") || TARGET_TWO.equals("")) {
                    Toast.makeText(getContext(), "Please select all the fields.", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        submitEditPlanOfActionForCMA(ewb_data_model.getCma().getId(),TARGET_ONE, TARGET_TWO, dialog);
                        sr_progress.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "No Data Found to Update", Toast.LENGTH_SHORT).show();
                        sr_progress.setVisibility(View.GONE);
                    }

                }

            }
        });
        if (Courses.equals("CMA")){
            try {
                int target1 = target_one_adapter.getPosition(ewb_data_model.getCma().getPart_1_exam_target());
                target_one_spinner.setSelection(target1);

                int target2 = target_two_adapter.getPosition(ewb_data_model.getCma().getPart_2_exam_target());
                target_two_spinner.setSelection(target2);



            }catch (Exception e){

            }
        }

    }

    private void submitEditPlanOfActionForCMA(int id,String target_one, String target_two,final Dialog dialog) {

            apiClient.updateCMAPlanOffAction(id, target_one, target_two, "Bearer " + accessToken.getAccessToken(), "application/json").enqueue(new Callback<SuccessModel>() {
                @Override
                public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                    try {
                        if (response.body().getStatus().equals("success")) {
                            getSrData(URL_FOR_SR);
                            dialog.dismiss();
                            sr_progress.setVisibility(View.VISIBLE);
                            Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Some thing went wrong.Please try after some time.", Toast.LENGTH_SHORT).show();
                            sr_progress.setVisibility(View.VISIBLE);
                        }

                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        sr_progress.setVisibility(View.VISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<SuccessModel> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });


    }

    private void openAlertForStudentReferel() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.add_referal_form);
        dialog.setCancelable(true);
        lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setAttributes(lp);
        myCalendar = Calendar.getInstance();
        ImageButton imageButton = dialog.findViewById(R.id.bt_close_reference);
        imageButton.setOnClickListener(this);
        sr_referral_progress = dialog.findViewById(R.id.sr_referral_progress);
        appCompatEditText = dialog.findViewById(R.id.bank_details_edit);
        RelativeLayout relativeLayout = dialog.findViewById(R.id.update_referal_action);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        reference_name_edit = dialog.findViewById(R.id.reference_name_edit);
        appCompatAutoCompleteTextView = dialog.findViewById(R.id.appCompatAutoCompleteTextView);
        autoSuggestAdapter = new AutoSuggestAdapter(getContext(),
                R.layout.referral_layout);
        appCompatAutoCompleteTextView.setThreshold(2);
        appCompatAutoCompleteTextView.setAdapter(autoSuggestAdapter);

        appCompatAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SUGGEST_MODEL = autoSuggestAdapter.getObject(position);
                appCompatAutoCompleteTextView.setText(SUGGEST_MODEL.getIdentity() + " - " + SUGGEST_MODEL.getPerson_name() + " - " + SUGGEST_MODEL.getPerson_name());
            }
        });

        appCompatAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(appCompatAutoCompleteTextView.getText())) {
                        makeApiCall(appCompatAutoCompleteTextView.getText().toString());
                    }
                }
                return false;
            }
        });


        reference_name_edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                reference_name_edit.setText(sdf.format(myCalendar.getTime()));
                try {
                    Date date = sdf.parse(sdf.format(myCalendar.getTime()));
                    long timestamp = date.getTime() / 1000L;
                    nextCallTimeStamp = timestamp;

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        };
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (appCompatEditText.getText().toString().isEmpty() || nextCallTimeStamp == 0 || SUGGEST_MODEL == null) {
                    Toast.makeText(getContext(), "Please Select Fields..", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        submitReferalForm(dialog);
                        sr_referral_progress.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "No Data available to update student reference", Toast.LENGTH_SHORT).show();
                        sr_referral_progress.setVisibility(View.GONE);
                    }

                }

            }
        });

    }

    private void submitReferalForm(final Dialog dialog) {
        apiClient.AddReferrelPersonTocandidate(person_id, mwb_id, appCompatEditText.getText().toString(), String.valueOf(nextCallTimeStamp), SUGGEST_MODEL.getPerson_id(), SUGGEST_MODEL.getPerson_name(), SUGGEST_MODEL.getId(), "Bearer " + accessToken.getAccessToken(), "application/json").enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                try {
                    if (response.body().getStatus().equals("success")) {
                        dialog.dismiss();
                        Toast.makeText(getContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        getSrData(URL_FOR_SR);
                        dialog.dismiss();
                        sr_referral_progress.setVisibility(View.INVISIBLE);

                    }

                } catch (Exception e) {
                    Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    sr_referral_progress.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                sr_referral_progress.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void makeApiCall(String personName) {
        apiClient.getSuggestNamesForMWB(personName, "Bearer " + accessToken.getAccessToken(), "application/json").enqueue(new Callback<ArrayList<SuggestModel>>() {
            @Override
            public void onResponse(Call<ArrayList<SuggestModel>> call, Response<ArrayList<SuggestModel>> response) {
                try {
                    ArrayList<SuggestModel> suggestModelArrayList = response.body();
                    autoSuggestAdapter.setData(suggestModelArrayList);
                    autoSuggestAdapter.notifyDataSetChanged();

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<ArrayList<SuggestModel>> call, Throwable t) {

            }
        });


    }

    private void openPoaAlert() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.add_poa);
        dialog.setCancelable(true);
        lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setAttributes(lp);
        ImageButton imageButton = dialog.findViewById(R.id.bt_close);
        /*evaluation_spinner,nts_spinner,aud_spinner,far_spinner,bec_spinner,reg_spinner;*/
        evaluation_spinner = dialog.findViewById(R.id.evaluation_spinner);
        nts_spinner = dialog.findViewById(R.id.nts_spinner);

        aud_spinner = dialog.findViewById(R.id.aud_spinner);
        far_spinner = dialog.findViewById(R.id.far_spinner);
        bec_spinner = dialog.findViewById(R.id.bec_spinner);
        reg_spinner = dialog.findViewById(R.id.reg_spinner);
        update_plan_off_action = dialog.findViewById(R.id.update_plan_off_action);
        sr_progress = dialog.findViewById(R.id.sr_progress);

        imageButton.setOnClickListener(this);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        evaluation_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, evaluation_arrayList);
        evaluation_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        evaluation_spinner.setAdapter(evaluation_adapter);

        nts_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, evaluation_arrayList);
        nts_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nts_spinner.setAdapter(nts_adapter);

        aud_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, aud_arrayList);
        aud_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        aud_spinner.setAdapter(aud_adapter);

        far_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, aud_arrayList);
        far_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        far_spinner.setAdapter(far_adapter);

        bec_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, aud_arrayList);
        bec_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bec_spinner.setAdapter(bec_adapter);

        reg_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, aud_arrayList);
        reg_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reg_spinner.setAdapter(reg_adapter);

        evaluation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EVALUATION = evaluation_spinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        nts_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NTS = nts_spinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        aud_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AUD = aud_spinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        far_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FAR = far_spinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        bec_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BEC = bec_spinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        reg_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                REG = reg_spinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        update_plan_off_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EVALUATION.equals("") || NTS.equals("") || AUD.equals("") || FAR.equals("") || BEC.equals("") || REG.equals("")) {
                    Toast.makeText(getContext(), "Please select all the fields.", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        submitEditPlanOfAction(EVALUATION, NTS, AUD, FAR, BEC, REG, dialog);
                        sr_progress.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "No Data Found to Update", Toast.LENGTH_SHORT).show();
                        sr_progress.setVisibility(View.GONE);
                    }

                }
            }
        });
        if (Courses.equals("CPA")){
            try {
                int evaluate = evaluation_adapter.getPosition(ewb_data_model.getCpa().getEvaluation());
                evaluation_spinner.setSelection(evaluate);
                int nts = nts_adapter.getPosition(ewb_data_model.getCpa().getNts());
                nts_spinner.setSelection(nts);

                int aud = aud_adapter.getPosition(ewb_data_model.getCpa().getAud());
                aud_spinner.setSelection(aud);

                int far = aud_adapter.getPosition(ewb_data_model.getCpa().getFar());
                far_spinner.setSelection(far);

                int bec = bec_adapter.getPosition(ewb_data_model.getCpa().getBec());
                bec_spinner.setSelection(bec);

                int reg = reg_adapter.getPosition(ewb_data_model.getCpa().getBec());
                reg_spinner.setSelection(reg);

            }catch (Exception e){

            }
        }













    }

    private void submitEditPlanOfAction(String evaluation, String nts, String aud, String far, String bec, String reg, final Dialog dialog) {
        apiClient.updatePlanOffAction(ewb_data_model.getCpa().getId(), evaluation, nts, aud, bec, far, reg, "Bearer " + accessToken.getAccessToken(), "application/json").enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                try {
                    if (response.body().getStatus().equals("success")) {
                        getSrData(URL_FOR_SR);
                        dialog.dismiss();
                        sr_progress.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Some thing went wrong.Please try after some time.", Toast.LENGTH_SHORT).show();
                        sr_progress.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    sr_progress.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
/* private void addStaticDataToviews() {
        cityArrayList = new ArrayList<>();
        cityArrayList.add("HYD");
        cityArrayList.add("BNG");
        cityArrayList.add("BOM");
        eligibility = new ArrayList<>();
        eligibility.add("Clear");
        eligibility.add("Check");
        eligibility.add("Pgdpa");

        educationTags = new ArrayList<>();
        educationTags.add("B.Com");
        educationTags.add("M.Com");
        educationTags.add("MBA");
        educationTags.add("CA");
        educationTags.add("CWA");
        educationTags.add("CS");
        educationTags.add("BMS");
        educationTags.add("BBA");
        educationTags.add("PGD");
        educationTags.add("LLB");
        educationTags.add("B.TECH");
        educationTags.add("BSC");
        educationTags.add("BA");
        educationTags.add("CA-INTER");
        educationTags.add("CWA-INTER");
        educationTags.add("CS-INTER");
        educationTags.add("PG-PURSUING");
        educationTags.add("BBA-PURSUING");
    }*/