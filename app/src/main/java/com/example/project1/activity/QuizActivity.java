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
import com.example.project1.database.ExamN5Sql;
import com.example.project1.database.QuestionBank;

public class QuizActivity extends BaseActivity implements View.OnClickListener {

    private QuestionBank mQuestionLibrary = new QuestionBank();

    private TextView mScoreView;   // view for current total score
    private TextView mQuestionView, tvQuestion;  //current question to answer
    private Button mButtonChoice1; // multiple choice 1 for mQuestionView
    private Button mButtonChoice2; // multiple choice 2 for mQuestionView
    private Button mButtonChoice3; // multiple choice 3 for mQuestionView
    private Button mButtonChoice4; // multiple choice 4 for mQuestionView
    private Button btnStop;
    private String mAnswer;  // correct answer for question in mQuestionView
    private int mScore = 0;  // current total score
    private int dem = 1;
    private int mQuestionNumber = 0; // current question number
    ExamN5Sql examN5Sql;
    String a = "A";
    String a2 = "B";
    String a3 = "C";
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        initView();
        initData();
        initStop();
    }


    private void updateQuestion() {
        if (mQuestionNumber <= 19) {
            mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
            mButtonChoice1.setText(mQuestionLibrary.getChoice(mQuestionNumber, 1));
            mButtonChoice2.setText(mQuestionLibrary.getChoice(mQuestionNumber, 2));
            mButtonChoice3.setText(mQuestionLibrary.getChoice(mQuestionNumber, 3));
            mButtonChoice4.setText(mQuestionLibrary.getChoice(mQuestionNumber, 4));
            mAnswer = mQuestionLibrary.getCorrectAnswer(mQuestionNumber);
            mQuestionNumber++;

            //animation
            ObjectAnimator animLeft = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.left_to_right);
            animLeft.setTarget(mButtonChoice1);
            animLeft.start();
            animLeft = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.left_to_right);
            animLeft.setTarget(mButtonChoice3);
            animLeft.start();
            ObjectAnimator animRight = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.right_to_left);
            animRight.setTarget(mButtonChoice2);
            animRight.start();
            animRight = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.right_to_left);
            animRight.setTarget(mButtonChoice4);
            animRight.start();


        } else {
            if (mScore > 10) {
                tvQuestion.setText(20 + "/" + 20);
                AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
                View alert = LayoutInflater.from(QuizActivity.this).inflate(R.layout.dialog_notificationtrue, null);
                builder.setView(alert);
                builder.setCancelable(false);
                TextView tvDiem;
                Button btnBack, btnRequest;
                ImageView imgmedal;
                tvDiem = (TextView) alert.findViewById(R.id.tvDiem);
                btnBack = alert.findViewById(R.id.btnBack);
                btnRequest = alert.findViewById(R.id.btnRequest);
                imgmedal = alert.findViewById(R.id.imgmedal);
                tvDiem.setText("" + mScore);
                ObjectAnimator animLeft = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.left_to_right);
                animLeft.setTarget(btnBack);
                animLeft.start();
                ObjectAnimator animRight = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.right_to_left);
                animRight = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.right_to_left);
                animRight.setTarget(btnRequest);
                animRight.start();
                ObjectAnimator animator = ObjectAnimator.ofFloat(imgmedal, View.ROTATION_Y, 0, 360);
                animator.setRepeatCount(100);
                animator.setDuration(4000);
                animator.start();
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openActivity(MainActivity.class);
                    }
                });
                btnRequest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
            } else {
                tvQuestion.setText(20 + "/" + 20);
                AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
                View alert = LayoutInflater.from(QuizActivity.this).inflate(R.layout.dialog_notificationfalse, null);
                builder.setView(alert);
                builder.setCancelable(false);
                TextView tvDiem1;
                Button btnBack1, btnRequest1;
                ImageView imgmedal;
                tvDiem1 = (TextView) alert.findViewById(R.id.tvDiem1);
                btnBack1 = alert.findViewById(R.id.btnBack1);
                btnRequest1 = alert.findViewById(R.id.btnRequest1);
                imgmedal = alert.findViewById(R.id.img);
                tvDiem1.setText("" + mScore);
                ObjectAnimator animLeft = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.left_to_right);
                animLeft.setTarget(btnBack1);
                animLeft.start();
                ObjectAnimator animRight = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.right_to_left);
                animRight = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.right_to_left);
                animRight.setTarget(btnRequest1);
                animRight.start();
                ObjectAnimator animator = ObjectAnimator.ofFloat(imgmedal, View.ROTATION_Y, 0, 360);
                animator.setRepeatCount(100);
                animator.setDuration(4000);
                animator.start();
                btnBack1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openActivity(MainActivity.class);
                    }
                });
                btnRequest1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
            }

        }
    }


    private void updateScore(int point,int count) {
        mScoreView.setText("" + point + "/" + 20);
        tvQuestion.setText("" + count + "/" + 20);

    }


    public void onClick(View view) {
        Button answer = (Button) view;
        if (answer.getText().equals(mAnswer)) {
            mScore = mScore + 1;
            AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
            View v = LayoutInflater.from(this).inflate(R.layout.dialog_answer_true, null);
            builder.setCancelable(false);
            builder.setView(v);
            TextView tvKQ;
            Button btnBack;
            Button btNext;

            tvKQ = (TextView) v.findViewById(R.id.tvKQ);

            btnBack = (Button) v.findViewById(R.id.btnBack);
            btNext = (Button) v.findViewById(R.id.btNext);
            tvKQ.setText("Chính xác");
            tvKQ.setTextColor(getResources().getColor(R.color.greenn));
            btnBack.setVisibility(View.GONE);
            btNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateScore(mScore,dem+=1);
                    updateQuestion();
                    dialog.dismiss();
                }
            });


            AlertDialog alertDialog = builder.create();
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog = alertDialog;
            alertDialog.show();
        } else {
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
            tvKQ.setText("Không Chính Xác");
            tvKQ.setTextColor(getResources().getColor(R.color.red));
            btnBack.setVisibility(View.VISIBLE);
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            btNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateScore(mScore,dem+=1);
                    updateQuestion();
                    dialog.dismiss();
                }
            });


            AlertDialog alertDialog1 = builder.create();
            alertDialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog = alertDialog1;
            alertDialog1.show();
        }


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

    public void initStop() {
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
                View view = LayoutInflater.from(QuizActivity.this).inflate(R.layout.dialog_notification, null);
                builder.setView(view);
                ImageView img;
                Button btnHuy;
                Button btnDongY;
                img = (ImageView) view.findViewById(R.id.img);
                btnHuy = (Button) view.findViewById(R.id.btnHuy);
                btnDongY = (Button) view.findViewById(R.id.btnDongY);
                ObjectAnimator animator = ObjectAnimator.ofFloat(img, View.ROTATION_Y, 0, 360);
                animator.setRepeatCount(100);
                animator.setDuration(4000);
                animator.start();
                ObjectAnimator animLeft = (ObjectAnimator) AnimatorInflater.loadAnimator(QuizActivity.this, R.animator.left_to_right);
                animLeft.setTarget(btnHuy);
                animLeft.start();
                ObjectAnimator animRight = (ObjectAnimator) AnimatorInflater.loadAnimator(QuizActivity.this, R.animator.right_to_left);
                animRight.setTarget(btnDongY);
                animRight.start();
                btnDongY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(QuizActivity.this);
                        View alert = LayoutInflater.from(QuizActivity.this).inflate(R.layout.dialog_notificationtrue, null);
                        builder.setView(alert);
                        builder.setCancelable(false);
                        TextView tvDiem;
                        Button btnBack, btnRequest;
                        ImageView imgmedal;
                        tvDiem = (TextView) alert.findViewById(R.id.tvDiem);
                        btnBack = alert.findViewById(R.id.btnBack);
                        btnRequest = alert.findViewById(R.id.btnRequest);
                        imgmedal = alert.findViewById(R.id.imgmedal);
                        tvDiem.setText("" + mScore);
                        ObjectAnimator animLeft = (ObjectAnimator) AnimatorInflater.loadAnimator(QuizActivity.this, R.animator.left_to_right);
                        animLeft.setTarget(btnBack);
                        animLeft.start();
                        ObjectAnimator animRight = (ObjectAnimator) AnimatorInflater.loadAnimator(QuizActivity.this, R.animator.right_to_left);
                        animRight = (ObjectAnimator) AnimatorInflater.loadAnimator(QuizActivity.this, R.animator.right_to_left);
                        animRight.setTarget(btnRequest);
                        animRight.start();
                        ObjectAnimator animator = ObjectAnimator.ofFloat(imgmedal, View.ROTATION_Y, 0, 360);
                        animator.setRepeatCount(100);
                        animator.setDuration(4000);
                        animator.start();
                        btnBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                openActivity(MainActivity.class);
                            }
                        });
                        btnRequest.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        alertDialog.show();
                    }
                });
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                dialog = alertDialog;
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
            }
        });
    }

    public void initData() {
        Intent intent = getIntent();
        String b = intent.getStringExtra("ExamN5");
        if (b.equals(a)) {
            mQuestionLibrary.initQuestions(getApplicationContext());
            getSupportActionBar().setTitle("文法 / Ngữ pháp");
        } else if (b.equals(a2)) {
            mQuestionLibrary.initQuestions1(getApplicationContext());;
            getSupportActionBar().setTitle("漢字 / Chữ hán");
        } else if (b.equals(a3)) {
            mQuestionLibrary.initQuestions2(getApplicationContext());
            getSupportActionBar().setTitle("語彙 / Từ vựng");
        }
        updateQuestion();
        updateScore(mScore,dem);
    }
}