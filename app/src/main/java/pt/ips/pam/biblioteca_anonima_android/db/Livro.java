package pt.ips.pam.biblioteca_anonima_android.db;

public class Livro {
    public String Nome;
    public int Foto;
    public String Sinopse;
    Livro (String Nome, int Foto,String Sinopse){
        this.Foto=Foto;
        this.Nome=Nome;
        this.Sinopse=Sinopse;
    }
    public String getNome() {
        return Nome;
    }
    public int getFoto() {
        return Foto;
    }
    public String getHabitat() {return Sinopse;}
}
