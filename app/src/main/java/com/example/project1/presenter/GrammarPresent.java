package com.example.project1.presenter;

import com.example.project1.model.Grammar;
import com.example.project1.view.GrammarView;

import java.util.List;

public class GrammarPresent {

    private GrammarView grammarView;

    public GrammarPresent(GrammarView grammarView) {
        this.grammarView = grammarView;
    }

    public void setListview(){
        if (grammarView.getGrammarSuccess() != null) {
            grammarView.loadList(grammarView.getGrammarSuccess());
        }else {
            grammarView.getGrammarError();
        }
    }

    public void filterData(){
        grammarView.searchData(grammarView.getGrammarSuccess());
    }
}
