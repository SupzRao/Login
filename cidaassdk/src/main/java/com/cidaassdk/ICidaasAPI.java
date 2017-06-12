package com.cidaassdk;


import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Suprada on 08-Mar-17.
 */

public interface ICidaasAPI {

    //get acess token
    @POST
    Observable<LoginEntity> getAccessTokenApi1(@Url String url, @Header("Content-Type") String content_type,
                                               @Query("client_id") String client_id,
                                               @Query("redirect_uri") String redirect_uri,
                                               @Query("code") String code,
                                               @Query("client_secret") String client_secret,
                                               @Query("grant_type") String grant_type);

    //get user details based on the access token we pass
    @GET
    Observable<UserProfile> getUserDetailsApi(@Url String url, @Header("access_token") String authorization);

    //get access token based on the refresh token
    @POST
    Observable<LoginEntity> getAccessTokenByRefreshToken(@Url String url, @Header("Content-Type") String content_type,
                                                         @Query("client_id") String client_id,
                                                         @Query("redirect_uri") String redirect_uri,
                                                         @Query("refresh_token") String refresh_token,
                                                         @Query("client_secret") String client_secret,
                                                         @Query("grant_type") String grant_type);
    //log out user api
    @POST
    Observable<Void> logoutUser(@Url String url,@Query("access_token") String path_access_token,
                                @Header("access_token") String access_token);

}
