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
        Bundle extras = getIntent().getExtras();
        String nome = getIntent().getExtras().getString("nome");
        String habitat = getIntent().getExtras().getString("habitat");
        int foto = extras.getInt("foto");
        TextView textoNome = findViewById(R.id.textoNome);
        TextView textoSinopse = findViewById(R.id.sinopse);
        ImageView imagemFoto = findViewById(R.id.iconeImagem);
        textoNome.setText(nome);
        textoSinopse.setText(habitat);
        imagemFoto.setImageResource(foto);
    }
    public void onClick(View v) {
        finish();
    }
    public void onBackPressed(){};
}