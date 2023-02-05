package pt.ips.pam.biblioteca_anonima_android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pt.ips.pam.biblioteca_anonima_android.db.Livro;

public class ListaItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public interface OnItemClickListener {
        void onItemClick(Object item);
    }
    class LivroViewHolder extends RecyclerView.ViewHolder {
        TextView nome;
        ImageView foto;
        TextView autores;
        TextView Editora;
        LivroViewHolder(View view) {
            super(view);
            nome = view.findViewById(R.id.textoNome);
            foto = view.findViewById(R.id.iconeImagem);
            autores=view.findViewById(R.id.texto_Autores);
            Editora=view.findViewById(R.id.texto_editora);
        }
        public void bind(final Object item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
    private final List<Object> items;
    private final OnItemClickListener itemClickListener;
    public ListaItemsAdapter(List<Object> items, OnItemClickListener clickListener) {
        this.items = items;
        this.itemClickListener = clickListener;
    }
    @Override
    public int getItemViewType(int position) {
        Object currObject = items.get(position);
        if(currObject instanceof Livro) {
            return 1;
        }
        return 0;
    }
    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case 0:
            case 1:
            default:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.book_list, parent, false);
                return new LivroViewHolder(itemView);
        }
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
            case 1:
            default:
                LivroViewHolder livroViewHolder = (LivroViewHolder) holder;
                livroViewHolder.nome.setText(((Livro) items.get(position)).getNome());
                livroViewHolder.foto.setImageResource(((Livro) items.get(position)).getFoto());
                livroViewHolder.autores.setText(((Livro) items.get(position)).getAutores());
                livroViewHolder.Editora.setText(((Livro) items.get(position)).getCategoria());
                livroViewHolder.bind(items.get(position), itemClickListener);
                break;
        }
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
}