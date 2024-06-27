package com.example.miprimeraaplicacion;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText correo, password;
    private Button loginButton;
    private TextView forgotPassword;
    private ImageButton backButton;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        correo = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        forgotPassword = findViewById(R.id.forgot_password);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
        String correoInput = correo.getText().toString().trim();
        String passwordInput = password.getText().toString().trim();

        Log.d(TAG, "Correo: " + correoInput);
        Log.d(TAG, "Password: " + passwordInput);

        ApiService.LoginRequest loginRequest = new ApiService.LoginRequest(correoInput, passwordInput);
        apiService.login(loginRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d(TAG, "response: " + response);
                if (response.isSuccessful()) {
                    // Inicio de sesión exitoso
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    // Redirigir a HomeActivity
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();  // Opcional: Llama a finish() si deseas cerrar la MainActivity
                } else {
                    // Error de inicio de sesión
                    String errorMessage = "Login Failed: " + response.message();
                    Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Error de red u otro fallo
                String errorMessage = "An error occurred: " + t.getMessage();
                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void goRegister(View v){
        Intent intent = new Intent(this, Registro.class);
        startActivity(intent);
    }




}