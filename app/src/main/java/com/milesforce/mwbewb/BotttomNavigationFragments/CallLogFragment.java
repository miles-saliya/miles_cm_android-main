package com.milesforce.mwbewb.BotttomNavigationFragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.milesforce.mwbewb.R;
import com.milesforce.mwbewb.TabFragments.CallLogInfoFragment;
import com.milesforce.mwbewb.TabFragments.MissedCallsFragments;
import com.milesforce.mwbewb.TabFragments.ToUpdateFragment;
import com.milesforce.mwbewb.TabFragments.UntrackedFragment;
import com.milesforce.mwbewb.Utils.ConstantUtills;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static com.milesforce.mwbewb.Utils.ConstantUtills.SaveToken;
import static com.milesforce.mwbewb.Utils.ConstantUtills.fragment_token;


/**
 * A simple {@link Fragment} subclass.
 */
public class CallLogFragment extends Fragment {
    private ViewPager view_pager;
    private TabLayout tab_layout;
    SharedPreferences sharedPreferences;
    String AccessToken;

    public CallLogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sharedPreferences = getContext().getSharedPreferences(SaveToken, MODE_PRIVATE);
        AccessToken = sharedPreferences.getString(ConstantUtills.AccessToken, null);
        return inflater.inflate(R.layout.fragment_call_log, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponent(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initComponent(View view) {
        view_pager = (ViewPager) view.findViewById(R.id.view_pager);
        setupViewPager(view_pager);

        tab_layout = (TabLayout) view.findViewById(R.id.tab_layout);
        tab_layout.setupWithViewPager(view_pager);
        setupTabIcons();
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

        ToUpdateFragment updateFragment = new ToUpdateFragment();
        Bundle args2 = new Bundle();
        args2.putString(fragment_token, AccessToken);
        updateFragment.setArguments(args2);

        MissedCallsFragments missedcall = new MissedCallsFragments();
        Bundle args = new Bundle();
        args.putString(fragment_token, AccessToken);
        missedcall.setArguments(args);

        UntrackedFragment untracked = new UntrackedFragment();
        Bundle args1 = new Bundle();
        args1.putString(fragment_token, AccessToken);
        untracked.setArguments(args1);

        CallLogInfoFragment callLogInfoFragment = new CallLogInfoFragment();
        Bundle argsLogs = new Bundle();
        args1.putString(fragment_token, AccessToken);
        callLogInfoFragment.setArguments(argsLogs);


        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getChildFragmentManager());

        adapter.addFragment(missedcall, "Missed");
        adapter.addFragment(untracked, "Untracked");
        adapter.addFragment(updateFragment, "To Update");
        adapter.addFragment(callLogInfoFragment, "Call Logs");
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
    private void setupTabIcons() {

        Objects.requireNonNull(tab_layout.getTabAt(0)).setText("Missed");
        Objects.requireNonNull(tab_layout.getTabAt(1)).setText("Untracked");
        Objects.requireNonNull(tab_layout.getTabAt(2)).setText("To Update");
        Objects.requireNonNull(tab_layout.getTabAt(3)).setText("Call Logs");

    }
}
