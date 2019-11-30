package com.example.project1.presenter;

import com.example.project1.model.Word1;
import com.example.project1.view.VietNhatView;

import java.util.List;

public class VietNhatPresenter {
    VietNhatView vietNhatView;


    public VietNhatPresenter(VietNhatView vietNhatView) {
        this.vietNhatView = vietNhatView;
    }


    public void timKiem(String text) {
        if (text.equals("")) {
            vietNhatView.setErroText();
        } else {
            vietNhatView.searchVietNhat(text);
        }
    }

    public void clearF(  List<Word1> word1s) {
   if (word1s.size()==0){
       vietNhatView.clearListNotSize();
   }else{
       vietNhatView.clearList();
   }
    }
}
