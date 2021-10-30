package com.example.myapplication1.Fragements;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication1.Adapter.OTTAdapter;
import com.example.myapplication1.Database.OTTdb;
import com.example.myapplication1.Modals.OTT;
import com.example.myapplication1.R;
import com.example.myapplication1.databinding.FragmentHomeBinding;
import com.example.myapplication1.databinding.FragmentProfileBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    FragmentHomeBinding binding;
    boolean allVisible;
    private OTTAdapter ottAdapter;
    private ArrayList list;
    OTTdb db;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        setRetainInstance(true);

        db = new OTTdb(getContext());
        list = db.getOTTList();
        ottAdapter = new OTTAdapter(getContext(), list);
        //binding.OTTLIST.setHasFixedSize(true);
        binding.OTTLIST.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.OTTLIST.setAdapter(ottAdapter);

        binding.addDisney.setVisibility(View.GONE);
        binding.addPrime.setVisibility(View.GONE);
        binding.addSpotify.setVisibility(View.GONE);
        binding.addNetflix.setVisibility(View.GONE);
        binding.addDisneyAction.setVisibility(View.GONE);
        binding.addPrimeaction.setVisibility(View.GONE);
        binding.addNetflixaction.setVisibility(View.GONE);
        binding.addspotifyaction.setVisibility(View.GONE);
        allVisible = false;
        binding.addAccount.shrink();

        binding.addAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });



        binding.addPrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("platform", "Prime");
                ItemListDialogFragment dialogFragment = new ItemListDialogFragment(ottAdapter);
                dialogFragment.setArguments(args);
                dialogFragment.show(getParentFragmentManager(), "PrimeDetails");
                show();

                /*BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
                bottomSheetDialog.setContentView(R.layout.bottom_sheet);
                bottomSheetDialog.setCanceledOnTouchOutside(true);
                bottomSheetDialog.setDismissWithAnimation(true);

                bottomSheetDialog.show();*/
            }
        });
        binding.addDisney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("platform", "Disney");
                ItemListDialogFragment dialogFragment = new ItemListDialogFragment(ottAdapter);
                dialogFragment.setArguments(args);
                dialogFragment.show(getParentFragmentManager(), "DisneyDetails");
                show();
            }
        });
        binding.addNetflix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("platform", "Netflix");
                ItemListDialogFragment dialogFragment = new ItemListDialogFragment(ottAdapter);
                dialogFragment.setArguments(args);
                dialogFragment.show(getParentFragmentManager(), "NetflixDetails");
                show();
            }
        });
        binding.addSpotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("platform", "Spotify");
                ItemListDialogFragment dialogFragment = new ItemListDialogFragment(ottAdapter);
                dialogFragment.setArguments(args);
                dialogFragment.show(getParentFragmentManager(), "SpotifyDetails");
                show();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);


    }

    @Override
    public void onResume() {
        super.onResume();
        list = db.getOTTList();
        ottAdapter.notifyDataSetChanged();
    }

    @Override
    public void setRetainInstance(boolean retain) {
        super.setRetainInstance(retain);
    }

    public void show(){
        if(!allVisible){
            binding.addDisney.show();
            binding.addPrime.show();
            binding.addSpotify.show();
            binding.addNetflix.show();
            binding.addDisneyAction.setVisibility(View.VISIBLE);
            binding.addPrimeaction.setVisibility(View.VISIBLE);
            binding.addNetflixaction.setVisibility(View.VISIBLE);
            binding.addspotifyaction.setVisibility(View.VISIBLE);
            binding.addAccount.extend();
            allVisible = true;
        }
        else{
            binding.addDisney.hide();
            binding.addPrime.hide();
            binding.addSpotify.hide();
            binding.addNetflix.hide();
            binding.addDisneyAction.setVisibility(View.INVISIBLE);
            binding.addPrimeaction.setVisibility(View.INVISIBLE);
            binding.addNetflixaction.setVisibility(View.INVISIBLE);
            binding.addspotifyaction.setVisibility(View.INVISIBLE);
            binding.addAccount.shrink();
            allVisible = false;
        }
    }
}