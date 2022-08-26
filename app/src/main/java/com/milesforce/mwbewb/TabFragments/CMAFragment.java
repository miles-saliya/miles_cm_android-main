package com.milesforce.mwbewb.TabFragments;


import android.app.Dialog;
import android.media.Image;
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

import com.milesforce.mwbewb.Model.CmaData;
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
public class CMAFragment extends Fragment implements View.OnClickListener {

    TextView  target_one, target_two;
    CmaData cmaData;
    ImageView add_poa;
    static WindowManager.LayoutParams lp;
    AppCompatSpinner evaluation_spinner, nts_spinner,target_two_spinner,target_one_spinner;
    ArrayList<String> aud_arrayList;
    ArrayAdapter<String> target_one_adapter;
    ArrayAdapter<String> target_two_adapter;
    String TARGET_ONE="";
    String TARGET_TWO="";
    RelativeLayout update_plan_off_action;
    ProgressBar sr_progress;
    ApiClient apiClient;
    Realm realm;
    UserToken accessToken;



    public CMAFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        cmaData = (CmaData) getArguments().getSerializable("cma_data");
        apiClient = ApiUtills.getAPIService();
        realm = Realm.getDefaultInstance();
        return inflater.inflate(R.layout.fragment_cma, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDataForSpinners();
        if (realm.where(UserToken.class).findFirst() != null) {
            accessToken = realm.where(UserToken.class).findFirst();
        }
        target_one = view.findViewById(R.id.target_one);
        target_two = view.findViewById(R.id.target_two);
        target_one.setText(cmaData.getPart_1_exam_target());
        target_two.setText(cmaData.getPart_2_exam_target());
        add_poa =view.findViewById(R.id.add_poa);
        add_poa.setOnClickListener(this);
    }

    private void initDataForSpinners() {
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_poa:
                openAlertForCMA();
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
                        submitEditPlanOfActionForCMA(cmaData.getId(),TARGET_ONE, TARGET_TWO, dialog);
                        sr_progress.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "No Data Found to Update", Toast.LENGTH_SHORT).show();
                        sr_progress.setVisibility(View.GONE);
                    }

                }

            }
        });

            try {
                int target1 = target_one_adapter.getPosition(cmaData.getPart_1_exam_target());
                target_one_spinner.setSelection(target1);
                int target2 = target_two_adapter.getPosition(cmaData.getPart_2_exam_target());
                target_two_spinner.setSelection(target2);
            }catch (Exception e){

            }
    }
    private void submitEditPlanOfActionForCMA(int id,String target_one_, String target_two_,final Dialog dialog) {

        apiClient.updateCMAPlanOffAction(id, target_one_, target_two_, "Bearer " + accessToken.getAccessToken(), "application/json").enqueue(new Callback<SuccessModel>() {
            @Override
            public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                try {
                    if (response.body().getStatus().equals("success")) {
                        target_one.setText(response.body().getCma().getPart_1_exam_target());
                        target_two.setText(response.body().getCma().getPart_2_exam_target());
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
