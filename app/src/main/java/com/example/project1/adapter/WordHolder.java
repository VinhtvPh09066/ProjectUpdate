package com.example.project1.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;

class WordHolder extends RecyclerView.ViewHolder {
    public TextView tvMean;
    public TextView tvSpell;
    public WordHolder(@NonNull View itemView) {
        super(itemView);

        tvMean = (TextView) itemView.findViewById(R.id.tvMean);
        tvSpell = (TextView) itemView.findViewById(R.id.tvSpell);
    }
}
