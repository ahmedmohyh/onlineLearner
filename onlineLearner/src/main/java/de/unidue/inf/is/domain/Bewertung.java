package de.unidue.inf.is.domain;

public class Bewertung {
private int bnummer =0;
private int Aid ;
private int note;

    public int getBnummer() {
        return bnummer;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public void setBnummer(int bnummer) {
        this.bnummer = bnummer;
    }

    public int getAid() {
        return Aid;
    }

    public void setAid(int aid) {
        Aid = aid;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

    private String kommentar;

}
