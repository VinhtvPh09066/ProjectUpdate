package com.example.project1.view;

import com.example.project1.model.Grammar;

import java.util.List;

public interface GrammarView {
    List<Grammar> getGrammarSuccess();
    void getGrammarError();
    void showDetail();
    void hideDetail();
    void searchData(List<Grammar> list);
    void loadList(List<Grammar> list);
}
