package com.example.project1.view;

import android.widget.Button;
import android.widget.ImageView;

public interface QuizView {

    void initDataNguPhap();

    void initDataChuHan();

    void initDataTuVung();

    void initStop();

    void showDialogTheEndTrue();

    void showDialogTheEndFalse();

    void napCauHoi();

    void updateCount(int a);

    void showDialogAnswerTrue();

    void showDialogAnswerFalse();

    void animationLeftToRight(Button button);

    void animationRightToLeft(Button button);

    void animationRotation(ImageView imageView);

}
