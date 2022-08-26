package com.milesforce.mwbewb.TabFragments;


import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.milesforce.mwbewb.Activities.AddEngagementActivity;
import com.milesforce.mwbewb.Activities.EWBSRActivity;
import com.milesforce.mwbewb.Model.CallLogs;
import com.milesforce.mwbewb.Model.DelaysModel;
import com.milesforce.mwbewb.Model.NetEnquiry;
import com.milesforce.mwbewb.Model.NetEnquiryData;
import com.milesforce.mwbewb.Model.UserToken;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;
import com.milesforce.mwbewb.Retrofit.SuccessModel;
import com.milesforce.mwbewb.Utils.AlertForAddB2BCRLeadForm;
import com.milesforce.mwbewb.Utils.AlertForAddB2CLeadForml;
import com.milesforce.mwbewb.Utils.AlertForMobileNumberInfo;
import com.milesforce.mwbewb.Utils.CustomEngagementFormForUntracked;
import com.milesforce.mwbewb.Utils.CustomSearchAdapter;
import com.milesforce.mwbewb.Utils.EscalationAdapter;
import com.milesforce.mwbewb.Utils.NetEnquiryAdapter;
import com.milesforce.mwbewb.Utils.ResumeWork;
import com.milesforce.mwbewb.Utils.TriggerCallWIthTelephoneManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.milesforce.mwbewb.Utils.ConstantUtills.Escalation_Url;
import static com.milesforce.mwbewb.Utils.ConstantUtills.NET_ENQUIRY_URL;
import static com.milesforce.mwbewb.Utils.ConstantUtills.VERSION_NUMBER;

/**
 * A simple {@link Fragment} subclass.
 */
public class NetEnquireFragment extends Fragment {

    SharedPreferences sharedPreferences;
    ApiClient apiClient;
    String AccessToken;
    RecyclerView net_enquiry_recyclerView;
    ProgressBar net_enquiry_progress, bottom_net_enquiry_Progress;
    FloatingActionButton flating_btn, net_enquiry_refresh_floating;
    NetEnquiryAdapter netEnquiryAdapter;
    ArrayList<NetEnquiryData> netEnquiryArrayList;
    ArrayList<NetEnquiryData>sortedNewEnquiryDataArrayList;
    int page = 1;
    SwipeRefreshLayout net_enquiry_SwipetoRefresh;
    Realm realm;

    UserToken userToken;
    String Access_token;
    RadioButton b2c_check, add_a_lead, irrelevant_check;
    CardView b2c_card, releationship_card;
    EditText relationship_name_untrack, et_search_clientsInUntracked;
    LinearLayout personal_save_btn, addLead_saveUpdateuntracked_form, addLead_untracked_form;
    ProgressBar untracke_progress;
    AppCompatSpinner untrackedLeadsSpinner_m2b;
    TextView untrack_user_info_details;
    FrameLayout snake_bar_top;
    ArrayList<DelaysModel> delaysModelArrayList;
    CustomSearchAdapter customSearchAdapter;

    public NetEnquireFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        realm = Realm.getDefaultInstance();
        apiClient = ApiUtills.getAPIService();
        if (realm.where(UserToken.class).findFirst() != null) {
            userToken = realm.where(UserToken.class).findFirst();
            Access_token = userToken.getAccessToken();
        }
        return inflater.inflate(R.layout.fragment_net_enquire, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        net_enquiry_recyclerView = view.findViewById(R.id.net_enquiry_recyclerView);
        net_enquiry_SwipetoRefresh = view.findViewById(R.id.net_enquiry_SwipetoRefresh);
        snake_bar_top = view.findViewById(R.id.snake_bar_top);
        net_enquiry_SwipetoRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                net_enquiry_SwipetoRefresh.setRefreshing(false);
            }
        });
        net_enquiry_progress = view.findViewById(R.id.net_enquiry_progress);
        flating_btn = view.findViewById(R.id.flating_btn);
        net_enquiry_refresh_floating = view.findViewById(R.id.net_enquiry_refresh_floating);
        bottom_net_enquiry_Progress = view.findViewById(R.id.bottom_net_enquiry_Progress);
        net_enquiry_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
       // paginatedRecyclerview(net_enquiry_recyclerView);
        flating_btn.setImageBitmap(textAsBitmap(String.valueOf("0"), 40, Color.BLUE));
        net_enquiry_refresh_floating = view.findViewById(R.id.net_enquiry_refresh_floating);
        net_enquiry_refresh_floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sortedNewEnquiryDataArrayList.size() > 0) {
                    sortedNewEnquiryDataArrayList.clear();
                }
                getNetEnquiryData();
            }
        });
        getNetEnquiryData();

    }

    private void getNetEnquiryData() {
        net_enquiry_progress.setVisibility(View.VISIBLE);
        netEnquiryArrayList = new ArrayList<>();
        sortedNewEnquiryDataArrayList = new ArrayList<>();
        page = 1;
        apiClient.getNetEnquiresData(NET_ENQUIRY_URL, 1, "Bearer " + Access_token, "application/json").enqueue(new Callback<NetEnquiry>() {
            @Override
            public void onResponse(Call<NetEnquiry> call, Response<NetEnquiry> response) {
                try {
                    if(response.raw().code() == 515){
                        new ResumeWork().stResumeWork(getContext());
                        return;
                    }
                    if (response.body() == null) {
                        int statusCode = response.raw().code();
                        if (statusCode == 401) {
                            //SessionLogout(getContext());
                            net_enquiry_progress.setVisibility(View.GONE);
                        }
                    } else {
                        List<NetEnquiryData> netEnquity = response.body().getNetEnquiryData();
                        netEnquiryArrayList.addAll(netEnquity);
                        ArrayList<NetEnquiryData> diallingNumberArrayLIst = getDiallingNUmberData(netEnquiryArrayList);

                      //  sortedNewEnquiryDataArrayList = removeDuplicates(diallingNumberArrayLIst);

                        sortedNewEnquiryDataArrayList = removeDuplicatesTest(diallingNumberArrayLIst);


                        netEnquiryAdapter = new NetEnquiryAdapter(getContext(), sortedNewEnquiryDataArrayList);
                        net_enquiry_recyclerView.setAdapter(netEnquiryAdapter);
                        netEnquiryAdapter.notifyDataSetChanged();

                      //  flating_btn.setImageBitmap(textAsBitmap(String.valueOf(response.body().getTotal()), 40, Color.BLUE));

                        flating_btn.setImageBitmap(textAsBitmap(String.valueOf(sortedNewEnquiryDataArrayList.size()), 40, Color.BLUE));

                        netEnquiryAdapter.onItemClickListners(new NetEnquiryAdapter.OnClickListner() {
                            @Override
                            public void itemSelected(View v, int position, String type) {
                                NetEnquiryData netEnquiryData = sortedNewEnquiryDataArrayList.get(position);
                                if (type.equals("mobile")) {
                                    if (netEnquiryData.getMobile() != null && netEnquiryData.getMobile() != "") {
                                        TriggerCallWIthTelephoneManager.TriggerCall(String.valueOf(netEnquiryData.getDialling_number()), getContext());
                                    }
                                }
                                if (type.equals("engagement")) {
                                    OpenUntrackedForm(netEnquiryData);
                                }

                            }
                        });

                        net_enquiry_progress.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    net_enquiry_progress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<NetEnquiry> call, Throwable t) {
                net_enquiry_progress.setVisibility(View.GONE);
                Toast.makeText(getContext(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private ArrayList<NetEnquiryData> removeDuplicatesTest(ArrayList<NetEnquiryData> diallingNumberArrayLIst) {
        Map<String, NetEnquiryData> cleanMap = new LinkedHashMap<String, NetEnquiryData>();

        for (int i = 0; i < diallingNumberArrayLIst.size(); i++) {
            cleanMap.put(diallingNumberArrayLIst.get(i).getDialling_number(), diallingNumberArrayLIst.get(i));
        }
        ArrayList<NetEnquiryData> list = new ArrayList<NetEnquiryData>(cleanMap.values());
        return list;
    }



    private ArrayList<NetEnquiryData> getDiallingNUmberData(ArrayList<NetEnquiryData> netEnquiryArrayList) {
        ArrayList<NetEnquiryData> netEnquiryDataArrayList = new ArrayList<>();
        for( int i = 0; i<netEnquiryArrayList.size();i++){
            NetEnquiryData netEnquiryData = new NetEnquiryData();
            netEnquiryData.setId(netEnquiryArrayList.get(i).getId());
            netEnquiryData.setIdentity(netEnquiryArrayList.get(i).getIdentity());
            netEnquiryData.setEnquiry_date(netEnquiryArrayList.get(i).getEnquiry_date());
            netEnquiryData.setEnquiry_date_unix(netEnquiryArrayList.get(i).getEnquiry_date_unix());
            netEnquiryData.setDetails(netEnquiryArrayList.get(i).getDetails());
            netEnquiryData.setCourse(netEnquiryArrayList.get(i).getCourse());
            netEnquiryData.setName(netEnquiryArrayList.get(i).getName());
            netEnquiryData.setMobile(netEnquiryArrayList.get(i).getMobile());
            netEnquiryData.setEmail(netEnquiryArrayList.get(i).getEmail());
            netEnquiryData.setCity(netEnquiryArrayList.get(i).getCity());
            netEnquiryData.setSuggested_city(netEnquiryArrayList.get(i).getSuggested_city());
            netEnquiryData.setSelected_spoc(netEnquiryArrayList.get(i).getSelected_spoc());
            netEnquiryData.setSelected_iiml_spoc(netEnquiryArrayList.get(i).getSelected_iiml_spoc());
            netEnquiryData.setStatus(netEnquiryArrayList.get(i).getStatus());
            netEnquiryData.setPerson_name(netEnquiryArrayList.get(i).getPerson_name());
            netEnquiryData.setPerson_id(netEnquiryArrayList.get(i).getPerson_id());
            netEnquiryData.setPerson_type(netEnquiryArrayList.get(i).getPerson_type());
            netEnquiryData.setMwb_id(netEnquiryArrayList.get(i).getMwb_id());
            netEnquiryData.setLevel(netEnquiryArrayList.get(i).getLevel());
            netEnquiryData.setPerson_type(netEnquiryArrayList.get(i).getPerson_type());
            netEnquiryData.setNet_enquiry_type(netEnquiryArrayList.get(i).getNet_enquiry_type());
            if(netEnquiryArrayList.get(i).getMobile().length() == 10){
                netEnquiryData.setDialling_number(netEnquiryArrayList.get(i).getMobile());
            }if(netEnquiryArrayList.get(i).getMobile().length() > 10){
                String Mobile_NUmber = netEnquiryArrayList.get(i).getMobile();
                String LastTenDigits = Mobile_NUmber.substring(Mobile_NUmber.length() - 10);
                netEnquiryData.setDialling_number(LastTenDigits);
            }else {
                netEnquiryData.setDialling_number(netEnquiryArrayList.get(i).getMobile());

            }
            netEnquiryDataArrayList.add(netEnquiryData);
        }
        return netEnquiryDataArrayList;

    }

    private void OpenUntrackedForm(final NetEnquiryData netEnquiryData) {
        final Dialog untracked_dialog = new Dialog(getContext());
        untracked_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        untracked_dialog.setContentView(R.layout.netenquiry_numberlayout);
        untracked_dialog.setCancelable(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(untracked_dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        b2c_check = untracked_dialog.findViewById(R.id.b2c_check);
        add_a_lead = untracked_dialog.findViewById(R.id.add_a_lead);
        irrelevant_check = untracked_dialog.findViewById(R.id.irrelevant_check);


        b2c_card = untracked_dialog.findViewById(R.id.b2c_card);

        releationship_card = untracked_dialog.findViewById(R.id.releationship_card);
        relationship_name_untrack = untracked_dialog.findViewById(R.id.relationship_name_untrack);
        personal_save_btn = untracked_dialog.findViewById(R.id.personal_save_btn);
        untracke_progress = untracked_dialog.findViewById(R.id.untracke_progress);
        et_search_clientsInUntracked = untracked_dialog.findViewById(R.id.et_search_clientsInUntracked);
        untrackedLeadsSpinner_m2b = untracked_dialog.findViewById(R.id.untrackedLeadsSpinner_m2b);
        untrack_user_info_details = untracked_dialog.findViewById(R.id.untrack_user_info_details);
        addLead_saveUpdateuntracked_form = untracked_dialog.findViewById(R.id.addLead_saveUpdateuntracked_form);
        addLead_untracked_form = untracked_dialog.findViewById(R.id.addLead_untracked_form);

        b2c_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    add_a_lead.setChecked(false);
                    irrelevant_check.setChecked(false);
                    b2c_card.setVisibility(View.VISIBLE);
                    releationship_card.setVisibility(View.GONE);
                    AddB2bUntrackedLead(untracked_dialog, netEnquiryData, "B2C");


                }
            }
        });

        add_a_lead.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    b2c_check.setChecked(false);
                    irrelevant_check.setChecked(false);
                    b2c_card.setVisibility(View.GONE);
                    releationship_card.setVisibility(View.GONE);
                    untracked_dialog.dismiss();
                    new AlertForAddB2CLeadForml().AddB2cLeadForm(getContext(), Access_token, netEnquiryData.getMobile(),netEnquiryData.getEmail(),netEnquiryData.getName(),"Net Enquiry",String.valueOf(netEnquiryData.getId()),1,netEnquiryData.getNet_enquiry_type());
                }
            }
        });
        irrelevant_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    b2c_check.setChecked(false);
                    add_a_lead.setChecked(false);
                    b2c_card.setVisibility(View.GONE);
                    releationship_card.setVisibility(View.VISIBLE);
                    saveAsIrrevalentData(untracked_dialog, netEnquiryData);

                }
            }
        });
        ((ImageButton) untracked_dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                untracked_dialog.dismiss();
            }
        });

        untracked_dialog.show();
        untracked_dialog.setCanceledOnTouchOutside(false);
        untracked_dialog.getWindow().setAttributes(lp);
    }

    private void AddB2bUntrackedLead(final Dialog untracked_dialog, final NetEnquiryData netEnquiryData, final String B2C) {
        et_search_clientsInUntracked.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //  hideKeyboard();
                    if (delaysModelArrayList != null) {
                        delaysModelArrayList.clear();
                    }
                    getSearchedUSerData(Access_token, v.getText().toString(), B2C, untracked_dialog, netEnquiryData);
                    return true;
                }
                return false;
            }
        });

    }

    private void getSearchedUSerData(String accessToken, String person_name, final String B2C, final Dialog dialog, final NetEnquiryData netEnquiryData) {
        untracke_progress.setVisibility(View.VISIBLE);
        delaysModelArrayList = new ArrayList<>();
        untrack_user_info_details.setText("");

        apiClient.getSearchedLeads(person_name, B2C, "Bearer " + Access_token, "application/json").enqueue(new Callback<List<DelaysModel>>() {
            @Override
            public void onResponse(Call<List<DelaysModel>> call, Response<List<DelaysModel>> response) {
                try {
                    List<DelaysModel> getdelaysModel = response.body();
                    for (int i = 0; i < getdelaysModel.size(); i++) {
                        DelaysModel delaysModel = new DelaysModel();
                        delaysModel.setId(getdelaysModel.get(i).getId());
                        delaysModel.setIdentity(getdelaysModel.get(i).getIdentity());
                        delaysModel.setCan_id(getdelaysModel.get(i).getCan_id());
                        delaysModel.setPerson_id(getdelaysModel.get(i).getPerson_id());
                        delaysModel.setPerson_name(getdelaysModel.get(i).getPerson_name());
                        delaysModel.setCourses(getdelaysModel.get(i).getCourses());
                        delaysModel.setLevel(getdelaysModel.get(i).getLevel());
                        delaysModel.setEngagement_details(getdelaysModel.get(i).getEngagement_details());
                        delaysModel.setCompany(getdelaysModel.get(i).getCompany());
                        delaysModel.setDesignation(getdelaysModel.get(i).getDesignation());
                        delaysModel.setMiles_type(getdelaysModel.get(i).getMiles_type());
                    //    delaysModel.setIiml_level(getdelaysModel.get(i).getIiml_level());
                        delaysModelArrayList.add(delaysModel);
                    }
                    customSearchAdapter = new CustomSearchAdapter(getContext(), delaysModelArrayList);
                    untrackedLeadsSpinner_m2b.setAdapter(customSearchAdapter);
                    customSearchAdapter.notifyDataSetChanged();
                    if (delaysModelArrayList.size() == 0) {
                        untrack_user_info_details.setText("Sorry did not find the person look for. Please add a person..!");
                    }
                    untracke_progress.setVisibility(View.GONE);

                } catch (Exception e) {
                    Toast.makeText(getContext(), String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
                    untracke_progress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<DelaysModel>> call, Throwable t) {
                Toast.makeText(getContext(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                untracke_progress.setVisibility(View.GONE);

            }
        });

        untrackedLeadsSpinner_m2b.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DelaysModel delaysModel = delaysModelArrayList.get(position);
                untrack_user_info_details.setText(delaysModel.getEngagement_details());
                AddLeadWithPersonDetails(delaysModel, dialog, netEnquiryData, B2C);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void AddLeadWithPersonDetails(final DelaysModel delaysModel, final Dialog dialog, final NetEnquiryData netEnquiryData, final String B2c) {
        addLead_untracked_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                untracke_progress.setVisibility(View.VISIBLE);
                apiClient.markNetEnquiryWIthMwbID(delaysModel.getIdentity(), netEnquiryData.getMobile(), delaysModel.getId(), delaysModel.getPerson_id(), netEnquiryData.getPerson_name(), "Bearer " + Access_token, "application/json").enqueue(new Callback<SuccessModel>() {
                    @Override
                    public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                        try {
                            if (response.body().getStatus().equals("success")) {
                                new CustomEngagementFormForUntracked().addCustomEngagement(delaysModel, getContext(), AccessToken);
                                dialog.dismiss();
                                if (netEnquiryArrayList != null) {
                                    netEnquiryArrayList.clear();
                                }
                                getNetEnquiryData();

                            } else {
                                ShowSnakeBar(response.body().getMessage());
                                untrack_user_info_details.setText(response.body().getMessage());
                            }
                            untracke_progress.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.getMessage();
                            ShowSnakeBar(String.valueOf(e.getMessage()));
                            untrack_user_info_details.setText(String.valueOf(e.getMessage()));
                        }

                    }

                    @Override
                    public void onFailure(Call<SuccessModel> call, Throwable t) {
                        untracke_progress.setVisibility(View.GONE);
                        t.getMessage();
                        ShowSnakeBar(String.valueOf(t.getMessage()));
                        untrack_user_info_details.setText(String.valueOf(t.getMessage()));
                    }
                });
            }
        });
    }


    private void saveAsIrrevalentData(final Dialog untracked_dialog, final NetEnquiryData netEnquiryData) {

        personal_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (relationship_name_untrack.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Please enter Comments", Toast.LENGTH_SHORT).show();
                    return;
                }
                untracke_progress.setVisibility(View.VISIBLE);
                apiClient.markNetEnquiryInValid(netEnquiryData.getId(), relationship_name_untrack.getText().toString(), "Bearer " + Access_token, "application/json").enqueue(new Callback<SuccessModel>() {
                    @Override
                    public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                        try {
                            if (response.body().getStatus().equals("success")) {
                                untracked_dialog.dismiss();
                                ShowSnakeBar(response.body().getMessage());
                                untracke_progress.setVisibility(View.GONE);
                                if (netEnquiryArrayList != null) {
                                    netEnquiryArrayList.clear();
                                }
                                getNetEnquiryData();
                            } else {
                                ShowSnakeBar(response.body().getMessage());
                                untracke_progress.setVisibility(View.GONE);
                            }

                        } catch (Exception e) {
                            untracked_dialog.dismiss();
                            ShowSnakeBar(e.getMessage());
                            untracke_progress.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onFailure(Call<SuccessModel> call, Throwable t) {

                    }
                });
            }
        });


    }

    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }

    private void paginatedRecyclerview(RecyclerView escalation_recyclerView) {
        escalation_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (dx == 0 && dy == 0) {

                } else {
                    if (Objects.requireNonNull(linearLayoutManager).findLastCompletelyVisibleItemPosition() ==
                            netEnquiryAdapter.getItemCount() - 1) {
                        page = page + 1;
                        bottom_net_enquiry_Progress.setVisibility(View.VISIBLE);
                        apiClient.getNetEnquiresData(NET_ENQUIRY_URL, page, "Bearer " + Access_token, "application/json").enqueue(new Callback<NetEnquiry>() {
                            @Override
                            public void onResponse(Call<NetEnquiry> call, Response<NetEnquiry> response) {
                                try {

                                    ArrayList<NetEnquiryData> net_enquiry = response.body().getNetEnquiryData();
                                    netEnquiryArrayList.addAll(net_enquiry);
                                    netEnquiryAdapter.notifyDataSetChanged();
                                    bottom_net_enquiry_Progress.setVisibility(View.GONE);
                                } catch (Exception e) {
                                    bottom_net_enquiry_Progress.setVisibility(View.GONE);
                                    Toast.makeText(getContext(), String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<NetEnquiry> call, Throwable t) {
                                bottom_net_enquiry_Progress.setVisibility(View.GONE);
                                Toast.makeText(getContext(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                            }
                        });


                        //load more items code is here
                    }
                }
            }
        });
    }

    public void ShowSnakeBar(String s) {
        Snackbar snackbar = Snackbar
                .make(snake_bar_top, String.valueOf(s), 5000);
        snackbar.show();
    }

    public ArrayList<NetEnquiryData>  removeDuplicates(ArrayList<NetEnquiryData> list){
        Set<NetEnquiryData> set = new TreeSet(new Comparator<NetEnquiryData>() {

            @Override
            public int compare(NetEnquiryData o1, NetEnquiryData o2) {
               if(o1.getDialling_number().equals(o2.getDialling_number()) || o1.getDialling_number().equalsIgnoreCase(o2.getDialling_number()) || o1.getDialling_number().contains(o2.getDialling_number()) || o2.getDialling_number().contains(o1.getDialling_number()) ){
                    if(o2.getDuplicateCount() == 0)
                        o2.setDuplicateCount(1);
                        o2.setDuplicateCount(o2.getDuplicateCount()+1);
                    return 0;
                }
                return 1;
            }
        });
        set.addAll(list);

        final ArrayList newList = new ArrayList(set);
        return newList;
    }
}
