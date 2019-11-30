package com.example.project1.presenter;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;
import com.example.project1.adapter.AlphabetAdapter;
import com.example.project1.model.Alphabet;
import com.example.project1.view.AlphabetView;

import java.io.IOException;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class AlphabetPresenter {

    private Context context;
    private AlphabetView alphabetView;
    private MediaPlayer mediaPlayer;
    private AlertDialog alertDialog;

    public AlphabetPresenter(Context context, AlphabetView alphabetView) {
        this.context = context;
        this.alphabetView = alphabetView;
    }

    public void loadRecycleview(RecyclerView recyclerView){
        List<Alphabet> list = alphabetView.getList();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 5));
        AlphabetAdapter alphabetAdapter = new AlphabetAdapter(context, list);
        recyclerView.setAdapter(alphabetAdapter);
    }

    public void showDialog(final Alphabet a){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        final View dialog = LayoutInflater.from(context).inflate(R.layout.dialog_alphabet_detail, null);
        builder.setView(dialog);


        TextView alphabetCardviewTvRoma, alphabetCardviewTvOk;
        ImageView alphabetCardviewImgPronun;
        GifImageView alphabetCardviewTvHira1, alphabetCardviewTvKata1;

        alphabetCardviewTvHira1 = (GifImageView) dialog.findViewById(R.id.alphabet_cardview_tv_hira1);
        alphabetCardviewTvKata1 = (GifImageView) dialog.findViewById(R.id.alphabet_cardview_tv_kata1);

        alphabetCardviewTvRoma = (TextView) dialog.findViewById(R.id.alphabet_cardview_tv_roma);
        alphabetCardviewImgPronun = (ImageView) dialog.findViewById(R.id.alphabet_cardview_img_pronun);
        alphabetCardviewTvOk = (TextView) dialog.findViewById(R.id.alphabet_cardview_tv_ok);


        getSound(a.sound);

        getGifImage(a.gif_hiragana, alphabetCardviewTvHira1);
        getGifImage(a.gif_katakana, alphabetCardviewTvKata1);

        alphabetCardviewTvRoma.setText(a.romari);

        alphabetCardviewTvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alphabetCardviewImgPronun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSound(a.sound);
            }
        });

        alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

    }

    public void getGifImage(String name, GifImageView imageView){
        try {
            AssetFileDescriptor afd = context.getAssets().openFd( "alphabet_gif/" + name + ".gif");

            final GifDrawable drawable = new GifDrawable(afd);

            drawable.start();
            drawable.setLoopCount(1);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                imageView.setImageDrawable(drawable);
            }
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    public void getSound(String sound) {
        try {
            AssetFileDescriptor afd = context.getAssets().openFd("alphabet_sound/" + sound + ".mp3");

            mediaPlayer = new MediaPlayer();

            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
