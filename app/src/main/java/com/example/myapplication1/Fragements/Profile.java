package com.example.myapplication1.Fragements;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication1.Adapter.ViewPagerAdapter;
import com.example.myapplication1.Authenticate.Auth;
import com.example.myapplication1.R;
import com.example.myapplication1.authPage;
import com.example.myapplication1.databinding.FragmentProfileBinding;
import com.example.myapplication1.databinding.FragmentSearchBinding;

import static android.content.Context.MODE_PRIVATE;
import static com.example.myapplication1.R.drawable.ic_netflix_logo_wine;

public class Profile extends Fragment {

    FragmentProfileBinding binding;
    Auth auth;
    private String name = "Shairil";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        setRetainInstance(true);
        auth = new Auth(getContext());
        SharedPreferences.Editor editor = getContext().getSharedPreferences("Name", MODE_PRIVATE).edit();
        SharedPreferences prefs = getContext().getSharedPreferences("Name", MODE_PRIVATE);
        name = prefs.getString("Name","");
        binding.editTextTextPersonName.setText(name);
        binding.editTextTextPersonName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //binding.editTextTextPersonName.setEnabled(true);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editor.putString("Name", String.valueOf(binding.editTextTextPersonName.getText()));
                editor.apply();
            }
        });
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPagerAdapter.addFragment("WatchList", new WatchList());
        viewPagerAdapter.addFragment("Shared WatchList", new SharedWatchList());
        binding.vp2.setAdapter(viewPagerAdapter);
        binding.tabLayout2.setupWithViewPager(binding.vp2);
        binding.imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.logout();
                Intent intent = new Intent(getContext(), authPage.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void setRetainInstance(boolean retain) {
        super.setRetainInstance(retain);
    }
}