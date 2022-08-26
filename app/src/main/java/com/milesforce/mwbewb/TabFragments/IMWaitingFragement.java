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

import android.util.Log;
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
import com.milesforce.mwbewb.Model.WorkLogModel;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;
import com.milesforce.mwbewb.Utils.AlertForMobileNumberInfo;
import com.milesforce.mwbewb.Utils.ResumeWork;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

import static android.content.Context.MODE_PRIVATE;
import static com.milesforce.mwbewb.Utils.ConstantUtills.AccessToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.SaveToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.Waiting_url;
import static com.milesforce.mwbewb.Utils.ConstantUtills.fragment_token;

/**
 * A simple {@link Fragment} subclass.
 */
public class IMWaitingFragement extends Fragment {
    RecyclerView waitingRecyclerView;
    WaitingAdapter waitingAdapter;
    SwipeRefreshLayout waiting_swipeToRefresh;
    ArrayList<DelaysModel> delaysModelArrayList;
    ApiClient apiClient;
    String AccessToken_new;
    ProgressBar waiting_progress, bottom_waitingProgress;
    int page = 1;
    FloatingActionButton flating_btn, iamwiating_refresh_floating;
    SharedPreferences sharedPreferences;


    public IMWaitingFragement() {
        // Required empty public constructor
    }

    public static IMWaitingFragement newInstance() {
        IMWaitingFragement fragment = new IMWaitingFragement();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sharedPreferences = getContext().getSharedPreferences(SaveToken, MODE_PRIVATE);
        apiClient = ApiUtills.getAPIService();
        AccessToken_new = getArguments().getString(fragment_token);
        return inflater.inflate(R.layout.fragment_imwaiting_fragement, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        getImWaitingData();
    }

    private void getImWaitingData() {
        waiting_progress.setVisibility(View.VISIBLE);
        delaysModelArrayList = new ArrayList<>();
        apiClient.getImWaitingData(Waiting_url,1, "Bearer " + AccessToken_new, "").enqueue(new Callback<ModelForAllData>() {
            @Override
            public void onResponse(Call<ModelForAllData> call, Response<ModelForAllData> response) {
                try {
                    if(response.raw().code() == 515){
                        new ResumeWork().stResumeWork(getContext());
                        return;
                    }
                    final ArrayList<DelaysModel> delaysModelList = response.body().getDashboard_data().getData();
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
                        delaysModel.setLast_call(delaysModelList.get(i).getLast_call());
                        delaysModel.setNext_call(delaysModelList.get(i).getNext_call());
                        delaysModel.setAddressed(delaysModelList.get(i).getAddressed());
                        delaysModel.setCreated_at(delaysModelList.get(i).getCreated_at());
                        delaysModel.setUpdated_at(delaysModelList.get(i).getUpdated_at());
                        delaysModel.setExperience(delaysModelList.get(i).getExperience());
                        delaysModel.setSubmitted_documents(delaysModelList.get(i).getSubmitted_documents());
                        delaysModel.setApplied_for_loan(delaysModelList.get(i).getApplied_for_loan());
                        delaysModel.setLoan_status(delaysModelList.get(i).getLoan_status());
                        delaysModel.setEducation_tags(delaysModelList.get(i).getEducation_tags());
                        delaysModel.setIiml_level(delaysModelList.get(i).getIiml_level());
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
                        delaysModelArrayList.add(delaysModel);
                    }
                    waitingAdapter = new WaitingAdapter(getContext(), delaysModelArrayList);
                    waitingRecyclerView.setAdapter(waitingAdapter);
                    waitingAdapter.notifyDataSetChanged();
                    flating_btn.setImageBitmap(textAsBitmap(String.valueOf(response.body().getDashboard_data().getTotal()), 40, Color.BLUE));
                    waitingAdapter.onItemSelectedListners(new WaitingAdapter.onClickListners() {
                        @Override
                        public void onItemSelectedListner(View view, int position, String type) {
                            DelaysModel delaysModel = delaysModelArrayList.get(position);
                            if (type.equals("mobile")) {
                                AlertForMobileNumberInfo.getMobileNumberwithPersonId(delaysModel.getPerson_id(), getContext(), AccessToken_new, delaysModel.getPerson_name());
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
                    waiting_progress.setVisibility(View.GONE);

                } catch (Exception e) {
                    waiting_progress.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<ModelForAllData> call, Throwable t) {
                waiting_progress.setVisibility(View.GONE);
                //Toast.makeText(getContext(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initViews(View view) {
        waiting_swipeToRefresh = view.findViewById(R.id.waiting_swipeToRefresh);
        flating_btn = view.findViewById(R.id.flating_btn);
        waitingRecyclerView = view.findViewById(R.id.waitingRecyclerView);
        waitingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        waiting_progress = view.findViewById(R.id.waiting_progress);
        iamwiating_refresh_floating = view.findViewById(R.id.iamwiating_refresh_floating);
        bottom_waitingProgress = view.findViewById(R.id.bottom_waitingProgress);
        waiting_swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                waiting_swipeToRefresh.setRefreshing(false);
            }
        });
        paginatedRecyclerview(waitingRecyclerView);
        flating_btn.setImageBitmap(textAsBitmap(String.valueOf("0"), 40, Color.BLUE));
        iamwiating_refresh_floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (delaysModelArrayList.size() > 0) {
                    delaysModelArrayList.clear();
                }
                getImWaitingData();
            }
        });
    }

    private void paginatedRecyclerview(RecyclerView waitingRecyclerView) {
        waitingRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            waitingAdapter.getItemCount() - 1) {
                        page = page + 1;
                        bottom_waitingProgress.setVisibility(View.VISIBLE);
                        apiClient.getImWaitingData(Waiting_url,page, "Bearer " + AccessToken_new, "application/json").enqueue(new Callback<ModelForAllData>() {
                            @Override
                            public void onResponse(Call<ModelForAllData> call, Response<ModelForAllData> response) {
                                try {
                                    if (response.body() == null) {
                                        int statusCode = response.raw().code();
                                        if (statusCode == 401) {
                                            SessionLogout(getContext());
                                            bottom_waitingProgress.setVisibility(View.GONE);
                                        }
                                    } else {
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
                                            delaysModel.setExperience(delaysModelList.get(i).getExperience());
                                            delaysModel.setType(delaysModelList.get(i).getType());
                                            delaysModel.setEducation(delaysModelList.get(i).getEducation());
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
                                            delaysModel.setIiml_level(delaysModelList.get(i).getIiml_level());

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
                                            delaysModelArrayList.add(delaysModel);
                                        }
                                        waitingAdapter.notifyDataSetChanged();
                                        bottom_waitingProgress.setVisibility(View.GONE);

                                    }
                                } catch (Exception e) {
                                    bottom_waitingProgress.setVisibility(View.GONE);
                                    // Toast.makeText(getContext(), String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ModelForAllData> call, Throwable t) {
                                bottom_waitingProgress.setVisibility(View.GONE);
                                //    Toast.makeText(getContext(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                            }
                        });


                        //load more items code is here
                    }
                }

            }
        });
    }

    private void SessionLogout(Context context) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(AccessToken);
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
}
