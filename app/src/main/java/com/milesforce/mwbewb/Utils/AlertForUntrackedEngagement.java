package com.milesforce.mwbewb.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.cardview.widget.CardView;

import com.milesforce.mwbewb.Model.CallLogs;
import com.milesforce.mwbewb.Model.DelaysModel;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;
import com.milesforce.mwbewb.Retrofit.SuccessModel;
import com.milesforce.mwbewb.TabFragments.AlertForAddB2BIRLeadForm;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.milesforce.mwbewb.Utils.ConstantUtills.VERSION_NUMBER;

public class AlertForUntrackedEngagement {
    TextView mobile_number_header;
    RadioButton b2c_check, b2b_ir_check, b2b_cr_check, personal_check, employee_check, spam_check, vendor_check;
    CardView b2c_card, b2b_ic_card, b2b_cr_card, releationship_card;
    LinearLayout submit_card;
    EditText relationship_name_untrack, relationship_untrack, milesEmployee_untracked, et_search_clientsInUntracked;
    LinearLayout personal_save_btn, milesEmployee_name_Card, addLead_saveUpdateuntracked_form, addLead_untracked_form;
    ProgressBar untracke_progress;
    ArrayList<DelaysModel> delaysModelArrayList;
    AppCompatSpinner untrackedLeadsSpinner_m2b;
    CustomSearchAdapter customSearchAdapter;
    DelaysModel delaysModel;
    SharedPreferences sharedPreferences;
    AlertDialog alert;
    BatteryModel batteryModel;
    TextView untrack_user_info_details;
    ApiClient apiClient;
    String AccessToken;
    Context context;


    public void UntrackedAdd(final Context context, final String callLogs, String AccessToken) {
        apiClient = ApiUtills.getAPIService();
        batteryModel = new BatterPercentage().getBattertPercentage(context);
        final Dialog untracked_dialog = new Dialog(context);
        this.AccessToken = AccessToken;
        this.context = context;
        untracked_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        untracked_dialog.setContentView(R.layout.untrackednumberlayout);
        untracked_dialog.setCancelable(true);
        mobile_number_header = untracked_dialog.findViewById(R.id.mobile_number_header);
        mobile_number_header.setText(callLogs);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(untracked_dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        b2c_check = untracked_dialog.findViewById(R.id.b2c_check);
        b2b_ir_check = untracked_dialog.findViewById(R.id.b2b_ir_check);
        b2b_cr_check = untracked_dialog.findViewById(R.id.b2b_cr_check);
        personal_check = untracked_dialog.findViewById(R.id.personal_check);
        employee_check = untracked_dialog.findViewById(R.id.employee_check);
        spam_check = untracked_dialog.findViewById(R.id.spam_check);
        vendor_check = untracked_dialog.findViewById(R.id.vendor_check);
        b2c_card = untracked_dialog.findViewById(R.id.b2c_card);
        b2b_ic_card = untracked_dialog.findViewById(R.id.b2b_ic_card);
        b2b_cr_card = untracked_dialog.findViewById(R.id.b2b_cr_card);
        releationship_card = untracked_dialog.findViewById(R.id.releationship_card);
        submit_card = untracked_dialog.findViewById(R.id.submit_card);
        relationship_name_untrack = untracked_dialog.findViewById(R.id.relationship_name_untrack);
        relationship_untrack = untracked_dialog.findViewById(R.id.relationship_untrack);
        personal_save_btn = untracked_dialog.findViewById(R.id.personal_save_btn);
        untracke_progress = untracked_dialog.findViewById(R.id.untracke_progress);
        milesEmployee_name_Card = untracked_dialog.findViewById(R.id.milesEmployee_name_Card);
        milesEmployee_untracked = untracked_dialog.findViewById(R.id.milesEmployee_untracked);
        et_search_clientsInUntracked = untracked_dialog.findViewById(R.id.et_search_clientsInUntracked);
        untrackedLeadsSpinner_m2b = untracked_dialog.findViewById(R.id.untrackedLeadsSpinner_m2b);
        untrack_user_info_details = untracked_dialog.findViewById(R.id.untrack_user_info_details);
        addLead_saveUpdateuntracked_form = untracked_dialog.findViewById(R.id.addLead_saveUpdateuntracked_form);
        addLead_untracked_form = untracked_dialog.findViewById(R.id.addLead_untracked_form);


        b2c_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    b2b_ir_check.setChecked(false);
                    b2b_cr_check.setChecked(false);
                    personal_check.setChecked(false);
                    employee_check.setChecked(false);
                    vendor_check.setChecked(false);
                    spam_check.setChecked(false);
                    b2c_card.setVisibility(View.VISIBLE);
                    b2b_ic_card.setVisibility(View.GONE);
                    b2b_cr_card.setVisibility(View.GONE);
                    releationship_card.setVisibility(View.GONE);
                    submit_card.setVisibility(View.GONE);
                    milesEmployee_name_Card.setVisibility(View.GONE);
                    AddB2bUntrackedLead(untracked_dialog, callLogs, "B2C");
                }
            }
        });
        b2b_ir_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    b2c_check.setChecked(false);
                    b2b_cr_check.setChecked(false);
                    personal_check.setChecked(false);
                    employee_check.setChecked(false);
                    spam_check.setChecked(false);
                    vendor_check.setChecked(false);
                    b2c_card.setVisibility(View.VISIBLE);
                    b2b_ic_card.setVisibility(View.GONE);
                    b2b_cr_card.setVisibility(View.GONE);
                    releationship_card.setVisibility(View.GONE);
                    submit_card.setVisibility(View.GONE);
                    milesEmployee_name_Card.setVisibility(View.GONE);
                    AddB2bUntrackedLead(untracked_dialog, callLogs, "B2BIR");
                }
            }
        });
        b2b_cr_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    b2c_check.setChecked(false);
                    b2b_ir_check.setChecked(false);
                    personal_check.setChecked(false);
                    employee_check.setChecked(false);
                    spam_check.setChecked(false);
                    vendor_check.setChecked(false);
                    b2c_card.setVisibility(View.VISIBLE);
                    b2b_ic_card.setVisibility(View.GONE);
                    b2b_cr_card.setVisibility(View.GONE);
                    releationship_card.setVisibility(View.GONE);
                    submit_card.setVisibility(View.GONE);
                    milesEmployee_name_Card.setVisibility(View.GONE);
                    AddB2bUntrackedLead(untracked_dialog, callLogs, "B2BCR");
                }
            }
        });
        personal_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    b2c_check.setChecked(false);
                    b2b_ir_check.setChecked(false);
                    b2b_cr_check.setChecked(false);
                    employee_check.setChecked(false);
                    spam_check.setChecked(false);
                    vendor_check.setChecked(false);
                    b2c_card.setVisibility(View.GONE);
                    b2b_ic_card.setVisibility(View.GONE);
                    b2b_cr_card.setVisibility(View.GONE);
                    releationship_card.setVisibility(View.VISIBLE);
                    submit_card.setVisibility(View.GONE);
                    ContactAsPersonal(callLogs, untracked_dialog);
                    milesEmployee_name_Card.setVisibility(View.GONE);
                }
            }
        });
        employee_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    b2c_check.setChecked(false);
                    b2b_ir_check.setChecked(false);
                    b2b_cr_check.setChecked(false);
                    personal_check.setChecked(false);
                    spam_check.setChecked(false);
                    vendor_check.setChecked(false);
                    b2c_card.setVisibility(View.GONE);
                    b2b_ic_card.setVisibility(View.GONE);
                    b2b_cr_card.setVisibility(View.GONE);
                    releationship_card.setVisibility(View.GONE);
                    submit_card.setVisibility(View.VISIBLE);
                    milesEmployee_name_Card.setVisibility(View.VISIBLE);
                    SaveContactNumberAsMilesEmployee(untracked_dialog, callLogs);
                }
            }
        });
        spam_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    b2c_check.setChecked(false);
                    b2b_ir_check.setChecked(false);
                    b2b_cr_check.setChecked(false);
                    personal_check.setChecked(false);
                    employee_check.setChecked(false);
                    vendor_check.setChecked(false);
                    b2c_card.setVisibility(View.GONE);
                    b2b_ic_card.setVisibility(View.GONE);
                    b2b_cr_card.setVisibility(View.GONE);
                    releationship_card.setVisibility(View.GONE);
                    submit_card.setVisibility(View.VISIBLE);
                    milesEmployee_untracked.setHint("Info");
                    milesEmployee_name_Card.setVisibility(View.VISIBLE);
                    saveNumberAsSpam(untracked_dialog, callLogs);
                }
            }
        });
        vendor_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    b2c_check.setChecked(false);
                    b2b_ir_check.setChecked(false);
                    b2b_cr_check.setChecked(false);
                    personal_check.setChecked(false);
                    spam_check.setChecked(false);
                    b2c_card.setVisibility(View.GONE);
                    b2b_ic_card.setVisibility(View.GONE);
                    b2b_cr_card.setVisibility(View.GONE);
                    releationship_card.setVisibility(View.GONE);
                    submit_card.setVisibility(View.VISIBLE);
                    milesEmployee_untracked.setHint("Vendor Name");
                    milesEmployee_name_Card.setVisibility(View.VISIBLE);
                    saveAsVendor(untracked_dialog, callLogs);
                    // SaveContactNumberAsMilesEmployee(untracked_dialog, callLogs.getPhone_number());
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

    private void AddB2bUntrackedLead(final Dialog untracked_dialog, final String callLogs, final String B2C) {
        et_search_clientsInUntracked.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //  hideKeyboard();
                    if (delaysModelArrayList != null) {
                        delaysModelArrayList.clear();
                    }
                    getSearchedUSerData(AccessToken, v.getText().toString(), B2C, untracked_dialog, callLogs);
                    return true;
                }
                return false;
            }
        });

        addLead_saveUpdateuntracked_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                untracked_dialog.dismiss();
                if (B2C.equals("B2C")) {
                    new AlertForAddB2CLeadForml().AddB2cLeadForm(context, AccessToken, callLogs,"","","","",0,"");
                }
                if (B2C.equals("B2BCR")) {
                    new AlertForAddB2BCRLeadForm().addb2bcrLeadForm(context, AccessToken, callLogs);
                }
                if (B2C.equals("B2BIR")) {
                    new AlertForAddB2BIRLeadForm().addb2bIRLeadForm(context, AccessToken, callLogs);
                }


            }
        });

    }

    private void ContactAsPersonal(final String phone_number, final Dialog dialog) {

        personal_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (relationship_name_untrack.getText().toString().isEmpty()) {
                    relationship_name_untrack.setError("Enter name");
                }
                if (relationship_untrack.getText().toString().isEmpty()) {
                    relationship_untrack.setError("Enter Relationship");
                } else {
                    untracke_progress.setVisibility(View.VISIBLE);
                    apiClient.AddUntrackedAsPersonal(relationship_name_untrack.getText().toString(), relationship_untrack.getText().toString().trim(), phone_number, batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), VERSION_NUMBER,"Bearer " + AccessToken, "application/json").enqueue(new Callback<SuccessModel>() {
                        @Override
                        public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                            try {
                                if (response.body().getStatus().equals("success")) {
                                    dialog.dismiss();
                                    ShowSnakeBar(response.body().getMessage());
                                    untracke_progress.setVisibility(View.GONE);

                                } else {
                                    ShowSnakeBar(response.body().getMessage());
                                    untracke_progress.setVisibility(View.GONE);
                                }

                            } catch (Exception e) {
                                dialog.dismiss();
                                ShowSnakeBar(e.getMessage());
                                untracke_progress.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<SuccessModel> call, Throwable t) {
                            dialog.dismiss();
                            ShowSnakeBar(t.getMessage());
                            untracke_progress.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    private void getSearchedUSerData(String accessToken, String person_name, final String B2C, final Dialog dialog, final String callLogs) {
        untracke_progress.setVisibility(View.VISIBLE);
        delaysModelArrayList = new ArrayList<>();
        untrack_user_info_details.setText("");

        apiClient.getSearchedLeads(person_name, B2C, "Bearer " + accessToken, "application/json").enqueue(new Callback<List<DelaysModel>>() {
            @Override
            public void onResponse(Call<List<DelaysModel>> call, Response<List<DelaysModel>> response) {
                try {
                    List<DelaysModel> getdelaysModel = response.body();
                    for (int i = 0; i < getdelaysModel.size(); i++) {
                        DelaysModel delaysModel = new DelaysModel();
                        String CAN_ID=String.valueOf(getdelaysModel.get(i).getCan_id());
                        /*if (CAN_ID==null) {
                            delaysModel.setCan_id(0);
                        }else {
                            delaysModel.setCan_id(getdelaysModel.get(i).getCan_id());
                        }*/
                        delaysModel.setCan_id(getdelaysModel.get(i).getCan_id());
                        delaysModel.setPerson_id(getdelaysModel.get(i).getPerson_id());
                        delaysModel.setPerson_name(getdelaysModel.get(i).getPerson_name());
                        delaysModel.setCourses(getdelaysModel.get(i).getCourses());
                        delaysModel.setLevel(getdelaysModel.get(i).getLevel());
                        delaysModel.setEngagement_details(getdelaysModel.get(i).getEngagement_details());
                        delaysModel.setCompany(getdelaysModel.get(i).getCompany());
                        delaysModel.setDesignation(getdelaysModel.get(i).getDesignation());
                        delaysModel.setMiles_type(getdelaysModel.get(i).getMiles_type());
                        delaysModel.setIdentity(getdelaysModel.get(i).getIdentity());
                        delaysModelArrayList.add(delaysModel);
                    }

                    customSearchAdapter = new CustomSearchAdapter(context, delaysModelArrayList);
                    untrackedLeadsSpinner_m2b.setAdapter(customSearchAdapter);
                    customSearchAdapter.notifyDataSetChanged();


                    if (delaysModelArrayList.size() == 0) {
                        untrack_user_info_details.setText("Sorry did not find the person look for. Please add a person..!");
                    }
                    untracke_progress.setVisibility(View.GONE);

                } catch (Exception e) {
                    Toast.makeText(context, String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
                    untracke_progress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<DelaysModel>> call, Throwable t) {
                Toast.makeText(context, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                untracke_progress.setVisibility(View.GONE);

            }
        });

        untrackedLeadsSpinner_m2b.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DelaysModel delaysModel = delaysModelArrayList.get(position);
                untrack_user_info_details.setText(delaysModel.getEngagement_details());
                AddLeadWithPersonDetails(delaysModel, dialog, callLogs, B2C);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void AddLeadWithPersonDetails(final DelaysModel delaysModel, final Dialog dialog, final String callLogs, final String B2c) {
        addLead_untracked_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                untracke_progress.setVisibility(View.VISIBLE);
                apiClient.AddUntrackedToExistingPerson(delaysModel.getPerson_id(), delaysModel.getPerson_name(), delaysModel.getCan_id(), delaysModel.getMiles_type(), callLogs, batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), VERSION_NUMBER,"Bearer " + AccessToken, "application/json").enqueue(new Callback<SuccessModel>() {
                    @Override
                    public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                        try {
                            if (response.body().getStatus().equals("success")) {
                                new CustomEngagementFormForUntracked().addCustomEngagement(delaysModel, context, AccessToken);
                                dialog.dismiss();


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

    private void SaveContactNumberAsMilesEmployee(final Dialog untracked_dialog,
                                                  final String phone_number) {
        submit_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (milesEmployee_untracked.getText().toString().isEmpty()) {
                    milesEmployee_untracked.setError("Please Enter Name");
                } else {
                    untracke_progress.setVisibility(View.VISIBLE);
                    apiClient.AddUntrackedAsMilesemployee(milesEmployee_untracked.getText().toString().trim(), phone_number, batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), VERSION_NUMBER,"Bearer " + AccessToken, "application/json").enqueue(new Callback<SuccessModel>() {
                        @Override
                        public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                            try {
                                if (response.body().getStatus().equals("success")) {
                                    untracked_dialog.dismiss();
                                    ShowSnakeBar(response.body().getMessage());
                                    untracke_progress.setVisibility(View.GONE);

                                } else {
                                    untracked_dialog.dismiss();
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
                            untracked_dialog.dismiss();
                            ShowSnakeBar(t.getMessage());
                            untracke_progress.setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

    private void saveNumberAsSpam(final Dialog untracked_dialog, final String phone_number) {
        submit_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                untracke_progress.setVisibility(View.VISIBLE);
                apiClient.AddUntrackedAsSpam(phone_number, milesEmployee_untracked.getText().toString().trim(), batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), VERSION_NUMBER,"Bearer " + AccessToken, "application/json").enqueue(new Callback<SuccessModel>() {
                    @Override
                    public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                        try {
                            if (response.body().getStatus().equals("success")) {
                                untracked_dialog.dismiss();
                                ShowSnakeBar(response.body().getMessage());
                                untracke_progress.setVisibility(View.GONE);

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
                        untracked_dialog.dismiss();
                        ShowSnakeBar(t.getMessage());
                        untracke_progress.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    private void saveAsVendor(final Dialog untracked_dialog, final String phone_number) {

        submit_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                untracke_progress.setVisibility(View.VISIBLE);
                apiClient.AddUntrackedAsVendor(milesEmployee_untracked.getText().toString().trim(), phone_number, batteryModel.getBattey_percentage(), batteryModel.getCharging_status(), VERSION_NUMBER,"Bearer " + AccessToken, "application/json").enqueue(new Callback<SuccessModel>() {
                    @Override
                    public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                        try {
                            if (response.body().getStatus().equals("success")) {
                                untracked_dialog.dismiss();
                                ShowSnakeBar(response.body().getMessage());
                                untracke_progress.setVisibility(View.GONE);
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
                        untracked_dialog.dismiss();
                        ShowSnakeBar(t.getMessage());
                        untracke_progress.setVisibility(View.GONE);
                    }
                });
            }
        });


    }

    private void ShowSnakeBar(String message) {
        Toast.makeText(context, String.valueOf(message), Toast.LENGTH_LONG).show();
    }
}
