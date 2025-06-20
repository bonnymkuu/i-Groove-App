package com.igroove.igrooveapp;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class RegisterActivity extends AppCompatActivity {
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) throws Resources.NotFoundException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ViewPager viewPager = findViewById(R.id.view_pager);
        setupViewPager(viewPager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) throws Resources.NotFoundException {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragment(), "Login");
        adapter.addFragment(new SignupFragment(), "Music Lovers");
        adapter.addFragment(new ArtistSignupFragment(), "Artist Signup");
        viewPager.setAdapter(adapter);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList;
        private final List<String> fragmentTitleList;

        ViewPagerAdapter(FragmentManager manager) {
            super(manager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.fragmentList = new ArrayList<>();
            this.fragmentTitleList = new ArrayList<>();
        }

        @NonNull
        @Override // androidx.fragment.app.FragmentPagerAdapter
        public Fragment getItem(int position) {
            return this.fragmentList.get(position);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return this.fragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            this.fragmentList.add(fragment);
            this.fragmentTitleList.add(title);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public CharSequence getPageTitle(int position) {
            return this.fragmentTitleList.get(position);
        }
    }
}
