package pt.ips.pam.biblioteca_anonima_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import pt.ips.pam.biblioteca_anonima_android.db.AuthRequest;
import pt.ips.pam.biblioteca_anonima_android.db.DatabaseRequest;
import pt.ips.pam.biblioteca_anonima_android.db.SQLiteStorage;
import pt.ips.pam.biblioteca_anonima_android.db.VolleyHandler;

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

                JSONObject info = new JSONObject();
                try {
                    info.put("Numero_Conta", 202100721);
                    info.put("Password", "password");
                }
                catch (JSONException e) {
                    Log.d("JSONException", e.toString());
                }

                AR.login(info, null);
            }
        });
    }



    private void checkStorage() {
        SQLiteStorage SQLite = new SQLiteStorage(MainActivity.this);
        SQLite.checkLocalDB();

    }
}