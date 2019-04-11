package com.deepak.TeaPoint.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.deepak.TeaPoint.R;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }


    public void  getStarted(View view)
    {
        startActivity(new Intent(this, LoginActivity.class));
    }

}
