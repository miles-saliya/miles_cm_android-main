package com.milesforce.mwbewb.TabFragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.milesforce.mwbewb.Activities.EWBSRActivity;
import com.milesforce.mwbewb.EngagementFragments.AddNewEngagement;
import com.milesforce.mwbewb.EngagementFragments.HistoryFragment;
import com.milesforce.mwbewb.EngagementFragments.UserInfoFragment;
import com.milesforce.mwbewb.Model.CmaData;
import com.milesforce.mwbewb.Model.CpaData;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SRCpaCMA extends Fragment implements View.OnClickListener {
    private int mwb_id;
    String URL_FOR_SR = "";
    ApiClient apiClient;
    Realm realm;
    UserToken accessToken;
    int TAb_SELECTION_TYPE;
    private ViewPager view_pager_engagement;
    private TabLayout tab_layoutEngagementform;
    RecyclerView student_referal_recycler;
    StudentRefferalAdapter studentRefferalAdapter;
    ImageView add_student_reference;
    static WindowManager.LayoutParams lp;
    Calendar myCalendar;
    ProgressBar sr_referral_progress;
    AppCompatEditText appCompatEditText;
    EditText reference_name_edit;
    AppCompatAutoCompleteTextView appCompatAutoCompleteTextView;
    AutoSuggestAdapter autoSuggestAdapter;
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private Handler handler;
    SuggestModel SUGGEST_MODEL = null;
    private static long nextCallTimeStamp = 0;
    DatePickerDialog.OnDateSetListener date;
    View view;
    int person_id;
    CpaData CPA_DATA;
    CmaData CMA_DATA;
    ProgressBar sr_show_progress ;


    public SRCpaCMA() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mwb_id = getArguments().getInt("id");
        person_id = getArguments().getInt("person_id");
        apiClient = ApiUtills.getAPIService();
        return inflater.inflate(R.layout.fragment_srcpa_cm, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       this. view=view;
        sr_show_progress = view.findViewById(R.id.sr_show_progress);
        realm = Realm.getDefaultInstance();
        if (realm.where(UserToken.class).findFirst() != null) {
            accessToken = realm.where(UserToken.class).findFirst();
        }
        URL_FOR_SR = "getCandidateDetails?mwb_id=" + mwb_id + "";
        student_referal_recycler = view.findViewById(R.id.student_referal_recycler);
        add_student_reference = view.findViewById(R.id.add_student_reference);
        add_student_reference.setOnClickListener(this);
        getSrData(URL_FOR_SR,view);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initViews(View view) {
        view_pager_engagement = view.findViewById(R.id.view_pager_srform);
        setupViewPager(view_pager_engagement);
        tab_layoutEngagementform = view.findViewById(R.id.tab_layoutsrform);
        tab_layoutEngagementform.setupWithViewPager(view_pager_engagement);

        setupTabIcons();
        tab_layoutEngagementform.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view_pager_engagement.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void getSrData(String URL_FOR_SR , final View view) {
        apiClient.getEwbDataWithSpocId(URL_FOR_SR, "Bearer " + accessToken.getAccessToken(), "application/json").enqueue(new Callback<SRDataModel>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(Call<SRDataModel> call, Response<SRDataModel> response) {
                try {
                    if (response.body().getStatus().equals("success")) {

                        if(response.body().getData().getStudent_reference()!=null){
                            ArrayList<StudentReferenceData>studentReferenceData = response.body().getData().getStudent_reference();
                            studentRefferalAdapter = new StudentRefferalAdapter(getContext(), studentReferenceData);
                            student_referal_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
                            student_referal_recycler.setAdapter(studentRefferalAdapter);
                            studentRefferalAdapter.notifyDataSetChanged();
                        }
                        if (response.body().getData().getCpa() != null && response.body().getData().getCma() != null) {
                            CPA_DATA = response.body().getData().getCpa();
                            CMA_DATA = response.body().getData().getCma();
                            TAb_SELECTION_TYPE = 0;
                            initViews(view);
                            sr_show_progress.setVisibility(View.GONE);
                            return;
                        }
                        if (response.body().getData().getCpa() != null) {
                            TAb_SELECTION_TYPE = 1;
                            CPA_DATA = response.body().getData().getCpa();
                            initViews(view);
                            sr_show_progress.setVisibility(View.GONE);
                            return;
                        }
                        if (response.body().getData().getCma() != null) {
                            TAb_SELECTION_TYPE = 2;
                            CMA_DATA = response.body().getData().getCma();
                            initViews(view);
                            sr_show_progress.setVisibility(View.GONE);
                            return;
                        }
                        if (response.body().getData().getCpa() == null && response.body().getData().getCma() == null){
                            TAb_SELECTION_TYPE = 3;
                            initViews(view);
                            sr_show_progress.setVisibility(View.GONE);
                            return;
                        }

                    }


                } catch (Exception e) {
                    e.getMessage();
                    sr_show_progress.setVisibility(View.GONE);
                }


            }

            @Override
            public void onFailure(Call<SRDataModel> call, Throwable t) {
                t.getMessage();
                sr_show_progress.setVisibility(View.GONE);

            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {

        CPAFragment srCpaCMA = new CPAFragment();
        Bundle cpabundle = new Bundle();
        cpabundle.putSerializable("cpa_data",CPA_DATA);
        srCpaCMA.setArguments(cpabundle);

        CMAFragment cmaFragment = new CMAFragment();
        Bundle cmabundle = new Bundle();
        cmabundle.putSerializable("cma_data",CMA_DATA);
        cmaFragment.setArguments(cmabundle);

        NoSrFragment noSrFragment =new NoSrFragment();
        Bundle noSR = new Bundle();
        noSrFragment.setArguments(noSR);


        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getChildFragmentManager());
        if (TAb_SELECTION_TYPE == 0) {
            adapter.addFragment(srCpaCMA, "CPA SR");
            adapter.addFragment(cmaFragment, "CMA SR");
        }
        if (TAb_SELECTION_TYPE == 1) {
            adapter.addFragment(srCpaCMA, "CPA SR");
        }
        if (TAb_SELECTION_TYPE == 2) {
            adapter.addFragment(cmaFragment, "CMA SR");
        }
        if (TAb_SELECTION_TYPE == 3){
            adapter.addFragment(noSrFragment, "");
        }
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_student_reference:
                openAlertForStudentReferel();
                break;
        }
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setupTabIcons() {
        if (TAb_SELECTION_TYPE == 0) {
            Objects.requireNonNull(tab_layoutEngagementform.getTabAt(0)).setText("CPA SR");
            Objects.requireNonNull(tab_layoutEngagementform.getTabAt(1)).setText("CMA SR");
        }
        if (TAb_SELECTION_TYPE == 1) {
            Objects.requireNonNull(tab_layoutEngagementform.getTabAt(0)).setText("CPA SR");
        }
        if (TAb_SELECTION_TYPE == 2) {
            Objects.requireNonNull(tab_layoutEngagementform.getTabAt(0)).setText("CMA SR");
        }
        if (TAb_SELECTION_TYPE == 3) {
            Objects.requireNonNull(tab_layoutEngagementform.getTabAt(0)).setText("");
        }
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
    private void submitReferalForm(final Dialog dialog) {
        apiClient.AddReferrelPersonTocandidate(person_id, mwb_id, appCompatEditText.getText().toString(), String.valueOf(nextCallTimeStamp), SUGGEST_MODEL.getPerson_id(), SUGGEST_MODEL.getPerson_name(), SUGGEST_MODEL.getId(), "Bearer " + accessToken.getAccessToken(), "application/json").enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                try {
                    if (response.body().getStatus().equals("success")) {
                        dialog.dismiss();
                        Toast.makeText(getContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        getSrData(URL_FOR_SR,view);
                        dialog.dismiss();
                        sr_referral_progress.setVisibility(View.INVISIBLE);
                    }
                    if(response.body().getStatus().equals("error")){
                        Toast.makeText(getContext(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
}
