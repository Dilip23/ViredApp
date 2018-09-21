package com.example.viredapp.adapters;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.viredapp.R;
import com.example.viredapp.model.Result;

import timber.log.Timber;

public class UserAdapter extends PagedListAdapter<Result,UserAdapter.UserViewHolder>{

    private Context ctx;
    public UserAdapter(Context ctx) {
        super(diffCallback);
        this.ctx = ctx;
    }

    private static DiffUtil.ItemCallback<Result> diffCallback =
            new DiffUtil.ItemCallback<Result>() {
                @Override
                public boolean areItemsTheSame(Result oldItem, Result newItem) {
                    return oldItem.getPk() == newItem.getPk();
                }

                @Override
                public boolean areContentsTheSame(Result oldItem, Result newItem) {
                    return oldItem.equals(newItem);
                }
            };

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Timber.d("onCreateViewHolder() --> Adapter");
        View view = LayoutInflater.from(ctx).inflate(R.layout.user_row,parent,false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Timber.d("onBindViewHolder() --> Adapter");
        Result result = super.getItem(position);
        if(result != null){

            /**
             * Set Data to Views
             * using @{holder}
             * */
            holder.textView.setText(result.getUsername());

            Glide
                    .with(ctx)
                    .load(result.getProfilePic())
                    .into(holder.imageView);
        }
        else {
            Toast.makeText(ctx,"result is null",Toast.LENGTH_LONG).show();
        }
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        TextView textView ;
        ImageView imageView;
        public UserViewHolder(View itemView) {
            super(itemView);
            Timber.d("UserViewHOlder");
            textView = itemView.findViewById(R.id.username);
            imageView = itemView.findViewById(R.id.userImage);
        }
    }
}
