package com.milesforce.mwbewb.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.milesforce.mwbewb.Model.DelaysModel;
import com.milesforce.mwbewb.Model.MobileSearchModel;
import com.milesforce.mwbewb.Model.WorkLogModel;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.milesforce.mwbewb.Utils.ConstantUtills.AccessToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.SaveToken;

public class SearchClient extends AppCompatActivity implements View.OnClickListener {
    AppCompatImageButton bt_menu_back_tomain;
    SharedPreferences sharedPreferences;
    String accessToken;
    SwipeRefreshLayout searchview_client_refresh;
    RecyclerView search_recyclerview;
    ProgressBar search_progress, search_progress_main_progress;
    ApiClient apiClient = ApiUtills.getAPIService();
    int page_number = 1;
    ArrayList<DelaysModel> delaysModelArrayList;
    SearchAdapter searchAdapter;
    TextView person_name_title_search;
    EditText et_search_clients, et_search_clients_with_mobile;
    ConstraintLayout snake_bar_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_client);
        sharedPreferences = getApplicationContext().getSharedPreferences(SaveToken, MODE_PRIVATE);
        accessToken = sharedPreferences.getString(AccessToken, null);
        bt_menu_back_tomain = findViewById(R.id.bt_menu_back_tomain);
        bt_menu_back_tomain.setOnClickListener(this);
        searchview_client_refresh = findViewById(R.id.searchview_client_refresh);
        search_recyclerview = findViewById(R.id.search_recyclerview);
        search_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        search_progress = findViewById(R.id.search_progress);
        person_name_title_search = findViewById(R.id.person_name_title_search);
        et_search_clients = findViewById(R.id.et_search_clients);
        search_progress_main_progress = findViewById(R.id.search_progress_main_progress);
        et_search_clients_with_mobile = findViewById(R.id.et_search_clients_with_mobile);
        snake_bar_search = findViewById(R.id.snake_bar_search);
        et_search_clients_with_mobile.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                et_search_clients.clearFocus();
                et_search_clients.getText().clear();
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (et_search_clients_with_mobile.getText().length() > 7) {
                        hideKeyboard();
                        if (delaysModelArrayList != null) {
                            delaysModelArrayList.clear();
                        }
                        getSearchedUSerDataWithMobile(accessToken, v.getText().toString());
                        return true;
                    } else {
                        if (delaysModelArrayList != null) {
                            delaysModelArrayList.clear();
                        }
                        showSnakeBarForSearch("Please Enter at least 8 digits");
                    }
                }
                return false;
            }
        });

        // getPaginationInSearchView();

        et_search_clients.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                et_search_clients_with_mobile.clearFocus();
                et_search_clients_with_mobile.getText().clear();
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (et_search_clients.getText().length() > 3) {
                        hideKeyboard();
                        if (delaysModelArrayList != null) {
                            delaysModelArrayList.clear();
                        }
                        getSearchedUSerData(accessToken, v.getText().toString());
                        return true;
                    } else {
                        showSnakeBarForSearch("Please enter at least 3 char..");
                    }

                }
                return false;
            }
        });

        searchview_client_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchview_client_refresh.setRefreshing(false);
                // getSearchedUSerData(accessToken, page_number);
            }
        });
    }

    private void getSearchedUSerDataWithMobile(String accessToken, String person_name) {
        search_progress_main_progress.setVisibility(View.VISIBLE);
        delaysModelArrayList = new ArrayList<>();
        apiClient.getSearchedLeadsWithMobileNumber(person_name, "Bearer " + accessToken, "application/json").enqueue(new Callback<MobileSearchModel>() {
            @Override
            public void onResponse(Call<MobileSearchModel> call, Response<MobileSearchModel> response) {
                try {
                    if (response.body() == null) {
                        int statusCode = response.raw().code();
                        if (statusCode > 399 && statusCode < 500) {
                            SessionLogout();
                            search_progress_main_progress.setVisibility(View.GONE);
                        }
                    } else {
                        if (response.body().getStatus().equals("success")) {
                            List<DelaysModel> getdelaysModel = response.body().getDelaysModel();
                            for (int i = 0; i < getdelaysModel.size(); i++) {
                                DelaysModel delaysModel = new DelaysModel();
                                delaysModel.setId(getdelaysModel.get(i).getId());
                                delaysModel.setCan_id(getdelaysModel.get(i).getCan_id());
                                delaysModel.setPerson_id(getdelaysModel.get(i).getPerson_id());
                                delaysModel.setPerson_name(getdelaysModel.get(i).getPerson_name());
                                delaysModel.setCourses(getdelaysModel.get(i).getCourses());
                                delaysModel.setLevel(getdelaysModel.get(i).getLevel());
                                delaysModel.setDetails(getdelaysModel.get(i).getEngagement_details());
                                delaysModel.setCompany(getdelaysModel.get(i).getCompany());
                                delaysModel.setEducation(getdelaysModel.get(i).getEducation());
                                delaysModel.setExperience(getdelaysModel.get(i).getExperience());
                                delaysModel.setDesignation(getdelaysModel.get(i).getDesignation());
                                delaysModel.setSubmitted_documents(getdelaysModel.get(i).getSubmitted_documents());
                                delaysModel.setApplied_for_loan(getdelaysModel.get(i).getApplied_for_loan());
                                delaysModel.setLoan_status(getdelaysModel.get(i).getLoan_status());
                                delaysModel.setEducation_tags(getdelaysModel.get(i).getEducation_tags());
                                delaysModel.setIiml_level(getdelaysModel.get(i).getIiml_level());
                                if (getdelaysModel.get(i).getCompany() != null) {
                                    delaysModel.setCompany(getdelaysModel.get(i).getCompany());
                                } else {
                                    delaysModel.setCompany(" ");
                                }
                                if (getdelaysModel.get(i).getSource() != null) {
                                    delaysModel.setSource(getdelaysModel.get(i).getSource());
                                } else {
                                    delaysModel.setSource(" ");
                                }
                                delaysModel.setIdentity(getdelaysModel.get(i).getIdentity());
                                delaysModelArrayList.add(delaysModel);
                            }
                            search_progress_main_progress.setVisibility(View.GONE);
                            searchAdapter = new SearchAdapter(getApplicationContext(), delaysModelArrayList);
                            search_recyclerview.setAdapter(searchAdapter);
                            searchAdapter.notifyDataSetChanged();
                            searchview_client_refresh.setRefreshing(false);
                            // person_name_title_search.setText(String.valueOf("Total clients : " + response.body().getTotal()));
                            searchAdapter.onItemSelectedListners(new SearchAdapter.OnItemClickListners() {
                                @Override
                                public void onItemClickedListners(View view, int posi) {
                                    DelaysModel delaysModel = delaysModelArrayList.get(posi);
                                    if (delaysModel.getLevel().equals("M7")){
                                        Intent intent = new Intent(getApplicationContext(), EWBSRActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("value", delaysModel);
                                        intent.putExtras(bundle);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }else {
                                        Intent intent = new Intent(getApplicationContext(), AddEngagementActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("value", delaysModel);
                                        intent.putExtras(bundle);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
//                                    Intent intent = new Intent(getApplicationContext(), AddEngagementActivity.class);
//                                    Bundle bundle = new Bundle();
//                                    bundle.putSerializable("value", delaysModel);
//                                    intent.putExtras(bundle);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    startActivity(intent);
                                }
                            });
                            if (delaysModelArrayList.size() == 0) {
                                search_progress_main_progress.setVisibility(View.GONE);
                                showSnakeBarForSearch("No Results found with this person name");
                            }

                        } else {
                            showSnakeBarForSearch(response.body().getMessage());
                            search_progress_main_progress.setVisibility(View.GONE);
                        }
                    }
                } catch (Exception e) {
                    search_progress_main_progress.setVisibility(View.GONE);
                    showSnakeBarForSearch(e.getMessage());
                    searchview_client_refresh.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<MobileSearchModel> call, Throwable t) {
                search_progress_main_progress.setVisibility(View.GONE);
                showSnakeBarForSearch(t.getMessage());
                searchview_client_refresh.setRefreshing(false);

            }
        });
    }

    /*private void getPaginationInSearchView() {
        search_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (dx == 0 && dy == 0) {

                } else {
                    if (Objects.requireNonNull(linearLayoutManager).findLastCompletelyVisibleItemPosition() ==
                            searchAdapter.getItemCount() - 1) {
                        page_number = page_number + 1;
                        search_progress.setVisibility(View.VISIBLE);
                        apiClient.getSearchedLeads(page_number, "Bearer " + accessToken, "application/json").enqueue(new Callback<WorkLogModel>() {
                            @Override
                            public void onResponse(Call<WorkLogModel> call, Response<WorkLogModel> response) {
                                try {
                                    ArrayList<DelaysModel> getdelaysModel = response.body().getData();
                                    for (int i = 0; i < getdelaysModel.size(); i++) {
                                        DelaysModel delaysModel = new DelaysModel();
                                        delaysModel.setCan_id(getdelaysModel.get(i).getCan_id());
                                        delaysModel.setPerson_id(getdelaysModel.get(i).getPerson_id());
                                        delaysModel.setPerson_name(getdelaysModel.get(i).getPerson_name());
                                        delaysModel.setCourses(getdelaysModel.get(i).getCourses());
                                        delaysModel.setLevel(getdelaysModel.get(i).getLevel());
                                        delaysModel.setDetails(getdelaysModel.get(i).getEngagement_details());
                                        delaysModel.setCompany(getdelaysModel.get(i).getCompany());
                                        delaysModel.setDesignation(getdelaysModel.get(i).getDesignation());
                                        delaysModelArrayList.add(delaysModel);
                                    }
                                    searchAdapter.notifyDataSetChanged();
                                    search_progress.setVisibility(View.GONE);
                                } catch (Exception e) {
                                    search_progress.setVisibility(View.GONE);
                                    Toast.makeText(SearchClient.this, String.valueOf(e.getMessage()), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<WorkLogModel> call, Throwable t) {
                                Toast.makeText(SearchClient.this, String.valueOf(t.getMessage()), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }*/

    private void getSearchedUSerData(String accessToken, String person_name) {
        search_progress_main_progress.setVisibility(View.VISIBLE);
        delaysModelArrayList = new ArrayList<>();
        apiClient.getSearchedLeadsWithName(person_name, "Bearer " + accessToken, "application/json").enqueue(new Callback<List<DelaysModel>>() {
            @Override
            public void onResponse(Call<List<DelaysModel>> call, Response<List<DelaysModel>> response) {
                try {
                    if (response.body() == null) {
                        int statusCode = response.raw().code();
                        if (statusCode > 399 && statusCode < 500) {
                            SessionLogout();
                            search_progress_main_progress.setVisibility(View.GONE);
                        }
                    } else {
                        List<DelaysModel> getdelaysModel = response.body();
                        Log.d("venkat_IIMl",String.valueOf(getdelaysModel));
                        for (int i = 0; i < getdelaysModel.size(); i++) {
                            try {
                                if(getdelaysModel.get(i) != null && getdelaysModel.get(i).getPerson_name() != null){
                                    DelaysModel delaysModel = new DelaysModel();
                                    delaysModel.setId(getdelaysModel.get(i).getId());
                                    delaysModel.setCan_id(getdelaysModel.get(i).getCan_id());
                                    delaysModel.setPerson_id(getdelaysModel.get(i).getPerson_id());
                                    delaysModel.setPerson_name(getdelaysModel.get(i).getPerson_name());
                                    delaysModel.setCourses(getdelaysModel.get(i).getCourses());
                                    delaysModel.setLevel(getdelaysModel.get(i).getLevel());
                                    delaysModel.setDetails(getdelaysModel.get(i).getEngagement_details());
                                    delaysModel.setCompany(getdelaysModel.get(i).getCompany());
                                    delaysModel.setEducation(getdelaysModel.get(i).getEducation());
                                    delaysModel.setExperience(getdelaysModel.get(i).getExperience());
                                    delaysModel.setDesignation(getdelaysModel.get(i).getDesignation());
                                    delaysModel.setSubmitted_documents(getdelaysModel.get(i).getSubmitted_documents());
                                    delaysModel.setApplied_for_loan(getdelaysModel.get(i).getApplied_for_loan());
                                    delaysModel.setLoan_status(getdelaysModel.get(i).getLoan_status());
                                    delaysModel.setEducation_tags(getdelaysModel.get(i).getEducation_tags());
                                    delaysModel.setIiml_level(getdelaysModel.get(i).getIiml_level());
                                    if (getdelaysModel.get(i).getCompany() != null) {
                                        delaysModel.setCompany(getdelaysModel.get(i).getCompany());
                                    } else {
                                        delaysModel.setCompany(" ");
                                    }
                                    if (getdelaysModel.get(i).getSource() != null) {
                                        delaysModel.setSource(getdelaysModel.get(i).getSource());
                                    } else {
                                        delaysModel.setSource(" ");
                                    }
                                    delaysModel.setIdentity(getdelaysModel.get(i).getIdentity());
                                    delaysModelArrayList.add(delaysModel);
                                }
                            }catch (Exception e){
                                Log.e("Null pointer", "Position at: "+i);
                            }


                        }
                        search_progress_main_progress.setVisibility(View.GONE);
                        searchAdapter = new SearchAdapter(getApplicationContext(), delaysModelArrayList);
                        search_recyclerview.setAdapter(searchAdapter);
                        searchAdapter.notifyDataSetChanged();
                        searchview_client_refresh.setRefreshing(false);
                        // person_name_title_search.setText(String.valueOf("Total clients : " + response.body().getTotal()));
                        searchAdapter.onItemSelectedListners(new SearchAdapter.OnItemClickListners() {
                            @Override
                            public void onItemClickedListners(View view, int posi) {
                                DelaysModel delaysModel = delaysModelArrayList.get(posi);
                                if (delaysModel.getLevel().equals("M7")){
                                    Intent intent = new Intent(getApplicationContext(), EWBSRActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("value", delaysModel);
                                    intent.putExtras(bundle);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }else {
                                    Intent intent = new Intent(getApplicationContext(), AddEngagementActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("value", delaysModel);
                                    intent.putExtras(bundle);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);

                                }

                             /*   Intent intent = new Intent(getApplicationContext(), AddEngagementActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("value", delaysModel);
                                intent.putExtras(bundle);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);*/

                            }
                        });

                        if (delaysModelArrayList.size() == 0) {
                            showSnakeBarForSearch("No Results found with this person name");
                        }

                    }
                } catch (Exception e) {
                    search_progress_main_progress.setVisibility(View.GONE);
                    showSnakeBarForSearch(e.getMessage());
                    searchview_client_refresh.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<DelaysModel>> call, Throwable t) {
                search_progress_main_progress.setVisibility(View.GONE);
                showSnakeBarForSearch(t.getMessage());
                searchview_client_refresh.setRefreshing(false);

            }
        });
    }

    private void SessionLogout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(AccessToken);
        editor.apply();
        startActivity(new Intent(SearchClient.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_menu_back_tomain:
                finish();
                break;
        }

    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showSnakeBarForSearch(String message) {
        Snackbar snackbar = Snackbar
                .make(snake_bar_search, String.valueOf(message), Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
