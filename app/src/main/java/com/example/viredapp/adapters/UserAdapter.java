package com.example.viredapp.adapters;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.viredapp.R;
import com.example.viredapp.model.Result;

import java.util.List;

import timber.log.Timber;

public class UserAdapter extends ArrayAdapter<Result>{

    private Context context;
    private List<Result> results;

    public UserAdapter(@NonNull Context context, @NonNull List<Result> results) {
        super(context, R.layout.user_row, results);
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.user_row,parent,false);
        Result result = results.get(position);

        TextView textView = convertView.findViewById(R.id.username);
        ImageView imageView = convertView.findViewById(R.id.profilePic);

        textView.setText(result.getUsername());
        Glide.with(context).load(result.getProfilePic()).into(imageView);

        return convertView;
    }
}