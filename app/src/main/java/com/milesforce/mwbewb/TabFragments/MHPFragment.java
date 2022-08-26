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
import com.milesforce.mwbewb.Utils.MHPAdapter;
import com.milesforce.mwbewb.Utils.ResumeWork;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.milesforce.mwbewb.Utils.ConstantUtills.MHP_URL;

/**
 * A simple {@link Fragment} subclass.
 */
public class MHPFragment extends Fragment {
    RecyclerView mhpRecyclerView;
    MHPAdapter mhpadapter;
    ArrayList<DelaysModel> mhpDelaysModelArrayList;
    ApiClient apiClient;
    SwipeRefreshLayout mhp_swipetorefresh;

    int page = 1;
    ProgressBar bottom_mhpProgress, mhp_progress;
    FloatingActionButton flating_btn, mhp_refresh_floating;
    SharedPreferences sharedPreferences;
    Realm realm;
    UserToken userToken;
    String Access_token;


    public MHPFragment() {
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
        return inflater.inflate(R.layout.fragment_mh, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mhpRecyclerView = view.findViewById(R.id.mhpRecyclerView);
        mhp_swipetorefresh = view.findViewById(R.id.mhp_swipetorefresh);
        bottom_mhpProgress = view.findViewById(R.id.bottom_mhpProgress);
        mhp_progress = view.findViewById(R.id.mhp_progress);
        flating_btn = view.findViewById(R.id.flating_btn);
        mhpRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mhp_refresh_floating = view.findViewById(R.id.mhp_refresh_floating);

        mhp_swipetorefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mhp_swipetorefresh.setRefreshing(false);
            }
        });
       paginatedRecyclerview(mhpRecyclerView);
        flating_btn.setImageBitmap(textAsBitmap(String.valueOf(0), 40, Color.BLUE));
        mhp_refresh_floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mhpDelaysModelArrayList.size() > 0) {
                    mhpDelaysModelArrayList.clear();
                }
                getMHPData();
            }
        });
        getMHPData();
    }

    private void getMHPData() {
        mhp_progress.setVisibility(View.VISIBLE);
        mhpDelaysModelArrayList = new ArrayList<>();
        apiClient.getMHPData(MHP_URL,1, "Bearer " + Access_token, "application/json").enqueue(new Callback<ModelForAllData>() {
            @Override
            public void onResponse(Call<ModelForAllData> call, Response<ModelForAllData> response) {
                try {
                    if(response.raw().code() == 515){
                        new ResumeWork().stResumeWork(getContext());
                        return;
                    }
                    if (response.body() == null) {
                        int statusCode = response.raw().code();
                        if (statusCode == 401) {
                            //  SessionLogout(getContext());
                            mhp_progress.setVisibility(View.GONE);
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


                            mhpDelaysModelArrayList.add(untappedModel);
                        }
                        mhpadapter = new MHPAdapter(getContext(), mhpDelaysModelArrayList);
                        mhpRecyclerView.setAdapter(mhpadapter);
                        mhpadapter.notifyDataSetChanged();

                        flating_btn.setImageBitmap(textAsBitmap(String.valueOf(response.body().getDashboard_data().getTotal()), 40, Color.BLUE));

                      //  flating_btn.setImageBitmap(textAsBitmap(String.valueOf(mhpDelaysModelArrayList.size()), 40, Color.BLUE));

                        mhpadapter.onItemSelectedListners(new MHPAdapter.onItemClickListner() {
                            @Override
                            public void onItemClickListner(View view, int position, String type) {
                                DelaysModel untappedModel = mhpDelaysModelArrayList.get(position);
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
                        mhp_progress.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    mhp_progress.setVisibility(View.GONE);
                    //  Toast.makeText(getContext(), String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ModelForAllData> call, Throwable t) {
                mhp_progress.setVisibility(View.GONE);
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
                            mhpadapter.getItemCount() - 1) {
                        page = page + 1;
                        bottom_mhpProgress.setVisibility(View.VISIBLE);
                        apiClient.getMHPData(MHP_URL,page, "Bearer " + Access_token, "application/json").enqueue(new Callback<ModelForAllData>() {
                            @Override
                            public void onResponse(Call<ModelForAllData> call, Response<ModelForAllData> response) {
                                try {
                                    if (response.body() == null) {
                                        int statusCode = response.raw().code();
                                        if (statusCode == 401) {
                                            // SessionLogout(getContext());
                                            mhp_progress.setVisibility(View.GONE);
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
                                            mhpDelaysModelArrayList.add(untappedModel);
                                        }
                                        bottom_mhpProgress.setVisibility(View.GONE);
                                        mhpadapter.notifyDataSetChanged();


                                    }
                                } catch (Exception e) {
                                    //  Toast.makeText(getContext(), String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
                                    bottom_mhpProgress.setVisibility(View.GONE);
                                }

                            }

                            @Override
                            public void onFailure(Call<ModelForAllData> call, Throwable t) {
                                bottom_mhpProgress.setVisibility(View.GONE);
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
