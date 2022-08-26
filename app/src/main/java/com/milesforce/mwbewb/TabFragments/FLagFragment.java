package com.milesforce.mwbewb.TabFragments;


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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.milesforce.mwbewb.Activities.AddEngagementActivity;
import com.milesforce.mwbewb.Activities.EWBSRActivity;
import com.milesforce.mwbewb.Model.DelaysModel;
import com.milesforce.mwbewb.Model.UserToken;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;
import com.milesforce.mwbewb.Utils.AlertForMobileNumberInfo;
import com.milesforce.mwbewb.Utils.FlaggedAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.milesforce.mwbewb.Utils.ConstantUtills.UnTapped_url;

/**
 * A simple {@link Fragment} subclass.
 */
public class FLagFragment extends Fragment {
    RecyclerView flagRecyclerView;
    FlaggedAdapter flaggedAdapter;
    ArrayList<DelaysModel> flagDelaysModelArrayList;
    ApiClient apiClient;
    SwipeRefreshLayout flag_swipetorefresh;

    int page = 1;
    ProgressBar bottom_flagProgress, flag_progress;
    FloatingActionButton flating_btn, flag_refresh_floating;
    SharedPreferences sharedPreferences;
    Realm realm;
    UserToken userToken;
    String Access_token;


    public FLagFragment() {
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
        return inflater.inflate(R.layout.fragment_flag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        flagRecyclerView = view.findViewById(R.id.flagRecyclerView);
        flag_swipetorefresh = view.findViewById(R.id.flag_swipetorefresh);
        bottom_flagProgress = view.findViewById(R.id.bottom_flagProgress);
        flag_progress = view.findViewById(R.id.flag_progress);
        flating_btn = view.findViewById(R.id.flating_btn);
        flagRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        flag_refresh_floating = view.findViewById(R.id.flag_refresh_floating);

        flag_swipetorefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                flag_swipetorefresh.setRefreshing(false);
            }
        });
        paginatedRecyclerview(flagRecyclerView);
        flating_btn.setImageBitmap(textAsBitmap(String.valueOf(0), 40, Color.BLUE));
        flag_refresh_floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagDelaysModelArrayList.size() > 0) {
                    flagDelaysModelArrayList.clear();
                }
                getFlaggedData();
            }
        });
        getFlaggedData();

    }

    private void getFlaggedData() {
        flag_progress.setVisibility(View.VISIBLE);
        flagDelaysModelArrayList = new ArrayList<>();
        apiClient.getFlaggedData(1, "Bearer " + Access_token, "application/json").enqueue(new Callback<ModelForAllData>() {
            @Override
            public void onResponse(Call<ModelForAllData> call, Response<ModelForAllData> response) {
                try {
                    if (response.body() == null) {
                        int statusCode = response.raw().code();
                        if (statusCode == 401) {
                          //  SessionLogout(getContext());
                            flag_progress.setVisibility(View.GONE);
                        }
                    } else {
                        List<DelaysModel> untappedModelList = response.body().getDashboard_data().getData();
                        for (int i = 0; i < untappedModelList.size(); i++) {
                            DelaysModel untappedModel = new DelaysModel();
                            untappedModel.setId(untappedModelList.get(i).getId());
                            untappedModel.setCan_id(untappedModelList.get(i).getCan_id());
                            untappedModel.setPerson_name(untappedModelList.get(i).getPerson_name());
                            untappedModel.setPerson_id(untappedModelList.get(i).getPerson_id());
                            untappedModel.setLevel(untappedModelList.get(i).getLevel());
                            untappedModel.setCourses(untappedModelList.get(i).getCourses());
                            untappedModel.setMiles_type(untappedModelList.get(i).getMiles_type());
                            untappedModel.setCompany(untappedModelList.get(i).getCompany());
                            untappedModel.setDesignation(untappedModelList.get(i).getDesignation());
                            untappedModel.setExperience(untappedModelList.get(i).getExperience());
                            untappedModel.setEducation(untappedModelList.get(i).getEducation());
                            untappedModel.setCity(untappedModelList.get(i).getCity());
                            untappedModel.setEligibility(untappedModelList.get(i).getEligibility());
                            untappedModel.setSource(untappedModelList.get(i).getSource());
                            untappedModel.setUntapped(untappedModelList.get(i).getUntapped());
                            untappedModel.setLast_call(untappedModelList.get(i).getLast_call());
                            untappedModel.setNext_call(untappedModelList.get(i).getNext_call());
                            untappedModel.setSpoc_id(untappedModelList.get(i).getSpoc_id());
                            untappedModel.setSpoc_name(untappedModelList.get(i).getSpoc_name());
                            untappedModel.setCreated_at(untappedModelList.get(i).getCreated_at());
                            untappedModel.setUpdated_at(untappedModelList.get(i).getUpdated_at());
                            untappedModel.setEngagement_details(untappedModelList.get(i).getEngagement_details());
                            untappedModel.setDetails(untappedModelList.get(i).getEngagement_details());
                            untappedModel.setSubmitted_documents(untappedModelList.get(i).getSubmitted_documents());
                            untappedModel.setApplied_for_loan(untappedModelList.get(i).getApplied_for_loan());
                            untappedModel.setLoan_status(untappedModelList.get(i).getLoan_status());
                            untappedModel.setEducation_tags(untappedModelList.get(i).getEducation_tags());
                            untappedModel.setIiml_level(untappedModelList.get(i).getIiml_level());
                            if (untappedModelList.get(i).getCompany() != null) {
                                untappedModel.setCompany(untappedModelList.get(i).getCompany());
                            } else {
                                untappedModel.setCompany(" ");
                            }
                            if (untappedModelList.get(i).getSource() != null) {
                                untappedModel.setSource(untappedModelList.get(i).getSource());
                            } else {
                                untappedModel.setSource(" ");
                            }
                            untappedModel.setIdentity(untappedModelList.get(i).getIdentity());


                            flagDelaysModelArrayList.add(untappedModel);
                        }
                        flaggedAdapter = new FlaggedAdapter(getContext(), flagDelaysModelArrayList);
                        flagRecyclerView.setAdapter(flaggedAdapter);
                        flaggedAdapter.notifyDataSetChanged();
                        flating_btn.setImageBitmap(textAsBitmap(String.valueOf(response.body().getDashboard_data().getTotal()), 40, Color.BLUE));
                        flaggedAdapter.onItemSelectedListners(new FlaggedAdapter.onItemClickListner() {
                            @Override
                            public void onItemClickListner(View view, int position, String type) {
                                DelaysModel untappedModel = flagDelaysModelArrayList.get(position);
                                if (type.equals("mobile")) {
                                    AlertForMobileNumberInfo.getMobileNumberwithPersonId(untappedModel.getPerson_id(), getContext(), Access_token, untappedModel.getPerson_name());
                                } else {

                                    if (untappedModel.getLevel().equals("M7")){
                                        Intent intent = new Intent(getContext(), EWBSRActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("value", untappedModel);
                                        intent.putExtras(bundle);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }else {
                                        Intent intent = new Intent(getContext(), AddEngagementActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("value", untappedModel);
                                        intent.putExtras(bundle);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }

                                    /*Intent intent = new Intent(getContext(), AddEngagementActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("value", untappedModel);
                                    intent.putExtras(bundle);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);*/
                                }
                            }
                        });
                        flag_progress.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    flag_progress.setVisibility(View.GONE);
                    //  Toast.makeText(getContext(), String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ModelForAllData> call, Throwable t) {
                flag_progress.setVisibility(View.GONE);
                //   Toast.makeText(getContext(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void paginatedRecyclerview(RecyclerView tappedRecyclerView) {
        tappedRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            flaggedAdapter.getItemCount() - 1) {
                        page = page + 1;
                        bottom_flagProgress.setVisibility(View.VISIBLE);
                        apiClient.getFlaggedData(page, "Bearer " + Access_token, "application/json").enqueue(new Callback<ModelForAllData>() {
                            @Override
                            public void onResponse(Call<ModelForAllData> call, Response<ModelForAllData> response) {
                                try {
                                    if (response.body() == null) {
                                        int statusCode = response.raw().code();
                                        if (statusCode == 401) {
                                           // SessionLogout(getContext());
                                            flag_progress.setVisibility(View.GONE);
                                        }
                                    } else {
                                        List<DelaysModel> untappedModelList = response.body().getDashboard_data().getData();
                                        for (int i = 0; i < untappedModelList.size(); i++) {
                                            DelaysModel untappedModel = new DelaysModel();
                                            untappedModel.setId(untappedModelList.get(i).getId());
                                            untappedModel.setCan_id(untappedModelList.get(i).getCan_id());
                                            untappedModel.setPerson_name(untappedModelList.get(i).getPerson_name());
                                            untappedModel.setPerson_id(untappedModelList.get(i).getPerson_id());
                                            untappedModel.setLevel(untappedModelList.get(i).getLevel());
                                            untappedModel.setCourses(untappedModelList.get(i).getCourses());
                                            untappedModel.setMiles_type(untappedModelList.get(i).getMiles_type());
                                            untappedModel.setCompany(untappedModelList.get(i).getCompany());
                                            untappedModel.setDesignation(untappedModelList.get(i).getDesignation());
                                            untappedModel.setExperience(untappedModelList.get(i).getExperience());
                                            untappedModel.setEducation(untappedModelList.get(i).getEducation());
                                            untappedModel.setCity(untappedModelList.get(i).getCity());
                                            untappedModel.setEligibility(untappedModelList.get(i).getEligibility());
                                            untappedModel.setSource(untappedModelList.get(i).getSource());
                                            untappedModel.setUntapped(untappedModelList.get(i).getUntapped());
                                            untappedModel.setLast_call(untappedModelList.get(i).getLast_call());
                                            untappedModel.setNext_call(untappedModelList.get(i).getNext_call());
                                            untappedModel.setSpoc_id(untappedModelList.get(i).getSpoc_id());
                                            untappedModel.setSpoc_name(untappedModelList.get(i).getSpoc_name());
                                            untappedModel.setCreated_at(untappedModelList.get(i).getCreated_at());
                                            untappedModel.setUpdated_at(untappedModelList.get(i).getUpdated_at());
                                            untappedModel.setEngagement_details(untappedModelList.get(i).getEngagement_details());
                                            untappedModel.setSubmitted_documents(untappedModelList.get(i).getSubmitted_documents());
                                            untappedModel.setApplied_for_loan(untappedModelList.get(i).getApplied_for_loan());
                                            untappedModel.setLoan_status(untappedModelList.get(i).getLoan_status());
                                            untappedModel.setEducation_tags(untappedModelList.get(i).getEducation_tags());
                                            untappedModel.setIiml_level(untappedModelList.get(i).getIiml_level());
                                            if (untappedModelList.get(i).getCompany() != null) {
                                                untappedModel.setCompany(untappedModelList.get(i).getCompany());
                                            } else {
                                                untappedModel.setCompany(" ");
                                            }
                                            if (untappedModelList.get(i).getSource() != null) {
                                                untappedModel.setSource(untappedModelList.get(i).getSource());
                                            } else {
                                                untappedModel.setSource(" ");
                                            }
                                            untappedModel.setIdentity(untappedModelList.get(i).getIdentity());
                                            flagDelaysModelArrayList.add(untappedModel);
                                        }
                                        bottom_flagProgress.setVisibility(View.GONE);
                                        flaggedAdapter.notifyDataSetChanged();


                                    }
                                } catch (Exception e) {
                                    //  Toast.makeText(getContext(), String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
                                    bottom_flagProgress.setVisibility(View.GONE);
                                }

                            }

                            @Override
                            public void onFailure(Call<ModelForAllData> call, Throwable t) {
                                bottom_flagProgress.setVisibility(View.GONE);
                                // Toast.makeText(getContext(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                            }
                        });

                        //load more items code is here
                    }
                }

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
}
