package de.unidue.inf.is.domain;

public class Aufgabe {
    private int bnummer;
    private int kid;
    private int anummer;
    private int AID;

    public int getBnummer() {
        return bnummer;
    }

    public void setBnummer(int bnummer) {
        this.bnummer = bnummer;
    }

    String name="";
    String beschreibung="";
    String abgabetext = "";
    String Bewertung = "";
    boolean von1_bewertet = false;

    public int getAID() {
        return AID;
    }

    public void setAID(int AID) {
        this.AID = AID;
    }

    public boolean isVon1_bewertet() {
        return von1_bewertet;
    }

    public void setVon1_bewertet(boolean von1_bewertet) {
        this.von1_bewertet = von1_bewertet;
    }

    public Aufgabe() {
    }

    public void setKid(int kid) {
        this.kid = kid;
    }

    public String getAbgabetext() {
        return abgabetext;
    }

    public void setAbgabetext(String abgabetext) {
        this.abgabetext = abgabetext;
    }

    public void setAnummer(int anummer) {
        this.anummer = anummer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public int getKid() {
        return kid;
    }

    public int getAnummer() {
        return anummer;
    }

    public String getBewertung() {
        return Bewertung;
    }

    public void setBewertung(String bewertung) {
        Bewertung = bewertung;
    }

    public String getName() {
        return name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }
}