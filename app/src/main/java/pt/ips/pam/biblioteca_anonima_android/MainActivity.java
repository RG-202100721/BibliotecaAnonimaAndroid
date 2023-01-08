package pt.ips.pam.biblioteca_anonima_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import pt.ips.pam.biblioteca_anonima_android.db.SQLiteStorage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkStorage();

    }

    private void checkStorage() {
        //não mexer nesta função, sff
        SQLiteStorage SQLite = new SQLiteStorage(MainActivity.this);
        SQLite.checkLocalDB();
    }
}