package com.example.gameofthrones;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class CharactersAdapter extends RecyclerView.Adapter {
    private Person[] mDataSet;
    static String txtColor;

    public CharactersAdapter(Person[] data, String color) {
        mDataSet = data;
        txtColor = color;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private ImageView mImageView;

        public MyViewHolder(View view) {
            super(view);
            mTextView = view.findViewById(R.id.characterName);
            mTextView.setTextColor(Color.parseColor(txtColor));
            mImageView = view.findViewById(R.id.characterPic);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell,
                parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Person thisPerson = mDataSet[position];
        ((MyViewHolder)holder).mTextView.setText(thisPerson.name);
        ((MyViewHolder)holder).mImageView.setImageResource(thisPerson.image);
    }

    @Override
    public int getItemCount() {
        return mDataSet.length;
    }
}
