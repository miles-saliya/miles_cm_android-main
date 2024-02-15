package com.milesforce.mwbewb.BotttomNavigationFragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.milesforce.mwbewb.Model.DelaysModel;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.TabFragments.EscalationFragment;
import com.milesforce.mwbewb.TabFragments.FLagFragment;
import com.milesforce.mwbewb.TabFragments.FlaggingDelaysFragment;
import com.milesforce.mwbewb.TabFragments.IMWaitingFragement;
import com.milesforce.mwbewb.TabFragments.JobsForToday;
import com.milesforce.mwbewb.TabFragments.MHPFragment;
import com.milesforce.mwbewb.TabFragments.MissedCallsFragments;
import com.milesforce.mwbewb.TabFragments.NetEnquireFragment;
import com.milesforce.mwbewb.TabFragments.SecondLevelFragment;
import com.milesforce.mwbewb.TabFragments.ToUpdateFragment;
import com.milesforce.mwbewb.TabFragments.UnTabbedFragment;
import com.milesforce.mwbewb.TabFragments.UntrackedFragment;
import com.milesforce.mwbewb.Utils.ConstantUtills;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.milesforce.mwbewb.Utils.ConstantUtills.SaveToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.TAB_CHANGE_CODE;
import static com.milesforce.mwbewb.Utils.ConstantUtills.fragment_token;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkLogFragment extends androidx.fragment.app.Fragment {

    private ViewPager view_pager;
    private TabLayout tab_layout;
    SharedPreferences sharedPreferences;
    String AccessToken;
    DelaysModel delaysModel;
    ArrayList<DelaysModel> delaysModelArrayList;

    public WorkLogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sharedPreferences = getContext().getSharedPreferences(SaveToken, Context.MODE_PRIVATE);
        AccessToken = sharedPreferences.getString(ConstantUtills.AccessToken, null);
        try {
            delaysModelArrayList = new ArrayList<>();
            delaysModel = (DelaysModel) getActivity().getIntent().getExtras().getSerializable("value");
            delaysModelArrayList.add(delaysModel);
        } catch (Exception e) {
        }
        return inflater.inflate(R.layout.fragment_work_log, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent(view);
    }

    private void initComponent(View view) {

        view_pager = (ViewPager) view.findViewById(R.id.view_pager_workLog);
        setupViewPager(view_pager);

        tab_layout = (TabLayout) view.findViewById(R.id.tab_layout_workLog);
        tab_layout.setupWithViewPager(view_pager);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setupTabIcons(TAB_CHANGE_CODE);
        }
        tab_layout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                view_pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {

        FlaggingDelaysFragment flaggingDelaysFragment = new FlaggingDelaysFragment();
        Bundle args2 = new Bundle();
        args2.putString(fragment_token, AccessToken);
        flaggingDelaysFragment.setArguments(args2);

        JobsForToday jobsForToday = new JobsForToday();
        Bundle args = new Bundle();
        args.putString(fragment_token, AccessToken);
        jobsForToday.setArguments(args);

        IMWaitingFragement imWaitingFragement = new IMWaitingFragement();
        Bundle args1 = new Bundle();
        args1.putString(fragment_token, AccessToken);
        imWaitingFragement.setArguments(args1);

        UnTabbedFragment unTabbedFragment = new UnTabbedFragment();
        Bundle args3 = new Bundle();
        args3.putString(fragment_token, AccessToken);
        unTabbedFragment.setArguments(args1);

        EscalationFragment escalationFragment = new EscalationFragment();
        Bundle escalation_bundle = new Bundle();
        escalation_bundle.putString(fragment_token, AccessToken);
        escalationFragment.setArguments(escalation_bundle);

        SecondLevelFragment secondLevelFragment = new SecondLevelFragment();
        Bundle second_args = new Bundle();
        second_args.putString(fragment_token, AccessToken);
        secondLevelFragment.setArguments(second_args);

        NetEnquireFragment netEnquireFragment = new NetEnquireFragment();
        Bundle new_enquiery = new Bundle();
//        Bundle args_netEnquireFragment = new Bundle();
//        args_netEnquireFragment.putInt("id", delaysModel.getId());
//        netEnquireFragment.setArguments(args_netEnquireFragment);

        new_enquiery.putString(fragment_token,AccessToken);
        netEnquireFragment.setArguments(new_enquiery);

        MHPFragment mhpFragment = new MHPFragment();
        Bundle mhp_bundle = new Bundle();
        mhp_bundle.putString(fragment_token,AccessToken);
        mhpFragment.setArguments(mhp_bundle);

        FLagFragment fLagFragment =new FLagFragment();
        Bundle flag_bundle = new Bundle();
        flag_bundle.putString(fragment_token,AccessToken);
        fLagFragment.setArguments(flag_bundle);











        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getChildFragmentManager());
        if (TAB_CHANGE_CODE == 0){
            adapter.addFragment(fLagFragment, "Flag");
            adapter.addFragment(escalationFragment, "Escalation");
            adapter.addFragment(netEnquireFragment, "Net Enquires");
            adapter.addFragment(mhpFragment, "MHP");
            adapter.addFragment(unTabbedFragment, "UnTapped");
        }
        if (TAB_CHANGE_CODE == 1){
            adapter.addFragment(escalationFragment, "Escalation");
            adapter.addFragment(secondLevelFragment, "Second Level");
        }
        if (TAB_CHANGE_CODE == 2){
            adapter.addFragment(escalationFragment, "Escalation");
            adapter.addFragment(netEnquireFragment, "Net Enquires");
            adapter.addFragment(mhpFragment, "MHP");
            adapter.addFragment(unTabbedFragment, "UnTapped");
        }

        adapter.addFragment(flaggingDelaysFragment, "Delays");
        adapter.addFragment(jobsForToday, "Today");
        adapter.addFragment(imWaitingFragement, "Waiting");


        viewPager.setAdapter(adapter);
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
    private void setupTabIcons(int tabSelect) {
        if (TAB_CHANGE_CODE == 0){
            Objects.requireNonNull(tab_layout.getTabAt(0)).setText("Flag");
            Objects.requireNonNull(tab_layout.getTabAt(1)).setText("Escalation");
            Objects.requireNonNull(tab_layout.getTabAt(2)).setText("Net Enquires");
            Objects.requireNonNull(tab_layout.getTabAt(3)).setText("MHP");
            Objects.requireNonNull(tab_layout.getTabAt(4)).setText("UnTapped");
            Objects.requireNonNull(tab_layout.getTabAt(5)).setText("Delays");
            Objects.requireNonNull(tab_layout.getTabAt(6)).setText("Today");
            Objects.requireNonNull(tab_layout.getTabAt(7)).setText("Waiting");
        }
        if (TAB_CHANGE_CODE == 1) {
            Objects.requireNonNull(tab_layout.getTabAt(0)).setText("Escalation");
            Objects.requireNonNull(tab_layout.getTabAt(1)).setText("Second level");
            Objects.requireNonNull(tab_layout.getTabAt(2)).setText("Delays");
            Objects.requireNonNull(tab_layout.getTabAt(3)).setText("Today");
            Objects.requireNonNull(tab_layout.getTabAt(4)).setText("Waiting");
        }
        if (TAB_CHANGE_CODE == 2){
            Objects.requireNonNull(tab_layout.getTabAt(0)).setText("Escalation");
            Objects.requireNonNull(tab_layout.getTabAt(1)).setText("Net Enquires");
            Objects.requireNonNull(tab_layout.getTabAt(2)).setText("MHP");
            Objects.requireNonNull(tab_layout.getTabAt(3)).setText("UnTapped");
            Objects.requireNonNull(tab_layout.getTabAt(4)).setText("Delays");
            Objects.requireNonNull(tab_layout.getTabAt(5)).setText("Today");
            Objects.requireNonNull(tab_layout.getTabAt(6)).setText("Waiting");
        }





    }

}
