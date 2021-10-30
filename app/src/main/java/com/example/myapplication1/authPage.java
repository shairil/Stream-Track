package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.myapplication1.Authenticate.Auth;
import com.example.myapplication1.databinding.ActivityAuthPageBinding;

public class authPage extends AppCompatActivity {

    ActivityAuthPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityAuthPageBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        Auth auth = new Auth(this);
        binding.username.addTextChangedListener(textWatcher);
        binding.password.addTextChangedListener(textWatcher);
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.loading.setVisibility(View.VISIBLE);
                String userEmail = binding.username.getText().toString().trim();
                String password = binding.password.getText().toString().trim();
                if(!userEmail.isEmpty() && !password.isEmpty()){
                    auth.register(userEmail, password, binding.loading);
                }
            }
        });
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String userEmail = binding.username.getText().toString();
            String password = binding.password.getText().toString();
            binding.login.setEnabled(!userEmail.isEmpty() && !password.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


}