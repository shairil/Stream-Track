package com.example.myapplication1.Fragements;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication1.Adapter.MovieAdapter;
import com.example.myapplication1.databinding.FragmentSearchBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Search extends Fragment {

    FragmentSearchBinding binding;
    RequestQueue requestQueue;
    private ArrayList<String> images, titles;
    MovieAdapter movieAdapter;
    private JSONObject jsonObject = new JSONObject();
    final String[] URL = {"https://imdb8.p.rapidapi.com/auto-complete?q="};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setRetainInstance(true);
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        Context context = null;
        if (container != null) {
            context = container.getContext();
        }

        images = new ArrayList<>();
        titles = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(context);
        binding.searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.progressBar.setVisibility(View.VISIBLE);
                if (binding.showName.getText() != null) {
                    String url = URL[0] + binding.showName.getText();
                    call(url);
                }
            }
        });

        movieAdapter = new MovieAdapter(context, titles, images, binding.progressBar);
        binding.movielist.setHasFixedSize(true);
        binding.movielist.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        binding.movielist.setAdapter(movieAdapter);
        return binding.getRoot();
    }

    @Override
    public void setRetainInstance(boolean retain) {
        super.setRetainInstance(retain);
    }

    void call(String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    try {
                        JSONArray arr = response.getJSONArray("d");
                        //String title = arr.getJSONObject(1).getString("l");
                        titles.clear();
                        images.clear();
                        for (int i = 0; i < arr.length(); i++) {
                            String title = arr.getJSONObject(i).getString("l");
                            String image = arr.getJSONObject(i).getJSONObject("i").getString("imageUrl");
                            titles.add(i, title);
                            images.add(i, image);
                            Log.d("HELLO", "onResponse: " + title + "Iteration no. " + i);
                        }
                        if(titles.isEmpty()){
                            binding.progressBar.setVisibility(View.INVISIBLE);
                        }
                        //movieAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        binding.progressBar.setVisibility(View.INVISIBLE);
                        Log.d("HELLO", e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("x-rapidapi-host", "imdb8.p.rapidapi.com");
                map.put("x-rapidapi-key", "3d780c10e9msh4688200f1f0660dp1c6132jsn2c7509e20795");
                return map;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}