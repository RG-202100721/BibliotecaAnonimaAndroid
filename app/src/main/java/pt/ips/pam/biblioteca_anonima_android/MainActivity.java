package pt.ips.pam.biblioteca_anonima_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import pt.ips.pam.biblioteca_anonima_android.db.Livro;
import pt.ips.pam.biblioteca_anonima_android.db.SQLiteStorage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkStorage();
        //lista para recyclerView
        List<Object> lista=new ArrayList<Object>();
        RecyclerView recyclerView = findViewById(R.id.Recicla_livros);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ListaItemsAdapter adapter = new ListaItemsAdapter(lista, new
                ListaItemsAdapter.OnItemClickListener() {
                    @Override
                    //metodo que muda o layout no click do objeto na recyclerView
                    //e coloca o nome,imagem e autores nas textviews e imageviews vazias no layout livro_view_holder
                    public void onItemClick(Object item) {
                        if (item instanceof Livro) {
                            Livro livro = (Livro) item;
                            //cria um bundle para enviar o nome,autores e foto de um livro para
                            //a LivroActivity
                            Bundle bundle = new Bundle();
                            bundle.putString("nome", livro.getNome());
                            bundle.putString("Autores", livro.getAutores());
                            bundle.putInt("foto", livro.getFoto());
                            Intent intent = new Intent(MainActivity.this, LivroActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }
                    }
                });
    }
    private void checkStorage() {
        //não mexer nesta função, sff
        SQLiteStorage SQLite = new SQLiteStorage(MainActivity.this);
        SQLite.checkLocalDB();
    }
}