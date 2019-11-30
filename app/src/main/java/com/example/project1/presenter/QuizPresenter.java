package com.example.project1.presenter;

import com.example.project1.view.QuizView;

public class QuizPresenter {
    QuizView quizView;
    String a = "A";
    String a2 = "B";
    String a3 = "C";



    public QuizPresenter(QuizView quizView) {
        this.quizView = quizView;
    }


    public void initData(String intent) {
        if (intent.equals(a)) {
            quizView.initDataNguPhap();
        } else if (intent.equals(a2)) {
            quizView.initDataChuHan();
        } else if (intent.equals(a3)) {
            quizView.initDataTuVung();
        }
    }

    public void updatecauhoi(int a, int diem) {
        if (a <= 19) {
            quizView.napCauHoi();
            quizView.updateCount(a + 1);
        } else {
            if (diem > 10) {
                quizView.updateCount(a);
                quizView.showDialogTheEndTrue();
            } else {
                quizView.updateCount(a);
                quizView.showDialogTheEndFalse();
            }

        }
    }

    public void soSanhDapAn(String a, String b) {
        if (a.equals(b)) {
            quizView.showDialogAnswerTrue();
        } else {
            quizView.showDialogAnswerFalse();
        }
    }



}
