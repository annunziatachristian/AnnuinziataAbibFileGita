package abibannunziatagitafile;

public class Studente {
    private String nome;
    private String cognome;
    private String classe;
    private boolean iscritto;

    public Studente(String nome, String cognome, String classe, boolean iscritto) {
        this.nome = nome;
        this.cognome = cognome;
        this.classe = classe;
        this.iscritto = iscritto;
    }

    public String getNome() {
        return nome; 
    }
    public String getCognome() {
        return cognome; 
    }
    public String getClasse() {
        return classe; 
    }
    public boolean isIscritto() {
        return iscritto;
    }

    public void setNome(String nome) { 
        this.nome = nome; 
    }
    public void setCognome(String cognome)   {
        this.cognome = cognome; 
    }
    public void setClasse(String classe)     {
        this.classe = classe; 
    }
    public void setIscritto(boolean iscritto){
        this.iscritto = iscritto;
    }

    @Override
    public String toString() {
        return nome + " " + cognome + " | Classe: " + classe + " | Iscritto: " + (iscritto ? "Sì" : "No");
    }
}
