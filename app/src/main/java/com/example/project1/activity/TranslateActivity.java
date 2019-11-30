package com.example.project1.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.example.project1.R;
import com.example.project1.adapter.HistoryAdapter;
import com.example.project1.adapter.ViewPagerAdapter;
import com.example.project1.fragment.VietNhatFragment;
import com.example.project1.fragment.NhatVietFragment;
import com.example.project1.model.TextHistory;
import com.example.project1.view.TranslateView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class TranslateActivity extends BaseActivity implements TranslateView, View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    BottomSheetBehavior behavior;
    ListView rcv;
    boolean isShowSheet = false;
    private List<TextHistory> list;
    private HistoryAdapter historyAdapter;
    TextView tvDelete;
    View bottomSheet;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("TRA TỪ ĐIỂN");
        initView();

        //getAllData
        getData();
        //add tab and view
        addTab(viewPager);
        tabLayout.setupWithViewPager(viewPager);


        //bottom sheet and click fab
        createBottomSheetBehavior();
        fab.setOnClickListener(this);
        tvDelete.setOnClickListener(this);


    }


    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tvDelete = findViewById(R.id.tvDelete);
        bottomSheet = findViewById(R.id.bottom_sheet);
        fab = findViewById(R.id.Fab);
        rcv = findViewById(R.id.rcv);
    }


    @Override
    public void createBottomSheetBehavior() {
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                switch (i) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                    case BottomSheetBehavior.STATE_HIDDEN:
                        isShowSheet = false;
                        fab.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                        break;

                    case BottomSheetBehavior.STATE_EXPANDED:
                        isShowSheet = true;
                        fab.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                        break;

                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
    }

    @Override
    public void onClickFab() {
        list = MainActivity.db.historyDAO().getAllHistory();
        historyAdapter = new HistoryAdapter(TranslateActivity.this, list);
        rcv.setAdapter(historyAdapter);

        if (!isShowSheet) {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            isShowSheet = true;
            fab.setExpanded(false);
        } else {
            behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            isShowSheet = false;
        }
    }


    @Override
    public void addTab(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrm(new VietNhatFragment(), "Việt-Nhật");
        adapter.addFrm(new NhatVietFragment(), "Nhật-Việt");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void getData() {
        list = MainActivity.db.historyDAO().getAllHistory();
        historyAdapter = new HistoryAdapter(this, list);
        rcv.setAdapter(historyAdapter);
    }

    @Override
    public void deleteHistory() {
        MainActivity.db.historyDAO().deleteAll();
        list = MainActivity.db.historyDAO().getAllHistory();
        historyAdapter.notifyDataSetChanged();
        historyAdapter = new HistoryAdapter(TranslateActivity.this, list);
        rcv.setAdapter(historyAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.Fab) {
            onClickFab();

        } else if (v.getId() == R.id.tvDelete) {
            deleteHistory();
        }
    }
}
