package pt.ips.pam.biblioteca_anonima_android.db;

public class Livro {
    public String Nome;
    public int Foto;
    public String Autores;
    Livro (String Nome, int Foto,String Autores){
        this.Foto=Foto;
        this.Nome=Nome;
        this.Autores=Autores;
    }
    public String getNome() {
        return Nome;
    }
    public int getFoto() {
        return Foto;
    }
    public String getHabitat() {return Autores;}
}
