package com.milesforce.mwbewb.Utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.milesforce.mwbewb.Model.MobileNumberModel;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlertForMobileNumebrsForWhatsup {
    static RecyclerView mobile_recycelr_view;
    static ApiClient apiClient = ApiUtills.getAPIService();
    static ArrayList<MobileNumberModel> mobileNumberModelArrayList;
    static WhatsAdapter mobileAdapter;
    static WindowManager.LayoutParams lp;
    static TextView person_dialog_name;

    public static void getMobileNumberwithPersonId(int id, Context context, String AccessToken, String personName) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.mobile_numer_layout);
        dialog.setCancelable(true);
        person_dialog_name = dialog.findViewById(R.id.person_dialog_name);
        person_dialog_name.setText(personName);
        lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        getMobileNumnbers(id, AccessToken, context, dialog);

        // dialog.show();

    }

    private static void getMobileNumnbers(int id, String accessToken, final Context context, final Dialog dialog) {
        mobileNumberModelArrayList = new ArrayList<>();
        apiClient.getClientMobileNumbers(id, "Bearer " + accessToken, "application/json").enqueue(new Callback<List<MobileNumberModel>>() {
            @Override
            public void onResponse(Call<List<MobileNumberModel>> call, Response<List<MobileNumberModel>> response) {
                try {
                    List<MobileNumberModel> mobileNumberModels = response.body();
                    for (int i = 0; i < mobileNumberModels.size(); i++) {
                        MobileNumberModel mobileNumberModel = new MobileNumberModel();
                        mobileNumberModel.setMasked_number(mobileNumberModels.get(i).getMasked_number());
                        mobileNumberModel.setPhone_number(mobileNumberModels.get(i).getPhone_number());
                        mobileNumberModel.setId(mobileNumberModels.get(i).getId());
                        mobileNumberModelArrayList.add(mobileNumberModel);
                    }
                    mobile_recycelr_view = dialog.findViewById(R.id.mobile_recycelr_view);
                    mobile_recycelr_view.setLayoutManager(new LinearLayoutManager(context));
                    mobileAdapter = new WhatsAdapter(context, mobileNumberModelArrayList);
                    mobile_recycelr_view.setAdapter(mobileAdapter);
                    mobileAdapter.notifyDataSetChanged();
                    if (mobileNumberModelArrayList.size() > 0) {
                        dialog.show();
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.getWindow().setAttributes(lp);
                    } else {
                        Toast.makeText(context, "No Mobile Number found this client ", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<MobileNumberModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
