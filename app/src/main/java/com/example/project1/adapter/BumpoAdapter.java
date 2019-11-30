package com.example.project1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1.R;
import com.example.project1.model.Grammar;

import java.util.List;

import static com.example.project1.morefunc.expand_collapse.collapse;
import static com.example.project1.morefunc.expand_collapse.expand;

public class BumpoAdapter extends RecyclerView.Adapter<BumpoAdapter.MyViewHolder> {

    private Context context;
    private List<Grammar> list;
    private static  int currentPosition = -1;

    public BumpoAdapter(Context context, List<Grammar> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grammar, parent, false);
        final MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
         holder.tvName.setText(list.get(position).getName());
         holder.linearLayout.setVisibility(View.GONE);
         holder.tvContent.setText(list.get(position).getContent());

        if (currentPosition == position) {
            Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);

            holder.linearLayout.setVisibility(View.VISIBLE);
            holder.linearLayout.startAnimation(slideDown);
//            expand(holder.linearLayout, 400);
        }else {

            holder.linearLayout.setVisibility(View.GONE);
        }

        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPosition = position;
                notifyDataSetChanged();
            }
        });

        holder.imgArrowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPosition = -1;
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        CardView linearLayout;
        TextView tvContent;
        ImageView imgArrowUp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            linearLayout = (CardView) itemView.findViewById(R.id.linearLayout);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);
            imgArrowUp = (ImageView) itemView.findViewById(R.id.imgArrowUp);
        }
    }

    public void filterList(List<Grammar> locList){
        list = locList;
        notifyDataSetChanged();
    }
}
