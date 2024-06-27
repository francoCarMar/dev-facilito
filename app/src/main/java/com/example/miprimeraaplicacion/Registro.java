package com.example.miprimeraaplicacion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Registro extends AppCompatActivity {
    private EditText etCorreo;
    private EditText etNombre;
    private EditText etApellido;
    private EditText etPassword;
    private EditText etDni;
    private Button registro;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etCorreo = findViewById(R.id.etEmail);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etPassword = findViewById(R.id.etPassword);
        etDni = findViewById(R.id.etDni);
        registro = findViewById(R.id.btnRegistrarse);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

    }

    public void registro(View v) {
        String emailInput = etCorreo.getText().toString().trim();
        String passwordInput = etPassword.getText().toString().trim();
        String apellidoInput = etApellido.getText().toString().trim();
        String nombreInput = etNombre.getText().toString().trim();
        Long dniInput = Long.parseLong(etDni.getText().toString().trim());
        ApiService.RegisterRequest registerRequest = new ApiService.RegisterRequest(emailInput,dniInput,nombreInput,apellidoInput,passwordInput);

        apiService.register(registerRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Registro.this, "Registro Successful", Toast.LENGTH_SHORT).show();
                    goLogin(v);
                } else {
                    Toast.makeText(Registro.this, "Registro fallido" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Registro.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void goLogin(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}