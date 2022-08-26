package com.milesforce.mwbewb.EngagementFragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.milesforce.mwbewb.Model.DelaysModel;
import com.milesforce.mwbewb.Model.EngagemmentHistoryModel;
import com.milesforce.mwbewb.Model.HistoryModel;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {
    RecyclerView HistoryRecyclerView;
    HistoryAdapter historyAdapter;
    int person_id;
    String AccessToken;
    int page = 1;
    ApiClient apiClient = ApiUtills.getAPIService();
    ArrayList<HistoryModel> historyModelArrayList;


    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AccessToken = getArguments().getString("token");
        person_id = getArguments().getInt("person_id");
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        getEngagementData(AccessToken, person_id);
    }

    private void getEngagementData(String accessToken, int person_id) {
        historyModelArrayList = new ArrayList<>();
        apiClient.getPersonengagement(page, person_id, "Bearer " + accessToken, "application/json").enqueue(new Callback<EngagemmentHistoryModel>() {
            @Override
            public void onResponse(Call<EngagemmentHistoryModel> call, Response<EngagemmentHistoryModel> response) {
                try {
                    if (response.body() == null) {
                        int statusCode = response.raw().code();
                        if (statusCode > 399 && statusCode < 500) {
                            SessionLogout(getContext());
                        }
                    } else {
                        List<HistoryModel> getHistoryModelList = response.body().getData();
                        for (int i = 0; i < getHistoryModelList.size(); i++) {
                            HistoryModel historyModel = new HistoryModel();
                            historyModel.setAdded_by_name(getHistoryModelList.get(i).getAdded_by_name());
                            historyModel.setDetails(getHistoryModelList.get(i).getDetails());
                            historyModel.setLast_call(getHistoryModelList.get(i).getCreated_at());

                            historyModelArrayList.add(historyModel);
                        }
                        historyAdapter = new HistoryAdapter(getContext(), historyModelArrayList);
                        HistoryRecyclerView.setAdapter(historyAdapter);
                        historyAdapter.notifyDataSetChanged();


                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<EngagemmentHistoryModel> call, Throwable t) {

            }
        });
    }

    private void SessionLogout(Context context) {
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

    private void initViews(View view) {
        HistoryRecyclerView = view.findViewById(R.id.HistoryRecyclerView);
        HistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}
