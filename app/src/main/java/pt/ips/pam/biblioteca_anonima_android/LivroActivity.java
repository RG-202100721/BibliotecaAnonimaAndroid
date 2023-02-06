package pt.ips.pam.biblioteca_anonima_android;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pt.ips.pam.biblioteca_anonima_android.db.SQLiteStorage;

public class LivroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livro_view_holder);
        SQLiteStorage SQLite = new SQLiteStorage(LivroActivity.this);

        //recebe o bundle enviado da Main
        int pos = getIntent().getExtras().getInt("position");
        JSONObject data = SQLite.getBook(pos + 1);

        //Identifica as TextView e ImageView no layout e coloca as strings e imagens nos elementos
        TextView titulo = findViewById(R.id.livro_titulo);
        TextView ISBN = findViewById(R.id.livro_isbn);
        TextView paginas = findViewById(R.id.livro_paginas);
        TextView editora = findViewById(R.id.livro_editora);
        ImageView capa = findViewById(R.id.livro_capa);
        TextView autores = findViewById(R.id.livro_autores);
        TextView categorias = findViewById(R.id.livro_categorias);

        try {
            titulo.setText(data.getString("Titulo"));
            ISBN.setText(data.getString("ISBN"));
            paginas.setText(String.valueOf(data.getInt("Numero_Paginas")));

            editora.setText(data.getJSONObject("IDEditora").getString("Nome"));

            Picasso.get().load(data.getString("Capa")).into(capa);

            StringBuilder text = new StringBuilder();
            JSONArray array = data.getJSONArray("IDAutores");
            for (int i = 0; i < array.length(); i++) text.append(array.getJSONObject(i).getString("Nome")).append(", ");
            autores.setText(text.delete(text.length() - 2, text.length()).toString());

            text = new StringBuilder();
            array = data.getJSONArray("IDCategorias");
            for (int i = 0; i < array.length(); i++) text.append(array.getJSONObject(i).getString("Nome")).append(", ");
            categorias.setText(text.delete(text.length() - 2, text.length()).toString());
        }
        catch (JSONException e) {
            Log.d("error", String.valueOf(e));
            e.printStackTrace();
        }

        findViewById(R.id.b_livro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });
    }
    public void onBackPressed() {};
}