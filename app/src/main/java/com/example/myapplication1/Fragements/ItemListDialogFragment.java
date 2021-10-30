package com.example.myapplication1.Fragements;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.example.myapplication1.Adapter.OTTAdapter;
import com.example.myapplication1.Database.OTTdb;
import com.example.myapplication1.Modals.OTT;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication1.R;

import java.security.PublicKey;
import java.security.Timestamp;
import java.util.ArrayList;

public class ItemListDialogFragment extends BottomSheetDialogFragment {

    EditText username, email, plan, date;
    Button submit;
    OTTAdapter ottAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);

        OTTdb db = new OTTdb(getContext());
        username = view.findViewById(R.id.edtUserName);
        username.addTextChangedListener(watcher);
        email = view.findViewById(R.id.edtEmail);
        email.addTextChangedListener(watcher);
        plan = view.findViewById(R.id.edtPlan);
        plan.addTextChangedListener(watcher);
        date = view.findViewById(R.id.edtDate);
        date.addTextChangedListener(watcher);
        submit = view.findViewById(R.id.submitButton);
        Bundle args = getArguments();
        String platform = null;
        if (args != null) {
            platform = args.getString("platform");
        }
        if(submit.isEnabled()){
            String finalPlatform = platform;
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String time = String.valueOf(System.currentTimeMillis());
                    OTT ott = new OTT(finalPlatform, username.getText().toString().trim(),
                            email.getText().toString().trim(), plan.getText().toString().trim(),
                            date.getText().toString().trim());
                    ott.setGroupId(time);
                    Log.d("Want", "onClick: " + ott.getPlatform() + finalPlatform);
                    db.update(ott);
                    ottAdapter.insert();
                    dismiss();
                }
            });
        }
        return view;
    }

    public TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String Name = username.getText().toString().trim();
            String Email = email.getText().toString().trim();
            String Plan = plan.getText().toString().trim();
            String Date = date.getText().toString().trim();
            submit.setEnabled(!Name.isEmpty() && !Email.isEmpty() && !Plan.isEmpty() && !Date.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    ItemListDialogFragment(OTTAdapter adapter){
        this.ottAdapter = adapter;
    }
}