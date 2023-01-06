package pt.ips.pam.biblioteca_anonima_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import pt.ips.pam.biblioteca_anonima_android.db.AuthRequest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AuthRequest AR = new AuthRequest(MainActivity.this);

        AR.logout(null);
    }
}