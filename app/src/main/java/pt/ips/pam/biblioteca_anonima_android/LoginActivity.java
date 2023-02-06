package pt.ips.pam.biblioteca_anonima_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import pt.ips.pam.biblioteca_anonima_android.db.AuthRequest;
import pt.ips.pam.biblioteca_anonima_android.db.SQLiteStorage;
import pt.ips.pam.biblioteca_anonima_android.db.VolleyHandler;

public class LoginActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SQLiteStorage SQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SQLite = new SQLiteStorage(LoginActivity.this);
        setupMenu();

        findViewById(R.id.b_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView conta = findViewById(R.id.login_conta);
                TextView pass = findViewById(R.id.login_pass);

                JSONObject info = new JSONObject();
                try {
                    info.put("Numero_Conta", conta.getText().toString());
                    info.put("Password", pass.getText().toString());
                }
                catch (JSONException e) {
                    Log.d("JSONException", e.toString());
                }

                new AuthRequest(LoginActivity.this).login(info, new VolleyHandler.callback() {
                    @Override
                    public void onSuccess() {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    public ActionBarDrawerToggle actionBarDrawerToggle;
    public void setupMenu() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener(this);

        if (SQLite.getAdmin() != null) {
            Menu menu = navigationView.getMenu();
            MenuItem register = menu.findItem(R.id.nav_crud);
            register.setVisible(true);
            register = menu.findItem(R.id.nav_login);
            register.setVisible(false);
            register = menu.findItem(R.id.nav_logout);
            register.setVisible(true);
            register.setTitle(String.format("Logout (%s)", SQLite.getAdmin()));
        }

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) return true;
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_books:
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_categories:
                Intent intent2 = new Intent(LoginActivity.this, CategoriaActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_publishers:
                Intent intent3 = new Intent(LoginActivity.this, EditoraActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav_authors:
                Intent intent4 = new Intent(LoginActivity.this, AutorActivity.class);
                startActivity(intent4);
                break;
            case R.id.nav_insert:

                break;
            case R.id.nav_update:

                break;
            case R.id.nav_delete:

                break;
            case R.id.nav_reset:
                SQLite.reset(new VolleyHandler.callback() {
                    @Override
                    public void onSuccess() {
                        Intent splash = new Intent(LoginActivity.this, SplashActivity.class);
                        startActivity(splash);
                    }
                });
                break;
            case R.id.nav_login:
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_logout:
                new AuthRequest(LoginActivity.this).logout();
                break;
        }
        return true;
    }
}