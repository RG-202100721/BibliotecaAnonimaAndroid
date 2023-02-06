package pt.ips.pam.biblioteca_anonima_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import pt.ips.pam.biblioteca_anonima_android.db.AuthRequest;
import pt.ips.pam.biblioteca_anonima_android.db.SQLiteStorage;
import pt.ips.pam.biblioteca_anonima_android.db.VolleyHandler;

public class CategoriaActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SQLiteStorage SQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        SQLite = new SQLiteStorage(CategoriaActivity.this);
        setupMenu();

        //lista para recyclerView
        List<JSONObject> lista = new ArrayList<JSONObject>();
        try {
            JSONArray array = SQLite.getCategories();
            for (int i = 0; i < array.length(); i++) lista.add(array.getJSONObject(i));
        }
        catch (JSONException e) { e.printStackTrace(); }

        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(CategoriaActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ListaItemsAdapter adapter = new ListaItemsAdapter(lista, null, CategoriaActivity.this);
        recyclerView.setAdapter(adapter);
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
                Intent intent = new Intent(CategoriaActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_categories:
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_publishers:
                Intent intent2 = new Intent(CategoriaActivity.this, EditoraActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_authors:
                Intent intent3 = new Intent(CategoriaActivity.this, AutorActivity.class);
                startActivity(intent3);
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
                        Intent splash = new Intent(CategoriaActivity.this, SplashActivity.class);
                        startActivity(splash);
                    }
                });
                break;
            case R.id.nav_login:
                Intent login = new Intent(CategoriaActivity.this, LoginActivity.class);
                startActivity(login);
                break;
            case R.id.nav_logout:
                new AuthRequest(CategoriaActivity.this).logout();
                break;
        }
        return true;
    }
}