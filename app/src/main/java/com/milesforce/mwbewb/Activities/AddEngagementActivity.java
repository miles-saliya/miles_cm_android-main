package com.milesforce.mwbewb.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.milesforce.mwbewb.EngagementFragments.AddNewEngagement;
import com.milesforce.mwbewb.EngagementFragments.HistoryFragment;
import com.milesforce.mwbewb.EngagementFragments.UserInfoFragment;
import com.milesforce.mwbewb.IIML.IIMLNewEngagement;
import com.milesforce.mwbewb.Model.DelaysModel;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.TabFragments.MissedCallsFragments;
import com.milesforce.mwbewb.TabFragments.ToUpdateFragment;
import com.milesforce.mwbewb.TabFragments.UntrackedFragment;
import com.milesforce.mwbewb.Utils.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.milesforce.mwbewb.Utils.ConstantUtills.AccessToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.IIML_TAB_CHANGE_CODE;
import static com.milesforce.mwbewb.Utils.ConstantUtills.SaveToken;

public class AddEngagementActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager view_pager_engagement;
    private TabLayout tab_layoutEngagementform;
    AppCompatImageButton bt_menu_back;
    DelaysModel delaysModel;
    ArrayList<DelaysModel> delaysModelArrayList;
    SharedPreferences sharedPreferences;
    String accessToken;
    TextView person_name_title;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_engagement);
        try {
            delaysModelArrayList = new ArrayList<>();
            delaysModel = (DelaysModel) getIntent().getExtras().getSerializable("value");
            delaysModelArrayList.add(delaysModel);
        } catch (Exception e) {

        }

        sharedPreferences = getApplicationContext().getSharedPreferences(SaveToken, MODE_PRIVATE);
        accessToken = sharedPreferences.getString(AccessToken, null);
        initviews();
        Tools.setSystemBarColor(this);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initviews() {
        person_name_title = findViewById(R.id.person_name_title);
        view_pager_engagement = findViewById(R.id.view_pager_engagement);
        setupViewPager(view_pager_engagement);
        bt_menu_back = findViewById(R.id.bt_menu_back);
        bt_menu_back.setOnClickListener(this);
        tab_layoutEngagementform = findViewById(R.id.tab_layoutEngagementform);
        tab_layoutEngagementform.setupWithViewPager(view_pager_engagement);
        setupTabIcons();
        tab_layoutEngagementform.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view_pager_engagement.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        person_name_title.setText(delaysModel.getPerson_name());
    }

    private void setupViewPager(ViewPager viewPager) {

        UserInfoFragment userInfo = new UserInfoFragment();
        Bundle args2 = new Bundle();
        args2.putInt("person_id", delaysModel.getPerson_id());
        args2.putString("person_name", delaysModel.getPerson_name());
        args2.putString("courses", delaysModel.getCourses());
        args2.putString("token", accessToken);
        args2.putInt("can_id", delaysModel.getCan_id());
        args2.putString("company", delaysModel.getCompany());
        args2.putString("designation", delaysModel.getDesignation());
        args2.putString("experiance", delaysModel.getExperience());
        args2.putInt("id", delaysModel.getId());
        args2.putString("documents_submitted", delaysModel.getSubmitted_documents());
        args2.putString("education", delaysModel.getEducation());
        args2.putInt("applied_for_loan", delaysModel.getApplied_for_loan());
        args2.putString("loan_status", delaysModel.getLoan_status());
        args2.putString("education_tags", delaysModel.getEducation_tags());
        args2.putString("source", delaysModel.getSource());
        args2.putString("identity", delaysModel.getIdentity());
        args2.putString("level", delaysModel.getLevel());
        args2.putString("'iiml_level'",delaysModel.getIiml_level());
        userInfo.setArguments(args2);

        /* company = getArguments().getString("company");
        designation = getArguments().getString("designation");
        experiance = getArguments().getString("experiance");*/

        HistoryFragment historyFragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putInt("person_id", delaysModel.getPerson_id());
        args.putString("token", accessToken);
        historyFragment.setArguments(args);

        AddNewEngagement addNewEngagement = new AddNewEngagement();
        Bundle args1 = new Bundle();
        args1.putInt("person_id", delaysModel.getPerson_id());
        args1.putString("token", accessToken);
        args1.putString("previousEngagement", delaysModel.getDetails());
        args1.putString("courses", delaysModel.getCourses());
        args1.putString("levels", delaysModel.getLevel());
        args1.putInt("id", delaysModel.getId());
        args1.putString("iiml_level", delaysModel.getIiml_level());
        args1.putString("user_name", delaysModel.getPerson_name());
        args1.putInt("can_id", delaysModel.getCan_id());
        args1.putString("phone_number", delaysModel.getPhone_number());
        addNewEngagement.setArguments(args1);


        IIMLNewEngagement iimlNewEngagement = new IIMLNewEngagement();
        Bundle iiml_bundle = new Bundle();
        iiml_bundle.putInt("person_id", delaysModel.getPerson_id());
        iiml_bundle.putString("token", accessToken);
        iiml_bundle.putString("previousEngagement", delaysModel.getDetails());
        iiml_bundle.putString("courses", delaysModel.getCourses());
        iiml_bundle.putString("levels", delaysModel.getLevel());
        iiml_bundle.putString("'iiml_level'",delaysModel.getIiml_level());
        iiml_bundle.putString("iimllevels", delaysModel.getIiml_level());
        iiml_bundle.putString("user_name", delaysModel.getPerson_name());
        iiml_bundle.putInt("can_id", delaysModel.getCan_id());
        iiml_bundle.putString("phone_number", delaysModel.getPhone_number());
        iimlNewEngagement.setArguments(iiml_bundle);













        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        if(IIML_TAB_CHANGE_CODE == 0){
            adapter.addFragment(userInfo, "Info");
            adapter.addFragment(historyFragment, "History");
            adapter.addFragment(addNewEngagement, "Add New");
        }
        if (IIML_TAB_CHANGE_CODE == 1){
            adapter.addFragment(userInfo, "Info");
            adapter.addFragment(historyFragment, "History");
            adapter.addFragment(iimlNewEngagement, "Add New");
        }
        viewPager.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_menu_back:
                /*Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);*/
                finish();
                // finishAffinity();
                break;

        }
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setupTabIcons() {
        Objects.requireNonNull(tab_layoutEngagementform.getTabAt(0)).setText("Info");
        Objects.requireNonNull(tab_layoutEngagementform.getTabAt(1)).setText("History");
        Objects.requireNonNull(tab_layoutEngagementform.getTabAt(2)).setText("Add New");
    }

}
