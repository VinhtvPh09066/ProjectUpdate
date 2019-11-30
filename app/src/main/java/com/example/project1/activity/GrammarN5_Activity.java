package com.example.project1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.project1.R;
import com.example.project1.adapter.BumpoAdapter;
import com.example.project1.database.DataBaseHelper;
import com.example.project1.model.Grammar;
import com.example.project1.presenter.GrammarPresent;
import com.example.project1.view.GrammarView;

import java.util.ArrayList;
import java.util.List;

public class GrammarN5_Activity extends BaseActivity implements GrammarView {

    private SearchView grammarN5Searchview;
    private RecyclerView grammarN5RecycleView;

    DataBaseHelper dataBaseHelper;
    List<Grammar> grammarList, allGrammarList, mDataFiltered;
    BumpoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar_n5_);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("NGỮ PHÁP N5");
        initView();

        GrammarPresent grammarPresent = new GrammarPresent(this);
        grammarPresent.setListview();
        grammarPresent.filterData();

    }

    private void initView(){
        grammarN5Searchview = (SearchView) findViewById(R.id.grammarN5_searchview);
        grammarN5RecycleView = (RecyclerView) findViewById(R.id.grammarN5_recycleView);
    }

    @Override
    public List<Grammar> getGrammarSuccess() {
        grammarList = new ArrayList<>();
        allGrammarList = new ArrayList<>();
        dataBaseHelper = new DataBaseHelper(this);

        for (int i = 0; i < 25; i++){
            grammarList = dataBaseHelper.getGrammar(i);
            for (int j = 0; j < grammarList.size(); j++){
                allGrammarList.add(grammarList.get(j));
            }
        }
        return allGrammarList;
    }

    @Override
    public void getGrammarError() {
        Toast.makeText(this, "Lỗi tải dữ liệu", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showDetail() {

    }

    @Override
    public void hideDetail() {

    }

    @Override
    public void searchData(final List<Grammar> list) {
        grammarN5Searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s, list);

                return false;
            }
        });
    }

    @Override
    public void loadList(List<Grammar> list) {
        adapter = new BumpoAdapter(this, list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        grammarN5RecycleView.setLayoutManager(layoutManager);
        grammarN5RecycleView.setAdapter(adapter);
    }

    private void filter(String text, List<Grammar> mDataFiltered) {
        List<Grammar> locList = new ArrayList<>();
        if (text.isEmpty()) {
            adapter.filterList(mDataFiltered);
            allGrammarList = mDataFiltered;
            adapter.notifyDataSetChanged();
        } else {
            for (Grammar item : mDataFiltered) {
                if (item.getUname().toLowerCase().contains(text.toLowerCase())) {
                    locList.add(item);
                } else if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                    locList.add(item);
                }
            }
            adapter.filterList(locList);
            allGrammarList = locList;
            adapter.notifyDataSetChanged();
        }
    }

}
