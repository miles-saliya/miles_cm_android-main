package com.milesforce.mwbewb.EngagementFragments;

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

import com.milesforce.mwbewb.Model.EmailModel;
import com.milesforce.mwbewb.Model.MobileNumberModel;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.Retrofit.ApiClient;
import com.milesforce.mwbewb.Retrofit.ApiUtills;
import com.milesforce.mwbewb.Utils.EmailFormClass;
import com.milesforce.mwbewb.Utils.WhatsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class AlertForEmails {
    static RecyclerView mobile_recycelr_view;
    static ApiClient apiClient = ApiUtills.getAPIService();
    static ArrayList<EmailModel> emailModelArrayList;
    static EMailInfoAdapter eMailInfoAdapter;
    static WindowManager.LayoutParams lp;
    static TextView person_dialog_name;
    static String AccessTokenForSendEmails;
    static Context context;

    public void getEmailsWithPersonId(int person_id, Context context, String accessToken, String user_name) {
        this.context = context;
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.emailadd_layout_emailshow);
        dialog.setCancelable(true);
        person_dialog_name = dialog.findViewById(R.id.person_dialog_name);
        person_dialog_name.setText(user_name);
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
        getMobileNumnbers(person_id, accessToken, context, dialog);

        // dialog.show();
    }

    private static void getMobileNumnbers(int id, String accessToken, final Context context, final Dialog dialog) {
        emailModelArrayList = new ArrayList<>();
        apiClient.getUserEmails(id, "Bearer " + accessToken, "application/json").enqueue(new Callback<List<EmailModel>>() {
            @Override
            public void onResponse(Call<List<EmailModel>> call, Response<List<EmailModel>> response) {
                try {
                    List<EmailModel> emailsModels = response.body();
                    for (int i = 0; i < emailsModels.size(); i++) {
                        EmailModel mobileNumberModel = new EmailModel();
                        mobileNumberModel.setMasked_email(emailsModels.get(i).getMasked_email());
                        mobileNumberModel.setEmail(emailsModels.get(i).getEmail());
                        mobileNumberModel.setId(emailsModels.get(i).getId());
                        emailModelArrayList.add(mobileNumberModel);
                    }
                    mobile_recycelr_view = dialog.findViewById(R.id.mobile_recycelr_view);
                    mobile_recycelr_view.setLayoutManager(new LinearLayoutManager(context));
                    eMailInfoAdapter = new EMailInfoAdapter(context, emailModelArrayList);
                    mobile_recycelr_view.setAdapter(eMailInfoAdapter);
                    eMailInfoAdapter.notifyDataSetChanged();
                    if (emailModelArrayList.size() > 0) {
                        dialog.show();
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.getWindow().setAttributes(lp);
                    } else {
                        Toast.makeText(context, "No Emails found this client ", Toast.LENGTH_SHORT).show();
                    }
                    eMailInfoAdapter.OnItemSelectedListners(new EMailInfoAdapter.OnClickListeners() {
                        @Override
                        public void itemSelected(View v, int position, String type) {
                            EmailModel emailModel = emailModelArrayList.get(position);
                            Toast.makeText(context, emailModel.getEmail(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            new EmailFormClass().SendNewEMailsToClient(emailModel.getId(), emailModel.getEmail(), AccessTokenForSendEmails, context);
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<EmailModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
