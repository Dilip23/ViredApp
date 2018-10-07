package com.example.viredapp.utilities;

import com.example.viredapp.model.FeedResult;
import com.example.viredapp.model.FriendsResult;
import com.example.viredapp.model.Login;
import com.example.viredapp.model.RequestName;
import com.example.viredapp.model.RequestResult;
import com.example.viredapp.model.Result;
import com.example.viredapp.model.User;
import com.example.viredapp.model.UserSearchResult;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserClient {

    //Login Call
    @POST("login/")
    Call<User> login(@Body Login login);

    //Signup Call
    @Multipart
    @POST("profile/")
    Call<ResponseBody> performRegistration(@Part("username") RequestBody username,
                                           @Part("email")    RequestBody email,
                                           @Part("password") RequestBody password,
                                           @Part("location") RequestBody location,
                                           @Part MultipartBody.Part profile_pic);

    //Retrieve User Data after first-time Login by searching
    @GET("profile/")
    Call<List<Result>> getUserData(@Query("search") String username);

    @Multipart
    @POST("feed/")
    Call<ResponseBody> postFeedItem(@Part("location") RequestBody location,
                                    @Part MultipartBody.Part m_url);

    @GET("/feed/")
    Call<FeedResult> getFeed(@Query("limit") Integer limit,
                             @Query("offset") Integer offset);


    //TODO:Download Media
//    @GET()
//    Call<ResponseBody> getMedia(@Url String url);


    //Get Friends List
    @GET("friends/")
    Call<FriendsResult> getFriends(@Query("limit") Integer limit,
                                   @Query("offset") Integer offset);



    //TODO:Add Friends
    @DELETE("add/{id}/")
    Call<ResponseBody> acceptRequest(@Path("id") Integer id);

    //TODO:Show Requests
    @GET("request-list/")
    Call<RequestResult> getRequests(
            @Query("limit")Integer limit,
            @Query("offset")Integer offset
    );

    //TODO:Send Requests
    @POST("request/")
    Call<ResponseBody> sendRequest(@Body RequestName requestName);


    //TODO:Get Profile Data
    @GET("/profile/{id}/")
    Call<Result> getProfile(@Path("id") Integer id);


}
