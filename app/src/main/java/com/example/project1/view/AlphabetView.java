package com.example.project1.view;

import com.example.project1.model.Alphabet;

import java.util.List;

public interface AlphabetView {

    List<Alphabet> getList();

    void clickItem(int i);
}
