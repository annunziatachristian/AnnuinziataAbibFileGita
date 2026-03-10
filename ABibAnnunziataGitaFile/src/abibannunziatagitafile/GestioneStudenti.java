package abibannunziatagitafile;

import java.io.*;
import java.util.ArrayList;

public class GestioneStudenti {

    private static final int LEN_NOME        = 20;
    private static final int LEN_COGNOME     = 20;
    private static final int LEN_CLASSE      = 5;
    private static final int LEN_NOME_GITA   = 30;
    private static final int LEN_DESTINAZIONE= 30;
    private static final int DIM_RECORD =
        (LEN_NOME + LEN_COGNOME + LEN_CLASSE + LEN_NOME_GITA + LEN_DESTINAZIONE) * 2 + 8 + 1;

    private static final String FILE_NAME = "gite.dat";

 
    public void salvaRecord(Studente studente, Gita gita) throws IOException {
    try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "rw")) {
        raf.seek(raf.length());
        scriviRecord(raf, studente, gita);
    }
}

    
    public ArrayList<Gita> leggiTutti() throws IOException {
    ArrayList<Gita> gite = new ArrayList<>();

    File file = new File(FILE_NAME);

    if (!file.exists()) {
        file.createNewFile();   // crea il file vuoto
        return gite;            // ritorna lista vuota
    }

    try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
        long nRecord = raf.length() / DIM_RECORD;

        for (long i = 0; i < nRecord; i++) {
            raf.seek(i * DIM_RECORD);

            String nome = readString(raf, LEN_NOME);
            String cognome = readString(raf, LEN_COGNOME);
            String classe = readString(raf, LEN_CLASSE);
            String nomeGita = readString(raf, LEN_NOME_GITA);
            String destinazione = readString(raf, LEN_DESTINAZIONE);
            double costo = raf.readDouble();
            boolean iscritto = raf.readBoolean();

            Gita gita = trovaNomeGita(gite, nomeGita);
            if (gita == null) {
                gita = new Gita(nomeGita, destinazione, costo);
                gite.add(gita);
            }

            gita.aggiungiStudente(new Studente(nome, cognome, classe, iscritto));
        }
    }

    return gite;
}

    
    public ArrayList<Studente> cercaStudentiPerGita(String nomeGita) throws IOException {
        ArrayList<Gita> gite = leggiTutti();
        Gita trovata = trovaNomeGita(gite, nomeGita.trim());
        if (trovata == null) return new ArrayList<>();
        return trovata.getStudentiIscritti();
    }

   
    private Gita trovaNomeGita(ArrayList<Gita> gite, String nome) {
        for (Gita g : gite) {
            if (g.getNome().equalsIgnoreCase(nome)) return g;
        }
        return null;
    }

    private void writeString(RandomAccessFile raf, String s, int lunghezza) throws IOException {
        if (s.length() > lunghezza) s = s.substring(0, lunghezza);
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < lunghezza) sb.append(' ');
        raf.writeChars(sb.toString());
    }

    private String readString(RandomAccessFile raf, int lunghezza) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lunghezza; i++) sb.append(raf.readChar());
        return sb.toString().trim();
    }
    
    public boolean studenteGiaInseritoNellaGita(Studente studente, String nomeGita) throws IOException {
    ArrayList<Gita> gite = leggiTutti();

    for (Gita g : gite) {
        if (g.getNome().equalsIgnoreCase(nomeGita.trim())) {
            for (Studente s : g.getStudentiPartecipanti()) {
                if (s.getNome().equalsIgnoreCase(studente.getNome().trim()) &&
                    s.getCognome().equalsIgnoreCase(studente.getCognome().trim()) &&
                    s.getClasse().equalsIgnoreCase(studente.getClasse().trim())) {
                    return true;
                }
            }
        }
    }
    
    

    return false;
}
    
private void scriviRecord(RandomAccessFile raf, Studente studente, Gita gita) throws IOException {
    writeString(raf, studente.getNome(), LEN_NOME);
    writeString(raf, studente.getCognome(), LEN_COGNOME);
    writeString(raf, studente.getClasse(), LEN_CLASSE);
    writeString(raf, gita.getNome(), LEN_NOME_GITA);
    writeString(raf, gita.getDestinazione(), LEN_DESTINAZIONE);
    raf.writeDouble(gita.getCosto());
    raf.writeBoolean(studente.isIscritto());
}

public void svuotaFile() throws IOException {
    File file = new File(FILE_NAME);
    if (!file.exists()) return;

    try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
        raf.setLength(0);
    }
}

public boolean eliminaGita(String nomeGita) throws IOException {
    File file = new File(FILE_NAME);
    if (!file.exists()) return false;

    ArrayList<Gita> gite = leggiTutti();
    boolean trovata = false;

    try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
        raf.setLength(0);

        for (Gita g : gite) {
            if (g.getNome().equalsIgnoreCase(nomeGita.trim())) {
                trovata = true;
                continue;
            }

            for (Studente s : g.getStudentiPartecipanti()) {
                scriviRecord(raf, s, g);
            }
        }
    }

    return trovata;
}
}
