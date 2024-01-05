package com.milesforce.mwbewb.EngagementFragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.telecom.TelecomManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.milesforce.mwbewb.Model.AddMobileEmailInfo;
import com.milesforce.mwbewb.Model.EmailModel;
import com.milesforce.mwbewb.Model.MobileNumberModel;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;
import com.milesforce.mwbewb.Retrofit.CommanApiClient;
import com.milesforce.mwbewb.Retrofit.CommanApiUtills;
import com.milesforce.mwbewb.Retrofit.SuccessModel;
import com.milesforce.mwbewb.Utils.BatterPercentage;
import com.milesforce.mwbewb.Utils.BatteryModel;
import com.milesforce.mwbewb.Utils.MultiSelectionSpinner;
import com.milesforce.mwbewb.Utils.TriggerCallWIthTelephoneManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.milesforce.mwbewb.Utils.ConstantUtills.IIML_TAB_CHANGE_CODE;
import static com.milesforce.mwbewb.Utils.ConstantUtills.VERSION_NUMBER;
import static com.milesforce.mwbewb.Utils.ConstantUtills.ZOOM_INVITATION;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserInfoFragment extends Fragment implements View.OnClickListener, MultiSelectionSpinner.OnMultipleItemsSelectedListener {

    AppCompatCheckBox cpa_check, cma_checkBox, iiml_fa_check, iiml_ba_check, iiml_pa_check, iiml_hr_check, iitr_bf_check,
            iitr_dbe_check, iimlfa_check, iimlsf_checkBox, CPA_AA_check, CFA_check, FRM_check, USP_check;
    ImageView add_phone, add_email, mobile_icon, email_icon;
    EditText textView_company, textview_designation, textview_experiance;
    TextView triggerToCall, person_name, visibleNumber, delete_number, masked_email_, visible_email, delete_email, education_details, person_Info_details;
    AppCompatSpinner city_info_spinner, eligibility_info_spinner, loan_assistance_info_spinner;
    ArrayList<String> cityArrayList = new ArrayList<>();
    ArrayList<String> eligibilityArrayList = new ArrayList<>();
    ArrayList<String> educationTagsArrayList = new ArrayList<>();
    ArrayList<String> loanAssistanceArrayList = new ArrayList<>();
    String AccessToken;
    int person_id, can_id, mwb_id, applied_for_loan;
    String Courses, loan_status, education_tags, source, level, identity;
    String personName, education, company, designation, experiance, documents_submitted;
    RecyclerView userMobileNumbers_info, userEmail_info;
    ApiClient apiClient = ApiUtills.getAPIService();
    static CommanApiClient commanApiClient = CommanApiUtills.getAPIService();

    ArrayList<MobileNumberModel> mobileNumberModelArrayList;
    MobileIconAdapter mobileIconAdapter;
    EmailAdapter emailAdapter;
    ArrayList<EmailModel> emailModelArrayList;
    boolean isvisible = false;
    boolean isVisible_email = false;
    boolean is_editable = false;
    Button bt_save_mobile_number, bt_save_email;
    boolean isEditble_email = false;
    EditText country_code, Add_mobile_number, addUserEmail;
    ProgressBar add_mobile_progress;
    FrameLayout user_info_snakebar;
    EditText edit_email, edit_number;
    BatteryModel batteryModel;
    AppCompatCheckBox document_submitted, loadAssistacne_submitted;
    RelativeLayout updated_layout_, updated_layout_for_loan_, updated_layout_for_education_tags_;
    private String DOCUMENTS_SUBMITTED = "No";
    private String Eligibility = "Pending";
    private String LoadAssistance = "WIP";
    private String AppliedForLoan = "No";
    RecyclerView education_tags_recycelrview;
    EducationTagsAdapter educationTagsAdapter;
    ArrayList<String> tagsArrayList = new ArrayList<>();
    String[] tags_fromResponce;
    MultiSelectionSpinner education_tags_info_spinner;
    String EducationTagsUpdate;
    LinearLayout iiml_team_courses, CA_team_courses;
    RelativeLayout updated_experiance, updated_designation, updated_company, invite_webinar;


    public UserInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getStaticData();
        return inflater.inflate(R.layout.fragment_user_info, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            AccessToken = getArguments().getString("token");
            person_id = getArguments().getInt("person_id");
            personName = getArguments().getString("person_name");
            Courses = getArguments().getString("courses");
            company = getArguments().getString("company");
            designation = getArguments().getString("designation");
            experiance = getArguments().getString("experiance");
            can_id = getArguments().getInt("can_id");
            mwb_id = getArguments().getInt("id");
            education = getArguments().getString("education");
            documents_submitted = getArguments().getString("documents_submitted");
            applied_for_loan = getArguments().getInt("applied_for_loan");
            loan_status = getArguments().getString("loan_status");
            education_tags = getArguments().getString("education_tags");
            source = getArguments().getString("source");
            level = getArguments().getString("level");
            identity = getArguments().getString("identity");



            /*  args2.putString("source", delaysModel.getSource());
        args2.putString("identity", delaysModel.getIdentity());
        args2.putString("level", delaysModel.getLevel());*/


            try {
                String responce_string = education_tags;
                if (responce_string.equals("null") || responce_string.length() <= 1 || responce_string == null) {
                } else {
                    tags_fromResponce = responce_string.split(",");
                    tagsArrayList = new ArrayList<String>(Arrays.asList(tags_fromResponce));
                }

            } catch (Exception e) {

            }




            /*args2.putInt("applied_for_loan", delaysModel.getApplied_for_loan());
        args2.putString("loan_status", delaysModel.getLoan_status());*/

        } catch (Exception e) {

        }

        initView(view);
        batteryModel = new BatterPercentage().getBattertPercentage(getContext());


    }

    private void initView(View view) {
        person_name = view.findViewById(R.id.person_name);
        userMobileNumbers_info = view.findViewById(R.id.userMobileNumbers_info);
        userMobileNumbers_info.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        userEmail_info = view.findViewById(R.id.userEmail_info);
        userEmail_info.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        textView_company = view.findViewById(R.id.textView_company);
        textview_designation = view.findViewById(R.id.textview_designation);
        textview_experiance = view.findViewById(R.id.textview_experiance);
        user_info_snakebar = view.findViewById(R.id.user_info_snakebar);
        document_submitted = view.findViewById(R.id.document_submitted);
        loadAssistacne_submitted = view.findViewById(R.id.loadAssistacne_submitted);
        loan_assistance_info_spinner = view.findViewById(R.id.loan_assistance_info_spinner);
        updated_layout_for_loan_ = view.findViewById(R.id.updated_layout_for_loan_);
        education_details = view.findViewById(R.id.education_details);
        education_details.setText(education);
//        invite_webinar = view.findViewById(R.id.invite_webinar);
//        invite_webinar.setOnClickListener(this);
//        if (IIML_TAB_CHANGE_CODE == 0) {
//            invite_webinar.setVisibility(View.VISIBLE);
//        }
//        if (IIML_TAB_CHANGE_CODE == 1) {
//            invite_webinar.setVisibility(View.VISIBLE);
//        }


        education_tags_recycelrview = view.findViewById(R.id.education_tags_recycelrview);
        education_tags_recycelrview.setLayoutManager(new GridLayoutManager(getContext(), 2));
        educationTagsAdapter = new EducationTagsAdapter(getContext(), tagsArrayList);
        education_tags_recycelrview.setAdapter(educationTagsAdapter);
        educationTagsAdapter.notifyDataSetChanged();
        updated_layout_for_education_tags_ = view.findViewById(R.id.updated_layout_for_education_tags_);
        person_Info_details = view.findViewById(R.id.person_Info_details);
        person_Info_details.setText(identity + " - " + personName + " - " + source + " - " + level + " - " + company);
        updated_layout_for_education_tags_.setOnClickListener(this);
        educationTagsAdapter.onItemClickListners(new EducationTagsAdapter.OnClickListeners() {
            @Override
            public void itemSelected(View v, int position, String type) {


                tagsArrayList.remove(position);
                educationTagsAdapter.notifyDataSetChanged();
                EducationTagsUpdate = TextUtils.join(", ", tagsArrayList);

            }
        });
        textview_experiance.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                updated_company.setVisibility(View.GONE);
                updated_designation.setVisibility(View.GONE);
                updated_experiance.setVisibility(View.VISIBLE);
                textView_company.setFocusable(true);
                return false;
            }
        });
        textview_designation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                updated_company.setVisibility(View.GONE);
                updated_designation.setVisibility(View.VISIBLE);
                updated_experiance.setVisibility(View.GONE);
                return false;
            }
        });
        textView_company.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                updated_company.setVisibility(View.VISIBLE);
                updated_designation.setVisibility(View.GONE);
                updated_experiance.setVisibility(View.GONE);
                return false;
            }
        });


        try {
            textView_company.setText(company);
            textview_experiance.setText(experiance);
            textview_designation.setText(designation);
        } catch (Exception e) {
        }
        getUserMobileNumber();
        getUserEmails();
        cpa_check = view.findViewById(R.id.cpa_check);
        cma_checkBox = view.findViewById(R.id.cma_checkBox);
        iimlfa_check = view.findViewById(R.id.iimlfa_check);
        iimlsf_checkBox = view.findViewById(R.id.iimlsf_checkBox);
        CPA_AA_check = view.findViewById(R.id.CPA_AA_check);
        CFA_check = view.findViewById(R.id.CFA_check);
        FRM_check = view.findViewById(R.id.FRM_check);
        USP_check = view.findViewById(R.id.USP_check);


        if (Courses.contains("CPA")) {
            cpa_check.setChecked(true);
        }
        if (Courses.contains("CMA")) {
            cma_checkBox.setChecked(true);
        }
        if (Courses.contains("IIML-FA")) {
            iimlfa_check.setChecked(true);
        }
        if (Courses.contains("IIML-SF")) {
            iimlsf_checkBox.setChecked(true);
        }

        if (Courses.contains("CPA-AA")) {
            CPA_AA_check.setChecked(true);
        }
        if (Courses.contains("CFA")) {
            CFA_check.setChecked(true);
        }
        if (Courses.contains("FRM")) {
            FRM_check.setChecked(true);
        }
        if (Courses.contains("USP")) {
            USP_check.setChecked(true);
        }

        cpa_check.setEnabled(false);
        cma_checkBox.setEnabled(false);
        iimlfa_check.setEnabled(false);
        iimlsf_checkBox.setEnabled(false);
        CPA_AA_check.setEnabled(false);
        CFA_check.setEnabled(false);
        FRM_check.setEnabled(false);
        USP_check.setEnabled(false);


        updated_experiance = view.findViewById(R.id.updated_experiance);
        updated_experiance.setOnClickListener(this);
        updated_designation = view.findViewById(R.id.updated_designation);
        updated_designation.setOnClickListener(this);
        updated_company = view.findViewById(R.id.updated_company);
        updated_company.setOnClickListener(this);


        add_phone = view.findViewById(R.id.add_phone);
        add_phone.setOnClickListener(this);
        add_email = view.findViewById(R.id.add_email);
        add_email.setOnClickListener(this);
        mobile_icon = view.findViewById(R.id.mobile_icon);
        mobile_icon.setOnClickListener(this);
        email_icon = view.findViewById(R.id.email_icon);
        email_icon.setOnClickListener(this);
        city_info_spinner = view.findViewById(R.id.city_info_spinner);
        eligibility_info_spinner = view.findViewById(R.id.eligibility_info_spinner);
        education_tags_info_spinner = view.findViewById(R.id.education_tags_info_spinner);
        updated_layout_ = view.findViewById(R.id.updated_layout_);
        eligibility_info_spinner.setClickable(false);
        eligibility_info_spinner.setEnabled(false);
        loan_assistance_info_spinner.setClickable(false);
        loan_assistance_info_spinner.setEnabled(false);
        loadAssistacne_submitted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    loan_assistance_info_spinner.setClickable(true);
                    loan_assistance_info_spinner.setEnabled(true);
                    updated_layout_for_loan_.setVisibility(View.VISIBLE);
                    AppliedForLoan = "Yes";
                } else {
                    loan_assistance_info_spinner.setClickable(false);
                    loan_assistance_info_spinner.setEnabled(false);
                    updated_layout_for_loan_.setVisibility(View.GONE);
                    AppliedForLoan = "No";
                }

            }
        });
        updated_layout_for_loan_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiClient.AddApplyForLoan(mwb_id, person_id, LoadAssistance, AppliedForLoan, batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), VERSION_NUMBER, "Bearer " + AccessToken, "application/json").enqueue(new Callback<SuccessModel>() {
                    @Override
                    public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                        try {
                            showSnakebar(response.body().getMessage());
                        } catch (Exception e) {
                            showSnakebar(String.valueOf(e.getMessage()));
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessModel> call, Throwable t) {
                        showSnakebar(String.valueOf(t.getMessage()));
                    }
                });
            }
        });
        document_submitted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    eligibility_info_spinner.setClickable(true);
                    eligibility_info_spinner.setEnabled(true);
                    updated_layout_.setVisibility(View.VISIBLE);
                    DOCUMENTS_SUBMITTED = "Yes";
                } else {
                    eligibility_info_spinner.setClickable(false);
                    eligibility_info_spinner.setEnabled(false);
                    updated_layout_.setVisibility(View.GONE);
                    DOCUMENTS_SUBMITTED = "No";
                }
            }
        });
        updated_layout_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiClient.AddEligibility(mwb_id, person_id, Eligibility, DOCUMENTS_SUBMITTED, batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), VERSION_NUMBER, "Bearer " + AccessToken, "application/json").enqueue(new Callback<SuccessModel>() {
                    @Override
                    public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                        try {
                            showSnakebar(response.body().getMessage());
                        } catch (Exception e) {
                            showSnakebar(String.valueOf(e.getMessage()));
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessModel> call, Throwable t) {
                        showSnakebar(String.valueOf(t.getMessage()));
                    }
                });
            }
        });

        /*Loan Assistance*/
        ArrayAdapter<String> load_infoAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, loanAssistanceArrayList);
        // Drop down layout style - list view with radio button
        load_infoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        loan_assistance_info_spinner.setAdapter(load_infoAdapter);
        loan_assistance_info_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LoadAssistance = loan_assistance_info_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, cityArrayList);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        city_info_spinner.setAdapter(dataAdapter);

        ArrayAdapter<String> eligibilityAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, eligibilityArrayList);
        // Drop down layout style - list view with radio button
        eligibilityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        eligibility_info_spinner.setAdapter(eligibilityAdapter);

      /*  ArrayAdapter<String> educationTag_infoAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, educationTagsArrayList);
        // Drop down layout style - list view with radio button
        educationTag_infoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        education_tags_info_spinner.setAdapter(educationTag_infoAdapter);*/

        education_tags_info_spinner.setItems(educationTagsArrayList);

        education_tags_info_spinner.setListener(this);


        loan_assistance_info_spinner.setSelection(loanAssistanceArrayList.indexOf(loan_status));

        person_name.setText(personName);
        eligibility_info_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Eligibility = eligibility_info_spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (documents_submitted.equals("Yes")) {
            document_submitted.setChecked(true);
        } else {
            document_submitted.setChecked(false);
        }
        if (applied_for_loan == 1) {
            loadAssistacne_submitted.setChecked(true);
        } else {
            loadAssistacne_submitted.setChecked(false);
        }

        iiml_team_courses = view.findViewById(R.id.iiml_team_courses);
        CA_team_courses = view.findViewById(R.id.CA_team_courses);
        iiml_fa_check = view.findViewById(R.id.iiml_fa_check);
        iiml_ba_check = view.findViewById(R.id.iiml_ba_check);
        iiml_pa_check = view.findViewById(R.id.iiml_pa_check);
        iiml_hr_check = view.findViewById(R.id.iiml_hr_check);
        iitr_bf_check = view.findViewById(R.id.iitr_bf_check);
        iitr_dbe_check = view.findViewById(R.id.iitr_dbe_check);
        if (IIML_TAB_CHANGE_CODE == 1) {
            iiml_team_courses.setVisibility(View.VISIBLE);
            CA_team_courses.setVisibility(View.GONE);
        }
        if (IIML_TAB_CHANGE_CODE == 0) {
            iiml_team_courses.setVisibility(View.GONE);
            CA_team_courses.setVisibility(View.VISIBLE);

        }
        if (Courses.contains("IIML-BA")) {
            iiml_ba_check.setChecked(true);

        }
        if (Courses.contains("IIML-FA")) {
            iiml_fa_check.setChecked(true);

        }
        if (Courses.contains("IIML-PM")) {
            iiml_pa_check.setChecked(true);

        }
        if (Courses.contains("IIML-HR")) {
            iiml_hr_check.setChecked(true);

        }
        if (Courses.contains("IITR-BF")) {
            iitr_bf_check.setChecked(true);

        }
        if (Courses.contains("IITR-DB")) {
            iitr_dbe_check.setChecked(true);
        }


    }

    /*Get User  Emails*/
    private void getUserEmails() {
        emailModelArrayList = new ArrayList<>();
//        apiClient.getUserEmails(person_id, "Bearer " + AccessToken, "application/json").enqueue(new Callback<List<EmailModel>>() {
        commanApiClient.getCommanUserEmails(person_id, "Bearer " + AccessToken, "application/json").enqueue(new Callback<List<EmailModel>>() {

            @Override
            public void onResponse(Call<List<EmailModel>> call, Response<List<EmailModel>> response) {
                try {
                    if (response.body() == null) {
                        int statusCode = response.raw().code();
                        if (statusCode > 399 && statusCode < 500) {
                            openAertDialog();
                        }
                    } else {
                        Log.d("onResponse_Email", response.body().toString());

                        List<EmailModel> getEmailModelList = response.body();
                        for (int i = 0; i < getEmailModelList.size(); i++) {
                            EmailModel emailModel = new EmailModel();
                            emailModel.setEmail(getEmailModelList.get(i).getEmail());
                            emailModel.setMasked_email(getEmailModelList.get(i).getMasked_email());
                            emailModel.setId(getEmailModelList.get(i).getId());
                            emailModelArrayList.add(emailModel);
                        }
                        emailAdapter = new EmailAdapter(getContext(), emailModelArrayList);
                        userEmail_info.setAdapter(emailAdapter);
                        emailAdapter.notifyDataSetChanged();
                        emailAdapter.onItemSelectedListners(new EmailAdapter.onItemClickListners() {
                            @Override
                            public void onItemSelectedListners(View view, int position) {
                                EmailModel emailModel = emailModelArrayList.get(position);
                                openEditEmailInfoData(emailModel);
                            }
                        });
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<List<EmailModel>> call, Throwable t) {
                Log.d("onFailure_Email", t.getMessage());

            }
        });
    }

    /*Get User Mobiles */
    private void getUserMobileNumber() {
        mobileNumberModelArrayList = new ArrayList<>();
//        apiClient.getClientMobileNumbers(person_id, "Bearer " + AccessToken,"application/json").enqueue(new Callback<List<MobileNumberModel>>() {
        commanApiClient.getCommanClientMobileNumbers(person_id, "Bearer " + AccessToken, "application/json").enqueue(new Callback<List<MobileNumberModel>>() {

            @Override
            public void onResponse(Call<List<MobileNumberModel>> call, Response<List<MobileNumberModel>> response) {

                try {
                    if (response.body() == null) {
                        int statusCode = response.raw().code();
                        if (statusCode > 399 && statusCode < 500) {
                            openAertDialog();
                        }
                    } else {
                        Log.d("onResponse_Mobile", response.body().toString());

                        List<MobileNumberModel> mobileNumberModels = response.body();
                        for (int i = 0; i < mobileNumberModels.size(); i++) {
                            MobileNumberModel mobileNumberModel = new MobileNumberModel();
                            mobileNumberModel.setPhone_number(mobileNumberModels.get(i).getPhone_number());
                            mobileNumberModel.setMasked_number(mobileNumberModels.get(i).getMasked_number());
                            mobileNumberModel.setId(mobileNumberModels.get(i).getId());
                            mobileNumberModelArrayList.add(mobileNumberModel);
                        }
                        mobileIconAdapter = new MobileIconAdapter(getContext(), mobileNumberModelArrayList);
                        userMobileNumbers_info.setAdapter(mobileIconAdapter);
                        mobileIconAdapter.notifyDataSetChanged();
                        mobileIconAdapter.OnitecmClickListners(new MobileIconAdapter.onClickListners() {
                            @Override
                            public void getClickedItem(View view, int position) {
                                MobileNumberModel mobileNumberModel = mobileNumberModelArrayList.get(position);
                                openEditMobileInfoData(mobileNumberModel);
                            }
                        });
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<List<MobileNumberModel>> call, Throwable t) {
                Log.d("onFailure_Mobile", t.getMessage());

            }
        });


    }

    private void openAertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Yours Session is Expired..!Please trt after Login")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_phone:
                openAlertAddMobileData("mobile");
                break;
            case R.id.email_icon:
                openAlertAddEmailData("email");
                break;
            case R.id.updated_layout_for_education_tags_:
                UpdateEducationTags(mwb_id, person_id);
                break;
            case R.id.updated_experiance:
                UpdateExperiance(mwb_id, person_id);
                break;
            case R.id.updated_designation:
                UpdateDesignation(mwb_id, person_id);
                break;
            case R.id.updated_company:
                UpdateCompany(mwb_id, person_id);
                break;
            case R.id.invite_webinar:
                InviteHimToWebinar(mwb_id);
                break;
           /* case R.id.textView_company:
                updated_company.setVisibility(View.VISIBLE);
                updated_designation.setVisibility(View.GONE);
                updated_experiance.setVisibility(View.GONE);
                textView_company.setFocusable(true);
                break;
            case R.id.textview_designation:
                updated_company.setVisibility(View.GONE);
                updated_designation.setVisibility(View.VISIBLE);
                updated_experiance.setVisibility(View.GONE);
                textview_designation.setFocusable(true);
                break;
            case R.id.textview_experiance:
                updated_company.setVisibility(View.GONE);
                updated_designation.setVisibility(View.GONE);
                updated_experiance.setVisibility(View.VISIBLE);
                textview_experiance.setFocusable(false);

                break;*/

           /* case R.id.mobile_icon:
                openEditMobileInfoData();
                break;*/
           /* case R.id.email_icon:
                openEditEmailInfoData();
                break;*/


        }

    }

    private void InviteHimToWebinar(int mwb_id) {
        apiClient.inviteToWebinar(ZOOM_INVITATION, mwb_id, "Bearer " + AccessToken).enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                try {
                    if (response.body().getStatus().equals("success")) {
                        showSnakebar(response.body().getMessage());

                    } else {
                        showSnakebar(response.body().getMessage());
                    }
                } catch (Exception e) {
                    showSnakebar(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                showSnakebar(t.getMessage());

            }
        });

    }

    private void UpdateCompany(int mwb_id, int person_id) {
        apiClient.editCompany(textView_company.getText().toString(), mwb_id, person_id, "Bearer " + AccessToken).enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                try {
                    if (response.body().getStatus().equals("success")) {
                        showSnakebar(response.body().getMessage());
                        updated_company.setVisibility(View.GONE);

                    } else {

                        showSnakebar(response.body().getMessage());
                    }
                } catch (Exception e) {
                    showSnakebar(e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                showSnakebar(t.getMessage());
            }
        });

    }

    private void UpdateDesignation(int mwb_id, int person_id) {
        apiClient.editDesignation(textview_designation.getText().toString(), mwb_id, person_id, "Bearer " + AccessToken).enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                try {
                    if (response.body().getStatus().equals("success")) {
                        showSnakebar(response.body().getMessage());
                        updated_designation.setVisibility(View.GONE);

                    } else {

                        showSnakebar(response.body().getMessage());
                    }
                } catch (Exception e) {
                    showSnakebar(e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                showSnakebar(t.getMessage());
            }
        });

    }

    private void UpdateExperiance(int mwb_id, int person_id) {
        apiClient.editExperiance(textview_experiance.getText().toString(), mwb_id, person_id, "Bearer " + AccessToken).enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                try {
                    if (response.body().getStatus().equals("success")) {
                        showSnakebar(response.body().getMessage());
                        updated_experiance.setVisibility(View.GONE);

                    } else {

                        showSnakebar(response.body().getMessage());
                    }
                } catch (Exception e) {
                    showSnakebar(e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                showSnakebar(t.getMessage());
            }
        });

    }


    private void UpdateEducationTags(int mwb_id, int person_id) {
        apiClient.UpdateEducation(mwb_id, person_id, EducationTagsUpdate, batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), VERSION_NUMBER, "Bearer " + AccessToken, "application/json").enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                try {
                    if (response.body().getStatus().equals("success")) {
                        showSnakebar(response.body().getMessage());
                        if (emailModelArrayList != null) {
                            emailModelArrayList.clear();
                            getUserEmails();
                        }
                    } else {

                        showSnakebar(response.body().getMessage());
                    }
                } catch (Exception e) {
                    showSnakebar(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<SuccessModel> call, Throwable t) {
                showSnakebar(t.getMessage());
            }
        });
    }


    private void openEditEmailInfoData(final EmailModel emailModel) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.email_info);
        dialog.setCancelable(true);

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
        masked_email_ = dialog.findViewById(R.id.masked_email_);
        visible_email = dialog.findViewById(R.id.visible_email);
        edit_email = dialog.findViewById(R.id.edit_email);
        delete_email = dialog.findViewById(R.id.delete_email);
        bt_save_email = dialog.findViewById(R.id.bt_save_email);
        masked_email_.setText(emailModel.getMasked_email());
        visible_email.setText(emailModel.getMasked_email());
        edit_email.setText(emailModel.getMasked_email());
        visible_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isVisible_email) {
                    visible_email.setText(emailModel.getEmail());
                    isVisible_email = true;
                } else {
                    visible_email.setText(emailModel.getMasked_email());
                    isVisible_email = false;
                }
            }
        });
        edit_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_email.setText(emailModel.getEmail());
                edit_email.setSelection(edit_email.getText().toString().length());
                bt_save_email.setVisibility(View.VISIBLE);

            }
        });
        bt_save_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_email.getText().toString().trim().isEmpty()) {
                    edit_email.setError("Please enter email");
                } else {
                    EditUserEmailAddress(dialog, emailModel.getId());
                }
            }
        });

        delete_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteUserEmail(dialog, emailModel.getId());

            }
        });


        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setAttributes(lp);
    }

    /*Edited User Email Address*/
    private void EditUserEmailAddress(final Dialog dialog, int id) {
        apiClient.EditUserEmailAddress(id, edit_email.getText().toString().trim(), batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), VERSION_NUMBER, "Bearer " + AccessToken, "application/json").enqueue(new Callback<AddMobileEmailInfo>() {
            @Override
            public void onResponse(Call<AddMobileEmailInfo> call, Response<AddMobileEmailInfo> response) {
                try {
                    if (response.body().getStatus().equals("success")) {
                        dialog.dismiss();
                        showSnakebar(response.body().getMessage());
                        if (emailModelArrayList != null) {
                            emailModelArrayList.clear();
                            getUserEmails();
                        }
                    } else {
                        dialog.dismiss();
                        showSnakebar(response.body().getMessage());
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                    showSnakebar(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<AddMobileEmailInfo> call, Throwable t) {
                dialog.dismiss();
                showSnakebar(t.getMessage());
            }
        });
    }

    /*Delete User Email*/
    private void DeleteUserEmail(final Dialog dialog, int id) {
        apiClient.DeleteEmail(id, batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), VERSION_NUMBER, "Bearer " + AccessToken, "application/json").enqueue(new Callback<AddMobileEmailInfo>() {
            @Override
            public void onResponse(Call<AddMobileEmailInfo> call, Response<AddMobileEmailInfo> response) {
                try {
                    if (response.body().getStatus().equals("success")) {
                        dialog.dismiss();
                        showSnakebar(response.body().getMessage());
                        if (emailModelArrayList != null) {
                            emailModelArrayList.clear();
                            getUserEmails();
                        }
                    } else {
                        showSnakebar(response.body().getMessage());
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                    showSnakebar(String.valueOf(e.getMessage()));
                }
            }

            @Override
            public void onFailure(Call<AddMobileEmailInfo> call, Throwable t) {
                dialog.dismiss();
                showSnakebar(String.valueOf(t.getMessage()));
            }
        });
    }

    private void openEditMobileInfoData(final MobileNumberModel mobileNumberModel) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.mobile_number_info);
        dialog.setCancelable(true);

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
        bt_save_mobile_number = dialog.findViewById(R.id.bt_save_mobile_number);
        triggerToCall = dialog.findViewById(R.id.triggerToCall);
        triggerToCall.setText(mobileNumberModel.getMasked_number());
        visibleNumber = dialog.findViewById(R.id.visibleNumber);
        visibleNumber.setText(mobileNumberModel.getMasked_number());
        edit_number = dialog.findViewById(R.id.edit_number);
        edit_number.setText(mobileNumberModel.getMasked_number());
        delete_number = dialog.findViewById(R.id.delete_number);
        delete_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeleteUserMobileNummber(dialog, mobileNumberModel.getId());

            }
        });

        visibleNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isvisible) {
                    visibleNumber.setText(mobileNumberModel.getPhone_number());
                    isvisible = true;
                } else {
                    visibleNumber.setText(mobileNumberModel.getMasked_number());
                    isvisible = false;
                }

            }
        });
        edit_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_number.setText(mobileNumberModel.getPhone_number());
                edit_number.setSelection(edit_number.getText().toString().length());
                bt_save_mobile_number.setVisibility(View.VISIBLE);

            }
        });
        bt_save_mobile_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditUserMobileNUmber(dialog, mobileNumberModel.getId());
            }
        });
        triggerToCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TriggerCallWIthTelephoneManager.TriggerCall(String.valueOf(mobileNumberModel.getPhone_number()), getContext());
                dialog.dismiss();


            }
        });

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setAttributes(lp);
    }

    /*Delete User MobileNumber*/
    private void DeleteUserMobileNummber(final Dialog dialog, int id) {
        apiClient.DeleteUserMobile(id, batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), VERSION_NUMBER, "Bearer " + AccessToken, "application/json").enqueue(new Callback<AddMobileEmailInfo>() {
            @Override
            public void onResponse(Call<AddMobileEmailInfo> call, Response<AddMobileEmailInfo> response) {
                try {
                    if (response.body().getStatus().equals("success")) {
                        dialog.dismiss();
                        showSnakebar(response.body().getMessage());
                        if (mobileNumberModelArrayList != null) {
                            mobileNumberModelArrayList.clear();
                        }
                        getUserMobileNumber();
                    } else {
                        showSnakebar(response.body().getMessage());
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                    showSnakebar(String.valueOf(e.getMessage()));
                }

            }

            @Override
            public void onFailure(Call<AddMobileEmailInfo> call, Throwable t) {
                dialog.dismiss();
                showSnakebar(String.valueOf(t.getMessage()));
            }
        });
    }

    /*Edited User Mobile Number*/
    private void EditUserMobileNUmber(final Dialog dialog, int id) {
        apiClient.EditUserMobileNumber(id, edit_number.getText().toString().trim(), "", batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), VERSION_NUMBER, "Bearer " + AccessToken, "application/json").enqueue(new Callback<AddMobileEmailInfo>() {
            @Override
            public void onResponse(Call<AddMobileEmailInfo> call, Response<AddMobileEmailInfo> response) {
                try {

                    if (response.body().getStatus().equals("success")) {
                        dialog.dismiss();
                        showSnakebar(response.body().getMessage());
                        if (mobileNumberModelArrayList != null) {
                            mobileNumberModelArrayList.clear();
                        }
                        getUserMobileNumber();

                    } else {
                        showSnakebar(response.body().getMessage());
                    }
                } catch (Exception e) {
                    showSnakebar(e.getMessage());
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<AddMobileEmailInfo> call, Throwable t) {
                showSnakebar(t.getMessage());
                dialog.dismiss();
            }
        });
    }


    private void openAlertAddEmailData(String email) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.emailadd_layout);
        dialog.setCancelable(true);
        addUserEmail = dialog.findViewById(R.id.addUserEmail);
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
                if (addUserEmail.getText().toString().isEmpty()) {
                    addUserEmail.setError("Please enter email....!");
                } else {
                    AddUserEmail(dialog);
                }
            }
        });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setAttributes(lp);
    }


    private void openAlertAddMobileData(String mobile) {
        final Dialog dialog_mobile = new Dialog(getContext());
        dialog_mobile.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog_mobile.setContentView(R.layout.mobile_info_layout);
        dialog_mobile.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog_mobile.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        country_code = dialog_mobile.findViewById(R.id.country_code);
        Add_mobile_number = dialog_mobile.findViewById(R.id.Add_mobile_number);
        add_mobile_progress = dialog_mobile.findViewById(R.id.add_mobile_progress);


        ((ImageButton) dialog_mobile.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_mobile.dismiss();
            }
        });
        ((Button) dialog_mobile.findViewById(R.id.bt_save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddUserMobileNUmber(dialog_mobile);


            }
        });
        dialog_mobile.show();
        dialog_mobile.setCanceledOnTouchOutside(false);
        dialog_mobile.getWindow().setAttributes(lp);
    }


    /*Add USer Mobile Number*/
    private void AddUserMobileNUmber(final Dialog dialog_mobile) {
        String countryCode = " ";
        if (country_code.getText().toString().isEmpty()) {
            countryCode = " ";
        } else {
            countryCode = country_code.getText().toString().trim();
        }

        if (Add_mobile_number.getText().toString().trim().isEmpty()) {
            Add_mobile_number.setError("Please Fill mobile Number...");
        } else {
            add_mobile_progress.setVisibility(View.VISIBLE);
            apiClient.AddUserMobilenumber(can_id, person_id, personName, Add_mobile_number.getText().toString().trim(), countryCode, batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), VERSION_NUMBER, "Bearer " + AccessToken, "application/json").enqueue(new Callback<AddMobileEmailInfo>() {
                @Override
                public void onResponse(Call<AddMobileEmailInfo> call, Response<AddMobileEmailInfo> response) {
                    try {
                        if (response.body().getStatus().equals("success")) {
                            dialog_mobile.dismiss();
                            add_mobile_progress.setVisibility(View.GONE);
                            showSnakebar(response.body().getMessage());
                            if (mobileNumberModelArrayList != null) {
                                mobileNumberModelArrayList.clear();
                            }
                            getUserMobileNumber();
                        }
                        if (response.body().getStatus().equals("error")) {
                            dialog_mobile.dismiss();
                            add_mobile_progress.setVisibility(View.GONE);
                            showSnakebar(response.body().getMessage());
                        } else {
                            showSnakebar(response.body().getMessage());
                        }

                    } catch (Exception e) {
                        dialog_mobile.dismiss();
                        add_mobile_progress.setVisibility(View.GONE);
                        showSnakebar(e.getMessage());
                    }

                }

                @Override
                public void onFailure(Call<AddMobileEmailInfo> call, Throwable t) {
                    dialog_mobile.dismiss();
                    add_mobile_progress.setVisibility(View.GONE);
                    showSnakebar(t.getMessage());
                }
            });
        }

    }

    public void showSnakebar(String s) {
        Snackbar snackbar = Snackbar
                .make(user_info_snakebar, s, 10000);
        snackbar.show();
    }

    /*Adding User Email*/
    private void AddUserEmail(final Dialog dialog) {
        apiClient.AddUserEmail(can_id, person_id, personName, addUserEmail.getText().toString().trim(), batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), VERSION_NUMBER, "Bearer " + AccessToken, "application/json").enqueue(new Callback<AddMobileEmailInfo>() {
            @Override
            public void onResponse(Call<AddMobileEmailInfo> call, Response<AddMobileEmailInfo> response) {
                try {
                    if (response.body().getStatus().equals("success")) {
                        dialog.dismiss();
                        showSnakebar(response.body().getMessage());
                        if (emailModelArrayList != null) {
                            emailModelArrayList.clear();
                        }
                        getUserEmails();
                    }
                    if (response.body().getStatus().equals("error")) {
                        dialog.dismiss();
                        add_mobile_progress.setVisibility(View.GONE);
                        showSnakebar(response.body().getMessage());
                    }
                } catch (Exception e) {
                    dialog.dismiss();
                    showSnakebar(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<AddMobileEmailInfo> call, Throwable t) {
                dialog.dismiss();
                showSnakebar(t.getMessage());
            }
        });
    }


    private void getStaticData() {
        cityArrayList.add("Hyderabad");
        cityArrayList.add("Delhi");
        cityArrayList.add("Mumbai");
        cityArrayList.add("Chennai");
        cityArrayList.add("Bengaluru");
        cityArrayList.add("Ernakulam");
        cityArrayList.add("Ahmadabad");
        cityArrayList.add("Jaipur");
        cityArrayList.add("Pune");


        eligibilityArrayList.add("Pending");
        eligibilityArrayList.add("Clear");
        eligibilityArrayList.add("Check");
        eligibilityArrayList.add("PGDPA");
        /*  eligibilityArrayList.add("PGDA");*/


        educationTagsArrayList.add("B.Com");
        educationTagsArrayList.add("B.Com (P)");
        educationTagsArrayList.add("M.Com");
        educationTagsArrayList.add("M.Com (P)");
        educationTagsArrayList.add("MBA");
        educationTagsArrayList.add("MBA (P)");
        educationTagsArrayList.add("CA");
        educationTagsArrayList.add("CWA");
        educationTagsArrayList.add("CS");
        educationTagsArrayList.add("BMS");
        educationTagsArrayList.add("BMS (P)");
        educationTagsArrayList.add("BBA");
        educationTagsArrayList.add("BBA (P)");
        educationTagsArrayList.add("PGD");
        educationTagsArrayList.add("PGD (P)");
        educationTagsArrayList.add("LLB");
        educationTagsArrayList.add("LLB (P)");
        educationTagsArrayList.add("B.Tech");
        educationTagsArrayList.add("B.Tech (P)");
        educationTagsArrayList.add("B.Sc");
        educationTagsArrayList.add("B.Sc (P)");
        educationTagsArrayList.add("BA");
        educationTagsArrayList.add("BA (P)");
        educationTagsArrayList.add("CA-Inter");
        educationTagsArrayList.add("CA Final");
        educationTagsArrayList.add("CWA-Inter");
        educationTagsArrayList.add("CWA Final");
        educationTagsArrayList.add("CS-Inter");
        educationTagsArrayList.add("CS Final");
        educationTagsArrayList.add("PG ");
        educationTagsArrayList.add("PG (P) ");
        educationTagsArrayList.add("IGNOU - B.Com(CA) ");
        educationTagsArrayList.add("IGNOU - B.Com(CWA) ");
        educationTagsArrayList.add("IGNOU - B.Com(CS) ");
        educationTagsArrayList.add("MCA");
        educationTagsArrayList.add("PGDPA");
        educationTagsArrayList.add("PGDSF");
        educationTagsArrayList.add("PGDM");


        loanAssistanceArrayList.add("WIP");
        loanAssistanceArrayList.add("Not Interested");
        loanAssistanceArrayList.add("Not Eligible");
        loanAssistanceArrayList.add("Postponed");
        loanAssistanceArrayList.add("Rejected");
        loanAssistanceArrayList.add("Processed");
        loanAssistanceArrayList.add("Disbursed");


       /* tagsArrayList = new ArrayList<>();
        tagsArrayList.add("CA-Inter");
        tagsArrayList.add("MBA");
        tagsArrayList.add("BBA (P)");
        tagsArrayList.add("B.Tech (P)");
        tagsArrayList.add("CA-Inter");
        tagsArrayList.add("MBA");
        tagsArrayList.add("BBA (P)");*/
    }


    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {
        tagsArrayList.addAll(strings);
        educationTagsAdapter.notifyDataSetChanged();
        EducationTagsUpdate = TextUtils.join(", ", tagsArrayList);
    }
}
