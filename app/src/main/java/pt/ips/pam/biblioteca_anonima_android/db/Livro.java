package pt.ips.pam.biblioteca_anonima_android.db;

public class Livro {
    public String Nome;
    public int Foto;
    public String Autores;
    public String Categoria;

    Livro (String Nome, int Foto,String Autores,String Categoria){
        this.Foto=Foto;
        this.Nome=Nome;
        this.Autores=Autores;
        this.Categoria=Categoria;
    }
    public String getNome() {
        return Nome;
    }
    public int getFoto() {
        return Foto;
    }
    public String getAutores() {return Autores;}
    public String getCategoria(){return Categoria;}
}
