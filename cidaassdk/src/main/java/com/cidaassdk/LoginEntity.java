package com.cidaassdk;

/**
 * Created by Suprada on 08-Mar-17.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Giri on 3/15/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginEntity {

    String access_token;
    long expires_in;
    String id_token ;
    String scope  ;
    String userstate;
    String refresh_token;

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getId_token() {
        return id_token;
    }

    public void setId_token(String id_token) {
        this.id_token = id_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUserstate() {
        return userstate;
    }

    public void setUserstate(String userstate) {
        this.userstate = userstate;
    }


}
