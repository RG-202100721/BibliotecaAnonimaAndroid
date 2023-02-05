package pt.ips.pam.biblioteca_anonima_android;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LivroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livro_view_holder);
        //recebe o bundle enviado da Main
        Bundle extras = getIntent().getExtras();
        String nome = getIntent().getExtras().getString("nome");
        String autor = getIntent().getExtras().getString("Autores");
        String categoria = getIntent().getExtras().getString("editora");
        int foto = extras.getInt("foto");
        //Identifica as TextView e ImageView no layout e coloca as strings e imagens nos elementos
        TextView textoNome = findViewById(R.id.textoNome);
        TextView textoAutor = findViewById(R.id.texto_Autores);
        ImageView imagemFoto = findViewById(R.id.iconeImagem);
        TextView textoEditora = findViewById(R.id.texto_editora);
        textoNome.setText(nome);
        textoAutor.setText(autor);
        textoEditora.setText(categoria);
        imagemFoto.setImageResource(foto);
    }
    public void onClick(View v) {
        finish();
    }
    public void onBackPressed(){};
}