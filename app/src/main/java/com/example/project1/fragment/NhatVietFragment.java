package com.example.project1.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.project1.R;
import com.example.project1.adapter.WordAdapter;
import com.example.project1.database.DataBaseTranslate;
import com.example.project1.model.Word1;

import java.util.ArrayList;
import java.util.List;

public class NhatVietFragment extends Fragment {
    private ImageView imgSearch;
    private EditText edtNhap;
    private ImageView imgDelete;
    private RecyclerView lvList;
    DataBaseTranslate dataBaseHelper;


    private WordAdapter wordAdapter;

    private LinearLayoutManager linearLayoutManager;
    private List<Word1> wordList = new ArrayList<>();

    public NhatVietFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        imgSearch = (ImageView) view.findViewById(R.id.imgSearch);
        edtNhap = (EditText) view.findViewById(R.id.edtNhap);
        imgDelete = (ImageView) view.findViewById(R.id.imgDelete);
        lvList = (RecyclerView) view.findViewById(R.id.lvList);

        dataBaseHelper = new DataBaseTranslate(getContext());
        dataBaseHelper.createDataBase();

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String word = edtNhap.getText().toString().trim();
                // kiểm tra nếu người dùng chưa nhập gì thì dừng lại và thông báo lỗi
                if (word.isEmpty()) {
                    edtNhap.setError("Vui lòng nhập dữ liệu !!!");
                    return;
                    // nếu chữ ko empty thì tiếp tục tìm kiếm và hiển thị danh sách kết quả lên list nếu có
                } else {

                    wordList = dataBaseHelper.searchNhatViet(word);
//                    this.addAll(wordList);
                    wordAdapter = new WordAdapter(wordList, getContext());
                    linearLayoutManager = new LinearLayoutManager(getContext());
                    lvList.setAdapter(wordAdapter);
                    lvList.setLayoutManager(linearLayoutManager);
                    wordAdapter.notifyDataSetChanged();


                }


            }
        });
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wordList.size() > 0) {
                    edtNhap.setText("");
                    wordList.clear();
                    wordAdapter.notifyDataSetChanged();
                } else {
                    edtNhap.setText("");
                }
            }
        });


        return view;
    }


}
