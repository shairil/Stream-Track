package com.example.myapplication1.Authenticate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication1.MainActivity;
import com.example.myapplication1.Modals.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Auth {

    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    private Context context;
    public Auth(Context context){
        this.context = context;
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }
    public void register(String email, String password, ProgressBar login){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("UserCreated", "onComplete: ");
                    String id = task.getResult().getUser().getUid();
                    User user = new User(password, email, id);
                    firebaseFirestore.collection("Users").document(id)
                            .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(context, "Thanks for creating account with us...", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("User Fire-store", "onFailure: " + e.getMessage());
                        }
                    });
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                    Activity activity = (Activity)context;
                    activity.finish();
                    login.setVisibility(View.GONE);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("UserSignIn", "onComplete: ");
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                            Activity activity = (Activity)context;
                            activity.finish();
                            login.setVisibility(View.GONE);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("UserSignIn", "onFailure: " + e.getMessage());
                        login.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    public void logout() {
        auth.signOut();
    }
}
