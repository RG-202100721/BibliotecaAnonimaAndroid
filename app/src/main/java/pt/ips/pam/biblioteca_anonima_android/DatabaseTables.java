package pt.ips.pam.biblioteca_anonima_android;

import androidx.annotation.NonNull;

public enum DatabaseTables {
    AUTOR, EDITORA, LIVRO, CATEGORIA, LIVRO_AUTOR, LIVRO_CATEGORIA;

    @NonNull
    public String toString() {
        String table = "";
        if (this == AUTOR) {
            table = "Autor";
        } else if (this == EDITORA) {
            table = "Editora";
        } else if (this == LIVRO) {
            table = "Livro";
        } else if (this == CATEGORIA) {
            table = "Categoria";
        } else if (this == LIVRO_AUTOR) {
            table = "Livro_Autor";
        } else if (this == LIVRO_CATEGORIA) {
            table = "Livro_Categoria";
        }
        return table;
    }
}