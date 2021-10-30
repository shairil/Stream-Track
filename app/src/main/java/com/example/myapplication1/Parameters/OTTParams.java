package com.example.myapplication1.Parameters;

import com.google.firebase.auth.FirebaseAuth;

public final class OTTParams {
    public final String uid = FirebaseAuth.getInstance().getUid();
    public final String DBName = "OTTDB";
    public final int Version = 1;
    public final String TBName = "OTTDetail";
    public final String KEY_PLATFORM = "Platform";
    public final String KEY_UserName = "UserName";
    public final String KEY_Email = "Email";
    public final String KEY_Plan = "PlanDetails";
    public final String KEY_Date = "Date";
    public final String KEY_GroupId = "GroupId";
}
