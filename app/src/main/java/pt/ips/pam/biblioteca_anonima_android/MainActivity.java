package pt.ips.pam.biblioteca_anonima_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import pt.ips.pam.biblioteca_anonima_android.db.AuthRequest;
import pt.ips.pam.biblioteca_anonima_android.db.SQLiteStorage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkStorage();

        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //test zone
                AuthRequest AR = new AuthRequest(MainActivity.this);
                AR.logout();
            }
        });
    }



    private void checkStorage() {
        SQLiteStorage SQLite = new SQLiteStorage(MainActivity.this);
        SQLite.checkLocalDB();
    }
}