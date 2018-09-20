package com.example.viredapp.utilities;

import com.example.viredapp.model.FeedResult;
import com.example.viredapp.model.Login;
import com.example.viredapp.model.User;
import com.example.viredapp.model.UserSearchResult;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    Call<UserSearchResult> getUserData(@Query("page")Integer page,
                                       @Query("search") String username);

    //TODO:Post feed Items
    @Multipart
    @POST("feed/")
    Call<ResponseBody> postFeedItem(@Part("location") RequestBody location,
                                    @Part MultipartBody.Part m_url);

    @GET("/feed/")
    Call<FeedResult> getFeed(@Query("limit") Integer limit,
                             @Query("offset") Integer offset);

    //Download Media
//    @GET()
//    Call<ResponseBody> getMedia(@Url String url);

    //TODO:Add Friends

    //TODO:Show friends list


    //TODO:Send Requests

    //TODO:Show Requests

    //TODO:Likes for feed items





}
