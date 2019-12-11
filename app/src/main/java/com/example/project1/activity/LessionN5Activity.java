package com.example.project1.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.project1.R;
import com.example.project1.adapter.ViewPagerAdapter;
import com.example.project1.fragment.BumpoFragment;
import com.example.project1.fragment.KaiwaFragment;
import com.example.project1.fragment.KanjiFragment;
import com.example.project1.fragment.KotobaFragment;
import com.google.android.material.tabs.TabLayout;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import static com.example.project1.morefunc.DataSave.idSpinnerChose;

public class LessionN5Activity extends BaseActivity {

    private Toolbar toolbarLession;
    private MaterialSpinner spinnerNav;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public MediaPlayer media = null;
    KaiwaFragment kw = new KaiwaFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lession_n5);
        initView();

        setSupportActionBar(toolbarLession);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("BÀI HỌC N5");
        getLession(spinnerNav);
        Log.e("spinner selected index", spinnerNav.getSelectedIndex() + "");
        spinnerNav.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                kw.onPause();
                idSpinnerChose = position + 1;
                addTabs(viewPager);
            }
        });

        addTabs(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    if (media == null){
                        kw.playKaiwa(idSpinnerChose, getApplicationContext());
                    }
                } else {
                    kw.onPause();
                    media = null;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        spinnerNav.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                idSpinnerChose = spinnerNav.getSelectedIndex() + 1;
                addTabs(viewPager);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        kw.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        kw.onPause();
    }

    public void addTabs(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFrm(new KotobaFragment(), "Từ vựng");
        adapter.addFrm(new BumpoFragment(), "Ngữ pháp");
        adapter.addFrm(new KaiwaFragment(), "Hội thoại");
        adapter.addFrm(new KanjiFragment(), "Chữ Hán");
        viewPager.setAdapter(adapter);
    }

    private void initView() {
        toolbarLession = findViewById(R.id.toolbar_lession);
        spinnerNav = findViewById(R.id.spinner_nav);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
    }

    public void getLession(MaterialSpinner spinner) {

        List<String> lessions = new ArrayList<>();
        for (int i = 1; i < 26; i++) {
            lessions.add("Bài " + i);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lessions);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);
    }


}
