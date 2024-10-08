package com.example.smdassignment2;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_SCREEN_TIMEOUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView logo = findViewById(R.id.appLogo);

        // Create the translate animation
        ObjectAnimator translateAnim = ObjectAnimator.ofFloat(logo, "translationY", -1000f, 0f);
        translateAnim.setDuration(3000);

        // Create the scale animations
        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(logo, "scaleX", 0.8f, 1.4f);
        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(logo, "scaleY", 0.8f, 1.4f);
        scaleXAnim.setDuration(3000);
        scaleYAnim.setDuration(3000);

        // Combine the animations using AnimatorSet
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translateAnim, scaleXAnim, scaleYAnim);
        animatorSet.start();

        // Wait for the animation to finish and navigate to MainActivity
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_SCREEN_TIMEOUT);
    }
}