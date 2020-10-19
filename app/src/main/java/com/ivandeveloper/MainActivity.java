package com.ivandeveloper;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.os.CountDownTimer;


public class MainActivity extends AppCompatActivity {
    CountDownTimer countTimer;
    String dot = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        countTimer = new CountDownTimer(3000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                dot += ".";
                if(dot.length() == 3){
                    dot = "";
                }
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(MainActivity.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }


}