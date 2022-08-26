package com.milesforce.mwbewb.TabFragments;


import android.content.Context;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.milesforce.mwbewb.Activities.AddEngagementActivity;
import com.milesforce.mwbewb.Activities.EWBSRActivity;
import com.milesforce.mwbewb.Activities.LoginActivity;
import com.milesforce.mwbewb.Model.DelaysModel;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;
import com.milesforce.mwbewb.Utils.AlertForMobileNumberInfo;
import com.milesforce.mwbewb.Utils.ConstantUtills;
import com.milesforce.mwbewb.Utils.EscalationAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.milesforce.mwbewb.Utils.ConstantUtills.Escalation_Url;
import static com.milesforce.mwbewb.Utils.ConstantUtills.SaveToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.SecondLevel_Url;
import static com.milesforce.mwbewb.Utils.ConstantUtills.fragment_token;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondLevelFragment extends Fragment {

    SharedPreferences sharedPreferences;
    ApiClient apiClient;
    String AccessToken;
    RecyclerView second_level_recyclerView;
    ProgressBar second_level_progress, bottom_second_levelProgress;
    FloatingActionButton flating_btn, second_level_refresh_floating;
    EscalationAdapter escalationAdapter;
    ArrayList<DelaysModel> escalationArrayList;
    int page = 1;
    SwipeRefreshLayout second_levelSwipetoRefresh;


    public SecondLevelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPreferences = getContext().getSharedPreferences(SaveToken, MODE_PRIVATE);
        apiClient = ApiUtills.getAPIService();
        AccessToken = getArguments().getString(fragment_token);
        return inflater.inflate(R.layout.fragment_second_level, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        getDelaysdata();
    }
    private void initView(View view) {
        second_level_recyclerView = view.findViewById(R.id.second_level_recyclerView);
        second_levelSwipetoRefresh =view.findViewById(R.id.second_levelSwipetoRefresh);
        second_levelSwipetoRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                second_levelSwipetoRefresh.setRefreshing(false);
            }
        });

        second_level_progress = view.findViewById(R.id.second_level_progress);
        flating_btn = view.findViewById(R.id.flating_btn);
        bottom_second_levelProgress = view.findViewById(R.id.bottom_second_levelProgress);
        second_level_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        paginatedRecyclerview(second_level_recyclerView);
        flating_btn.setImageBitmap(textAsBitmap(String.valueOf("0"), 40, Color.BLUE));
        second_level_refresh_floating = view.findViewById(R.id.second_level_refresh_floating);
        second_level_refresh_floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (escalationArrayList.size() > 0) {
                    escalationArrayList.clear();
                }
                getDelaysdata();
            }
        });

    }
    private void getDelaysdata() {
        second_level_progress.setVisibility(View.VISIBLE);
        escalationArrayList = new ArrayList<>();
        page = 1;
        apiClient.getAccadesData(SecondLevel_Url,1, "Bearer " + AccessToken, "application/json").enqueue(new Callback<ModelForAllData>() {
            @Override
            public void onResponse(Call<ModelForAllData> call, Response<ModelForAllData> response) {
                try {
                    if (response.body() == null) {
                        int statusCode = response.raw().code();
                        if (statusCode ==401) {
                            SessionLogout(getContext());
                            second_level_progress.setVisibility(View.GONE);
                        }
                    } else {

                        List<DelaysModel> delaysModelList = response.body().getDashboard_data().getData();
                        for (int i = 0; i < delaysModelList.size(); i++) {
                            DelaysModel delaysModel = new DelaysModel();
                            delaysModel.setId(delaysModelList.get(i).getId());
                            delaysModel.setCan_id(delaysModelList.get(i).getCan_id());
                            delaysModel.setPerson_id(delaysModelList.get(i).getPerson_id());
                            delaysModel.setPerson_name(delaysModelList.get(i).getPerson_name());
                            delaysModel.setAdded_by_id(delaysModelList.get(i).getAdded_by_id());
                            delaysModel.setAdded_by_name(delaysModelList.get(i).getAdded_by_name());
                            delaysModel.setAssigned_spoc_id(delaysModelList.get(i).getAssigned_spoc_id());
                            delaysModel.setAssigned_spoc_name(delaysModelList.get(i).getAssigned_spoc_name());
                            delaysModel.setEducation(delaysModelList.get(i).getEducation());
                            delaysModel.setLevel(delaysModelList.get(i).getLevel());
                            delaysModel.setCourses(delaysModelList.get(i).getCourses());
                            delaysModel.setDetails(delaysModelList.get(i).getEngagement_details());
                            delaysModel.setType(delaysModelList.get(i).getType());
                            delaysModel.setExperience(delaysModelList.get(i).getExperience());
                            delaysModel.setAcads(delaysModelList.get(i).getAcads());
                            delaysModel.setLast_call(delaysModelList.get(i).getLast_call());
                            delaysModel.setNext_call(delaysModelList.get(i).getNext_call());
                            delaysModel.setAddressed(delaysModelList.get(i).getAddressed());
                            delaysModel.setCreated_at(delaysModelList.get(i).getCreated_at());
                            delaysModel.setUpdated_at(delaysModelList.get(i).getUpdated_at());
                            delaysModel.setSubmitted_documents(delaysModelList.get(i).getSubmitted_documents());
                            delaysModel.setApplied_for_loan(delaysModelList.get(i).getApplied_for_loan());
                            delaysModel.setLoan_status(delaysModelList.get(i).getLoan_status());
                            delaysModel.setEducation_tags(delaysModelList.get(i).getEducation_tags());
                            if (delaysModelList.get(i).getCompany() != null) {
                                delaysModel.setCompany(delaysModelList.get(i).getCompany());
                            } else {
                                delaysModel.setCompany(" ");
                            }
                            if (delaysModelList.get(i).getSource() != null) {
                                delaysModel.setSource(delaysModelList.get(i).getSource());
                            } else {
                                delaysModel.setSource(" ");
                            }
                            delaysModel.setIdentity(delaysModelList.get(i).getIdentity());
                            escalationArrayList.add(delaysModel);
                        }
                        escalationAdapter = new EscalationAdapter(getContext(), escalationArrayList);
                        second_level_recyclerView.setAdapter(escalationAdapter);
                        escalationAdapter.notifyDataSetChanged();
                        flating_btn.setImageBitmap(textAsBitmap(String.valueOf(response.body().getDashboard_data().getTotal()), 40, Color.BLUE));

                        escalationAdapter.onItemClickListners(new EscalationAdapter.OnClickListner() {
                            @Override
                            public void itemSelected(View v, int position, String type) {
                                DelaysModel delaysModel = escalationArrayList.get(position);
                                if (type.equals("mobile")) {
                                    AlertForMobileNumberInfo.getMobileNumberwithPersonId(delaysModel.getPerson_id(), getContext(), AccessToken, delaysModel.getPerson_name());
                                } else {
                                    if (delaysModel.getLevel().equals("M7")){
                                        Intent intent = new Intent(getContext(), EWBSRActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("value", delaysModel);
                                        intent.putExtras(bundle);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }else {
                                        Intent intent = new Intent(getContext(), AddEngagementActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("value", delaysModel);
                                        intent.putExtras(bundle);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }

                                    /*Intent intent = new Intent(getContext(), AddEngagementActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("value", delaysModel);
                                    intent.putExtras(bundle);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);*/
                                }
                            }
                        });
                        second_level_progress.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    second_level_progress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ModelForAllData> call, Throwable t) {
                second_level_progress.setVisibility(View.GONE);
                Toast.makeText(getContext(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void SessionLogout(Context context) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(ConstantUtills.AccessToken);
        editor.apply();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
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
    private void paginatedRecyclerview(RecyclerView second_level_recyclerView) {
        second_level_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            escalationAdapter.getItemCount() - 1) {
                        page = page + 1;
                        bottom_second_levelProgress.setVisibility(View.VISIBLE);
                        apiClient.getAccadesData(SecondLevel_Url,page, "Bearer " + AccessToken, "application/json").enqueue(new Callback<ModelForAllData>() {
                            @Override
                            public void onResponse(Call<ModelForAllData> call, Response<ModelForAllData> response) {
                                try {

                                    ArrayList<DelaysModel> delaysModelList = response.body().getDashboard_data().getData();
                                    for (int i = 0; i < delaysModelList.size(); i++) {
                                        DelaysModel delaysModel = new DelaysModel();
                                        delaysModel.setId(delaysModelList.get(i).getId());
                                        delaysModel.setCan_id(delaysModelList.get(i).getCan_id());
                                        delaysModel.setPerson_id(delaysModelList.get(i).getPerson_id());
                                        delaysModel.setPerson_name(delaysModelList.get(i).getPerson_name());
                                        delaysModel.setAdded_by_id(delaysModelList.get(i).getAdded_by_id());
                                        delaysModel.setAdded_by_name(delaysModelList.get(i).getAdded_by_name());
                                        delaysModel.setAssigned_spoc_id(delaysModelList.get(i).getAssigned_spoc_id());
                                        delaysModel.setAssigned_spoc_name(delaysModelList.get(i).getAssigned_spoc_name());
                                        delaysModel.setLevel(delaysModelList.get(i).getLevel());
                                        delaysModel.setCourses(delaysModelList.get(i).getCourses());
                                        delaysModel.setDetails(delaysModelList.get(i).getEngagement_details());
                                        delaysModel.setType(delaysModelList.get(i).getType());
                                        delaysModel.setEducation(delaysModelList.get(i).getEducation());
                                        delaysModel.setAcads(delaysModelList.get(i).getAcads());
                                        delaysModel.setExperience(delaysModelList.get(i).getExperience());
                                        delaysModel.setLast_call(delaysModelList.get(i).getLast_call());
                                        delaysModel.setNext_call(delaysModelList.get(i).getNext_call());
                                        delaysModel.setAddressed(delaysModelList.get(i).getAddressed());
                                        delaysModel.setCreated_at(delaysModelList.get(i).getCreated_at());
                                        delaysModel.setUpdated_at(delaysModelList.get(i).getUpdated_at());
                                        delaysModel.setSubmitted_documents(delaysModelList.get(i).getSubmitted_documents());
                                        delaysModel.setApplied_for_loan(delaysModelList.get(i).getApplied_for_loan());
                                        delaysModel.setLoan_status(delaysModelList.get(i).getLoan_status());
                                        delaysModel.setEducation_tags(delaysModelList.get(i).getEducation_tags());
                                        if (delaysModelList.get(i).getCompany() != null) {
                                            delaysModel.setCompany(delaysModelList.get(i).getCompany());
                                        } else {
                                            delaysModel.setCompany(" ");
                                        }
                                        if (delaysModelList.get(i).getSource() != null) {
                                            delaysModel.setSource(delaysModelList.get(i).getSource());
                                        } else {
                                            delaysModel.setSource(" ");
                                        }
                                        delaysModel.setIdentity(delaysModelList.get(i).getIdentity());
                                        escalationArrayList.add(delaysModel);
                                    }
                                    escalationAdapter.notifyDataSetChanged();
                                    bottom_second_levelProgress.setVisibility(View.GONE);
                                } catch (Exception e) {
                                    bottom_second_levelProgress.setVisibility(View.GONE);
                                    Toast.makeText(getContext(), String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ModelForAllData> call, Throwable t) {
                                bottom_second_levelProgress.setVisibility(View.GONE);
                                Toast.makeText(getContext(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                            }
                        });


                        //load more items code is here
                    }
                }
            }
        });
    }
}
