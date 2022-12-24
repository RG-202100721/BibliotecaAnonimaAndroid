package pt.ips.pam.biblioteca_anonima_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseRequest DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB = new DatabaseRequest(MainActivity.this);

        DB.getBooks(new VolleyHandler.callback() {
            @Override
            public void onSuccess(ArrayList<String> data) {

            }
        });
    }
}