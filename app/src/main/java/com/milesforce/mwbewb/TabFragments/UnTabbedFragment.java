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
import com.milesforce.mwbewb.Model.UnTappedLogModel;
import com.milesforce.mwbewb.Model.UntabbedModel;
import com.milesforce.mwbewb.Model.UntappedModel;
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

import static android.content.Context.MODE_PRIVATE;
import static com.milesforce.mwbewb.Utils.ConstantUtills.AccessToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.SaveToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.UnTapped_url;
import static com.milesforce.mwbewb.Utils.ConstantUtills.fragment_token;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnTabbedFragment extends Fragment {
    RecyclerView tappedRecyclerView;
    UntabbedAdapter untabbedAdapter;
    ArrayList<DelaysModel> untabbedModelArrayList;
    ApiClient apiClient;
    SwipeRefreshLayout untabbed_swipetorefresh;
    String AccessToken_new;
    int page = 1;
    ProgressBar bottom_waitingProgress, untabbed_progress;
    FloatingActionButton flating_btn, untapped_refresh_floating;
    SharedPreferences sharedPreferences;

    public UnTabbedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        apiClient = ApiUtills.getAPIService();
        sharedPreferences = getContext().getSharedPreferences(SaveToken, MODE_PRIVATE);
        AccessToken_new = getArguments().getString(fragment_token);
        return inflater.inflate(R.layout.fragment_un_tabbed, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intiviews(view);
        getUntabbedData();
    }

    private void getUntabbedData() {
        untabbed_progress.setVisibility(View.VISIBLE);
        untabbedModelArrayList = new ArrayList<>();
        apiClient.getUntappedData(UnTapped_url,1, "Bearer " + AccessToken_new, "application/json").enqueue(new Callback<ModelForAllData>() {
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
                            SessionLogout(getContext());
                            untabbed_progress.setVisibility(View.GONE);
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


                            untabbedModelArrayList.add(untappedModel);
                        }
                        untabbedAdapter = new UntabbedAdapter(getContext(), untabbedModelArrayList);
                        tappedRecyclerView.setAdapter(untabbedAdapter);
                        untabbedAdapter.notifyDataSetChanged();
                        flating_btn.setImageBitmap(textAsBitmap(String.valueOf(response.body().getDashboard_data().getTotal()), 40, Color.BLUE));
                        untabbedAdapter.onItemSelectedListners(new UntabbedAdapter.onItemClickListner() {
                            @Override
                            public void onItemClickListner(View view, int position, String type) {
                                DelaysModel untappedModel = untabbedModelArrayList.get(position);
                                if (type.equals("mobile")) {
                                    AlertForMobileNumberInfo.getMobileNumberwithPersonId(untappedModel.getPerson_id(), getContext(), AccessToken_new, untappedModel.getPerson_name());
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
                        untabbed_progress.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    untabbed_progress.setVisibility(View.GONE);
                    //  Toast.makeText(getContext(), String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ModelForAllData> call, Throwable t) {
                untabbed_progress.setVisibility(View.GONE);
                //   Toast.makeText(getContext(), String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
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

    private void intiviews(View view) {
        flating_btn = view.findViewById(R.id.flating_btn);
        untabbed_swipetorefresh = view.findViewById(R.id.untabbed_swipetorefresh);
        tappedRecyclerView = view.findViewById(R.id.tappedRecyclerView);
        bottom_waitingProgress = view.findViewById(R.id.bottom_untappedProgress);
        untabbed_progress = view.findViewById(R.id.untabbed_progress);
        untapped_refresh_floating = view.findViewById(R.id.untapped_refresh_floating);
        tappedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        untabbed_swipetorefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                untabbed_swipetorefresh.setRefreshing(false);
            }
        });

        paginatedRecyclerview(tappedRecyclerView);
        flating_btn.setImageBitmap(textAsBitmap(String.valueOf(0), 40, Color.BLUE));
        untapped_refresh_floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (untabbedModelArrayList.size() > 0) {
                    untabbedModelArrayList.clear();
                }
                getUntabbedData();
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
                            untabbedAdapter.getItemCount() - 1) {
                        page = page + 1;
                        bottom_waitingProgress.setVisibility(View.VISIBLE);
                        apiClient.getUntappedData(UnTapped_url,page, "Bearer " + AccessToken_new, "application/json").enqueue(new Callback<ModelForAllData>() {
                            @Override
                            public void onResponse(Call<ModelForAllData> call, Response<ModelForAllData> response) {
                                try {
                                    if (response.body() == null) {
                                        int statusCode = response.raw().code();
                                        if (statusCode == 401) {
                                            SessionLogout(getContext());
                                            untabbed_progress.setVisibility(View.GONE);
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
                                            untabbedModelArrayList.add(untappedModel);
                                        }
                                        bottom_waitingProgress.setVisibility(View.GONE);
                                        untabbedAdapter.notifyDataSetChanged();


                                    }
                                } catch (Exception e) {
                                    //  Toast.makeText(getContext(), String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
                                    bottom_waitingProgress.setVisibility(View.GONE);
                                }

                            }

                            @Override
                            public void onFailure(Call<ModelForAllData> call, Throwable t) {
                                bottom_waitingProgress.setVisibility(View.GONE);
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

