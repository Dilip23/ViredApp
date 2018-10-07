package com.example.viredapp.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.viredapp.R;
import com.example.viredapp.db.Friends;
import com.example.viredapp.model.FriendsViewModel;
import com.example.viredapp.model.RequestName;
import com.example.viredapp.model.Result;
import com.example.viredapp.utilities.ApiClient;
import com.example.viredapp.utilities.InjectorUtils;
import com.example.viredapp.utilities.UserClient;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;


/**
 * Displays User Profile Data and also sends Requests to Users
 * */


public class UserActivity extends AppCompatActivity {


    private CircleImageView profilePic;
    private TextView username,email,location,friends_count;
    private Button follow;
    private ViewModel viewModel;
    private LinearLayout userLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

       profilePic = findViewById(R.id.profilePic);
       username   = findViewById(R.id.username);
       email      = findViewById(R.id.email);
       location   = findViewById(R.id.location);
       friends_count = findViewById(R.id.friends_count);
       follow = findViewById(R.id.follow);

        userLayout = findViewById(R.id.userLayout);

       final Object id = getIntent().getExtras().get("user_id");
       Object name = getIntent().getExtras().get("username");

       LiveData<Friends> result = subscribeUI(this, (String) name);
       //Check friendship status in DB and handle Button
       if(result.getValue()==null){
           follow.setVisibility(View.VISIBLE);
       }else {
           follow.setVisibility(View.GONE);
       }
       //Get Profile Data
        getProfileData((Integer) id);

       follow.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                sendRequest((Integer)id);
           }
       });

    }

    /**
     * Method to retrieve friendship data from DB
     * */
    private LiveData<Friends> subscribeUI(Context context, String name){
        ViewModelProvider.Factory factory = InjectorUtils.INSTANCE.provideFriendsViewModel(context);
        viewModel = ViewModelProviders.of(this,factory).get(FriendsViewModel.class);
        LiveData<Friends> friend = ((FriendsViewModel) viewModel).getFriend(name);
        return friend;
    }


    /**
     * Method to retrieve user data from REST API
     * */
    private void getProfileData(Integer id){
        UserClient userClient = ApiClient.getApiClient().create(UserClient.class);
        Call<Result> call = userClient.getProfile(id);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                Glide.with(UserActivity.this).load(response.body().getProfilePic()).into(profilePic);
                username.setText(response.body().getUsername());
                email.setText(response.body().getEmail());
                location.setText(response.body().getLocation());
                //friends_count.setText(response.body().getFriendsCount().toString());
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Timber.d(t.getMessage());
            }
        });
    }

    private void sendRequest(Integer username){
        UserClient userClient = ApiClient.getApiClient().create(UserClient.class);
        RequestName requestName = new RequestName(username);
        Call<ResponseBody> call = userClient.sendRequest(requestName);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 201){
                    showSnackBar("Request Sent!");
                }
                else {
                    showSnackBar("Unable to send Request!");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Timber.d(t.getMessage());
            }
        });
    }

    private void showSnackBar(String message) {

        Snackbar snackbar = Snackbar.make(userLayout,message,Snackbar.LENGTH_LONG);
        snackbar.show();
        follow.setVisibility(View.INVISIBLE);

    }
}
