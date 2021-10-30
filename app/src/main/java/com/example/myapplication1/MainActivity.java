package com.example.myapplication1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.myapplication1.Adapter.ViewPagerAdapter;
import com.example.myapplication1.Database.OTTdb;
import com.example.myapplication1.Fragements.Home;
import com.example.myapplication1.Fragements.Profile;
import com.example.myapplication1.Fragements.Search;
import com.example.myapplication1.Modals.Group;
import com.example.myapplication1.Modals.OTT;
import com.example.myapplication1.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.android.volley.Request.*;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    /*TextView textView;
    ImageView imageView;
    RequestQueue requestQueue;*/
    private ImageButton netflix, prime, disney, spotify;
    private FirebaseAuth auth;
    //JSONObject jsonObject = new JSONObject();
    //final String[] URL = {"https://imdb8.p.rapidapi.com/auto-complete?q="};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        Group group = new Group();
        OTTdb db = new OTTdb(this);
        ArrayList<OTT> list = db.getOTTList();

        for(OTT ott : list){
            group.setStartDate(ott.getStartDate());
            group.setGrpId(ott.getGroupId());
            group.setOtt(ott.getPlatform());
            group.setPlan(Double.parseDouble(ott.getPlan()));
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
                Date startDate = format.parse(ott.getStartDate());
                String endDate;
                Calendar c = Calendar.getInstance();
                c.setTime(startDate);
                if(ott.getPlatform().equals("Netflix")) {
                    c.add(Calendar.DATE, 30);
                }
                else if(ott.getPlatform().equals("Prime")) {
                    if(ott.getPlan().equals("999")){
                        c.add(Calendar.DATE, 365);
                    }
                    else if(ott.getPlan().equals("329")){
                        c.add(Calendar.MONTH, 3);
                    }
                    else if(ott.getPlan().equals("129")){
                        c.add(Calendar.MONTH, 1);
                    }
                }
                else if(ott.getPlatform().equals("Disney")){
                    c.add(Calendar.YEAR, 1);
                }

                endDate = format.format(c.getTime());
                group.setEndDate(endDate);
                group.setPaid(false);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            firebaseFirestore.collection("Groups").document(ott.getGroupId()).set(group)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("Group Successfully", "onComplete: ");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Group", "onFailure: " + e.getMessage());
                }
            });

        }

        //SharedPreferences sharedPreferences = getSharedPreferences(OTT, Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPreferences.edit();
        //Log.d("Check", "onCreate: " + sharedPreferences.getString("Netflixplan", ""));

        /*binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
                bottomSheetDialog.setContentView(R.layout.bottom_sheet);
                bottomSheetDialog.setCanceledOnTouchOutside(true);
                netflix = bottomSheetDialog.findViewById(R.id.netflix);
                disney = bottomSheetDialog.findViewById(R.id.disney);
                prime = bottomSheetDialog.findViewById(R.id.prime);
                spotify = bottomSheetDialog.findViewById(R.id.spotify);
                netflix.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show(v, editor, "Netflix");
                    }
                });
                prime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show(v, editor, "Prime");
                    }
                });
                spotify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show(v, editor, "Spotify");
                    }
                });
                disney.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        show(v, editor, "Disney");
                    }
                });
                bottomSheetDialog.show();
            }
        });*/
        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()==null){
            Intent intent = new Intent(this, authPage.class);
            startActivity(intent);
            finish();
        }

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment("Home", new Home());
        viewPagerAdapter.addFragment("Search", new Search());
        viewPagerAdapter.addFragment("Profile", new Profile());
        //Objects.requireNonNull(binding.tabLayout.getTabAt(0)).setIcon(R.drawable.ic_baseline_home_24);
        //Objects.requireNonNull(binding.tabLayout.getTabAt(1)).setIcon(R.drawable.ic_baseline_search_24);
        //Objects.requireNonNull(binding.tabLayout.getTabAt(2)).setIcon(R.drawable.ic_baseline_person_24);
        binding.vp.setAdapter(viewPagerAdapter);
        binding.tabLayout.setupWithViewPager(binding.vp);
        binding.tabLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_home_24);
        binding.tabLayout.getTabAt(0).setText("");
        binding.tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_search_24);
        binding.tabLayout.getTabAt(1).setText("");
        binding.tabLayout.getTabAt(2).setIcon(R.drawable.ic_baseline_person_24);
        binding.tabLayout.getTabAt(2).setText("");
        /*requestQueue = Volley.newRequestQueue(this);
        textView = findViewById(R.id.textView2);
        imageView = findViewById(R.id.imageView);
        EditText editText = findViewById(R.id.textView3);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText()!=null){
                    String url = URL[0] + editText.getText();
                    func(url);
                }
            }
        });*/
    }

    /*void show(View v, SharedPreferences.Editor editor, String platform){
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
        bottomSheetDialog.setContentView(R.layout.dialog_layout);
        bottomSheetDialog.setCanceledOnTouchOutside(true);


        //Log.d("Here", "show: " + plan);

        Button button = bottomSheetDialog.findViewById(R.id.confirm_button);
        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String plan = ((EditText) Objects.requireNonNull(bottomSheetDialog.findViewById(R.id.plan))).getText().toString().trim();
                String date = ((EditText) Objects.requireNonNull(bottomSheetDialog.findViewById(R.id.Date))).getText().toString().trim();
                if(!plan.isEmpty() && !date.isEmpty()) {
                    editor.putString(platform + "date", date);
                    editor.putString(platform + "plan", plan);
                    editor.apply();
                    Log.d("Check", "onClick: ");
                }
                else{
                    Log.d("Check", "onClick: " + "Don't know");
                }
            }
        });

        bottomSheetDialog.show();

    }*/

    /*public void func(String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(response!=null){
                    try {
                        JSONArray arr = response.getJSONArray("d");
                        String name = arr.getJSONObject(0).getString("l");
                        String image = arr.getJSONObject(0).getJSONObject("i").getString("imageUrl");
                        Log.d("HELLO", "onResponse: " + name);
                        textView.setText(name);
                        Glide.with(getApplicationContext())
                                .load(image)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imageView);

                    } catch (JSONException e) {
                        Log.d("HELLO", e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("x-rapidapi-host", "imdb8.p.rapidapi.com");
                map.put("x-rapidapi-key", "3d780c10e9msh4688200f1f0660dp1c6132jsn2c7509e20795");
                return map;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }*/
}