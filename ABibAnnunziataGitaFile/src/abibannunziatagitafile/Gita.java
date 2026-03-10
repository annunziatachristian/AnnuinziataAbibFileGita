package abibannunziatagitafile;

import java.util.ArrayList;
import java.util.Objects;

public class Gita {
    private String nome;
    private String destinazione;
    private double costo;
    private ArrayList<Studente> studentiPartecipanti;

    public Gita(String nome, String destinazione, double costo) {
        this.nome = nome;
        this.destinazione = destinazione;
        this.costo = costo;
        this.studentiPartecipanti = new ArrayList<>();
    }

    public String getNome() {
        return nome; 
    }
    public String getDestinazione() {
        return destinazione; 
    }
    public double getCosto() {
        return costo; 
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Gita other = (Gita) obj;
        if (Double.doubleToLongBits(this.costo) != Double.doubleToLongBits(other.costo)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.destinazione, other.destinazione)) {
            return false;
        }
        return Objects.equals(this.studentiPartecipanti, other.studentiPartecipanti);
    }
    public ArrayList<Studente> getStudentiPartecipanti() {
        return studentiPartecipanti; 
    }

    public void aggiungiStudente(Studente s) {
        studentiPartecipanti.add(s);
    }

    public ArrayList<Studente> getStudentiIscritti() {
        ArrayList<Studente> iscritti = new ArrayList<>();
        for (Studente s : studentiPartecipanti) {
            if (s.isIscritto()) iscritti.add(s);
        }
        return iscritti;
    }

    @Override
    public String toString() {
        return "Gita: " + nome + " | Destinazione: " + destinazione + " | Costo: " + costo + "€";
    }
}
