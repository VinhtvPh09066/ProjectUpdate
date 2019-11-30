package com.example.project1.activity;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.project1.R;
import com.example.project1.database.QuestionBank;
import com.example.project1.presenter.QuizPresenter;
import com.example.project1.view.QuizView;

public class QuizActivity extends BaseActivity implements QuizView, View.OnClickListener {

    QuizPresenter quizPresenter;
    private QuestionBank mQuestionLibrary = new QuestionBank();
    private TextView mScoreView;
    private TextView mQuestionView, tvQuestion;
    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonChoice3;
    private Button mButtonChoice4;
    private Button btnStop;
    private String mAnswer;
    public int mScore = 0;
    private int mQuestionNumber = 0;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        quizPresenter = new QuizPresenter(this);
        initView();
        initAction();

        //lấy lại dữ liệu click từ activity trước và sét data tương ứng.
        Intent intent = getIntent();
        String b = intent.getStringExtra("ExamN5");
        quizPresenter.initData(b);

    }

    public void initAction() {
        btnStop.setOnClickListener(this);

    }

    public void initView() {
        mScoreView = (TextView) findViewById(R.id.score);
        mQuestionView = (TextView) findViewById(R.id.question);
        mButtonChoice1 = (Button) findViewById(R.id.choice1);
        mButtonChoice2 = (Button) findViewById(R.id.choice2);
        mButtonChoice3 = (Button) findViewById(R.id.choice3);
        mButtonChoice4 = (Button) findViewById(R.id.choice4);
        tvQuestion = findViewById(R.id.tvQuestion);
        btnStop = findViewById(R.id.btnStop);
    }


    @Override
    public void initDataNguPhap() {
        mQuestionLibrary.initQuestions(getApplicationContext());
        getSupportActionBar().setTitle("文法 / Ngữ pháp");
        quizPresenter.updatecauhoi(mQuestionNumber, mScore);

    }

    @Override
    public void initDataChuHan() {
        mQuestionLibrary.initQuestions1(getApplicationContext());
        getSupportActionBar().setTitle("漢字 / Chữ hán");
        quizPresenter.updatecauhoi(mQuestionNumber, mScore);
    }

    @Override
    public void initDataTuVung() {
        mQuestionLibrary.initQuestions2(getApplicationContext());
        getSupportActionBar().setTitle("語彙 / Từ vựng");
        quizPresenter.updatecauhoi(mQuestionNumber, mScore);
    }

    @Override
    public void initStop() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
        View view = LayoutInflater.from(QuizActivity.this).inflate(R.layout.dialog_notification, null);
        builder.setView(view);


        //khai báo và ánh xạ
        ImageView img;
        Button btnHuy;
        Button btnDongY;
        img = (ImageView) view.findViewById(R.id.img);
        btnHuy = (Button) view.findViewById(R.id.btnHuy);
        btnDongY = (Button) view.findViewById(R.id.btnDongY);

        //set animation
        animationRotation(img);
        animationLeftToRight(btnHuy);
        animationRightToLeft(btnDongY);


        //set onclick
        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
                View alert = LayoutInflater.from(QuizActivity.this).inflate(R.layout.dialog_notificationtrue, null);
                builder.setView(alert);
                builder.setCancelable(false);

                //khai báo và ánh xạ
                TextView tvDiem;
                Button btnBack, btnRequest;
                ImageView imgmedal;
                tvDiem = (TextView) alert.findViewById(R.id.tvDiem);
                btnBack = alert.findViewById(R.id.btnBackHome);
                btnRequest = alert.findViewById(R.id.btnRequest);
                imgmedal = alert.findViewById(R.id.imgmedal);
                tvDiem.setText("" + mScore);

                //set animaton
                animationLeftToRight(btnBack);
                animationRotation(imgmedal);


                // set onlick
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openActivity(MainActivity.class);
                    }
                });
                btnRequest.setVisibility(View.GONE);


                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
            }
        });
        btnHuy.setOnClickListener(this);


        AlertDialog alertDialog = builder.create();
        dialog = alertDialog;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    @Override
    public void showDialogTheEndTrue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
        View alert = LayoutInflater.from(QuizActivity.this).inflate(R.layout.dialog_notificationtrue, null);
        builder.setView(alert);
        builder.setCancelable(false);


        //khai báo và ánh xạ
        TextView tvDiem;
        Button btnBack, btnRequest;
        ImageView imgmedal;
        tvDiem = alert.findViewById(R.id.tvDiem);
        btnBack = alert.findViewById(R.id.btnBackHome);
        btnRequest = alert.findViewById(R.id.btnRequest);
        imgmedal = alert.findViewById(R.id.imgmedal);
        tvDiem.setText("" + mScore);


        //set animation
        animationLeftToRight(btnBack);
        animationRightToLeft(btnRequest);
        animationRotation(imgmedal);

        // set Action
        btnBack.setOnClickListener(this);
        btnRequest.setOnClickListener(this);


        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    @Override
    public void showDialogTheEndFalse() {
        AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
        View alert = LayoutInflater.from(QuizActivity.this).inflate(R.layout.dialog_notificationfalse, null);
        builder.setView(alert);
        builder.setCancelable(false);

        // khai báo và ánh xạ
        TextView tvDiem1;
        Button btnBack1, btnRequest1;
        ImageView imgmedal;
        tvDiem1 = alert.findViewById(R.id.tvDiem1);
        btnBack1 = alert.findViewById(R.id.btnBack1);
        btnRequest1 = alert.findViewById(R.id.btnRequest1);
        imgmedal = alert.findViewById(R.id.img);
        tvDiem1.setText("" + mScore);


        //set animation
        animationLeftToRight(btnBack1);
        animationRightToLeft(btnRequest1);
        animationRotation(imgmedal);


        //set Action
        btnBack1.setOnClickListener(this);
        btnRequest1.setOnClickListener(this);


        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

    @Override
    public void napCauHoi() {
        mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
        mButtonChoice1.setText(mQuestionLibrary.getChoice(mQuestionNumber, 1));
        mButtonChoice2.setText(mQuestionLibrary.getChoice(mQuestionNumber, 2));
        mButtonChoice3.setText(mQuestionLibrary.getChoice(mQuestionNumber, 3));
        mButtonChoice4.setText(mQuestionLibrary.getChoice(mQuestionNumber, 4));
        mAnswer = mQuestionLibrary.getCorrectAnswer(mQuestionNumber);
        mQuestionNumber++;
        animationLeftToRight(mButtonChoice1);
        animationLeftToRight(mButtonChoice3);
        animationRightToLeft(mButtonChoice2);
        animationRightToLeft(mButtonChoice4);

    }


    @Override
    public void updateCount(int mQuestionNumber) {
        tvQuestion.setText("" + mQuestionNumber + "/" + 20);

    }


    @Override
    public void showDialogAnswerTrue() {
        mScore++;

        AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_answer_true, null);
        builder.setCancelable(false);
        builder.setView(v);

        //khai báo và ánh xạ
        TextView tvKQ;
        Button btnBack;
        Button btNext;
        tvKQ = (TextView) v.findViewById(R.id.tvKQ);
        btnBack = (Button) v.findViewById(R.id.btnBack);
        btNext = (Button) v.findViewById(R.id.btNext);

        //set text và color khi đoán đúng
        tvKQ.setText("Chính xác");
        mScoreView.setText(mScore + "");
        tvKQ.setTextColor(getResources().getColor(R.color.greenn));
        btnBack.setVisibility(View.GONE);

        //set Action
        btnBack.setOnClickListener(this);
        btNext.setOnClickListener(this);

        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog = alertDialog;
        alertDialog.show();
    }

    @Override
    public void showDialogAnswerFalse() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_answer_true, null);
        builder.setCancelable(false);
        builder.setView(v);
        TextView tvKQ;
        Button btnBack;
        Button btNext;
        tvKQ = (TextView) v.findViewById(R.id.tvKQ);
        btnBack = (Button) v.findViewById(R.id.btnBack);
        btNext = (Button) v.findViewById(R.id.btNext);

        //set Text và color khi đoán sai
        tvKQ.setText("Không Chính Xác");
        tvKQ.setTextColor(getResources().getColor(R.color.red));
        btnBack.setVisibility(View.VISIBLE);


        //set action
        btnBack.setOnClickListener(this);
        btNext.setOnClickListener(this);

        AlertDialog alertDialog1 = builder.create();
        alertDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog = alertDialog1;
        alertDialog1.show();
    }

    @Override
    public void animationLeftToRight(Button button) {
        ObjectAnimator animLeft = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.left_to_right);
        animLeft.setTarget(button);
        animLeft.start();
    }

    @Override
    public void animationRightToLeft(Button button) {
        ObjectAnimator animRight = (ObjectAnimator) AnimatorInflater.loadAnimator(QuizActivity.this, R.animator.right_to_left);
        animRight.setTarget(button);
        animRight.start();
    }

    @Override
    public void animationRotation(ImageView imageView) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, View.ROTATION_Y, 0, 360);
        animator.setRepeatCount(100);
        animator.setDuration(4000);
        animator.start();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnStop) {
            initStop();
        } else if (view.getId() == R.id.btnBack) {
            dialog.dismiss();
        } else if (view.getId() == R.id.btNext) {
            quizPresenter.updatecauhoi(mQuestionNumber, mScore);
            dialog.dismiss();
        } else if (view.getId() == R.id.btnBackHome) {
            openActivity(MainActivity.class);
        } else if (view.getId() == R.id.btnBack1) {
            openActivity(MainActivity.class);
        } else if (view.getId() == R.id.btnRequest1) {
            finish();
        } else if (view.getId() == R.id.btnRequest) {
            finish();
        } else if (view.getId() == R.id.btnHuy) {
            dialog.dismiss();
        }
    }

    //click answer
    public void onClickAnswer(View view) {
        Button answer = (Button) view;
        quizPresenter.soSanhDapAn(answer.getText().toString(), mAnswer);
    }

}