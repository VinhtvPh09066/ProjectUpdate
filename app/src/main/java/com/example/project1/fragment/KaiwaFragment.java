package com.example.project1.fragment;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.project1.R;
import com.example.project1.adapter.KaiwaAdapter;
import com.example.project1.database.DataBaseHelper;
import com.example.project1.model.Kaiwa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.example.project1.morefunc.DataSave.idSpinnerChose;

public class KaiwaFragment extends Fragment {

    private SeekBar sbTime;
    private ImageView imgPlay;
    private TextView tvStartTime;
    private TextView tvEndTime;
    private TextView tvKaiwaTitle;
    private ListView lvKaiwa;

    private List<Kaiwa> kaiwaList;
    DataBaseHelper dataBaseHelper;
    KaiwaAdapter kaiwaAdapter;
    private Boolean isPlaying = false;

    MediaPlayer mediaPlayer;
    double startTime = 0;
    double finalTime = 0;
    Handler myHandler = new Handler();
    static int oneTimeOnly = 0;

    public KaiwaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kaiwa, container, false);
        initView(view);

        kaiwaList = new ArrayList<>();
        dataBaseHelper = new DataBaseHelper(getActivity());
        kaiwaList = dataBaseHelper.getKaiwa(idSpinnerChose);

        tvKaiwaTitle.setText(kaiwaList.get(0).getCharacter());

        List<Kaiwa> kaiwaListCut = new ArrayList<>();
        for (int j = 1; j < kaiwaList.size(); j++) {
            kaiwaListCut.add(kaiwaList.get(j));
        }
        kaiwaAdapter = new KaiwaAdapter(getActivity(), kaiwaListCut);
        lvKaiwa.setAdapter(kaiwaAdapter);



        return view;
    }


    public void playKaiwa(int lesson_id, Context context) {
        try {
            AssetFileDescriptor afd = context.getAssets().openFd("kaiwa/" + lesson_id + ".mp3");

            mediaPlayer = new MediaPlayer();

            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.prepare();

            mediaPlayer.start();
            Log.e("Media", mediaPlayer.isPlaying()+"");
            isPlaying = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("Playing", "onResume");
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mediaPlayer.isPlaying()) {

                    if (mediaPlayer != null) {
                        mediaPlayer.pause();
                        imgPlay.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    }

                } else {
                    if (mediaPlayer != null) {
                        mediaPlayer.start();
                        imgPlay.setImageResource(R.drawable.ic_pause_black_24dp);
                    }
                }
            }
        });
        onPlaying(mediaPlayer);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }


    private void initView(View view) {
        LinearLayout statusbar = view.findViewById(R.id.statusbar);
        statusbar.setVisibility(View.GONE);
        sbTime = view.findViewById(R.id.sbTime);
        imgPlay = view.findViewById(R.id.imgPlay);
        tvStartTime = view.findViewById(R.id.tvStartTime);
        tvEndTime = view.findViewById(R.id.tvEndTime);
        tvKaiwaTitle = view.findViewById(R.id.tvKaiwa_Title);
        lvKaiwa = view.findViewById(R.id.lvKaiwa);

    }

    public void onPlaying(MediaPlayer media) {
        if (isPlaying) {
            finalTime = media.getDuration();
            startTime = media.getCurrentPosition();
            if (oneTimeOnly == 0) {
                sbTime.setMax((int) finalTime);
                oneTimeOnly = 1;
            }
            tvStartTime.setText(String.format(
                    "%d : %d",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime)
                            - TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes((long) startTime))

            ));
            tvEndTime.setText(String.format(
                    "%d : %d",
                    TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) finalTime)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                            .toMinutes((long) finalTime))
            ));

            sbTime.setProgress((int) startTime);
            myHandler.postDelayed(UpdateSongTime, 100);
            imgPlay.setImageResource(R.drawable.ic_pause_black_24dp);
        }
        Log.e("Playing", "onPlaying");

    }

    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            tvStartTime.setText(String.format(
                    "%d : %d",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime)
                            - TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes((long) startTime))

            ));
            sbTime.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }
    };



}
