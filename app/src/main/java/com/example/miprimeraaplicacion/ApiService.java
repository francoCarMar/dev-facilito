package com.example.miprimeraaplicacion;
import android.util.Log;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/login")
    Call<Void> login(@Body LoginRequest loginRequest);

    class LoginRequest {
        private String correo;
        private String password;

        public LoginRequest(String correo, String password) {
            this.correo = correo;
            this.password = password;
        }
    }
}
