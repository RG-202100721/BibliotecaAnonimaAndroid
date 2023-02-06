package pt.ips.pam.biblioteca_anonima_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import pt.ips.pam.biblioteca_anonima_android.db.AuthRequest;
import pt.ips.pam.biblioteca_anonima_android.db.SQLiteStorage;
import pt.ips.pam.biblioteca_anonima_android.db.VolleyHandler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new SQLiteStorage(SplashActivity.this).checkLocalDB();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.p_bar).setVisibility(View.INVISIBLE);
                findViewById(R.id.b_splash).setVisibility(View.VISIBLE);
            }
        },5000);

        findViewById(R.id.b_splash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mainActivity);
                finish();
            }
        });
    }
}