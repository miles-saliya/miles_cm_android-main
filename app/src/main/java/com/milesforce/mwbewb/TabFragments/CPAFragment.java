package com.milesforce.mwbewb.TabFragments;


import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.milesforce.mwbewb.Model.CpaData;
import com.milesforce.mwbewb.Model.UserToken;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;
import com.milesforce.mwbewb.Retrofit.SuccessModel;

import java.util.ArrayList;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CPAFragment extends Fragment implements View.OnClickListener {

    TextView evaluation_id, nts_id, aud_one, far_one, bec_one, reg_one, aud_quater, far_quater, bec_quater, reg_quater;
    CpaData cpaData;
    ImageView add_poa;
    static WindowManager.LayoutParams lp;
    AppCompatSpinner evaluation_spinner, nts_spinner, aud_spinner, far_spinner, bec_spinner, reg_spinner;
    ArrayAdapter<String> evaluation_adapter;
    ArrayAdapter<String> nts_adapter;
    ArrayAdapter<String> aud_adapter;
    ArrayAdapter<String> far_adapter;
    ArrayAdapter<String> bec_adapter;
    ArrayAdapter<String> reg_adapter;
    String EVALUATION = "";
    String NTS = "";
    String AUD = "";
    String FAR = "";
    String BEC = "";
    String REG = "";
    ArrayList<String> evaluation_arrayList;
    ArrayList<String> nts_arrayList;
    ArrayList<String> aud_arrayList;
    RelativeLayout update_plan_off_action;
    ProgressBar sr_progress;
    ApiClient apiClient;
    Realm realm;
    UserToken accessToken;

    public CPAFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         cpaData = (CpaData) getArguments().getSerializable("cpa_data");
        apiClient = ApiUtills.getAPIService();
        realm = Realm.getDefaultInstance();

        return inflater.inflate(R.layout.fragment_cpa, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDataForSpinners();
        if (realm.where(UserToken.class).findFirst() != null) {
            accessToken = realm.where(UserToken.class).findFirst();
        }
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

        evaluation_id.setText(cpaData.getEvaluation());
        nts_id.setText(cpaData.getNts());
        aud_quater.setText(cpaData.getAud());
        far_quater.setText(cpaData.getFar());
        bec_quater.setText(cpaData.getBec());
        reg_quater.setText(cpaData.getReg());
        add_poa = view.findViewById(R.id.add_poa);
        add_poa.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_poa:
                openPoaAlert();
                break;
        }
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

            try {
                int evaluate = evaluation_adapter.getPosition(cpaData.getEvaluation());
                evaluation_spinner.setSelection(evaluate);
                int nts = nts_adapter.getPosition(cpaData.getNts());
                nts_spinner.setSelection(nts);

                int aud = aud_adapter.getPosition(cpaData.getAud());
                aud_spinner.setSelection(aud);

                int far = aud_adapter.getPosition(cpaData.getFar());
                far_spinner.setSelection(far);

                int bec = bec_adapter.getPosition(cpaData.getBec());
                bec_spinner.setSelection(bec);

                int reg = reg_adapter.getPosition(cpaData.getBec());
                reg_spinner.setSelection(reg);

            }catch (Exception e){

            }


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
    private void submitEditPlanOfAction(String evaluation, String nts, String aud, String far, String bec, String reg, final Dialog dialog) {
        apiClient.updatePlanOffAction(cpaData.getId(), evaluation, nts, aud, bec, far, reg, "Bearer " + accessToken.getAccessToken(), "application/json").enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                try {
                    if (response.body().getStatus().equals("success")) {
                        Toast.makeText(getContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        sr_progress.setVisibility(View.VISIBLE);
                        evaluation_id.setText(response.body().getCpa().getEvaluation());
                        nts_id.setText(response.body().getCpa().getNts());
                        aud_quater.setText(response.body().getCpa().getAud());
                        far_quater.setText(response.body().getCpa().getFar());
                        bec_quater.setText(response.body().getCpa().getBec());
                        reg_quater.setText(response.body().getCpa().getReg());
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
