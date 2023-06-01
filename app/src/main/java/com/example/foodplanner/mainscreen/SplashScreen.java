package com.example.foodplanner.mainscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplanner.R;
import com.example.foodplanner.signup.view.MainSignUpActivity;
import com.example.foodplanner.signup.view.SignUpActivity;

public class SplashScreen extends AppCompatActivity {
    LottieAnimationView lottieAnimationView;

    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        lottieAnimationView=findViewById(R.id.animation);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainSignUpActivity.class));
                finish();
            }
        }, 3000);
    }
}